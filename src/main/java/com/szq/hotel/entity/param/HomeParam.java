package com.szq.hotel.entity.param;

import java.util.Arrays;

public class HomeParam {
    private String vacant; //空房
    private String inthe; //在住
    private String timeout; //超时
    private String dirty; //脏房
    private String subscribe;//预约中
    private String departure;//预离店
    private String maintain; //维修
    private String shop; //门店锁
    private String network;//网络锁
    private String types; //房型


    public String getVacant() {
        return vacant;
    }

    public void setVacant(String vacant) {
        this.vacant = vacant;
    }

    public String getInthe() {
        return inthe;
    }

    public void setInthe(String inthe) {
        this.inthe = inthe;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public String getDirty() {
        return dirty;
    }

    public void setDirty(String dirty) {
        this.dirty = dirty;
    }

    public String getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(String subscribe) {
        this.subscribe = subscribe;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getMaintain() {
        return maintain;
    }

    public void setMaintain(String maintain) {
        this.maintain = maintain;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }


    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    @Override
    public String toString() {
        return "HomeParam{" +
                "vacant='" + vacant + '\'' +
                ", inthe='" + inthe + '\'' +
                ", timeout='" + timeout + '\'' +
                ", dirty='" + dirty + '\'' +
                ", subscribe='" + subscribe + '\'' +
                ", departure='" + departure + '\'' +
                ", maintain='" + maintain + '\'' +
                ", shop='" + shop + '\'' +
                ", network='" + network + '\'' +
                ", types='" + types + '\'' +
                '}';
    }
}
