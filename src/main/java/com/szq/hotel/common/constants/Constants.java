package com.szq.hotel.common.constants;

public enum Constants {

    /*正常状态*/YES("yes"),
    /*删除|异常状态*/NO("no"),
    /* 订单状态:预约 */RESERVATION("reservation"),
    /* 订单状态:入住待支付 */NOTPAY("notpay"),
    /* 订单状态:入驻中 */ADMISSIONS("admissions"),
    /* 订单状态:退房未结账 */notpaid("notpaid"),
    /* 订单状态:退房未结账 */ACCOMPLISH("accomplish"),
    /* 订单状态:取消 */CANCEL("cancel"),
    /* 主订单状态 : 预约订单*/ SUBSCRIBE("subscribe"),
    /* 主订单状态 : 直接入住*/ DIRECTLY("directly"),
    /* 主订单入住类型 : 全天房 */ DAY("day"),
    /* 主订单入住类型 : 钟点房 */ HOUR("hour"),
    /* 主订单入住类型 : 免费房 */ FREE("free"),
    /* 支付方式 : 现金 */ CASH("cash"),
    /* 支付方式 : 银行卡 */ CART("cart"),
    /* 支付方式 : 微信 */ WECHAT("wechat"),
    /* 支付方式 : 支付宝 */ ALIPAY("alipay"),
    /* 支付方式 : 其他支付 */ OTHER("other"),
    /* 支付方式 : 储值 */ STORED("stored"),
    /* 支付方式 : 积分 */ INTEGRAL("integral");

    Constants(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }

}
