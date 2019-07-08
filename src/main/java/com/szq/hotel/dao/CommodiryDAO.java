package com.szq.hotel.dao;


import com.szq.hotel.entity.bo.CommodityBO;

import java.util.List;
import java.util.Map;

/**
 * 商品交易
 */
public interface CommodiryDAO {


    void addCommodiry(CommodityBO commodityBO);

    List<CommodityBO> queryCommodiry(Map<String,Object> map);

}
