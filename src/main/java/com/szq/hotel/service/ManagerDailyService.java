package com.szq.hotel.service;


import com.szq.hotel.dao.ManagerDailyDAO;
import com.szq.hotel.dao.ManagerdailyBOMapper;
import com.szq.hotel.entity.bo.*;
import com.szq.hotel.util.DateUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;



@Service
public class ManagerDailyService {

    final static Logger log = LoggerFactory.getLogger(ManagerDailyService.class);

    @Resource
    private ManagerDailyDAO managerDailyDAO;

    @Resource
    private RoomService roomService;

    @Resource
    private ManagerdailyBOMapper managerdailyBOMapper;

    java.text.DecimalFormat   df   =   new   java.text.DecimalFormat("#.00");
    java.text.DecimalFormat   f   =   new   java.text.DecimalFormat("#");

    public ManagerdailyChangeBO queryMdC(){
        ManagerdailyChangeBO managerdailyChangeBO = new ManagerdailyChangeBO();

        HotelTableBO grossrealIncome = new HotelTableBO();//总实际收入
        managerdailyChangeBO.setGrossrealIncome(grossrealIncome);
        HotelTableBO totalTurnover = new HotelTableBO();//总营业额
        managerdailyChangeBO.setTotalTurnover(totalTurnover);
        HotelTableBO numberOrder = new HotelTableBO();//预订未到的房数
        managerdailyChangeBO.setNumberOrder(numberOrder);
        HotelTableBO maintenanceroomNumber = new HotelTableBO();//维修房子数
        managerdailyChangeBO.setMaintenanceroomNumber(maintenanceroomNumber);
        HotelTableBO numberlockedStores = new HotelTableBO();//门店锁房数
        managerdailyChangeBO.setNumberlockedStores(numberlockedStores);
        HotelTableBO numberroomsAvailablerent = new HotelTableBO();//可出租房数
        managerdailyChangeBO.setNumberroomsAvailablerent(numberroomsAvailablerent);
        HotelTableBO totalnumberGuestrooms = new HotelTableBO();//客房总数
        managerdailyChangeBO.setTotalnumberGuestrooms(totalnumberGuestrooms);
        HotelTableBO cashDisbursements = new HotelTableBO();//现金支出
        managerdailyChangeBO.setCashDisbursements(cashDisbursements);
        HotelTableBO cash = new HotelTableBO();//现金
        managerdailyChangeBO.setCash(cash);

        //,2:营业收入明细
        HotelTableBO throughoutDayrent = new HotelTableBO();//全天日租
        managerdailyChangeBO.setThroughoutDayrent(throughoutDayrent);
        HotelTableBO rateAdjustment = new HotelTableBO();//房费调整
        managerdailyChangeBO.setRateAdjustment(rateAdjustment);
        HotelTableBO hourRate = new HotelTableBO();//钟点房费
        managerdailyChangeBO.setHourRate(hourRate);
        HotelTableBO timeoutRate = new HotelTableBO();//超时房费
        managerdailyChangeBO.setTimeoutRate(timeoutRate);
        HotelTableBO nuclearnightRoomcharge = new HotelTableBO();//夜核房费
        managerdailyChangeBO.setNuclearnightRoomcharge(nuclearnightRoomcharge);
        HotelTableBO compensation = new HotelTableBO();//赔偿
        managerdailyChangeBO.setCompensation(compensation);
        HotelTableBO membershipFee = new HotelTableBO();//会员卡费
        managerdailyChangeBO.setMembershipFee(membershipFee);
        HotelTableBO goods = new HotelTableBO();//商品
        managerdailyChangeBO.setGoods(goods);
        HotelTableBO subtotal2 = new HotelTableBO();//小计
        managerdailyChangeBO.setSubtotal2(subtotal2);

        //3:房费收入分析,4:房晚数分析,5:平均房价分析,6:出租率分析
        HotelTableBO members3 = new HotelTableBO();//会员
        managerdailyChangeBO.setMembers3(members3);
        HotelTableBO agreementUnit3 = new HotelTableBO();//协议单位
        managerdailyChangeBO.setAgreementUnit3(agreementUnit3);
        HotelTableBO intermediary3 = new HotelTableBO();//中介
        managerdailyChangeBO.setIntermediary3(intermediary3);
        HotelTableBO app3 = new HotelTableBO();//app
        managerdailyChangeBO.setApp3(app3);
        HotelTableBO microLetter3 = new HotelTableBO();//微信
        managerdailyChangeBO.setMicroLetter3(microLetter3);
        HotelTableBO individualTraveler3 = new HotelTableBO();//散客
        managerdailyChangeBO.setIndividualTraveler3(individualTraveler3);
        HotelTableBO directBooking3 = new HotelTableBO();//直接预订
        managerdailyChangeBO.setDirectBooking3(directBooking3);
        HotelTableBO enter3 = new HotelTableBO();//步入
        managerdailyChangeBO.setEnter3(enter3);
        HotelTableBO subtotal3 = new HotelTableBO();//小计
        managerdailyChangeBO.setSubtotal3(subtotal3);

        //4:房晚数分析
        HotelTableBO members4 = new HotelTableBO();//会员
        managerdailyChangeBO.setMembers4(members4);
        HotelTableBO agreementUnit4 = new HotelTableBO();//协议单位
        managerdailyChangeBO.setAgreementUnit4(agreementUnit4);
        HotelTableBO intermediary4 = new HotelTableBO();//中介
        managerdailyChangeBO.setIntermediary4(intermediary4);
        HotelTableBO app4 = new HotelTableBO();//app
        managerdailyChangeBO.setApp4(app4);
        HotelTableBO microLetter4 = new HotelTableBO();//微信
        managerdailyChangeBO.setMicroLetter4(microLetter4);
        HotelTableBO individualTraveler4 = new HotelTableBO();//散客
        managerdailyChangeBO.setIndividualTraveler4(individualTraveler4);
        HotelTableBO directBooking4 = new HotelTableBO();//直接预订
        managerdailyChangeBO.setDirectBooking4(directBooking4);
        HotelTableBO enter4 = new HotelTableBO();//步入
        managerdailyChangeBO.setEnter4(enter4);
        HotelTableBO subtotal4 = new HotelTableBO();//小计
        managerdailyChangeBO.setSubtotal4(subtotal4);

        //,5:平均房价分析
        HotelTableBO members5 = new HotelTableBO();//会员
        managerdailyChangeBO.setMembers5(members5);
        HotelTableBO agreementUnit5 = new HotelTableBO();//协议单位
        managerdailyChangeBO.setAgreementUnit5(agreementUnit5);
        HotelTableBO intermediary5 = new HotelTableBO();//中介
        managerdailyChangeBO.setIntermediary5(intermediary5);
        HotelTableBO app5 = new HotelTableBO();//app
        managerdailyChangeBO.setApp5(app5);
        HotelTableBO microLetter5 = new HotelTableBO();//微信
        managerdailyChangeBO.setMicroLetter5(microLetter5);
        HotelTableBO individualTraveler5 = new HotelTableBO();//散客
        managerdailyChangeBO.setIndividualTraveler5(individualTraveler5);
        HotelTableBO directBooking5 = new HotelTableBO();//直接预订
        managerdailyChangeBO.setDirectBooking5(directBooking5);
        HotelTableBO enter5 = new HotelTableBO();//步入
        managerdailyChangeBO.setEnter5(enter5);
        HotelTableBO subtotal5 = new HotelTableBO();//小计
        managerdailyChangeBO.setSubtotal5(subtotal5);
        //,6:出租率分析
        HotelTableBO members6 = new HotelTableBO();//会员
        managerdailyChangeBO.setMembers6(members6);
        HotelTableBO agreementUnit6 = new HotelTableBO();//协议单位
        managerdailyChangeBO.setAgreementUnit6(agreementUnit6);
        HotelTableBO intermediary6 = new HotelTableBO();//中介
        managerdailyChangeBO.setIntermediary6(intermediary6);
        HotelTableBO app6 = new HotelTableBO();//app
        managerdailyChangeBO.setApp6(app6);
        HotelTableBO microLetter6 = new HotelTableBO();//微信
        managerdailyChangeBO.setMicroLetter6(microLetter6);
        HotelTableBO individualTraveler6 = new HotelTableBO();//散客
        managerdailyChangeBO.setIndividualTraveler6(individualTraveler6);
        HotelTableBO directBooking6 = new HotelTableBO();//直接预订
        managerdailyChangeBO.setDirectBooking6(directBooking6);
        HotelTableBO enter6 = new HotelTableBO();//步入
        managerdailyChangeBO.setEnter6(enter6);
        HotelTableBO subtotal6 = new HotelTableBO();//小计
        managerdailyChangeBO.setSubtotal6(subtotal6);
        return managerdailyChangeBO;

    }



