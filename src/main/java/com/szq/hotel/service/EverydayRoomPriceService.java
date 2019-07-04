package com.szq.hotel.service;

import com.szq.hotel.dao.EverydayRoomPriceDAO;
import com.szq.hotel.entity.bo.EverydayRoomPriceBO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("EverydayRoomPriceService")
@Transactional
public class EverydayRoomPriceService {

    @Resource
    EverydayRoomPriceDAO everydayRoomPriceDAO;

    //添加每日房价
    public Integer addEverydayRoomPrice(EverydayRoomPriceBO everydayRoomPriceBO){
        return everydayRoomPriceDAO.addEverydayRoomPrice(everydayRoomPriceBO);
    }
}
