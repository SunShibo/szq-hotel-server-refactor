package com.szq.hotel.service;

import com.szq.hotel.dao.ManagerDailyDAO;
import com.szq.hotel.entity.bo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Bin Wang
 * @date: Created in 12:29 2019/7/23
 */
@Service
public class ManagerDailyService {

    @Resource
    private ManagerDailyDAO managerDailyDAO;

    @Resource
    private RoomService roomService;

    /**
     * 经理日报
     * 每天凌晨十二点开始统计数据 并且记录到数据库内
     * 营业状况统计:
     *      实际总收入
     *      总营业额
     *      预定未到房数
     *      维修房数
     *      锁房数
     *      可出租房数
     *      客房总数
     *      现金支出
     *      现金收入
     * 营业状况统计
     */
    public void insertManagerDaliy(){
        return;
    }


    /**
     * 计算昨天预定未到房数
     * @param hotelId 酒店id
     * @param startTime 一天时间范围 开始时间
     * @param endTime   结束时间
     * @return
     */
    private int numberOrder(Integer hotelId, String startTime, String endTime){
        //查询子订单根据酒店id和昨天时间日期
        List<OcBO> ocBOS = managerDailyDAO.queryNumberOrder(hotelId, startTime, endTime);
        return ocBOS.size();
    }

    /**
     * 计算维修房数
     * @param hotelId
     * @return
     */
    private int maintenanceroomNumber(Integer hotelId){
        List<RoomRecordBO> roomRecordBOS = managerDailyDAO.queryMaintenanceroomNumber(hotelId);
        return roomRecordBOS.size();
    }

    /** TODO 标记
     * 计算锁房数
     * @return
     */
    private int numberlockedStores(Integer hotelId, String startTime, String endTime){
        List<RoomBO> roomBOS = managerDailyDAO.queryNumberlockedStores(hotelId, startTime, endTime);
        return roomBOS.size();
    }

    /**
     * 计算可出租房数
     * @return
     */
    private int numberroomsAvailablerent(Integer hotelId, String startTime, String endTime){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("hotelId", hotelId);
        map.put("checkTime", startTime);
        map.put("endTime", endTime);
        List<String> ll = new ArrayList<String>();
        ll.add("reservation");
        ll.add("notpay");
        ll.add("admissions");
        List<RmBO> rmBOS = roomService.publicQuery(map, ll);
        return rmBOS.size();
    }

    /**
     * 计算昨天客房总数
     * @return
     */
    private int totalnumberGuestrooms(Integer hotelId){
        int i = managerDailyDAO.queryTotalnumberGuestrooms(hotelId);
        return i;
    }

    /**
     * 计算现金支出
     * @return
     */
    private double cashDisbursements(){
        return 0.0;
    }

    /**
     * 计算现金收入
     * @return
     */
    private double cash(Integer hotelId, String startTime, String endTime){
        List<CommodityTransactionBO> commodityTransactionBOS = managerDailyDAO.queryCash(hotelId, startTime, endTime);
        double n = 0.0;
        for (CommodityTransactionBO commodityTransactionBO : commodityTransactionBOS) {
            n = n + commodityTransactionBO.getMoney();
        }
       return n;
    }






    /**
     * 计算日租金额
     *
     * @return
     */
    private double throughoutDayrent(@Param("startTime")String startTime, @Param("endTime")String endTime){
        List<HmCashierSummaryBO> hmCashierSummaryBOS = managerDailyDAO.queryThroughoutDayrent(startTime, endTime);
        double n = 0.0;
        for (HmCashierSummaryBO hmCashierSummaryBO : hmCashierSummaryBOS) {
            n = n + hmCashierSummaryBO.getSettlement();
        }

        return n;
    }

    /**
     * 房费调整 TODO 标记
     * @return
     */
    private double rateAdjustment(){
        return 0.0;
    }





}
