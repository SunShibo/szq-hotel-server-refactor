package com.szq.hotel.entity.bo;

import com.szq.hotel.common.base.BaseModel;

import java.math.BigDecimal;
import java.util.Date;

public class PrintHotelBO extends BaseModel {
    private BigDecimal money;//总消费
    private String address;// 地址
    private String phone;//电话
    private Date time=new Date();

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
