package com.szq.hotel.service;

import com.szq.hotel.common.constants.Constants;
import com.szq.hotel.dao.ShiftRecordsDAO;
import com.szq.hotel.entity.bo.ShiftRecordsBO;
import com.szq.hotel.util.JsonUtils;
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
    public void beOnDuty(Integer userId, Integer classesId,Integer hotelId) {
        log.info("start beOnDuty............................");
        log.info("userId{}\tclassesId{}\thotelId:{}", userId, classesId,hotelId);
        //判断是否上班
        Integer row = shiftRecordsDAO.queryIsExist(userId);
        if (row != null) {
            //在上班
            log.info("end beOnDuty............................");
            return;
        }
        //二次效验
        Integer count = shiftRecordsDAO.queryIsExist(userId);
        if (count != null) {
            //在上班
            log.info("end beOnDuty............................");
            return;
        }
        //上班
        log.info("addShiftRecords............................");
        shiftRecordsDAO.addShiftRecords(new ShiftRecordsBO(classesId,userId,hotelId));
        log.info("end beOnDuty............................");
    }


    /**
     * 交班
     *
     * @param type
     * @param userId
     * @return
     */
    public ShiftRecordsBO shifRecord(String type, Integer userId) {
        log.info("sdtart shifRecord .......................................");
        log.info("type:{}\tuserId:{}", type, userId);
        log.info("start queryIsExist .......................................");
        Integer id = shiftRecordsDAO.queryIsExist(userId);
        log.info("result:{}", id);
        log.info("start shiftRecordsDAO.queryShiftRecordsById.......................................");
        ShiftRecordsBO shiftRecordsBO = shiftRecordsDAO.queryShiftRecordsById(id);
        log.info("result:{}", shiftRecordsBO);

        //查询条件
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("startTime", shiftRecordsBO.getAttendanceTime());
        queryMap.put("endTime", new Date());
        queryMap.put("userId", shiftRecordsBO.getAdminId());

        log.info("start shiftRecordsDAO.queryIncome....shiftRecordsDAO.queryBack..............");
        log.info("param{}", JsonUtils.getJsonString4JavaPOJO(queryMap));
        //现金
        queryMap.put("payType", Constants.CASH.getValue());
        BigDecimal cashIncome = shiftRecordsDAO.queryIncome(queryMap);
        BigDecimal cashBack = shiftRecordsDAO.queryBack(queryMap);
        shiftRecordsBO.setCashIncome(cashIncome);
        shiftRecordsBO.setCashBack(cashBack);
        shiftRecordsBO.setCashAmount(shiftRecordsBO.getCashIncome().add(shiftRecordsBO.getCashBack()));
        //银行卡
        queryMap.put("payType", Constants.CART.getValue());
        BigDecimal cardIncome = shiftRecordsDAO.queryIncome(queryMap);
        BigDecimal cardBack = shiftRecordsDAO.queryBack(queryMap);
        shiftRecordsBO.setBankCardIncome(cardIncome);
        shiftRecordsBO.setBankCardBack(cardBack);
        shiftRecordsBO.setBankCardAmount(shiftRecordsBO.getBankCardIncome().add(shiftRecordsBO.getBankCardBack()));
        //微信
        queryMap.put("payType", Constants.WECHAT.getValue());
        BigDecimal wechatIncome = shiftRecordsDAO.queryIncome(queryMap);
        BigDecimal wechatBack = shiftRecordsDAO.queryBack(queryMap);
        shiftRecordsBO.setWechatIncome(wechatIncome);
        shiftRecordsBO.setWechatBack(wechatBack);
        shiftRecordsBO.setWechatAmount(shiftRecordsBO.getWechatIncome().add(shiftRecordsBO.getWechatBack()));
        //支付宝
        queryMap.put("payType", Constants.ALIPAY.getValue());
        BigDecimal aliIncome = shiftRecordsDAO.queryIncome(queryMap);
        BigDecimal aliBack = shiftRecordsDAO.queryBack(queryMap);
        shiftRecordsBO.setAlipayIncome(aliIncome);
        shiftRecordsBO.setAlipayBack(aliBack);
        shiftRecordsBO.setAlipayAmount(shiftRecordsBO.getAlipayIncome().add(shiftRecordsBO.getAlipayBack()));
        //其他支付
        queryMap.put("payType", Constants.OTHER.getValue());
        BigDecimal otherIncome = shiftRecordsDAO.queryIncome(queryMap);
        BigDecimal otherBack = shiftRecordsDAO.queryBack(queryMap);
        shiftRecordsBO.setOtherIncome(otherIncome);
        shiftRecordsBO.setOtherBack(otherBack);
        shiftRecordsBO.setOtherAmount(shiftRecordsBO.getOtherIncome().add(shiftRecordsBO.getOtherBack()));
        //储值
        queryMap.put("payType", Constants.STORED.getValue());
        BigDecimal storedIncome = shiftRecordsDAO.queryIncome(queryMap);
        BigDecimal storedBack = shiftRecordsDAO.queryBack(queryMap);
        shiftRecordsBO.setStoredValueIncome(storedIncome);
        shiftRecordsBO.setStoredValueBack(storedBack);
        shiftRecordsBO.setStoredValueAmount(shiftRecordsBO.getStoredValueIncome().add(shiftRecordsBO.getStoredValueBack()));
        //积分
        queryMap.put("payType", Constants.INTEGRAL.getValue());
        BigDecimal integralIncome = shiftRecordsDAO.queryIncome(queryMap);
        BigDecimal integralBack = shiftRecordsDAO.queryBack(queryMap);
        shiftRecordsBO.setIntegralIncome(integralIncome);
        shiftRecordsBO.setIntegralBack(integralBack);
        shiftRecordsBO.setIntegralAmount(shiftRecordsBO.getIntegralIncome().add(shiftRecordsBO.getIntegralBack()));

        //总收入
        shiftRecordsBO.setGeneralIncome(shiftRecordsBO.getCashAmount().add(shiftRecordsBO.getBankCardAmount()).
                add(shiftRecordsBO.getWechatAmount()).add(shiftRecordsBO.getAlipayAmount()).add(shiftRecordsBO.getOtherAmount()).
                add(shiftRecordsBO.getStoredValueAmount()).add(shiftRecordsBO.getIntegralAmount()));

        //办卡数
        int count = shiftRecordsDAO.queryServizioCount(queryMap);

        shiftRecordsBO.setMemberCardSellCount(count);
        log.info("shiftRecordsDAO.queryIncome..shiftRecordsDAO.queryBack.....result{}", shiftRecordsBO);

        if (Constants.YES.getValue().equals(type)) {
            log.info("start..updateShiftRecords.................");
            log.info("param{}", shiftRecordsBO);
            shiftRecordsDAO.updateShiftRecords(shiftRecordsBO);
            log.info("end..updateShiftRecords.................");
        }

        log.info("end shifRecord .......................................");
        return shiftRecordsBO;
    }

    /**
     * 查询交班记录
     */
    public List<ShiftRecordsBO> queryShiftRecordList(Map<String,Object> map){
        return shiftRecordsDAO.queryShiftRecordList(map);
    }
    /**
     * 列表页数量
     */
    public int queryShiftRecordCount(Map<String,Object> map){
        return shiftRecordsDAO.queryShiftRecordCount(map);
    }

    /**
     * 查询一条交班记录
     */
    public ShiftRecordsBO queryShiftRecordsById(Integer id) {
          return shiftRecordsDAO.queryShiftRecordsById(id);
    }



}