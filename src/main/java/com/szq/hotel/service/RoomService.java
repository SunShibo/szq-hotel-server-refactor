package com.szq.hotel.service;

import com.szq.hotel.dao.RoomDAO;
import com.szq.hotel.dao.RoomRecordDAO;
import com.szq.hotel.entity.bo.*;
import com.szq.hotel.util.DateUtils;
import com.szq.hotel.web.controller.RoomController;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.velocity.runtime.log.LogChute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
     * 预约入住选择房间
     */
    public Map<String, Object> queryRm(Map<String, Object> map) {
        Map<String, Object> mp = new HashMap<String, Object>();

        //获取预入住时间
        String dt =  (String) map.get("checkTime");
        log.info("获取预约入住的时间:{}",dt);
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
        List<OcBO> l = roomDAO.queryOc(ls);
        log.info("获取符合条件房间的订单:{}",l);


        List<Integer> reId = new ArrayList<Integer>();
        if (!CollectionUtils.isEmpty(l)) {
            for (OcBO c : l) {
                //判断客人选择的预入住时间是否在其他符合条件订单之间
                if (belongCalendar(DateUtils.parseDate(dt,"yyyy-MM-dd HH:mm::ss"), c.getStartTime(), c.getEndTime())) {
                    reId.add(c.getRoomId());
                }
            }
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
        while (iterator.hasNext()) {
            RmBO rmBO = iterator.next();
            for (Integer id : reId){
               rmBO.getId().equals(id);
                iterator.remove();//使用迭代器的删除方法删除
            }
        }
        log.info("去掉不能预约入住的房间的房间:{}",list);
        map.put("list",list);
        return  mp;
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
}