    /**
     * 经理日报查询
     * @param date
     * @param
     * @param hotelId
     * @return
     */
    public ManagerdailyChangeBO queryInfo(String date, Integer hotelId){
        log.info("com.szq.hotel.service.ManagerDailyService.queryInfo******************************start");
        ManagerdailyChangeBO managerdailyChangeBO = new ManagerdailyChangeBO();
        //1:营业状况统计
        HotelTableBO grossrealIncome = new HotelTableBO();//总实际收入
        HotelTableBO totalTurnover = new HotelTableBO();//总营业额
        HotelTableBO numberOrder = new HotelTableBO();//预订未到的房数
        HotelTableBO maintenanceroomNumber = new HotelTableBO();//维修房子数
        HotelTableBO numberlockedStores = new HotelTableBO();//门店锁房数
        HotelTableBO numberroomsAvailablerent = new HotelTableBO();//可出租房数
        HotelTableBO totalnumberGuestrooms = new HotelTableBO();//客房总数
        HotelTableBO cashDisbursements = new HotelTableBO();//现金支出
        HotelTableBO cash = new HotelTableBO();//现金

        //,2:营业收入明细
        HotelTableBO throughoutDayrent = new HotelTableBO();//全天日租
        HotelTableBO rateAdjustment = new HotelTableBO();//房费调整
        HotelTableBO hourRate = new HotelTableBO();//钟点房费
        HotelTableBO timeoutRate = new HotelTableBO();//超时房费
        HotelTableBO nuclearnightRoomcharge = new HotelTableBO();//夜核房费
        HotelTableBO compensation = new HotelTableBO();//赔偿
        HotelTableBO membershipFee = new HotelTableBO();//会员卡费
        HotelTableBO goods = new HotelTableBO();//商品
        HotelTableBO subtotal2 = new HotelTableBO();//小计

        //3:房费收入分析,4:房晚数分析,5:平均房价分析,6:出租率分析
        HotelTableBO members3 = new HotelTableBO();//会员
        HotelTableBO agreementUnit3 = new HotelTableBO();//协议单位
        HotelTableBO intermediary3 = new HotelTableBO();//中介
        HotelTableBO app3 = new HotelTableBO();//app
        HotelTableBO microLetter3 = new HotelTableBO();//微信
        HotelTableBO individualTraveler3 = new HotelTableBO();//散客
        HotelTableBO directBooking3 = new HotelTableBO();//直接预订
        HotelTableBO enter3 = new HotelTableBO();//步入
        HotelTableBO subtotal3 = new HotelTableBO();//小计

        //4:房晚数分析
        HotelTableBO members4 = new HotelTableBO();//会员
        HotelTableBO agreementUnit4 = new HotelTableBO();//协议单位
        HotelTableBO intermediary4 = new HotelTableBO();//中介
        HotelTableBO app4 = new HotelTableBO();//app
        HotelTableBO microLetter4 = new HotelTableBO();//微信
        HotelTableBO individualTraveler4 = new HotelTableBO();//散客
        HotelTableBO directBooking4 = new HotelTableBO();//直接预订
        HotelTableBO enter4 = new HotelTableBO();//步入
        HotelTableBO subtotal4 = new HotelTableBO();//小计


        //,5:平均房价分析
        HotelTableBO members5 = new HotelTableBO();//会员
        HotelTableBO agreementUnit5 = new HotelTableBO();//协议单位
        HotelTableBO intermediary5 = new HotelTableBO();//中介
        HotelTableBO app5 = new HotelTableBO();//app
        HotelTableBO microLetter5 = new HotelTableBO();//微信
        HotelTableBO individualTraveler5 = new HotelTableBO();//散客
        HotelTableBO directBooking5 = new HotelTableBO();//直接预订
        HotelTableBO enter5 = new HotelTableBO();//步入
        HotelTableBO subtotal5 = new HotelTableBO();//小计
        //,6:出租率分析
        HotelTableBO members6 = new HotelTableBO();//会员
        HotelTableBO agreementUnit6 = new HotelTableBO();//协议单位
        HotelTableBO intermediary6 = new HotelTableBO();//中介
        HotelTableBO app6 = new HotelTableBO();//app
        HotelTableBO microLetter6 = new HotelTableBO();//微信
        HotelTableBO individualTraveler6 = new HotelTableBO();//散客
        HotelTableBO directBooking6 = new HotelTableBO();//直接预订
        HotelTableBO enter6 = new HotelTableBO();//步入
        HotelTableBO subtotal6 = new HotelTableBO();//小计

        //当天营业状况统计
        System.err.println(hotelId);
        System.err.println(date);
        ManagerdailyBO managerdailyBO = managerdailyBOMapper.queryManagerdaily(hotelId, 1, date);
        System.err.println(managerdailyBO);
        if (managerdailyBO == null){
            return queryMdC();
        }
        //当天营业收入明细
        ManagerdailyBO managerdailyBO1 = managerdailyBOMapper.queryManagerdaily(hotelId, 2, date);
        if(managerdailyBO1 == null){
            managerdailyBO1 = new ManagerdailyBO();
        }
        //当天房费收入明细
        ManagerdailyBO managerdailyBO2 = managerdailyBOMapper.queryManagerdaily(hotelId, 3, date);
        if(managerdailyBO2 == null){
            managerdailyBO2 = new ManagerdailyBO();
        }
        //当天房晚数分析
        ManagerdailyBO managerdailyBO3 = managerdailyBOMapper.queryManagerdaily(hotelId, 4 , date);
        if(managerdailyBO3 == null){
            managerdailyBO3 = new ManagerdailyBO();
        }
        //当天平均房价分析
        ManagerdailyBO managerdailyBO4 = managerdailyBOMapper.queryManagerdaily(hotelId, 5 , date);
        if(managerdailyBO4 == null){
            managerdailyBO4 = new ManagerdailyBO();
        }
        //当天出租率分析
        ManagerdailyBO managerdailyBO5 = managerdailyBOMapper.queryManagerdaily(hotelId, 6 , date);
        if(managerdailyBO5 == null){
            managerdailyBO5 = new ManagerdailyBO();
        }


        //获取今年日期
        String yyyy = date.substring(0,4);
        //获取上一年日期
        String year = (Integer.parseInt(yyyy) - 1) + "";


        //当天营业收入
        grossrealIncome.setDay(managerdailyBO.getGrossrealIncome().intValue() != 0 ? df.format(managerdailyBO.getGrossrealIncome())+"" : "0.00");

        //本月营业收入
        grossrealIncome.setMonth(month(hotelId, date).intValue() != 0 ? df.format(month(hotelId, date))+"" : "0.00");

        //上月同期
        grossrealIncome.setLastMonthDay(lastMonthDay(hotelId, date).intValue() != 0 ? df.format(lastMonthDay(hotelId, date))+"" : "0.00");

        //本年累计
        grossrealIncome.setYear(year(hotelId, yyyy).intValue() != 0 ? df.format(year(hotelId, yyyy))+"" : "0.00");

        //上年同期
        grossrealIncome.setLastYearDay(lastYearDay(hotelId, date).intValue() != 0 ? df.format(lastYearDay(hotelId, date))+"" : "0.00");

        //计算年增长率
        grossrealIncome.setInsertRial(isIncrease(year(hotelId, yyyy),year(hotelId, year)));




        //获取当天营业额
        totalTurnover.setDay(managerdailyBO.getTotalTurnover().intValue() != 0  ? df.format(managerdailyBO.getTotalTurnover())+"" : "0.00");

        //计算本月累计营业额
        totalTurnover.setMonth(queryMonth(hotelId, date).intValue() != 0 ? df.format(queryMonth(hotelId, date))+"" : "0.00");

        //计算上月同期营业额
        totalTurnover.setLastMonthDay(queryLastMonthDay(hotelId, date).intValue() != 0 ? df.format(queryLastMonthDay(hotelId, date))+"" : "0.00");

        //计算本年累计营业额
        totalTurnover.setYear(queryYear(hotelId, yyyy).intValue() != 0 ? df.format(queryYear(hotelId, yyyy))+"" : "0.00");

        //计算上年同期营业额
        totalTurnover.setLastYearDay(queryLastYearDay(hotelId, date).intValue() != 0 ? queryLastYearDay(hotelId, date)+"" : "0.00");

        //计算营业额年增长率
        totalTurnover.setInsertRial(isIncrease(queryYear(hotelId, yyyy),queryYear(hotelId, year)));



        //获取当天预订未到房数
        numberOrder.setDay(managerdailyBO.getNumberOrder().intValue() != 0 ? f.format(managerdailyBO.getNumberOrder())+"" : "0");

        //计算本月累计预订未到房数
        numberOrder.setMonth(ydDay(hotelId, date).intValue() != 0 ? f.format(ydDay(hotelId, date))+"" : "0");

        //计算上月同期预订未到房数
        numberOrder.setLastMonthDay(ydLastMonthDay(hotelId, date).intValue() != 0 ? f.format(ydLastMonthDay(hotelId, date))+"" : "0" );

        //计算本年累计预订未到房数
        numberOrder.setYear(ydYear(hotelId,yyyy).intValue() != 0 ? f.format(ydYear(hotelId,yyyy))+"" : "0");

        //计算上年同期预订未到房数
        numberOrder.setLastYearDay(ydLastYearDay(hotelId, date).intValue() != 0 ? f.format(ydLastYearDay(hotelId, date))+"" : "0");

        //计算预订未到房数年增长率
        numberOrder.setInsertRial(isIncrease(ydYear(hotelId,yyyy),ydYear(hotelId,year)));


        //获取当天维修房数
        maintenanceroomNumber.setDay(managerdailyBO.getMaintenanceroomNumber().intValue() != 0 ? f.format(managerdailyBO.getMaintenanceroomNumber())+"" : "0");

        //计算本月累计维修房数
        maintenanceroomNumber.setMonth(wxMonth(hotelId, date).intValue() != 0 ? f.format(wxMonth(hotelId, date))+"" : "0");

        //计算上月同期维修房数
        maintenanceroomNumber.setLastMonthDay(wxLastMonthDay(hotelId, date).intValue() != 0 ? f.format(wxLastMonthDay(hotelId, date))+"" : "0");

        //计算本年累计维修房数
        maintenanceroomNumber.setYear(wxYear(hotelId, yyyy).intValue() != 0 ? f.format(wxYear(hotelId, yyyy))+"" : "0");

        //计算上年同期维修房数
        maintenanceroomNumber.setLastYearDay(wxLastYearDay(hotelId, date).intValue() != 0 ? f.format(wxLastYearDay(hotelId, date))+"" : "0");

        //计算预订维修房数年增长率
        maintenanceroomNumber.setInsertRial(isIncrease(wxYear(hotelId, yyyy),wxYear(hotelId, year)));



        //获取当天门店锁房数
        numberlockedStores.setDay(managerdailyBO.getNumberlockedStores().intValue() !=0 ? f.format(managerdailyBO.getNumberlockedStores())+"" : "0");

        //计算本月累计门店锁房数
        numberlockedStores.setMonth(sfMonth(hotelId,date).intValue() != 0  ? f.format(sfMonth(hotelId,date))+"" : "0");

        //计算上月同期门店锁房数
        numberlockedStores.setLastMonthDay(sfLastMonthDay(hotelId, date).intValue() != 0 ? f.format(sfLastMonthDay(hotelId, date))+"" : "0");

        //计算本年累计门店锁房数
        numberlockedStores.setYear(sfYear(hotelId, yyyy).intValue() != 0 ? f.format(sfYear(hotelId, yyyy))+"" : "0");

        //计算上年同期门店锁房数
        numberlockedStores.setLastYearDay(sfLastYearDay(hotelId, date).intValue() != 0 ? f.format(sfLastYearDay(hotelId, date))+"" : "0");

        //计算门店锁房数年增长率
        numberlockedStores.setInsertRial(isIncrease(sfYear(hotelId, yyyy),sfYear(hotelId, year)));




        //获取当天可出租房数
        numberroomsAvailablerent.setDay(managerdailyBO.getNumberroomsAvailablerent().intValue() != 0 ? f.format(managerdailyBO.getNumberroomsAvailablerent())+"" : "0");

        //获取本月可出租房数
        numberroomsAvailablerent.setMonth(zfMonth(hotelId, date).intValue() != 0 ? f.format(zfMonth(hotelId, date))+"":"0");

        //获取上月同期可出租房数
        numberroomsAvailablerent.setLastMonthDay(zfLastMonthDay(hotelId, date).intValue() != 0 ? f.format(zfLastMonthDay(hotelId, date))+"" : "0");

        //获取本年累计可出租房数
        numberroomsAvailablerent.setYear(zfYear(hotelId, yyyy).intValue() != 0 ? f.format(zfYear(hotelId, yyyy))+"": "0");

        //获取上年同期可出租房数
        numberroomsAvailablerent.setLastYearDay(zfLastYearDay(hotelId, date).intValue() != 0 ? f.format(zfLastYearDay(hotelId, date))+"" : "0");

        //获取可出租房数年增长率
        numberroomsAvailablerent.setInsertRial(isIncrease(zfYear(hotelId, yyyy),zfYear(hotelId, year)));


        //获取当天客房房数

        totalnumberGuestrooms.setDay(managerdailyBO.getTotalnumberGuestrooms().intValue() != 0 ? f.format(managerdailyBO.getTotalnumberGuestrooms())+"" : "0");

        //获取本月累计客房总数
        BigDecimal kfzsyue = kfMonth(hotelId, date);
        totalnumberGuestrooms.setMonth(kfzsyue.intValue() != 0 ? f.format(kfzsyue)+"" : "0");

        //获取上月同期客房总数
        totalnumberGuestrooms.setLastMonthDay(kfLastMonthDay(hotelId, date).intValue() != 0 ? f.format(kfLastMonthDay(hotelId, date))+"" : "0");

        //获取本年累计客房总数
        BigDecimal kfzsnian = kfYear(hotelId, yyyy);
        totalnumberGuestrooms.setYear(kfzsnian.intValue() != 0 ? f.format(kfzsnian)+"" : "0");

        //获取上年同期客房总数
        totalnumberGuestrooms.setLastYearDay(kfLastYearDay(hotelId, date).intValue() != 0 ?  f.format(kfLastYearDay(hotelId, date)) + "" : "0");

        //获取客房总数年增长率
        totalnumberGuestrooms.setInsertRial(isIncrease(kfYear(hotelId, yyyy),kfYear(hotelId, year)));


        //获取当天现金支出
        cashDisbursements.setDay(managerdailyBO.getCashDisbursements().intValue() != 0 ? df.format(managerdailyBO.getCashDisbursements())+"" : "0.00");

        //获取本月累计现金支出
        cashDisbursements.setMonth(zcMonth(hotelId, date).intValue() != 0 ? df.format(zcMonth(hotelId, date))+"" : "0.00");

        //获取上月同期现金支出
        cashDisbursements.setLastMonthDay(zcLastMonthDay(hotelId, date).intValue() != 0 ? df.format(zcLastMonthDay(hotelId, date))+"" : "0.00");

        //获取本年累计现金支出
        cashDisbursements.setYear(zcYear(hotelId, yyyy).intValue() != 0 ? df.format(zcYear(hotelId, yyyy)) + "" : "0.00");

        //获取上年同期现金支出
        cashDisbursements.setLastYearDay(zcLastYearDay(hotelId, date).intValue() != 0 ? df.format(zcLastYearDay(hotelId, date))+"" : "0.00");

        //获取现金支出年增长率
        cashDisbursements.setInsertRial(isIncrease(zcYear(hotelId, yyyy),zcYear(hotelId, year)));


        //获取当天现金收入
        cash.setDay(managerdailyBO.getCash().intValue() != 0 ? df.format(managerdailyBO.getCash())+"" : "0.00");

        //获取本月累计现金收入
        cash.setMonth(srMonth(hotelId, date).intValue() != 0 ? df.format(srMonth(hotelId, date))+"" : "0.00");

        //获取上月同期现金收入
        cash.setLastMonthDay(srLastMonthDay(hotelId, date).intValue() != 0 ? df.format(srLastMonthDay(hotelId, date))+"" : "0.00");

        //获取本年累计现金收入
        cash.setYear(srYear(hotelId, yyyy).intValue() != 0 ? df.format(srYear(hotelId, yyyy))+"" : "0.00");

        //获取上年同期现金收入
        cash.setLastYearDay(srLastYearDay(hotelId, date).intValue() != 0 ? df.format(srLastYearDay(hotelId, date))+"" : "0.00");

        //获取现金收入年增长率
        cash.setInsertRial(isIncrease(srYear(hotelId, yyyy),srYear(hotelId, year)));



        //营业收入明细
        //获取全天日租今日发生
        throughoutDayrent.setDay(managerdailyBO1.getThroughoutDayrent().intValue() != 0 ? df.format(managerdailyBO1.getThroughoutDayrent())+"" : "0.00");

        //获取全天日租本月累计
        throughoutDayrent.setMonth(qtrzMonth(hotelId, date).intValue() != 0 ? df.format(qtrzMonth(hotelId, date))+"" : "0.00");

        //获取全天日租上月同期
        throughoutDayrent.setLastMonthDay(qtrzLastMonthDay(hotelId, date).intValue() != 0 ? df.format(qtrzLastMonthDay(hotelId, date))+ "":"0.00");

        //获取全天日租本年积累
        throughoutDayrent.setYear(qtrzYear(hotelId, yyyy).intValue() != 0 ? df.format(qtrzYear(hotelId, yyyy))+"" : "0.00");

        //获取全天日租上年同期
        throughoutDayrent.setLastYearDay(qtrzLastYearDay(hotelId, date).intValue() != 0 ? df.format(qtrzLastYearDay(hotelId, date))+"" : "0.00");

        //获取全天日租年增率
        throughoutDayrent.setInsertRial(isIncrease(qtrzYear(hotelId, yyyy),qtrzYear(hotelId, year)));



        //房费调整今日发生
        rateAdjustment.setDay(managerdailyBO1.getRateAdjustment().intValue() != 0 ? df.format(managerdailyBO1.getRateAdjustment())+"" : "0.00");

        //房费调整本月累计
        rateAdjustment.setMonth(fftzMonth(hotelId, date).intValue() != 0 ? df.format(fftzMonth(hotelId, date)) + "" : "0.00");

        //房费调整上月同期
        rateAdjustment.setLastMonthDay(fftzLastMonthDay(hotelId, date).intValue() != 0 ? df.format(fftzLastMonthDay(hotelId, date))+"" : "0.00");

        //房费调整本年累计
        rateAdjustment.setYear(fftzYear(hotelId, yyyy).intValue() != 0 ? df.format(fftzYear(hotelId, yyyy)) +"" : "0.00");

        //房费调整上年同期
        rateAdjustment.setLastYearDay(fftzLastYearDay(hotelId, date).intValue() != 0 ? df.format(fftzLastYearDay(hotelId, date))+ "" :"0.00");

        //房费调整年增长率
        rateAdjustment.setInsertRial(isIncrease(fftzYear(hotelId, yyyy),fftzYear(hotelId, year)));


        //钟点房费今日发生
        hourRate.setDay(managerdailyBO1.getHourRate().intValue() != 0 ? df.format(managerdailyBO1.getHourRate())+"" : "0.00");

        //钟点房费本月累计
        hourRate.setMonth(zdffMonth(hotelId, date).intValue() != 0 ? df.format(zdffMonth(hotelId, date)) + "":"0.00");

        //钟点房费上月同期
        hourRate.setLastMonthDay(zdffLastMonthDay(hotelId, date).intValue() != 0 ? df.format(zdffLastMonthDay(hotelId, date)) +"":"0.00");

        //钟点房费本年累计
        hourRate.setYear(zdffYear(hotelId, yyyy).intValue() != 0 ? df.format(zdffYear(hotelId, yyyy)) + "" : "0.00");

        //钟点房费上年同期
        hourRate.setLastYearDay(zdffLastYearDay(hotelId, date).intValue() != 0 ? df.format(zdffLastYearDay(hotelId, date))+"" :"0.00") ;

        //钟点房费年增长率
        hourRate.setInsertRial(isIncrease(zdffYear(hotelId, yyyy),zdffYear(hotelId, year)));



        //超时房费今日发生
        timeoutRate.setDay(managerdailyBO1.getTimeoutRate().intValue() != 0 ? df.format(managerdailyBO1.getTimeoutRate())+"":"0.00");

        //超时房费本月累计
        timeoutRate.setMonth(csffMonth(hotelId, date).intValue()!=0 ?  df.format(csffMonth(hotelId, date))+ "" : "0.00");

        //超时房费上月同期
        timeoutRate.setLastMonthDay(csffLastMonthDay(hotelId, date).intValue() != 0  ? df.format(csffLastMonthDay(hotelId, date))+"" : "0.00");

        //超时房费本年累计
        timeoutRate.setYear(csffYear(hotelId, yyyy).intValue() !=0 ? df.format(csffYear(hotelId, yyyy))+"":"0.00");

        //超时房费上年同期
        timeoutRate.setLastYearDay(csffLastYearDay(hotelId, date).intValue() != 0 ? df.format(csffLastYearDay(hotelId, date))+"":"0.00");

        //超时房费年增长率
        timeoutRate.setInsertRial(isIncrease(csffYear(hotelId, yyyy),csffYear(hotelId, year)));



        //夜核房费今日发生
        nuclearnightRoomcharge.setDay(managerdailyBO1.getNuclearnightRoomcharge().intValue()!= 0 ? df.format(managerdailyBO1.getNuclearnightRoomcharge())+"" : "0.00");

        //夜核房费本月累计
        nuclearnightRoomcharge.setMonth(yhffMonth(hotelId, date).intValue() !=0 ? df.format(yhffMonth(hotelId, date)) + "" : "0.00");

        //夜核房费上月同期
        nuclearnightRoomcharge.setLastMonthDay(yhffLastMonthDay(hotelId, date).intValue() != 0 ? df.format(yhffLastMonthDay(hotelId, date)) + "":"0.00");

        //夜核房费本年累计
        nuclearnightRoomcharge.setYear(yhffYear(hotelId, yyyy).intValue() != 0 ? df.format(yhffYear(hotelId, yyyy)) +"" : "0.00");

        //夜核房费上年同期
        nuclearnightRoomcharge.setLastYearDay(yhffLastYearDay(hotelId, date).intValue() != 0 ? df.format(yhffLastYearDay(hotelId, date))+"" : "0.00");

        //夜核房费年增长率
        nuclearnightRoomcharge.setInsertRial(isIncrease(yhffYear(hotelId, yyyy),yhffYear(hotelId, year)));



        //赔偿今日发生
        compensation.setDay(managerdailyBO1.getCompensation().intValue() != 0 ? df.format(managerdailyBO1.getCompensation())+"" : "0.00");

        //赔偿本月累计
        compensation.setMonth(pcMonth(hotelId, date).intValue() != 0 ? df.format(pcMonth(hotelId, date))+"" : "0.00");

        //赔偿上月同期
        compensation.setLastMonthDay(pcLastMonthDay(hotelId, date).intValue() != 0 ? df.format(pcLastMonthDay(hotelId, date)) + "" : "0.00");

        //赔偿本年累计
        compensation.setYear(pcYear(hotelId, yyyy).intValue() != 0 ? df.format(pcYear(hotelId, yyyy))+"":"0.00");

        //赔偿上年同期
        compensation.setLastYearDay(pcLastYearDay(hotelId, date).intValue() != 0 ? df.format(pcLastYearDay(hotelId, date)) +"" : "0.00");

        //赔偿年增长率
        compensation.setInsertRial(isIncrease(pcYear(hotelId, yyyy),pcYear(hotelId, year)));



        //会员卡费今日发生
        membershipFee.setDay(managerdailyBO1.getMembershipFee().intValue() != 0 ? df.format(managerdailyBO1.getMembershipFee())+"":"0.00");

        //会员卡费本月累计
        membershipFee.setMonth(hykfMonth(hotelId, date).intValue() != 0 ? df.format(hykfMonth(hotelId, date))+"" : "0.00");

        //会员卡费上月同期
        membershipFee.setLastMonthDay(hykfLastMonthDay(hotelId, date).intValue() != 0 ? df.format(hykfLastMonthDay(hotelId, date))+"" : "0.00");

        //会员卡费本年累计
        membershipFee.setYear(hykfYear(hotelId, yyyy).intValue() != 0 ? df.format(hykfYear(hotelId, yyyy))+"" : "0.00");

        //会员卡费上年同期
        membershipFee.setLastYearDay(hykfLastYearDay(hotelId, date).intValue() != 0 ? df.format(hykfLastYearDay(hotelId, date))+"" : "0.00");

        //会员卡费年增长率
        membershipFee.setInsertRial(isIncrease(hykfYear(hotelId, yyyy),hykfYear(hotelId, year)));


        //商品今日发生
        goods.setDay(managerdailyBO1.getGoods().intValue() != 0 ? df.format(managerdailyBO1.getGoods())+"" : "0.00");

        //商品本月累计
        goods.setMonth(spMonth(hotelId, date).intValue() != 0 ? df.format(spMonth(hotelId, date))+"" : "0.00");

        //商品上月同期
        goods.setLastMonthDay(spLastMonthDay(hotelId, date).intValue() != 0 ? df.format(spLastMonthDay(hotelId, date))+"" : "0.00");

        //商品本年累计
        goods.setYear(spYear(hotelId, yyyy).intValue() != 0 ? df.format(spYear(hotelId, yyyy))+"" : "0.00");

        //商品上年同期
        goods.setLastYearDay(spLastYearDay(hotelId, date).intValue() != 0 ? df.format(spLastYearDay(hotelId, date))+"":"0.00");

        //商品年增长率
        goods.setInsertRial(isIncrease(spYear(hotelId, yyyy),spYear(hotelId, year)));



        //当天小计
        subtotal2.setDay(managerdailyBO1.getSubtotal().intValue() != 0 ? df.format(managerdailyBO1.getSubtotal())+"" : "0.00");

        //小计本月累计
        subtotal2.setMonth(xjMonth(hotelId, date).intValue() != 0 ? df.format(xjMonth(hotelId, date))+"":"0.00");

        //小计上月同期
        subtotal2.setLastMonthDay(xjLastMonthDay(hotelId, date).intValue() != 0 ? df.format(xjLastMonthDay(hotelId, date))+"":"0.00");

        //小计本年累计
        subtotal2.setYear(xjYear(hotelId, yyyy).intValue() != 0 ? df.format(xjYear(hotelId, yyyy))+"" : "0.00");

        //小计上年同期
        subtotal2.setLastYearDay(xjLastYearDay(hotelId, date).intValue() != 0 ? df.format(xjLastYearDay(hotelId, date))+"" : "0.00");

        //小计年增长率
        subtotal2.setInsertRial(isIncrease(xjYear(hotelId, yyyy),xjYear(hotelId, year)));


        //房费收入分析
        //会员今日
        members3.setDay(managerdailyBO2.getMembers().intValue() != 0 ? df.format(managerdailyBO2.getMembers())+"" : "0.00");

        //会员本月累计
        members3.setMonth(hyMonth(hotelId,date).intValue() != 0 ? df.format(hyMonth(hotelId,date))+"":"0.00");

        //会员上月同期
        members3.setLastMonthDay(hyLastMonthDay(hotelId, date).intValue() != 0 ? df.format(hyLastMonthDay(hotelId, date))+"":"0.00");

        //会员本年累计
        members3.setYear(hyYear(hotelId, yyyy).intValue() != 0 ? df.format(hyYear(hotelId, yyyy))+"":"0.00");

        //会员上年同期
        members3.setLastYearDay(hyLastYearDay(hotelId, date).intValue() != 0 ? df.format(hyLastYearDay(hotelId, date))+"" : "0.00");

        //会员年增长率
        members3.setInsertRial(isIncrease(hyYear(hotelId, yyyy),hyYear(hotelId, year)));



        //散客今日
        individualTraveler3.setDay(managerdailyBO2.getIndividualTraveler().intValue() != 0 ? df.format(managerdailyBO2.getIndividualTraveler())+"" : "0.00");

        //散客本月累计
        individualTraveler3.setMonth(skMonth(hotelId, date).intValue()  != 0 ? df.format(skMonth(hotelId, date))+"" : "0.00");

        //散客上月同期
        individualTraveler3.setLastMonthDay(skLastMonthDay(hotelId, date).intValue() != 0 ? df.format(skLastMonthDay(hotelId, date))+"" : "0.00");

        //散客本年累计
        individualTraveler3.setYear(skYear(hotelId, yyyy).intValue() != 0 ? df.format(skYear(hotelId, yyyy))+"" :"0.00");

        //散客上年同期
        individualTraveler3.setLastYearDay(skLastYearDay(hotelId, date).intValue() != 0 ? df.format(skLastYearDay(hotelId, date))+"":"0.00");

        //散客年增长率
        individualTraveler3.setInsertRial(isIncrease(skYear(hotelId, yyyy),skYear(hotelId, year)));


        //协议单位今日
        agreementUnit3.setDay(managerdailyBO2.getAgreementUnit().intValue() != 0 ? df.format(managerdailyBO2.getAgreementUnit())+"":"0.00");

        //协议单位本月累计
        agreementUnit3.setMonth(xydwMonth(hotelId, date).intValue() != 0 ? df.format(xydwMonth(hotelId, date))+"":"0.00");

        //协议单位上月同期
        agreementUnit3.setLastMonthDay(xydwLastMonthDay(hotelId, date).intValue() != 0 ? df.format(xydwLastMonthDay(hotelId, date))+"":"0.00");

        //协议单位本年累计
        agreementUnit3.setYear(xydwYear(hotelId,yyyy).intValue() != 0 ? df.format(xydwYear(hotelId,yyyy))+"" : "0.00");

        //协议单位上年同期
        agreementUnit3.setLastYearDay(xydwLastYearDay(hotelId, date).intValue() != 0 ? df.format(xydwLastYearDay(hotelId, date))+"" : "0.00");

        //协议单位年增长率
        agreementUnit3.setInsertRial(isIncrease(xydwYear(hotelId,yyyy),xydwYear(hotelId,year)));



        //直接入住今日
        enter3.setDay(managerdailyBO2.getEnter().intValue() != 0 ? df.format(managerdailyBO2.getEnter())+"" : "0.00");

        //直接入住本月累计
        enter3.setMonth(zjrzMonth(hotelId, date).intValue() != 0 ? df.format(zjrzMonth(hotelId, date))+"" : "0.00");

        //直接入住上月同期
        enter3.setLastMonthDay(zjrzLastMonthDay(hotelId, date).intValue() != 0 ? df.format(zjrzLastMonthDay(hotelId, date))+"" : "0.00");

        //直接入住本年累计
        enter3.setYear(zjrzYear(hotelId, yyyy).intValue() != 0 ? df.format(zjrzYear(hotelId, yyyy))+"" : "0.00");

        //直接入住上年同期
        enter3.setLastYearDay(zjrzLastYearDay(hotelId, date).intValue() != 0 ? df.format(zjrzLastYearDay(hotelId, date))+"" : "0.00");

        //直接入住年增长率
        enter3.setInsertRial(isIncrease(zjrzYear(hotelId, yyyy),zjrzYear(hotelId, year)));



        //预约入住今日
        directBooking3.setDay(managerdailyBO2.getDirectBooking().intValue() != 0 ? df.format(managerdailyBO2.getDirectBooking())+"" : "0.00");

        //预约入住本月累计
        directBooking3.setMonth(yyrzMonth(hotelId, date).intValue() != 0 ? df.format(yyrzMonth(hotelId, date))+"":"0.00");

        //预约入住上月同期
        directBooking3.setLastMonthDay(yyrzLastMonthDay(hotelId, date).intValue() != 0 ? df.format(yyrzLastMonthDay(hotelId, date))+"" : "0.00");

        //预约入住本年累计
        directBooking3.setYear(yyrzYear(hotelId, yyyy).intValue() != 0 ? df.format(yyrzYear(hotelId, yyyy))+"" : "0.00");

        //预约入住上年同期
        directBooking3.setLastYearDay(yyrzLastYearDay(hotelId, date).intValue() != 0 ? df.format(yyrzLastYearDay(hotelId, date))+"":"0.00");

        //预约入住年增长率
        directBooking3.setInsertRial(isIncrease(yyrzYear(hotelId, yyyy),yyrzYear(hotelId, year)));



        //小计今日
        subtotal3.setDay(managerdailyBO2.getSubtotal().intValue() != 0 ? df.format(managerdailyBO2.getSubtotal())+"" : "0.00");

        //小计本月累计
        subtotal3.setMonth(xj2Month(hotelId, date).intValue() != 0 ? df.format(xj2Month(hotelId, date))+"":"0.00");

        //小计上月同期
        subtotal3.setLastMonthDay(xj2LastMonthDay(hotelId, date).intValue() != 0 ? df.format(xj2LastMonthDay(hotelId, date))+"" : "0.00");

        //小计本年累计
        subtotal3.setYear(xj2Year(hotelId, yyyy).intValue() != 0 ? df.format(xj2Year(hotelId, yyyy))+"":"0.00");

        //小计上年同期
        subtotal3.setLastYearDay(xj2LastYearDay(hotelId, date).intValue() != 0 ? df.format(xj2LastYearDay(hotelId, date))+"" : "0.00");

        //小计年增长率
        subtotal3.setInsertRial(isIncrease(xj2Year(hotelId, yyyy),xj2Year(hotelId, year)));

        //房晚数分析
        //会员今日
        members4.setDay(f.format(managerdailyBO3.getMembers())+"");

        //会员本月累计
        BigDecimal bigDecimal = hy2Month(hotelId, date);
        members4.setMonth(f.format(bigDecimal)+"");

        //会员上月同期
        members4.setLastMonthDay(f.format(hy2LastMonthDay(hotelId, date))+"");

        //会员本年累计
        BigDecimal bigDecimal1 = hy2Year(hotelId, yyyy);
        members4.setYear(f.format(bigDecimal1)+"");

        //会员上年同期
        members4.setLastYearDay(f.format(hy2LastYearDay(hotelId, date))+"");

        //会员年增长率
        members4.setInsertRial(isIncrease(hy2Year(hotelId, yyyy),hy2Year(hotelId, year)));


        //散客今日
        individualTraveler4.setDay(f.format(managerdailyBO3.getIndividualTraveler())+"");

        //散客本月累计
        BigDecimal bigDecimal2 = sk2Month(hotelId, date);
        individualTraveler4.setMonth(f.format(bigDecimal2)+"");

        //散客上月同期
        individualTraveler4.setLastMonthDay(f.format(sk2LastMonthDay(hotelId, date))+"");

        //散客本年累计
        BigDecimal bigDecimal3 = sk2Year(hotelId, yyyy);
        individualTraveler4.setYear(f.format(bigDecimal3)+"");

        //散客上年同期
        individualTraveler4.setLastYearDay(f.format(sk2LastYearDay(hotelId, date))+"");

        //散客年增长率
        individualTraveler4.setInsertRial(isIncrease(sk2Year(hotelId, yyyy),sk2Year(hotelId, year)));



        //协议单位今日
        agreementUnit4.setDay(f.format(managerdailyBO3.getAgreementUnit())+"");

        //协议单位本月累计
        BigDecimal bigDecimal4 = xydw2Month(hotelId, date);
        agreementUnit4.setMonth(f.format(bigDecimal4)+"");

        //协议单位上月同期
        agreementUnit4.setLastMonthDay(f.format(xydw2LastMonthDay(hotelId, date))+"");

        //协议单位本年累计
        BigDecimal bigDecimal5 = xydw2Year(hotelId, yyyy);
        agreementUnit4.setYear(f.format(bigDecimal5)+"");

        //协议单位上年同期
        agreementUnit4.setLastYearDay(f.format(xydw2LastYearDay(hotelId, date))+"");

        //协议单位年增长率
        agreementUnit4.setInsertRial(isIncrease(xydw2Year(hotelId, yyyy),xydw2Year(hotelId, year)));



        //直接入住今日
        enter4.setDay(f.format(managerdailyBO3.getEnter())+"");

        //直接入住本月累计
        BigDecimal zjrzfwyue = zjrz2Month(hotelId, date);
        enter4.setMonth(f.format(zjrzfwyue)+"");

        //直接入住上月同期
        enter4.setLastMonthDay(f.format(zjrz2LastMonthDay(hotelId, date))+"");

        //直接入住本年累计
        BigDecimal zjrzfwnian = zjrz2Year(hotelId, yyyy);
        enter4.setYear(f.format(zjrz2Year(hotelId, yyyy))+"");

        //直接入住上年同期
        enter4.setLastYearDay(f.format(zjrz2LastYearDay(hotelId, date))+"");

        //直接入住年增长率
        enter4.setInsertRial(isIncrease(zjrz2Year(hotelId, yyyy),zjrz2Year(hotelId, year)));



        //房间预订今日
        directBooking4.setDay(f.format(managerdailyBO3.getDirectBooking())+"");

        //房间预订本月累计
        BigDecimal yyrzfwyue = fjyd2Month(hotelId, date);
        directBooking4.setMonth(f.format(yyrzfwyue)+"");

        //房间预订上月同期
        directBooking4.setLastMonthDay(f.format(fjyd2LastMonthDay(hotelId, date))+"");

        //房间预订本年累计
        BigDecimal yyrzfwnian = fjyd2Year(hotelId, yyyy);
        directBooking4.setYear(f.format(fjyd2Year(hotelId, yyyy))+"");

        //房间预订上年同期
        directBooking4.setLastYearDay(f.format(fjyd2LastYearDay(hotelId, date))+"");

        //房间预订年增长率
        directBooking4.setInsertRial(isIncrease(fjyd2Year(hotelId, yyyy),fjyd2Year(hotelId, year)));



        //小计今日
        subtotal4.setDay(f.format(managerdailyBO3.getSubtotal())+"");

        //小计本月累计
        BigDecimal yuexj = xj3Month(hotelId, date);
        subtotal4.setMonth(f.format(yuexj)+"");

        //小计上月同期
        subtotal4.setLastMonthDay(f.format(xj3LastMonthDay(hotelId, date))+"");

        //小计本年累计
        BigDecimal nianxj = xj3Year(hotelId, yyyy);
        subtotal4.setYear(f.format(xj3Year(hotelId, yyyy))+"");

        //小计上年同期
        subtotal4.setLastYearDay(f.format(xj3LastYearDay(hotelId, date))+"");

        //小计年增长率
        subtotal4.setInsertRial(isIncrease(xj3Year(hotelId, yyyy),xj3Year(hotelId, year)));




        //平均房价分析
        //会员房价今日分析
        members5.setDay(managerdailyBO4.getMembers().intValue() != 0 ? df.format(managerdailyBO4.getMembers())+"" : "0.00");

        //会员本月累计
        members5.setMonth(hyfj2Month(hotelId, date).intValue() != 0 ? df.format(hyfj2Month(hotelId, date))+"" : "0.00");

        //会员上月同期
        members5.setLastMonthDay(hyfj2LastMonthDay(hotelId, date).intValue() != 0 ?  df.format(hyfj2LastMonthDay(hotelId, date))+"" : "0.00");

        //会员本年年累计
        members5.setYear(hyfj2Year(hotelId, yyyy).intValue() != 0 ? df.format(hyfj2Year(hotelId, yyyy))+"" : "0.00");

        //会员上年同期
        members5.setLastYearDay(hyfj2LastYearDay(hotelId, date).intValue() != 0 ? df.format(hyfj2LastYearDay(hotelId, date))+"" : "0.00");

        //会员年增长率
        members5.setInsertRial(isIncrease(hyfj2Year(hotelId, yyyy),hyfj2Year(hotelId, year)));



        //散客今日
        individualTraveler5.setDay(managerdailyBO4.getIndividualTraveler().intValue() != 0 ? df.format(managerdailyBO4.getIndividualTraveler())+"" : "0.00");

        //散客本月累计
        individualTraveler5.setMonth(sk3Month(hotelId, date).intValue() != 0 ? df.format(sk3Month(hotelId, date))+"" : "0.00");

        //散客上月同期
        individualTraveler5.setLastMonthDay(sk3LastMonthDay(hotelId, date).intValue() !=  0 ? df.format(sk3LastMonthDay(hotelId, date))+"" : "0.00");

        //散客本年年累计
        individualTraveler5.setYear(sk3Year(hotelId, yyyy).intValue() != 0 ? df.format(sk3Year(hotelId, yyyy))+"" : "0.00");

        //散客上年同期
        individualTraveler5.setLastYearDay(sk3LastYearDay(hotelId, date).intValue() != 0 ? df.format(sk3LastYearDay(hotelId, date))+"" : "0.00");

        //散客年增长率
        individualTraveler5.setInsertRial(isIncrease(sk3Year(hotelId, yyyy),sk3Year(hotelId, year)));



        //协议单位今日
        agreementUnit5.setDay(managerdailyBO4.getAgreementUnit().intValue() != 0 ? df.format(managerdailyBO4.getAgreementUnit())+"":"0.00");

        //协议单位本月累计
        agreementUnit5.setMonth(xydw3Month(hotelId, date).intValue() != 0 ? df.format(xydw3Month(hotelId, date))+"":"0.00");

        //协议单位上月同期
        agreementUnit5.setLastMonthDay(xydw3LastMonthDay(hotelId, date).intValue() != 0 ? df.format(xydw3LastMonthDay(hotelId, date))+"" : "0.00");

        //协议单位本年年累计
        agreementUnit5.setYear(xydw3Year(hotelId, yyyy).intValue() != 0 ? df.format(xydw3Year(hotelId, yyyy))+"" : "0.00");

        //协议单位上年同期
        agreementUnit5.setLastYearDay(xydw3LastYearDay(hotelId, date).intValue() != 0 ? df.format(xydw3LastYearDay(hotelId, date))+"" : "0.00");

        //协议单位年增长率
        agreementUnit5.setInsertRial(isIncrease(xydw3Year(hotelId, yyyy),xydw3Year(hotelId, year)));




        //直接入住今日
        enter5.setDay(managerdailyBO4.getEnter().intValue() != 0 ? df.format(managerdailyBO4.getEnter())+"" : "0.00");

        //直接入住本月累计
        enter5.setMonth(zjrz3Month(hotelId, date).intValue() != 0 ? df.format(zjrz3Month(hotelId, date))+"" : "0.00");

        //直接入住上月同期
        enter5.setLastMonthDay(zjrz3LastMonthDay(hotelId, date).intValue() != 0 ? df.format(zjrz3LastMonthDay(hotelId, date))+"" : "0.00");

        //直接入住本年年累计
        enter5.setYear(zjrz3Year(hotelId, yyyy).intValue() != 0 ? df.format(zjrz3Year(hotelId, yyyy))+"" : "0.00");

        //直接入住上年同期
        enter5.setLastYearDay(zjrz3LastYearDay(hotelId, date).intValue() != 0 ? df.format(zjrz3LastYearDay(hotelId, date))+"" : "0.00");

        //直接入住年增长率
        enter5.setInsertRial(isIncrease(zjrz3Year(hotelId, yyyy),zjrz3Year(hotelId, year)));


        //房间预订今日
        directBooking5.setDay(managerdailyBO4.getDirectBooking().intValue() != 0 ? df.format(managerdailyBO4.getDirectBooking())+"" : "0.00");

        //房间预订本月累计
        directBooking5.setMonth(fjyd3Month(hotelId,date).intValue() != 0 ? df.format(fjyd3Month(hotelId,date))+"" : "0.00");

        //房间预订上月同期
        directBooking5.setLastMonthDay(fjyd3LastMonthDay(hotelId, date).intValue() != 0 ? df.format(fjyd3LastMonthDay(hotelId, date))+"" : "0.00");

        //房间预定本年年累计
        directBooking5.setYear(fjyd3Year(hotelId, yyyy).intValue() != 0 ? df.format(fjyd3Year(hotelId, yyyy))+"":"0.00");

        //房间预定上年同期
        directBooking5.setLastYearDay(fjyd3LastYearDay(hotelId, date).intValue() != 0 ? df.format(fjyd3LastYearDay(hotelId, date))+"" : "0.00");

        //房间预定年增长率
        directBooking5.setInsertRial(isIncrease(fjyd3Year(hotelId, yyyy),fjyd3Year(hotelId, year)));



        //小计今日
        subtotal5.setDay(managerdailyBO4.getSubtotal().intValue() != 0 ? df.format(managerdailyBO4.getSubtotal())+"" : "0.00");

        //小计本月累计
        subtotal5.setMonth(xj4Month(hotelId, date).intValue() != 0 ? df.format(xj4Month(hotelId, date))+"":"0.00");

        //小计上月同期
        subtotal5.setLastMonthDay(xj4LastMonthDay(hotelId, date).intValue() != 0 ? df.format(xj4LastMonthDay(hotelId, date))+"":"0.00");

        //小计本年年累计
        subtotal5.setYear(xj4Year(hotelId, yyyy).intValue() != 0 ? df.format(xj4Year(hotelId, yyyy))+"" : "0.00");

        //小计上年同期
        subtotal5.setLastYearDay(xj4LastYearDay(hotelId, date).intValue() != 0 ? df.format(xj4LastYearDay(hotelId, date))+"" : "0.00");

        //小计年增长率
        subtotal5.setInsertRial(isIncrease(xj4Year(hotelId, yyyy),xj4Year(hotelId, year)));


        //出租率分析
        //会员出租率本天
        members6.setDay(managerdailyBO5.getMembers().intValue() != 0 ? df.format(managerdailyBO5.getMembers())+"%" : "0.00%");

        //本月累计
        //本月会员房数除以本月累计客房总数
        members6.setMonth(kfzsyue.intValue() != 0 ? bigDecimal.divide(kfzsyue,2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"))+"%" : "0.00%");

        //获取上月同期
        members6.setLastMonthDay(hyrzlLastMonthDay(hotelId,date) == null ? "0.00%":df.format(hyrzlLastMonthDay(hotelId,date).getMembers())+"%");
        //获取年会员出租率
        //年会员房数处于本年小计
        members6.setYear( kfzsnian.intValue() != 0 ? (bigDecimal1.divide(kfzsnian,2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")))+"%" : "0.00%");
        //获取上年同期出租率
        members6.setLastYearDay(hyrzLastYearDay(hotelId, date) == null ? "0.00%":hyrzLastYearDay(hotelId, date).getMembers()+"%");

        //年增长率
        members6.setInsertRial(isRentalRate(hotelId, date, yyyy, year));



        //获取散客当天出租率
        individualTraveler6.setDay(managerdailyBO5.getIndividualTraveler().intValue() != 0 ? df.format(managerdailyBO5.getIndividualTraveler())+"%":"0.00%");

        //获取散客本月累计出租率
        individualTraveler6.setMonth(kfzsyue.intValue() != 0 ? bigDecimal2.divide(kfzsyue,2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"))+"%" : "0.00%");

        //获取上月同期
        individualTraveler6.setLastMonthDay(hyrzlLastMonthDay(hotelId, date) == null ? "0.00%":df.format(hyrzlLastMonthDay(hotelId, date).getIndividualTraveler())+"%" );

        //获取本年累计
        individualTraveler6.setYear(kfzsnian.intValue() != 0 ? bigDecimal3.divide(kfzsnian,2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"))+"%" : "0.00%");

        //获取上年同期
        individualTraveler6.setLastYearDay( hyrzLastYearDay(hotelId, date) == null ? "0.00%":hyrzLastYearDay(hotelId, date).getIndividualTraveler()+"%");

        //获取年增长路
        individualTraveler6.setInsertRial(isRenta(hotelId, date, yyyy, year));



        //获取协议单位当天出租率
        agreementUnit6.setDay(managerdailyBO5.getAgreementUnit().intValue() != 0 ? df.format(managerdailyBO5.getAgreementUnit())+"%" : "0.00%");

        //获取协议单位本月出租率
        agreementUnit6.setMonth(kfzsyue.intValue() !=0 ? bigDecimal4.divide(kfzsyue,2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"))+"%" : "0.00%");

        //获取协议单位上月同期
        agreementUnit6.setLastMonthDay(hyrzlLastMonthDay(hotelId, date) == null ? "0.00%":df.format(hyrzlLastMonthDay(hotelId, date).getAgreementUnit())+"%");

        //获取本年累计
        agreementUnit6.setYear(kfzsnian.intValue() != 0 ? bigDecimal5.divide(kfzsnian,2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"))+"%" : "0.00%");

        //获取上年同期
        agreementUnit6.setLastYearDay(hyrzLastYearDay(hotelId, date) == null ? "0.00%":hyrzLastYearDay(hotelId, date).getAgreementUnit()+"%");

        //年增长率
        agreementUnit6.setInsertRial(isRentaXy(hotelId, date, yyyy, year));



        //获取直接入住当天出租率
        enter6.setDay(managerdailyBO5.getEnter().intValue() != 0 ? df.format(managerdailyBO5.getEnter())+"%" : "0.00%");

        //获取本月直接入住出租率
        enter6.setMonth(kfzsyue.intValue() != 0 ? zjrzfwyue.divide(kfzsyue,2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"))+"%" : "0.00%");

        //获取直接入住上月同期
        enter6.setLastMonthDay(hyrzlLastMonthDay(hotelId, date) == null ? "0.00%" :  df.format(hyrzlLastMonthDay(hotelId, date).getEnter())+"%");

        //获取本年累计
        enter6.setYear(kfzsnian.intValue() != 0 ? zjrzfwnian.divide(kfzsnian,2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"))+"%" : "0.00%");

        //获取上年同期
        enter6.setLastYearDay(hyrzLastYearDay(hotelId, date) == null ? "0.00%" : hyrzLastYearDay(hotelId, date).getEnter()+"%");

        //获取年增长率
        enter6.setInsertRial(isRentaZj(hotelId, date, yyyy, year));



        //获取预约入住当天出租率
        directBooking6.setDay(managerdailyBO5.getDirectBooking().intValue() != 0 ? df.format(managerdailyBO5.getDirectBooking())+"%" : "0.00%");

        //获取预约入住本月出租率
        directBooking6.setMonth(kfzsyue.intValue() != 0 ? yyrzfwyue.divide(kfzsyue,2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"))+"%":"0.00%");

        //获取预约入住上月同期
        directBooking6.setLastMonthDay(hyrzlLastMonthDay(hotelId, date) == null ? "0.00%" : df.format(hyrzlLastMonthDay(hotelId, date).getDirectBooking()) +"%");

        //获取预约入住本年累计
        directBooking6.setYear(kfzsnian.intValue() != 0 ? yyrzfwnian.divide(kfzsnian,2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"))+"%" : "0.00%");

        //获取上年同期
        directBooking6.setLastYearDay(hyrzLastYearDay(hotelId, date) == null ? "0.00%": hyrzLastYearDay(hotelId, date).getDirectBooking()+"%");

        //获取年增长率
        directBooking6.setInsertRial(isRentayy(hotelId, date, yyyy, year));


        //小计
        //获取今日
        subtotal6.setDay(managerdailyBO5.getSubtotal().intValue() != 0 ? df.format(managerdailyBO5.getSubtotal())+"%" : "0.00%");

        //获取本月累计 本月累计房晚数 除以 本月累计客房总数
        subtotal6.setMonth( kfzsyue.intValue() != 0 ? yuexj.divide(kfzsyue,2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"))+"%" : "0.00%");

        //获取上月同期
        subtotal6.setLastMonthDay(hyrzlLastMonthDay(hotelId, date) == null ? "0.00%" : df.format(hyrzlLastMonthDay(hotelId, date).getSubtotal())+"%");

        //获取今年年累计  本年累计房晚数 除以 本年累计客房总数
        subtotal6.setYear(kfzsnian.intValue() != 0 ? nianxj.divide(kfzsnian,2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"))+"%" : "0.00%");

        //获取上年同期
        subtotal6.setLastYearDay(hyrzLastYearDay(hotelId, date) == null ? "0.00%": hyrzLastYearDay(hotelId, date).getSubtotal()+"%" );




        managerdailyChangeBO.setGrossrealIncome(grossrealIncome);

        managerdailyChangeBO.setTotalTurnover(totalTurnover);

        managerdailyChangeBO.setNumberOrder(numberOrder);

        managerdailyChangeBO.setMaintenanceroomNumber(maintenanceroomNumber);

        managerdailyChangeBO.setNumberlockedStores(numberlockedStores);

        managerdailyChangeBO.setNumberroomsAvailablerent(numberroomsAvailablerent);

        managerdailyChangeBO.setTotalnumberGuestrooms(totalnumberGuestrooms);

        managerdailyChangeBO.setCashDisbursements(cashDisbursements);

        managerdailyChangeBO.setCash(cash);

        //,2:营业收入明细

        managerdailyChangeBO.setThroughoutDayrent(throughoutDayrent);

        managerdailyChangeBO.setRateAdjustment(rateAdjustment);

        managerdailyChangeBO.setHourRate(hourRate);

        managerdailyChangeBO.setTimeoutRate(timeoutRate);

        managerdailyChangeBO.setNuclearnightRoomcharge(nuclearnightRoomcharge);

        managerdailyChangeBO.setCompensation(compensation);

        managerdailyChangeBO.setMembershipFee(membershipFee);

        managerdailyChangeBO.setGoods(goods);

        managerdailyChangeBO.setSubtotal2(subtotal2);

        //3:房费收入分析,4:房晚数分析,5:平均房价分析,6:出租率分析

        managerdailyChangeBO.setMembers3(members3);

        managerdailyChangeBO.setAgreementUnit3(agreementUnit3);

        managerdailyChangeBO.setIntermediary3(intermediary3);

        managerdailyChangeBO.setApp3(app3);

        managerdailyChangeBO.setMicroLetter3(microLetter3);

        managerdailyChangeBO.setIndividualTraveler3(individualTraveler3);

        managerdailyChangeBO.setDirectBooking3(directBooking3);

        managerdailyChangeBO.setEnter3(enter3);

        managerdailyChangeBO.setSubtotal3(subtotal3);

        //4:房晚数分析

        managerdailyChangeBO.setMembers4(members4);

        managerdailyChangeBO.setAgreementUnit4(agreementUnit4);

        managerdailyChangeBO.setIntermediary4(intermediary4);

        managerdailyChangeBO.setApp4(app4);

        managerdailyChangeBO.setMicroLetter4(microLetter4);

        managerdailyChangeBO.setIndividualTraveler4(individualTraveler4);

        managerdailyChangeBO.setDirectBooking4(directBooking4);

        managerdailyChangeBO.setEnter4(enter4);

        managerdailyChangeBO.setSubtotal4(subtotal4);

        //,5:平均房价分析

        managerdailyChangeBO.setMembers5(members5);

        managerdailyChangeBO.setAgreementUnit5(agreementUnit5);

        managerdailyChangeBO.setIntermediary5(intermediary5);

        managerdailyChangeBO.setApp5(app5);

        managerdailyChangeBO.setMicroLetter5(microLetter5);

        managerdailyChangeBO.setIndividualTraveler5(individualTraveler5);

        managerdailyChangeBO.setDirectBooking5(directBooking5);

        managerdailyChangeBO.setEnter5(enter5);

        managerdailyChangeBO.setSubtotal5(subtotal5);
        //,6:出租率分析

        managerdailyChangeBO.setMembers6(members6);

        managerdailyChangeBO.setAgreementUnit6(agreementUnit6);

        managerdailyChangeBO.setIntermediary6(intermediary6);

        managerdailyChangeBO.setApp6(app6);

        managerdailyChangeBO.setMicroLetter6(microLetter6);

        managerdailyChangeBO.setIndividualTraveler6(individualTraveler6);

        managerdailyChangeBO.setDirectBooking6(directBooking6);

        managerdailyChangeBO.setEnter6(enter6);

        managerdailyChangeBO.setSubtotal6(subtotal6);

        return managerdailyChangeBO;
    }


    //获取会员出租率年增长率
    //获取今年出租率和去年出租率 用去年减去今年
    //今年出租率 今年累计房晚数/今年累计小计*100
    private String isRentalRate(Integer hotelId, String date, String yyyy, String year){
        if(xj3Year(hotelId, yyyy).intValue() == 0 || xj3Year(hotelId, year).intValue() == 0 ){
            return "0.00%";
        }

        //今年出租率
       BigDecimal j =  hy2Year(hotelId, yyyy).divide(xj3Year(hotelId, yyyy)).multiply(new BigDecimal("100"));
       //去年出租率
        BigDecimal s =  hy2Year(hotelId, year).divide(xj3Year(hotelId, year)).multiply(new BigDecimal("100"));

      return  j.subtract(s)+"%";
    }

    //计算散客年增长出租率
    private String isRenta(Integer hotelId, String date,String yyyy, String year){
        if(xj3Year(hotelId, yyyy).intValue() == 0 || xj3Year(hotelId, year).intValue() == 0 ){
            return "0.00%";
        }

        sk2Year(hotelId, yyyy).divide(xj3Year(hotelId, yyyy)).multiply(new BigDecimal("100"));
        //今年出租率
        BigDecimal j =  sk2Year(hotelId, yyyy).divide(xj3Year(hotelId, yyyy)).multiply(new BigDecimal("100"));
        //去年出租率
        BigDecimal s =  sk2Year(hotelId, year).divide(xj3Year(hotelId, year)).multiply(new BigDecimal("100"));

        return  j.subtract(s)+"%";
    }

    //计算协议单位年增长出租率
    private String isRentaXy(Integer hotelId, String date, String yyyy, String year){
        if(xj3Year(hotelId, yyyy).intValue() == 0 || xj3Year(hotelId, year).intValue() == 0 ){
            return "0.00%";
        }
        //今年出租率
        BigDecimal j =  xydw2Year(hotelId, yyyy).divide(xj3Year(hotelId, yyyy)).multiply(new BigDecimal("100"));
        //去年出租率
        BigDecimal s =  xydw2Year(hotelId, year).divide(xj3Year(hotelId, year)).multiply(new BigDecimal("100"));

        return  j.subtract(s)+"%";
    }

    //计算直接入住年增长出租率
    private String isRentaZj(Integer hotelId, String date, String yyyy, String year){
        if(xj3Year(hotelId, yyyy).intValue() == 0 || xj3Year(hotelId, year).intValue() == 0 ){
            return "0.00%";
        }
        //今年出租率
        BigDecimal j =   zjrz2Year(hotelId, yyyy).divide(xj3Year(hotelId, yyyy)).multiply(new BigDecimal("100"));
        //去年出租率
        BigDecimal s =   zjrz2Year(hotelId, year).divide(xj3Year(hotelId, year)).multiply(new BigDecimal("100"));

        return  j.subtract(s)+"%";
    }

    //计算预约入住年增长率
    private String isRentayy(Integer hotelId, String date, String yyyy, String year){

        if(xj3Year(hotelId, yyyy).intValue() == 0 || xj3Year(hotelId, year).intValue() == 0 ){
            return "0.00%";
        }
        //今年出租率
        BigDecimal j =  fjyd2Year(hotelId, yyyy).divide(xj3Year(hotelId, yyyy)).multiply(new BigDecimal("100"));
        //去年出租率
        BigDecimal s =   fjyd2Year(hotelId, year).divide(xj3Year(hotelId, year)).multiply(new BigDecimal("100"));

        return j.subtract(s) + "%";
    }



    //获取上个月的今天
    private String isDate(Date date){

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.MONTH,-1);
        return sdf.format(cal.getTime());
    }



    //获取指定时间月的第一天,
    public  Date getFirstDayDateOfMonth(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        final int last = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, last);
        return cal.getTime();
    }

    //获取指定时间月的最后一天
    public  Date getLastDayOfMonth(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        final int last = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, last);
        return cal.getTime();
    }

    /**
     * 获取本月总实际收入
     * @param date
     * @return
     */
    private BigDecimal month(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        System.err.println("date1"+date1);
        System.err.println("date2"+dete2);

        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId,new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 1);
        System.err.println(managerdailyBOS);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }

        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            System.err.println(managerdailyBO.getGrossrealIncome());
             n= n.add(managerdailyBO.getGrossrealIncome());
        }
        System.err.println(n);
        return n;
    }


    /**
     * 获取上月同期总实际收入
     * @return
     */
    private BigDecimal  lastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1 , 1);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getGrossrealIncome()) ;
        }
        return n;
    }

    /**
     * 获取总实际收入的本年累计
     * @param hotelId
     * @param date
     * @return
     */
    private BigDecimal year(Integer hotelId, String date){

        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, date, 1);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        System.err.println("本年累计总实际收入");
        System.err.println(date);
        System.err.println(managerdailyBOS);
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {

           n = n.add(managerdailyBO.getGrossrealIncome());
        }
        return n;
    }


    //获取上一年的时间
     private String isYear(Date date){
     Calendar cal = Calendar.getInstance();
     cal.setTime(date);
     cal.add(Calendar.YEAR, 1);//增加一年
     return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
 }

    /**
     * 获取上年同期总收入
     * @param hotelId
     * @param date
     * @return
     */
    private BigDecimal lastYearDay(Integer hotelId, String date) {
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 1);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n = n.add(managerdailyBO.getGrossrealIncome());
        }
        return n;
    }


    /**
     * 获取本月累计营业额
     * @return
     */
    private BigDecimal queryMonth(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 1);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add( managerdailyBO.getTotalTurnover());
        }
        return n;
    }


    /**
     * 获取上月同期总营业额
     * @param hotelId
     * @return
     */
    private BigDecimal queryLastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 1);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }

        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
          n =  n.add(managerdailyBO.getTotalTurnover());
        }
        return n ;
    }

    /**
     * 获取本年积累的总营业额
     * @param hotelId
     * @param
     * @return
     */
    private BigDecimal queryYear(Integer hotelId, String yyyy){
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, yyyy, 1);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }

        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n =  n.add(managerdailyBO.getTotalTurnover());
        }
        return n;
    }

    /**
     * 获取总营业额的去年同期
     * @param hotelId
     * @param date
     * @return
     */
    private BigDecimal queryLastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 1);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
          n =  n.add(managerdailyBO.getGrossrealIncome()) ;
        }
        return n;
    }

