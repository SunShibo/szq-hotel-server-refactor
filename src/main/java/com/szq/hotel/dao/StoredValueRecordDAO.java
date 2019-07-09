package com.szq.hotel.dao;

import com.szq.hotel.entity.bo.StoredValueRecordBO;

import java.util.List;
import java.util.Map;

public interface StoredValueRecordDAO {
    /*
        添加储值记录
     */
    void addStoredValueRecord(StoredValueRecordBO storedValueRecordBO);
    /*
        获取储值记录列表
     */
    List<StoredValueRecordBO> getStoredValueRecord(Map<String,Object> map);
    /*
        获取储值记录数量
     */
    Integer getStoredValueRecordCount(Map<String,Object> map);
}
