package com.szq.hotel.tenpay.util;

public class ConstantUtil {
    /**
     * 商家向财付通申请的商家id
     */
    public static final String PARTNER_ID = "1500050062";
    /**
     * 商家可以考虑读取配置文件
     */
    public static String APP_ID = "wxfceafb8ea3eae188";
    public static String APP_SECRET = "2a7d40dd47ca2fe70abd4ea474dbb513";           //应用对应的凭证
    public static String PARTNER_KEY = "6895ec07dc3749222b9323f706608b1e";         //商户号对应的密钥
    public static String TOKENURL = "https://management.weixin.qq.com/cgi-bin/token";    //获取access_token对应的url
    public static String GRANT_TYPE = "client_credential";//常量固定值
    public static String ACCESS_TOKEN = "access_token";//access_token常量值
    public static String ERRORCODE = "errcode";//用来判断access_token是否失效的值
    /** 统一下单接口链接 */
    public static final String URL="https://management.mch.weixin.qq.com/pay/unifiedorder";
}
