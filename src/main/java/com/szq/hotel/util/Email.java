package com.szq.hotel.util;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class Email {


    // 发件人的 邮箱 和 密码（替换为自己的邮箱和密码）
    // PS: 某些邮箱服务器为了增加邮箱本身密码的安全性，给 SMTP 客户端设置了独立密码（有的邮箱称为“授权码”）, 
    //     对于开启了独立密码的邮箱, 这里的邮箱密码必需使用这个独立密码（授权码）。
    public static String myEmailAccount = "15214440324@163.com";
    public static String myEmailPassword = "hexiaowen123.";

    // 发件人邮箱的 SMTP 服务器地址, 必须准确, 不同邮件服务器地址不同, 一般(只是一般, 绝非绝对)格式为: smtp.xxx.com
    // 网易163邮箱的 SMTP 服务器地址为: smtp.163.com
    public static String myEmailSMTPHost = "smtp.163.com";


    // 收件人邮箱（替换为自己知道的有效邮箱）
    public static String receiveMailAccount = "15214440324@163.com";

    public static void  send(String title,String content) throws Exception {
        // 1. 创建一封邮件
        Properties props = new Properties(); // 用于连接邮件服务器的参数配置（发送邮件时才需要用到）
        props.setProperty("mail.transport.protocol", "smtp");  // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", myEmailSMTPHost);  // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");// 需要请求认证


        Session session = Session.getInstance(props); // 根据参数配置，创建会话对象（为了发送邮件准备的）
        session.setDebug(true); // 设置为debug模式, 可以查看详细的发送 log
//        MimeMessage message = new MimeMessage(session);     // 创建邮件对象

        // 3. 创建一封邮件
        MimeMessage message = createMimeMessage(session, myEmailAccount, receiveMailAccount,title,content);


        // 4. 根据 Session 获取邮件传输对象
        Transport transport = session.getTransport();

        //    PS_03: 仔细看log, 认真看log, 看懂log, 错误原因都在log已说明。
        transport.connect(myEmailAccount, myEmailPassword);

        // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(message, message.getAllRecipients());


        // 7. 关闭连接
        transport.close();


    }

    /**
     *      * 创建一封只包含文本的简单邮件
     *      *
     *      * @param session 和服务器交互的会话
     *      * @param sendMail 发件人邮箱
     *      * @param receiveMail 收件人邮箱
     *      * @return
     *      * @throws Exception
     *      
     */
    public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail,String title,String content) throws Exception {
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);


        // 2. From: 发件人（昵称有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改昵称）
        message.setFrom(new InternetAddress(sendMail, "酒店管理系统", "UTF-8"));


        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "线程", "UTF-8"));


        // 4. Subject: 邮件主题（标题有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改标题）
        message.setSubject(title, "UTF-8");


        // 5. Content: 邮件正文（可以使用html标签）（内容有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改发送内容）
        message.setContent(content, "text/html;charset=UTF-8");

        SimpleDateFormat  simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = simpleDateFormat.parse("2018-10-10 10:10:10");
        // 6. 设置发件时间
        message.setSentDate(parse);


        // 7. 保存设置
        message.saveChanges();


        return message;

    }

    public static void main(String[] args) throws Exception {
        send("跨越时间","来自过去的一封邮件");
    }

}
