package com.szq.hotel.service;

import com.szq.hotel.common.constants.Constants;
import com.szq.hotel.dao.FloorDAO;
import com.szq.hotel.dao.ShiftRecordsDAO;
import com.szq.hotel.entity.bo.FloorBO;
import com.szq.hotel.entity.bo.ShiftRecordsBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  交班
 */
@Service("ShiftRecordsService")
@Transactional
public class ShiftRecordsService {
    private static final Logger log = LoggerFactory.getLogger(ShiftRecordsService.class);

    @Resource
    private ShiftRecordsDAO shiftRecordsDAO;

    /**
     * 上班
     */
    public void beOnDuty(Integer userId,Integer classesId){
        log.info("start beOnDuty............................");
        log.info("userId{}\tclassesId{}",userId,classesId);
        //判断是否上班
        Integer row = shiftRecordsDAO.queryIsExist(userId);
        if(row!=null){
            //在上班
            log.info("end beOnDuty............................");
            return;
        }
        //二次效验
        Integer count = shiftRecordsDAO.queryIsExist(userId);
        if(count!=null){
            //在上班
            log.info("end beOnDuty............................");
            return;
        }
        //上班
        log.info("addShiftRecords............................");
        shiftRecordsDAO.addShiftRecords(new ShiftRecordsBO(userId,classesId));
        log.info("end beOnDuty............................");
    }


    /**
     * 交班
     * @param type
     * @param userId
     * @return
     */
    public ShiftRecordsBO shifRecord(String type,Integer userId){
        Integer id = shiftRecordsDAO.queryIsExist(userId);
        ShiftRecordsBO shiftRecordsBO = shiftRecordsDAO.queryShiftRecordsById(id);

        Map<String,Object> queryMap=new HashMap<String,Object>();
        queryMap.put("startTime",shiftRecordsBO.getAttendanceTime());
        queryMap.put("endTime",new Date());
        queryMap.put("userId",shiftRecordsBO.getAdminId());
      /*          *//* 支付方式 : 支付宝 *//* ALIPAY("alipay"),
                *//* 支付方式 : 其他支付 *//* OTHER("other"),
                *//* 支付方式 : 储值 *//* STORED("stored"),
                *//* 支付方式 : 积分 *//* INTEGRAL("integral");*/
        //现金
        queryMap.put("payType",Constants.CASH.getValue());
        BigDecimal cashIncome = shiftRecordsDAO.queryIncome(queryMap);
        BigDecimal cashBack = shiftRecordsDAO.queryBack(queryMap);
        shiftRecordsBO.setCashIncome(cashIncome);
        shiftRecordsBO.setCashBack(cashBack);
        shiftRecordsBO.setCashAmount(shiftRecordsBO.getCashBack().add(shiftRecordsBO.getCashBack()));
        //银行卡
        queryMap.put("payType",Constants.CART.getValue());
        BigDecimal cardIncome = shiftRecordsDAO.queryIncome(queryMap);
        BigDecimal cardBack = shiftRecordsDAO.queryBack(queryMap);
        shiftRecordsBO.setBankCardIncome(cardIncome);
        shiftRecordsBO.setBankCardBack(cardBack);
        shiftRecordsBO.setBankCardAmount(shiftRecordsBO.getBankCardIncome().add(shiftRecordsBO.getBankCardBack()));
        //微信
        queryMap.put("payType",Constants.WECHAT.getValue());
        BigDecimal wechatIncome = shiftRecordsDAO.queryIncome(queryMap);
        BigDecimal wechatBack = shiftRecordsDAO.queryBack(queryMap);
        shiftRecordsBO.setWechatIncome(wechatIncome);
        shiftRecordsBO.setWechatBack(wechatBack);
        shiftRecordsBO.setWechatAmount(shiftRecordsBO.getWechatIncome().add(shiftRecordsBO.getWechatBack()));
        //支付宝
        queryMap.put("payType",Constants.ALIPAY.getValue());
        BigDecimal aliIncome = shiftRecordsDAO.queryIncome(queryMap);
        BigDecimal aliBack = shiftRecordsDAO.queryBack(queryMap);
        shiftRecordsBO.setAlipayIncome(aliIncome);
        shiftRecordsBO.setAlipayBack(aliBack);
        shiftRecordsBO.setWechatAmount(shiftRecordsBO.getAlipayIncome().add(shiftRecordsBO.getAlipayBack()));
        //微信
        queryMap.put("payType",Constants.ALIPAY.getValue());
     /*   BigDecimal aliIncome = shiftRecordsDAO.queryIncome(queryMap);
        BigDecimal aliBack = shiftRecordsDAO.queryBack(queryMap);
        shiftRecordsBO.setAlipayIncome(aliIncome);
        shiftRecordsBO.setAlipayBack(aliBack);
        shiftRecordsBO.setWechatAmount(shiftRecordsBO.getAlipayIncome().add(shiftRecordsBO.getAlipayBack()));*/

        return  null;
    }


}