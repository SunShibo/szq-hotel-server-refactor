package com.szq.hotel.service;

import com.szq.hotel.common.constants.Constants;
import com.szq.hotel.dao.RoomDAO;
import com.szq.hotel.dao.RoomRecordDAO;
import com.szq.hotel.entity.bo.*;
import com.szq.hotel.entity.dto.*;
import com.szq.hotel.entity.dto.OcDTO;
import com.szq.hotel.util.DateUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: Bin Wang
 * @date: Created in 13:00 2019/7/5
 */
@Service
@Transactional
public class RoomService {

    static final Logger log = LoggerFactory.getLogger(RoomService.class);

    @Resource
    private RoomDAO roomDAO;
    @Resource
    private RoomRecordDAO roomRecordDAO;

    public Map<String, Object> queryRoom(Map<String, Object> map) {
        log.info("map:{}", map);
        Map<String, Object> mp = new HashMap<String, Object>();
        List<RoomBO> list = roomDAO.queryRoom(map);
        Integer count = roomDAO.queryRoomCount(map);
        mp.put("list", list);
        mp.put("count", count);
        log.info("return:{}", mp);
        return mp;
    }

    /*
        通过id查询房间信息
    */
    public RoomBO selectByPrimaryKey(Integer id) {
        return roomDAO.selectByPrimaryKey(id);
    }

    public int insertSelective(RoomBO record) {
        return roomDAO.insertSelective(record);
    }

    public int updateByPrimaryKeySelective(RoomBO record) {
        return roomDAO.updateByPrimaryKeySelective(record);
    }

    public void deleteByPrimaryKey(Integer[] id) {
        roomDAO.updateShow(id);
    }

    public void updatelockRoomClose(Map<String, Object> map) {
        roomDAO.updatelockRoomState(map);
    }

    public void updatelockRoomOpen(Map<String, Object> map) {
        roomDAO.updatelockRoomState2(map);
    }

    public List<RoomTypeCountBO> queryRoomTypeCount(Integer id) {
        return roomDAO.queryRoomTypeCount(id);
    }

    public void updateroomMajorState(Map<String, Object> map) {

        log.info("map:{}", map);
        RoomBO roomBO = roomDAO.selectByPrimaryKey((Integer) map.get("id"));
        log.info("roomBO:{}", roomBO);
        Map<String, Object> mp = new HashMap<String, Object>();
        mp.put("createTime", new Date());
        mp.put("createUserId", map.get("userId"));
        mp.put("newState", map.get("state"));
        mp.put("roomId", roomBO.getId());
        mp.put("virginState", roomBO.getRoomMajorState());
        mp.put("remark", map.get("remark"));
        log.info("mp:{}", mp);
        int i = roomRecordDAO.insertRoomState(mp);
        log.info("i:{}", i);
        roomDAO.updateroomMajorState(map);
    }

    /**
     * 抽取公共方法
     *
     * @param map
     * @return
     */
    public List<RmBO> publicQuery(Map<String, Object> map, List<String> ll) {
        log.info("*************************************publicQuery***************************************************");
        //获取预入住时间
        String dt = (String) map.get("checkTime");

        String et = (String) map.get("endTime");


        //获取符合条件的房间集合
        List<RmBO> list = roomDAO.queryRm(map);


        if (CollectionUtils.isEmpty(list)) {
            return list;
        }
        List<Integer> ls = new ArrayList<Integer>();
        //获取出房间的id
        if (!CollectionUtils.isEmpty(list)) {
            for (RmBO id : list) {
                ls.add(id.getId());
            }
        }


        List<OcBO> l = roomDAO.queryOc(ls, dt, et, ll);

        List<Integer> reId = new ArrayList<Integer>();
        if (!CollectionUtils.isEmpty(l)) {
            for (OcBO oc : l) {

                reId.add(oc.getRoomId());
            }
        }



        //去重
        for (int i = 0; i < reId.size() - 1; i++) {
            for (int j = reId.size() - 1; j > i; j--) {
                if (reId.get(j).equals(reId.get(i))) {
                    reId.remove(j);
                }
            }
        }



        //去掉不能预约入住的房间的房间
        Iterator<RmBO> iterator = list.iterator();
        if (!CollectionUtils.isEmpty(reId)) {
            while (iterator.hasNext()) {
                RmBO rmBO = iterator.next();
                for (Integer id : reId) {
                    if (rmBO.getId().equals(id)) {
                        iterator.remove();//使用迭代器的删除方法删除
                    }
                }
            }
        }


        return list;
    }


