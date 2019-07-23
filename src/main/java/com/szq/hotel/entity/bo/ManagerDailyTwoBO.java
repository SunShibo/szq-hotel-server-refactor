package com.szq.hotel.entity.bo;

import com.szq.hotel.common.base.BaseModel;

public class ManagerDailyTwoBO extends BaseModel {
    private Integer id;

    private String member;

    private String individualTraveler;

    private String bargainingUnit;

    private String intermediary;

    private String app;

    private String wechat;

    private String stepIn;

    private String directReserve;

    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member == null ? null : member.trim();
    }

    public String getIndividualTraveler() {
        return individualTraveler;
    }

    public void setIndividualTraveler(String individualTraveler) {
        this.individualTraveler = individualTraveler == null ? null : individualTraveler.trim();
    }

    public String getBargainingUnit() {
        return bargainingUnit;
    }

    public void setBargainingUnit(String bargainingUnit) {
        this.bargainingUnit = bargainingUnit == null ? null : bargainingUnit.trim();
    }

    public String getIntermediary() {
        return intermediary;
    }

    public void setIntermediary(String intermediary) {
        this.intermediary = intermediary == null ? null : intermediary.trim();
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app == null ? null : app.trim();
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat == null ? null : wechat.trim();
    }

    public String getStepIn() {
        return stepIn;
    }

    public void setStepIn(String stepIn) {
        this.stepIn = stepIn == null ? null : stepIn.trim();
    }

    public String getDirectReserve() {
        return directReserve;
    }

    public void setDirectReserve(String directReserve) {
        this.directReserve = directReserve == null ? null : directReserve.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
}