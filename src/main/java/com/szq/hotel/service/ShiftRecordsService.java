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

        return  null;
    }


}