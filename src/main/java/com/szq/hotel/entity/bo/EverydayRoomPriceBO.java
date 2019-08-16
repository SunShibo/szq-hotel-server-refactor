package com.szq.hotel.entity.bo;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

public class EverydayRoomPriceBO {
    private Integer id;//每日房价 与子订单相关联
    private Integer orderChildId;//子订单id
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date time;//日期
    private BigDecimal money=new BigDecimal(0);//价格
    private String status;//状态


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    @Override
    public String toString() {
        return "EverydayRoomPriceBO{" +
                "id=" + id +
                ", orderChildId=" + orderChildId +
                ", time=" + time +
                ", money=" + money +
                ", status='" + status + '\'' +
                '}';
    }
}
