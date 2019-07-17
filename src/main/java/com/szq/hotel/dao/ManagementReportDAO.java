package com.szq.hotel.dao;


import com.szq.hotel.entity.bo.ManagementReportBO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Map;

public interface ManagementReportDAO {

    /**
     * 添加数据
     */
    void addData(ManagementReportBO managementReportBO);

    //查询应收合计（管理层报表）
    BigDecimal getReceivableSum(Map<String,Object> map);
    //房费收入
    BigDecimal getRoomRate();
    //房间总数
    Integer getRoomSum(Map<String,Object> map);
    //会员卡收入
    BigDecimal getMemberCardRate();
    //入住人数
    Integer getCheckInPerson(Map<String,Object> map);
    //赔偿收入
    BigDecimal getCompensation();
    //免费入住房数
    Integer getFreeRoomSum(Map<String,Object> map);
    //维修房数
    Integer getMaintainSum();
    //会员房数
    Integer getMemberRoomSum(Map<String,Object> map);
    //商品收入
    BigDecimal getCommodity();
    //房费调整
    BigDecimal getRoomRateAdjustment();
    //锁房数
    Integer getLockRoomSum();
    //空房数
    Integer getEmptyRoomSum();
    //钟点房晚数
    Integer getHourRoomSum(Map<String,Object> map);
}
