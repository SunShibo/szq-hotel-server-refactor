package com.szq.hotel.entity.bo;

import java.util.Date;

/**
 * @Author: Bin Wang
 * @date: Created in 10:17 2019/7/26
 */
public class ManagerdailyChangeBO {

    private HotelTableBO grossrealIncome;//总实际收入
    private HotelTableBO totalTurnover;//总营业额
    private HotelTableBO numberOrder;//预订未到的房数
    private HotelTableBO maintenanceroomNumber;//维修房子数
    private HotelTableBO numberlockedStores;//门店锁房数
    private HotelTableBO numberroomsAvailablerent;//可出租房数
    private HotelTableBO totalnumberGuestrooms;//客房总数
    private HotelTableBO cashDisbursements;//现金支出
    private HotelTableBO cash;//现金



    private HotelTableBO throughoutDayrent;//全天日租
    private HotelTableBO rateAdjustment;//房费调整
    private HotelTableBO hourRate;//钟点房费
    private HotelTableBO timeoutRate;//超时房费
    private HotelTableBO nuclearnightRoomcharge;//夜核房费
    private HotelTableBO compensation;//赔偿
    private HotelTableBO membershipFee;//会员卡费
    private HotelTableBO goods;//商品
    private HotelTableBO subtotal2;//小计


    private HotelTableBO members3;//会员
    private HotelTableBO agreementUnit3;//协议单位
    private HotelTableBO intermediary3;//中介
    private HotelTableBO app3;//app
    private HotelTableBO microLetter3;//微信
    private HotelTableBO individualTraveler3;//散客
    private HotelTableBO directBooking3;//直接预订
    private HotelTableBO enter3;//步入
    private HotelTableBO subtotal3;//小计


    private HotelTableBO members4;//会员
    private HotelTableBO agreementUnit4;//协议单位
    private HotelTableBO intermediary4;//中介
    private HotelTableBO app4;//app
    private HotelTableBO microLetter4;//微信
    private HotelTableBO individualTraveler4;//散客
    private HotelTableBO directBooking4;//直接预订
    private HotelTableBO enter4;//步入
    private HotelTableBO subtotal4;//小计



    private HotelTableBO members5;//会员
    private HotelTableBO agreementUnit5;//协议单位
    private HotelTableBO intermediary5;//中介
    private HotelTableBO app5;//app
    private HotelTableBO microLetter5;//微信
    private HotelTableBO individualTraveler5;//散客
    private HotelTableBO directBooking5;//直接预订
    private HotelTableBO enter5;//步入
    private HotelTableBO subtotal5;//小计

    private HotelTableBO members6;//会员
    private HotelTableBO agreementUnit6;//协议单位
    private HotelTableBO intermediary6;//中介
    private HotelTableBO app6;//app
    private HotelTableBO microLetter6;//微信
    private HotelTableBO individualTraveler6;//散客
    private HotelTableBO directBooking6;//直接预订
    private HotelTableBO enter6;//步入
    private HotelTableBO subtotal6;//小计


    private Integer dailyType;//1:营业状况统计,2:营业收入明细,3:房费收入分析,4:房晚数分析,5:平均房价分析,6:出租率分析
    private Integer hotelId;//酒店id

    private Date dateTime;//日期


    public HotelTableBO getGrossrealIncome() {
        return grossrealIncome;
    }

    public void setGrossrealIncome(HotelTableBO grossrealIncome) {
        this.grossrealIncome = grossrealIncome;
    }

    public HotelTableBO getTotalTurnover() {
        return totalTurnover;
    }

    public void setTotalTurnover(HotelTableBO totalTurnover) {
        this.totalTurnover = totalTurnover;
    }

    public HotelTableBO getNumberOrder() {
        return numberOrder;
    }

    public void setNumberOrder(HotelTableBO numberOrder) {
        this.numberOrder = numberOrder;
    }

    public HotelTableBO getMaintenanceroomNumber() {
        return maintenanceroomNumber;
    }

    public void setMaintenanceroomNumber(HotelTableBO maintenanceroomNumber) {
        this.maintenanceroomNumber = maintenanceroomNumber;
    }

    public HotelTableBO getNumberlockedStores() {
        return numberlockedStores;
    }

    public void setNumberlockedStores(HotelTableBO numberlockedStores) {
        this.numberlockedStores = numberlockedStores;
    }

    public HotelTableBO getNumberroomsAvailablerent() {
        return numberroomsAvailablerent;
    }

    public void setNumberroomsAvailablerent(HotelTableBO numberroomsAvailablerent) {
        this.numberroomsAvailablerent = numberroomsAvailablerent;
    }

    public HotelTableBO getTotalnumberGuestrooms() {
        return totalnumberGuestrooms;
    }

