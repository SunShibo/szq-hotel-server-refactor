package com.szq.hotel.service;

import com.szq.hotel.dao.ManagementReportDAO;
import com.szq.hotel.entity.bo.ManagementReportResponseBO;
import com.szq.hotel.entity.bo.FormUtilBO;
import com.szq.hotel.entity.bo.ManagementReportBO;
import com.szq.hotel.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("managementReportService")
public class ManagementReportService {
    private static final Logger log = LoggerFactory.getLogger(ManagementReportService.class);

    @Resource
    private ManagementReportDAO managementReportDAO;

    /**
     * 添加数据
     * @param hotelId 酒店id
     */
    public void addData(Integer hotelId){
        log.info("start addData..........ManagementReportService.................................");
        log.info("hotelId:{}",hotelId);
        Map<String,Object> map = new HashMap<String, Object>();
        Date date = DateUtils.getYesTaday();
        String endTime = DateUtils.getStringData(new Date(),"yyyy-MM-dd");
        String endTime1 = endTime+" 04:00:00";
        String startTime = DateUtils.getLastDay(endTime);
        String startTime1 = startTime+" 04:00:00";
        map.put("startTime",startTime1);
        map.put("endTime",endTime1);//推迟1分钟防止网络延迟
        map.put("hotelId",hotelId);
        log.info("map:{}",map);
        ManagementReportBO managementReportBO = new ManagementReportBO();

        managementReportBO.setReceivableSum(this.getReceivableSum(map));
        managementReportBO.setAvgRoomRate(this.avgRoomRate(map));
        managementReportBO.setAvgConsumptionOfRoom(this.getAvgConsumptionOfRoom(map));
        managementReportBO.setAvgConsumptionOfPerson(this.getAvgConsumptionOfPerson(map));
        managementReportBO.setIndemnityIncome(this.getCompensation(map));
        managementReportBO.setFreeCheckInSum(this.getFreeRoomSum(map));
        managementReportBO.setRoomSum(this.getRoomSum(map));
        managementReportBO.setMaintainRoomSum(this.getMaintainSum(map));
        managementReportBO.setMemberRoomSum(this.getMemberRoomSum(map));
        managementReportBO.setMemberCardSoldMoney(this.getMemberCardRate(map));
        managementReportBO.setRoomLateSum(this.getRoomLateSum(map));
        managementReportBO.setPersonLateSum(this.getPersonLateSum(map));
        managementReportBO.setCommodityRevenues(this.getCommodity(map));
        managementReportBO.setRoomRateAdjustment(this.getRoomRateAdjustment(map));
        managementReportBO.setOccupancyRate(this.getOccupancyRate(map).toString());
        managementReportBO.setREVPAR(this.REVPAR(map).toString());
        managementReportBO.setDisableRoomSum(this.getDisableRoomSum(map));
        managementReportBO.setRentalIncome(this.getRoomRate(map));
        managementReportBO.setEmptyRoomSum(this.getEmptyRoomSum(map));
        managementReportBO.setHourRoomLateSum(this.getHourRoomSum(map));
        managementReportBO.setHotelId(hotelId);
        managementReportBO.setCreateTime(date);
        log.info("managementReportBO:{}",managementReportBO);
        managementReportDAO.addData(managementReportBO);
        log.info("end  addData............ManagementReportService...............................");
    }

