package com.szq.hotel.dao;

import com.szq.hotel.entity.bo.RoomTypeBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoomTypeDAO {
    int deleteByPrimaryKey(Integer id);

    int insert(RoomTypeBO record);

    int insertSelective(RoomTypeBO record);

    RoomTypeBO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RoomTypeBO record);

    int updateByPrimaryKey(RoomTypeBO record);

    List<RoomTypeBO> queryRoomTypeList(@Param("id")Integer id);
    /*
        查询所有房型
    */
    List<Integer> getRoomTypeList();
    void updateShow(@Param("id") Integer id);
}