package com.szq.hotel.entity.bo;

import java.math.BigDecimal;
import java.util.Date;

public class EverydayRoomPriceBO {
    private Integer id;//没日房价 与子订单相关联
    private Integer orderChildId;//子订单id
    private Date time;//日期
    private BigDecimal money;//价格
    private Integer roomTypeId;//房型的id

    public Integer getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Integer roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderChildId() {
        return orderChildId;
    }

    public void setOrderChildId(Integer orderChildId) {
        this.orderChildId = orderChildId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
