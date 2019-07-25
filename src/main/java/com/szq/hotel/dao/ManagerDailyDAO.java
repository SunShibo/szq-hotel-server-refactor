package com.szq.hotel.dao;


import com.szq.hotel.entity.bo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ManagerDailyDAO {

    /**
     * 计算昨天预约未到的房间数
     * @param hotelId 酒店id
     * @param startTime 时间范围 开始时间
     * @param endTime   结束时间
     * @return
     */
    List<OcBO> queryNumberOrder(@Param("hotelId")Integer hotelId,
                                @Param("startTime")String startTime,
                                @Param("endTime")String endTime);


    /**
     * 计算昨天维修房数量
     * @return
     */
    List<RoomRecordBO> queryMaintenanceroomNumber(@Param("hotelId")Integer hotelId);

    /**
     * 计算昨天锁房数
     * @param startTime
     * @param endTime
     * @return
     */
    List<RoomBO> queryNumberlockedStores(@Param("hotelId")Integer hotelId,
                                         @Param("startTime")String startTime,
                                         @Param("endTime")String endTime);


    /**
     * 计算房间总数
     * @param hotelId
     * @return
     */
    int queryTotalnumberGuestrooms(@Param("hotelId")Integer hotelId);

    /**
     * 计算现金收入
     * @param hotelId
     * @param startTime
     * @param endTime
     * @return
     */
    List<CommodityTransactionBO> queryCash(@Param("hotelId")Integer hotelId,
                                           @Param("startTime")String startTime,
                                           @Param("endTime")String endTime);

    /**
     * 计算日租金额
     * @param startTime
     * @param endTime
     * @return
     */
    List<HmCashierSummaryBO> queryThroughoutDayrent(@Param("startTime")String startTime,
                                                  @Param("endTime")String endTime);
}