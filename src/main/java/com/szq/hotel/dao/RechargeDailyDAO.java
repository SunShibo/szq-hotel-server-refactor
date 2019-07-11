package com.szq.hotel.dao;

import com.szq.hotel.entity.bo.RechargeDailyBO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface RechargeDailyDAO {
    /*
        添加充值日报
     */
    void insertRechargeDaily(RechargeDailyBO rechargeDailyBO);
    /*
        获取充值日报
     */
    List<RechargeDailyBO> getRechargeDaily(@Param("begin") Date begin,@Param("end") Date end);
}
