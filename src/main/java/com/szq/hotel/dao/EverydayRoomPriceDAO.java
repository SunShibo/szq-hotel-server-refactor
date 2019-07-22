package com.szq.hotel.dao;

import com.szq.hotel.entity.bo.EverydayRoomPriceBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EverydayRoomPriceDAO {

    //添加每日房价
    Integer addEverydayRoomPrice(EverydayRoomPriceBO everydayRoomPriceBO);

    //根据子订单id查询每日房价
    List<EverydayRoomPriceBO> getEverydayRoomById(Integer id);

    //根据子订单id查询未经过夜审的日期价格
    List<EverydayRoomPriceBO> getRemainingEverydayRoomById(@Param("time") String time, @Param("id") Integer id);

    //根据子订单id 时间 查询日期价格
    EverydayRoomPriceBO getRemainingEverydayRoomByIdAndTime(@Param("time") String time, @Param("id") Integer id);

    //根据子订单删除每日房价
    Integer delEverydayRoomById(Integer id);

    //修改每日房价
    Integer updEverydayRoomPrice(EverydayRoomPriceBO everydayRoomPriceBO);
}