    /*
        查询结果
     */
    public ManagementReportResponseBO selectManagementReport(Date startTime, Date endTime, Integer hotelId)throws ParseException {
        log.info("start selectManagementReport============ManagementReportService=================");
        log.info("startTime:{},endTime:{},hotelId:{}",startTime,endTime,hotelId);
        ManagementReportResponseBO managementReportResponseBO = new ManagementReportResponseBO();
        FormUtilBO receivableSum = new FormUtilBO();//应收合计
        FormUtilBO avgRoomRate = new FormUtilBO();//平均房价
        FormUtilBO avgConsumptionOfRoom = new FormUtilBO();//房平均消费
        FormUtilBO avgConsumptionOfPerson = new FormUtilBO();//人均消费
        FormUtilBO indemnityIncome = new FormUtilBO();//赔偿收入
        FormUtilBO freeCheckInSum = new FormUtilBO();//免费入住房数
        FormUtilBO roomSum = new FormUtilBO();//房间总数
        FormUtilBO maintainRoomSum = new FormUtilBO();//维修房数
        FormUtilBO memberRoomSum = new FormUtilBO();//会员房数
        FormUtilBO memberCardSoldMoney = new FormUtilBO();//会员卡销售金额
        FormUtilBO roomLateSum = new FormUtilBO();//房晚数
        FormUtilBO personLateSum = new FormUtilBO();//人晚数
        FormUtilBO commodityRevenues = new FormUtilBO();//商品收入
        FormUtilBO roomRateAdjustment = new FormUtilBO();//房费调整 //
        FormUtilBO occupancyRate = new FormUtilBO();//出租率
        FormUtilBO REVPAR = new FormUtilBO();//REVPAR = 应收合计 / (总房间数 - 维修房数)
        FormUtilBO disableRoomSum = new FormUtilBO();//停用房间数
        FormUtilBO rentalIncome = new FormUtilBO();//房租收入
        FormUtilBO emptyRoomSum = new FormUtilBO();//空房数
        FormUtilBO hourRoomLateSum = new FormUtilBO();//钟点房晚数

        //查询当天
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("hotelId", hotelId);

        ManagementReportBO managementReportBO = managementReportDAO.selectManagementReport(map);

        if (managementReportBO!=null){
            receivableSum.setDay(managementReportBO.getReceivableSum().toString());//应收合计
            avgRoomRate.setDay(managementReportBO.getAvgRoomRate().toString());//平均房价
            avgConsumptionOfRoom.setDay(managementReportBO.getAvgConsumptionOfRoom().toString());//房平均消费
            avgConsumptionOfPerson.setDay(managementReportBO.getAvgConsumptionOfPerson().toString());//人均消费
            indemnityIncome.setDay(managementReportBO.getIndemnityIncome().toString());//赔偿收入
            freeCheckInSum.setDay(managementReportBO.getFreeCheckInSum().toString());//免费入住房数
            roomSum.setDay(managementReportBO.getRoomSum().toString());//房间总数
            maintainRoomSum.setDay(managementReportBO.getMaintainRoomSum().toString());//维修房数
            memberRoomSum.setDay(managementReportBO.getMemberRoomSum().toString());//会员房数
            memberCardSoldMoney.setDay(managementReportBO.getMemberCardSoldMoney().toString());//会员卡销售金额
            roomLateSum.setDay(managementReportBO.getRoomLateSum().toString());//房晚数
            personLateSum.setDay(managementReportBO.getPersonLateSum().toString());//人晚数
            commodityRevenues.setDay(managementReportBO.getCommodityRevenues().toString());//商品收入
            roomRateAdjustment.setDay(managementReportBO.getRoomRateAdjustment().toString());//房费调整
            occupancyRate.setDay(managementReportBO.getOccupancyRate());//出租率
            REVPAR.setDay(managementReportBO.getREVPAR());//REVPAR = 应收合计 / (总房间数 - 维修房数)
            disableRoomSum.setDay(managementReportBO.getDisableRoomSum().toString());//停用房间数
            rentalIncome.setDay(managementReportBO.getRentalIncome().toString());//房租收入
            emptyRoomSum.setDay(managementReportBO.getEmptyRoomSum().toString());//空房数
            hourRoomLateSum.setDay(managementReportBO.getHourRoomLateSum().toString());//钟点房晚数
        }


        //查询本月
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取前月的第一天
        Calendar cal_1 = Calendar.getInstance();
        //获取当前日期
        cal_1.setTime(startTime);
        cal_1.add(Calendar.MONTH, 0);
        cal_1.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        String firstDay = format.format(cal_1.getTime());
        String str = firstDay + " 03:00:00";
        Date startTime1 = dd.parse(str);

        Calendar ct = Calendar.getInstance();
        ct.setTime(endTime);
        String firstDay1 = format.format(ct.getTime());
        String endTime1 = firstDay1 + " 05:00:00";
        //当前时间
        Date endTime111 = dd.parse(endTime1);
        //获取当前月最后一天
        ct.set(Calendar.DAY_OF_MONTH, ct.getActualMaximum(Calendar.DAY_OF_MONTH));
        //ct.add(Calendar.DAY_OF_MONTH,1);
        String last = format.format(ct.getTime())+" 04:00:00";
        Date endTime0 = dd.parse(last);

        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("startTime", startTime1);
        //结束时间为当前日
        //map1.put("endTime", endTime111);
        //结束时间为月底
        map1.put("endTime",endTime0);
        map1.put("hotelId", hotelId);

        ManagementReportBO managementReportMonth = managementReportDAO.selectManagementReport(map1);
        if (managementReportMonth!=null){
            receivableSum.setMonth(managementReportMonth.getReceivableSum().toString());//应收合计
            //房晚数
            BigDecimal roomLateSumMonth = new BigDecimal(managementReportMonth.getRoomLateSum());
            //应收合计
            BigDecimal receivableSumMonth=managementReportMonth.getReceivableSum();
            //会员卡收入
            BigDecimal memberCardSoldMoneyMonth=managementReportMonth.getMemberCardSoldMoney();
            //差
            BigDecimal q = receivableSumMonth.subtract(memberCardSoldMoneyMonth);
            if (roomLateSumMonth.intValue()==0){
                avgRoomRate.setMonth("0.00");
                avgConsumptionOfRoom.setMonth("0.00");
            }else {
                //房平均消费-房间所有总消费（应收合计-会员卡收入）/房晚数
                avgConsumptionOfRoom.setMonth(q.divide(roomLateSumMonth, 2, BigDecimal.ROUND_HALF_UP).toString());
                //平均房价-房租收入/房晚数
                avgRoomRate.setMonth(managementReportMonth.getRentalIncome().divide(roomLateSumMonth, 2, BigDecimal.ROUND_HALF_UP).toString());
            }
            //人晚数
            Integer personLateSumMonth=managementReportMonth.getPersonLateSum();
            if (personLateSumMonth==0){
                avgConsumptionOfPerson.setMonth("0.00");
            }else {
                //人均消费--应收合计/人晚数
                avgConsumptionOfPerson.setMonth(receivableSumMonth.divide(new BigDecimal(personLateSumMonth), 2, BigDecimal.ROUND_HALF_UP).toString());
            }
            indemnityIncome.setMonth(managementReportMonth.getIndemnityIncome().toString());//赔偿收入
            freeCheckInSum.setMonth(managementReportMonth.getFreeCheckInSum().toString());//免费入住房数
            roomSum.setMonth(managementReportMonth.getRoomSum().toString());//房间总数
            maintainRoomSum.setMonth(managementReportMonth.getMaintainRoomSum().toString());//维修房数
            memberRoomSum.setMonth(managementReportMonth.getMemberRoomSum().toString());//会员房数
            memberCardSoldMoney.setMonth(managementReportMonth.getMemberCardSoldMoney().toString());//会员卡销售金额
            roomLateSum.setMonth(managementReportMonth.getRoomLateSum().toString());//房晚数
            personLateSum.setMonth(managementReportMonth.getPersonLateSum().toString());//人晚数
            commodityRevenues.setMonth(managementReportMonth.getCommodityRevenues().toString());//商品收入
            roomRateAdjustment.setMonth(managementReportMonth.getRoomRateAdjustment().toString());//房费调整
            //房间总数
            BigDecimal roomSumMonth = new BigDecimal(managementReportMonth.getRoomSum());
            //维修房数
            BigDecimal maintainRoomSumMonth = new BigDecimal(managementReportMonth.getMaintainRoomSum());
            //差
            BigDecimal i=roomSumMonth.subtract(maintainRoomSumMonth);
            if (i.intValue()==0){
                occupancyRate.setMonth("0");
                REVPAR.setMonth("0.00");
            }else {
                //出租率-房晚数 / (总房间数 - 维修房数)
                occupancyRate.setMonth(roomLateSumMonth.divide(i, 4, BigDecimal.ROUND_HALF_UP).toString());
                REVPAR.setMonth(receivableSumMonth.divide(i,2,BigDecimal.ROUND_HALF_UP).toString() );//REVPAR = 应收合计 / (总房间数 - 维修房数)
            }

            disableRoomSum.setMonth(managementReportMonth.getDisableRoomSum().toString());//停用房间数
            rentalIncome.setMonth(managementReportMonth.getRentalIncome().toString());//房租收入
            emptyRoomSum.setMonth(managementReportMonth.getEmptyRoomSum().toString());//空房数
            hourRoomLateSum.setMonth(managementReportMonth.getHourRoomLateSum().toString());//钟点房晚数
        }


        //查询本年

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);
        int s = calendar.get(calendar.YEAR);

