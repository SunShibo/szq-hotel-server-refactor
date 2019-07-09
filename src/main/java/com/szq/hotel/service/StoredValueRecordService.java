package com.szq.hotel.service;

import com.szq.hotel.dao.StoredValueRecordDAO;
import com.szq.hotel.entity.bo.StoredValueRecordBO;
import com.szq.hotel.web.controller.MemberController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("storedValueRecordService")
@Transactional
public class StoredValueRecordService {
    private static final Logger log = LoggerFactory.getLogger(MemberController.class);
    @Resource
    private StoredValueRecordDAO storedValueRecordDAO;

    /*
        添加储值记录
     */
    public void addStoredValueRecord(StoredValueRecordBO storedValueRecordBO,Integer userId){
        log.info("start================addStoredValueRecord");
        log.info("param{}\tUserId{}",storedValueRecordBO,userId);
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
