package com.szq.hotel.service;

import com.szq.hotel.dao.StoredValueRecordDAO;
import com.szq.hotel.entity.bo.StoredValueRecordBO;
import com.szq.hotel.util.IDBuilder;
import com.szq.hotel.web.controller.MemberController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service("storedValueRecordService")
@Transactional
public class StoredValueRecordService {
    private static final Logger log = LoggerFactory.getLogger(MemberController.class);
    @Resource
    private StoredValueRecordDAO storedValueRecordDAO;

    /**
     * 添加储值记录
     * @param id 入住会员id
     * @param storedValueChange 储值变化值
     * @param remark 备注
     * @param type 类型
     * @param presenterMoney 优惠金额
     * @param currentBalance 余额
     * @param userId 操作人id
     */
    public void addStoredValueRecord(Integer id, BigDecimal storedValueChange,String remark,String type, BigDecimal presenterMoney,BigDecimal currentBalance, Integer userId){
        log.info("start================addStoredValueRecord");
        log.info("param{}\tUserId{}",id,storedValueChange,remark,type,presenterMoney,currentBalance,userId);
        StoredValueRecordBO storedValueRecordBO = new StoredValueRecordBO();
        storedValueRecordBO.setCheckInMemberId(id);
        storedValueRecordBO.setStoredValueMoney(storedValueChange);
        storedValueRecordBO.setRemark(remark);
        storedValueRecordBO.setType(type);
        storedValueRecordBO.setPresenterMoney(presenterMoney);
        storedValueRecordBO.setOddNumbers(IDBuilder.getOrderNumber());
        storedValueRecordBO.setCurrentBalance(currentBalance);
        storedValueRecordBO.setCreateUserId(userId);
        storedValueRecordDAO.addStoredValueRecord(storedValueRecordBO);
        log.info("end===================addStoredValueRecord");
    }

    /*
       获取储值记录列表
    */
    public List<StoredValueRecordBO> getStoredValueRecord(Map<String,Object> map){
        return storedValueRecordDAO.getStoredValueRecord(map);
    }
    /*
        获取储值记录数量
     */
    public Integer getStoredValueRecordCount(Map<String,Object> map){
        return storedValueRecordDAO.getStoredValueRecordCount(map);
    }

}
