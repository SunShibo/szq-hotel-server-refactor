package com.szq.hotel.entity.bo;

import com.szq.hotel.entity.bo.FormUtilBO;

public class ManagementReportResponseBO {
    private Integer id; //管理层报表
    private FormUtilBO receivableSum; //应收合计
    private FormUtilBO avgRoomRate; //平均房价
    private FormUtilBO avgConsumptionOfRoom; //房平均消费
    private FormUtilBO avgConsumptionOfPerson; //人均消费
    private FormUtilBO indemnityIncome; //赔偿收入
    private FormUtilBO freeCheckInSum; //免费入住房数
    private FormUtilBO roomSum; //房间总数
    private FormUtilBO maintainRoomSum; //维修房数
    private FormUtilBO memberRoomSum; //会员房数
    private FormUtilBO memberCardSoldMoney; //会员卡销售金额
    private FormUtilBO roomLateSum; //房晚数
    private FormUtilBO personLateSum; //人晚数
    private FormUtilBO commodityRevenues; //商品收入
    private FormUtilBO roomRateAdjustment; //房费调整
    private FormUtilBO occupancyRate; //出租率
    private FormUtilBO REVPAR; //REVPAR = 应收合计 / (总房间数 - 维修房数)
    private FormUtilBO disableRoomSum; //停用房间数
    private FormUtilBO rentalIncome; //房租收入
    private FormUtilBO emptyRoomSum; //空房数
    private FormUtilBO hourRoomLateSum; //钟点房晚数
    private FormUtilBO createTime;//创建时间
    private FormUtilBO hotelId;//酒店id

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public FormUtilBO getReceivableSum() {
        return receivableSum;
    }

    public void setReceivableSum(FormUtilBO receivableSum) {
        this.receivableSum = receivableSum;
    }

    public FormUtilBO getAvgRoomRate() {
        return avgRoomRate;
    }

    public void setAvgRoomRate(FormUtilBO avgRoomRate) {
        this.avgRoomRate = avgRoomRate;
    }

    public FormUtilBO getAvgConsumptionOfRoom() {
        return avgConsumptionOfRoom;
    }

    public void setAvgConsumptionOfRoom(FormUtilBO avgConsumptionOfRoom) {
        this.avgConsumptionOfRoom = avgConsumptionOfRoom;
    }

    public FormUtilBO getAvgConsumptionOfPerson() {
        return avgConsumptionOfPerson;
    }

    public void setAvgConsumptionOfPerson(FormUtilBO avgConsumptionOfPerson) {
        this.avgConsumptionOfPerson = avgConsumptionOfPerson;
    }

    public FormUtilBO getIndemnityIncome() {
        return indemnityIncome;
    }

    public void setIndemnityIncome(FormUtilBO indemnityIncome) {
        this.indemnityIncome = indemnityIncome;
    }

    public FormUtilBO getFreeCheckInSum() {
        return freeCheckInSum;
    }

    public void setFreeCheckInSum(FormUtilBO freeCheckInSum) {
        this.freeCheckInSum = freeCheckInSum;
    }

    public FormUtilBO getRoomSum() {
        return roomSum;
    }

    public void setRoomSum(FormUtilBO roomSum) {
        this.roomSum = roomSum;
    }

    public FormUtilBO getMaintainRoomSum() {
        return maintainRoomSum;
    }

    public void setMaintainRoomSum(FormUtilBO maintainRoomSum) {
        this.maintainRoomSum = maintainRoomSum;
    }

    public FormUtilBO getMemberRoomSum() {
        return memberRoomSum;
    }

    public void setMemberRoomSum(FormUtilBO memberRoomSum) {
        this.memberRoomSum = memberRoomSum;
    }

    public FormUtilBO getMemberCardSoldMoney() {
        return memberCardSoldMoney;
    }

    public void setMemberCardSoldMoney(FormUtilBO memberCardSoldMoney) {
        this.memberCardSoldMoney = memberCardSoldMoney;
    }

    public FormUtilBO getRoomLateSum() {
        return roomLateSum;
    }

    public void setRoomLateSum(FormUtilBO roomLateSum) {
        this.roomLateSum = roomLateSum;
    }

    public FormUtilBO getPersonLateSum() {
        return personLateSum;
    }

    public void setPersonLateSum(FormUtilBO personLateSum) {
        this.personLateSum = personLateSum;
    }

    public FormUtilBO getCommodityRevenues() {
        return commodityRevenues;
    }

    public void setCommodityRevenues(FormUtilBO commodityRevenues) {
        this.commodityRevenues = commodityRevenues;
    }

    public FormUtilBO getRoomRateAdjustment() {
        return roomRateAdjustment;
    }

    public void setRoomRateAdjustment(FormUtilBO roomRateAdjustment) {
        this.roomRateAdjustment = roomRateAdjustment;
    }

    public FormUtilBO getOccupancyRate() {
        return occupancyRate;
    }

    public void setOccupancyRate(FormUtilBO occupancyRate) {
        this.occupancyRate = occupancyRate;
    }

    public FormUtilBO getREVPAR() {
        return REVPAR;
    }

    public void setREVPAR(FormUtilBO REVPAR) {
        this.REVPAR = REVPAR;
    }

    public FormUtilBO getDisableRoomSum() {
        return disableRoomSum;
    }

    public void setDisableRoomSum(FormUtilBO disableRoomSum) {
        this.disableRoomSum = disableRoomSum;
    }

    public FormUtilBO getRentalIncome() {
        return rentalIncome;
    }

    public void setRentalIncome(FormUtilBO rentalIncome) {
        this.rentalIncome = rentalIncome;
    }

    public FormUtilBO getEmptyRoomSum() {
        return emptyRoomSum;
    }

    public void setEmptyRoomSum(FormUtilBO emptyRoomSum) {
        this.emptyRoomSum = emptyRoomSum;
    }

    public FormUtilBO getHourRoomLateSum() {
        return hourRoomLateSum;
    }

    public void setHourRoomLateSum(FormUtilBO hourRoomLateSum) {
        this.hourRoomLateSum = hourRoomLateSum;
    }

    public FormUtilBO getCreateTime() {
        return createTime;
    }

    public void setCreateTime(FormUtilBO createTime) {
        this.createTime = createTime;
    }

    public FormUtilBO getHotelId() {
        return hotelId;
    }

    public void setHotelId(FormUtilBO hotelId) {
        this.hotelId = hotelId;
    }
}
