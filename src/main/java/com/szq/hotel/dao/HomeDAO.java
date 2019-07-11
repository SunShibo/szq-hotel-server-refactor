package com.szq.hotel.dao;


import com.szq.hotel.entity.bo.FloorRoomBO;
import com.szq.hotel.entity.bo.HomeOrderBO;
import com.szq.hotel.entity.bo.HomeRoomTypeBO;

import java.util.List;
import java.util.Map;

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

}