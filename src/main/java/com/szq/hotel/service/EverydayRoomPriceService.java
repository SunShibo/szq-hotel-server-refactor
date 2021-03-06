package com.szq.hotel.service;

import com.szq.hotel.dao.EverydayRoomPriceDAO;
import com.szq.hotel.entity.bo.EverydayRoomPriceBO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("EverydayRoomPriceService")
@Transactional
public class EverydayRoomPriceService {

    @Resource
    EverydayRoomPriceDAO everydayRoomPriceDAO;

    //根据子订单id查询每日房价
    public List<EverydayRoomPriceBO> getEverydayRoomById(Integer id){
        return everydayRoomPriceDAO.getEverydayRoomById(id);
    }

}
