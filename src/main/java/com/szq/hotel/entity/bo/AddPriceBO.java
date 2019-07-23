package com.szq.hotel.entity.bo;

import com.szq.hotel.entity.bo.common.base.BaseModel;

import java.util.List;

/**
 * 改价
 */
public class AddPriceBO extends BaseModel {


    private Integer orderId ;
    private Integer roomTypeId;
    private List<PriceBO>  priceBOS;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Integer roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public List<PriceBO> getPriceBOS() {
        return priceBOS;
    }

    public void setPriceBOS(List<PriceBO> priceBOS) {
        this.priceBOS = priceBOS;
    }
}