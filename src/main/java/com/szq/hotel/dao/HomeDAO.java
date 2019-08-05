package com.szq.hotel.dao;


import com.szq.hotel.entity.bo.FloorRoomBO;
import com.szq.hotel.entity.bo.HomeOrderBO;
import com.szq.hotel.entity.bo.HomeRoomTypeBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 首页
 */
public interface HomeDAO {


    /**
     * 查询首页
     */
     List<FloorRoomBO> home(Map<String,Object> map);

    /**
     * 查询是否有预约
     */
    Integer querySubStatus(Map<String,Object> map);

    /**
     * 查询入住信息
     */
    HomeOrderBO queryChildOrder(Map<String,Object> map);

    /**
     * 查询房型数量
     */
    List<HomeRoomTypeBO> queryRoomTypeNum(Integer hotelId);

    /**
     * 查询主状态数量
     */
    int queryMainStausCount(@Param("hotelId")Integer hotelId,@Param("state")String state);

    /**
     * 查询维修房数量
     */
    int queryMaintainCount(@Param("hotelId")Integer hotelId);

    /**
     * 查询锁房数量
     */
    int queryLockCount(@Param("hotelId")Integer hotelId);

    /**
     * 查询预约中数量
     * @param map
     * @return
     */
    int querySubCount(Map<String,Object> map);

    /**
     * 查询预离店数量
     */
    int queryCheOutCount(Map<String,Object> map);

}