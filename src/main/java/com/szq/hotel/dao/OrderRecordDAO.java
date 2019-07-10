package com.szq.hotel.dao;




import com.szq.hotel.entity.bo.OrderRecoredBO;

import java.util.List;

/**
 * 子订单详情
 */

public interface OrderRecordDAO {


    void addOrderRecord(OrderRecoredBO orderRecoredBO);

    List<OrderRecoredBO> queryOrderRecord(Integer id);
}
