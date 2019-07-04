package com.szq.hotel.dao;

import com.szq.hotel.entity.bo.OrderBO;
import com.szq.hotel.entity.bo.OrderChildBO;

public interface OrderDAO {
    //添加主订单 返回id
    Integer addOrder(OrderBO orderBO);

    //添加子订单 返回id
    Integer addOrderChild(OrderChildBO orderChildBO);

    //修改子订单
    Integer updOrderChild(OrderChildBO orderChildBO);
}