    /**
     * 计算本月累计预订未到房数
     * @param hotelId
     * @param date
     * @return
     */
    private BigDecimal ydDay(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 1);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
             n = n.add(managerdailyBO.getNumberOrder()) ;
        }
        return n;
    }

    //获取上月同期 预订未到房数
    private BigDecimal ydLastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 1);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add( managerdailyBO.getNumberOrder());
        }
        return n ;
    }

    //获取本年累计 预订未到房数
    private BigDecimal ydYear(Integer hotelId, String yyyy){
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, yyyy, 1);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }

        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n  = n.add(managerdailyBO.getNumberOrder()) ;
        }
        return n;
    }


    //获取上年同期 预订未到房数
    private BigDecimal ydLastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 1);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n  = n.add( managerdailyBO.getNumberOrder());
        }
        return n;
    }


    //计算本月累计维修房数
    private BigDecimal wxMonth(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 1);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getMaintenanceroomNumber());
        }
        return n;
    }

    //计算上月同期维修房数
    private BigDecimal wxLastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 1);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getMaintenanceroomNumber());
        }
        return n ;
    }

    //计算本年累计维修房数
    private BigDecimal wxYear(Integer hotelId, String yyyy){
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, yyyy, 1);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }

        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getMaintenanceroomNumber());
        }
        return n;
    }

    //上年同期维修房数
    private BigDecimal wxLastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 1);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getMaintenanceroomNumber());
        }
        return n;
    }

    //获取本月累计门店锁房数
    private BigDecimal sfMonth(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 1);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getNumberlockedStores());
        }
        return n;
    }


    //获取上月同期门店锁房数
    private BigDecimal sfLastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 1);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getNumberlockedStores());
        }
        return n ;
    }


    //获取本年累计门店锁房数
    private BigDecimal sfYear(Integer hotelId, String date){
        System.err.println(date);

        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, date, 1);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            System.err.println(managerdailyBO.getNumberlockedStores());
            n = n.add(managerdailyBO.getNumberlockedStores());
        }
        System.err.println("本年累计门店锁房数"+n);
        return n;
    }

    //获取上年同期门店锁房数
    private BigDecimal sfLastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 1);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getNumberlockedStores());
        }
        return n;
    }

    //获取本月可出租房数
    private BigDecimal zfMonth(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 1);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getNumberroomsAvailablerent());
        }
        return n;
    }

    //获取上月同期可出租房数
    private BigDecimal zfLastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 1);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getNumberroomsAvailablerent());
        }
        return n ;
    }

    //获取本年累计可出租房数
    private BigDecimal zfYear(Integer hotelId, String date){
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, date, 1);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getNumberroomsAvailablerent());
        }
        return n;
    }

    //获取上年同期可出租房数
    private BigDecimal zfLastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 1);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getNumberroomsAvailablerent());
        }
        return n;
    }


    //获取本月累计客房总数
    private BigDecimal kfMonth(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 1);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getTotalnumberGuestrooms());
        }
        return n;
    }

    //获取上月同期客房总数
    private BigDecimal kfLastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 1);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getTotalnumberGuestrooms());
        }
        return n ;
    }

    //获取本年累计客房总数
    private BigDecimal kfYear(Integer hotelId, String date){

        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, date, 1);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getTotalnumberGuestrooms());
        }
        return n;
    }

    //获取上年同期客房总数
    private BigDecimal kfLastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 1);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getTotalnumberGuestrooms());
        }
        return n;
    }

    //获取本月累计现金支出
    private BigDecimal zcMonth(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 1);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n =  n.add(managerdailyBO.getCashDisbursements()) ;
        }
        return n;
    }

    //获取上月同期现金支出
    private BigDecimal zcLastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 1);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add( managerdailyBO.getCashDisbursements());
        }
        return n ;
    }

    //获取本年累计现金支出
    private BigDecimal zcYear(Integer hotelId, String date){
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, date, 1);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getCashDisbursements()) ;
        }
        return n;
    }

    //获取上年同期现金支出
    private BigDecimal zcLastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 1);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n =  n.add(managerdailyBO.getCashDisbursements()) ;
        }
        return n;
    }

    //获取本月累计现金收入
    private BigDecimal srMonth(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 1);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n =  n.add( managerdailyBO.getCash());
        }
        return n;
    }

    //获取上月同期现金收入
    private BigDecimal srLastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 1);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n =  n.add(managerdailyBO.getCash()) ;
        }
        return n ;
    }

    //获取本年累计现金收入
    private BigDecimal srYear(Integer hotelId, String date){
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, date, 1);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n =  n.add(managerdailyBO.getCash()) ;
        }
        return n;
    }

    //获取上年同期现金收入
    private BigDecimal srLastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 1);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getCash()) ;
        }
        return n;
    }



    //获取全天日租本月累计
    private BigDecimal qtrzMonth(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 2);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n =  n.add( managerdailyBO.getThroughoutDayrent());
        }
        return n;
    }

    //获取全天日租上月同期
    private BigDecimal qtrzLastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 2);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getThroughoutDayrent()) ;
        }
        return n ;
    }

    //获取全天日租本年积累
    private BigDecimal qtrzYear(Integer hotelId, String date){
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, date, 2);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n =  n.add(managerdailyBO.getThroughoutDayrent()) ;
        }
        return n;
    }


    //获取全天日租上年同期
    private BigDecimal qtrzLastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 2);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getThroughoutDayrent()) ;
        }
        return n;
    }


    //房费调整本月累计
    private BigDecimal fftzMonth(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 2);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n =  n.add(managerdailyBO.getRateAdjustment()) ;
        }
        return n;
    }


    //房费调整上月同期
    private BigDecimal fftzLastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 2);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n =  n.add(managerdailyBO.getRateAdjustment()) ;
        }
        return n ;
    }

    //房费调整本年累计
    private BigDecimal fftzYear(Integer hotelId, String date){
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, date, 2);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n =  n.add(managerdailyBO.getRateAdjustment()) ;
        }
        return n;
    }

    //房费调整上年同期
    private BigDecimal fftzLastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 2);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getRateAdjustment()) ;
        }
        return n;
    }


     //钟点房费本月累计
     private BigDecimal zdffMonth(Integer hotelId, String date){
         //当月第一天
         Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
         //当月最后一天
         Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
         List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                 new SimpleDateFormat("yyyy-MM-dd").format(dete2), 2);
         BigDecimal n = new BigDecimal("0");
         if(CollectionUtils.isEmpty(managerdailyBOS)){
             return n;
         }
         for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
             n = n.add(managerdailyBO.getHourRate()) ;
         }
         return n;
     }

    //钟点房费上月同期
    private BigDecimal zdffLastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 2);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getHourRate()) ;
        }
        return n ;
    }

    //钟点房费本年累计
    private BigDecimal zdffYear(Integer hotelId, String date){
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, date, 2);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n =  n.add( managerdailyBO.getHourRate());
        }
        return n;
    }

    //钟点房费上年同期
    private BigDecimal zdffLastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 2);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n =  n.add( managerdailyBO.getHourRate());
        }
        return n;
    }


    //超时房费本月累计
    private BigDecimal csffMonth(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 2);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getTimeoutRate()) ;
        }
        return n;
    }

    //超时房费上月同期
    private BigDecimal csffLastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 2);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getTimeoutRate()) ;
        }
        return n ;
    }

    //超时房费本年累计
    private BigDecimal csffYear(Integer hotelId, String date){
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, date, 2);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n =  n.add( managerdailyBO.getTimeoutRate());
        }
        return n;
    }

    //超时房费上年同期
    private BigDecimal csffLastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 2);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getTimeoutRate()) ;
        }
        return n;
    }

    //超时房费年增长率


    //夜核房费本月累计
    private BigDecimal yhffMonth(Integer hotelId, String date){
    //当月第一天
    Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
    //当月最后一天
    Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
    List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
            new SimpleDateFormat("yyyy-MM-dd").format(dete2), 2);
    BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
    for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
        n = n.add(managerdailyBO.getNuclearnightRoomcharge()) ;
    }
    return n;
  }
    //夜核房费上月同期
    private BigDecimal yhffLastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 2);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n =  n.add(managerdailyBO.getNuclearnightRoomcharge()) ;
        }
        return n ;
    }

    //夜核房费本年累计
    private BigDecimal yhffYear(Integer hotelId, String date){
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, date, 2);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n =  n.add(managerdailyBO.getNuclearnightRoomcharge()) ;
        }
        return n;
    }

    //夜核房费上年同期
    private BigDecimal yhffLastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 2);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n =  n.add(managerdailyBO.getNuclearnightRoomcharge()) ;
        }
        return n;
    }
    //夜核房费年增长率


    //赔偿本月累计
    private BigDecimal pcMonth(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 2);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
          n =  n.add(managerdailyBO.getCompensation()) ;
        }
        return n;
    }

    //赔偿上月同期
    private BigDecimal pcLastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 2);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
          n =   n.add(managerdailyBO.getCompensation()) ;
        }
        return n ;
    }

    //赔偿上年同期
    private BigDecimal pcYear(Integer hotelId, String date){
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, date, 2);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n =  n.add(managerdailyBO.getCompensation()) ;
        }
        return n;
    }

    //赔偿年增长率
    private BigDecimal pcLastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 2);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getCompensation();
        }
        return n;
    }


    //会员卡费本月累计
    private BigDecimal hykfMonth(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 2);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getMembershipFee()) ;
        }
        return n;
    }

    //会员卡费上月同期
    private BigDecimal hykfLastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 2);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getMembershipFee()) ;
        }
        return n ;
    }

    //会员卡费本年累计
    private BigDecimal hykfYear(Integer hotelId, String date){
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, date, 2);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }

        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getMembershipFee()) ;
        }
        return n;
    }

    ///会员卡费上年同期
    private BigDecimal hykfLastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 2);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getMembershipFee()) ;
        }
        return n;
    }


    //商品本月累计
    private BigDecimal spMonth(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 2);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n  = n.add(managerdailyBO.getGoods()) ;
        }
        return n;
    }


    //商品上月同期
    private BigDecimal spLastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 2);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getGoods()) ;
        }
        return n ;
    }

    //商品本年累计
    private BigDecimal spYear(Integer hotelId, String date){
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, date, 2);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getGoods()) ;
        }
        return n;
    }

    //商品上年同期
    private BigDecimal spLastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 2);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getGoods()) ;
        }
        return n;
    }



    //小计本月累计
    private BigDecimal xjMonth(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        log.info("当月第一天:{}", date1);
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        log.info("当月最后一天:{}", dete2);
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 2);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }

        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getSubtotal()) ;
        }
        return n;
    }

    //小计上月同期
    private BigDecimal xjLastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 2);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getSubtotal());
        }
        return n ;
    }

        //小计本年累计
    private BigDecimal xjYear(Integer hotelId, String date){
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, date, 2);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getSubtotal()) ;
        }
        return n;
    }

    //小计上年同期
    private BigDecimal xjLastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 2);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getSubtotal()) ;
        }
        return n;
    }


    //会员本月累计
    private BigDecimal hyMonth(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 3);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getMembers()) ;
        }
        return n;
    }

    //会员上月同期
    private BigDecimal hyLastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 3);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getMembers()) ;
        }
        return n ;
    }

    //会员本年累计
    private BigDecimal hyYear(Integer hotelId, String date){
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, date, 3);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getMembers()) ;
        }
        return n;
    }

    //会员上年同期
    private BigDecimal hyLastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 3);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }

        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getMembers()) ;
        }
        return n;
    }
    //会员年增长率


    //散客本月累计
    private BigDecimal skMonth(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 3);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getIndividualTraveler()) ;
        }
        return n;
    }

    //散客上月同期
    private BigDecimal skLastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 3);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getIndividualTraveler()) ;
        }
        return n ;
    }

    //散客本年累计
    private BigDecimal skYear(Integer hotelId, String date){
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, date, 3);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getIndividualTraveler()) ;
        }
        return n;
    }

    //散客上年同期
    private BigDecimal skLastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 3);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getIndividualTraveler()) ;
        }
        return n;
    }


    //协议单位本月累计
    private BigDecimal xydwMonth(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 3);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getAgreementUnit()) ;
        }
        return n;
    }

    //协议单位上月同期
    private BigDecimal xydwLastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 3);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n =  n.add(managerdailyBO.getAgreementUnit()) ;
        }
        return n ;
    }

    //协议单位本年累计
    private BigDecimal xydwYear(Integer hotelId, String date){
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, date, 3);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getAgreementUnit()) ;
        }
        return n;
    }

    //协议单位上年同期
    private BigDecimal xydwLastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 3);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add( managerdailyBO.getAgreementUnit());
        }
        return n;
    }



    //直接入住本月累计
    private BigDecimal zjrzMonth(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        log.info("当月第一天:{}", new SimpleDateFormat("yyyy-MM-dd").format(date1));
        log.info("当月最后一天:{}", new SimpleDateFormat("yyyy-MM-dd").format(dete2));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 3);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getEnter()) ;
        }
        return n;
    }

    //直接入住上月同期
    private BigDecimal zjrzLastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 3);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add( managerdailyBO.getEnter());
        }
        return n ;
    }

    //直接入住本年累计
    private BigDecimal zjrzYear(Integer hotelId, String date){
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, date, 3);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getEnter()) ;
        }
        return n;
    }

    //直接入住上年同期
    private BigDecimal zjrzLastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 3);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getEnter()) ;
        }
        return n;
    }


    //预约入住本月累计
    private BigDecimal yyrzMonth(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 3);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getSubtotal()) ;
        }
        return n;
    }

    //预约入住上月同期
    private BigDecimal yyrzLastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 3);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n=  n.add(managerdailyBO.getSubtotal()) ;
        }
        return n ;
    }

    //预约入住本年累计
    private BigDecimal yyrzYear(Integer hotelId, String date){
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, date, 3);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n =  n.add(managerdailyBO.getSubtotal()) ;
        }
        return n;
    }

    //预约入住上年同期
    private BigDecimal yyrzLastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 3);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getSubtotal()) ;
        }
        return n;
    }

    //小计本月累计
    private BigDecimal xj2Month(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 3);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n =  n.add(managerdailyBO.getDirectBooking()) ;
        }
        return n;
    }

    //小计上月同期
    private BigDecimal xj2LastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 3);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n =  n.add( managerdailyBO.getDirectBooking());
        }
        return n ;
    }

    //小计本年累计
    private BigDecimal xj2Year(Integer hotelId, String date){
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, date, 3);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n =  n.add(managerdailyBO.getDirectBooking()) ;
        }
        return n;
    }

    //小计上年同期
    private BigDecimal xj2LastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 3);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getDirectBooking()) ;
        }
        return n;
    }


    //会员本月累计房晚
    private BigDecimal hy2Month(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 4);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getMembers()) ;
        }
        return n;
    }

    //会员上月同期
    private BigDecimal hy2LastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 4);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add( managerdailyBO.getMembers());
        }
        return n ;
    }

    //会员本年累计
    private BigDecimal hy2Year(Integer hotelId, String date){
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, date, 4);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n =  n.add(managerdailyBO.getMembers()) ;
        }
        return n;
    }

    //会员上年同期
    private BigDecimal hy2LastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 4);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n =  n.add(managerdailyBO.getMembers()) ;
        }
        return n;
    }


    //散客本月累计
    private BigDecimal sk2Month(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 4);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n = n.add(managerdailyBO.getIndividualTraveler()) ;
        }
        return n;
    }

    //散客上月同期
    private BigDecimal sk2LastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 4);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
          n =   n.add(managerdailyBO.getIndividualTraveler()) ;
        }
        return n ;
    }

    //散客本年累计
    private BigDecimal sk2Year(Integer hotelId, String date){
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, date, 4);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n =  n.add( managerdailyBO.getIndividualTraveler());
        }
        return n;
    }

    //散客上年同期
    private BigDecimal sk2LastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 4);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n =  n.add(managerdailyBO.getIndividualTraveler()) ;
        }
        return n;
    }




    //协议单位本月累计
    private BigDecimal xydw2Month(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 4);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n =  n.add(managerdailyBO.getAgreementUnit()) ;
        }
        return n;
    }

    //协议单位上月同期
    private BigDecimal xydw2LastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 4);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
          n =   n.add( managerdailyBO.getIndividualTraveler());
        }
        return n ;
    }

    //协议单位本年累计
    private BigDecimal xydw2Year(Integer hotelId, String date){
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, date, 4);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n =  n.add(managerdailyBO.getAgreementUnit()) ;
        }
        return n;
    }

    //协议单位上年同期
    private BigDecimal xydw2LastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 4);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }

        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n =  n.add(managerdailyBO.getIndividualTraveler()) ;
        }
        return n;
    }


    //直接入住本月累计
    private BigDecimal zjrz2Month(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 4);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n =  n.add( managerdailyBO.getEnter());
        }
        return n;
    }

    //直接入住上月同期
    private BigDecimal zjrz2LastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 4);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n =  n.add(managerdailyBO.getEnter()) ;
        }
        return n ;
    }

    //直接入住本年累计
    private BigDecimal zjrz2Year(Integer hotelId, String date){
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, date, 4);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n =  n.add(managerdailyBO.getEnter()) ;
        }
        return n;
    }

    //直接入住上年同期
    private BigDecimal zjrz2LastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 4);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }

        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
          n =   n.add(managerdailyBO.getEnter()) ;
        }
        return n;
    }


    //房间预订本月累计
    private BigDecimal fjyd2Month(Integer hotelId, String date){
    //当月第一天
    Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
    //当月最后一天
    Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
    List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
            new SimpleDateFormat("yyyy-MM-dd").format(dete2), 4);
    BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
    for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
       n =  n.add(managerdailyBO.getDirectBooking()) ;
    }
    return n;
  }


    //房间预订上月同期
    private BigDecimal fjyd2LastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 4);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }

        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
         n =    n.add(managerdailyBO.getDirectBooking()) ;
        }
        return n ;
    }

    //房间预订本年累计
    private BigDecimal fjyd2Year(Integer hotelId, String date){
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, date, 4);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n =  n.add(managerdailyBO.getDirectBooking()) ;
        }
        return n;
    }

    //房间预订上年同期
    private BigDecimal fjyd2LastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 4);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n =  n.add(managerdailyBO.getDirectBooking()) ;
        }
        return n;
    }







    //小计本月累计
    private BigDecimal xj3Month(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 4);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n =  n.add(managerdailyBO.getSubtotal()) ;
        }
        return n;
    }

    //小计上月同期
    private BigDecimal xj3LastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 4);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }

        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n =  n.add(managerdailyBO.getSubtotal()) ;
        }
        return n ;
    }

    //小计本年累计
    private BigDecimal xj3Year(Integer hotelId, String date){
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, date, 4);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
          n =   n.add(managerdailyBO.getSubtotal()) ;
        }
        return n;
    }

    //小计上年同期
    private BigDecimal xj3LastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 4);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n =  n.add(managerdailyBO.getSubtotal()) ;
        }
        return n;
    }


    //会员本月累计
    private BigDecimal hyfj2Month(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 5);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n =  n.add(managerdailyBO.getMembers()) ;
        }
        return n;
    }

    //会员上月同期
    private BigDecimal hyfj2LastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 5);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getMembers()) ;
        }
        return n ;
    }

    //会员本年年累计
    private BigDecimal hyfj2Year(Integer hotelId, String date){
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, date, 5);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getMembers()) ;
        }
        return n;
    }

    //会员上年同期
    private BigDecimal hyfj2LastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 5);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getMembers()) ;
        }
        return n;
    }



    //散客本月累计
    private BigDecimal sk3Month(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 5);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add( managerdailyBO.getIndividualTraveler());
        }
        return n;
    }

    //散客上月同期
    private BigDecimal sk3LastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        log.info("上个月的今天是:{}", date1);
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 5);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n =  n.add(managerdailyBO.getIndividualTraveler()) ;
        }
        return n ;
    }

    //散客本年年累计
    private BigDecimal sk3Year(Integer hotelId, String date){
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, date, 5);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getIndividualTraveler()) ;
        }
        return n;
    }

    //散客上年同期
    private BigDecimal sk3LastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 5);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getIndividualTraveler()) ;
        }
        return n;
    }






    //协议单位本月累计
    private BigDecimal xydw3Month(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 5);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n =  n.add(managerdailyBO.getAgreementUnit()) ;
        }
        return n;
    }

    //协议单位上月同期
    private BigDecimal xydw3LastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 5);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n =  n.add(managerdailyBO.getAgreementUnit()) ;
        }
        return n ;
    }

    //协议单位本年年累计
    private BigDecimal xydw3Year(Integer hotelId, String date){
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, date, 5);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add( managerdailyBO.getAgreementUnit());
        }
        return n;
    }

    //协议单位上年同期
    private BigDecimal xydw3LastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 5);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getAgreementUnit()) ;
        }
        return n;
    }








    //直接入住本月累计
    private BigDecimal zjrz3Month(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 5);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n =  n.add(managerdailyBO.getEnter()) ;
        }
        return n;
    }

    //协议单位上月同期
    private BigDecimal zjrz3LastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 5);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }

        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n =  n.add(managerdailyBO.getEnter());
        }
        return n ;
    }

    //直接入住本年年累计
    private BigDecimal zjrz3Year(Integer hotelId, String date){
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, date, 5);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n =  n.add(managerdailyBO.getEnter()) ;
        }
        return n;
    }

    //直接入住上年同期
    private BigDecimal zjrz3LastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 5);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n =  n.add(managerdailyBO.getEnter()) ;
        }
        return n;
    }








    //房间预订本月累计
    private BigDecimal fjyd3Month(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 5);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getDirectBooking()) ;
        }
        return n;
    }

    //房间预订上月同期
    private BigDecimal fjyd3LastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 5);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n =  n.add(managerdailyBO.getDirectBooking()) ;
        }
        return n ;
    }

    //房间预定本年年累计
    private BigDecimal fjyd3Year(Integer hotelId, String date){
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, date, 5);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n =  n.add(managerdailyBO.getDirectBooking()) ;
        }
        return n;
    }

    //房间预定上年同期
    private BigDecimal fjyd3LastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 5);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getDirectBooking()) ;
        }
        return n;
    }







    //小计本月累计
    private BigDecimal xj4Month(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 5);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getSubtotal()) ;
        }
        return n;
    }

    //小计上月同期
    private BigDecimal xj4LastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 5);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }

        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n.add(managerdailyBO.getSubtotal()) ;
        }
        return n ;
    }

    //小计本年年累计
    private BigDecimal xj4Year(Integer hotelId, String date){

        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, date, 5);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n =  n.add(managerdailyBO.getSubtotal()) ;
        }
        return n;
    }
    //小计上年同期
    private BigDecimal xj4LastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 5);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(managerdailyBOS)){
            return n;
        }
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
           n =  n.add(managerdailyBO.getSubtotal()) ;
        }
        return n;
    }


    //会员入住率上月同期
    private ManagerdailyBO hyrzlLastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        ManagerdailyBO managerdailyBO = managerdailyBOMapper.queryHy(hotelId, date1, 6);

        return managerdailyBO;
    }

    //会员入住率上年同期
    private ManagerdailyBO hyrzLastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));

        ManagerdailyBO managerdailyBO = managerdailyBOMapper.queryHy(hotelId, year, 6);
        return managerdailyBO;
    }






    /**
     *  计算年增长率
     * @return
     */
    public String isIncrease(BigDecimal year1, BigDecimal year2){
        System.err.println(year1);
        System.err.println(year2);
        if(year2.intValue() ==  0){
            System.err.println("返100.00%");
            return "100.00%";
        }
        BigDecimal n = (year1.subtract(year2)).divide(year2).multiply(new BigDecimal("100"));

        return n+"%";
    }



    /**
     * 插入经理日报
     * @param hotelId
     */
    public void insertManagerDaliy(Integer hotelId){
        String date = new SimpleDateFormat("yyyy-MM-dd").format(DateUtils.getYesTaday());
        log.info("date:{}",date);
        String startTime = date + " 04:00:00";
        String end = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String endTime = end + " 04:00:00";
        log.info("startTime:{}",startTime);
        log.info("endTime:{}",endTime);
        log.info("hotelId:{}",hotelId);
        ManagerdailyBO managerdailyBO = new ManagerdailyBO();

        //添加营业状况统计
        managerdailyBO.setGrossrealIncome(orderTotalPrice(hotelId, startTime, endTime));//总实际收入
        managerdailyBO.setTotalTurnover(queryConsumption(hotelId, startTime, endTime));//总营业额
        managerdailyBO.setNumberOrder(BigDecimal.valueOf(numberOrder(hotelId, startTime, endTime)));//预定未到房数
        managerdailyBO.setMaintenanceroomNumber(BigDecimal.valueOf(maintenanceroomNumber(hotelId)));//计算维修房数
        managerdailyBO.setNumberlockedStores(BigDecimal.valueOf(numberlockedStores(hotelId, startTime, endTime)));//计算锁房数
        managerdailyBO.setNumberroomsAvailablerent(BigDecimal.valueOf(numberroomsAvailablerent(hotelId, startTime, endTime)));//计算可租房数
        managerdailyBO.setTotalnumberGuestrooms(BigDecimal.valueOf(totalnumberGuestrooms(hotelId)));//计算客房总数
        managerdailyBO.setCashDisbursements(cashDisbursements(hotelId,startTime,endTime).abs());//计算现金支出
        managerdailyBO.setCash(cash(hotelId, startTime, endTime));//计算现金收入
        managerdailyBO.setDailyType(1);
        managerdailyBO.setHotelId(hotelId);
        managerdailyBO.setDateTime(DateUtils.parseDate(date, "yyyy-MM-dd"));
        managerdailyBOMapper.insertSelective(managerdailyBO);

        //添加营业收入明细
        ManagerdailyBO managerdailyBO2 = new ManagerdailyBO();
        BigDecimal v8 = throughoutDayrent(hotelId, startTime, endTime);
        managerdailyBO2.setThroughoutDayrent(v8);//全天日租
        BigDecimal v9 = rateAdjustment(hotelId,startTime, endTime);
        managerdailyBO2.setRateAdjustment(v9);//计算房费调整
        BigDecimal v10 = hourRate(hotelId, startTime, endTime);
        managerdailyBO2.setHourRate(v10);//计算钟点房费
        BigDecimal v11 = timeoutRate(hotelId,startTime, endTime);
        managerdailyBO2.setTimeoutRate(v11);//计算超时房费

        BigDecimal v12 = v8.add(v10).add(v11).subtract(v9);
        managerdailyBO2.setNuclearnightRoomcharge(v12);//计算夜核房费
        BigDecimal v13 = compensation(hotelId,startTime, endTime);
        managerdailyBO2.setCompensation(v13);//赔偿
        BigDecimal v14 = membershipFee(hotelId,startTime, endTime);
        managerdailyBO2.setMembershipFee(v14);//计算会员卡费
        BigDecimal v15 = goods(hotelId,startTime, endTime);
        managerdailyBO2.setGoods(v15);//计算商品
        managerdailyBO2.setSubtotal(v12.add(v13).add(v14).add(v15));//小计
        managerdailyBO2.setDailyType(2);
        managerdailyBO2.setHotelId(hotelId);
        managerdailyBO2.setDateTime(DateUtils.parseDate(date, "yyyy-MM-dd"));
        managerdailyBOMapper.insertSelective(managerdailyBO2);

        //房费收入分析
        ManagerdailyBO managerdailyBO3 = new ManagerdailyBO();
        //会员
        BigDecimal members = members(hotelId, startTime, endTime);
        managerdailyBO3.setMembers(members);
        //协议单位
        BigDecimal v = agreementUnit(hotelId, startTime, endTime);
        managerdailyBO3.setAgreementUnit(v);
        //散客
        BigDecimal v1 = individualTraveler(hotelId, startTime, endTime);
        managerdailyBO3.setIndividualTraveler(v1);
        //直接入住
        BigDecimal enter = enter(hotelId, startTime, endTime);
        managerdailyBO3.setEnter(enter);
        //预约入住
        BigDecimal v2 = directBooking(hotelId, startTime, endTime);

        managerdailyBO3.setDirectBooking(v2);
        BigDecimal add = enter.add(v2);
        managerdailyBO3.setSubtotal(add);
        managerdailyBO3.setDailyType(3);
        managerdailyBO3.setHotelId(hotelId);
        managerdailyBO3.setDateTime(DateUtils.parseDate(date, "yyyy-MM-dd"));
        managerdailyBOMapper.insertSelective(managerdailyBO3);

        BigDecimal f = new BigDecimal("0");

        //房晚数分析
        ManagerdailyBO managerdailyBO4 = new ManagerdailyBO();
        //会员房晚数
        BigDecimal a = FwMembers(hotelId, startTime, endTime);
        managerdailyBO4.setMembers(a);
        //协议单位房晚数
        BigDecimal b = FwAgreementUnit(hotelId, startTime, endTime);
        managerdailyBO4.setAgreementUnit(b);
        //散客房晚数
        BigDecimal c = FwIndividualTraveler(hotelId, startTime, endTime);
        managerdailyBO4.setIndividualTraveler(c);
        //直接入住房晚数
        BigDecimal d = FwEnter(hotelId, startTime, endTime);
        managerdailyBO4.setEnter(d);
        //房间预订房晚数
        BigDecimal e = FwDirectBooking(hotelId, startTime, endTime);
        managerdailyBO4.setDirectBooking(e);
        //小计
         f = f.add(d).add(e);
        managerdailyBO4.setSubtotal(f);
        managerdailyBO4.setDailyType(4);
        managerdailyBO4.setHotelId(hotelId);
        managerdailyBO4.setDateTime(DateUtils.parseDate(date, "yyyy-MM-dd"));
        managerdailyBOMapper.insertSelective(managerdailyBO4);


        System.err.println("段四");
        //平均房价分析
        ManagerdailyBO managerdailyBO5 = new ManagerdailyBO();
        //会员平均房价分析
        BigDecimal v3 = new BigDecimal("0");
        if(a.intValue() != 0 ){
             v3 =add.divide(a,2, BigDecimal.ROUND_HALF_UP);
        }

        managerdailyBO5.setMembers(v3);
        //协议单位平均房价
        BigDecimal v4 = b.intValue() == 0 ? new BigDecimal("0") : add.divide(b,2, BigDecimal.ROUND_HALF_UP);
        managerdailyBO5.setAgreementUnit(v4);
        log.info("v4:{}",v4);
        //散客平均房价
        BigDecimal v5 = c.intValue() == 0 ? new BigDecimal("0") : add.divide(c,2, BigDecimal.ROUND_HALF_UP);
        log.info("v5:{}",v5);
        managerdailyBO5.setIndividualTraveler(v5);
        //直接入住平均房价
        BigDecimal v6 = d.intValue() == 0 ? new BigDecimal("0") : add.divide(d, 2, BigDecimal.ROUND_HALF_UP);
        log.info("v6:{}",v6);
        managerdailyBO5.setEnter(v6);
        //预约入住平均房价
        BigDecimal v7 = e.intValue() == 0 ? new BigDecimal("0") : add.divide(e,2, BigDecimal.ROUND_HALF_UP);
        log.info("v7:{}",v7);
        managerdailyBO5.setDirectBooking(v7);

        log.info("v3+v4+v5+v6+v7:{}",v3.add(v4).add(v5));
        //小计
        BigDecimal add1 = v6.add(v7);
        managerdailyBO5.setSubtotal(add1);
        managerdailyBO5.setDailyType(5);
        managerdailyBO5.setHotelId(hotelId);
        managerdailyBO5.setDateTime(DateUtils.parseDate(date, "yyyy-MM-dd"));
        managerdailyBOMapper.insertSelective(managerdailyBO5);

        System.err.println("段五");

        //百分比格式
        NumberFormat nf   =   NumberFormat.getPercentInstance();
         nf.setMinimumFractionDigits( 2 );       // 保留到小数点后几位        显示：47.00%

        //出租率分析
        ManagerdailyBO managerdailyBO6 = new ManagerdailyBO();
        //会员出租率
        managerdailyBO6.setMembers( managerdailyBO.getTotalnumberGuestrooms().intValue() == 0 ? new BigDecimal("0") : a.divide(managerdailyBO.getTotalnumberGuestrooms(),4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")) );
        //协议单位出租率
        managerdailyBO6.setAgreementUnit(  managerdailyBO.getTotalnumberGuestrooms().intValue() == 0 ? new BigDecimal("0") :b.divide(managerdailyBO.getTotalnumberGuestrooms(),4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"))  );
        //散客出租率
        managerdailyBO6.setIndividualTraveler(   managerdailyBO.getTotalnumberGuestrooms().intValue() == 0 ? new BigDecimal("0") :c.divide(managerdailyBO.getTotalnumberGuestrooms(),4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")) );

        //直接入住出租率
        managerdailyBO6.setEnter(  managerdailyBO.getTotalnumberGuestrooms().intValue() == 0 ? new BigDecimal("0") : d.divide(managerdailyBO.getTotalnumberGuestrooms(),4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")) );

        //预约入住出租率
        managerdailyBO6.setDirectBooking(managerdailyBO.getTotalnumberGuestrooms().intValue() == 0 ? new BigDecimal("0") :e.divide(managerdailyBO.getTotalnumberGuestrooms(),4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")) );
        //小计
        managerdailyBO6.setSubtotal( f.intValue() != 0 ? f.divide(managerdailyBO.getTotalnumberGuestrooms(),4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")) : new BigDecimal("0") );
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

    /**
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
      //客房总数 减去 锁房数

        return totalnumberGuestrooms(hotelId) - numberlockedStores(hotelId, startTime, endTime);
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
    private BigDecimal cashDisbursements(Integer hotelId, String startTime, String endTime){
        BigDecimal bigDecimal = managerDailyDAO.queryGoods2(hotelId, startTime, endTime);

        return bigDecimal.abs();
    }

    /**
     * 计算现金收入
     * @return
     */
    private BigDecimal cash(Integer hotelId, String startTime, String endTime){
        BigDecimal bigDecimal = managerDailyDAO.queryCash(hotelId, startTime, endTime);

       return bigDecimal;
    }



    /**
     * 计算全天日租  计算全天日租出去的房间价格
     *
     * @return
     */
    private BigDecimal throughoutDayrent(Integer hotelId, String startTime, String endTime){
        List<OrderReBO> orderReBOS = managerDailyDAO.queryOrderRe(hotelId, startTime, endTime);
        BigDecimal n = new BigDecimal("0");
        if(CollectionUtils.isEmpty(orderReBOS)){
            return n;
        }

        for (OrderReBO orderReBO : orderReBOS) {
           n = n.add(orderReBO.getMoney()) ;
        }
        return n;
    }

    /**
     * 房费调整
     * @return
     */
    private BigDecimal rateAdjustment(Integer hotelId,String startTime, String endTime){
        BigDecimal bigDecimal = managerDailyDAO.queryRateAdjustment(hotelId, startTime, endTime);

        return bigDecimal.abs();
    }

    /**
     * 钟点房费
     * @param hotelId
     * @param startTime
     * @param endTime
     * @return
     */
    private BigDecimal hourRate(Integer hotelId, String startTime, String endTime){
        BigDecimal bigDecimal = managerDailyDAO.queryhourRate(hotelId, startTime, endTime);
        return bigDecimal.abs();
    }


    /**
     * 计算超时房费
     * @param startTime
     * @param endTime
     * @return
     */
    private BigDecimal timeoutRate(Integer hotelId,String startTime, String endTime){
        BigDecimal bigDecimal = managerDailyDAO.querytimeoutRate(startTime, endTime, hotelId);

        return bigDecimal.abs() ;
    }


    /**
     *
     * @param startTime
     * @param endTime
     * @return
     */
    private BigDecimal nuclearnightRoomcharge(Integer hotelId,String startTime, String endTime){
        /*List<CashierSummary> cashierSummaries = managerDailyDAO.querynuclearnightRoomcharge1(startTime, endTime);
        BigDecimal n1 = new BigDecimal("0");
        for (CashierSummary cashierSummary : cashierSummaries) {
            n1 = n1.add(cashierSummary.getConsumption()) ;
        }

        List<CashierSummary> cashierSummaries2 = managerDailyDAO.querynuclearnightRoomcharge2(startTime, endTime);
        BigDecimal n2 = new BigDecimal("0");
        for (CashierSummary cashierSummary : cashierSummaries2) {
           n2 =  n2.add(cashierSummary.getConsumption()) ;
        }*/

        return null ;
    }


    /**
     * 计算赔偿
     * @param startTime
     * @param endTime
     * @return
     */
    private BigDecimal compensation(Integer hotelId,String startTime, String endTime){
        List<CashierSummary> querycompensation = managerDailyDAO.querycompensation(hotelId,startTime, endTime);
        BigDecimal n = new BigDecimal("0");
        for (CashierSummary cashierSummary : querycompensation) {
            if(cashierSummary != null){
              n =   n.add(cashierSummary.getConsumption()) ;
            }
        }
        return n;
    }

    /**
     * 会员卡费
     * @param startTime
     * @param endTime
     * @return
     */
    private BigDecimal membershipFee(Integer hotelId,String startTime, String endTime){
        BigDecimal bigDecimal = managerDailyDAO.querymembershipFee(hotelId, startTime, endTime);
        return bigDecimal ;
    }

    /**
     * 商品
     * @param
     * @param endTime
     * @return
     */
    private BigDecimal goods(Integer hotelId, String startTime, String endTime){
        List<CashierSummary> querygoods = managerDailyDAO.querygoods(hotelId, startTime, endTime);
        BigDecimal n = new BigDecimal("0");
        for (CashierSummary querygood : querygoods) {
            if(querygood != null){
                n =  n.add(querygood.getConsumption()) ;
            }
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
    private BigDecimal members(Integer hotelId, String startTime, String endTime){
        BigDecimal querymembers = managerDailyDAO.querymembers(hotelId, startTime, endTime);

        return querymembers.abs() ;
    }

    /**
     * 房费收入分析
     * 中介  协议单位
     * @param hotelId
     * @param startTime
     * @param endTime
     * @return
     */
    private BigDecimal agreementUnit(Integer hotelId, String startTime, String endTime){
        BigDecimal bigDecimal = managerDailyDAO.qyeryagreementUnit(hotelId, startTime, endTime);
        return bigDecimal.abs();
    }

    /**
     * 房费收入分析
     * 散客
     * @param hotelId
     * @param startTime
     * @param endTime
     * @return
     */
    private BigDecimal individualTraveler(Integer hotelId, String startTime, String endTime){
        BigDecimal bigDecimal = managerDailyDAO.queryindividualTraveler(hotelId, startTime, endTime);
        return  bigDecimal.abs();
    }


    /**
     * 步入 直接入住
     * @param hotelId
     * @param startTime
     * @param endTime
     * @return
     */
    private BigDecimal enter(Integer hotelId, String startTime, String endTime){
        BigDecimal bigDecimal = managerDailyDAO.queryEnter(hotelId, startTime, endTime);

        return bigDecimal.abs();
    }

    /**
     * 房间预订 | 直接预订
     * @param hotelId
     * @param startTime
     * @param endTime
     * @return
     */
    private BigDecimal directBooking(Integer hotelId, String startTime, String endTime){
        BigDecimal bigDecimal = managerDailyDAO.queryDirectBooking(hotelId, startTime, endTime);

        return bigDecimal.abs();
    }


    /**
     * 会员房晚数
     * @param hotelId
     * @param startTime
     * @param endTime
     * @return
     */
    private BigDecimal FwMembers(Integer hotelId, String startTime, String endTime){
        Integer integer = managerDailyDAO.queryFwMembers(hotelId, startTime, endTime);
        return BigDecimal.valueOf(integer);
    }

    /**
     * 协议单位房晚数
     * @param hotelId
     * @param startTime
     * @param endTime
     * @return
     */
    private BigDecimal FwAgreementUnit(Integer hotelId, String startTime, String endTime){
        Integer integer = managerDailyDAO.queryFwAgreementUnit(hotelId, startTime, endTime);
        return BigDecimal.valueOf((int)integer).abs();
    }

    /**
     * 散客房晚数
     * @param hotelId
     * @param startTime
     * @param endTime
     * @return
     */
    private BigDecimal FwIndividualTraveler(Integer hotelId, String startTime, String endTime){
        Integer integer = managerDailyDAO.queryFwIndividualTraveler(hotelId, startTime, endTime);
        return BigDecimal.valueOf((int)integer);
    }

    /**
     * 直接入住房晚数
     * @param hotelId
     * @param startTime
     * @param endTime
     * @return
     */
    private BigDecimal FwEnter(Integer hotelId, String startTime, String endTime ){
        Integer integer = managerDailyDAO.queryFwEnter(hotelId, startTime, endTime);
        return BigDecimal.valueOf(integer);
    }

    /**
     * 房间预订房晚数
     * @param hotelId
     * @param startTime
     * @param endTime
     * @return
     */
    private BigDecimal FwDirectBooking(Integer hotelId, String startTime, String endTime ){
        int integer = managerDailyDAO.queryFwDirectBooking(hotelId, startTime, endTime);

        return BigDecimal.valueOf((int)integer);
    }

    /**
     * 会员平均房价分析
     * @param hotelId
     * @param startTime
     * @param endTime
     * @return
     */
    private BigDecimal PjMembers(Integer hotelId, String startTime, String endTime){
        //会员总房价
        BigDecimal members = members(hotelId, startTime, endTime);
        log.info("会员总房价:{}",members);
        //会员开放数量
        BigDecimal i = FwMembers(hotelId, startTime, endTime);
        log.info("会员开房数量:{}",i);

        BigDecimal divide = new BigDecimal("0");
        if(i.intValue() != 0){
             divide = members.divide(i);
        }
        return divide;
    }


    /**
     * 协议单位平均房价分析
     * @param hotelId
     * @param startTime
     * @param endTime
     * @return
     */
    private BigDecimal PjAgreementUnit(Integer hotelId, String startTime, String endTime){
        //协议单位总房价
        BigDecimal v = agreementUnit(hotelId, startTime, endTime);
        log.info("协议单位总房价:{}",v);
        //协议单位开放数量
        BigDecimal i = FwAgreementUnit(hotelId, startTime, endTime);
        BigDecimal divide = new BigDecimal("0");
        if(i.intValue() != 0){
             divide = v.divide(i);
        }
        return divide;
    }


    /**
     * 散客平均房价分析
     * @param hotelId
     * @param startTime
     * @param endTime
     * @return
     */
    private BigDecimal PjIndividualTraveler(Integer hotelId, String startTime, String endTime){
        //散客总房价
        BigDecimal v = individualTraveler(hotelId, startTime, endTime);
        log.info("v:{}",v);
        //散客总开房数
        BigDecimal i = FwIndividualTraveler(hotelId, startTime, endTime);
        BigDecimal divide = new BigDecimal("0");
        if(i.intValue() != 0){
             divide = v.divide(i);
        }
        return divide;
    }

    /**
     * 直接入住平均房价分析
     * @param hotelId
     * @param startTime
     * @param endTime
     * @return
     */
    private BigDecimal PjEnter(Integer hotelId, String startTime, String endTime){
        //直接入住总房价
        BigDecimal enter = enter(hotelId, startTime, endTime);
        //直接入住房间数
        BigDecimal i = FwEnter(hotelId, startTime, endTime);
        BigDecimal divide = new BigDecimal("0");
        if(i.intValue() != 0){
            divide = enter.divide(i);
        }
        return divide;
    }


    /**
     * 房间预订分均房价分析
     * @param hotelId
     * @param startTime
     * @param endTime
     * @return
     */
    private BigDecimal PjDirectBooking(Integer hotelId, String startTime, String endTime){
        //房间预订总房价
        BigDecimal v = directBooking(hotelId, startTime, endTime);
        log.info("房间预订总房价:{}", v);
        //房间预订总数量
        BigDecimal i = FwDirectBooking(hotelId, startTime, endTime);
        BigDecimal divide = new BigDecimal("0");
        if(i.intValue() != 0){
            divide = v.divide(i);
        }
        return divide;
    }


    /**
     * 查询今天营业额
     * @param hotelId
     * @param startTime
     * @param endTime
     * @return
     */
    private BigDecimal orderTotalPrice(Integer hotelId, String startTime, String endTime){
        BigDecimal bigDecimal = managerdailyBOMapper.queryOrderTotalPrice(hotelId, startTime, endTime);
        log.info("totalPriceBOS:{}",bigDecimal);
        BigDecimal n = new BigDecimal("0");

        return bigDecimal;
    }

    /**
     * 获取当天总收入
     * @param hotelId
     * @param startTime
     * @param endTime
     * @return
     */
    private BigDecimal queryConsumption(Integer hotelId, String startTime, String endTime){
        BigDecimal bigDecimal = managerdailyBOMapper.queryConsumption(hotelId, startTime, endTime);
        return bigDecimal;
    }
}
