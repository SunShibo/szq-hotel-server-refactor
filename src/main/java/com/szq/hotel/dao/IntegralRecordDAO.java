package com.szq.hotel.dao;

import com.szq.hotel.entity.bo.IntegralRecordBO;

import java.util.List;
import java.util.Map;

public interface IntegralRecordDAO {

/*
    新增记录
 */
    void addIntegralRecord(IntegralRecordBO integralRecordBO);
/*
    查询积分记录
 */
    List<IntegralRecordBO> getIntegralRecord(Map<String,Object> map);
    /*
        查询积分记录数量
     */
    Integer getIntegralRecordCount(Map<String,Object> map);
}
