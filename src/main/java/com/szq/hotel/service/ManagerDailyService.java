package com.szq.hotel.service;


import com.szq.hotel.dao.ManagerDailyDAO;
import com.szq.hotel.dao.ManagerdailyBOMapper;
import com.szq.hotel.entity.bo.*;
import com.szq.hotel.util.DateUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class ManagerDailyService {

    @Resource
    private ManagerDailyDAO managerDailyDAO;

    @Resource
    private RoomService roomService;

    @Resource
    private ManagerdailyBOMapper managerdailyBOMapper;


    /**
     * 经理日报查询
     * @param date
     * @param
     * @param hotelId
     * @return
     */
    public ManagerdailyChangeBO queryInfo(String date, Integer hotelId){
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
        ManagerdailyBO managerdailyBO = managerdailyBOMapper.queryManagerdaily(hotelId, 1, date);
        //当天营业收入明细
        ManagerdailyBO managerdailyBO1 = managerdailyBOMapper.queryManagerdaily(hotelId, 2, date);
        //当天房费收入明细
        ManagerdailyBO managerdailyBO2 = managerdailyBOMapper.queryManagerdaily(hotelId, 3, date);
        //当天房晚数分析
        ManagerdailyBO managerdailyBO3 = managerdailyBOMapper.queryManagerdaily(hotelId, 4 , date);
        //当天平均房价分析
        ManagerdailyBO managerdailyBO4 = managerdailyBOMapper.queryManagerdaily(hotelId, 5 , date);
        //当天出租率分析
        ManagerdailyBO managerdailyBO5 = managerdailyBOMapper.queryManagerdaily(hotelId, 6 , date);


        //获取今年日期
        String yyyy = new SimpleDateFormat("yyyy").format(date);
        //获取上一年日期
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));


        //当天营业收入
        grossrealIncome.setDay(managerdailyBO.getGrossrealIncome()+"");
        //本月营业收入
        grossrealIncome.setMonth(month(hotelId, date)+"");
        //上月同期
        grossrealIncome.setLastMonthDay(lastMonthDay(hotelId, date) + "");
        //本年累计
        grossrealIncome.setYear(year(hotelId, date)+"");
        //上年同期
        grossrealIncome.setLastYearDay(lastYearDay(hotelId, date)+"");
        //计算年增长率
        grossrealIncome.setInsertRial(isIncrease(year(hotelId, yyyy),year(hotelId, year)));



        //获取当天营业额
        totalTurnover.setDay(managerdailyBO.getTotalTurnover()+"");
        //计算本月累计营业额
        totalTurnover.setMonth(queryMonth(hotelId, date)+"");
        //计算上月同期营业额
        totalTurnover.setLastMonthDay(queryLastMonthDay(hotelId, date)+"");
        //计算本年累计营业额
        totalTurnover.setYear(queryYear(hotelId, date)+"");
        //计算上年同期营业额
        totalTurnover.setLastYearDay( queryLastYearDay(hotelId, date) + "");
        //计算营业额年增长率
        totalTurnover.setInsertRial(isIncrease(queryYear(hotelId, yyyy),queryYear(hotelId, year)));


        //获取当天预订未到房数
        numberOrder.setDay(managerdailyBO.getNumberOrder()+"");
        //计算本月累计预订未到房数
        numberOrder.setMonth(ydDay(hotelId, date) + "");
        //计算上月同期预订未到房数
        numberOrder.setLastMonthDay(ydLastMonthDay(hotelId, date) + "");
        //计算本年累计预订未到房数
        numberOrder.setYear(ydYear(hotelId,date)+"");
        //计算上年同期预订未到房数
        numberOrder.setLastYearDay(ydLastYearDay(hotelId, date)+"");
        //计算预订未到房数年增长率
        numberOrder.setInsertRial(isIncrease(ydYear(hotelId,yyyy),ydYear(hotelId,year)));

        //获取当天维修房数
        maintenanceroomNumber.setDay(managerdailyBO.getMaintenanceroomNumber()+"");
        //计算本月累计维修房数
        maintenanceroomNumber.setMonth(wxMonth(hotelId, date) + "");
        //计算上月同期维修房数
        maintenanceroomNumber.setLastMonthDay(wxLastMonthDay(hotelId, date) + "");
        //计算本年累计维修房数
        maintenanceroomNumber.setYear(wxYear(hotelId, date)+ "");
        //计算上年同期维修房数
        maintenanceroomNumber.setLastYearDay(wxLastYearDay(hotelId, date) + "");
        //计算预订维修房数年增长率
        maintenanceroomNumber.setInsertRial(isIncrease(wxYear(hotelId, yyyy),wxYear(hotelId, year)));


        //获取当天门店锁房数
        numberlockedStores.setDay(managerdailyBO.getNumberlockedStores()+"");
        //计算本月累计门店锁房数
        numberlockedStores.setMonth(sfMonth(hotelId,date)+ "");
        //计算上月同期门店锁房数
        numberlockedStores.setLastMonthDay(sfLastMonthDay(hotelId, date) + "");
        //计算本年累计门店锁房数
        numberlockedStores.setYear(sfYear(hotelId, date) + "");
        //计算上年同期门店锁房数
        numberlockedStores.setLastYearDay(sfLastYearDay(hotelId, date) + "");
        //计算门店锁房数年增长率
        maintenanceroomNumber.setInsertRial(isIncrease(sfYear(hotelId, yyyy),sfYear(hotelId, year)));

        //获取当天可出租房数
        numberroomsAvailablerent.setDay(managerdailyBO.getNumberroomsAvailablerent() + "");
        //获取本月可出租房数
        numberroomsAvailablerent.setMonth(zfMonth(hotelId, date) + "");
        //获取上月同期可出租房数
        numberroomsAvailablerent.setLastMonthDay(zfLastMonthDay(hotelId, date) + "");
        //获取本年累计可出租房数
        numberroomsAvailablerent.setYear(zfYear(hotelId, date) + "");
        //获取上年同期可出租房数
        numberroomsAvailablerent.setLastYearDay(zfLastYearDay(hotelId, date) + "");
        //获取可出租房数年增长率
        numberroomsAvailablerent.setInsertRial(isIncrease(zfYear(hotelId, yyyy),zfYear(hotelId, year)));

        //获取当天客房房数
        totalnumberGuestrooms.setDay(managerdailyBO.getTotalnumberGuestrooms() + "");
        //获取本月累计客房总数
        totalnumberGuestrooms.setMonth(kfMonth(hotelId, date) + "");
        //获取上月同期客房总数
        totalnumberGuestrooms.setLastMonthDay(kfLastMonthDay(hotelId, date) + "");
        //获取本年累计客房总数
        totalnumberGuestrooms.setYear(kfYear(hotelId, date) + "");
        //获取上年同期客房总数
        totalnumberGuestrooms.setLastYearDay(kfLastYearDay(hotelId, date) + "");
        //获取客房总数年增长率
        numberroomsAvailablerent.setInsertRial(isIncrease(kfYear(hotelId, yyyy),kfYear(hotelId, year)));

        //获取当天现金支出
        cashDisbursements.setDay(managerdailyBO.getCashDisbursements() + "");
        //获取本月累计现金支出
        cashDisbursements.setMonth(zcMonth(hotelId, date) + "");
        //获取上月同期现金支出
        cashDisbursements.setLastMonthDay(zcLastMonthDay(hotelId, date) + "");
        //获取本年累计现金支出
        cashDisbursements.setYear(zcYear(hotelId, date) + "");
        //获取上年同期现金支出
        cashDisbursements.setLastYearDay(zcLastYearDay(hotelId,  date) + "");
        //获取现金支出年增长率
        cashDisbursements.setInsertRial(isIncrease(zcYear(hotelId, yyyy),zcYear(hotelId, year)));

        //获取当天现金收入
        cash.setDay(managerdailyBO.getCash() + "");
        //获取本月累计现金收入
        cash.setMonth(srMonth(hotelId, date) + "");
        //获取上月同期现金收入
        cash.setLastMonthDay(srLastMonthDay(hotelId, date) + "");
        //获取本年累计现金收入
        cash.setYear(srYear(hotelId, date) + "");
        //获取上年同期现金收入
        cash.setLastYearDay(srLastYearDay(hotelId, date) + "");
        //获取现金收入年增长率
        cash.setInsertRial(isIncrease(srYear(hotelId, yyyy),srYear(hotelId, year)));


        //营业收入明细
        //获取全天日租今日发生
        throughoutDayrent.setDay(managerdailyBO1.getThroughoutDayrent()+"");
        //获取全天日租本月累计
        throughoutDayrent.setMonth(qtrzMonth(hotelId, date) + "");
        //获取全天日租上月同期
        throughoutDayrent.setLastMonthDay(qtrzLastMonthDay(hotelId, date) + "");
        //获取全天日租本年积累
        throughoutDayrent.setYear(qtrzYear(hotelId, date)+"");
        //获取全天日租上年同期
        throughoutDayrent.setLastYearDay(qtrzLastYearDay(hotelId, date) + "");
        //获取全天日租年增率
        throughoutDayrent.setInsertRial(isIncrease(qtrzYear(hotelId, yyyy),qtrzYear(hotelId, year)));


        //房费调整今日发生
        rateAdjustment.setDay(managerdailyBO1.getRateAdjustment() + "");
        //房费调整本月累计
        rateAdjustment.setMonth(fftzMonth(hotelId, date) + "");
        //房费调整上月同期
        rateAdjustment.setLastMonthDay(fftzLastMonthDay(hotelId, date) + "");
        //房费调整本年累计
        rateAdjustment.setYear(fftzYear(hotelId, date) +"");
        //房费调整上年同期
        rateAdjustment.setLastYearDay(fftzLastYearDay(hotelId, date) + "");
        //房费调整年增长率
        rateAdjustment.setInsertRial(isIncrease(fftzYear(hotelId, yyyy),fftzYear(hotelId, year)));

        //钟点房费今日发生
        hourRate.setDay(managerdailyBO1.getHourRate()+"");
        //钟点房费本月累计
        hourRate.setMonth(zdffMonth(hotelId, date) + "");
        //钟点房费上月同期
        hourRate.setLastMonthDay(zdffLastMonthDay(hotelId, date) +"");
        //钟点房费本年累计
        hourRate.setYear(zdffYear(hotelId, date) + "");
        //钟点房费上年同期
        hourRate.setLastYearDay(zdffLastYearDay(hotelId, date)+"") ;
        //钟点房费年增长率
        hourRate.setInsertRial(isIncrease(zdffYear(hotelId, yyyy),zdffYear(hotelId, year)));


        //超时房费今日发生
        timeoutRate.setDay(managerdailyBO1.getTimeoutRate() + "");
        //超时房费本月累计
        timeoutRate.setMonth(csffMonth(hotelId, date)+ "");
        //超时房费上月同期
        timeoutRate.setLastMonthDay(csffLastMonthDay(hotelId, date) + "");
        //超时房费本年累计
        timeoutRate.setYear(csffYear(hotelId, date) + "");
        //超时房费上年同期
        timeoutRate.setLastYearDay(csffLastYearDay(hotelId, date) + "");
        //超时房费年增长率
        timeoutRate.setInsertRial(isIncrease(csffYear(hotelId, yyyy),csffYear(hotelId, year)));


        //夜核房费今日发生
        nuclearnightRoomcharge.setDay(managerdailyBO1.getNuclearnightRoomcharge()+"");
        //夜核房费本月累计
        nuclearnightRoomcharge.setMonth(yhffMonth(hotelId, date) + "");
        //夜核房费上月同期
        nuclearnightRoomcharge.setLastMonthDay(yhffLastMonthDay(hotelId, date) + "");
        //夜核房费本年累计
        nuclearnightRoomcharge.setYear(yhffYear(hotelId, date) +"");
        //夜核房费上年同期
        nuclearnightRoomcharge.setLastYearDay(yhffLastYearDay(hotelId, date)+"");
        //夜核房费年增长率
        nuclearnightRoomcharge.setInsertRial(isIncrease(yhffYear(hotelId, yyyy),yhffYear(hotelId, year)));


        //赔偿今日发生
        compensation.setDay(managerdailyBO1.getCompensation()+"");
        //赔偿本月累计
        compensation.setMonth(pcMonth(hotelId, date)+"");
        //赔偿上月同期
        compensation.setLastMonthDay(pcLastMonthDay(hotelId, date) + "");
        //赔偿本年累计
        compensation.setYear(pcYear(hotelId, date)+"");
        //赔偿上年同期
        compensation.setLastYearDay(pcLastYearDay(hotelId, date) +"");
        //赔偿年增长率
        compensation.setInsertRial(isIncrease(pcYear(hotelId, yyyy),pcYear(hotelId, year)));


        //会员卡费今日发生
        membershipFee.setDay(managerdailyBO1.getMembershipFee()+"");
        //会员卡费本月累计
        membershipFee.setMonth(hykfMonth(hotelId, date)+"");
        //会员卡费上月同期
        membershipFee.setLastMonthDay(hykfLastMonthDay(hotelId, date)+"");
        //会员卡费本年累计
        membershipFee.setYear(hykfYear(hotelId, date)+"");
        //会员卡费上年同期
        membershipFee.setLastYearDay(hykfLastYearDay(hotelId, date)+"");
        //会员卡费年增长率
        membershipFee.setInsertRial(isIncrease(hykfYear(hotelId, yyyy),hykfYear(hotelId, year)));

        //商品今日发生
        goods.setDay(managerdailyBO1.getGoods()+"");
        //商品本月累计
        goods.setMonth(spMonth(hotelId, date)+"");
        //商品上月同期
        goods.setLastMonthDay(spLastMonthDay(hotelId, date)+"");
        //商品本年累计
        goods.setYear(spYear(hotelId, date)+"");
        //商品上年同期
        goods.setLastYearDay(spLastYearDay(hotelId, date)+"");
        //商品年增长率
        goods.setInsertRial(isIncrease(spYear(hotelId, yyyy),spYear(hotelId, year)));


        //当天小计
        subtotal2.setDay(managerdailyBO1.getSubtotal()+"");
        //小计本月累计
        subtotal2.setMonth(xjMonth(hotelId, date)+"");
        //小计上月同期
        subtotal2.setLastMonthDay(xjLastMonthDay(hotelId, date)+"");
        //小计本年累计
        subtotal2.setYear(xjYear(hotelId, date)+"");
        //小计上年同期
        subtotal2.setLastYearDay(xjLastYearDay(hotelId, date)+"");
        //小计年增长率
        subtotal2.setInsertRial(isIncrease(xjYear(hotelId, yyyy),xjYear(hotelId, year)));

        //房费收入分析
        //会员今日
        members3.setDay(managerdailyBO2.getMembers()+"");
        //会员本月累计
        members3.setMonth(hyMonth(hotelId,date)+"");
        //会员上月同期
        members3.setLastMonthDay(hyLastMonthDay(hotelId, date)+"");
        //会员本年累计
        members3.setYear(hyYear(hotelId, date)+"");
        //会员上年同期
        members3.setLastYearDay(hyLastYearDay(hotelId, date)+"");
        //会员年增长率
        members3.setInsertRial(isIncrease(hyYear(hotelId, yyyy),hyYear(hotelId, year)));


        //散客今日
        individualTraveler3.setDay(managerdailyBO2.getIndividualTraveler()+"");
        //散客本月累计
        individualTraveler3.setMonth(skMonth(hotelId, date)+"");
        //散客上月同期
        individualTraveler3.setLastMonthDay(skLastMonthDay(hotelId, date)+"");
        //散客本年累计
        individualTraveler3.setYear(skYear(hotelId, date)+"");
        //散客上年同期
        individualTraveler3.setLastYearDay(skLastYearDay(hotelId, date)+"");
        //散客年增长率
        individualTraveler3.setInsertRial(isIncrease(skYear(hotelId, yyyy),skYear(hotelId, year)));

        //协议单位今日
        agreementUnit3.setDay(managerdailyBO2.getAgreementUnit()+"");
        //协议单位本月累计
        agreementUnit3.setMonth(xydwMonth(hotelId, date)+"");
        //协议单位上月同期
        agreementUnit3.setLastMonthDay(xydwLastMonthDay(hotelId, date)+"");
        //协议单位本年累计
        agreementUnit3.setYear(xydwYear(hotelId,date)+"");
        //协议单位上年同期
        agreementUnit3.setLastYearDay(xydwLastYearDay(hotelId, date)+"");
        //协议单位年增长率
        agreementUnit3.setInsertRial(isIncrease(xydwYear(hotelId,yyyy),xydwYear(hotelId,year)));


        //直接入住今日
        enter3.setDay(managerdailyBO2.getEnter()+"");
        //直接入住本月累计
        enter3.setMonth(zjrzMonth(hotelId, date)+"");
        //直接入住上月同期
        enter3.setLastMonthDay(zjrzLastMonthDay(hotelId, date)+"");
        //直接入住本年累计
        enter3.setYear(zjrzYear(hotelId, date)+"");
        //直接入住上年同期
        enter3.setLastYearDay(zjrzLastYearDay(hotelId, date)+"");
        //直接入住年增长率
        enter3.setInsertRial(isIncrease(zjrzYear(hotelId, yyyy),zjrzYear(hotelId, year)));


        //预约入住今日
        directBooking3.setDay(managerdailyBO2.getDirectBooking()+"");
        //预约入住本月累计
        directBooking3.setMonth(yyrzMonth(hotelId, date)+"");
        //预约入住上月同期
        directBooking3.setLastMonthDay(yyrzLastMonthDay(hotelId, date)+"");
        //预约入住本年累计
        directBooking3.setYear(yyrzYear(hotelId, date)+"");
        //预约入住上年同期
        directBooking3.setLastYearDay(yyrzLastYearDay(hotelId, date)+"");
        //预约入住年增长率
        directBooking3.setInsertRial(isIncrease(yyrzYear(hotelId, yyyy),yyrzYear(hotelId, year)));


        //小计今日
        subtotal3.setDay(managerdailyBO2.getSubtotal()+"");
        //小计本月累计
        subtotal3.setMonth(xj2Month(hotelId, date)+"");
        //小计上月同期
        subtotal3.setLastMonthDay(xj2LastMonthDay(hotelId, date)+"");
        //小计本年累计
        subtotal3.setYear(xj2Year(hotelId, date)+"");
        //小计上年同期
        subtotal3.setLastYearDay(xj2LastYearDay(hotelId, date)+"");
        //小计年增长率
        subtotal3.setInsertRial(isIncrease(xj2Year(hotelId, yyyy),xj2Year(hotelId, year)));

        //房晚数分析
        //会员今日
        members4.setDay(managerdailyBO3.getMembers()+"");
        //会员本月累计
        members4.setMonth(hy2Month(hotelId, date)+"");
        //会员上月同期
        members4.setLastMonthDay(hy2LastMonthDay(hotelId, date)+"");
        //会员本年累计
        members4.setYear(hy2Year(hotelId, date)+"");
        //会员上年同期
        members4.setLastYearDay(hy2LastYearDay(hotelId, date)+"");
        //会员年增长率
        members4.setInsertRial(isIncrease(hy2Year(hotelId, yyyy),hy2Year(hotelId, year)));

        //散客今日
        individualTraveler4.setDay(managerdailyBO3.getIndividualTraveler()+"");
        //散客本月累计
        individualTraveler4.setMonth(sk2Month(hotelId, date)+"");
        //散客上月同期
        individualTraveler4.setLastMonthDay(sk2LastMonthDay(hotelId, date)+"");
        //散客本年累计
        individualTraveler4.setYear(sk2Year(hotelId, date)+"");
        //散客上年同期
        individualTraveler4.setLastYearDay(sk2LastYearDay(hotelId, date)+"");
        //散客年增长率
        individualTraveler4.setInsertRial(isIncrease(sk2Year(hotelId, yyyy),sk2Year(hotelId, year)));


        //协议单位今日
        agreementUnit4.setDay(managerdailyBO3.getAgreementUnit()+"");
        //协议单位本月累计
        agreementUnit4.setMonth(xydw2Month(hotelId, date)+"");
        //协议单位上月同期
        agreementUnit4.setLastMonthDay(xydw2LastMonthDay(hotelId, date)+"");
        //协议单位本年累计
        agreementUnit4.setYear(xydw2Year(hotelId, date)+"");
        //协议单位上年同期
        agreementUnit4.setLastYearDay(xydw2LastYearDay(hotelId, date)+"");
        //协议单位年增长率
        agreementUnit4.setInsertRial(isIncrease(xydw2Year(hotelId, yyyy),xydw2Year(hotelId, year)));


        //直接入住今日
        enter4.setDay(managerdailyBO3.getEnter()+"");
        //直接入住本月累计
        enter4.setMonth(zjrz2Month(hotelId, date)+"");
        //直接入住上月同期
        enter4.setLastMonthDay(zjrz2LastMonthDay(hotelId, date)+"");
        //直接入住本年累计
        enter4.setYear(zjrz2Year(hotelId, date)+"");
        //直接入住上年同期
        enter4.setLastYearDay(zjrz2LastYearDay(hotelId, date)+"");
        //直接入住年增长率
        enter4.setInsertRial(isIncrease(zjrz2Year(hotelId, yyyy),zjrz2Year(hotelId, year)));


        //房间预订今日
        directBooking4.setDay(managerdailyBO3.getDirectBooking()+"");
        //房间预订本月累计
        directBooking4.setMonth(fjyd2Month(hotelId, date)+"");
        //房间预订上月同期
        directBooking4.setLastMonthDay(fjyd2LastMonthDay(hotelId, date)+"");
        //房间预订本年累计
        directBooking4.setYear(fjyd2Year(hotelId, date)+"");
        //房间预订上年同期
        directBooking4.setLastYearDay(fjyd2LastYearDay(hotelId, date)+"");
        //房间预订年增长率
        directBooking4.setInsertRial(isIncrease(fjyd2Year(hotelId, yyyy),fjyd2Year(hotelId, year)));

        //小计今日
        subtotal4.setDay(managerdailyBO3.getSubtotal()+"");
        //小计本月累计
        subtotal4.setMonth(xj3Month(hotelId, date)+"");
        //小计上月同期
        subtotal4.setLastMonthDay(xj3LastMonthDay(hotelId, date)+"");
        //小计本年累计
        subtotal4.setYear(xj3Year(hotelId, date)+"");
        //小计上年同期
        subtotal4.setLastYearDay(xj3LastYearDay(hotelId, date)+"");
        //小计年增长率
        subtotal4.setInsertRial(isIncrease(xj3Year(hotelId, yyyy),xj3Year(hotelId, year)));




        //平均房价分析
        //会员房价今日分析
        members5.setDay(managerdailyBO4.getMembers()+"");
        //会员本月累计
        members5.setMonth(hyfj2Month(hotelId, date)+"");
        //会员上月同期
        members5.setLastMonthDay(hyfj2LastMonthDay(hotelId, date)+"");
        //会员本年年累计
        members5.setYear(hyfj2Year(hotelId, date)+"");
        //会员上年同期
        members5.setLastYearDay(hyfj2LastYearDay(hotelId, date)+"");
        //会员年增长率
        members5.setInsertRial(isIncrease(hyfj2Year(hotelId, yyyy),hyfj2Year(hotelId, year)));


        //散客今日
        individualTraveler5.setDay(managerdailyBO4.getIndividualTraveler()+"");
        //散客本月累计
        individualTraveler5.setMonth(sk3Month(hotelId, date)+"");
        //散客上月同期
        individualTraveler5.setLastMonthDay(sk3LastMonthDay(hotelId, date)+"");
        //散客本年年累计
        individualTraveler5.setYear(sk3Year(hotelId, date)+"");
        //散客上年同期
        individualTraveler5.setLastYearDay(sk3LastYearDay(hotelId, date)+"");
        //散客年增长率
        individualTraveler5.setInsertRial(isIncrease(sk3Year(hotelId, yyyy),sk3Year(hotelId, year)));


        //协议单位今日
        agreementUnit5.setDay(managerdailyBO4.getAgreementUnit()+"");
        //协议单位本月累计
        agreementUnit5.setMonth(xydw3Month(hotelId, date)+"");
        //协议单位上月同期
        agreementUnit5.setLastMonthDay(xydw3LastMonthDay(hotelId, date)+"");
        //协议单位本年年累计
        agreementUnit5.setYear(xydw3Year(hotelId, date)+"");
        //协议单位上年同期
        agreementUnit5.setLastYearDay(xydw3LastYearDay(hotelId, date)+"");
        //协议单位年增长率
        agreementUnit5.setInsertRial(isIncrease(xydw3Year(hotelId, yyyy),xydw3Year(hotelId, year)));



        //直接入住今日
        enter5.setDay(managerdailyBO4.getEnter()+"");
        //直接入住本月累计
        enter5.setMonth(zjrz3Month(hotelId, date)+"");
        //直接入住上月同期
        enter5.setLastMonthDay(zjrz3LastMonthDay(hotelId, date)+"");
        //直接入住本年年累计
        enter5.setYear(zjrz3Year(hotelId, date)+"");
        //直接入住上年同期
        enter5.setLastYearDay(zjrz3LastYearDay(hotelId, date)+"");
        //直接入住年增长率
        enter5.setInsertRial(isIncrease(zjrz3Year(hotelId, yyyy),zjrz3Year(hotelId, year)));

        //房间预订今日
        directBooking5.setDay(managerdailyBO4.getDirectBooking()+"");
        //房间预订本月累计
        directBooking5.setMonth(fjyd3Month(hotelId,date)+"");
        //房间预订上月同期
        directBooking5.setLastMonthDay(fjyd3LastMonthDay(hotelId, date)+"");
        //房间预定本年年累计
        directBooking5.setYear(fjyd3Year(hotelId, date)+"");
        //房间预定上年同期
        directBooking5.setLastYearDay(fjyd3LastYearDay(hotelId, date)+"");
        //房间预定年增长率
        directBooking5.setInsertRial(isIncrease(fjyd3Year(hotelId, yyyy),fjyd3Year(hotelId, year)));


        //小计今日
        subtotal5.setDay(managerdailyBO4.getSubtotal()+"");
        //小计本月累计
        subtotal5.setMonth(xj4Month(hotelId, date)+"");
        //小计上月同期
        subtotal5.setLastMonthDay(xj4LastMonthDay(hotelId, date)+"");
        //小计本年年累计
        subtotal5.setYear(xj4Year(hotelId, date)+"");
        //小计上年同期
        subtotal5.setLastYearDay(xj4LastYearDay(hotelId, date)+"");
        //小计年增长率
        subtotal5.setInsertRial(isIncrease(xj4Year(hotelId, yyyy),xj4Year(hotelId, year)));

        //出租率分析
        //会员出租率本天
        members6.setDay(managerdailyBO5.getMembers()+"");
        //本月累计
        //本月会员房数除以本月小计
        members6.setMonth((hy2Month(hotelId,date)/xj3Month(hotelId, date))+"");
        //获取上月同期
        members6.setLastMonthDay(hyrzlLastMonthDay(hotelId,date).getMembers()+"");
        //获取年会员出租率
        //年会员房数处于本年小计
        members6.setLastYearDay((hy2Year(hotelId, date)/xj3Year(hotelId, date))+"");
        //获取上年同期出租率
        members6.setLastYearDay(hyrzLastYearDay(hotelId, date).getMembers()+"");
        //年增长率 todo


        //获取散客当天出租率
        individualTraveler6.setDay(managerdailyBO5.getIndividualTraveler()+"");
        //获取散客本月累计出租率
        individualTraveler6.setMonth((sk2Month(hotelId, date)/xj3Month(hotelId, date))+"");
        //获取上月同期
        individualTraveler6.setMonth(hyrzlLastMonthDay(hotelId, date).getIndividualTraveler()+"");
        //获取上年累计
        individualTraveler6.setYear(sk2Year(hotelId, date)/xj3Year(hotelId, date)+"");
        //获取上年同期
        individualTraveler6.setLastYearDay(hyrzLastYearDay(hotelId, date).getIndividualTraveler()+"");


        //获取协议单位当天出租率
        agreementUnit6.setDay(managerdailyBO5.getAgreementUnit()+"");
        //获取协议单位本月出租率
        agreementUnit6.setMonth(xydw2Month(hotelId, date)/xj3Month(hotelId, date)+"");
        //获取协议单位上月同期
        agreementUnit6.setLastMonthDay(hyrzlLastMonthDay(hotelId, date).getAgreementUnit()+"");
        //获取上年累计
        agreementUnit6.setYear(xydw2Year(hotelId, date)/xj3Year(hotelId, date)+"");
        //获取上年同期
        agreementUnit6.setLastYearDay(hyrzLastYearDay(hotelId, date).getAgreementUnit()+"");

        //获取直接入住当天出租率
        enter6.setDay(managerdailyBO5.getEnter()+"");
        //获取本月直接入住出租率
        enter6.setMonth(zjrz2Month(hotelId, date)/xj3Month(hotelId, date)+"");
        //获取直接入住上月同期
        enter6.setLastMonthDay(hyrzlLastMonthDay(hotelId, date).getEnter()+"");
        //获取上年累计
        enter6.setYear(xydw3Year(hotelId, date)/xj4Year(hotelId, date)+"");
        //获取上年同期
        enter6.setLastYearDay(hyrzLastYearDay(hotelId, date).getEnter()+"");


        //获取预约入住当天出租率
        directBooking6.setDay(managerdailyBO5.getDirectBooking()+"");
        //获取预约入住本月出租率
        directBooking6.setMonth(fjyd3Month(hotelId,date)/xj4Month(hotelId, date)+"");
        //获取预约入住上月同期
        directBooking6.setLastMonthDay(hyrzlLastMonthDay(hotelId, date).getDirectBooking()+"");
        //获取预约入住上年累计
        directBooking6.setYear(fjyd3Year(hotelId, date)/xj4Year(hotelId, date)+"");
        //获取上年同期
        directBooking6.setLastYearDay(hyrzLastYearDay(hotelId, date).getDirectBooking()+"");


        //小计
        //获取今日
        subtotal6.setDay(managerdailyBO5.getSubtotal()+"");
        //获取本月累计
        subtotal6.setMonth(xj3Month(hotelId, date)/xj3Month(hotelId, date)+"");
        //获取上月同期
        subtotal6.setLastMonthDay(hyrzlLastMonthDay(hotelId, date).getSubtotal()+"");
        //获取上年累计
        subtotal6.setYear(xj3Year(hotelId, date)/xj3Year(hotelId, date)+"");



        return null;
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
    private double month(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));

        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId,new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 1);
        double n = 0.0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n  = n + managerdailyBO.getGrossrealIncome();
        }
        return n;
    }


    /**
     * 获取上月同期总实际收入
     * @return
     */
    private double  lastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1 , 1);
        double n = 0.0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n  = n + managerdailyBO.getGrossrealIncome();
        }
        return n;
    }

    /**
     * 获取总实际收入的本年累计
     * @param hotelId
     * @param date
     * @return
     */
    private double year(Integer hotelId, String date){
        //获取今年日期
        String yyyy = new SimpleDateFormat("yyyy").format(date);
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, yyyy, 1);
        double n = 0.0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n  = n + managerdailyBO.getGrossrealIncome();
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
    private double lastYearDay(Integer hotelId, String date) {
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 1);
        double n = 0.0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n  = n + managerdailyBO.getGrossrealIncome();
        }
        return n;
    }


    /**
     * 获取本月累计营业额
     * @return
     */
    private double queryMonth(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 1);
        double n = 0.0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getTotalTurnover();
        }
        return n;
    }


    /**
     * 获取上月同期总营业额
     * @param hotelId
     * @return
     */
    private double queryLastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 1);
        double n = 0.0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n + managerdailyBO.getTotalTurnover();
        }
        return n ;
    }

    /**
     * 获取本年积累的总营业额
     * @param hotelId
     * @param date
     * @return
     */
    private double queryYear(Integer hotelId, String date){
        //获取今年日期
        String yyyy = new SimpleDateFormat("yyyy").format(date);
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, yyyy, 1);
        double n = 0.0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n  = n + managerdailyBO.getTotalTurnover();
        }
        return n;
    }

    /**
     * 获取总营业额的去年同期
     * @param hotelId
     * @param date
     * @return
     */
    private double queryLastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 1);
        double n = 0.0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n  = n + managerdailyBO.getGrossrealIncome();
        }
        return n;
    }

    /**
     * 计算本月累计预订未到房数
     * @param hotelId
     * @param date
     * @return
     */
    private int ydDay(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 1);
        int n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getNumberOrder();
        }
        return n;
    }

    //获取上月同期 预订未到房数
    private int ydLastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 1);
        int n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = n + managerdailyBO.getNumberOrder();
        }
        return n ;
    }

    //获取本年累计 预订未到房数
    private int ydYear(Integer hotelId, String date){
        //获取今年日期
        String yyyy = new SimpleDateFormat("yyyy").format(date);
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, yyyy, 1);
        int n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n  = n + managerdailyBO.getNumberOrder();
        }
        return n;
    }


    //获取上年同期 预订未到房数
    private double ydLastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 1);
        int n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n  = n + managerdailyBO.getNumberOrder();
        }
        return n;
    }


    //计算本月累计维修房数
    private int wxMonth(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 1);
        int n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getMaintenanceroomNumber();
        }
        return n;
    }

    //计算上月同期维修房数
    private int wxLastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 1);
        int n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getMaintenanceroomNumber();
        }
        return n ;
    }

    //计算本年累计维修房数
    private int wxYear(Integer hotelId, String date){
        //获取今年日期
        String yyyy = new SimpleDateFormat("yyyy").format(date);
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, yyyy, 1);
        int n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getMaintenanceroomNumber();
        }
        return n;
    }

    //上年同期维修房数
    private int wxLastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 1);
        int n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getMaintenanceroomNumber();
        }
        return n;
    }

    //获取本月累计门店锁房数
    private int sfMonth(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 1);
        int n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getNumberlockedStores();
        }
        return n;
    }
    //获取上月同期门店锁房数
    private int sfLastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 1);
        int n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getNumberlockedStores();
        }
        return n ;
    }
    //获取本年累计门店锁房数
    private int sfYear(Integer hotelId, String date){
        //获取今年日期
        String yyyy = new SimpleDateFormat("yyyy").format(date);
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, yyyy, 1);
        int n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getNumberlockedStores();
        }
        return n;
    }
    //获取上年同期门店锁房数
    private int sfLastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 1);
        int n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getNumberlockedStores();
        }
        return n;
    }

    //获取本月可出租房数
    private int zfMonth(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 1);
        int n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getNumberroomsAvailablerent();
        }
        return n;
    }
    //获取上月同期可出租房数
    private int zfLastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 1);
        int n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getNumberroomsAvailablerent();
        }
        return n ;
    }

    //获取本年累计可出租房数
    private int zfYear(Integer hotelId, String date){
        //获取今年日期
        String yyyy = new SimpleDateFormat("yyyy").format(date);
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, yyyy, 1);
        int n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getNumberroomsAvailablerent();
        }
        return n;
    }
    //获取上年同期可出租房数
    private int zfLastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 1);
        int n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getNumberroomsAvailablerent();
        }
        return n;
    }


    //获取本月累计客房总数
    private int kfMonth(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 1);
        int n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getTotalnumberGuestrooms();
        }
        return n;
    }

    //获取上月同期客房总数
    private int kfLastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 1);
        int n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getTotalnumberGuestrooms();
        }
        return n ;
    }

    //获取本年累计客房总数
    private int kfYear(Integer hotelId, String date){
        //获取今年日期
        String yyyy = new SimpleDateFormat("yyyy").format(date);
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, yyyy, 1);
        int n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getTotalnumberGuestrooms();
        }
        return n;
    }

    //获取上年同期客房总数
    private int kfLastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 1);
        int n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getTotalnumberGuestrooms();
        }
        return n;
    }

    //获取本月累计现金支出
    private double zcMonth(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 1);
        double n = 0.0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getCashDisbursements();
        }
        return n;
    }
    //获取上月同期现金支出
    private double zcLastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 1);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getCashDisbursements();
        }
        return n ;
    }
    //获取本年累计现金支出
    private double zcYear(Integer hotelId, String date){
        //获取今年日期
        String yyyy = new SimpleDateFormat("yyyy").format(date);
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, yyyy, 1);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getCashDisbursements();
        }
        return n;
    }
    //获取上年同期现金支出
    private double zcLastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 1);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getCashDisbursements();
        }
        return n;
    }

    //获取本月累计现金收入
    private double srMonth(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 1);
        double n = 0.0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getCash();
        }
        return n;
    }

    //获取上月同期现金收入
    private double srLastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 1);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getCash();
        }
        return n ;
    }

    //获取本年累计现金收入
    private double srYear(Integer hotelId, String date){
        //获取今年日期
        String yyyy = new SimpleDateFormat("yyyy").format(date);
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, yyyy, 1);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getCash();
        }
        return n;
    }

    //获取上年同期现金收入
    private double srLastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 1);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getCash();
        }
        return n;
    }



    //获取全天日租本月累计
    private double qtrzMonth(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 2);
        double n = 0.0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getThroughoutDayrent();
        }
        return n;
    }

    //获取全天日租上月同期
    private double qtrzLastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 2);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getThroughoutDayrent();
        }
        return n ;
    }

    //获取全天日租本年积累
    private double qtrzYear(Integer hotelId, String date){
        //获取今年日期
        String yyyy = new SimpleDateFormat("yyyy").format(date);
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, yyyy, 2);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getThroughoutDayrent();
        }
        return n;
    }
    //获取全天日租上年同期
    private double qtrzLastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 2);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getThroughoutDayrent();
        }
        return n;
    }




    //房费调整本月累计
    private double fftzMonth(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 2);
        double n = 0.0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getRateAdjustment();
        }
        return n;
    }
    //房费调整上月同期
    private double fftzLastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 2);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getRateAdjustment();
        }
        return n ;
    }
    //房费调整本年累计
    private double fftzYear(Integer hotelId, String date){
        //获取今年日期
        String yyyy = new SimpleDateFormat("yyyy").format(date);
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, yyyy, 2);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getRateAdjustment();
        }
        return n;
    }
    //房费调整上年同期
    private double fftzLastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 2);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getRateAdjustment();
        }
        return n;
    }





     //钟点房费本月累计
     private double zdffMonth(Integer hotelId, String date){
         //当月第一天
         Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
         //当月最后一天
         Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
         List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                 new SimpleDateFormat("yyyy-MM-dd").format(dete2), 2);
         double n = 0.0;
         for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
             n = managerdailyBO.getHourRate();
         }
         return n;
     }
    //钟点房费上月同期
    private double zdffLastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 2);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getHourRate();
        }
        return n ;
    }
    //钟点房费本年累计
    private double zdffYear(Integer hotelId, String date){
        //获取今年日期
        String yyyy = new SimpleDateFormat("yyyy").format(date);
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, yyyy, 2);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getHourRate();
        }
        return n;
    }
    //钟点房费上年同期
    private double zdffLastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 2);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getHourRate();
        }
        return n;
    }
    //钟点房费年增长率









    //超时房费本月累计
    private double csffMonth(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 2);
        double n = 0.0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getTimeoutRate();
        }
        return n;
    }
    //超时房费上月同期
    private double csffLastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 2);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getTimeoutRate();
        }
        return n ;
    }
    //超时房费本年累计
    private double csffYear(Integer hotelId, String date){
        //获取今年日期
        String yyyy = new SimpleDateFormat("yyyy").format(date);
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, yyyy, 2);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getTimeoutRate();
        }
        return n;
    }
    //超时房费上年同期
    private double csffLastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 2);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getTimeoutRate();
        }
        return n;
    }

    //超时房费年增长率





    //夜核房费本月累计
    private double yhffMonth(Integer hotelId, String date){
    //当月第一天
    Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
    //当月最后一天
    Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
    List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
            new SimpleDateFormat("yyyy-MM-dd").format(dete2), 2);
    double n = 0.0;
    for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
        n = managerdailyBO.getNuclearnightRoomcharge();
    }
    return n;
}
    //夜核房费上月同期
    private double yhffLastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 2);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getNuclearnightRoomcharge();
        }
        return n ;
    }
    //夜核房费本年累计
    private double yhffYear(Integer hotelId, String date){
        //获取今年日期
        String yyyy = new SimpleDateFormat("yyyy").format(date);
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, yyyy, 2);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getNuclearnightRoomcharge();
        }
        return n;
    }
    //夜核房费上年同期
    private double yhffLastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 2);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getNuclearnightRoomcharge();
        }
        return n;
    }
    //夜核房费年增长率









    //赔偿本月累计
    private double pcMonth(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 2);
        double n = 0.0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getCompensation();
        }
        return n;
    }
    //赔偿上月同期
    private double pcLastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 2);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getCompensation();
        }
        return n ;
    }
    //赔偿上年同期
    private double pcYear(Integer hotelId, String date){
        //获取今年日期
        String yyyy = new SimpleDateFormat("yyyy").format(date);
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, yyyy, 2);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getCompensation();
        }
        return n;
    }
    //赔偿年增长率
    private double pcLastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 2);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getCompensation();
        }
        return n;
    }








    //会员卡费本月累计
    private double hykfMonth(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 2);
        double n = 0.0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getMembershipFee();
        }
        return n;
    }

    //会员卡费上月同期
    private double hykfLastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 2);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getMembershipFee();
        }
        return n ;
    }
    //会员卡费本年累计
    private double hykfYear(Integer hotelId, String date){
        //获取今年日期
        String yyyy = new SimpleDateFormat("yyyy").format(date);
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, yyyy, 2);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getMembershipFee();
        }
        return n;
    }
    ///会员卡费上年同期
    private double hykfLastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 2);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getMembershipFee();
        }
        return n;
    }





    //商品本月累计
    private double spMonth(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 2);
        double n = 0.0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getGoods();
        }
        return n;
    }
    //商品上月同期
    private double spLastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 2);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getGoods();
        }
        return n ;
    }
    //商品本年累计
    private double spYear(Integer hotelId, String date){
        //获取今年日期
        String yyyy = new SimpleDateFormat("yyyy").format(date);
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, yyyy, 2);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getGoods();
        }
        return n;
    }
    //商品上年同期
    private double spLastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 2);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getGoods();
        }
        return n;
    }






    //小计本月累计
    private double xjMonth(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 2);
        double n = 0.0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getSubtotal();
        }
        return n;
    }
    //小计上月同期
    private double xjLastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 2);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getSubtotal();
        }
        return n ;
    }
    //小计本年累计
    private double xjYear(Integer hotelId, String date){
        //获取今年日期
        String yyyy = new SimpleDateFormat("yyyy").format(date);
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, yyyy, 2);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getSubtotal();
        }
        return n;
    }
    //小计上年同期
    private double xjLastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 2);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getSubtotal();
        }
        return n;
    }









    //会员本月累计
    private double hyMonth(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 3);
        double n = 0.0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getMembers();
        }
        return n;
    }
    //会员上月同期
    private double hyLastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 3);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getMembers();
        }
        return n ;
    }
    //会员本年累计
    private double hyYear(Integer hotelId, String date){
        //获取今年日期
        String yyyy = new SimpleDateFormat("yyyy").format(date);
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, yyyy, 3);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getMembers();
        }
        return n;
    }
    //会员上年同期
    private double hyLastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 3);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getMembers();
        }
        return n;
    }
    //会员年增长率






    //散客本月累计
    private double skMonth(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 3);
        double n = 0.0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getIndividualTraveler();
        }
        return n;
    }
    //散客上月同期
    private double skLastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 3);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getIndividualTraveler();
        }
        return n ;
    }
    //散客本年累计
    private double skYear(Integer hotelId, String date){
        //获取今年日期
        String yyyy = new SimpleDateFormat("yyyy").format(date);
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, yyyy, 3);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getIndividualTraveler();
        }
        return n;
    }
    //散客上年同期
    private double skLastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 3);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getIndividualTraveler();
        }
        return n;
    }




    //协议单位本月累计
    private double xydwMonth(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 3);
        double n = 0.0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getAgreementUnit();
        }
        return n;
    }
    //协议单位上月同期
    private double xydwLastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 3);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getAgreementUnit();
        }
        return n ;
    }
    //协议单位本年累计
    private double xydwYear(Integer hotelId, String date){
        //获取今年日期
        String yyyy = new SimpleDateFormat("yyyy").format(date);
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, yyyy, 3);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getAgreementUnit();
        }
        return n;
    }
    //协议单位上年同期
    private double xydwLastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 3);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getAgreementUnit();
        }
        return n;
    }









    //直接入住本月累计
    private double zjrzMonth(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 3);
        double n = 0.0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getEnter();
        }
        return n;
    }
    //直接入住上月同期
    private double zjrzLastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 3);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getEnter();
        }
        return n ;
    }
    //直接入住本年累计
    private double zjrzYear(Integer hotelId, String date){
        //获取今年日期
        String yyyy = new SimpleDateFormat("yyyy").format(date);
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, yyyy, 3);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getEnter();
        }
        return n;
    }
    //直接入住上年同期
    private double zjrzLastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 3);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getEnter();
        }
        return n;
    }





    //预约入住本月累计
    private double yyrzMonth(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 3);
        double n = 0.0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getSubtotal();
        }
        return n;
    }
    //预约入住上月同期
    private double yyrzLastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 3);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getSubtotal();
        }
        return n ;
    }
    //预约入住本年累计
    private double yyrzYear(Integer hotelId, String date){
        //获取今年日期
        String yyyy = new SimpleDateFormat("yyyy").format(date);
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, yyyy, 3);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getSubtotal();
        }
        return n;
    }
    //预约入住上年同期
    private double yyrzLastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 3);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getSubtotal();
        }
        return n;
    }







    //小计本月累计
    private double xj2Month(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 3);
        double n = 0.0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getDirectBooking();
        }
        return n;
    }
    //小计上月同期
    private double xj2LastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 3);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getDirectBooking();
        }
        return n ;
    }
    //小计本年累计
    private double xj2Year(Integer hotelId, String date){
        //获取今年日期
        String yyyy = new SimpleDateFormat("yyyy").format(date);
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, yyyy, 3);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getDirectBooking();
        }
        return n;
    }
    //小计上年同期
    private double xj2LastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 3);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getDirectBooking();
        }
        return n;
    }








    //会员本月累计
    private double hy2Month(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 4);
        double n = 0.0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getMembers();
        }
        return n;
    }
    //会员上月同期
    private double hy2LastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 4);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getMembers();
        }
        return n ;
    }
    //会员本年累计
    private double hy2Year(Integer hotelId, String date){
        //获取今年日期
        String yyyy = new SimpleDateFormat("yyyy").format(date);
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, yyyy, 4);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getMembers();
        }
        return n;
    }
    //会员上年同期
    private double hy2LastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 4);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getMembers();
        }
        return n;
    }








    //散客本月累计
    private double sk2Month(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 4);
        double n = 0.0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getIndividualTraveler();
        }
        return n;
    }
    //散客上月同期
    private double sk2LastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 4);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getIndividualTraveler();
        }
        return n ;
    }
    //散客本年累计
    private double sk2Year(Integer hotelId, String date){
        //获取今年日期
        String yyyy = new SimpleDateFormat("yyyy").format(date);
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, yyyy, 4);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getIndividualTraveler();
        }
        return n;
    }
    //散客上年同期
    private double sk2LastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 4);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getIndividualTraveler();
        }
        return n;
    }







    //协议单位本月累计
    private double xydw2Month(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 4);
        double n = 0.0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getIndividualTraveler();
        }
        return n;
    }
    //协议单位上月同期
    private double xydw2LastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 4);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getIndividualTraveler();
        }
        return n ;
    }
    //协议单位本年累计
    private double xydw2Year(Integer hotelId, String date){
        //获取今年日期
        String yyyy = new SimpleDateFormat("yyyy").format(date);
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, yyyy, 4);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getIndividualTraveler();
        }
        return n;
    }
    //协议单位上年同期
    private double xydw2LastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 4);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getIndividualTraveler();
        }
        return n;
    }







    //直接入住本月累计
    private double zjrz2Month(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 4);
        double n = 0.0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getEnter();
        }
        return n;
    }
    //直接入住上月同期
    private double zjrz2LastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 4);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getEnter();
        }
        return n ;
    }
    //直接入住本年累计
    private double zjrz2Year(Integer hotelId, String date){
        //获取今年日期
        String yyyy = new SimpleDateFormat("yyyy").format(date);
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, yyyy, 4);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getEnter();
        }
        return n;
    }
    //直接入住上年同期
    private double zjrz2LastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 4);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getEnter();
        }
        return n;
    }






    //房间预订本月累计
    private double fjyd2Month(Integer hotelId, String date){
    //当月第一天
    Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
    //当月最后一天
    Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
    List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
            new SimpleDateFormat("yyyy-MM-dd").format(dete2), 4);
    double n = 0.0;
    for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
        n = managerdailyBO.getDirectBooking();
    }
    return n;
}
    //房间预订上月同期
    private double fjyd2LastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 4);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getDirectBooking();
        }
        return n ;
    }
    //房间预订本年累计
    private double fjyd2Year(Integer hotelId, String date){
        //获取今年日期
        String yyyy = new SimpleDateFormat("yyyy").format(date);
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, yyyy, 4);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getDirectBooking();
        }
        return n;
    }
    //房间预订上年同期
    private double fjyd2LastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 4);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getDirectBooking();
        }
        return n;
    }







    //小计本月累计
    private double xj3Month(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 4);
        double n = 0.0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getSubtotal();
        }
        return n;
    }
    //小计上月同期
    private double xj3LastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 4);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getSubtotal();
        }
        return n ;
    }
    //小计本年累计
    private double xj3Year(Integer hotelId, String date){
        //获取今年日期
        String yyyy = new SimpleDateFormat("yyyy").format(date);
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, yyyy, 4);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getSubtotal();
        }
        return n;
    }
    //小计上年同期
    private double xj3LastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 4);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getSubtotal();
        }
        return n;
    }







    //会员本月累计
    private double hyfj2Month(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 5);
        double n = 0.0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getMembers();
        }
        return n;
    }
    //会员上月同期
    private double hyfj2LastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 5);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getMembers();
        }
        return n ;
    }
    //会员本年年累计
    private double hyfj2Year(Integer hotelId, String date){
        //获取今年日期
        String yyyy = new SimpleDateFormat("yyyy").format(date);
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, yyyy, 5);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getMembers();
        }
        return n;
    }
    //会员上年同期
    private double hyfj2LastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 5);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getMembers();
        }
        return n;
    }





    //散客本月累计
    private double sk3Month(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 5);
        double n = 0.0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getIndividualTraveler();
        }
        return n;
    }
    //散客上月同期
    private double sk3LastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 5);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getIndividualTraveler();
        }
        return n ;
    }
    //散客本年年累计
    private double sk3Year(Integer hotelId, String date){
        //获取今年日期
        String yyyy = new SimpleDateFormat("yyyy").format(date);
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, yyyy, 5);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getIndividualTraveler();
        }
        return n;
    }
    //散客上年同期
    private double sk3LastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 5);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getIndividualTraveler();
        }
        return n;
    }






    //协议单位本月累计
    private double xydw3Month(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 5);
        double n = 0.0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getAgreementUnit();
        }
        return n;
    }
    //协议单位上月同期
    private double xydw3LastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 5);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getAgreementUnit();
        }
        return n ;
    }
    //协议单位本年年累计
    private double xydw3Year(Integer hotelId, String date){
        //获取今年日期
        String yyyy = new SimpleDateFormat("yyyy").format(date);
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, yyyy, 5);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getAgreementUnit();
        }
        return n;
    }
    //协议单位上年同期
    private double xydw3LastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 5);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getAgreementUnit();
        }
        return n;
    }








    //直接入住本月累计
    private double zjrz3Month(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 5);
        double n = 0.0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getEnter();
        }
        return n;
    }
    //协议单位上月同期
    private double zjrz3LastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 5);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getEnter();
        }
        return n ;
    }
    //直接入住本年年累计
    private double zjrz3Year(Integer hotelId, String date){
        //获取今年日期
        String yyyy = new SimpleDateFormat("yyyy").format(date);
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, yyyy, 5);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getEnter();
        }
        return n;
    }
    //直接入住上年同期
    private double zjrz3LastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 5);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getEnter();
        }
        return n;
    }








    //房间预订本月累计
    private double fjyd3Month(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 5);
        double n = 0.0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getDirectBooking();
        }
        return n;
    }
    //房间预订上月同期
    private double fjyd3LastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 5);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getDirectBooking();
        }
        return n ;
    }
    //房间预定本年年累计
    private double fjyd3Year(Integer hotelId, String date){
        //获取今年日期
        String yyyy = new SimpleDateFormat("yyyy").format(date);
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, yyyy, 5);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getDirectBooking();
        }
        return n;
    }
    //房间预定上年同期
    private double fjyd3LastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 5);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getDirectBooking();
        }
        return n;
    }







    //小计本月累计
    private double xj4Month(Integer hotelId, String date){
        //当月第一天
        Date date1 = getFirstDayDateOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //当月最后一天
        Date dete2 = getLastDayOfMonth(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList(hotelId, new SimpleDateFormat("yyyy-MM-dd").format(date1),
                new SimpleDateFormat("yyyy-MM-dd").format(dete2), 5);
        double n = 0.0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getSubtotal();
        }
        return n;
    }
    //小计上月同期
    private double xj4LastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, date1, 5);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getSubtotal();
        }
        return n ;
    }
    //小计本年年累计
    private double xj4Year(Integer hotelId, String date){
        //获取今年日期
        String yyyy = new SimpleDateFormat("yyyy").format(date);
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryYear(hotelId, yyyy, 5);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getSubtotal();
        }
        return n;
    }
    //小计上年同期
    private double xj4LastYearDay(Integer hotelId, String date){
        //获取上一年的今日
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        List<ManagerdailyBO> managerdailyBOS = managerdailyBOMapper.queryManagerdailyList2(hotelId, year, 5);
        double n = 0;
        for (ManagerdailyBO managerdailyBO : managerdailyBOS) {
            n = managerdailyBO.getSubtotal();
        }
        return n;
    }


    //会员入住率上月同期
    private ManagerdailyBO hyrzlLastMonthDay(Integer hotelId, String date){
        //获取上个月的今天
        String date1 = isDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
        ManagerdailyBO managerdailyBO = managerdailyBOMapper.queryHy(hotelId, date, 6);
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
    public String isIncreas(Integer hotelId,String date){
        //获取今年日期
        String yyyy = new SimpleDateFormat("yyyy").format(date);
        //获取上一年日期
        String year = isYear(DateUtils.parseDate(date, "yyyy-MM-dd"));
        //计算公式 增长率=增量/原总量*100%。
        //获取今年总量
        double year1 = year(hotelId, yyyy);
        //获取去年总量
        double year2 = year(hotelId, year);
        double n = (year1-year2)/year2*100;
        return n+"%";
    }

    /**
     *  计算年增长率
     * @return
     */
    public String isIncrease(double year1, double year2){
        double n = (year1-year2)/year2*100;
        return n+"%";
    }











    /**
     * 插入经理日报
     * @param hotelId
     */
    public void insertManagerDaliy(Integer hotelId,String date){
        String specifiedDayBefore = DateUtils.getSpecifiedDayBefore(date);
        String startTime = specifiedDayBefore + " 04:00:00";
        String endTime = date + " 04:00:00";
        ManagerdailyBO managerdailyBO = new ManagerdailyBO();


        //添加营业状况统计
        managerdailyBO.setGrossrealIncome(orderTotalPrice(hotelId, startTime, endTime));//总实际收入
        managerdailyBO.setTotalTurnover(queryConsumption(hotelId, startTime, endTime));//总营业额
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
        managerdailyBO2.setThroughoutDayrent((double)throughoutDayrent(hotelId, startTime, endTime));//全天日租
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
    private int throughoutDayrent(Integer hotelId, String startTime, String endTime){
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


    /**
     * 查询今天营业额
     * @param hotelId
     * @param startTime
     * @param endTime
     * @return
     */
    private double orderTotalPrice(Integer hotelId, String startTime, String endTime){
        List<TotalPriceBO> totalPriceBOS = managerdailyBOMapper.queryOrderTotalPrice(hotelId, startTime, endTime);
        double n = 0;
        for (TotalPriceBO totalPriceBO : totalPriceBOS) {
            n = n +totalPriceBO.getTotalPrice();
        }
        return n;
    }

    /**
     * 获取当天总收入
     * @param hotelId
     * @param startTime
     * @param endTime
     * @return
     */
    private double queryConsumption(Integer hotelId, String startTime, String endTime){
        List<CashierSummary> cashierSummaries = managerdailyBOMapper.queryConsumption(hotelId, startTime, endTime);
        double n = 0;
        for (CashierSummary cashierSummary : cashierSummaries) {
            n = n + cashierSummary.getConsumption();
        }
        return n;
    }


}