        Calendar calendar1 = Calendar.getInstance();
        calendar1.clear();
        calendar1.set(Calendar.YEAR, s);
        String firstDay2 = format.format(calendar1.getTime());
        String endTime2 = firstDay2 + " 03:00:00";
        Date s1 = dd.parse(endTime2);
//        calendar1.set(Calendar.YEAR, s + 1);
//        String firstDay3 = format.format(calendar1.getTime());
//        String endTime3 = firstDay3 + " 05:00:00";
//        Date s2 = dd.parse(endTime3);
        calendar1.set(Calendar.MONTH,calendar1.getActualMaximum(Calendar.MONTH));
        calendar1.set(Calendar.DAY_OF_MONTH,calendar1.getActualMaximum(Calendar.DAY_OF_MONTH));
        String firstDay3 = format.format(calendar1.getTime());
        String endTime3 = firstDay3 + " 05:00:00";
        Date s2 = dd.parse(endTime3);

        Calendar qq = Calendar.getInstance();
        qq.set(Calendar.YEAR, s - 1);
        qq.set(Calendar.DAY_OF_YEAR,1);
        String firstDay4 = format.format(qq.getTime());
        String endTime4 = firstDay4 + " 03:00:00";
        Date s3 = dd.parse(endTime4);


        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("startTime", s1);
        map2.put("endTime", s2);
        map2.put("hotelId", hotelId);

        ManagementReportBO managementReportYear = managementReportDAO.selectManagementReport(map2);
        if(managementReportYear!=null){
            receivableSum.setYear(managementReportYear.getReceivableSum().toString());//应收合计
            //房晚数
            BigDecimal roomLateSumYear = new BigDecimal(managementReportYear.getRoomLateSum());
            //应收合计
            BigDecimal receivableSumYear=managementReportYear.getReceivableSum();
            //会员卡收入
            BigDecimal memberCardSoldMoneyYear=managementReportYear.getMemberCardSoldMoney();
            //差
            BigDecimal q = receivableSumYear.subtract(memberCardSoldMoneyYear);
            if (roomLateSumYear.intValue()==0){
                avgRoomRate.setYear("0.00");
                avgConsumptionOfRoom.setYear("0.00");
            }else {
                //平均房价=房租收入/房晚数
                avgRoomRate.setYear(managementReportYear.getRentalIncome().divide(roomLateSumYear,2,BigDecimal.ROUND_HALF_UP).toString());
                //房平均消费-房间所有总消费（应收合计-会员卡收入）/房晚数
                avgConsumptionOfRoom.setYear(q.divide(roomLateSumYear,2,BigDecimal.ROUND_HALF_UP).toString());//房平均消费
            }
            //人晚数
            Integer personLateSumYear=managementReportYear.getPersonLateSum();
            if (personLateSumYear==0){
                avgConsumptionOfPerson.setYear("0.00");
            }else {
                //人均消费--应收合计/人晚数
                avgConsumptionOfPerson.setYear(receivableSumYear.divide(new BigDecimal(personLateSumYear), 2, BigDecimal.ROUND_HALF_UP).toString());
            }
            indemnityIncome.setYear(managementReportYear.getIndemnityIncome().toString());//赔偿收入
            freeCheckInSum.setYear(managementReportYear.getFreeCheckInSum().toString());//免费入住房数
            roomSum.setYear(managementReportYear.getRoomSum().toString());//房间总数
            maintainRoomSum.setYear(managementReportYear.getMaintainRoomSum().toString());//维修房数
            memberRoomSum.setYear(managementReportYear.getMemberRoomSum().toString());//会员房数
            memberCardSoldMoney.setYear(managementReportYear.getMemberCardSoldMoney().toString());//会员卡销售金额
            roomLateSum.setYear(managementReportYear.getRoomLateSum().toString());//房晚数
            personLateSum.setYear(managementReportYear.getPersonLateSum().toString());//人晚数
            commodityRevenues.setYear(managementReportYear.getCommodityRevenues().toString());//商品收入
            roomRateAdjustment.setYear(managementReportYear.getRoomRateAdjustment().toString());//房费调整
            //房间总数
            BigDecimal roomSumYaer = new BigDecimal(managementReportYear.getRoomSum());
            //维修房数
            BigDecimal maintainRoomSumYear = new BigDecimal(managementReportYear.getMaintainRoomSum());
            //差
            BigDecimal i=roomSumYaer.subtract(maintainRoomSumYear);
            if (i.intValue()==0){
                occupancyRate.setYear("0");
                REVPAR.setYear("0.00");
            }else {
                occupancyRate.setYear(roomLateSumYear.divide(i,4,BigDecimal.ROUND_HALF_UP).toString());//出租率=房晚数 / (总房间数 - 维修房数)
                //REVPAR = 应收合计 / (总房间数 - 维修房数)
                REVPAR.setYear(receivableSumYear.divide(i,2,BigDecimal.ROUND_HALF_UP).toString());//REVPAR = 应收合计 / (总房间数 - 维修房数)
            }
            disableRoomSum.setYear(managementReportYear.getDisableRoomSum().toString());//停用房间数
            rentalIncome.setYear(managementReportYear.getRentalIncome().toString());//房租收入
            emptyRoomSum.setYear(managementReportYear.getEmptyRoomSum().toString());//空房数
            hourRoomLateSum.setYear(managementReportYear.getHourRoomLateSum().toString());//钟点房晚数
        }

