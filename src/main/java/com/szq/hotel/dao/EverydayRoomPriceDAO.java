package com.szq.hotel.dao;

import com.szq.hotel.entity.bo.EverydayRoomPriceBO;

public interface EverydayRoomPriceDAO {

    //添加每日房价
    Integer addEverydayRoomPrice(EverydayRoomPriceBO everydayRoomPriceBO);
}
