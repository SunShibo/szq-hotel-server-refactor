package com.szq.hotel.service;

import com.szq.hotel.dao.RoomDAO;
import com.szq.hotel.dao.RoomRecordDAO;
import com.szq.hotel.entity.bo.RoomBO;
import com.szq.hotel.entity.bo.RoomRecordBO;
import com.szq.hotel.entity.bo.RoomTypeCountBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Bin Wang
 * @date: Created in 13:00 2019/7/5
 */
@Service
@Transactional
public class RoomService {

    @Resource
    private RoomDAO roomDAO;
    @Resource
    private RoomRecordDAO roomRecordDAO;

    public Map<String, Object> queryRoom(Map<String, Object> map){
        Map<String, Object> mp = new HashMap<String, Object>();
        List<RoomBO> list = roomDAO.queryRoom(map);
        Integer count = roomDAO.queryRoomCount(map);
        mp.put("list",list);
        mp.put("count",count);
        return mp;
    }

    public int insertSelective(RoomBO record){
        return roomDAO.insertSelective(record);
    }

    public int updateByPrimaryKeySelective(RoomBO record){
        return roomDAO.updateByPrimaryKeySelective(record);
    }

    public void deleteByPrimaryKey(Integer[] id){
         roomDAO.updateShow(id);
    }

    public void updatelockRoomClose(Integer[] idArr){
        roomDAO.updatelockRoomState(idArr);
    }

    public void updatelockRoomOpen(Integer[] idArr){
        roomDAO.updatelockRoomState(idArr);
    }

    public List<RoomTypeCountBO> queryRoomTypeCount(Integer id){
        return roomDAO.queryRoomTypeCount(id);
    }

    public void updateroomMajorState( Map<String, Object> map){
        roomDAO.updateroomMajorState(map);
        RoomBO roomBO = roomDAO.selectByPrimaryKey((Integer)map.get("id"));
        RoomRecordBO roomRecordBO = new RoomRecordBO();
        roomRecordBO.setCreateTime(new Date());
        roomRecordBO.setNewState((String)map.get("state"));
        roomRecordBO.setVirginState(roomBO.getRoomState());
        roomRecordBO.setRoomId(roomBO.getId());
        roomRecordDAO.insertSelective(roomRecordBO);

    }

}
