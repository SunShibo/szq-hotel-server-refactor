package com.szq.hotel.entity.bo;

import com.szq.hotel.common.base.BaseModel;

public class RoomMessageBO extends BaseModel {
    private String setting;//设置
    private String character;//特性
    private String remark;//备注

    public String getSetting() {
        return setting;
    }

    public void setSetting(String setting) {
        this.setting = setting;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
