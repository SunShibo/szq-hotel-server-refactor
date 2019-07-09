package com.szq.hotel.service;

import com.szq.hotel.dao.IntegralRecordDAO;
import com.szq.hotel.dao.MemberDAO;
import com.szq.hotel.entity.bo.IntegralRecordBO;
import com.szq.hotel.entity.bo.MemberBO;
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

@Service("integralRecordService")
@Transactional
public class IntegralRecordService {
    private static final Logger log = LoggerFactory.getLogger(MemberController.class);
    @Resource
    private IntegralRecordDAO integralRecordDAO;
    @Resource
    private MemberDAO memberDAO;

    /**
     * 新增记录
     * @param id 会员id
     * @param integralChange 积分改变值
     * @param remark 备注
     * @param type 类型
     * @param userId 操作人id
     * @param currentBalance 剩余积分
     */
    public void addIntegralRecord(Integer id, BigDecimal integralChange, String remark,String type,BigDecimal currentBalance, Integer userId){
        log.info("start================addIntegralRecord");
        log.info("param{}\tUserId{}",id,integralChange,remark,type,userId);


        IntegralRecordBO integralRecordBO = new IntegralRecordBO();
        integralRecordBO.setCreateUserId(userId);
        integralRecordBO.setCheckInMemberId(id);
        integralRecordBO.setType(type);
        integralRecordBO.setOddNumbers(IDBuilder.getOrderNumber());
        integralRecordBO.setCurrentBalance(currentBalance);
        integralRecordBO.setRemark(remark);
        integralRecordBO.setIntegralChange(integralChange);
        integralRecordDAO.addIntegralRecord(integralRecordBO);
        log.info("end===================addIntegralRecord");
    }

    /*
    查询积分记录
 */
    public List<IntegralRecordBO> getIntegralRecord(Map<String,Object> map){
        return integralRecordDAO.getIntegralRecord(map);
    }
    /*
        查询积分记录数量
     */
    public Integer getIntegralRecordCount(Map<String,Object> map){
        return integralRecordDAO.getIntegralRecordCount(map);
    }
}
