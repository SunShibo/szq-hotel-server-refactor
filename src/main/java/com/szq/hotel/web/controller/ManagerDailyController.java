package com.szq.hotel.web.controller;

import com.szq.hotel.entity.bo.AdminBO;
import com.szq.hotel.entity.bo.HotelTableBO;
import com.szq.hotel.entity.bo.ManagerdailyChangeBO;
import com.szq.hotel.entity.dto.ResultDTOBuilder;
import com.szq.hotel.service.ManagerDailyService;
import com.szq.hotel.util.DateUtils;
import com.szq.hotel.util.JsonUtils;
import com.szq.hotel.web.controller.base.BaseCotroller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;



/**
 * @Author: Bin Wang
 * @date: Created in 15:25 2019/7/23
 */
@Controller
@RequestMapping("/managerDaliy")
public class ManagerDailyController extends BaseCotroller {

    final static Logger log = LoggerFactory.getLogger(ManagerDailyService.class);

    @Resource
    private ManagerDailyService  managerDailyService;

    @RequestMapping("/selectManagerDaliy")
    public void selectManagerDaliy(HttpServletRequest request, HttpServletResponse response,String date){
        AdminBO userBO = super.getLoginUser(request);
        if (userBO == null) {
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001", "请登录"));
            safeTextPrint(response, json);
            return;
        }
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
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(managerdailyChangeBO));
        super.safeJsonPrint(response, result);
        return;
    }


    /**
     * 经理日报
     * @param request
     * @param response
     * @param
     */
    @RequestMapping("/queryTest")
    public void queryManagerDaliy(HttpServletRequest request, HttpServletResponse response, String date){
        AdminBO userBO = super.getLoginUser(request);


        ManagerdailyChangeBO managerdailyChangeBO = managerDailyService.queryInfo(date, 1);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(managerdailyChangeBO));
        super.safeJsonPrint(response, result);
        return;
    }


    @RequestMapping("/insertTest")
    public void insertTest(HttpServletRequest request, HttpServletResponse response, Integer hotelId){
        AdminBO userBO = super.getLoginUser(request);
        if (userBO == null) {
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001", "请登录"));
            safeTextPrint(response, json);
            return;
        }
        managerDailyService.insertManagerDaliy(userBO.getHotelId(), new SimpleDateFormat("yyyy-MM-dd").format(DateUtils.getYesTaday()));
    }
}
