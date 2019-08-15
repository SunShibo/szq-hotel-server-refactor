package com.szq.hotel.dao;


import com.szq.hotel.entity.bo.NightAuditBO;

public interface NightAuditDAO {
    /**
     * 添加
     */
    void addAudit(NightAuditBO nightAuditBO);

    /**
     * 删除
     */
     void  deleteAudit(Integer id);
}
