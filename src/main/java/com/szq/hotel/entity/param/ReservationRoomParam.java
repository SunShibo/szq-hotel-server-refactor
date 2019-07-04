package com.szq.hotel.entity.param;

import java.math.BigDecimal;
import java.util.Date;

public class ReservationRoomParam {
    //房间预定 需要的信息
    //选房间号需要把房型和房间号都传过来
    //选房型就不需要房间号了
    private Integer roomNumber;//房间号
    private Integer roomTypeId;//房型id
    private Date time;//哪天
    private BigDecimal room_rate;//选定时的价格
    private String remark;//每间房的备注

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Integer getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Integer roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public BigDecimal getRoom_rate() {
        return room_rate;
    }

    public void setRoom_rate(BigDecimal room_rate) {
        this.room_rate = room_rate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