    /**
     * 预约入住选择房间
     */
    public List<List<RmBO>> queryRm(Map<String, Object> map) {
        log.info("*************************************************queryRm*********************************************");
        //Map<String, Object> mp = new LinkedHashMap<String, Object>();
        List<List<RmBO>> ls = new ArrayList<List<RmBO>>();
        //获取酒店下面所有楼层
        Integer hotelId = (Integer) map.get("hotelId");
        log.info("hotelId:{}", hotelId);
        List<FlrBO> flrList = roomDAO.queryFlr(hotelId);
        log.info("酒店下共有楼层:{}", flrList);
        List<String> ll = new ArrayList<String>();
        ll.add("reservation");
        ll.add("notpay");
        ll.add("admissions");
        List<RmBO> list = this.publicQuery(map, ll);
        log.info("结果:{}",list);

        String phone = (String) map.get("phone");
        MemberDiscountBO memberDiscountBO = queryMember(phone);
        if (memberDiscountBO == null) {
            //不是会员
            // mp.put("discount", false);
        } else {
            //是会员
            //获取折扣价格
            //mp.put("discount", true);
            Double discount = memberDiscountBO.getDiscount();
            if (!CollectionUtils.isEmpty(list)) {
                for (RmBO rmBO : list) {
                    rmBO.setBasicPrice(Math.ceil(rmBO.getBasicPrice() * discount));
                    rmBO.setHourRoomPrice(Math.ceil(rmBO.getHourRoomPrice() * discount));
                }
            }
        }


        if (!CollectionUtils.isEmpty(flrList) && !CollectionUtils.isEmpty(list)) {
            for (FlrBO f : flrList) {
                List<RmBO> a = new ArrayList<RmBO>();
                for (RmBO rmBO : list) {
                    if (rmBO.getFloorId().equals(f.getId())) {
                        a.add(rmBO);
                    }
                }
                ls.add(a);
            }
        }

        // mp.put("list",list);

        return ls;
    }




    public List<RoomTypeNumBO> queryRoomTypeNum(Map<String, Object> map) {
        //Map<String, Object> mp = new HashMap<String, Object>();
        List<String> ll = new ArrayList<String>();
        ll.add("reservation");
        ll.add("notpay");
        ll.add("admissions");
        //根据条件筛选出第一波可入住的房间
        List<RmBO> list = this.publicQuery(map, ll);
        Integer hotelId = (Integer) map.get("hotelId");
        String phone = (String) map.get("phone");
        log.info("phone:{}", phone);
        log.info("list:{}", list);

        List<RoomTypeNumBO> ls = new ArrayList<RoomTypeNumBO>();

        //获取所有房型信息
        List<RtBO> rtBOS = roomDAO.queryRt(hotelId);

        log.info("rtBOS:{}", rtBOS);



        //判断用户是否是会员
        MemberDiscountBO memberDiscountBO = queryMember(phone);
        log.info("是否是会员:{}", memberDiscountBO);
        //没有优惠
        if (memberDiscountBO == null) {
            log.info("没有优惠");
            if (!CollectionUtils.isEmpty(rtBOS) && !CollectionUtils.isEmpty(list)) {
                for (RtBO rtBO : rtBOS) {
                    RoomTypeNumBO roomTypeBO = new RoomTypeNumBO();
                    //roomTypeBO.setState(false);
                    roomTypeBO.setName(rtBO.getRoomTypeName());
                    roomTypeBO.setHotelId(rtBO.getHotelId());
                    roomTypeBO.setId(rtBO.getId());
                    roomTypeBO.setBasicPrice(rtBO.getBasicPrice());
                    roomTypeBO.setHourRoomPrice(rtBO.getHourRoomPrice());
                    //roomTypeBO.setName(rtBO.getRoomType());
                    Integer i = 0;
                    for (RmBO rmBO : list) {
                        if (rtBO.getId().equals(rmBO.getRoomTypeId())) {
                            //roomTypeBO.setBasicPrice(rmBO.getBasicPrice());
                            //roomTypeBO.setHourRoomPrice(rmBO.getHourRoomPrice());
                            //roomTypeBO.setHotelId(rmBO.getHotelId());
                            i++;
                        }
                        roomTypeBO.setCount(i);
                    }
                    ls.add(roomTypeBO);
                }
            }
        } else {
            //有优惠
            if (!CollectionUtils.isEmpty(rtBOS) && !CollectionUtils.isEmpty(list)) {
                log.info("有优惠");
                for (RtBO rtBO : rtBOS) {
                    RoomTypeNumBO roomTypeBO = new RoomTypeNumBO();
                    //roomTypeBO.setState(true);
                    roomTypeBO.setName(rtBO.getRoomTypeName());
                    roomTypeBO.setId(rtBO.getId());
                    roomTypeBO.setHotelId(rtBO.getHotelId());
                    roomTypeBO.setBasicPrice(Math.ceil(rtBO.getBasicPrice() * memberDiscountBO.getDiscount()));
                    roomTypeBO.setHourRoomPrice(Math.ceil(rtBO.getHourRoomPrice() * memberDiscountBO.getDiscount()));
                    //roomTypeBO.setName(rtBO.getRoomType());
                    Integer i = 0;
                    for (RmBO rmBO : list) {
                        if (rtBO.getId().equals(rmBO.getRoomTypeId())) {
                            //roomTypeBO.setBasicPrice(rmBO.getBasicPrice() * memberDiscountBO.getDiscount());
                            // roomTypeBO.setHourRoomPrice(rmBO.getHourRoomPrice() * memberDiscountBO.getDiscount());
                            //roomTypeBO.setHotelId(rmBO.getHotelId());
                            // roomTypeBO.setName(rmBO.getRoomType());
                            //roomTypeBO.setId(rmBO.getId());
                            i++;
                        }
                        roomTypeBO.setCount(i);
                    }
                    ls.add(roomTypeBO);
                }
            }
        }

        log.info("checkTime:{}",(String) map.get("checkTime"));
        log.info("endTime:{}",(String) map.get("endTime"));
        log.info("hotelId:{}", (Integer) map.get("hotelId"));

        //获取被预约掉的房型以及数量
        List<RmTypeIdBO> rmTypeIdBOS = roomDAO.queryOrderTypeRoom((String) map.get("checkTime"), (String) map.get("endTime"),
                (Integer) map.get("hotelId"));

        log.info("rmTypeIdBOS:{}",rmTypeIdBOS);
        //筛选掉被预约掉的房型数量

        List<RoomTypeNumberBO> roomTypeList = new ArrayList<RoomTypeNumberBO>();
        if(!CollectionUtils.isEmpty(rtBOS) && !CollectionUtils.isEmpty(rmTypeIdBOS)){
            for (RtBO rtBO : rtBOS) {
                RoomTypeNumberBO roomTypeNumber = new RoomTypeNumberBO();
                roomTypeNumber.setRoomTypeId(rtBO.getId());
                int i = 0;
                for (RmTypeIdBO integer : rmTypeIdBOS) {
                    if(rtBO.getId().equals(integer.getRoomTypeId())){
                        i++;
                    }
                }
                roomTypeNumber.setCount(i);
                roomTypeList.add(roomTypeNumber);
                log.info("roomTypeNumber:{}",roomTypeNumber);
            }

        }

        if(!CollectionUtils.isEmpty(ls) && !CollectionUtils.isEmpty(roomTypeList)){
            for (RoomTypeNumBO l : ls) {
                for (RoomTypeNumberBO roomTypeNumberBO : roomTypeList) {
                    if(l.getId().equals(roomTypeNumberBO.getRoomTypeId())){
                        if(l.getCount()<roomTypeNumberBO.getCount()){
                            l.setCount(0);
                        }else{
                            l.setCount(l.getCount()-roomTypeNumberBO.getCount());
                        }

                    }
                }
            }
        }

        return ls;
    }

