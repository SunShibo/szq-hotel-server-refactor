package com.szq.hotel.service;

import com.szq.hotel.common.constants.Constants;
import com.szq.hotel.dao.RoomDAO;
import com.szq.hotel.dao.RoomRecordDAO;
import com.szq.hotel.entity.bo.*;
import com.szq.hotel.entity.dto.OcDTO;
import com.szq.hotel.entity.dto.RoomStateDTO;
import com.szq.hotel.util.DateUtils;
import com.szq.hotel.web.controller.RoomController;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.velocity.runtime.log.LogChute;
import org.omg.CORBA.OBJ_ADAPTER;
import org.redisson.core.RList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.awt.geom.AreaOp;

import javax.annotation.Resource;
import javax.xml.transform.dom.DOMResult;
import java.text.ParseException;
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
        Map<String, Object> mp = new HashMap<String, Object>();
        List<RoomBO> list = roomDAO.queryRoom(map);
        Integer count = roomDAO.queryRoomCount(map);
        mp.put("list", list);
        mp.put("count", count);
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

        roomDAO.updateroomMajorState(map);
        RoomBO roomBO = roomDAO.selectByPrimaryKey((Integer) map.get("id"));
        RoomRecordBO roomRecordBO = new RoomRecordBO();
        roomRecordBO.setCreateTime(new Date());
        roomRecordBO.setNewState((String) map.get("state"));
        roomRecordBO.setVirginState(roomBO.getRoomState());
        roomRecordBO.setRoomId(roomBO.getId());
        roomRecordDAO.insertSelective(roomRecordBO);
    }

    /**
     * 抽取公共方法
     *
     * @param map
     * @return
     */
    public List<RmBO> publicQuery(Map<String, Object> map) {

        //获取预入住时间
        String dt = (String) map.get("checkTime");
        log.info("获取预约入住的时间:{}", dt);
        String et = (String) map.get("endTime");
        log.info("获取结束入住的时间:{}", et);
        //获取符合条件的房间集合
        List<RmBO> list = roomDAO.queryRm(map);

        log.info("获取符合条件的房间集合:{}", list);

        List<Integer> ls = new ArrayList<Integer>();
        //获取出房间的id
        if (!CollectionUtils.isEmpty(list)) {
            for (RmBO id : list) {
                ls.add(id.getId());
            }
        }
        log.info("获取符合条件房间的id:{}", ls);
        List<OcBO> l = null;
        //根据房间id获取符合条件的订单
        if (!CollectionUtils.isEmpty(ls)) {
            l = roomDAO.queryOc(ls, dt, et);
        }

        List<Integer> reId = new ArrayList<Integer>();
        if (!CollectionUtils.isEmpty(l)) {
            for (OcBO oc : l) {
                log.info("获取符合条件房间的订单:{}", oc.getRoomId());
                reId.add(oc.getRoomId());
            }
        }

        log.info("要扣减调的房间id:{}", reId);
        //去重
        for (int i = 0; i < reId.size() - 1; i++) {
            for (int j = reId.size() - 1; j > i; j--) {
                if (reId.get(j).equals(reId.get(i))) {
                    reId.remove(j);
                }
            }
        }
        log.info("去重后:{}", reId);

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

        log.info("最终筛选后可以入住的房间是:{}", list);
        return list;
    }


    /**
     * 预约入住选择房间
     */
    public List<List<RmBO>> queryRm(Map<String, Object> map) {
        //Map<String, Object> mp = new LinkedHashMap<String, Object>();
        List<List<RmBO>> ls = new ArrayList<List<RmBO>>();
        //获取酒店下面所有楼层
        Integer hotelId = (Integer) map.get("hotelId");
        log.info("hotelId:{}", hotelId);
        List<FlrBO> flrList = roomDAO.queryFlr(hotelId);
        log.info("酒店下共有楼层:{}", flrList);

        List<RmBO> list = this.publicQuery(map);

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
                    rmBO.setBasicPrice(rmBO.getBasicPrice() * discount);
                    rmBO.setHourRoomPrice(rmBO.getHourRoomPrice() * discount);
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
        List<RmBO> list = this.publicQuery(map);
        Integer hotelId = (Integer) map.get("hotelId");
        String phone = (String) map.get("phone");
        log.info("phone:{}", phone);
        log.info("list:{}", list);

        List<RoomTypeNumBO> ls = new ArrayList<RoomTypeNumBO>();


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
                    roomTypeBO.setBasicPrice(rtBO.getBasicPrice() * memberDiscountBO.getDiscount());
                    roomTypeBO.setHourRoomPrice(rtBO.getHourRoomPrice() * memberDiscountBO.getDiscount());
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


        return ls;
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
        //从开始天使的下一天结束时间
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
            recordBO.setNewState(roomBO.getRoomState());
            recordBO.setRemark("改为维修房");
            recordBO.setCreateUserId(userId);
            recordBO.setCreateTime(new Date());
            roomRecordDAO.insert(recordBO);
            //之前是维修房
        } else if ("yes".equals(state)) {
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


    private boolean isDate(Date leftStartDate, Date leftEndDate, Date rightStartDate, Date rightEndDate) {
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
        String end = checkTime + " 06:00:00";

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

        for (RtBO rtBO : rtBOS) {
            Map<String, Object> m = new HashMap<String, Object>();
            m.put("sumCountRoomType", roomDAO.querRoomTypeCount(rtBO.getId(), hotrlId));
            m.put("roomTypeName", rtBO.getRoomTypeName());
            int i = 1;
            for (Time time : times) {

                map.put("checkTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time.getStartTime()));
                map.put("endTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time.getEndTime()));
                map.put("roomTypeId", rtBO.getId());

                List<RmBO> rmBOS1 = this.publicQuery(map);
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
        roomDAO.closeRoom(startTime, endTime, list, remark);
    }

    public void opeRoom(List<Integer> list, String remark) {
        roomDAO.opeRoom(list,remark);
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
        List<RmBO> rmBOS = publicQuery(map);
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
                mp.put("state",true);
                mp.put("msg","可直接入住或预定");
                return mp;
            } else {
                List<Integer> diffrent2 = getDiffrent2(list, ls);
                log.info("不能直接入住或者预定的:{}",diffrent2);
                List<String> strings = roomDAO.queryRoomName(diffrent2);
                log.info("strings:{}",strings);
                StringBuffer sb = new StringBuffer();
                sb.append("以下房间已更改了状态:");
                for (String string : strings) {
                    log.info("string:{}",string);
                    sb.append(string+" ");
                }
                sb.append(" 请重新选择");
                log.info("sb:{}",sb);
                mp.put("state",false);
                mp.put("msg",sb.toString());
                return mp;
            }
        } else {
            List<String> strings = roomDAO.queryRoomName(list);
            StringBuffer sb = new StringBuffer();
            sb.append("以下房间已更改了状态:");
            for (String string : strings) {
                log.info("string:{}",string);
                sb.append(string+" ");
            }
            sb.append(" 请重新选择");
            log.info("sb:{}",sb);
            log.info("请重新选择房间");
            mp.put("state",false);
            mp.put("msg",sb.toString());
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

}




