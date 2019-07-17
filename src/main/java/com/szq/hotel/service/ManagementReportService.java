package com.szq.hotel.service;

import com.szq.hotel.dao.ManagementReportDAO;
import com.szq.hotel.entity.bo.ManagementReportBO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service("managementReportService")
@Transactional
public class ManagementReportService {
    @Resource
    private ManagementReportDAO managementReportDAO;
    /*
        添加数据
     */
    public void addData(ManagementReportBO managementReportBO){


        managementReportDAO.addData(managementReportBO);
    }
    //查询应收合计（管理层报表）
    public BigDecimal getReceivableSum(String start, String end){
        String startTime  = start + " 04:00:00";
        String endTime = end + " 04:00:00";
        return managementReportDAO.getReceivableSum(startTime,endTime);
    }
}
