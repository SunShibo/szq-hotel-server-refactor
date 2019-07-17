package com.szq.hotel.dao;


import com.szq.hotel.entity.bo.CashierSummaryBO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * 收银汇总
 */
public interface CashierSummaryDAO {

    /**
     * 添加数据
     */
    void addData(CashierSummaryBO cashierSummaryBO);

    /**
     * 查询报表
     * @param cashierSummaryBO
     * @return
     */
    List<CashierSummaryBO> queryCashierSummary(CashierSummaryBO cashierSummaryBO);

    /**
     * 删除一条数据
     */
    void deleteCashierSummary(Integer id);

}