    /**
     * 获取同一时间同一房型的可用数量
     * @param map
     * @param rtBO
     * @return
     */
    public RoomTypeNumBO queryRoomTypeNum2(Map<String, Object> map, RtBO rtBO) {
        //Map<String, Object> mp = new HashMap<String, Object>();
        List<String> ll = new ArrayList<String>();
        ll.add("reservation");
        ll.add("notpay");
        ll.add("admissions");
        //根据条件筛选出第一波可入住的房间
        List<RmBO> list = this.publicQuery(map, ll);
        Integer hotelId = (Integer) map.get("hotelId");
        String phone = (String) map.get("phone");





        RoomTypeNumBO roomTypeBO = new RoomTypeNumBO();

        //判断用户是否是会员
        MemberDiscountBO memberDiscountBO = queryMember(phone);
        log.info("是否是会员:{}", memberDiscountBO);
        //没有优惠
        if (memberDiscountBO == null) {
            log.info("没有优惠");
            if ( !CollectionUtils.isEmpty(list)) {


                    //roomTypeBO.setState(false);
                    roomTypeBO.setName(rtBO.getRoomTypeName());
                    roomTypeBO.setHotelId(rtBO.getHotelId());
                    roomTypeBO.setId(rtBO.getId());
                    roomTypeBO.setBasicPrice(rtBO.getBasicPrice());
                    roomTypeBO.setHourRoomPrice(rtBO.getHourRoomPrice());
                    //roomTypeBO.setName(rtBO.getRoomType());
                    Integer i = 0;
                    for (RmBO rmBO : list) {
                        if (rtBO.getId().equals(rmBO.getRoomTypeId())) {
                            //roomTypeBO.setBasicPrice(rmBO.getBasicPrice());
                            //roomTypeBO.setHourRoomPrice(rmBO.getHourRoomPrice());
                            //roomTypeBO.setHotelId(rmBO.getHotelId());
                            i++;
                        }
                        roomTypeBO.setCount(i);
                    }


            }
        } else {
            //有优惠
            if ( !CollectionUtils.isEmpty(list)) {
                log.info("有优惠");
                    //roomTypeBO.setState(true);
                    roomTypeBO.setName(rtBO.getRoomTypeName());
                    roomTypeBO.setId(rtBO.getId());
                    roomTypeBO.setHotelId(rtBO.getHotelId());
                    roomTypeBO.setBasicPrice(Math.ceil(rtBO.getBasicPrice() * memberDiscountBO.getDiscount()));
                    roomTypeBO.setHourRoomPrice(Math.ceil(rtBO.getHourRoomPrice() * memberDiscountBO.getDiscount()));
                    //roomTypeBO.setName(rtBO.getRoomType());
                    Integer i = 0;
                    for (RmBO rmBO : list) {
                        if (rtBO.getId().equals(rmBO.getRoomTypeId())) {
                            //roomTypeBO.setBasicPrice(rmBO.getBasicPrice() * memberDiscountBO.getDiscount());
                            // roomTypeBO.setHourRoomPrice(rmBO.getHourRoomPrice() * memberDiscountBO.getDiscount());
                            //roomTypeBO.setHotelId(rmBO.getHotelId());
                            // roomTypeBO.setName(rmBO.getRoomType());
                            //roomTypeBO.setId(rmBO.getId());
                            i++;
                        }
                        roomTypeBO.setCount(i);
                    }

            }
        }




        //获取被预约掉的房型以及数量
        List<RmTypeIdBO> rmTypeIdBOS = roomDAO.queryOrderTypeRoom((String) map.get("checkTime"), (String) map.get("endTime"),
                (Integer) map.get("hotelId"));


        //筛选掉被预约掉的房型数量

        List<RoomTypeNumberBO> roomTypeList = new ArrayList<RoomTypeNumberBO>();
        if(!CollectionUtils.isEmpty(rmTypeIdBOS)){
                RoomTypeNumberBO roomTypeNumber = new RoomTypeNumberBO();
                roomTypeNumber.setRoomTypeId(rtBO.getId());
                int i = 0;
                for (RmTypeIdBO integer : rmTypeIdBOS) {
                    if(rtBO.getId().equals(integer.getRoomTypeId())){
                        i++;
                    }
                }
                roomTypeNumber.setCount(i);
                roomTypeList.add(roomTypeNumber);

            }



        if(!CollectionUtils.isEmpty(roomTypeList)){
                for (RoomTypeNumberBO roomTypeNumberBO : roomTypeList) {
                    if(roomTypeBO.getId().equals(roomTypeNumberBO.getRoomTypeId())){
                        if(roomTypeBO.getCount()<roomTypeNumberBO.getCount()){
                            roomTypeBO.setCount(0);
                        }else{
                            roomTypeBO.setCount(roomTypeBO.getCount()-roomTypeNumberBO.getCount());
                        }

                    }
                }
        }

        return roomTypeBO;
    }
    /**
     * 判断时间是否在某一区间内
     *
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 查询酒店下面的房型
     *
     * @param hotelId
     * @return
     */
    public List<RtBO> queryRt(Integer hotelId) {
        return roomDAO.queryRt(hotelId);
    }


