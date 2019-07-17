package com.szq.hotel.dao;


import com.szq.hotel.entity.bo.ManagementReportBO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

public interface ManagementReportDAO {

    /**
     * 添加数据
     */
    void addData(ManagementReportBO managementReportBO);

    //查询应收合计（管理层报表）
    BigDecimal getReceivableSum(@Param("startTime") String startTime, @Param("endTime") String endTime);
}
