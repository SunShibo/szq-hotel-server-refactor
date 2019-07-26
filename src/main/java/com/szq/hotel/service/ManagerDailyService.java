package com.szq.hotel.service;

import com.szq.hotel.dao.ManagerDailyDAO;
import com.szq.hotel.dao.ManagerdailyBOMapper;
import com.szq.hotel.entity.bo.*;
import com.szq.hotel.util.DateUtils;
import org.apache.ibatis.annotations.Param;
import org.openxmlformats.schemas.drawingml.x2006.main.STGeomGuideFormula;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @Resource
    private ManagerdailyBOMapper managerdailyBOMapper;

    /**
     * 插入经理日报
     * @param hotelId
     */
    public void insertManagerDaliy(Integer hotelId,String date){
        String startTime = date + " 00:00:00";
        String endTime = date + " 23:59:59";
        ManagerdailyBO managerdailyBO = new ManagerdailyBO();

        //添加营业状况统计
       /* managerdailyBO.setGrossrealIncome();//总实际收入 //todo
        managerdailyBO.setTotalTurnover();//总营业额  //todo*/
        managerdailyBO.setNumberOrder(numberOrder(hotelId, startTime, endTime));//预定未到房数
        managerdailyBO.setMaintenanceroomNumber(maintenanceroomNumber(hotelId));//计算维修房数
        managerdailyBO.setNumberlockedStores(numberlockedStores(hotelId, startTime, endTime));//计算锁房数
        managerdailyBO.setNumberroomsAvailablerent(numberroomsAvailablerent(hotelId, startTime, endTime));//计算可租房数
        managerdailyBO.setTotalnumberGuestrooms(totalnumberGuestrooms(hotelId));//计算客房总数
        managerdailyBO.setCashDisbursements(cashDisbursements());//计算现金支出
        managerdailyBO.setCash(cash(hotelId, startTime, endTime));//计算现金收入
        managerdailyBO.setDailyType(1);
        managerdailyBO.setHotelId(hotelId);
        managerdailyBO.setDateTime(DateUtils.parseDate(date, "yyyy-MM-dd"));
        managerdailyBOMapper.insertSelective(managerdailyBO);



        //添加营业收入明细
        ManagerdailyBO managerdailyBO2 = new ManagerdailyBO();
        managerdailyBO2.setThroughoutDayrent(throughoutDayrent(hotelId, startTime, endTime));//全天日租
        managerdailyBO2.setRateAdjustment(rateAdjustment(startTime, endTime));//计算房费调整
        managerdailyBO2.setHourRate(hourRate(hotelId, startTime, endTime));//计算钟点房费
        managerdailyBO2.setTimeoutRate(timeoutRate(startTime, endTime));//计算超时房费
        managerdailyBO2.setNuclearnightRoomcharge(nuclearnightRoomcharge(startTime, endTime));//计算夜核房费
        managerdailyBO2.setCompensation(compensation(startTime, endTime));//赔偿
        managerdailyBO2.setMembershipFee(membershipFee(startTime, endTime));//计算会员卡费
        managerdailyBO2.setGoods(goods(startTime, endTime));//计算商品
        managerdailyBO2.setDailyType(2);
        managerdailyBO2.setHotelId(hotelId);
        managerdailyBO2.setDateTime(DateUtils.parseDate(date, "yyyy-MM-dd"));
        managerdailyBOMapper.insertSelective(managerdailyBO2);





        //房费收入分析
        ManagerdailyBO managerdailyBO3 = new ManagerdailyBO();
        //会员
        double members = members(hotelId, startTime, endTime);
        managerdailyBO3.setMembers(members);
        //协议单位
        double v = agreementUnit(hotelId, startTime, endTime);
        managerdailyBO3.setAgreementUnit(v);
        //散客
        double v1 = individualTraveler(hotelId, startTime, endTime);
        managerdailyBO3.setIndividualTraveler(v1);
        //直接入住
        double enter = enter(hotelId, startTime, endTime);
        //预约入住
        double v2 = directBooking(hotelId, startTime, endTime);
        managerdailyBO3.setSubtotal(members+v+v1+enter+v2);
        managerdailyBO3.setDailyType(3);
        managerdailyBO3.setHotelId(hotelId);
        managerdailyBO3.setDateTime(DateUtils.parseDate(date, "yyyy-MM-dd"));
        managerdailyBOMapper.insertSelective(managerdailyBO3);





        //房晚数分析
        ManagerdailyBO managerdailyBO4 = new ManagerdailyBO();
        //会员房晚数
        double a = (double)FwMembers(hotelId, startTime, endTime);
        managerdailyBO4.setMembers(a);
        //协议单位房晚数
        double b = (double)FwAgreementUnit(hotelId, startTime, endTime);
        managerdailyBO4.setAgreementUnit(b);
        //散客房晚数
        double c = (double)FwIndividualTraveler(hotelId, startTime, endTime);
        managerdailyBO4.setIndividualTraveler(c);
        //直接入住房晚数
        double d = (double)FwEnter(hotelId, startTime, endTime);
        managerdailyBO4.setEnter(d);
        //房间预订房晚数
        double e = (double)FwDirectBooking(hotelId, startTime, endTime);
        managerdailyBO4.setDirectBooking(e);
        //小计
        double f = a+b+c+d+e;
        managerdailyBO4.setSubtotal(f );
        managerdailyBO4.setDailyType(4);
        managerdailyBO4.setHotelId(hotelId);
        managerdailyBO4.setDateTime(DateUtils.parseDate(date, "yyyy-MM-dd"));
        managerdailyBOMapper.insertSelective(managerdailyBO4);




        //平均房价分析
        ManagerdailyBO managerdailyBO5 = new ManagerdailyBO();
        //会员平均房价分析
        double v3 = PjMembers(hotelId, startTime, endTime);
        managerdailyBO5.setMembers(v3);
        //协议单位平均房价
        double v4 = PjAgreementUnit(hotelId, startTime, endTime);
        managerdailyBO5.setAgreementUnit(v4);
        //散客平均房价
        double v5 = PjIndividualTraveler(hotelId, startTime, endTime);
        managerdailyBO5.setIndividualTraveler(v5);
        //直接入住平均房价
        double v6 = PjEnter(hotelId, startTime, endTime);
        managerdailyBO5.setEnter(v6);
        //预约入住平均房价
        double v7 = PjDirectBooking(hotelId, startTime, endTime);
        managerdailyBO5.setDirectBooking(v7);
        //小计
        managerdailyBO5.setSubtotal(v3+v4+v5+v6+v7);
        managerdailyBO5.setDailyType(5);
        managerdailyBO5.setHotelId(hotelId);
        managerdailyBO5.setDateTime(DateUtils.parseDate(date, "yyyy-MM-dd"));
        managerdailyBOMapper.insertSelective(managerdailyBO5);




        //出租率分析
        ManagerdailyBO managerdailyBO6 = new ManagerdailyBO();
        //会员出租率
        managerdailyBO6.setMembers(a/f);
        //协议单位出租率
        managerdailyBO6.setAgreementUnit(b/f);
        //散客出租率
        managerdailyBO6.setIndividualTraveler(c/f);
        //直接入住出租率
        managerdailyBO6.setEnter(d/f);
        //预约入住出租率
        managerdailyBO6.setDirectBooking(e/f);
        //小计
        managerdailyBO6.setSubtotal(f/f);
        managerdailyBO6.setDailyType(6);
        managerdailyBO6.setHotelId(hotelId);
        managerdailyBO6.setDateTime(DateUtils.parseDate(date, "yyyy-MM-dd"));
        managerdailyBOMapper.insertSelective(managerdailyBO6);
        return;

    }


    /**
     * 计算预定未到房数
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
     * 计算客房总数
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
     * 计算全天日租  计算全天日租出去的房间数量
     *
     * @return
     */
    private int  throughoutDayrent(Integer hotelId, String startTime, String endTime){
        Integer integer = managerDailyDAO.queryThroughoutDayrent2(hotelId, startTime, endTime);
        return integer;
    }

    /**
     * 房费调整
     * @return
     */
    private double rateAdjustment(String startTime, String endTime){
        List<CashierSummary> cashierSummaries = managerDailyDAO.queryRateAdjustment(startTime, endTime);
        double n = 0.0;
        for (CashierSummary cashierSummary : cashierSummaries) {
            n = n + cashierSummary.getConsumption();
        }
        return n;
    }

    /**
     * 钟点房费
     * @param hotelId
     * @param startTime
     * @param endTime
     * @return
     */
    private double hourRate(Integer hotelId, String startTime, String endTime){
        List<Order> orders = managerDailyDAO.queryhourRate(hotelId, startTime, endTime);
        double n = 0.0;
        for (Order order : orders) {
            n = n + order.getTotalPrice();
        }
        return n;
    }


    /**
     * 计算超时房费
     * @param startTime
     * @param endTime
     * @return
     */
    private double timeoutRate(String startTime, String endTime){
        List<CashierSummary> cashierSummaries = managerDailyDAO.querytimeoutRate(startTime, endTime);
        double n = 0;
        for (CashierSummary cashierSummary : cashierSummaries) {
            n = n + cashierSummary.getConsumption();
        }
        return n ;
    }


    /**
     * 计算夜核房费
     * @param startTime
     * @param endTime
     * @return
     */
    private double nuclearnightRoomcharge(String startTime, String endTime){
        List<CashierSummary> cashierSummaries = managerDailyDAO.querynuclearnightRoomcharge1(startTime, endTime);
        double n1 = 0.0;
        for (CashierSummary cashierSummary : cashierSummaries) {
            n1 = n1 + cashierSummary.getConsumption();
        }

        List<CashierSummary> cashierSummaries2 = managerDailyDAO.querynuclearnightRoomcharge2(startTime, endTime);
        double n2 = 0.0;
        for (CashierSummary cashierSummary : cashierSummaries2) {
            n2 = n2 + cashierSummary.getConsumption();
        }
        return n1 - n2 ;
    }


    /**
     * 计算赔偿
     * @param startTime
     * @param endTime
     * @return
     */
    private double compensation(String startTime, String endTime){
        List<CashierSummary> querycompensation = managerDailyDAO.querycompensation(startTime, endTime);
        double n = 0.0;
        for (CashierSummary cashierSummary : querycompensation) {
            n = n + cashierSummary.getConsumption();
        }
        return n;
    }

    /**
     * 会员卡费
     * @param startTime
     * @param endTime
     * @return
     */
    private double membershipFee(String startTime, String endTime){
        List<CashierSummary> cashierSummaries = managerDailyDAO.querymembershipFee(startTime, endTime);
        double n = 0.0;
        for (CashierSummary cashierSummary : cashierSummaries) {
            n = n + cashierSummary.getConsumption();
        }
        return n ;
    }

    /**
     * 商品
     * @param startTime
     * @param endTime
     * @return
     */
    private double goods(String startTime, String endTime){
        List<CashierSummary> querygoods = managerDailyDAO.querygoods(startTime, endTime);
        double n = 0.0 ;
        for (CashierSummary querygood : querygoods) {
            n = n + querygood.getConsumption();
        }
       return n ;
    }

    /**
     *  房费收入分析
     *  会员
     * @param hotelId
     * @param startTime
     * @param endTime
     * @return
     */
    private double members(Integer hotelId, String startTime, String endTime){
        List<Order> querymembers = managerDailyDAO.querymembers(hotelId, startTime, endTime);
        double n =0.0;
        for (Order querymember : querymembers) {
            n = n + querymember.getTotalPrice();
        }
        return n ;
    }

    /**
     * 房费收入分析
     * 中介  协议单位
     * @param hotelId
     * @param startTime
     * @param endTime
     * @return
     */
    private double agreementUnit(Integer hotelId, String startTime, String endTime){
        List<Order> orders = managerDailyDAO.qyeryagreementUnit(hotelId, startTime, endTime);
        double n =0.0;
        for (Order querymember : orders) {
            n = n + querymember.getTotalPrice();
        }
        return n ;
    }

    /**
     * 房费收入分析
     * 散客
     * @param hotelId
     * @param startTime
     * @param endTime
     * @return
     */
    private double individualTraveler(Integer hotelId, String startTime, String endTime){
        List<Order> orders = managerDailyDAO.queryindividualTraveler(hotelId, startTime, endTime);
        double n = 0.0;
        for (Order order : orders) {
            n = n + order.getTotalPrice();
        }
        return n;
    }


    /**
     * 步入 直接入住
     * @param hotelId
     * @param startTime
     * @param endTime
     * @return
     */
    private double enter(Integer hotelId, String startTime, String endTime){
        List<Order> orders = managerDailyDAO.queryEnter(hotelId, startTime, endTime);
        double n = 0.0;
        for (Order order : orders) {
            n = n + order.getTotalPrice();
        }
        return n;
    }

    /**
     * 房间预订 | 直接预订
     * @param hotelId
     * @param startTime
     * @param endTime
     * @return
     */
    private double directBooking(Integer hotelId, String startTime, String endTime){
        List<Order> orders = managerDailyDAO.queryDirectBooking(hotelId, startTime, endTime);
        double n = 0.0;
        for (Order order : orders) {
            n = n + order.getTotalPrice();
        }
        return n;
    }


    /**
     * 会员房晚数
     * @param hotelId
     * @param startTime
     * @param endTime
     * @return
     */
    private int FwMembers(Integer hotelId, String startTime, String endTime){
        Integer integer = managerDailyDAO.queryFwMembers(hotelId, startTime, endTime);
        return integer;
    }

    /**
     * 协议单位房晚数
     * @param hotelId
     * @param startTime
     * @param endTime
     * @return
     */
    private int FwAgreementUnit(Integer hotelId, String startTime, String endTime){
        Integer integer = managerDailyDAO.queryFwAgreementUnit(hotelId, startTime, endTime);
        return integer;
    }

    /**
     * 散客房晚数
     * @param hotelId
     * @param startTime
     * @param endTime
     * @return
     */
    private int FwIndividualTraveler(Integer hotelId, String startTime, String endTime){
        Integer integer = managerDailyDAO.queryFwIndividualTraveler(hotelId, startTime, endTime);
        return integer;
    }

    /**
     * 直接入住房晚数
     * @param hotelId
     * @param startTime
     * @param endTime
     * @return
     */
    private int FwEnter(Integer hotelId, String startTime, String endTime ){
        Integer integer = managerDailyDAO.queryFwEnter(hotelId, startTime, endTime);
        return integer;
    }

    /**
     * 房间预订房晚数
     * @param hotelId
     * @param startTime
     * @param endTime
     * @return
     */
    private int FwDirectBooking(Integer hotelId, String startTime, String endTime ){
        Integer integer = managerDailyDAO.queryFwDirectBooking(hotelId, startTime, endTime);
        return integer;
    }

    /**
     * 会员平均房价分析
     * @param hotelId
     * @param startTime
     * @param endTime
     * @return
     */
    private double PjMembers(Integer hotelId, String startTime, String endTime){
        //会员总房价
        double members = members(hotelId, startTime, endTime);
        //会员开放数量
        double i = FwMembers(hotelId, startTime, endTime);
        return members/i;
    }


    /**
     * 协议单位平均房价分析
     * @param hotelId
     * @param startTime
     * @param endTime
     * @return
     */
    private double PjAgreementUnit(Integer hotelId, String startTime, String endTime){
        //协议单位总房价
        double v = agreementUnit(hotelId, startTime, endTime);
        //协议单位开放数量
        double i = FwAgreementUnit(hotelId, startTime, endTime);
        return v/i;
    }


    /**
     * 散客平均房价分析
     * @param hotelId
     * @param startTime
     * @param endTime
     * @return
     */
    private double PjIndividualTraveler(Integer hotelId, String startTime, String endTime){
        //散客总房价
        double v = individualTraveler(hotelId, startTime, endTime);
        //散客总开房数
        double i = FwIndividualTraveler(hotelId, startTime, endTime);

        return v/i;
    }

    /**
     * 直接入住平均房价分析
     * @param hotelId
     * @param startTime
     * @param endTime
     * @return
     */
    private double PjEnter(Integer hotelId, String startTime, String endTime){
        //直接入住总房价
        double enter = enter(hotelId, startTime, endTime);
        //直接入住房间数
        double i = FwEnter(hotelId, startTime, endTime);

        return enter/i;
    }


    /**
     * 房间预订分均房价分析
     * @param hotelId
     * @param startTime
     * @param endTime
     * @return
     */
    private double PjDirectBooking(Integer hotelId, String startTime, String endTime){
        //房间预订总房价
        double v = directBooking(hotelId, startTime, endTime);
        //房间预订总数量
        double i = FwDirectBooking(hotelId, startTime, endTime);
        return v/i;
    }
}
