package com.szq.hotel.service;

import com.szq.hotel.common.constants.Constants;
import com.szq.hotel.dao.RoomDAO;
import com.szq.hotel.dao.RoomRecordDAO;
import com.szq.hotel.entity.bo.*;
import com.szq.hotel.util.DateUtils;
import com.szq.hotel.web.controller.RoomController;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.velocity.runtime.log.LogChute;
import org.omg.CORBA.OBJ_ADAPTER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
    public RoomBO selectByPrimaryKey(Integer id){
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
     * @param map
     * @return
     */
    public List<RmBO> publicQuery(Map<String, Object> map){

        //获取预入住时间
        String dt =  (String) map.get("checkTime");
        log.info("获取预约入住的时间:{}",dt);
        String et =  (String) map.get("endTime");
        log.info("获取结束入住的时间:{}",et);
        //获取符合条件的房间集合
        List<RmBO> list = roomDAO.queryRm(map);

        log.info("获取符合条件的房间集合:{}",list);

        List<Integer> ls = new ArrayList<Integer>();
        //获取出房间的id
        if (!CollectionUtils.isEmpty(list)) {
            for (RmBO id : list) {
                ls.add(id.getId());
            }
        }
        log.info("获取符合条件房间的id:{}",ls);
        //根据房间id获取符合条件的订单
        List<OcBO> l = roomDAO.queryOc(ls,dt,et);
        List<Integer> reId = new ArrayList<Integer>();
        for (OcBO oc : l){
            log.info("获取符合条件房间的订单:{}",oc.getRoomId());
            reId.add(oc.getRoomId());
        }


        log.info("判断客人选择的预入住时间是否在其他符合条件订单之间的id:{}",reId);
        //去重
        for (int i = 0; i < reId.size() - 1; i++) {
            for (int j = reId.size() - 1; j > i; j--) {
                if (reId.get(j).equals(reId.get(i))) {
                    reId.remove(j);
                }
            }
        }
        log.info("去重后:{}",reId);

        //去掉不能预约入住的房间的房间
        Iterator<RmBO> iterator = list.iterator();
        if(!CollectionUtils.isEmpty(reId)){
            while (iterator.hasNext()) {
                RmBO rmBO = iterator.next();
                for (Integer id : reId){
                    if(rmBO.getId().equals(id)){
                        iterator.remove();//使用迭代器的删除方法删除
                    }
                }
            }
        }

        log.info("去掉不能预约入住的房间的房间:{}",list);
        return list;
    }

    /**
     * 预约入住选择房间
     */
    public Map<String, Object> queryRm(Map<String, Object> map)  {
        Map<String, Object> mp = new HashMap<String, Object>();
        
        //获取酒店下面所有楼层
         Integer hotelId =   (Integer)map.get("hotelId");
         log.info("hotelId:{}",hotelId);
        List<FlrBO> flrList  = roomDAO.queryFlr(hotelId);
        log.info("酒店下共有楼层:{}",flrList);
        
        List<RmBO> list =  this.publicQuery(map);
        
        if(!CollectionUtils.isEmpty(flrList) && !CollectionUtils.isEmpty(list)){
            for (FlrBO f : flrList){
                List<RmBO> a = new ArrayList<RmBO>();
                for (RmBO rmBO : list){
                   if(rmBO.getFloorId().equals(f.getId())){
                       a.add(rmBO);
                   }
                }
                mp.put(f.getName()+"",a);
            }
        }

       // mp.put("list",list);

        return  mp;
    }

    public Map<String, Object>  queryRoomTypeNum(Map<String, Object> map){
        Map<String,Object> mp = new HashMap<String, Object>();
        List<RmBO> list =  this.publicQuery(map);
        Integer hotelId =   (Integer)map.get("hotelId");
        log.info("list:{}",list);

        List<RtBO> rtBOS = roomDAO.queryRt(hotelId);

        if(!CollectionUtils.isEmpty(rtBOS) && !CollectionUtils.isEmpty(list)){
            for (RtBO rtBO : rtBOS){
                Integer i = 0;
               for (RmBO rmBO : list){
                 if(rtBO.getId().equals(rmBO.getRoomTypeId())){
                     i++;
                 }
               }
               mp.put( rtBO.getRoomTypeName()+"("+i+")",rtBO.getId());
            }
        }
        return mp;
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
     * @param hotelId
     * @return
     */
    public List<RtBO> queryRt(Integer hotelId){
        return  roomDAO.queryRt(hotelId);
    }

    /*
        修改房间是否为维修房
     */
    public void updateRoomState(Integer id,Integer userId){
        //查询房间主状态
        String majorState = roomDAO.getRoomMajorState(id);
        //查询房间维修状态
        String state = roomDAO.getRoomState(id);
        RoomBO roomBO = new RoomBO();
        roomBO.setId(id);
        //之前不是维修房改为维修房
        if ("no".equals(state)){
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
        }else if ("yes".equals(state)){
            Map<String,Object> map =  new HashMap<String, Object>();
            map.put("id",id);
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
    public void updateRoomRemark(Integer id,String remark,Integer userId){
        //查询房间备注
        String remark1 = roomDAO.getRoomRemark(id);
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("id",id);
        map.put("remark",remark);
        roomDAO.updateRoomRemark(map);

        //查询房间主状态
        String majorState = roomDAO.getRoomMajorState(id);
        //添加房间操作记录

        RoomRecordBO recordBO = new RoomRecordBO();
        recordBO.setRoomId(id);
        recordBO.setVirginState(majorState);
        recordBO.setNewState(majorState);
        recordBO.setRemark("修改备注"+"原:"+remark1+";"+"现:"+remark);
        recordBO.setCreateUserId(userId);
        recordBO.setCreateTime(new Date());
        roomRecordDAO.insert(recordBO);
    }
}
