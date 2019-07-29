package com.szq.hotel.dao;

import com.szq.hotel.entity.bo.*;
import com.szq.hotel.entity.dto.OcDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestPart;

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
                       @Param("endTime")String endTime,
                       @Param("list")List<String> list);


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
     * @param
     * @return
     */
    RoomBO queryRooms(@Param("name")String name, @Param("hotelId")Integer hotelId);

    void closeRoom(@Param("startTime")String startTime, @Param("endTime")String endTime,
                   @Param("list")List<Integer> list,@Param("remark")String remark);

    void opeRoom( @Param("list")List<Integer> list,@Param("remark")String remark);

    /**
     * 查询酒店下各种房型房间数量
     * @param roomTypeId
     * @param hotelId
     * @return
     */
    Integer querRoomTypeCount(@Param("roomTypeId")Integer roomTypeId, @Param("hotelId")Integer hotelId);


    List<String> queryRoomName(@Param("list")List<Integer> list);

    //锁房时间到了 把锁房状态修改为未锁房
    Integer updRoom();

    //锁房时间到了 把锁房状态修改为锁房
    Integer updRoom2();

    List<RmBO> queryInthe(@Param("roomTypeId")Integer roomTypeId,
                          @Param("hotelId")Integer hotelId,
                          @Param("inthe")String inthe,
                          @Param("timeout")String timeout);

    List<OrderChildBO> querySubscribe(@Param("roomTypeId")Integer roomTypeId,@Param("hotelId")Integer hotelId,@Param("checkTime")String checkTime, @Param("endTime")String endTime);


    /**
     * 预约订单反显
     * @param orderId
     */
    List<RmBO> queryRoomFx(Integer orderId);

    RoomBO getRoomBo(Integer id);

    /**
     * 计算当天未过夜审的房价总和
     * @param date
     * @return
     */
    Integer queryEverydayRoomPrice(@Param("date")String date);

    /**
     * 查询所有会员级别
     * @return
     */
    List<MemberLevelBO> queryMemberLevel();

    /**
     * 查询当前在住的订单
     * @param hotelId
     * @param date
     * @return
     */
    List<OrderBO>  queryOrder(@Param("hotelId")Integer hotelId,@Param("date")String date);

    /**
     * 获取用户预约过的房间id
     * @param map
     * @return
     */
    List<Integer> queryUserRoom(Map<String, Object> map);

    /**
     * 根据房间id获取房间信息
     * @param map
     * @return
     */
    List<RmBO> queryUserRoom2(Map<String, Object> map);

    RmBO selectRoomId(@Param("id") Integer id, @Param("hotelId") Integer hotelId);
}