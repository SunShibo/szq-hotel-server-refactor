package com.szq.hotel.service;

import com.szq.hotel.common.constants.Constants;
import com.szq.hotel.dao.CommodiryDAO;
import com.szq.hotel.entity.bo.CommodityBO;
import com.szq.hotel.entity.bo.MemberBO;
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
    @Resource
    private MemberService memberService;

    /**
     * 添加商品交易
     * @param payType 支付方式
     * @param consumptionType 消费类型
     * @param money 金额
     * @param info 备注
     * @param orderNumber 订单号
     * @param userId
     * @param hotelId
     * @param certificateNumber 证件号
     * @return
     */
    public Integer addCommodiry(String payType, String consumptionType, BigDecimal money, String info,String orderNumber,Integer userId,Integer hotelId,String certificateNumber){
        log.info("start addFloor..........................");
        log.info("payType:{}\tconsumptionType:{}\tmoney:{}\tinfo:{}\torderNumber:{}\tuserId:{}\thotelId:{}",payType,consumptionType,money,info,orderNumber,userId,hotelId);
        CommodityBO commodityBO = new CommodityBO(orderNumber, payType, consumptionType, money, userId, info, hotelId);
        commodiryDAO.addCommodiry(commodityBO);
        if(payType.equals(Constants.STORED.getValue())){
            memberService.storedValuePay(certificateNumber,money,info,"储值支付",new BigDecimal("0"),userId);
            MemberBO memberBO = memberService.selectMemberByCerNumber(certificateNumber);
            memberService.accountIntegral( memberBO.getId(),money,info,userId);
        }
        log.info("end  addFloor..........................");
        return commodityBO.getId();

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

    /**
     * 查询记录数
     * @param map
     * @return
     */
    public  int queryCommodiryCount(Map<String,Object> map){
        return commodiryDAO.queryCommodiryCount(map);

    }

    public CommodityBO queryCommodiryById(Integer id) {
        return commodiryDAO.queryCommodiryById(id);
    }
}