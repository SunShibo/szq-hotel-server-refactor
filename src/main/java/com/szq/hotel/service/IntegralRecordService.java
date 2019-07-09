package com.szq.hotel.service;

import com.szq.hotel.dao.IntegralRecordDAO;
import com.szq.hotel.entity.bo.IntegralRecordBO;
import com.szq.hotel.web.controller.MemberController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("integralRecordService")
@Transactional
public class IntegralRecordService {
    private static final Logger log = LoggerFactory.getLogger(MemberController.class);
    @Resource
    private IntegralRecordDAO integralRecordDAO;

    /*
    新增记录
 */
    public void addIntegralRecord(IntegralRecordBO integralRecordBO,Integer userId){
        log.info("start================addIntegralRecord");
        log.info("param{}\tUserId{}",integralRecordBO,userId);
        integralRecordBO.setCreateUserId(userId);
        integralRecordDAO.addIntegralRecord(integralRecordBO);
        log.info("end===================addIntegralRecord");
    }
}
