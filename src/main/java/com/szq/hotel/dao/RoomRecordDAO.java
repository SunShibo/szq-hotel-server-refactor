package com.szq.hotel.dao;

import com.szq.hotel.entity.bo.RoomRecordBO;

public interface RoomRecordDAO {
    int deleteByPrimaryKey(Integer id);

    int insert(RoomRecordBO record);

    int insertSelective(RoomRecordBO record);

    RoomRecordBO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RoomRecordBO record);

    int updateByPrimaryKey(RoomRecordBO record);
}