        //查询去年
        Map<String, Object> map3 = new HashMap<String, Object>();
        map3.put("startTime", s3);
        //去年最后一天
        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(Calendar.YEAR, -1);//拨回去年
        calendar2.set(Calendar.DAY_OF_YEAR,calendar2.getActualMaximum(Calendar.DAY_OF_YEAR));//最后一天
        String last1 = format.format(calendar2.getTime())+" 05:00:00";
        Date last2 = dd.parse(last1);
        map3.put("endTime", last2);
        map3.put("hotelId", hotelId);

        ManagementReportBO managementReportLastYear = managementReportDAO.selectManagementReport(map3);
        if (managementReportLastYear!=null){
            receivableSum.setLastYear(managementReportLastYear.getReceivableSum().toString());//应收合计
            //房晚数
            BigDecimal roomLateSumLastYear = new BigDecimal(managementReportLastYear.getRoomLateSum());

            //应收合计
            BigDecimal receivableSumLastYear=managementReportLastYear.getReceivableSum();
            //会员卡收入
            BigDecimal memberCardSoldMoneyLastYear=managementReportLastYear.getMemberCardSoldMoney();
            //差
            BigDecimal q = receivableSumLastYear.subtract(memberCardSoldMoneyLastYear);
            if (roomLateSumLastYear.intValue()==0){
                avgRoomRate.setLastYear("0.00");
                avgConsumptionOfRoom.setLastYear("0.00");
            }else {
                //平均房价=房租收入/房晚数
                avgRoomRate.setLastYear(managementReportLastYear.getRentalIncome().divide(roomLateSumLastYear,2,BigDecimal.ROUND_HALF_UP).toString());//平均房价
                //房平均消费-房间所有总消费（应收合计-会员卡收入）/房晚数
                avgConsumptionOfRoom.setLastYear(q.divide(roomLateSumLastYear,2,BigDecimal.ROUND_HALF_UP).toString());//房平均消费
            }

            //人晚数
            Integer personLateSumLastYear=managementReportLastYear.getPersonLateSum();
            if (personLateSumLastYear==0){
                avgConsumptionOfPerson.setLastYear("0.00");
            }else {
                //人均消费--应收合计/人晚数
                avgConsumptionOfPerson.setLastYear(receivableSumLastYear.divide(new BigDecimal(personLateSumLastYear), 2, BigDecimal.ROUND_HALF_UP).toString());//人均消费
            }
            indemnityIncome.setLastYear(managementReportLastYear.getIndemnityIncome().toString());//赔偿收入
            freeCheckInSum.setLastYear(managementReportLastYear.getFreeCheckInSum().toString());//免费入住房数
            roomSum.setLastYear(managementReportLastYear.getRoomSum().toString());//房间总数
            maintainRoomSum.setLastYear(managementReportLastYear.getMaintainRoomSum().toString());//维修房数
            memberRoomSum.setLastYear(managementReportLastYear.getMemberRoomSum().toString());//会员房数
            memberCardSoldMoney.setLastYear(managementReportLastYear.getMemberCardSoldMoney().toString());//会员卡销售金额
            roomLateSum.setLastYear(managementReportLastYear.getRoomLateSum().toString());//房晚数
            personLateSum.setLastYear(managementReportLastYear.getPersonLateSum().toString());//人晚数
            commodityRevenues.setLastYear(managementReportLastYear.getCommodityRevenues().toString());//商品收入
            roomRateAdjustment.setLastYear(managementReportLastYear.getRoomRateAdjustment().toString());//房费调整
            //房间总数
            BigDecimal roomSumLastLastYaer = new BigDecimal(managementReportLastYear.getRoomSum());
            //维修房数
            BigDecimal maintainRoomSumLastYear = new BigDecimal(managementReportLastYear.getMaintainRoomSum());
            //差
            BigDecimal i=roomSumLastLastYaer.subtract(maintainRoomSumLastYear);
            if (i.intValue()==0){
                occupancyRate.setLastYear("0");
                REVPAR.setLastYear("0.00");
            }else {
                //出租率=房晚数 / (总房间数 - 维修房数)
                occupancyRate.setLastYear(roomLateSumLastYear.divide(i, 4, BigDecimal.ROUND_HALF_UP).toString());//出租率
                //REVPAR = 应收合计 / (总房间数 - 维修房数)
                REVPAR.setLastYear(receivableSumLastYear.divide(i, 2, BigDecimal.ROUND_HALF_UP).toString());//REVPAR = 应收合计 / (总房间数 - 维修房数)
            }
            disableRoomSum.setLastYear(managementReportLastYear.getDisableRoomSum().toString());//停用房间数
            rentalIncome.setLastYear(managementReportLastYear.getRentalIncome().toString());//房租收入
            emptyRoomSum.setLastYear(managementReportLastYear.getEmptyRoomSum().toString());//空房数
            hourRoomLateSum.setLastYear(managementReportLastYear.getHourRoomLateSum().toString());//钟点房晚数
        }


