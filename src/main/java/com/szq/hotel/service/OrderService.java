package com.szq.hotel.service;

import com.szq.hotel.common.constants.Constants;
import com.szq.hotel.dao.OrderDAO;
import com.szq.hotel.entity.bo.EverydayRoomPriceBO;
import com.szq.hotel.entity.bo.OrderBO;
import com.szq.hotel.entity.bo.OrderChildBO;
import com.szq.hotel.util.IDBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service("OrderService")
@Transactional
public class OrderService {

    @Resource
    OrderDAO orderDAO;

    @Resource
    EverydayRoomPriceService everydayRoomPriceService;

    //添加主订单 返回订单id
    public Integer addOrder(OrderBO orderBO){
        //生成订单号
        IDBuilder idBuilder = new IDBuilder(10, 10);
        String orderNumber = idBuilder.nextId()+"";
        orderBO.setOrderNumber(orderNumber);
        return orderDAO.addOrder(orderBO);
    }

    //添加子订单
    public Integer addOrderChild(List<OrderChildBO> orderChildBOList, Integer orderId, Integer hotelId){
        //联房码
        String alRoomCode= UUID.randomUUID().toString();
        for (OrderChildBO orderChild:orderChildBOList) {
            //添加子订单 返回订单id
            orderChild.setOrderId(orderId);//主订单id
            orderChild.setAlRoomCode(alRoomCode);//联房
            orderChild.setOrderState(Constants.RESERVATION.getValue());//状态
            orderDAO.addOrderChild(orderChild);//返回子订单id

            //这个房型下的每日价格
            List<EverydayRoomPriceBO> everydayRoomPriceBOList =orderChild.getEverydayRoomPriceBOS();
            for (EverydayRoomPriceBO everydayRoomPriceBO:everydayRoomPriceBOList) {
                orderChild.setOrderId(orderChild.getId());
                everydayRoomPriceService.addEverydayRoomPrice(everydayRoomPriceBO);
            }

        }
        return 0;
    }
}
