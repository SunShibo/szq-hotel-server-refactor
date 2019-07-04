package com.szq.hotel.service;

import com.szq.hotel.common.constants.Constants;
import com.szq.hotel.dao.OrderDAO;
import com.szq.hotel.entity.bo.EverydayRoomPriceBO;
import com.szq.hotel.entity.bo.OrderBO;
import com.szq.hotel.entity.bo.OrderChildBO;
import com.szq.hotel.util.IDBuilder;
import org.jaxen.expr.iter.IterableNamespaceAxis;
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
        Integer orderNumber = new Integer(idBuilder.nextId()+"");
        orderBO.setOrderNumber(orderNumber);
        return orderDAO.addOrder(orderBO);
    }

    //添加子订单
    public Integer addOrderChild(List<OrderChildBO> orderChildBOList,Integer orderId,Integer hotelId){
        //联房码
        String alRoomCode= UUID.randomUUID().toString();
        for (OrderChildBO orderChild:orderChildBOList) {
            orderChild.setOrderId(orderId);
            orderChild.setAlRoomCode(alRoomCode);
            orderChild.setOrderState(Constants.RESERVATION.getValue());
            //酒店id
            orderChild.setHotelId(hotelId);
            //插入子订单
            Integer orderChildId = orderDAO.addOrderChild(orderChild);

            // 插入hm_everyday_room_price没日房费信息
            EverydayRoomPriceBO everydayRoomPriceBO=new EverydayRoomPriceBO();
            everydayRoomPriceBO.setOrderChildId(orderChildId);
            everydayRoomPriceService.addEverydayRoomPrice(everydayRoomPriceBO);
        }



        return 0;
    }
}