    /**
     * 查询未来15天该房间是否有人预约
     *
     * @param roomId
     * @return
     */
    public List<RoomStateDTO> queryindexRoomState(Integer roomId) throws Exception {
        //获取房间所有订单列表
        List<OcDTO> ocBOS = roomDAO.queryTc(roomId);
        //获取未来十五天的时间段
        List<Time> times = timeDate(new Date());
        //判断未来十五天的时间段是否包括在订单列表入住时间和离开时间这个时间段之间
        log.info("roomId:{}", roomId);
        log.info("ocBOs:{}", ocBOS);
        List<RoomStateDTO> list = new ArrayList<RoomStateDTO>();
        for (Time time : times) {
            log.info("进入time");
            RoomStateDTO roomState = new RoomStateDTO();
            roomState.setStartDate(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(time.getStartTime()));
            roomState.setEndDate(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(time.getEndTime()));
            for (OcDTO rtBOS1 : ocBOS) {
                log.info("进入循环");
                boolean b = false;
                try {
                    b = isDate(rtBOS1.getStartTime(), rtBOS1.getEndTime(), time.getStartTime(), time.getEndTime());
                    log.info("b:{}", b);
                    if (b) {
                        roomState.setState("有预约");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            list.add(roomState);
        }
        return list;
    }


    /**
     * 获取今天某一时间
     *
     * @param i
     * @return
     */
    public Date quDate(Integer i, Integer i2, Integer i3) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, i);
        c.set(Calendar.MINUTE, i2);
        c.set(Calendar.SECOND, i3);
        Date m6 = c.getTime();
        //System.out.println(m6);
        return m6;
    }


    /**
     * 获取未来i天这个时间
     *
     * @param date
     * @return
     */
    public Date lDate(Date date, Integer i) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, i);//把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime(); //这个时间就是日期往后推一天的结果
        return date;
    }


    /**
     * 获取未来十五天的时间
     *
     * @param date
     * @return
     */
    public List<Time> timeDate(Date date) {
        List<Time> list = new ArrayList<Time>();

        //当天凌晨六点
        Date da = quDate(6, 0, 0);
        //当天凌晨5点59分59秒
        Date dat = quDate(5, 59, 59);
        //从当天开始的下一天开始时间
        Date dd = lDate(da, 0);
        //从当天开始的下一天结束时间
        Date d = lDate(dat, 0);
        //从明天开始的下一天开始时间
        Date dad = lDate(da, 1);
        //从明天开始的下一天结束时间
        Date dadd = lDate(dat, 1);
        //判断当前时间是否在凌晨零点到凌晨六点之间
        //如果是那么获取今天6点后开始的之后的十五天的时间段
        if (belongCalendar(date, quDate(0, 0, 0), quDate(05, 59, 59))) {
            System.err.println("start if");
            for (int i = 0; i < 15; i++) {
                Time time = new Time();
                time.setStartTime(lDate(dd, i));
                //System.err.println(lDate(dd, i));
                //System.err.println(lDate(d, i+1));
                time.setEndTime(lDate(d, i + 1));
                list.add(time);
            }
            //如果不是那么获取明天开始后6点之后的十五天的时间段
        } else {
            System.err.println("start else");
            for (int i = 0; i < 15; i++) {
                Time time = new Time();
                time.setStartTime(lDate(dad, i));
                //System.err.println(lDate(dad, i));
                //System.err.println(lDate(dadd, i+1));
                time.setEndTime(lDate(dadd, i + 1));
                list.add(time);
            }
        }
        return list;
    }

    /**
     * 获取某个区间段的时间
     *
     * @param
     * @return
     */
    public List<Time> timeDate2(Date da, Date dab, Integer num) {
        List<Time> list = new ArrayList<Time>();
        for (int i = 0; i < num; i++) {
            Time time = new Time();
            time.setStartTime(lDate(da, i));
            //System.err.println(lDate(dd, i));
            //System.err.println(lDate(d, i+1));
            time.setEndTime(lDate(dab, i + 1));
            list.add(time);
        }
        return list;
    }

    /**
     * 判断用户是否为会员
     * 并且获取折扣价格
     *
     * @param phone
     * @return
     */
    public MemberDiscountBO queryMember(String phone) {
        MemberDiscountBO memberDiscountBO = roomDAO.queryMemberByPhone(phone);
        return memberDiscountBO;
    }


    /*
         修改房间是否为维修房
      */
    public void updateRoomState(Integer id, Integer userId) {
        //查询房间主状态
        String majorState = roomDAO.getRoomMajorState(id);
        //查询房间维修状态
        String state = roomDAO.getRoomState(id);
        RoomBO roomBO = new RoomBO();
        roomBO.setId(id);
        //之前不是维修房改为维修房
        if ("no".equals(state)) {
            //执行修改
            roomBO.setRoomState("yes");
            roomDAO.updateRoomState(roomBO);
            //添加操作日志
            RoomRecordBO recordBO = new RoomRecordBO();
            recordBO.setRoomId(id);
            recordBO.setVirginState(majorState);
            recordBO.setNewState(majorState);
            recordBO.setRemark("改为维修房");
            recordBO.setCreateUserId(userId);
            recordBO.setCreateTime(new Date());
            roomRecordDAO.insert(recordBO);
            //之前是维修房而且是空房则改为脏房
        } else if ("yes".equals(state)&Constants.VACANT.getValue().equals(majorState)) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", id);
            map.put("state", Constants.DIRTY.getValue());
            roomDAO.updateroomMajorState(map);
            //执行修改
            roomBO.setRoomState("no");
            roomDAO.updateRoomState(roomBO);
            //添加操作日志
            RoomRecordBO recordBO = new RoomRecordBO();
            recordBO.setRoomId(id);
            recordBO.setVirginState(majorState);
            recordBO.setNewState(Constants.DIRTY.getValue());
            recordBO.setRemark("取消维修房");
            recordBO.setCreateUserId(userId);
            recordBO.setCreateTime(new Date());
            roomRecordDAO.insert(recordBO);
            //之前是维修房而且不是空房则改为原状态
        }else if ("yes".equals(state)&(!Constants.VACANT.getValue().equals(majorState))){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", id);
            map.put("state",majorState);
            roomDAO.updateroomMajorState(map);
            //执行修改
            roomBO.setRoomState("no");
            roomDAO.updateRoomState(roomBO);
            //添加操作日志
            RoomRecordBO recordBO = new RoomRecordBO();
            recordBO.setRoomId(id);
            recordBO.setVirginState(majorState);
            recordBO.setNewState(majorState);
            recordBO.setRemark("取消维修房");
            recordBO.setCreateUserId(userId);
            recordBO.setCreateTime(new Date());
            roomRecordDAO.insert(recordBO);
        }
    }

    /*
        修改房间备注
     */
    public void updateRoomRemark(Integer id, String remark, Integer userId) {
        //查询房间备注
        String remark1 = roomDAO.getRoomRemark(id);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("remark", remark);
        roomDAO.updateRoomRemark(map);

        //查询房间主状态
        String majorState = roomDAO.getRoomMajorState(id);
        //添加房间操作记录

        RoomRecordBO recordBO = new RoomRecordBO();
        recordBO.setRoomId(id);
        recordBO.setVirginState(majorState);
        recordBO.setNewState(majorState);
        recordBO.setRemark("修改备注" + "原:" + remark1 + ";" + "现:" + remark);
        recordBO.setCreateUserId(userId);
        recordBO.setCreateTime(new Date());
        roomRecordDAO.insert(recordBO);
    }

    /*
           查询房屋信息
        */
    public RoomMessageBO getRoomMessage(Integer id) {
        return roomDAO.getRoomMessage(id);
    }


    public RoomBO queryRoom(String name, Integer hotelId) {
        return roomDAO.queryRooms(name, hotelId);
    }


    public boolean isDate(Date leftStartDate, Date leftEndDate, Date rightStartDate, Date rightEndDate) {
        /*判断*/
        if (((leftStartDate.getTime() >= rightStartDate.getTime())
                && leftStartDate.getTime() < rightEndDate.getTime())
                || ((leftStartDate.getTime() > rightStartDate.getTime())
                && leftStartDate.getTime() <= rightEndDate.getTime())
                || ((rightStartDate.getTime() >= leftStartDate.getTime())
                && rightStartDate.getTime() < leftEndDate.getTime())
                || ((rightStartDate.getTime() > leftStartDate.getTime())
                && rightStartDate.getTime() <= leftEndDate.getTime())) {
            //存在交集
            return true;
        }
        //不存在交集
        return false;
    }




    /**
     * 共用方法 查询某个时间下酒店所有房型的可用数量
     * @param hotelId
     * @param startTime
     * @param endTime
     * @return
     */
    private List<DateRoomDTO> publicRoomNum(Integer hotelId, String startTime, String endTime){

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("hotelId", hotelId);
        //获取酒店下面所有房型
        List<RtBO> rtBOS = queryRt(hotelId);
        List<String> ll = new ArrayList<String>();
        ll.add("reservation");
        ll.add("notpay");
        ll.add("admissions");

        List<DateRoomDTO> l = new ArrayList<DateRoomDTO>();
        for (RtBO rtBO : rtBOS) {
            map.put("checkTime", startTime);
            map.put("endTime", endTime);
            map.put("roomTypeId", rtBO.getId());

            DateRoomDTO dateRoomDTO = new DateRoomDTO();

            //房型总数
            dateRoomDTO.setSumRoomType(roomDAO.querRoomTypeCount(rtBO.getId(), hotelId));
            //房型名称
            dateRoomDTO.setTypename(rtBO.getRoomTypeName());
            List<RmBO> rmBOS1 = this.publicQuery(map, ll);
            //房型可用房间数
            dateRoomDTO.setUsableRoomNunber(rmBOS1.size());

            l.add(dateRoomDTO);

        }
        return l;
    }





    /**
     * 查询某一时间段下每天剩下多少房
     *
     * @param checkTime
     * @param endTime
     * @param hotrlId
     */
    public Map<String, Object> querySc(String checkTime, String endTime, Integer hotrlId) {
        Map<String, Object> map = new HashMap<String, Object>();
       /* long quot = DateUtils.getQuot(DateUtils.parseDate(checkTime, "yyyy-MM-dd"), DateUtils.parseDate(endTime, "yyyy-MM-dd"));
        log.info("两个日期之间相差的天数:{}",quot);*/
        List<String> dates = querSeTime(DateUtils.parseDate(checkTime, "yyyy-MM-dd"), DateUtils.parseDate(endTime, "yyyy-MM-dd"));
        log.info("两段时间区间的每一天日期:{}", dates);

        String start = checkTime + " 14:00:00";
        String end = checkTime + " 14:00:00";

        log.info("checkTime:{}", checkTime);
        log.info("endTime:{}", endTime);


        map.put("hotelId", hotrlId);


        List<Time> times = timeDate2(DateUtils.parseDate(start, "yyyy-MM-dd HH:mm:ss"),
                DateUtils.parseDate(end, "yyyy-MM-dd HH:mm:ss"),
                dates.size());
        log.info("times:{}", times);
        //获取酒店下面所有房型
        List<RtBO> rtBOS = queryRt(hotrlId);
        log.info("酒店下面所有房型:{}", rtBOS);
        Map<String, Object> mp = new HashMap<String, Object>();

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        List<String> ll = new ArrayList<String>();
        ll.add("reservation");
        ll.add("notpay");
        ll.add("admissions");
        for (RtBO rtBO : rtBOS) {
            Map<String, Object> m = new HashMap<String, Object>();
            m.put("sumCountRoomType", roomDAO.querRoomTypeCount(rtBO.getId(), hotrlId));
            m.put("roomTypeName", rtBO.getRoomTypeName());
            int i = 1;
            for (Time time : times) {

                map.put("checkTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time.getStartTime()));
                map.put("endTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time.getEndTime()));
                map.put("roomTypeId", rtBO.getId());

                List<RmBO> rmBOS1 = this.publicQuery(map, ll);
                m.put("date" + i, rmBOS1.size());
                i++;
            }
            list.add(m);
        }
        mp.put("first", list);
        mp.put("dateNumber", dates.size());
        mp.put("date", dates);
        return mp;
    }

    /**
     * 图标统计格式一
     * @param checkTime
     * @param endTime
     * @param hotrlId
     * @return
     */
    public List<List<DateRoomDTO>> querySs(String checkTime, String endTime, Integer hotrlId) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<String> dates = querSeTime(DateUtils.parseDate(checkTime, "yyyy-MM-dd"), DateUtils.parseDate(endTime, "yyyy-MM-dd"));
        log.info("两段时间区间的每一天日期:{}", dates);

        String start = checkTime + " 14:00:00";
        String end = checkTime + " 14:00:00";

        log.info("checkTime:{}", checkTime);
        log.info("endTime:{}", endTime);


        map.put("hotelId", hotrlId);


        List<Time> times = timeDate2(DateUtils.parseDate(start, "yyyy-MM-dd HH:mm:ss"),
                DateUtils.parseDate(end, "yyyy-MM-dd HH:mm:ss"),
                dates.size());
        log.info("times:{}", times);
        //获取酒店下面所有房型
        List<RtBO> rtBOS = queryRt(hotrlId);
        log.info("酒店下面所有房型:{}", rtBOS);
        List<List<DateRoomDTO>> list = new ArrayList<List<DateRoomDTO>>();


        List<String> ll = new ArrayList<String>();
        ll.add("reservation");
        ll.add("notpay");
        ll.add("admissions");
        int i = 0;
        for (Time time : times){
            List<DateRoomDTO> l = new ArrayList<DateRoomDTO>();

            for (RtBO rtBO : rtBOS) {
                map.put("checkTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time.getStartTime()));
                map.put("endTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time.getEndTime()));
                map.put("roomTypeId", rtBO.getId());
                log.info("开始时间:{}",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time.getStartTime()));
                log.info("结束时间:{}",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time.getEndTime()));
                DateRoomDTO dateRoomDTO = new DateRoomDTO();
                dateRoomDTO.setSumRoomType(roomDAO.querRoomTypeCount(rtBO.getId(), hotrlId));

                dateRoomDTO.setTypename(rtBO.getRoomTypeName());
                log.info("房型名称:{}",rtBO.getRoomTypeName());
                log.info("房型id:{}",rtBO.getId());
                log.info("酒店id:{}",hotrlId);
                log.info("房型总房间数:{}",roomDAO.querRoomTypeCount(rtBO.getId(), hotrlId));
                List<RmBO> rmBOS1 = this.publicQuery(map, ll);
                log.info("最终结果:{}",rmBOS1);
                dateRoomDTO.setUsableRoomNunber(rmBOS1.size());
                log.info("长度:{}",rmBOS1.size());
                dateRoomDTO.setDate(dates.get(i));
                log.info("dates.get(i):{}",dates.get(i));
                l.add(dateRoomDTO);

            }
            i++;
            list.add(l);

        }
        return list;

    }


    public List<String> querSeTime(Date bigtime, Date endtime) {
        //定义一个接受时间的集合
        List<Date> lDate = new ArrayList<Date>();
        lDate.add(bigtime);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(bigtime);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(endtime);
        // 测试此日期是否在指定日期之后
        while (endtime.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(calBegin.getTime());
        }
        List<String> ls = new ArrayList<String>();
        for (Date d : lDate) {
            ls.add(new SimpleDateFormat("yyyy-MM-dd").format(d));
        }

        return ls;
    }

    public void closeRoom(String startTime, String endTime, List<Integer> list, String remark) {
        log.info("startTime:{}",startTime);
        log.info("endTime:{}",endTime);
        Date date = DateUtils.parseDate(startTime, "yyyy/MM/dd HH:mm:ss");
        log.info("date:{}",date);
        Date date1 = DateUtils.parseDate(endTime, "yyyy/MM/dd HH:mm:ss");
        log.info("date1:{}",date1);

        boolean b = belongCalendar(new Date(),date ,date1);

        if(b){
            roomDAO.closeRoom(startTime, endTime, list, remark, "yes");
        } else {
            roomDAO.closeRoom(startTime, endTime, list, remark, null);
        }

    }

    public void opeRoom(List<Integer> list, String remark) {
        roomDAO.opeRoom(list, remark);
    }

    public Map<String, Object> verificationRoom(List<Integer> list, String state, String checkTime, String endTime, Integer hotelId) {
        Map<String, Object> map = new HashMap<String, Object>();
        if ("day".equals(state)) {
           /* map.put("roomAuxiliaryStatus", "yes");
            map.put("roomAuxiliaryStatusStand", "yes");*/
        }
        if ("hour".equals(state)) {
            map.put("roomAuxiliaryStatus", "yes");
            map.put("roomAuxiliaryStatusStand", "no");
        }
        if ("free".equals(state)) {
            map.put("roomAuxiliaryStatus", "no");
            map.put("roomAuxiliaryStatusStand", "yes");
        }

        map.put("list", list);
        map.put("checkTime", checkTime);
        map.put("endTime", endTime);
        map.put("hotelId", hotelId);


        List<String> ll = new ArrayList<String>();
        ll.add("reservation");
        ll.add("notpay");
        ll.add("admissions");
        List<RmBO> rmBOS = publicQuery(map, ll);
        List<Integer> ls = new ArrayList<Integer>();
        for (RmBO rmBO : rmBOS) {
            ls.add(rmBO.getId());
        }
        log.info("ls:{}", ls);
        log.info("rmBOS:{}", rmBOS);

        Map<String, Object> mp = new HashMap<String, Object>();
        if (!CollectionUtils.isEmpty(ls)) {
            boolean listEqual = isListEqual(list, ls);
            log.info("listEqual:{}", listEqual);
            if (listEqual) {
                mp.put("state", true);
                mp.put("msg", "可直接入住或预定");
                return mp;
            } else {
                List<Integer> diffrent2 = getDiffrent2(list, ls);
                log.info("不能直接入住或者预定的:{}", diffrent2);
                List<String> strings = roomDAO.queryRoomName(diffrent2);
                log.info("strings:{}", strings);
                StringBuffer sb = new StringBuffer();
                sb.append("以下房间已更改了状态:");
                for (String string : strings) {
                    log.info("string:{}", string);
                    sb.append(string + " ");
                }
                sb.append(" 请重新选择");
                log.info("sb:{}", sb);
                mp.put("state", false);
                mp.put("msg", sb.toString());
                return mp;
            }
        } else {
            List<String> strings = roomDAO.queryRoomName(list);
            StringBuffer sb = new StringBuffer();
            sb.append("以下房间已更改了状态:");
            for (String string : strings) {
                log.info("string:{}", string);
                sb.append(string + " ");
            }
            sb.append(" 请重新选择");
            log.info("sb:{}", sb);
            log.info("请重新选择房间");
            mp.put("state", false);
            mp.put("msg", sb.toString());
            return mp;
        }
    }

    public static <E> boolean isListEqual(List<E> list1, List<E> list2) {
        // 两个list引用相同（包括两者都为空指针的情况）
        if (list1 == list2) {
            return true;
        }

        // 两个list都为空（包括空指针、元素个数为0）
        if ((list1 == null && list2 != null && list2.size() == 0)
                || (list2 == null && list1 != null && list1.size() == 0)) {
            return true;
        }

        // 两个list元素个数不相同
        if (list1.size() != list2.size()) {
            return false;
        }

        // 两个list元素个数已经相同，再比较两者内容
        // 采用这种可以忽略list中的元素的顺序
        // 涉及到对象的比较是否相同时，确保实现了equals()方法
        if (!list1.containsAll(list2)) {
            return false;
        }

        return true;
    }

    private static List<Integer> getDiffrent2(List<Integer> list1, List<Integer> list2) {
        long start = System.currentTimeMillis();
        list1.removeAll(list2);// 返回值是boolean
        System.out.println("方法2 耗时：" + (System.currentTimeMillis() - start) + " 毫秒");
        return list1;
    }

    //锁房时间到了 把锁房状态修改为未锁房
    public Integer updRoom() {
        roomDAO.updRoom2();
        return roomDAO.updRoom();
    }


    public Map<String, Object> todayPictureView(Integer hotelId) {
        //查询当天各房型 可用数量 入住中数量 预约中数量 以及计算出入住率
        HashMap<String, Object> map = new HashMap<String, Object>();

        //获取当前时间
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        log.info("format:{}",format);
        String date = format + " 06:00:00" ;

        //获取明天早上六点的时间
        Date date1 = lDate(quDate(6, 0, 0), 1);


        //查询今天房型可用数量
        //查询中当前酒店有多少房型
        List<RtBO> rtBOS = roomDAO.queryRt(hotelId);
        List<RoomTypeCountDTO> list = new ArrayList<RoomTypeCountDTO>();

        List<String> ll = new ArrayList<String>();
        ll.add("reservation");
        ll.add("notpay");
        ll.add("admissions");

        Map<String, Object> mp = new HashMap<String, Object>();

        mp.put("checkTime", date);
        mp.put("hotelId", hotelId);
        mp.put("endTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date1));



        //获取今天统计房价
        Integer integer1 = roomDAO.queryEverydayRoomPrice(format);
        log.info("integer1:{}",integer1);
        map.put("three", integer1);
        for (RtBO rtBO : rtBOS) {
            mp.put("roomTypeId", rtBO.getId());
            RoomTypeCountDTO roomTypeCountDTO = new RoomTypeCountDTO();
            //查询可用房间
            List<RmBO> rmBOS = publicQuery(mp, ll);
            roomTypeCountDTO.setName(rtBO.getRoomTypeName());
            roomTypeCountDTO.setCount(rmBOS.size());
            //查询当前正在入住的
            List<RmBO> rmBOS1 = roomDAO.queryInthe(rtBO.getId(), hotelId, "inthe", "timeout");
            roomTypeCountDTO.setCountChinkRoom(rmBOS1.size());
            //获取预约中的
            List<OrderChildBO> orderChildBOS = roomDAO.querySubscribe(rtBO.getId(), hotelId, date, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date1));
            roomTypeCountDTO.setCountOrderRoom(orderChildBOS.size());
            int integer = roomDAO.querRoomTypeCount(rtBO.getId(), hotelId);
            roomTypeCountDTO.setRotio(baifenbi(rmBOS1.size(), integer));
            roomTypeCountDTO.setHotelId(rtBO.getHotelId());
            roomTypeCountDTO.setTypePrice(rtBO.getBasicPrice());
            list.add(roomTypeCountDTO);
        }


        List<XxDTO> ls = new ArrayList<XxDTO>();
        //查询全部会员级别
        List<MemberLevelBO> memberLevelBOS = roomDAO.queryMemberLevel();
        //查询当前在住的订单
        List<OrderBO> orderBOS = roomDAO.queryOrder(hotelId, date);
        int j = 0;
        for (MemberLevelBO memberLevelBO : memberLevelBOS) {
            XxDTO xxDTO = new XxDTO();
            xxDTO.setName(memberLevelBO.getName());
            int i = 0;
            for (OrderBO orderBO : orderBOS) {
                if (memberLevelBO.getId().equals(orderBO.getMembersId())) {
                    i++;
                }
            }
            xxDTO.setNumber(i);
            ls.add(xxDTO);
            j = j + i;
        }
        XxDTO xx = new XxDTO();
        xx.setNumber(orderBOS.size() - j);
        xx.setName("散客");
        ls.add(xx);


        map.put("second", ls);
        map.put("first", list);
        return map;
    }

    private String baifenbi(int a, int b) {
        log.info("a:{}", a);
        log.info("b:{}", b);
        if (a == 0 && b == 0) {
            return "0%";
        }

        // 创建一个数值格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(2);
        String result = numberFormat.format((float) a / (float) b * 100);
        System.out.println("diliverNum和queryMailNum的百分比为:" + result + "%");
        return result + "%";
    }

    //获取房间信息
    public RoomBO getRoomBo(Integer id) {
        return roomDAO.getRoomBo(id);
    }

    /**
     * 获取预定反显房间
     *
     * @param orderId
     */
    public List<RmBO> queryRoomFx(Integer orderId) {
        return roomDAO.queryRoomFx(orderId);
    }


    /**
     * 查询出用户之前预约过的房间
     *
     * @param
     * @return
     */
    public List<RmBO> queryUserRoom(Integer hotelId, String phone) {
        Map<String, Object> mp = new HashMap<String, Object>();
        mp.put("hotelId", hotelId);
        mp.put("phone", phone);
        List<Integer> integers = roomDAO.queryUserRoom(mp);
        List<RmBO> list = new ArrayList<RmBO>();
        if (!CollectionUtils.isEmpty(integers)) {
            mp.put("list", integers);

            list = roomDAO.queryUserRoom2(mp);

        }


        MemberDiscountBO memberDiscountBO = queryMember(phone);
        if (memberDiscountBO == null) {
            //不是会员
            // mp.put("discount", false);
        } else {
            //是会员
            //获取折扣价格
            //mp.put("discount", true);
            Double discount = memberDiscountBO.getDiscount();
            if (!CollectionUtils.isEmpty(list)) {
                for (RmBO rmBO : list) {
                    rmBO.setBasicPrice(rmBO.getBasicPrice() * discount);
                    rmBO.setHourRoomPrice(rmBO.getHourRoomPrice() * discount);
                }
            }
        }
        return list;
    }

    public RmBO selectRoom(Integer id, Integer hotelId) {
        log.info("id:{}",id);
        log.info("hotelId:{}", hotelId);
        return roomDAO.selectRoomId(id, hotelId);
    }

    public List<Integer> queryRoomTypeAndId(Integer hotelId, String phone){
        return roomDAO.queryRoomTypeAndId(hotelId, phone);
    }


}