        //查询去年当月
        //获取前月的第一天
        Calendar cal_2 = Calendar.getInstance();
        //获取当前日期
        cal_2.setTime(startTime);
        cal_2.add(Calendar.MONTH, 0);
        cal_2.add(Calendar.YEAR, -1);
        cal_2.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        String firstDayy = format.format(cal_2.getTime());
        String str3 = firstDayy + " 03:00:00";
        Date startTimelast = dd.parse(str3);

        cal_2.set(Calendar.DAY_OF_MONTH,cal_2.getActualMaximum(Calendar.DAY_OF_MONTH));
        String mouthlast = format.format(cal_2.getTime());
        String str4 = mouthlast+" 04:00:00";
        Date mouthlastdate = dd.parse(str4);

        Calendar ct1 = Calendar.getInstance();
        ct1.setTime(format.parse(firstDayy));
        ct1.add(Calendar.MONTH, +1);
        String firstDayyy = format.format(ct1.getTime());
        String endTimey = firstDayyy + " 04:00:00";
        Date endTimeTimelast = dd.parse(endTimey);

        Map<String, Object> map4 = new HashMap<String, Object>();
        map4.put("startTime", startTimelast);
        map4.put("endTime", mouthlastdate);
        map4.put("hotelId", hotelId);

        ManagementReportBO managementReportLastYearMonth = managementReportDAO.selectManagementReport(map4);

        if (managementReportLastYearMonth!=null){
            receivableSum.setLastYearMonth(managementReportLastYearMonth.getReceivableSum().toString());//应收合计
            //房晚数
            BigDecimal roomLateSumLastYearMonth = new BigDecimal(managementReportLastYearMonth.getRoomLateSum());

            //应收合计
            BigDecimal receivableSumLastYearMonth=managementReportLastYearMonth.getReceivableSum();
            //会员卡收入
            BigDecimal memberCardSoldMoneyLastYearMonth=managementReportLastYearMonth.getMemberCardSoldMoney();
            //差
            BigDecimal q = receivableSumLastYearMonth.subtract(memberCardSoldMoneyLastYearMonth);
            if (roomLateSumLastYearMonth.intValue()==0){
                avgRoomRate.setLastYearMonth("0.00");
                avgConsumptionOfRoom.setLastYearMonth("0.00");
            }else {
                //平均房价=房租收入/房晚数
                avgRoomRate.setLastYearMonth(managementReportLastYearMonth.getRentalIncome().divide(roomLateSumLastYearMonth,2,BigDecimal.ROUND_HALF_UP).toString());//平均房价
                //房平均消费-房间所有总消费（应收合计-会员卡收入）/房晚数
                avgConsumptionOfRoom.setLastYearMonth(q.divide(roomLateSumLastYearMonth,2,BigDecimal.ROUND_HALF_UP).toString());//房平均消费
            }
            //人晚数
            Integer personLateSumLastYearMonth=managementReportLastYearMonth.getPersonLateSum();
            if (personLateSumLastYearMonth==0){
                avgConsumptionOfPerson.setLastYearMonth("0.00");
            }else {
                //人均消费--应收合计/人晚数
                avgConsumptionOfPerson.setLastYearMonth(receivableSumLastYearMonth.divide(new BigDecimal(personLateSumLastYearMonth), 2, BigDecimal.ROUND_HALF_UP).toString());//人均消费
            }
            indemnityIncome.setLastYearMonth(managementReportLastYearMonth.getIndemnityIncome().toString());//赔偿收入
            freeCheckInSum.setLastYearMonth(managementReportLastYearMonth.getFreeCheckInSum().toString());//免费入住房数
            roomSum.setLastYearMonth(managementReportLastYearMonth.getRoomSum().toString());//房间总数
            maintainRoomSum.setLastYearMonth(managementReportLastYearMonth.getMaintainRoomSum().toString());//维修房数
            memberRoomSum.setLastYearMonth(managementReportLastYearMonth.getMemberRoomSum().toString());//会员房数
            memberCardSoldMoney.setLastYearMonth(managementReportLastYearMonth.getMemberCardSoldMoney().toString());//会员卡销售金额
            roomLateSum.setLastYearMonth(managementReportLastYearMonth.getRoomLateSum().toString());//房晚数
            personLateSum.setLastYearMonth(managementReportLastYearMonth.getPersonLateSum().toString());//人晚数
            commodityRevenues.setLastYearMonth(managementReportLastYearMonth.getCommodityRevenues().toString());//商品收入
            roomRateAdjustment.setLastYearMonth(managementReportLastYearMonth.getRoomRateAdjustment().toString());//房费调整
            //房间总数
            BigDecimal roomSumLastLastYaerMonth = new BigDecimal(managementReportLastYearMonth.getRoomSum());
            //维修房数
            BigDecimal maintainRoomSumLastYearMonth = new BigDecimal(managementReportLastYearMonth.getMaintainRoomSum());
            //差
            BigDecimal i=roomSumLastLastYaerMonth.subtract(maintainRoomSumLastYearMonth);
            if (i.intValue()==0){
                occupancyRate.setLastYearMonth("0");
                REVPAR.setLastYearMonth("0.00");
            }else {
                //出租率=房晚数 / (总房间数 - 维修房数)
                occupancyRate.setLastYearMonth(roomLateSumLastYearMonth.divide(i, 4, BigDecimal.ROUND_HALF_UP).toString());//出租率
                //REVPAR = 应收合计 / (总房间数 - 维修房数)
                REVPAR.setLastYearMonth(receivableSumLastYearMonth.divide(i, 2, BigDecimal.ROUND_HALF_UP).toString());//REVPAR = 应收合计 / (总房间数 - 维修房数)
            }
            disableRoomSum.setLastYearMonth(managementReportLastYearMonth.getDisableRoomSum().toString());//停用房间数
            rentalIncome.setLastYearMonth(managementReportLastYearMonth.getRentalIncome().toString());//房租收入
            emptyRoomSum.setLastYearMonth(managementReportLastYearMonth.getEmptyRoomSum().toString());//空房数
            hourRoomLateSum.setLastYearMonth(managementReportLastYearMonth.getHourRoomLateSum().toString());//钟点房晚数
        }

