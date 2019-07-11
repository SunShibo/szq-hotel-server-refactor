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
    private int network;//网络锁
    private int all;//全部锁


    public int getVacant() {
        return vacant;
    }

    public void setVacant() {
        this.vacant++;
    }

    public int getInthe() {
        return inthe;
    }

    public void setInthe() {
        this.inthe++;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout() {
        this.timeout++;
    }

    public int getDirty() {
        return dirty;
    }

    public void setDirty() {
        this.dirty++;
    }

    public int getDeparture() {
        return departure;
    }

    public void setDeparture() {
        this.departure++;
    }

    public int getSubscribe() {
        return subscribe;
    }

    public void setSubscribe() {
        this.subscribe++;
    }

    public int getMaintain() {
        return maintain;
    }

    public void setMaintain() {
        this.maintain++;
    }

    public int getShop() {
        return shop;
    }

    public void setShop() {
        this.shop++;
    }

    public int getNetwork() {
        return network;
    }

    public void setNetwork() {
        this.network++;
    }

    public int getAll() {
        return all;
    }

    public void setAll() {
        this.all++;
    }
}
