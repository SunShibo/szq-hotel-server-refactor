package com.szq.hotel.dao;

import com.szq.hotel.entity.bo.OcBO;
import com.szq.hotel.entity.bo.RmBO;
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

    void updatelockRoomState(Map<String,Object> map);

    void updatelockRoomState2(Map<String,Object> map);

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

    /**
     * 预定入住选房型
     * @param map
     * @return
     */
    List<RmBO> queryRm(Map<String, Object> map);

    /**
     * 根据房间id查询订单
     * @param id
     * @return
     */
    List<OcBO> queryOc(@Param("id") List<Integer> id);




}