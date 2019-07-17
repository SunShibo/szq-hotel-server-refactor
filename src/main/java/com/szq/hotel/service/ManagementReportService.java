package com.szq.hotel.service;

import com.szq.hotel.dao.ManagementReportDAO;
import com.szq.hotel.entity.bo.ManagementReportBO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;

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
    public BigDecimal getReceivableSum(Map<String,Object> map){
        return managementReportDAO.getReceivableSum(map);
    }
    //房费收入
    public BigDecimal getRoomRate(){
        return managementReportDAO.getRoomRate();
    }
}
