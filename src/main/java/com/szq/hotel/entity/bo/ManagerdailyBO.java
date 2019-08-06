package com.szq.hotel.entity.bo;

import java.math.BigDecimal;
import java.util.Date;

public class ManagerdailyBO {
    private Integer id;

    private BigDecimal grossrealIncome;

    private BigDecimal totalTurnover;

    private BigDecimal numberOrder;

    private BigDecimal maintenanceroomNumber;

    private BigDecimal numberlockedStores;

    private BigDecimal numberroomsAvailablerent;

    private BigDecimal totalnumberGuestrooms;

    private BigDecimal cashDisbursements;

    private BigDecimal cash;

    private BigDecimal throughoutDayrent;

    private BigDecimal rateAdjustment;

    private BigDecimal hourRate;

    private BigDecimal timeoutRate;

    private BigDecimal nuclearnightRoomcharge;

    private BigDecimal compensation;

    private BigDecimal membershipFee;

    private BigDecimal goods;

    private BigDecimal subtotal;

    private BigDecimal members;

    private BigDecimal agreementUnit;

    private BigDecimal app;

    private BigDecimal microLetter;

    private BigDecimal individualTraveler;

    private BigDecimal directBooking;

    private BigDecimal enter;

    private Integer dailyType;

    private Integer hotelId;

    private Date dateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getGrossrealIncome() {
        return grossrealIncome;
    }

    public void setGrossrealIncome(BigDecimal grossrealIncome) {
        this.grossrealIncome = grossrealIncome;
    }

    public BigDecimal getTotalTurnover() {
        return totalTurnover;
    }

    public void setTotalTurnover(BigDecimal totalTurnover) {
        this.totalTurnover = totalTurnover;
    }

    public BigDecimal getNumberOrder() {
        return numberOrder;
    }

    public void setNumberOrder(BigDecimal numberOrder) {
        this.numberOrder = numberOrder;
    }

    public BigDecimal getMaintenanceroomNumber() {
        return maintenanceroomNumber;
    }

    public void setMaintenanceroomNumber(BigDecimal maintenanceroomNumber) {
        this.maintenanceroomNumber = maintenanceroomNumber;
    }

    public BigDecimal getNumberlockedStores() {
        return numberlockedStores;
    }

    public void setNumberlockedStores(BigDecimal numberlockedStores) {
        this.numberlockedStores = numberlockedStores;
    }

    public BigDecimal getNumberroomsAvailablerent() {
        return numberroomsAvailablerent;
    }

    public void setNumberroomsAvailablerent(BigDecimal numberroomsAvailablerent) {
        this.numberroomsAvailablerent = numberroomsAvailablerent;
    }

    public BigDecimal getTotalnumberGuestrooms() {
        return totalnumberGuestrooms;
    }

    public void setTotalnumberGuestrooms(BigDecimal totalnumberGuestrooms) {
        this.totalnumberGuestrooms = totalnumberGuestrooms;
    }

    public BigDecimal getCashDisbursements() {
        return cashDisbursements;
    }

    public void setCashDisbursements(BigDecimal cashDisbursements) {
        this.cashDisbursements = cashDisbursements;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public BigDecimal getThroughoutDayrent() {
        return throughoutDayrent;
    }

    public void setThroughoutDayrent(BigDecimal throughoutDayrent) {
        this.throughoutDayrent = throughoutDayrent;
    }

    public BigDecimal getRateAdjustment() {
        return rateAdjustment;
    }

    public void setRateAdjustment(BigDecimal rateAdjustment) {
        this.rateAdjustment = rateAdjustment;
    }

    public BigDecimal getHourRate() {
        return hourRate;
    }

    public void setHourRate(BigDecimal hourRate) {
        this.hourRate = hourRate;
    }

    public BigDecimal getTimeoutRate() {
        return timeoutRate;
    }

    public void setTimeoutRate(BigDecimal timeoutRate) {
        this.timeoutRate = timeoutRate;
    }

    public BigDecimal getNuclearnightRoomcharge() {
        return nuclearnightRoomcharge;
    }

    public void setNuclearnightRoomcharge(BigDecimal nuclearnightRoomcharge) {
        this.nuclearnightRoomcharge = nuclearnightRoomcharge;
    }

    public BigDecimal getCompensation() {
        return compensation;
    }

    public void setCompensation(BigDecimal compensation) {
        this.compensation = compensation;
    }

    public BigDecimal getMembershipFee() {
        return membershipFee;
    }

    public void setMembershipFee(BigDecimal membershipFee) {
        this.membershipFee = membershipFee;
    }

    public BigDecimal getGoods() {
        return goods;
    }

    public void setGoods(BigDecimal goods) {
        this.goods = goods;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getMembers() {
        return members;
    }

    public void setMembers(BigDecimal members) {
        this.members = members;
    }

    public BigDecimal getAgreementUnit() {
        return agreementUnit;
    }

    public void setAgreementUnit(BigDecimal agreementUnit) {
        this.agreementUnit = agreementUnit;
    }

    public BigDecimal getApp() {
        return app;
    }

    public void setApp(BigDecimal app) {
        this.app = app;
    }

    public BigDecimal getMicroLetter() {
        return microLetter;
    }

    public void setMicroLetter(BigDecimal microLetter) {
        this.microLetter = microLetter;
    }

    public BigDecimal getIndividualTraveler() {
        return individualTraveler;
    }

    public void setIndividualTraveler(BigDecimal individualTraveler) {
        this.individualTraveler = individualTraveler;
    }

    public BigDecimal getDirectBooking() {
        return directBooking;
    }

    public void setDirectBooking(BigDecimal directBooking) {
        this.directBooking = directBooking;
    }

    public BigDecimal getEnter() {
        return enter;
    }

    public void setEnter(BigDecimal enter) {
        this.enter = enter;
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


    @Override
    public String toString() {
        return "ManagerdailyBO{" +
                "id=" + id +
                ", grossrealIncome=" + grossrealIncome +
                ", totalTurnover=" + totalTurnover +
                ", numberOrder=" + numberOrder +
                ", maintenanceroomNumber=" + maintenanceroomNumber +
                ", numberlockedStores=" + numberlockedStores +
                ", numberroomsAvailablerent=" + numberroomsAvailablerent +
                ", totalnumberGuestrooms=" + totalnumberGuestrooms +
                ", cashDisbursements=" + cashDisbursements +
                ", cash=" + cash +
                ", throughoutDayrent=" + throughoutDayrent +
                ", rateAdjustment=" + rateAdjustment +
                ", hourRate=" + hourRate +
                ", timeoutRate=" + timeoutRate +
                ", nuclearnightRoomcharge=" + nuclearnightRoomcharge +
                ", compensation=" + compensation +
                ", membershipFee=" + membershipFee +
                ", goods=" + goods +
                ", subtotal=" + subtotal +
                ", members=" + members +
                ", agreementUnit=" + agreementUnit +
                ", app=" + app +
                ", microLetter=" + microLetter +
                ", individualTraveler=" + individualTraveler +
                ", directBooking=" + directBooking +
                ", enter=" + enter +
                ", dailyType=" + dailyType +
                ", hotelId=" + hotelId +
                ", dateTime=" + dateTime +
                '}';
    }
}