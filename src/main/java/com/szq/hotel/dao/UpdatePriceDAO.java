package com.szq.hotel.dao;


import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 改价
 */
public interface UpdatePriceDAO {

    @MapKey("time")
    Map<String,Object> queryPrice(@Param("orderId") Integer orderId, @Param("roomTypeId") Integer roomTypeId);

    List<Integer> queryChildId(@Param("orderId") Integer orderId, @Param("roomTypeId") Integer roomTypeId);


    void delePrice(Integer id);

    void addPrice(Map<String,Object> map);

}
