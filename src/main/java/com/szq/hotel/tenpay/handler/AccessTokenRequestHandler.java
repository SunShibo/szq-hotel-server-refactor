package com.szq.hotel.tenpay.handler;

import com.szq.hotel.tenpay.client.TenpayHttpClient;
import com.szq.hotel.tenpay.util.ConstantUtil;
import com.szq.hotel.tenpay.util.JsonUtil;
import com.szq.hotel.tenpay.util.Sha1Util;
import org.apache.http.NameValuePair;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

public class AccessTokenRequestHandler extends RequestHandler {

    private static String access_token = "";

    public AccessTokenRequestHandler(HttpServletRequest request,
                                     HttpServletResponse response) {
        super(request, response);
    }

    /**
     * 获取凭证access_token
     *
     * @return
     */
    public static String getAccessToken() {
        if ("".equals(access_token)) {// 如果为空直接获取
            return getTokenReal();
        }
        // 如果过期重新获取
        /*if (tokenIsExpire(access_token)) {
            return getTokenReal();
        }*/
        return access_token;
    }

    /**
     * 实际获取access_token的方法
     *
     * @return
     */
    protected static String getTokenReal() {
        String requestUrl = ConstantUtil.TOKENURL + "?grant_type=" + ConstantUtil.GRANT_TYPE + "&appid="
                + ConstantUtil.APP_ID + "&secret=" + ConstantUtil.APP_SECRET;
        String resContent = "";
        TenpayHttpClient httpClient = new TenpayHttpClient();
        httpClient.setMethod("GET");
        httpClient.setReqContent(requestUrl);
        if (httpClient.call()) {
            resContent = httpClient.getResContent();
            if (resContent.indexOf(ConstantUtil.ACCESS_TOKEN) > 0) {
                access_token = JsonUtil.getJsonValue(resContent, ConstantUtil.ACCESS_TOKEN);
            } else {
                System.out.println("获取access_token值返回错误！！！");
            }
        } else {
            System.out.println("后台调用通信失败");
            System.out.println(httpClient.getResponseCode());
            System.out.println(httpClient.getErrInfo());
            // 有可能因为网络原因，请求已经处理，但未收到应答。
        }
        return access_token;
    }

    /**
     * 判断传递过来的参数access_token是否过期
     *
     * @param access_token
     * @return
     */
    /*private static boolean tokenIsExpire(String access_token) {
        boolean flag = false;
        PrepayIdRequestHandler wxReqHandler = new PrepayIdRequestHandler(null, null);
        wxReqHandler.setParameter("appid", ConstantUtil.APP_ID);
        wxReqHandler.setParameter("appkey", ConstantUtil.APP_KEY);
        wxReqHandler.setParameter("noncestr", WXUtil.getNonceStr());
        wxReqHandler.setParameter("package", ConstantUtil.packageValue);
        wxReqHandler.setParameter("timestamp", WXUtil.getTimeStamp());
        wxReqHandler.setParameter("traceid", ConstantUtil.traceid);

        // 生成支付签名
        String sign = wxReqHandler.createSHA1Sign();
        wxReqHandler.setParameter("app_signature", sign);
        wxReqHandler.setParameter("sign_method", ConstantUtil.SIGN_METHOD);
        String gateUrl = ConstantUtil.GATEURL + access_token;
        wxReqHandler.setGateUrl(gateUrl);

        // 发送请求
        String accesstoken = wxReqHandler.sendAccessToken();
        if (ConstantUtil.EXPIRE_ERRCODE.equals(accesstoken) || ConstantUtil.FAIL_ERRCODE.equals(accesstoken))
            flag = true;
        return flag;
    }*/


    /**
     * 获取时间戳
     *
     * @return
     * @author sun
     * @version 2014-9-24 下午02:26:52
     */
    public static long genTimeStamp() {
        return new Date().getTime() / 1000;
    }

    /**
     * 生成数字签名
     *
     * @param params
     * @return
     * @author sun
     * @version 2014-9-24 下午02:33:57
     */
    public static String createSignature(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        int i = 0;
        for (; i < params.size() - 1; i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append(params.get(i).getName());
        sb.append('=');
        sb.append(params.get(i).getValue());

        String sha1 = Sha1Util.getSha1(sb.toString());
        System.out.println("genSign, sha1 = " + sha1);
        return sha1;
    }


    /**
     * 建议 traceid 字段包含用户信息及订单信息，方便后续对订单状态的查询和跟踪
     */
    private static String getTraceId() {
        return "crestxu_" + genTimeStamp();
    }

    public static void main(String[] args) {
        String res = AccessTokenRequestHandler.getAccessToken();

        System.out.println(res);

    }

}
