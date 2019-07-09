package com.szq.hotel.dao;

import com.szq.hotel.entity.bo.StoredValueRecordBO;

public interface StoredValueRecordDAO {
    /*
        添加储值记录
     */
    void addStoredValueRecord(StoredValueRecordBO storedValueRecordBO);
}
