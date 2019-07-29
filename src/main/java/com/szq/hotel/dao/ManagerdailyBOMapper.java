package com.szq.hotel.dao;

import com.szq.hotel.entity.bo.ManagerdailyBO;
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
                                               @Param("endTime") String endTime);

    /**
     * 获取上月同期实际总收入
     * @param hotelId
     * @param date
     * @return
     */
    List<ManagerdailyBO> queryManagerdailyList2(@Param("hotelId")Integer hotelId,@Param("date")String date);

    /**
     * 获取本年累计
     * @param hotelId
     * @param date
     * @return
     */
    List<ManagerdailyBO> queryYear(@Param("hotelId")Integer hotelId,@Param("date")String date);
}