package com.szq.hotel.dao;

import com.szq.hotel.entity.bo.RoomRecordBO;

import java.util.List;
import java.util.Map;

public interface RoomRecordDAO {
    int deleteByPrimaryKey(Integer id);

    int insert(RoomRecordBO record);

    int insertSelective(RoomRecordBO record);

    RoomRecordBO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RoomRecordBO record);

    int updateByPrimaryKey(RoomRecordBO record);

    List<RoomRecordBO> selectRoomRecord(Map<String,Object> map);

    Integer selectRoomRecordCount(Integer id);


    int insertRoomState(Map<String, Object> map);
}