    public void setTotalnumberGuestrooms(HotelTableBO totalnumberGuestrooms) {
        this.totalnumberGuestrooms = totalnumberGuestrooms;
    }

    public HotelTableBO getCashDisbursements() {
        return cashDisbursements;
    }

    public void setCashDisbursements(HotelTableBO cashDisbursements) {
        this.cashDisbursements = cashDisbursements;
    }

    public HotelTableBO getCash() {
        return cash;
    }

    public void setCash(HotelTableBO cash) {
        this.cash = cash;
    }

    public HotelTableBO getThroughoutDayrent() {
        return throughoutDayrent;
    }

    public void setThroughoutDayrent(HotelTableBO throughoutDayrent) {
        this.throughoutDayrent = throughoutDayrent;
    }

    public HotelTableBO getRateAdjustment() {
        return rateAdjustment;
    }

    public void setRateAdjustment(HotelTableBO rateAdjustment) {
        this.rateAdjustment = rateAdjustment;
    }

    public HotelTableBO getHourRate() {
        return hourRate;
    }

    public void setHourRate(HotelTableBO hourRate) {
        this.hourRate = hourRate;
    }

    public HotelTableBO getTimeoutRate() {
        return timeoutRate;
    }

    public void setTimeoutRate(HotelTableBO timeoutRate) {
        this.timeoutRate = timeoutRate;
    }

    public HotelTableBO getNuclearnightRoomcharge() {
        return nuclearnightRoomcharge;
    }

    public void setNuclearnightRoomcharge(HotelTableBO nuclearnightRoomcharge) {
        this.nuclearnightRoomcharge = nuclearnightRoomcharge;
    }

    public HotelTableBO getCompensation() {
        return compensation;
    }

    public void setCompensation(HotelTableBO compensation) {
        this.compensation = compensation;
    }

    public HotelTableBO getMembershipFee() {
        return membershipFee;
    }

    public void setMembershipFee(HotelTableBO membershipFee) {
        this.membershipFee = membershipFee;
    }

    public HotelTableBO getGoods() {
        return goods;
    }

    public void setGoods(HotelTableBO goods) {
        this.goods = goods;
    }

    public HotelTableBO getSubtotal2() {
        return subtotal2;
    }

    public void setSubtotal2(HotelTableBO subtotal2) {
        this.subtotal2 = subtotal2;
    }

    public HotelTableBO getMembers3() {
        return members3;
    }

    public void setMembers3(HotelTableBO members3) {
        this.members3 = members3;
    }

    public HotelTableBO getAgreementUnit3() {
        return agreementUnit3;
    }

    public void setAgreementUnit3(HotelTableBO agreementUnit3) {
        this.agreementUnit3 = agreementUnit3;
    }

    public HotelTableBO getIntermediary3() {
        return intermediary3;
    }

    public void setIntermediary3(HotelTableBO intermediary3) {
        this.intermediary3 = intermediary3;
    }

    public HotelTableBO getApp3() {
        return app3;
    }

    public void setApp3(HotelTableBO app3) {
        this.app3 = app3;
    }

    public HotelTableBO getMicroLetter3() {
        return microLetter3;
    }

    public void setMicroLetter3(HotelTableBO microLetter3) {
        this.microLetter3 = microLetter3;
    }

    public HotelTableBO getIndividualTraveler3() {
        return individualTraveler3;
    }

    public void setIndividualTraveler3(HotelTableBO individualTraveler3) {
        this.individualTraveler3 = individualTraveler3;
    }

    public HotelTableBO getDirectBooking3() {
        return directBooking3;
    }

    public void setDirectBooking3(HotelTableBO directBooking3) {
        this.directBooking3 = directBooking3;
    }

    public HotelTableBO getEnter3() {
        return enter3;
    }

    public void setEnter3(HotelTableBO enter3) {
        this.enter3 = enter3;
    }

    public HotelTableBO getSubtotal3() {
        return subtotal3;
    }

    public void setSubtotal3(HotelTableBO subtotal3) {
        this.subtotal3 = subtotal3;
    }

    public HotelTableBO getMembers4() {
        return members4;
    }

    public void setMembers4(HotelTableBO members4) {
        this.members4 = members4;
    }

    public HotelTableBO getAgreementUnit4() {
        return agreementUnit4;
    }

    public void setAgreementUnit4(HotelTableBO agreementUnit4) {
        this.agreementUnit4 = agreementUnit4;
    }

    public HotelTableBO getIntermediary4() {
        return intermediary4;
    }

    public void setIntermediary4(HotelTableBO intermediary4) {
        this.intermediary4 = intermediary4;
    }

