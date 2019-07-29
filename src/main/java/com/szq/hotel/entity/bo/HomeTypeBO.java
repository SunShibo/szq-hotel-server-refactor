package com.szq.hotel.entity.bo;

import com.szq.hotel.entity.bo.common.base.BaseModel;

public class HomeTypeBO extends BaseModel{

    private int vacant;//空房
    private int inthe;//在住
    private int timeout;//超时
    private int dirty;//脏房
    private int departure;//预离店
    private int subscribe;//  预约中
    private int maintain;//维修
    private int shop;//门店锁


    public int getVacant() {
        return vacant;
    }

    public void setVacant(int vacant) {
        this.vacant = vacant;
    }

    public int getInthe() {
        return inthe;
    }

    public void setInthe(int inthe) {
        this.inthe = inthe;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getDirty() {
        return dirty;
    }

    public void setDirty(int dirty) {
        this.dirty = dirty;
    }

    public int getDeparture() {
        return departure;
    }

    public void setDeparture(int departure) {
        this.departure = departure;
    }

    public int getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(int subscribe) {
        this.subscribe = subscribe;
    }

    public int getMaintain() {
        return maintain;
    }

    public void setMaintain(int maintain) {
        this.maintain = maintain;
    }

    public int getShop() {
        return shop;
    }

    public void setShop(int shop) {
        this.shop = shop;
    }


}
