package com.szq.hotel.common.constants;

public enum Constants {

    /*正常状态*/YES("yes"),
    /*删除|异常状态*/NO("no");

    Constants(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }

}
