package com.szq.hotel.service;


import com.szq.hotel.dao.NightAuditDAO;
import com.szq.hotel.entity.bo.NightAuditBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service("NightAuditService")
@Transactional
public class NightAuditService {
    private static final Logger log = LoggerFactory.getLogger(CommodiryService.class);
    @Resource
    private NightAuditDAO  nightAuditDAO;


    /**
     *
     * @param childId  子订单id
     * @param hotelId  酒店id
     * @param personNum  在住人数
     * @param source  客源
     */
    public  Integer addAudit(Integer childId,Integer hotelId,Integer personNum,String source){
        log.info("start addAudit..............................................................");
        log.info("childId:{}\thotelId:{}\tpersonNum:{}\tsource:{}",childId,hotelId,personNum,source);
        NightAuditBO nightAuditBO=new NightAuditBO();
        nightAuditBO.setChildId(childId);
        nightAuditBO.setHotelId(hotelId);
        nightAuditBO.setPersonNum(personNum);
        nightAuditBO.setSource(source);
        nightAuditDAO.addAudit(nightAuditBO);
        log.info("end addAudit..............................................................");
        return  nightAuditBO.getId();
    }

    /**
     * 删除
     */
    public void  deleteAudit(Integer id){
        log.info("start deleteAudit..............................................................");
        log.info("Integer:{}",id);
        nightAuditDAO.deleteAudit(id);
        log.info("end deleteAudit..............................................................");
    }

}
