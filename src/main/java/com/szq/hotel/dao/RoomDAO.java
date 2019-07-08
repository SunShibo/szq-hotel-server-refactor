package com.szq.hotel.dao;

import com.szq.hotel.entity.bo.RoomBO;
import com.szq.hotel.entity.bo.RoomTypeCountBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RoomDAO {
    int deleteByPrimaryKey(Integer id);

    int insert(RoomBO record);

    int insertSelective(RoomBO record);

    RoomBO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RoomBO record);

    int updateByPrimaryKey(RoomBO record);

    List<RoomBO> queryRoom(Map<String,Object> map);

    Integer queryRoomCount(Map<String,Object> map);

    void updateShow(Integer[] idArr);

    void updatelockRoomState(Integer[] idArr);

    void updatelockRoom(Integer[] idArr);

    /**
     * 查询当前酒店下的房型数量 id 名称
     * @param id
     * @return
     */
    List<RoomTypeCountBO> queryRoomTypeCount(@Param("id")Integer id);

    /**
     * 修改房间状态
     * @param map
     */
    void updateroomMajorState(Map<String, Object> map);
}