package com.szq.hotel.entity.dto;

import java.io.Serializable;

/**
 * @Author: Bin Wang
 * @date: Created in 15:52 2019/7/19
 */
public class XxDTO implements Serializable {

    private String name; //会员名称
    private Integer number; //数量

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "XxDTO{" +
                "name='" + name + '\'' +
                ", number=" + number +
                '}';
    }
}
