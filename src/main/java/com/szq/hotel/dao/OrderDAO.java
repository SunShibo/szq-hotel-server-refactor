package com.szq.hotel.dao;

import com.szq.hotel.entity.bo.OrderBO;
import com.szq.hotel.entity.bo.OrderChildBO;
import com.szq.hotel.entity.bo.OrderListBO;
import com.szq.hotel.entity.bo.OrderRecoredBO;
import com.szq.hotel.entity.param.OrderParam;

import java.math.BigDecimal;
import java.util.List;

public interface OrderDAO {
    //添加主订单 返回id
    Integer addOrder(OrderBO orderBO);

    //添加子订单 返回id
    Integer addOrderChild(OrderChildBO orderChildBO);

    //修改子订单
    Integer updOrderChild(OrderChildBO orderChildBO);

    /**
     * 订单列表
     * @param param
     * @return
     */
    List<OrderListBO> queryOrderList(OrderParam param);

    BigDecimal queryUnitPrice(Integer id);

    void addOrderRecored(OrderRecoredBO orderRecoredBO);
}
