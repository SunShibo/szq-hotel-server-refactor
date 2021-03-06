package com.szq.hotel.dao;


import com.szq.hotel.entity.bo.*;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
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
    BigDecimal queryCash(@Param("hotelId")Integer hotelId,
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

    /**
     * 计算今天日租出去房间价格
     * @param hotelId
     * @param startTime
     * @param endTime
     * @return
     */
    Integer queryThroughoutDayrent2(@Param("hotelId")Integer hotelId, @Param("startTime")String startTime,
                                @Param("endTime")String endTime);


    /**
     * 计算房费调整
     * @param startTime
     * @param endTime
     * @return
     */
    BigDecimal queryRateAdjustment(@Param("hotelId")Integer hotelId,@Param("startTime")String startTime,@Param("endTime")String endTime);


    /**
     * 查询全天钟点房费
     * @param hotelId
     * @param startTime
     * @param endTime
     * @return
     */
    BigDecimal queryhourRate(@Param("hotelId")Integer hotelId, @Param("startTime")String startTime, @Param("endTime")String endTime);


    /**
     * 计算今天超时房费
     * @param startTime
     * @param endTime
     * @return
     */
    BigDecimal querytimeoutRate(@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("hotelId")Integer hotelId);


    /**
     *  夜核房费  房费
     * @param startTime
     * @param endTime
     * @return
     */
    List<CashierSummary> querynuclearnightRoomcharge1(@Param("startTime")String startTime,@Param("endTime")String endTime);

    /**
     *  夜核房费 房费冲减
     * @param startTime
     * @param endTime
     * @return
     */
    List<CashierSummary> querynuclearnightRoomcharge2(@Param("startTime")String startTime,@Param("endTime")String endTime);

    /**
     * 计算赔偿
     * @param startTime
     * @param endTime
     * @return
     */
    List<CashierSummary> querycompensation(@Param("hotelId")Integer hotelId,@Param("startTime")String startTime,@Param("endTime")String endTime);


    /**
     * 会员卡费
     * @param startTime
     * @param endTime
     * @return
     */
    BigDecimal querymembershipFee(@Param("hotelId")Integer hotelId,@Param("startTime")String startTime,@Param("endTime")String endTime);

    /**
     * 商品
     * @param startTime
     * @param endTime
     * @return
     */
    List<CashierSummary> querygoods(@Param("hotelId")Integer hotelId,@Param("startTime")String startTime,@Param("endTime")String endTime);


    /**
     * 房费 收入 分析
     * 会员入住
     * @param hotelId
     * @param startTime
     * @param endTime
     * @return
     */
    BigDecimal querymembers(@Param("hotelId")Integer hotelId, @Param("startTime")String startTime, @Param("endTime")String endTime);


    /**
     * 房费收入分析
     *  协议单位 中介
     * @param hotelId
     * @param startTime
     * @param endTime
     * @return
     */
    BigDecimal qyeryagreementUnit(@Param("hotelId")Integer hotelId, @Param("startTime")String startTime, @Param("endTime")String endTime);


    /** 房费收入分析
     *  散客
     * @param hotelId
     * @param startTime
     * @param endTime
     * @return
     */
    BigDecimal queryindividualTraveler(@Param("hotelId")Integer hotelId, @Param("startTime")String startTime, @Param("endTime")String endTime);

    /**
     * 房费收入分析
     * 步入 | 直接入住
     * @param hotelId
     * @param startTime
     * @param endTime
     * @return
     */
    BigDecimal queryEnter(@Param("hotelId")Integer hotelId, @Param("startTime")String startTime, @Param("endTime")String endTime);


    /**
     * 房费收入分析
     * 直接入住 | 预订入住
     * @param hotelId
     * @param startTime
     * @param endTime
     * @return
     */
    BigDecimal queryDirectBooking(@Param("hotelId")Integer hotelId, @Param("startTime")String startTime, @Param("endTime")String endTime);


    /**
     * 计算会员房晚数
     * @param hotelId
     * @param startTime
     * @param endTime
     * @return
     */
    Integer  queryFwMembers(@Param("hotelId")Integer hotelId, @Param("startTime")String startTime, @Param("endTime")String endTime);

    /**
     * 协议单位房晚数
     * @param hotelId
     * @param startTime
     * @param endTime
     * @return
     */
    Integer queryFwAgreementUnit(@Param("hotelId")Integer hotelId, @Param("startTime")String startTime, @Param("endTime")String endTime);

    /**
     * 散客房晚数
     * @param hotelId
     * @param startTime
     * @param endTime
     * @return
     */
    Integer queryFwIndividualTraveler(@Param("hotelId")Integer hotelId, @Param("startTime")String startTime, @Param("endTime")String endTime);


    /**
     * 直接入住房晚数
     */
    Integer queryFwEnter(@Param("hotelId")Integer hotelId, @Param("startTime")String startTime, @Param("endTime")String endTime);


    /**
     * 房间预订房晚数
     * @param hotelId
     * @param startTime
     * @param endTime
     * @return
     */
    Integer queryFwDirectBooking(@Param("hotelId")Integer hotelId, @Param("startTime")String startTime, @Param("endTime")String endTime);


    /**
     * 计算全天日租房的收入
     * @param hotelId
     * @param startTime
     * @param endTime
     * @return
     */
    List<OrderReBO> queryOrderRe(@Param("hotelId")Integer hotelId, @Param("startTime")String startTime, @Param("endTime")String endTime);


    /**
     *
     * @param hotelId
     * @param startTime
     * @param endTime
     * @return
     */
    BigDecimal queryGoods2(@Param("hotelId")Integer hotelId, @Param("startTime")String startTime, @Param("endTime")String endTime);


    void up(@Param("day")BigDecimal day,@Param("hour")BigDecimal hour,@Param("time")String time);
}