package com.szq.hotel.dao;

import com.szq.hotel.entity.bo.CashierSummary;
import com.szq.hotel.entity.bo.ManagerdailyBO;
import com.szq.hotel.entity.bo.OrderBO;
import com.szq.hotel.entity.bo.TotalPriceBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ManagerdailyBOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ManagerdailyBO record);

    int insertSelective(ManagerdailyBO record);

    ManagerdailyBO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ManagerdailyBO record);

    int updateByPrimaryKey(ManagerdailyBO record);

    ManagerdailyBO queryManagerdaily(@Param("hotelId")Integer hotelId,
                                     @Param("dailyType")Integer dailyType,
                                     @Param("dateTime")String dateTime);

    /**
     * 获取本月实际总收入
     * @param startTime
     * @param endTime
     * @return
     */
    List<ManagerdailyBO> queryManagerdailyList(@Param("hotelId")Integer hotelId,
                                               @Param("startTime") String startTime,
                                               @Param("endTime") String endTime,
                                               @Param("dailyType")Integer dailyType);

    /**
     * 获取上月同期实际总收入
     * @param hotelId
     * @param date
     * @return
     */
    List<ManagerdailyBO> queryManagerdailyList2(@Param("hotelId")Integer hotelId,@Param("date")String date, @Param("dailyType")Integer dailyType);

    /**
     * 获取本年累计
     * @param hotelId
     * @param date
     * @return
     */
    List<ManagerdailyBO> queryYear(@Param("hotelId")Integer hotelId,@Param("date")String date,@Param("dailyType")Integer dailyType);


    /**
     * 上月同期会员入住率
     * @param hotelId
     * @param date
     * @param dailyType
     * @return
     */
    ManagerdailyBO queryHy(@Param("hotelId")Integer hotelId,@Param("date")String date,@Param("dailyType")Integer dailyType);


    /**
     * 查询当天总营业额
     * @param startTime
     * @param endTime
     * @param hotelId
     * @return
     */
    List<TotalPriceBO> queryOrderTotalPrice(@Param("hotelId")Integer hotelId, @Param("startTime")String startTime, @Param("endTime")String endTime);

    /**
     * 获取当天总收入
     * @param hotelId
     * @param startTime
     * @param endTime
     * @return
     */
    List<CashierSummary> queryConsumption(@Param("hotelId")Integer hotelId, @Param("startTime")String startTime, @Param("endTime")String endTime);
}