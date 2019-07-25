package com.szq.hotel.dao;

import com.szq.hotel.entity.bo.IncomeBO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

public interface IncomeDAO {
    //根据时间查询营收报表
    IncomeBO getIncome(@Param("year") Integer year, @Param("month")Integer month,@Param("day")Integer day, @Param("hotelId") Integer hotelId);
    //添加营收信息
    Integer addIncome(IncomeBO incomeBO);
    //收集营收信息
    BigDecimal getCashierSummaryByProject(@Param("createTime")String createTime,@Param("project") String project,@Param("hotelId")Integer hotelId);
    //收集营收信息
    BigDecimal getCashierSummaryByType(@Param("createTime")String createTime,@Param("type") String type,@Param("hotelId")Integer hotelId);
}