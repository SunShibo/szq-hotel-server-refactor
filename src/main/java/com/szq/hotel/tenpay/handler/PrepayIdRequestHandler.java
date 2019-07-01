package com.szq.hotel.tenpay.handler;

import com.szq.hotel.tenpay.client.TenpayHttpClient;
import com.szq.hotel.tenpay.util.ConstantUtil;
import com.szq.hotel.tenpay.util.Sha1Util;
import com.szq.hotel.util.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class PrepayIdRequestHandler extends RequestHandler {

    public PrepayIdRequestHandler(HttpServletRequest request,
                                  HttpServletResponse response) {
        super(request, response);
    }

    /**
     * 创建签名SHA1
     *
     * @return
     * @throws Exception
     */
    public String createSHA1Sign() {
        StringBuffer sb = new StringBuffer();
        Set es = super.getAllParameters().entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            sb.append(k + "=" + v + "&");
        }
        String params = sb.substring(0, sb.lastIndexOf("&"));
        String appsign = Sha1Util.getSha1(params);
        this.setDebugInfo(this.getDebugInfo() + "\r\n" + "sha1 sb:" + params);
        this.setDebugInfo(this.getDebugInfo() + "\r\n" + "app sign:" + appsign);
        return appsign;
    }

    /**
     * 创建签名MD5
     *
     * @return
     * @throws Exception
     */
    public String createMD5Sign(String key) {
        StringBuffer sb = new StringBuffer();
        Set es = super.getAllParameters().entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            sb.append(k + "=" + v + "&");
        }
        String signA = sb.toString(); // 根据签名格式组装数据，详见微信支付api
        String stringSignTemp = signA + "key=" + key; // 根据签名格式组装数据，详见微信支付api
        String appsign = DigestUtils.md5Hex(
                getContentBytes(stringSignTemp, "UTF-8")).toUpperCase();
        this.setDebugInfo(this.getDebugInfo() + "\r\n" + "app sign:" + appsign);
        return appsign;
    }

    /**
     * 创建签名MD5
     *
     * @return
     * @throws Exception
     */
    public String createMD5Sign2(String key) {
        StringBuffer sb = new StringBuffer();
        Set es = super.getAllParameters().entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            sb.append(k + "=" + v + "&");
        }
        String signA = sb.toString(); // 根据签名格式组装数据，详见微信支付api
        String stringSignTemp = signA + "key=" + key; // 根据签名格式组装数据，详见微信支付api
        String appsign = DigestUtils.md5Hex(stringSignTemp.getBytes()).toUpperCase();
        this.setDebugInfo(this.getDebugInfo() + "\r\n" + "app sign:" + appsign);
        return appsign;
    }

    // 提交预支付
    public String sendPrepay() throws Exception {
        String prepayid = "";
        StringBuffer sb = new StringBuffer();
        Set es = super.getAllParameters().entrySet();
        Iterator it = es.iterator();
        sb.append("<xml>");
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (null != v && !"".equals(v) && !"appkey".equals(k)) {
                sb.append("<" + k + ">");
                sb.append(v);
                sb.append("</" + k + ">");
            }
        }
        sb.append("</xml>");
        System.out.println("post data:----->"+sb.toString());
        TenpayHttpClient httpClient = new TenpayHttpClient();
        httpClient.setReqContent(ConstantUtil.URL);
        String resContent = "";
        this.setDebugInfo(this.getDebugInfo() + "\r\n" + "post data:" + sb.toString());
        if (httpClient.callHttpPost(ConstantUtil.URL, sb.toString())) {
            resContent = httpClient.getResContent();
            if(!StringUtils.isEmpty(parse(resContent))){
                prepayid = parse(resContent);
            }
            String result = new String(resContent.getBytes("GBK"), "UTF-8");
            System.out.println("请求返回的结果=" + result);
        }
        return prepayid;
    }

    // 判断access_token是否失效
    public String sendAccessToken() {
        String accesstoken = "";
        StringBuffer sb = new StringBuffer("{");
        Set es = super.getAllParameters().entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (null != v && !"".equals(v) && !"appkey".equals(k)) {
                sb.append("\"" + k + "\":\"" + v + "\",");
            }
        }
        String params = sb.substring(0, sb.lastIndexOf(","));
        params += "}";

        String requestUrl = super.getGateUrl();
//		this.setDebugInfo(this.getDebugInfo() + "\r\n" + "requestUrl:"
//				+ requestUrl);
        TenpayHttpClient httpClient = new TenpayHttpClient();
        httpClient.setReqContent(requestUrl);
        String resContent = "";
//		this.setDebugInfo(this.getDebugInfo() + "\r\n" + "post data:" + params);
        if (httpClient.callHttpPost(requestUrl, params)) {
            resContent = httpClient.getResContent();
            if (2 == resContent.indexOf(ConstantUtil.ERRORCODE)) {
                accesstoken = resContent.substring(11, 16);//获取对应的errcode的值
            }
        }
        return accesstoken;
    }


    // 编码转换
    public byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:"
                    + charset);
        }
    }

    public static String parse(String protocolXML) {

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder
                    .parse(new InputSource(new StringReader(protocolXML)));

            Element root = doc.getDocumentElement();
            NodeList books = root.getChildNodes();
            if (books != null) {
                for (int i = 0; i < books.getLength(); i++) {
                    Node book = books.item(i);
                    if(book.getNodeName().equals("prepay_id")){
                        System.out.println("prepayId=" + book.getTextContent());
                        return book.getTextContent();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String,String> parse2(String protocolXML) {

        try {
            Map<String,String> map = new HashMap<String, String>();
            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder
                    .parse(new InputSource(new StringReader(protocolXML)));

            Element root = doc.getDocumentElement();
            NodeList books = root.getChildNodes();
            if (books != null) {
                for (int i = 0; i < books.getLength(); i++) {
                    Node book = books.item(i);
                    map.put(book.getNodeName(),book.getTextContent());

                }
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