        //查询去年当天
        //获取前月的第一天
        Calendar cal_3 = Calendar.getInstance();
        //获取当前日期
        cal_3.setTime(startTime);
        cal_3.add(Calendar.YEAR, -1);
        String firstDayyyy = format.format(cal_3.getTime());
        String str5 = firstDayyyy + " 03:00:00";
        Date startTimelast1 = dd.parse(str5);
        System.out.print(startTimelast1);

        Calendar aaaa = Calendar.getInstance();
        aaaa.setTime(startTimelast1);
        //aaaa.add(Calendar.DAY_OF_YEAR, 1);
        String firstDayyyyy = format.format(aaaa.getTime());
        String str6 = firstDayyyyy + " 04:00:00";
        Date endTimeyy = dd.parse(str6);
        System.out.print(endTimeyy);
        Map<String, Object> map5 = new HashMap<String, Object>();
        map5.put("startTime", startTimelast1);
        map5.put("endTime", endTimeyy);
        map5.put("hotelId", hotelId);

        ManagementReportBO managementReportLastYearDay = managementReportDAO.selectManagementReport(map5);
        if (managementReportLastYearDay!=null){
            receivableSum.setLastYearMonth(managementReportLastYearDay.getReceivableSum().toString());//应收合计
            avgRoomRate.setLastYearMonth(managementReportLastYearDay.getAvgRoomRate().toString());//平均房价
            avgConsumptionOfRoom.setLastYearMonth(managementReportLastYearDay.getAvgConsumptionOfRoom().toString());//房平均消费
            avgConsumptionOfPerson.setLastYearMonth(managementReportLastYearDay.getAvgConsumptionOfPerson().toString());//人均消费
            indemnityIncome.setLastYearMonth(managementReportLastYearDay.getIndemnityIncome().toString());//赔偿收入
            freeCheckInSum.setLastYearMonth(managementReportLastYearDay.getFreeCheckInSum().toString());//免费入住房数
            roomSum.setLastYearMonth(managementReportLastYearDay.getRoomSum().toString());//房间总数
            maintainRoomSum.setLastYearMonth(managementReportLastYearDay.getMaintainRoomSum().toString());//维修房数
            memberRoomSum.setLastYearMonth(managementReportLastYearDay.getMemberRoomSum().toString());//会员房数
            memberCardSoldMoney.setLastYearMonth(managementReportLastYearDay.getMemberCardSoldMoney().toString());//会员卡销售金额
            roomLateSum.setLastYearMonth(managementReportLastYearDay.getRoomLateSum().toString());//房晚数
            personLateSum.setLastYearMonth(managementReportLastYearDay.getPersonLateSum().toString());//人晚数
            commodityRevenues.setLastYearMonth(managementReportLastYearDay.getCommodityRevenues().toString());//商品收入
            roomRateAdjustment.setLastYearMonth(managementReportLastYearDay.getRoomRateAdjustment().toString());//房费调整
            occupancyRate.setLastYearMonth(managementReportLastYearDay.getOccupancyRate());//出租率
            REVPAR.setLastYearMonth(managementReportLastYearDay.getREVPAR());//REVPAR = 应收合计 / (总房间数 - 维修房数)
            disableRoomSum.setLastYearMonth(managementReportLastYearDay.getDisableRoomSum().toString());//停用房间数
            rentalIncome.setLastYearMonth(managementReportLastYearDay.getRentalIncome().toString());//房租收入
            emptyRoomSum.setLastYearMonth(managementReportLastYearDay.getEmptyRoomSum().toString());//空房数
            hourRoomLateSum.setLastYearMonth(managementReportLastYearDay.getHourRoomLateSum().toString());//钟点房晚数
        }