    public HotelTableBO getApp4() {
        return app4;
    }

    public void setApp4(HotelTableBO app4) {
        this.app4 = app4;
    }

    public HotelTableBO getMicroLetter4() {
        return microLetter4;
    }

    public void setMicroLetter4(HotelTableBO microLetter4) {
        this.microLetter4 = microLetter4;
    }

    public HotelTableBO getIndividualTraveler4() {
        return individualTraveler4;
    }

    public void setIndividualTraveler4(HotelTableBO individualTraveler4) {
        this.individualTraveler4 = individualTraveler4;
    }

    public HotelTableBO getDirectBooking4() {
        return directBooking4;
    }

    public void setDirectBooking4(HotelTableBO directBooking4) {
        this.directBooking4 = directBooking4;
    }

    public HotelTableBO getEnter4() {
        return enter4;
    }

    public void setEnter4(HotelTableBO enter4) {
        this.enter4 = enter4;
    }

    public HotelTableBO getSubtotal4() {
        return subtotal4;
    }

    public void setSubtotal4(HotelTableBO subtotal4) {
        this.subtotal4 = subtotal4;
    }

    public HotelTableBO getMembers5() {
        return members5;
    }

    public void setMembers5(HotelTableBO members5) {
        this.members5 = members5;
    }

    public HotelTableBO getAgreementUnit5() {
        return agreementUnit5;
    }

    public void setAgreementUnit5(HotelTableBO agreementUnit5) {
        this.agreementUnit5 = agreementUnit5;
    }

    public HotelTableBO getIntermediary5() {
        return intermediary5;
    }

    public void setIntermediary5(HotelTableBO intermediary5) {
        this.intermediary5 = intermediary5;
    }

    public HotelTableBO getApp5() {
        return app5;
    }

    public void setApp5(HotelTableBO app5) {
        this.app5 = app5;
    }

    public HotelTableBO getMicroLetter5() {
        return microLetter5;
    }

    public void setMicroLetter5(HotelTableBO microLetter5) {
        this.microLetter5 = microLetter5;
    }

    public HotelTableBO getIndividualTraveler5() {
        return individualTraveler5;
    }

    public void setIndividualTraveler5(HotelTableBO individualTraveler5) {
        this.individualTraveler5 = individualTraveler5;
    }

    public HotelTableBO getDirectBooking5() {
        return directBooking5;
    }

    public void setDirectBooking5(HotelTableBO directBooking5) {
        this.directBooking5 = directBooking5;
    }

    public HotelTableBO getEnter5() {
        return enter5;
    }

    public void setEnter5(HotelTableBO enter5) {
        this.enter5 = enter5;
    }

    public HotelTableBO getSubtotal5() {
        return subtotal5;
    }

    public void setSubtotal5(HotelTableBO subtotal5) {
        this.subtotal5 = subtotal5;
    }

    public HotelTableBO getMembers6() {
        return members6;
    }

    public void setMembers6(HotelTableBO members6) {
        this.members6 = members6;
    }

    public HotelTableBO getAgreementUnit6() {
        return agreementUnit6;
    }

    public void setAgreementUnit6(HotelTableBO agreementUnit6) {
        this.agreementUnit6 = agreementUnit6;
    }

    public HotelTableBO getIntermediary6() {
        return intermediary6;
    }

    public void setIntermediary6(HotelTableBO intermediary6) {
        this.intermediary6 = intermediary6;
    }

    public HotelTableBO getApp6() {
        return app6;
    }

    public void setApp6(HotelTableBO app6) {
        this.app6 = app6;
    }

    public HotelTableBO getMicroLetter6() {
        return microLetter6;
    }

    public void setMicroLetter6(HotelTableBO microLetter6) {
        this.microLetter6 = microLetter6;
    }

    public HotelTableBO getIndividualTraveler6() {
        return individualTraveler6;
    }

    public void setIndividualTraveler6(HotelTableBO individualTraveler6) {
        this.individualTraveler6 = individualTraveler6;
    }

    public HotelTableBO getDirectBooking6() {
        return directBooking6;
    }

    public void setDirectBooking6(HotelTableBO directBooking6) {
        this.directBooking6 = directBooking6;
    }

    public HotelTableBO getEnter6() {
        return enter6;
    }

    public void setEnter6(HotelTableBO enter6) {
        this.enter6 = enter6;
    }

    public HotelTableBO getSubtotal6() {
        return subtotal6;
    }

    public void setSubtotal6(HotelTableBO subtotal6) {
        this.subtotal6 = subtotal6;
    }

    public Integer getDailyType() {
        return dailyType;
    }

    public void setDailyType(Integer dailyType) {
        this.dailyType = dailyType;
    }

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}
