package com.szq.hotel.dao;


import com.szq.hotel.entity.bo.EverydayRoomPriceBO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 改价
 */
public interface UpdatePriceDAO {


    List<EverydayRoomPriceBO> queryPrice(@Param("orderId") Integer orderId, @Param("roomTypeId") Integer roomTypeId);

    List<EverydayRoomPriceBO> queryChildPrice(@Param("orderId") Integer orderId, @Param("roomTypeId") Integer roomTypeId);



    void delePrice(Integer id);

    void addPrice(Map<String,Object> map);


    List<String>  queryHour(@Param("list")List<Integer>  list);
    List<String>  queryFree(@Param("list")List<Integer>  list);


    int queryCount(@Param("startTime") Date starTime,@Param("endTime") Date endTime,@Param("roomId") Integer roomId);

    int queryTypeCount(Integer typeId);

    int queryOrdedrTypeCount(@Param("startTime") Date starTime,@Param("endTime") Date endTime,@Param("roomTypeId") Integer roomTypeId);
}
