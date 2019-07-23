package com.szq.hotel.entity.bo;

import com.szq.hotel.common.base.BaseModel;

public class ManagerDailyOneBO extends BaseModel {
    private Integer id;

    private Double realizedIncome;

    private Double turnover;

    private Integer reserveNoshowRoom;

    private Integer maintainRoom;

    private Integer storeLockRoom;

    private Integer leasableRoom;

    private Integer guestRooms;

    private Double cashOutlay;

    private Double cashIncome;

    private Double dailyRental;

    private Double roomRateAdjustment;

    private Double hourRoomRate;

    private Double timeoutRoomRate;

    private Double auditorRoomRate;

    private Double compensation;

    private Double memberCardRate;

    private Double commodity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getRealizedIncome() {
        return realizedIncome;
    }

    public void setRealizedIncome(Double realizedIncome) {
        this.realizedIncome = realizedIncome;
    }

    public Double getTurnover() {
        return turnover;
    }

    public void setTurnover(Double turnover) {
        this.turnover = turnover;
    }

    public Integer getReserveNoshowRoom() {
        return reserveNoshowRoom;
    }

    public void setReserveNoshowRoom(Integer reserveNoshowRoom) {
        this.reserveNoshowRoom = reserveNoshowRoom;
    }

    public Integer getMaintainRoom() {
        return maintainRoom;
    }

    public void setMaintainRoom(Integer maintainRoom) {
        this.maintainRoom = maintainRoom;
    }

    public Integer getStoreLockRoom() {
        return storeLockRoom;
    }

    public void setStoreLockRoom(Integer storeLockRoom) {
        this.storeLockRoom = storeLockRoom;
    }

    public Integer getLeasableRoom() {
        return leasableRoom;
    }

    public void setLeasableRoom(Integer leasableRoom) {
        this.leasableRoom = leasableRoom;
    }

    public Integer getGuestRooms() {
        return guestRooms;
    }

    public void setGuestRooms(Integer guestRooms) {
        this.guestRooms = guestRooms;
    }

    public Double getCashOutlay() {
        return cashOutlay;
    }

    public void setCashOutlay(Double cashOutlay) {
        this.cashOutlay = cashOutlay;
    }

    public Double getCashIncome() {
        return cashIncome;
    }

    public void setCashIncome(Double cashIncome) {
        this.cashIncome = cashIncome;
    }

    public Double getDailyRental() {
        return dailyRental;
    }

    public void setDailyRental(Double dailyRental) {
        this.dailyRental = dailyRental;
    }

    public Double getRoomRateAdjustment() {
        return roomRateAdjustment;
    }

    public void setRoomRateAdjustment(Double roomRateAdjustment) {
        this.roomRateAdjustment = roomRateAdjustment;
    }

    public Double getHourRoomRate() {
        return hourRoomRate;
    }

    public void setHourRoomRate(Double hourRoomRate) {
        this.hourRoomRate = hourRoomRate;
    }

    public Double getTimeoutRoomRate() {
        return timeoutRoomRate;
    }

    public void setTimeoutRoomRate(Double timeoutRoomRate) {
        this.timeoutRoomRate = timeoutRoomRate;
    }

    public Double getAuditorRoomRate() {
        return auditorRoomRate;
    }

    public void setAuditorRoomRate(Double auditorRoomRate) {
        this.auditorRoomRate = auditorRoomRate;
    }

    public Double getCompensation() {
        return compensation;
    }

    public void setCompensation(Double compensation) {
        this.compensation = compensation;
    }

    public Double getMemberCardRate() {
        return memberCardRate;
    }

    public void setMemberCardRate(Double memberCardRate) {
        this.memberCardRate = memberCardRate;
    }

    public Double getCommodity() {
        return commodity;
    }

    public void setCommodity(Double commodity) {
        this.commodity = commodity;
    }
}