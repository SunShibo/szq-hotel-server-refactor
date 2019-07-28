package com.szq.hotel.service;

import com.szq.hotel.common.constants.Constants;
import com.szq.hotel.dao.IncomeDAO;
import com.szq.hotel.entity.bo.IncomeBO;
import com.szq.hotel.util.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class IncomeService {

    @Resource
    IncomeDAO incomeDAO;

    //获取营收入报表
    public List<IncomeBO> getIncome(Integer hotelId){
        List<IncomeBO> incomeBOS=new ArrayList<IncomeBO>();
        //获取今天日期
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if(hour<=6){
            calendar.add(Calendar.DATE, -1);
        }
        int year = calendar.get(Calendar.YEAR);//获取年份

        int month=calendar.get(Calendar.MONTH);//获取月份

        int day=calendar.get(Calendar.DATE);//获取日

        //获取当日营收
        IncomeBO incomeBO=new IncomeBO();
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
        if(incomeBO1.getRoomRate().toString().equals("0")){
            monthDifferences.setRoomRate(new BigDecimal(100));
        }else{
            monthDifferences.setRoomRate((incomeBO1.getRoomRate().subtract(incomeBO4.getRoomRate())).divide(incomeBO1.getRoomRate()).multiply(new BigDecimal(100)));
        }
        if(incomeBO1.getTimeoutRoomRate().toString().equals("0")){
            monthDifferences.setTimeoutRoomRate(new BigDecimal(100));
        }else{
            monthDifferences.setTimeoutRoomRate((incomeBO1.getTimeoutRoomRate().subtract(incomeBO4.getTimeoutRoomRate())).divide(incomeBO1.getTimeoutRoomRate()).multiply(new BigDecimal(100)));
        }
        if(incomeBO1.getRoomRateAdjustment().toString().equals("0")){
            monthDifferences.setRoomRateAdjustment(new BigDecimal(100));
        }else{
            monthDifferences.setRoomRateAdjustment((incomeBO1.getRoomRateAdjustment().subtract(incomeBO4.getRoomRateAdjustment())).divide(incomeBO1.getRoomRateAdjustment()).multiply(new BigDecimal(100)));
        }
        if(incomeBO1.getOtherRate().toString().equals("0")){
            monthDifferences.setOtherRate(new BigDecimal(100));
        }else{
            monthDifferences.setOtherRate((incomeBO1.getOtherRate().subtract(incomeBO4.getOtherRate())).divide(incomeBO1.getOtherRate()).multiply(new BigDecimal(100)));
        }
        if(incomeBO1.getCommodity().toString().equals("0")){
            monthDifferences.setCommodity(new BigDecimal(100));
        }else{
            monthDifferences.setCommodity((incomeBO1.getCommodity().subtract(incomeBO4.getCommodity())).divide(incomeBO1.getCommodity()).multiply(new BigDecimal(100)));
        }
        if(incomeBO1.getCompensation().toString().equals("0")){
            monthDifferences.setCompensation(new BigDecimal(100));
        }else{
            monthDifferences.setCompensation((incomeBO1.getCompensation().subtract(incomeBO4.getCompensation())).divide(incomeBO1.getCompensation()).multiply(new BigDecimal(100)));
        }
        if(incomeBO1.getMemberCardRate().toString().equals("0")){
            monthDifferences.setMemberCardRate(new BigDecimal(100));
        }else{
            monthDifferences.setMemberCardRate((incomeBO1.getMemberCardRate().subtract(incomeBO4.getMemberCardRate())).divide(incomeBO1.getMemberCardRate()).multiply(new BigDecimal(100)));
        }
        if(incomeBO1.getDebtSum().toString().equals("0")){
            monthDifferences.setDebtSum(new BigDecimal(100));
        }else{
            monthDifferences.setDebtSum((incomeBO1.getDebtSum().subtract(incomeBO4.getDebtSum())).divide(incomeBO1.getDebtSum()).multiply(new BigDecimal(100)));
        }
        if(incomeBO1.getCash().toString().equals("0")){
            monthDifferences.setCash(new BigDecimal(100));
        }else{
            monthDifferences.setCash((incomeBO1.getCash().subtract(incomeBO4.getCash())).divide(incomeBO1.getCash()).multiply(new BigDecimal(100)));
        }
        if(incomeBO1.getBankCard().toString().equals("0")){
            monthDifferences.setBankCard(new BigDecimal(100));
        }else{
            monthDifferences.setBankCard((incomeBO1.getBankCard().subtract(incomeBO4.getBankCard())).divide(incomeBO1.getBankCard()).multiply(new BigDecimal(100)));
        }
        if(incomeBO1.getWechat().toString().equals("0")){
            monthDifferences.setWechat(new BigDecimal(100));
        }else{
            monthDifferences.setWechat((incomeBO1.getWechat().subtract(incomeBO4.getWechat())).divide(incomeBO1.getWechat()).multiply(new BigDecimal(100)));
        }
        if(incomeBO1.getAlipay().toString().equals("0")){
            monthDifferences.setAlipay(new BigDecimal(100));
        }else{
            monthDifferences.setAlipay((incomeBO1.getAlipay().subtract(incomeBO4.getAlipay())).divide(incomeBO1.getAlipay()).multiply(new BigDecimal(100)));
        }
        if(incomeBO1.getStoredPay().toString().equals("0")){
            monthDifferences.setStoredPay(new BigDecimal(100));
        }else{
            monthDifferences.setStoredPay((incomeBO1.getStoredPay().subtract(incomeBO4.getStoredPay())).divide(incomeBO1.getStoredPay()).multiply(new BigDecimal(100)));
        }
        if(incomeBO1.getCreditSum().toString().equals("0")){
            monthDifferences.setCreditSum(new BigDecimal(100));
        }else{
            monthDifferences.setCreditSum((incomeBO1.getCreditSum().subtract(incomeBO4.getCreditSum())).divide(incomeBO1.getCreditSum()).multiply(new BigDecimal(100)));
        }
        //今年和去年的差异
        IncomeBO yearDifferences=new IncomeBO();
        if(incomeBO3.getRoomRate().intValue()<=0){
            yearDifferences.setRoomRate(new BigDecimal(0));
        }else{
            yearDifferences.setRoomRate((incomeBO3.getRoomRate().subtract(incomeBO5.getRoomRate())).divide(incomeBO3.getRoomRate()).multiply(new BigDecimal(100)));
        }
        if(incomeBO3.getTimeoutRoomRate().intValue()<=0){
            yearDifferences.setTimeoutRoomRate(new BigDecimal(100));
        }else{
            yearDifferences.setTimeoutRoomRate((incomeBO3.getTimeoutRoomRate().subtract(incomeBO5.getTimeoutRoomRate())).divide(incomeBO3.getTimeoutRoomRate()).multiply(new BigDecimal(100)));
        }
        if(incomeBO3.getRoomRateAdjustment().intValue()<=0){
            yearDifferences.setRoomRateAdjustment(new BigDecimal(100));
        }else{
            yearDifferences.setRoomRateAdjustment((incomeBO3.getRoomRateAdjustment().subtract(incomeBO5.getRoomRateAdjustment())).divide(incomeBO3.getRoomRateAdjustment()).multiply(new BigDecimal(100)));
        }
        if(incomeBO3.getOtherRate().intValue()<=0){
            yearDifferences.setOtherRate(new BigDecimal(100));
        }else{
            yearDifferences.setOtherRate((incomeBO1.getOtherRate().subtract(incomeBO5.getOtherRate())).divide(incomeBO3.getOtherRate()).multiply(new BigDecimal(100)));
        }
        if(incomeBO3.getCommodity().intValue()<=0){
            yearDifferences.setCommodity(new BigDecimal(100));
        }else{
            yearDifferences.setCommodity((incomeBO3.getCommodity().subtract(incomeBO5.getCommodity())).divide(incomeBO3.getCommodity()).multiply(new BigDecimal(100)));
        }
        if(incomeBO3.getCompensation().intValue()<=0){
            yearDifferences.setCompensation(new BigDecimal(100));
        }else{
            yearDifferences.setCompensation((incomeBO3.getCompensation().subtract(incomeBO5.getCompensation())).divide(incomeBO3.getCompensation()).multiply(new BigDecimal(100)));
        }
        if(incomeBO3.getMemberCardRate().intValue()<=0){
            yearDifferences.setMemberCardRate(new BigDecimal(100));
        }else{
            yearDifferences.setMemberCardRate((incomeBO3.getMemberCardRate().subtract(incomeBO5.getMemberCardRate())).divide(incomeBO3.getMemberCardRate()).multiply(new BigDecimal(100)));
        }
        if(incomeBO3.getDebtSum().intValue()<=0){
            yearDifferences.setDebtSum(new BigDecimal(100));
        }else{
            yearDifferences.setDebtSum((incomeBO3.getDebtSum().subtract(incomeBO5.getDebtSum())).divide(incomeBO3.getDebtSum()).multiply(new BigDecimal(100)));
        }
        if(incomeBO3.getCash().intValue()<=0){
            yearDifferences.setCash(new BigDecimal(100));
        }else{
            yearDifferences.setCash((incomeBO3.getCash().subtract(incomeBO5.getCash())).divide(incomeBO3.getCash()).multiply(new BigDecimal(100)));
        }
        if(incomeBO3.getBankCard().intValue()<=0){
            yearDifferences.setBankCard(new BigDecimal(100));
        }else{
            yearDifferences.setBankCard((incomeBO3.getBankCard().subtract(incomeBO5.getBankCard())).divide(incomeBO3.getBankCard()).multiply(new BigDecimal(100)));
        }
        if(incomeBO3.getWechat().intValue()<=0){
            yearDifferences.setWechat(new BigDecimal(100));
        }else{
            yearDifferences.setWechat((incomeBO3.getWechat().subtract(incomeBO5.getWechat())).divide(incomeBO3.getWechat()).multiply(new BigDecimal(100)));
        }
        if(incomeBO3.getAlipay().intValue()<=0){
            yearDifferences.setAlipay(new BigDecimal(100));
        }else{
            yearDifferences.setAlipay((incomeBO3.getAlipay().subtract(incomeBO5.getAlipay())).divide(incomeBO3.getAlipay()).multiply(new BigDecimal(100)));
        }
        if(incomeBO3.getStoredPay().intValue()<=0){
            yearDifferences.setStoredPay(new BigDecimal(100));
        }else{
            yearDifferences.setStoredPay((incomeBO3.getStoredPay().subtract(incomeBO5.getStoredPay())).divide(incomeBO3.getStoredPay()).multiply(new BigDecimal(100)));
        }
        if(incomeBO3.getCreditSum().intValue()<=0){
            yearDifferences.setCreditSum(new BigDecimal(100));
        }else{
            yearDifferences.setCreditSum((incomeBO3.getCreditSum().subtract(incomeBO5.getCreditSum())).divide(incomeBO3.getCreditSum()).multiply(new BigDecimal(100)));
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
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date date=DateUtils.getYesTaday();
        String dateStr=simpleDateFormat.format(date);
        IncomeBO incomeBO=new IncomeBO();
        //房费
        BigDecimal roomRate=incomeDAO.getCashierSummaryByProject(dateStr, Constants.ROOMRATE.getValue(),hotelId);
        if(roomRate==null){
            roomRate=new BigDecimal(0);
        }
        incomeBO.setRoomRate(roomRate);
        //超时费
        BigDecimal timeoutRoomRate=incomeDAO.getCashierSummaryByProject(dateStr, Constants.TIMEOUTCOST.getValue(),hotelId);
        if(timeoutRoomRate==null){
            timeoutRoomRate=new BigDecimal(0);
        }
        incomeBO.setTimeoutRoomRate(timeoutRoomRate);
        //房费减免
        BigDecimal roomRateRoom=incomeDAO.getCashierSummaryByProject(dateStr, Constants.ROOMRATEFREE.getValue(),hotelId);
        if(roomRateRoom==null){
            roomRateRoom=new BigDecimal(0);
        }
        incomeBO.setRoomRate(roomRateRoom);
        //商品减免
        BigDecimal commodityFerr=incomeDAO.getCashierSummaryByProject(dateStr, Constants.COMMODITYFREE.getValue(),hotelId);
        if(commodityFerr==null){
            commodityFerr=new BigDecimal(0);
        }
        //赔偿减免
        BigDecimal compEnsatinonEecomp=incomeDAO.getCashierSummaryByProject(dateStr, Constants.COMPENSATIONFREE.getValue(),hotelId);
        if(compEnsatinonEecomp==null){
            compEnsatinonEecomp=new BigDecimal(0);
        }
        //超时费减免
        BigDecimal mitigate=incomeDAO.getCashierSummaryByProject(dateStr, Constants.MITIGATE.getValue(),hotelId);
        if(mitigate==null){
            mitigate=new BigDecimal(0);
        }
        //房费调整
        BigDecimal roomRateAdjustment=roomRateRoom.add(commodityFerr).add(compEnsatinonEecomp).add(mitigate);
        if(roomRateAdjustment==null){
            roomRateAdjustment=new BigDecimal(0);
        }
        incomeBO.setRoomRateAdjustment(roomRateAdjustment);
        //商品
        BigDecimal commodity=incomeDAO.getCashierSummaryByProject(dateStr, Constants.COMMODITY.getValue(),hotelId);
        if(commodity==null){
            commodity=new BigDecimal(0);
        }
        incomeBO.setCommodity(commodity);
        //赔偿
        BigDecimal compensation=incomeDAO.getCashierSummaryByProject(dateStr, Constants.COMPENSATE.getValue(),hotelId);
        if(compensation==null){
            compensation=new BigDecimal(0);
        }
        incomeBO.setCompensation(compensation);
        //会员卡收入
        BigDecimal memberCardRate=incomeDAO.getCashierSummaryByProject(dateStr, Constants.APPLYCARD.getValue(),hotelId);
        if(memberCardRate==null){
            memberCardRate=new BigDecimal(0);
        }
        incomeBO.setMemberCardRate(memberCardRate);
        //其他费用
        BigDecimal otherRate=commodity.add(compensation).add(memberCardRate);
        if(otherRate==null){
            otherRate=new BigDecimal(0);
        }
        incomeBO.setOtherRate(otherRate);
        //借方总记
        BigDecimal debtSum=roomRate.add(timeoutRoomRate).add(roomRateAdjustment).add(otherRate).add(commodity).add(compensation).add(memberCardRate);
        if(debtSum==null){
            debtSum=new BigDecimal(0);
        }
        incomeBO.setDebtSum(debtSum);
        //现金
        BigDecimal cash=incomeDAO.getCashierSummaryByType(dateStr,Constants.CASH.getValue(),hotelId);
        if(cash==null){
            cash=new BigDecimal(0);
        }
        incomeBO.setCash(cash);
        //银行卡
        BigDecimal bankCard=incomeDAO.getCashierSummaryByType(dateStr,Constants.CART.getValue(),hotelId);
        if(bankCard==null){
            bankCard=new BigDecimal(0);
        }
        incomeBO.setBankCard(bankCard);
        //微信
        BigDecimal wechat=incomeDAO.getCashierSummaryByType(dateStr,Constants.WECHAT.getValue(),hotelId);
        if(wechat==null){
            wechat=new BigDecimal(0);
        }
        incomeBO.setWechat(wechat);
        //支付宝
        BigDecimal alipay=incomeDAO.getCashierSummaryByType(dateStr,Constants.ALIPAY.getValue(),hotelId);
        if(alipay==null){
            alipay=new BigDecimal(0);
        }
        incomeBO.setAlipay(alipay);
        //储值支付
        BigDecimal storedPay=incomeDAO.getCashierSummaryByType(dateStr,Constants.STORED.getValue(),hotelId);
        if(storedPay==null){
            storedPay=new BigDecimal(0);
        }
        incomeBO.setStoredPay(storedPay);
        //贷方总记
        BigDecimal creditSum=cash.add(bankCard).add(wechat).add(alipay).add(storedPay);
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
