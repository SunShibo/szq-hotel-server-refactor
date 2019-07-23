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
    void updateRecord(OrderRecoredBO orderRecoredBO);

    /**
     * 查询是否包含以结账
     */
    int queryInvoicing(@Param("list")List<Integer> list);

    /**
     * 查询支付方式
     * @param childId
     * @return
     */
    List<String> queryPayType(Integer childId);

    /**
     * 查询支付方式
     */
    Integer queryChildIdByRecordId(Integer id);

    /**
     * 查询消费项
     * @param list
     * @return
     */
    double consumption(@Param("list") List<Integer> list);

    /**
     * 查询支付项
     * @param list
     * @return
     */
    double pay(@Param("list")List<Integer> list);

    /**
     * 修改订单状态为已结
     * @param list
     */
    void closedAccount(@Param("list")List<Integer> list);

    /**
     * 结账查询
     * @param list
     * @return
     */
    List<Integer> queryRecordIds(@Param("list")List<Integer> list);

    /**
     * 完成订单
     * @param list
     */
    void completeAccount(@Param("list")List<Integer> list);
}
