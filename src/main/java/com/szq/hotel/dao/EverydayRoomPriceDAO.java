package com.szq.hotel.dao;

import com.szq.hotel.entity.bo.EverydayRoomPriceBO;

import java.util.List;

public interface EverydayRoomPriceDAO {

    //添加每日房价
    Integer addEverydayRoomPrice(EverydayRoomPriceBO everydayRoomPriceBO);

    //根据子订单id查询每日房价
    List<EverydayRoomPriceBO> getEverydayRoomById(Integer id);

    //根据子订单删除每日房价
    Integer delEverydayRoomById(Integer id);
}
