package com.szq.hotel.dao;

import com.szq.hotel.entity.bo.OrderBO;
import com.szq.hotel.entity.bo.OrderChildBO;
import com.szq.hotel.entity.bo.OrderListBO;
import com.szq.hotel.entity.bo.OrderRecoredBO;
import com.szq.hotel.entity.param.OrderParam;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface OrderDAO {
    //添加主订单 返回id
    Integer addOrder(OrderBO orderBO);

    //添加子订单 返回id
    Integer addOrderChild(OrderChildBO orderChildBO);

    //修改子订单
    Integer updOrderChild(OrderChildBO orderChildBO);

    //根据手机号 身份证号 查询主订单预约信息
    OrderBO getOrderByIdOrMobile(@Param("idNumber")String idNumber, @Param("mobile")String mobile, @Param("date") String date);

    //根据订单id查询订单信息
    OrderBO getOrderById(Integer orderId);

    //根据订单id查询子订单
    List<OrderChildBO> getOrderChildById(Integer id);

    //修改主订单
    Integer updOrder(OrderBO orderBO);
    //删除旧子订单
    Integer delOrderChild(Integer id);

    /**
     * 订单列表
     * @param param
     * @return
     */
    List<OrderListBO> queryOrderList(OrderParam param);

    BigDecimal queryUnitPrice(Integer id);

    void addOrderRecored(OrderRecoredBO orderRecoredBO);
}
