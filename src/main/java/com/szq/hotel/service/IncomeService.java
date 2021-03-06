package com.szq.hotel.service;

import com.szq.hotel.common.constants.Constants;
import com.szq.hotel.dao.IncomeDAO;
import com.szq.hotel.entity.bo.IncomeBO;
import com.szq.hotel.util.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class IncomeService {

    @Resource
    IncomeDAO incomeDAO;

    //获取营收入报表
    public List<IncomeBO> getIncome(Integer hotelId,Date date){
        List<IncomeBO> incomeBOS=new ArrayList<IncomeBO>();
        //获取今天日期
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
//        int hour = calendar.get(Calendar.HOUR_OF_DAY);
//        if(hour<=6){
//            calendar.add(Calendar.DATE, -1);
//        }
        int year = calendar.get(Calendar.YEAR);//获取年份

        int month = calendar.get(Calendar.MONTH) + 1;//获取月份

        int day=calendar.get(Calendar.DATE);//获取日

        //获取当日营收
        IncomeBO incomeBO=new IncomeBO();
        System.err.println(month+"=================");
        IncomeBO incomeResult=incomeDAO.getIncome(year,month,day,hotelId);
        if(incomeResult!=null){
            incomeBO=incomeResult;
        }
        //获取当月营收
        IncomeBO incomeBO1=new IncomeBO();
        IncomeBO incomeBO1Result=incomeDAO.getIncome(year,month,null,hotelId);
        if(incomeBO1Result!=null){
            incomeBO1=incomeBO1Result;
        }

        //获取上月营收
        IncomeBO incomeBO2=new IncomeBO();
        IncomeBO incomeBO2Result=incomeDAO.getIncome(year,month-1,null,hotelId);
        if(incomeBO2Result!=null){
            incomeBO2=incomeBO2Result;
        }

        //获取当年营收
        IncomeBO incomeBO3=new IncomeBO();
        IncomeBO incomeBO3Result=incomeDAO.getIncome(year,null,null,hotelId);
        if(incomeBO3Result!=null){
            incomeBO3=incomeBO3Result;
        }

        //获取去年同月营收
        IncomeBO incomeBO4=new IncomeBO();
        IncomeBO incomeBO4Result=incomeDAO.getIncome(year-1,month,null,hotelId);
        if(incomeBO4Result!=null){
            incomeBO4=incomeBO4Result;
        }

        //获取去年营收
        IncomeBO incomeBO5=new IncomeBO();
        IncomeBO incomeBO5Result=incomeDAO.getIncome(year-1,null,null,hotelId);
        if(incomeBO5Result!=null){
            incomeBO5=incomeBO5Result;
        }
        //今年同月和去年同月的差异
        IncomeBO monthDifferences=new IncomeBO();
        //新增：其他支付
        if(incomeBO1.getOther().intValue()<=0||incomeBO4.getOther().intValue()<=0){
            monthDifferences.setOther(new BigDecimal(100));
        }else if(incomeBO4.getOther().intValue()>incomeBO1.getOther().intValue()){
            monthDifferences.setOther((incomeBO4.getOther().subtract(incomeBO1.getOther())).divide(incomeBO4.getOther(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }else{
            monthDifferences.setOther((incomeBO1.getOther().subtract(incomeBO4.getOther())).divide(incomeBO1.getOther(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }
        //
        if(incomeBO1.getRoomRate().intValue()<=0||incomeBO4.getRoomRate().intValue()<=0){
            monthDifferences.setRoomRate(new BigDecimal(100));
        }else if(incomeBO4.getRoomRate().intValue()>incomeBO1.getRoomRate().intValue()){
            monthDifferences.setRoomRate((incomeBO4.getRoomRate().subtract(incomeBO1.getRoomRate())).divide(incomeBO4.getRoomRate(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }else{
            monthDifferences.setRoomRate((incomeBO1.getRoomRate().subtract(incomeBO4.getRoomRate())).divide(incomeBO1.getRoomRate(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }
        if(incomeBO1.getTimeoutRoomRate().intValue()<=0||incomeBO4.getTimeoutRoomRate().intValue()<=0){
            monthDifferences.setTimeoutRoomRate(new BigDecimal(100));
        }else if(incomeBO4.getTimeoutRoomRate().intValue()>incomeBO1.getTimeoutRoomRate().intValue()){
            monthDifferences.setTimeoutRoomRate((incomeBO4.getTimeoutRoomRate().subtract(incomeBO1.getTimeoutRoomRate())).divide(incomeBO4.getTimeoutRoomRate(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }else{
            monthDifferences.setTimeoutRoomRate((incomeBO1.getTimeoutRoomRate().subtract(incomeBO4.getTimeoutRoomRate())).divide(incomeBO1.getTimeoutRoomRate(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }
        if(incomeBO1.getRoomRateAdjustment().intValue()<=0||incomeBO4.getRoomRateAdjustment().intValue()<=0){
            monthDifferences.setRoomRateAdjustment(new BigDecimal(100));
        }else if(incomeBO4.getRoomRateAdjustment().intValue()>incomeBO1.getRoomRateAdjustment().intValue()){
            monthDifferences.setRoomRateAdjustment((incomeBO4.getRoomRateAdjustment().subtract(incomeBO1.getRoomRateAdjustment())).divide(incomeBO4.getRoomRateAdjustment(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }else{
            monthDifferences.setRoomRateAdjustment((incomeBO1.getRoomRateAdjustment().subtract(incomeBO4.getRoomRateAdjustment())).divide(incomeBO1.getRoomRateAdjustment(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }
        if(incomeBO1.getOtherRate().intValue()<=0||incomeBO4.getOtherRate().intValue()<=0){
            monthDifferences.setOtherRate(new BigDecimal(100));
        }else if(incomeBO4.getOtherRate().intValue()>incomeBO1.getOtherRate().intValue()){
            monthDifferences.setOtherRate((incomeBO4.getOtherRate().subtract(incomeBO1.getOtherRate())).divide(incomeBO4.getOtherRate(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }else{
            monthDifferences.setOtherRate((incomeBO1.getOtherRate().subtract(incomeBO4.getOtherRate())).divide(incomeBO1.getOtherRate(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }
        if(incomeBO1.getCommodity().intValue()<=0||incomeBO4.getCommodity().intValue()<=0){
            monthDifferences.setCommodity(new BigDecimal(100));
        }else if(incomeBO4.getCommodity().intValue()>incomeBO1.getCommodity().intValue()){
            monthDifferences.setCommodity((incomeBO4.getCommodity().subtract(incomeBO1.getCommodity())).divide(incomeBO4.getCommodity(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }else{
            monthDifferences.setCommodity((incomeBO1.getCommodity().subtract(incomeBO4.getCommodity())).divide(incomeBO1.getCommodity(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }
        if(incomeBO1.getCompensation().intValue()<=0||incomeBO4.getCompensation().intValue()<=0){
            monthDifferences.setCompensation(new BigDecimal(100));
        }else if(incomeBO4.getCompensation().intValue()>incomeBO1.getCompensation().intValue()){
            monthDifferences.setCompensation((incomeBO4.getCompensation().subtract(incomeBO1.getCompensation())).divide(incomeBO4.getCompensation(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }else{
            monthDifferences.setCompensation((incomeBO1.getCompensation().subtract(incomeBO4.getCompensation())).divide(incomeBO1.getCompensation(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }
        if(incomeBO1.getMemberCardRate().intValue()<=0||incomeBO4.getMemberCardRate().intValue()<=0){
            monthDifferences.setMemberCardRate(new BigDecimal(100));
        }else if(incomeBO4.getMemberCardRate().intValue()>incomeBO1.getMemberCardRate().intValue()){
            monthDifferences.setMemberCardRate((incomeBO4.getMemberCardRate().subtract(incomeBO1.getMemberCardRate())).divide(incomeBO4.getMemberCardRate(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }else{
            monthDifferences.setMemberCardRate((incomeBO1.getMemberCardRate().subtract(incomeBO4.getMemberCardRate())).divide(incomeBO1.getMemberCardRate(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }
        if(incomeBO1.getDebtSum().intValue()<=0||incomeBO4.getDebtSum().intValue()<=0){
            monthDifferences.setDebtSum(new BigDecimal(100));
        }else if(incomeBO4.getDebtSum().intValue()>incomeBO1.getDebtSum().intValue()){
            monthDifferences.setDebtSum((incomeBO4.getDebtSum().subtract(incomeBO1.getDebtSum())).divide(incomeBO4.getDebtSum(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }else{
            monthDifferences.setDebtSum((incomeBO1.getDebtSum().subtract(incomeBO4.getDebtSum())).divide(incomeBO1.getDebtSum(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }
        if(incomeBO1.getCash().intValue()<=0||incomeBO4.getCash().intValue()<=0){
            monthDifferences.setCash(new BigDecimal(100));
        }else if(incomeBO4.getCash().intValue()>incomeBO1.getCash().intValue()){
            monthDifferences.setCash((incomeBO4.getCash().subtract(incomeBO1.getCash())).divide(incomeBO4.getCash(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }else{
            monthDifferences.setCash((incomeBO1.getCash().subtract(incomeBO4.getCash())).divide(incomeBO1.getCash(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }
        if(incomeBO1.getBankCard().intValue()<=0||incomeBO4.getBankCard().intValue()<=0){
            monthDifferences.setBankCard(new BigDecimal(100));
        }else if(incomeBO4.getBankCard().intValue()>incomeBO1.getBankCard().intValue()){
            monthDifferences.setBankCard((incomeBO4.getBankCard().subtract(incomeBO1.getBankCard())).divide(incomeBO4.getBankCard(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }else{
            monthDifferences.setBankCard((incomeBO1.getBankCard().subtract(incomeBO4.getBankCard())).divide(incomeBO1.getBankCard(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }
        if(incomeBO1.getWechat().intValue()<=0||incomeBO4.getWechat().intValue()<=0){
            monthDifferences.setWechat(new BigDecimal(100));
        }else if(incomeBO4.getWechat().intValue()>incomeBO1.getWechat().intValue()){
            monthDifferences.setWechat((incomeBO4.getWechat().subtract(incomeBO1.getWechat())).divide(incomeBO4.getWechat(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }else{
            monthDifferences.setWechat((incomeBO1.getWechat().subtract(incomeBO4.getWechat())).divide(incomeBO1.getWechat(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }
        if(incomeBO1.getAlipay().intValue()<=0||incomeBO4.getAlipay().intValue()<=0){
            monthDifferences.setAlipay(new BigDecimal(100));
        }else if(incomeBO4.getAlipay().intValue()>incomeBO1.getAlipay().intValue()){
            monthDifferences.setAlipay((incomeBO4.getAlipay().subtract(incomeBO1.getAlipay())).divide(incomeBO4.getAlipay(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }else{
            monthDifferences.setAlipay((incomeBO1.getAlipay().subtract(incomeBO4.getAlipay())).divide(incomeBO1.getAlipay(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }
        if(incomeBO1.getStoredPay().intValue()<=0||incomeBO4.getStoredPay().intValue()<=0){
            monthDifferences.setStoredPay(new BigDecimal(100));
        }else if(incomeBO4.getStoredPay().intValue()>incomeBO1.getStoredPay().intValue()){
            monthDifferences.setStoredPay((incomeBO4.getStoredPay().subtract(incomeBO1.getStoredPay())).divide(incomeBO4.getStoredPay(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }else{
            monthDifferences.setStoredPay((incomeBO1.getStoredPay().subtract(incomeBO4.getStoredPay())).divide(incomeBO1.getStoredPay(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }
        if(incomeBO1.getCreditSum().intValue()<=0||incomeBO4.getCreditSum().intValue()<=0){
            monthDifferences.setCreditSum(new BigDecimal(100));
        }else if(incomeBO4.getCreditSum().intValue()>incomeBO1.getCreditSum().intValue()){
            monthDifferences.setCreditSum((incomeBO4.getCreditSum().subtract(incomeBO1.getCreditSum())).divide(incomeBO4.getCreditSum(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }else{
            monthDifferences.setCreditSum((incomeBO1.getCreditSum().subtract(incomeBO4.getCreditSum())).divide(incomeBO1.getCreditSum(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }
        //今年和去年的差异
        IncomeBO yearDifferences=new IncomeBO();
        //新增：其他支付
        if(incomeBO3.getOther().intValue()<=0||incomeBO5.getOther().intValue()<=0){
            yearDifferences.setOther(new BigDecimal(100));
        }else if(incomeBO5.getOther().intValue()>incomeBO3.getOther().intValue()){
            yearDifferences.setOther((incomeBO5.getOther().subtract(incomeBO3.getOther())).divide(incomeBO5.getOther(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }else{
            yearDifferences.setOther((incomeBO3.getOther().subtract(incomeBO5.getOther())).divide(incomeBO3.getOther(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }
        //
        if(incomeBO3.getRoomRate().intValue()<=0||incomeBO5.getRoomRate().intValue()<=0){
            yearDifferences.setRoomRate(new BigDecimal(100));
        }else if(incomeBO5.getRoomRate().intValue()>incomeBO3.getRoomRate().intValue()){
            yearDifferences.setRoomRate((incomeBO5.getRoomRate().subtract(incomeBO3.getRoomRate())).divide(incomeBO5.getRoomRate(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }else{
            yearDifferences.setRoomRate((incomeBO3.getRoomRate().subtract(incomeBO5.getRoomRate())).divide(incomeBO3.getRoomRate(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }
        if(incomeBO3.getTimeoutRoomRate().intValue()<=0||incomeBO5.getTimeoutRoomRate().intValue()<=0){
            yearDifferences.setTimeoutRoomRate(new BigDecimal(100));
        }else if(incomeBO5.getTimeoutRoomRate().intValue()>incomeBO3.getTimeoutRoomRate().intValue()){
            yearDifferences.setTimeoutRoomRate((incomeBO5.getTimeoutRoomRate().subtract(incomeBO3.getTimeoutRoomRate())).divide(incomeBO5.getTimeoutRoomRate(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }else{
            yearDifferences.setTimeoutRoomRate((incomeBO3.getTimeoutRoomRate().subtract(incomeBO5.getTimeoutRoomRate())).divide(incomeBO3.getTimeoutRoomRate(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }
        if(incomeBO3.getRoomRateAdjustment().intValue()<=0||incomeBO5.getRoomRateAdjustment().intValue()<=0){
            yearDifferences.setRoomRateAdjustment(new BigDecimal(100));
        }else if(incomeBO5.getRoomRateAdjustment().intValue()>incomeBO3.getRoomRateAdjustment().intValue()){
            yearDifferences.setRoomRateAdjustment((incomeBO5.getRoomRateAdjustment().subtract(incomeBO3.getRoomRateAdjustment())).divide(incomeBO5.getRoomRateAdjustment(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }else{
            yearDifferences.setRoomRateAdjustment((incomeBO3.getRoomRateAdjustment().subtract(incomeBO5.getRoomRateAdjustment())).divide(incomeBO3.getRoomRateAdjustment(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }
        if(incomeBO3.getOtherRate().intValue()<=0||incomeBO5.getOtherRate().intValue()<=0){
            yearDifferences.setOtherRate(new BigDecimal(100));
        }else if(incomeBO5.getOtherRate().intValue()>incomeBO3.getOtherRate().intValue()){
            yearDifferences.setOtherRate((incomeBO5.getOtherRate().subtract(incomeBO3.getOtherRate())).divide(incomeBO5.getOtherRate(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }else{
            yearDifferences.setOtherRate((incomeBO3.getOtherRate().subtract(incomeBO5.getOtherRate())).divide(incomeBO3.getOtherRate(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }
        if(incomeBO3.getCommodity().intValue()<=0||incomeBO5.getCommodity().intValue()<=0){
            yearDifferences.setCommodity(new BigDecimal(100));
        }else if(incomeBO5.getCommodity().intValue()>incomeBO3.getCommodity().intValue()){
            yearDifferences.setCommodity((incomeBO5.getCommodity().subtract(incomeBO3.getCommodity())).divide(incomeBO5.getCommodity(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }else{
            yearDifferences.setCommodity((incomeBO3.getCommodity().subtract(incomeBO5.getCommodity())).divide(incomeBO3.getCommodity(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }
        if(incomeBO3.getCompensation().intValue()<=0||incomeBO5.getCompensation().intValue()<=0){
            yearDifferences.setCompensation(new BigDecimal(100));
        }else if(incomeBO5.getCompensation().intValue()>incomeBO3.getCompensation().intValue()){
            yearDifferences.setCompensation((incomeBO5.getCompensation().subtract(incomeBO3.getCompensation())).divide(incomeBO5.getCompensation(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }else{
            yearDifferences.setCompensation((incomeBO3.getCompensation().subtract(incomeBO5.getCompensation())).divide(incomeBO3.getCompensation(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }
        if(incomeBO3.getMemberCardRate().intValue()<=0||incomeBO5.getMemberCardRate().intValue()<=0){
            yearDifferences.setMemberCardRate(new BigDecimal(100));
        }else if(incomeBO5.getMemberCardRate().intValue()>incomeBO3.getMemberCardRate().intValue()){
            yearDifferences.setMemberCardRate((incomeBO5.getMemberCardRate().subtract(incomeBO3.getMemberCardRate())).divide(incomeBO5.getMemberCardRate(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }else{
            yearDifferences.setMemberCardRate((incomeBO3.getMemberCardRate().subtract(incomeBO5.getMemberCardRate())).divide(incomeBO3.getMemberCardRate(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }
        if(incomeBO3.getDebtSum().intValue()<=0||incomeBO5.getDebtSum().intValue()<=0){
            yearDifferences.setDebtSum(new BigDecimal(100));
        }else if(incomeBO5.getDebtSum().intValue()>incomeBO3.getDebtSum().intValue()){
            yearDifferences.setDebtSum((incomeBO5.getDebtSum().subtract(incomeBO3.getDebtSum())).divide(incomeBO5.getDebtSum(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }else{
            yearDifferences.setDebtSum((incomeBO3.getDebtSum().subtract(incomeBO5.getDebtSum())).divide(incomeBO3.getDebtSum(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }
        if(incomeBO3.getCash().intValue()<=0||incomeBO5.getCash().intValue()<=0){
            yearDifferences.setCash(new BigDecimal(100));
        }else if(incomeBO5.getCash().intValue()>incomeBO3.getCash().intValue()){
            yearDifferences.setCash((incomeBO5.getCash().subtract(incomeBO3.getCash())).divide(incomeBO5.getCash(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }else{
            yearDifferences.setCash((incomeBO3.getCash().subtract(incomeBO5.getCash())).divide(incomeBO3.getCash(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }
        if(incomeBO3.getBankCard().intValue()<=0||incomeBO5.getBankCard().intValue()<=0){
            yearDifferences.setBankCard(new BigDecimal(100));
        }else if(incomeBO5.getBankCard().intValue()>incomeBO3.getBankCard().intValue()){
            yearDifferences.setBankCard((incomeBO5.getBankCard().subtract(incomeBO3.getBankCard())).divide(incomeBO5.getBankCard(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }else{
            yearDifferences.setBankCard((incomeBO3.getBankCard().subtract(incomeBO5.getBankCard())).divide(incomeBO3.getBankCard(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }
        if(incomeBO3.getWechat().intValue()<=0||incomeBO5.getWechat().intValue()<=0){
            yearDifferences.setWechat(new BigDecimal(100));
        }else if(incomeBO5.getWechat().intValue()>incomeBO3.getWechat().intValue()){
            yearDifferences.setWechat((incomeBO5.getWechat().subtract(incomeBO3.getWechat())).divide(incomeBO5.getWechat(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }else{
            yearDifferences.setWechat((incomeBO3.getWechat().subtract(incomeBO5.getWechat())).divide(incomeBO3.getWechat(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }
        if(incomeBO3.getAlipay().intValue()<=0||incomeBO5.getAlipay().intValue()<=0){
            yearDifferences.setAlipay(new BigDecimal(100));
        }else if(incomeBO5.getAlipay().intValue()>incomeBO3.getAlipay().intValue()){
            yearDifferences.setAlipay((incomeBO5.getAlipay().subtract(incomeBO3.getAlipay())).divide(incomeBO5.getAlipay(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }else{
            yearDifferences.setAlipay((incomeBO3.getAlipay().subtract(incomeBO5.getAlipay())).divide(incomeBO3.getAlipay(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }
        if(incomeBO3.getStoredPay().intValue()<=0||incomeBO5.getAlipay().intValue()<=0){
            yearDifferences.setStoredPay(new BigDecimal(100));
        }else if(incomeBO5.getStoredPay().intValue()>incomeBO3.getStoredPay().intValue()){
            yearDifferences.setStoredPay((incomeBO5.getStoredPay().subtract(incomeBO3.getStoredPay())).divide(incomeBO5.getStoredPay(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }else{
            yearDifferences.setStoredPay((incomeBO3.getStoredPay().subtract(incomeBO5.getStoredPay())).divide(incomeBO3.getStoredPay(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }
        if(incomeBO3.getCreditSum().intValue()<=0||incomeBO5.getAlipay().intValue()<=0){
            yearDifferences.setCreditSum(new BigDecimal(100));
        }else if(incomeBO5.getCreditSum().intValue()>incomeBO3.getCreditSum().intValue()){
            yearDifferences.setCreditSum((incomeBO5.getCreditSum().subtract(incomeBO3.getCreditSum())).divide(incomeBO5.getCreditSum(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }else{
            yearDifferences.setCreditSum((incomeBO3.getCreditSum().subtract(incomeBO5.getCreditSum())).divide(incomeBO3.getCreditSum(),RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        }

        //差异 用当月减去上月 除以上月
        incomeBOS.add(incomeBO);
        incomeBOS.add(incomeBO1);
        incomeBOS.add(incomeBO2);
        incomeBOS.add(incomeBO3);
        incomeBOS.add(incomeBO4);
        incomeBOS.add(monthDifferences);
        incomeBOS.add(incomeBO5);
        incomeBOS.add(yearDifferences);
        return incomeBOS;
    }

    /**
     * 添加营收记录
     * @param hotelId 酒店id
     * */
    public void addIncome(Integer hotelId){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=DateUtils.getYesTaday();
        //Date date=new Date();

        //获取今天凌晨四点
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 04);
        String endDateStr=simpleDateFormat.format(calendar.getTime());
        calendar.setTime(date);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 04);
        String dateStr=simpleDateFormat.format(calendar.getTime());
        IncomeBO incomeBO=new IncomeBO();
        //房费减免
        BigDecimal roomRateRoom=incomeDAO.getCashierSummaryByProject(dateStr,endDateStr, Constants.ROOMRATEFREE.getValue(),hotelId);
        if(roomRateRoom==null){
            roomRateRoom=new BigDecimal(0);
        }

        //房费
        BigDecimal roomRate=incomeDAO.getCashierSummaryByProject(dateStr,endDateStr, Constants.ROOMRATE.getValue(),hotelId);
        if(roomRate==null){
            roomRate=new BigDecimal(0);
        }
        incomeBO.setRoomRate(roomRate.add(roomRateRoom));

        //超时费减免
        BigDecimal mitigate=incomeDAO.getCashierSummaryByProject(dateStr, endDateStr,Constants.MITIGATE.getValue(),hotelId);
        if(mitigate==null){
            mitigate=new BigDecimal(0);
        }

        //超时费
        BigDecimal timeoutRoomRate=incomeDAO.getCashierSummaryByProject(dateStr,endDateStr,  Constants.TIMEOUTCOST.getValue(),hotelId);
        if(timeoutRoomRate==null){
            timeoutRoomRate=new BigDecimal(0);
        }
        incomeBO.setTimeoutRoomRate(timeoutRoomRate.add(mitigate));

        //商品减免
        BigDecimal commodityFerr=incomeDAO.getCashierSummaryByProject(dateStr, endDateStr,Constants.COMMODITYFREE.getValue(),hotelId);
        if(commodityFerr==null){
            commodityFerr=new BigDecimal(0);
        }

        //赔偿减免
        BigDecimal compEnsatinonEecomp=incomeDAO.getCashierSummaryByProject(dateStr,endDateStr, Constants.COMPENSATIONFREE.getValue(),hotelId);
        if(compEnsatinonEecomp==null){
            compEnsatinonEecomp=new BigDecimal(0);
        }

        //房费调整
        BigDecimal roomRateAdjustment=roomRateRoom.add(mitigate);
        if(roomRateAdjustment==null){
            roomRateAdjustment=new BigDecimal(0);
        }
        incomeBO.setRoomRateAdjustment(roomRateAdjustment.negate());

        //商品
        BigDecimal commodity=incomeDAO.getCashierSummaryByProject(dateStr,endDateStr, Constants.COMMODITY.getValue(),hotelId);
        if(commodity==null){
            commodity=new BigDecimal(0);
        }
        incomeBO.setCommodity(commodity.add(commodityFerr));

        //赔偿
        BigDecimal compensation=incomeDAO.getCashierSummaryByProject(dateStr,endDateStr, Constants.COMPENSATE.getValue(),hotelId);
        if(compensation==null){
            compensation=new BigDecimal(0);
        }
        incomeBO.setCompensation(compensation.add(compEnsatinonEecomp));

        //会员卡收入
        BigDecimal memberCardRate=incomeDAO.getCashierSummaryByProject(dateStr, endDateStr,Constants.APPLYCARD.getValue(),hotelId);
        if(memberCardRate==null){
            memberCardRate=new BigDecimal(0);
        }
        incomeBO.setMemberCardRate(memberCardRate);
        //其他费用
        BigDecimal otherRate=incomeBO.getCommodity().add(incomeBO.getCompensation()).add(memberCardRate);
        if(otherRate==null){
            otherRate=new BigDecimal(0);
        }
        incomeBO.setOtherRate(otherRate);

        //借方总记：房费+超时费+其他费用
        BigDecimal debtSum= incomeBO.getRoomRate().add(timeoutRoomRate).add(otherRate);

        if(debtSum==null){
            debtSum=new BigDecimal(0);
        }
        incomeBO.setDebtSum(debtSum);
        //现金
        BigDecimal cash=incomeDAO.getCashierSummaryByType(dateStr,endDateStr,Constants.CASH.getValue(),hotelId);
        if(cash==null){
            cash=new BigDecimal(0);
        }
        incomeBO.setCash(cash);
        //银行卡
        BigDecimal bankCard=incomeDAO.getCashierSummaryByType(dateStr,endDateStr,Constants.CART.getValue(),hotelId);
        if(bankCard==null){
            bankCard=new BigDecimal(0);
        }
        incomeBO.setBankCard(bankCard);
        //微信
        BigDecimal wechat=incomeDAO.getCashierSummaryByType(dateStr,endDateStr,Constants.WECHAT.getValue(),hotelId);
        if(wechat==null){
            wechat=new BigDecimal(0);
        }
        incomeBO.setWechat(wechat);
        //支付宝
        BigDecimal alipay=incomeDAO.getCashierSummaryByType(dateStr,endDateStr,Constants.ALIPAY.getValue(),hotelId);
        if(alipay==null){
            alipay=new BigDecimal(0);
        }
        incomeBO.setAlipay(alipay);
        //储值支付
        BigDecimal storedPay=incomeDAO.getCashierSummaryByType(dateStr,endDateStr,Constants.STORED.getValue(),hotelId);
        if(storedPay==null){
            storedPay=new BigDecimal(0);
        }
        incomeBO.setStoredPay(storedPay);
        //新增:其他支付
        BigDecimal other=incomeDAO.getCashierSummaryByType(dateStr,endDateStr,Constants.OTHER.getValue(),hotelId);
        if(other==null){
            other=new BigDecimal(0);
        }
        incomeBO.setOther(other);
        //贷方总记
        BigDecimal creditSum=cash.add(bankCard).add(wechat).add(alipay).add(other);
        if(creditSum==null){
            creditSum=new BigDecimal(0);
        }
        incomeBO.setCreditSum(creditSum);
        //夜审时间
        Date nightAuditorTime=date;
        incomeBO.setNightAuditorTime(nightAuditorTime);
        incomeBO.setHotelId(hotelId);
        //这得计算 每个字段的金额
        incomeDAO.addIncome(incomeBO);
    }
}
