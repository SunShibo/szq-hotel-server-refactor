package com.szq.hotel.dao;


import com.szq.hotel.entity.bo.EverydayRoomPriceBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 改价
 */
public interface UpdatePriceDAO {


    List<EverydayRoomPriceBO> queryPrice(@Param("orderId") Integer orderId, @Param("roomTypeId") Integer roomTypeId);

    List<Integer> queryChildId(@Param("orderId") Integer orderId, @Param("roomTypeId") Integer roomTypeId);


    void delePrice(Integer id);

    void addPrice(Map<String,Object> map);

}
