package com.szq.hotel.service;

import com.szq.hotel.dao.CommodiryDAO;
import com.szq.hotel.dao.FloorDAO;
import com.szq.hotel.entity.bo.CommodityBO;
import com.szq.hotel.entity.bo.FloorBO;
import com.szq.hotel.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 *  商品交易
 */
@Service("CommodiryService")
@Transactional
public class CommodiryService {

    private static final Logger log = LoggerFactory.getLogger(CommodiryService.class);

    @Resource
    private CommodiryDAO commodiryDAO;

    /**
     * 添加商品交易
     */
    public void addCommodiry(String payType, String consumptionType, BigDecimal money, String info,String orderNumber,Integer userId,Integer hotelId){
        log.info("start addFloor..........................");
        log.info("payType:{}\tconsumptionType:{}\tmoney:{}\tinfo:{}\torderNumber:{}\tuserId:{}\thotelId:{}",payType,consumptionType,money,info,orderNumber,userId,hotelId);
        commodiryDAO.addCommodiry(new  CommodityBO(orderNumber,payType,consumptionType,money,userId,info,hotelId));
        log.info("end  addFloor..........................");
    }

    /**
     * 查询商品交易
     * startTime
     * endTime
     * condition
     * hotelId
     */
    public List<CommodityBO> queryCommodiry(Map<String,Object> map){
        log.info("start queryCommodiry..........................");
        log.info("param:{}", JsonUtils.getJsonString4JavaPOJO(map));
        List<CommodityBO> commodityBOS = commodiryDAO.queryCommodiry(map);
        log.info("end queryCommodiry..........................");
        return commodityBOS;
    }

}