        managementReportResponseBO.setReceivableSum(receivableSum);
        managementReportResponseBO.setAvgRoomRate(avgRoomRate); //平均房价
        managementReportResponseBO.setAvgConsumptionOfRoom(avgConsumptionOfRoom); //房平均消费
        managementReportResponseBO.setAvgConsumptionOfPerson(avgConsumptionOfPerson); //人均消费
        managementReportResponseBO.setIndemnityIncome(indemnityIncome); //赔偿收入
        managementReportResponseBO.setFreeCheckInSum(freeCheckInSum); //免费入住房数
        managementReportResponseBO.setRoomSum(roomSum); //房间总数
        managementReportResponseBO.setMaintainRoomSum(maintainRoomSum); //维修房数
        managementReportResponseBO.setMemberRoomSum(memberRoomSum); //会员房数
        managementReportResponseBO.setMemberCardSoldMoney(memberCardSoldMoney);//会员卡销售金额
        managementReportResponseBO.setRoomLateSum(roomLateSum); //房晚数
        managementReportResponseBO.setPersonLateSum(personLateSum); //人晚数
        managementReportResponseBO.setCommodityRevenues(commodityRevenues); //商品收入
        managementReportResponseBO.setRoomRateAdjustment(roomRateAdjustment);//房费调整
        managementReportResponseBO.setOccupancyRate(occupancyRate);//出租率
        managementReportResponseBO.setREVPAR(REVPAR);//REVPAR = 应收合计 / (总房间数 - 维修房数)
        managementReportResponseBO.setDisableRoomSum(disableRoomSum);//停用房间数
        managementReportResponseBO.setRentalIncome(rentalIncome); //房租收入
        managementReportResponseBO.setEmptyRoomSum(emptyRoomSum); //空房数
        managementReportResponseBO.setHourRoomLateSum(hourRoomLateSum); //钟点房晚数
        log.info("end selectManagementReport============ManagementReportService=================");
        return managementReportResponseBO;

    }


    //查询应收合计（管理层报表）
    public BigDecimal getReceivableSum(Map<String,Object> map){
        BigDecimal receivableSum = managementReportDAO.getReceivableSum(map);
        if (receivableSum == null){
            return new BigDecimal(0.00);
        }
        return receivableSum;
    }
    //平均房价=房费收入/房晚数
    public BigDecimal avgRoomRate(Map<String,Object> map){
        //房费收入
        BigDecimal roomRate = this.getRoomRate(map);
        //房晚数
        Integer roomLateSum = this.getRoomLateSum(map);
        if (roomLateSum==0||roomRate==null||roomRate.intValue()==0){
            return new BigDecimal(0.00);
        }
        //平均房价
        BigDecimal avgRoomRate = roomRate.divide(new BigDecimal(roomLateSum),2,BigDecimal.ROUND_HALF_UP);
        return  avgRoomRate;
    }
    //房费收入
    public BigDecimal getRoomRate(Map<String,Object> map){
        BigDecimal roomRate = managementReportDAO.getRoomRate(map);
        if (roomRate==null){
            roomRate = new BigDecimal(0.00);
        }
        //房费冲减
        BigDecimal roomRateOffset = managementReportDAO.getRoomRateOffset(map);
        if (roomRateOffset==null){
            roomRateOffset = new BigDecimal(0.00);
        }

        BigDecimal roomRate1 = roomRate.add(roomRateOffset);

        return roomRate1;
    }
    //房间总数
    public Integer getRoomSum(Map<String,Object> map){
        return managementReportDAO.getRoomSum(map);
    }
    //房平均消费=房间所有总消费（所有收入-会员卡收入）/房晚数
    public BigDecimal getAvgConsumptionOfRoom(Map<String,Object> map){
        //所有收入
        BigDecimal receivableSum = this.getReceivableSum(map);
        if (receivableSum==null){
            receivableSum = new BigDecimal(0);
        }
        //会员卡收入
        BigDecimal memberCardRate = this.getMemberCardRate(map);
        if (memberCardRate==null){
            memberCardRate = new BigDecimal(0);
        }
        //房晚数
        Integer roomLateSum = this.getRoomLateSum(map);

        if (roomLateSum == 0||receivableSum.intValue()==0||receivableSum.subtract(memberCardRate).intValue()==0){
            return new BigDecimal(0.00);
        }
        //房平均消费
        BigDecimal avgConsumptionOfRoom = (receivableSum.subtract(memberCardRate)).divide(new BigDecimal(roomLateSum),2,BigDecimal.ROUND_HALF_UP);

        return avgConsumptionOfRoom;
    }
    //会员卡收入
    public BigDecimal getMemberCardRate(Map<String,Object> map){
        BigDecimal memberCardRate = managementReportDAO.getMemberCardRate(map);
        if (memberCardRate==null){
            return new BigDecimal(0.00);
        }
        return memberCardRate;
    }
    //房晚数
    public Integer getRoomLateSum(Map<String,Object> map){
        Integer roomLateSum = managementReportDAO.getRoomLateSum(map);
        if (roomLateSum==null){
            return 0;
        }
        return roomLateSum;
    }
    //人晚数
    public  Integer getPersonLateSum(Map<String,Object> map){
        Integer personLateSum = managementReportDAO.getPersonLateSum(map);
        if (personLateSum==null){
            return 0;
        }
        return personLateSum;
    }
    //出租率-------房晚数 / (总房间数 - 维修房数)
    public BigDecimal getOccupancyRate(Map<String,Object> map){
        //房晚数
        BigDecimal roomLateSum = new BigDecimal(this.getRoomLateSum(map));
        //总房间数
        BigDecimal roomSum = new BigDecimal(this.getRoomSum(map));
        //维修房数
        BigDecimal maintainSum = new BigDecimal(this.getMaintainSum(map));
        if (roomSum.subtract(maintainSum).intValue()==0){
            return new BigDecimal(0);
        }
        BigDecimal occupancyRate = roomLateSum.divide(roomSum.subtract(maintainSum),4,BigDecimal.ROUND_HALF_UP);//TODO
        return occupancyRate;
    }

    //人均消费 = 应收合计/人晚数
    public BigDecimal getAvgConsumptionOfPerson(Map<String,Object> map){
        //应收合计
        BigDecimal receivableSum = this.getReceivableSum(map);
        if (receivableSum==null){
            receivableSum = new BigDecimal(0);
        }
        //人晚数
        Integer personLateSum = this.getPersonLateSum(map);
        if (personLateSum ==0){
            return new BigDecimal(0.00);
        }else {
            //人均消费
            BigDecimal avgConsumptionOfPerson = receivableSum.divide(new BigDecimal(personLateSum), 2, BigDecimal.ROUND_HALF_UP);
            return avgConsumptionOfPerson;
        }
    }
    //入住人数
    public Integer getCheckInPerson(Map<String,Object> map){
        Integer checkInPerson = managementReportDAO.getCheckInPerson(map);
        if (checkInPerson==null){
            return 0;
        }
        return checkInPerson;
    }
    //赔偿收入-赔偿冲减
    public BigDecimal getCompensation(Map<String,Object> map){
        BigDecimal compensation = managementReportDAO.getCompensation(map);
        if (compensation==null){
            compensation=new BigDecimal(0.00);
        }
        //赔偿冲减
        BigDecimal compensationOffset=managementReportDAO.getCompensationOffset(map);
        if (compensationOffset==null){
            compensationOffset=new BigDecimal(0.00);
        }
        BigDecimal compensation1 = compensation.add(compensationOffset);
        return compensation1;
    }
    //免费入住房数
    public Integer getFreeRoomSum(Map<String,Object> map){
        Integer freeRoomSum = managementReportDAO.getFreeRoomSum(map);
        if (freeRoomSum==null){
            return 0;
        }
        return freeRoomSum;
    }
    //维修房数
    public Integer getMaintainSum(Map<String,Object> map){
        Integer maintainSum =  managementReportDAO.getMaintainSum(map);
        if (maintainSum==null){
            return 0;
        }
        return maintainSum;
    }
    //会员房数
    public Integer getMemberRoomSum(Map<String,Object> map){
        Integer memberRoomSum = managementReportDAO.getMemberRoomSum(map);
        if (memberRoomSum==null){
            return 0;
        }
        return memberRoomSum;
    }
    //商品收入=商品收入-商品冲减
    public BigDecimal getCommodity(Map<String,Object> map){
        BigDecimal commidity = managementReportDAO.getCommodity(map);
        if (commidity==null){
            commidity = new BigDecimal(0.00);
        }
        //商品冲减
        BigDecimal commidityOffset = managementReportDAO.getCommodityOffset(map);
        if (commidityOffset==null){
            commidityOffset = new BigDecimal(0.00);
        }
        BigDecimal commidity1 = commidity.add(commidityOffset);
        return commidity1;
    }
    //房费调整
    public BigDecimal getRoomRateAdjustment(Map<String,Object> map){
        BigDecimal roomRateAdjustment = managementReportDAO.getRoomRateAdjustment(map);
        if (roomRateAdjustment==null){
            return new BigDecimal(0.00);
        }
        return roomRateAdjustment;
    }
    //出租率 房晚数 / (总房间数 - 维修房数)
    //REVPAR REVPAR = 应收合计 / (总房间数 - 维修房数)
    public BigDecimal REVPAR(Map<String,Object> map){
        //应收合计
        BigDecimal receivableSum = managementReportDAO.getReceivableSum(map);
        //总房间数
        Integer roomSum = managementReportDAO.getRoomSum(map);
        //维修房数
        Integer maintainSum = managementReportDAO.getMaintainSum(map);
        Integer i = roomSum - maintainSum ;
        if (i==0||receivableSum ==null){
            return new BigDecimal(0.00);
        }
        BigDecimal REVPAR = receivableSum.divide(new BigDecimal(i),2,BigDecimal.ROUND_HALF_UP);
        return REVPAR;
    }
    //锁房数
    public Integer getLockRoomSum(Map<String,Object> map){
        return managementReportDAO.getLockRoomSum(map);
    }
    //停用房间数 =  维修房数+锁房数
    public Integer getDisableRoomSum(Map<String,Object> map){
//        //维修房数
//        Integer maintainSum = managementReportDAO.getMaintainSum(map);
//        //锁房数
//        Integer lockRoomSum = managementReportDAO.getLockRoomSum(map);
//        //停用房间数
//        Integer disableRoomSum = maintainSum + lockRoomSum;
        Integer disableRoomSum= managementReportDAO.getLockRoomSum(map);
        if (disableRoomSum==null){
            return 0;
        }
        return disableRoomSum;
    }
    //空房数
    public Integer getEmptyRoomSum(Map<String,Object> map){
        Integer emptyRoomSum= managementReportDAO.getEmptyRoomSum(map);
        if (emptyRoomSum==null){
            return 0;
        }
        return emptyRoomSum;
    }

    //钟点房晚数
    public Integer getHourRoomSum(Map<String,Object> map){
        Integer hourRoomSum=managementReportDAO.getHourRoomSum(map);
        if (hourRoomSum==null){
            return 0;
        }
        return hourRoomSum;
    }

}
