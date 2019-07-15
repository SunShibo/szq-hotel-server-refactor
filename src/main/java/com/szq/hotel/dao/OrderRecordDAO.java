package com.szq.hotel.dao;




import com.szq.hotel.entity.bo.OrderRecoredBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 子订单详情
 */

public interface OrderRecordDAO {


    void addOrderRecord(OrderRecoredBO orderRecoredBO);

    //查询消费记录
    List<OrderRecoredBO> queryOrderRecord(Integer id);

    /**
     * 查询子订单详情
     * @param id
     * @return
     */
    OrderRecoredBO queryOrderRecordById(Integer id);

    /**
     * 修改子订单详情
     */
    OrderRecoredBO updateRecord(OrderRecoredBO orderRecoredBO);

    /**
     * 查询是否包含以结账
     */
    int queryInvoicing(@Param("list")List<Integer> list);

    /**
     * 查询支付方式
     * @param list
     * @return
     */
    List<String> queryPayType(@Param("list")List<Integer> list);

}
