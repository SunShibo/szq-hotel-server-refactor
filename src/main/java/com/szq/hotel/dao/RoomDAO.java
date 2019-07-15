package com.szq.hotel.dao;

import com.szq.hotel.entity.bo.*;
import com.szq.hotel.entity.dto.OcDTO;
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
    List<OcBO> queryOc(@Param("id") List<Integer> id,
                       @Param("checkTime")String checkTime,
                       @Param("endTime")String endTime);


    /**
     * 查询酒店下面的房型
     * @param hotelId
     * @return
     */
    List<RtBO> queryRt(@Param("hotelId")Integer hotelId);


    /**
     * 查询酒店下面的楼层
     * @param hotelId
     * @return
     */
    List<FlrBO> queryFlr(@Param("hotelId")Integer hotelId);
    /*
        修改房间是否为维修房
     */
    void updateRoomState(RoomBO roomBO);
    /*
        查询房间维修状态
     */
    String getRoomState(Integer id);

    /*
        查询房间主状态
     */
    String getRoomMajorState(Integer id);
    /*
        修改房间备注
     */
    void updateRoomRemark(Map<String, Object> map);
    /*
        查询房间备注
     */
    String getRoomRemark(Integer id);
/*
    查询房屋信息
 */
  RoomMessageBO getRoomMessage(Integer id);

    /**
     * 查询未来  15天是否有人预约该房间
     * @param roomId
     * @return
     */
    List<OcDTO> queryTc(@Param("roomId")Integer roomId);

    MemberDiscountBO  queryMemberByPhone(@Param("phone")String phone);

    /**
     * 判断同一酒店下是否有相同名称的客房
     * @param name
     * @param id
     * @return
     */
    RoomBO queryRoom(@Param("nanme")String name, @Param("hotelId")Integer hotelId);

}