package com.szq.hotel.entity.bo;

import com.szq.hotel.common.base.BaseModel;

import java.math.BigDecimal;
import java.util.Date;

public class ManagementReportBO extends BaseModel {
    private Integer id; //管理层报表
    private BigDecimal receivableSum; //应收合计
    private BigDecimal avgRoomRate; //平均房价
    private BigDecimal avgConsumptionOfRoom; //房平均消费
    private BigDecimal avgConsumptionOfPerson; //人均消费
    private BigDecimal indemnityIncome; //赔偿收入
    private Integer freeCheckInSum; //免费入住数
    private Integer roomSum; //房间总数
    private Integer maintainRoomSum; //维修房数
    private Integer memberRoomSum; //会员房数
    private BigDecimal memberCardSoldMoney; //会员卡销售金额
    private Integer roomLateSum; //房晚数
    private Integer personLateSum; //人晚数
    private BigDecimal commodityRevenues; //商品收入
    private BigDecimal roomRateAdjustment; //房费调整
    private String occupancyRate; //出租率
    private String REVPAR; //REVPAR = 应收合计 / (总房间数 - 维修房数)
    private Integer disableRoomSum; //停用房间数
    private BigDecimal rentalIncome; //房租收入
    private Integer emptyRoomSum; //空房数
    private Integer hourRoomLateSum; //钟点房晚数
    private Date createTime;//创建时间
    private Integer hotelId;//酒店id

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getReceivableSum() {
        return receivableSum;
    }

    public void setReceivableSum(BigDecimal receivableSum) {
        this.receivableSum = receivableSum;
    }

    public BigDecimal getAvgRoomRate() {
        return avgRoomRate;
    }

    public void setAvgRoomRate(BigDecimal avgRoomRate) {
        this.avgRoomRate = avgRoomRate;
    }

    public BigDecimal getAvgConsumptionOfRoom() {
        return avgConsumptionOfRoom;
    }

    public void setAvgConsumptionOfRoom(BigDecimal avgConsumptionOfRoom) {
        this.avgConsumptionOfRoom = avgConsumptionOfRoom;
    }

    public BigDecimal getAvgConsumptionOfPerson() {
        return avgConsumptionOfPerson;
    }

    public void setAvgConsumptionOfPerson(BigDecimal avgConsumptionOfPerson) {
        this.avgConsumptionOfPerson = avgConsumptionOfPerson;
    }

    public BigDecimal getIndemnityIncome() {
        return indemnityIncome;
    }

    public void setIndemnityIncome(BigDecimal indemnityIncome) {
        this.indemnityIncome = indemnityIncome;
    }

    public Integer getFreeCheckInSum() {
        return freeCheckInSum;
    }

    public void setFreeCheckInSum(Integer freeCheckInSum) {
        this.freeCheckInSum = freeCheckInSum;
    }

    public Integer getRoomSum() {
        return roomSum;
    }

    public void setRoomSum(Integer roomSum) {
        this.roomSum = roomSum;
    }

    public Integer getMaintainRoomSum() {
        return maintainRoomSum;
    }

    public void setMaintainRoomSum(Integer maintainRoomSum) {
        this.maintainRoomSum = maintainRoomSum;
    }

    public Integer getMemberRoomSum() {
        return memberRoomSum;
    }

    public void setMemberRoomSum(Integer memberRoomSum) {
        this.memberRoomSum = memberRoomSum;
    }

    public BigDecimal getMemberCardSoldMoney() {
        return memberCardSoldMoney;
    }

    public void setMemberCardSoldMoney(BigDecimal memberCardSoldMoney) {
        this.memberCardSoldMoney = memberCardSoldMoney;
    }

    public Integer getRoomLateSum() {
        return roomLateSum;
    }

    public void setRoomLateSum(Integer roomLateSum) {
        this.roomLateSum = roomLateSum;
    }

    public Integer getPersonLateSum() {
        return personLateSum;
    }

    public void setPersonLateSum(Integer personLateSum) {
        this.personLateSum = personLateSum;
    }

    public BigDecimal getCommodityRevenues() {
        return commodityRevenues;
    }

    public void setCommodityRevenues(BigDecimal commodityRevenues) {
        this.commodityRevenues = commodityRevenues;
    }

    public BigDecimal getRoomRateAdjustment() {
        return roomRateAdjustment;
    }

    public void setRoomRateAdjustment(BigDecimal roomRateAdjustment) {
        this.roomRateAdjustment = roomRateAdjustment;
    }

    public String getOccupancyRate() {
        return occupancyRate;
    }

    public void setOccupancyRate(String occupancyRate) {
        this.occupancyRate = occupancyRate;
    }

    public String getREVPAR() {
        return REVPAR;
    }

    public void setREVPAR(String REVPAR) {
        this.REVPAR = REVPAR;
    }

    public Integer getDisableRoomSum() {
        return disableRoomSum;
    }

    public void setDisableRoomSum(Integer disableRoomSum) {
        this.disableRoomSum = disableRoomSum;
    }

    public BigDecimal getRentalIncome() {
        return rentalIncome;
    }

    public void setRentalIncome(BigDecimal rentalIncome) {
        this.rentalIncome = rentalIncome;
    }

    public Integer getEmptyRoomSum() {
        return emptyRoomSum;
    }

    public void setEmptyRoomSum(Integer emptyRoomSum) {
        this.emptyRoomSum = emptyRoomSum;
    }

    public Integer getHourRoomLateSum() {
        return hourRoomLateSum;
    }

    public void setHourRoomLateSum(Integer hourRoomLateSum) {
        this.hourRoomLateSum = hourRoomLateSum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }
}
