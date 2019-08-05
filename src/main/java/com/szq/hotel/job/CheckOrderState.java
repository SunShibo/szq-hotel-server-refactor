package com.szq.hotel.job;

import com.szq.hotel.common.constants.Constants;
import com.szq.hotel.dao.HotelDAO;
import com.szq.hotel.entity.bo.CommonBO;
import com.szq.hotel.entity.bo.EverydayRoomPriceBO;
import com.szq.hotel.entity.bo.HotelBO;
import com.szq.hotel.entity.bo.RoomRateBO;
import com.szq.hotel.service.*;
import com.szq.hotel.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Component("CheckOrderState")
public class CheckOrderState {
    private static final Logger log = LoggerFactory.getLogger(CheckOrderState.class);
    @Resource
    OrderService orderService;
    @Resource
    RoomService roomService;
    @Resource
    ChildOrderService childOrderService;
    @Resource
    OrderRecordService orderRecordService;
    @Resource
    CashierSummaryService cashierSummaryService;
    @Resource
    IncomeService incomeService;
    @Resource
    HotelDAO hotelDAO;
    @Resource
    ManagementReportService managementReportService;


    int count = 0;

    @Scheduled(cron = "0 0/5 * * * *")    // 13.15 启动项目
    public void CheckOrderState() {
        count++;
        try {
            //关闭未支付的
            orderService.closeOrder(1);
            //解除锁房
            roomService.updRoom();
            //入住超时修改房态
            orderService.updTimeOutOrder(1);

            System.err.println("执行" + count + "次当前时间:" + new Date());
        } catch (Exception e) {
            System.out.println("修改失败");
        }
    }

    /**
     * 每天4点滚房费
     */
    @Scheduled(cron = "0 0 4 * * ?")    // 13.15 启动项目
    public void nightAuditor() {
            log.info("start  nightAuditor +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            List<RoomRateBO> roomRateBOS = childOrderService.queryOrderChild();
            log.info("roomRateBOS:{}",roomRateBOS);
            for(int i=0;i<roomRateBOS.size();i++){
                RoomRateBO roomRateBO = roomRateBOS.get(i);
                log.info("roomRateBO:{} , i:{}",roomRateBO,i);
                List<EverydayRoomPriceBO> everydayRoomPriceBOS = childOrderService.queryRoomPrice(roomRateBO.getId(), DateUtils.getNow());
                log.info("everydayRoomPriceBOS:{}",everydayRoomPriceBOS);
                if(everydayRoomPriceBOS!=null && everydayRoomPriceBOS.size()>0){
                    for(EverydayRoomPriceBO priceBO:everydayRoomPriceBOS){
                        log.info("start  room price  ...............................................");
                        //生成房费     //生成在主账房里
                        Integer integer = childOrderService.queryOrderChildMain(roomRateBO.getAlRoomCode());
                        childOrderService.increaseRoomRate(integer,priceBO.getMoney());
                        orderRecordService.addOrderRecord(priceBO.getOrderChildId(), DateUtils.format(priceBO.getTime())+"房费",
                                null,priceBO.getMoney().multiply(new BigDecimal("-1")), Constants.ROOMRATE.getValue(),1,"1天", Constants.NO.getValue());
                        //把夜审状态修改回去
                        log.info("update  room price  status................................................");
                        childOrderService.updateRoomPrice(priceBO.getId());
                        //生成报表
                        log.info("start  make a repor.....................................................");
                        CommonBO commonBO = childOrderService.queryChildName(priceBO.getOrderChildId());
                        cashierSummaryService.addRoomRate(priceBO.getMoney(),roomRateBO.getOrderNumber(),1,commonBO.getName(),
                                roomRateBO.getOTA(),roomRateBO.getChannel(),roomRateBO.getPassengerSource(),roomRateBO.getRoomName(),
                                roomRateBO.getRoomTypeName(),roomRateBO.getHotelId());
                    }
            }
        }
        List<HotelBO> hotelBOS = hotelDAO.queryHotel();
        for (HotelBO hotelBO:hotelBOS) {
            incomeService.addIncome(hotelBO.getId());
            //管理层报表
            managementReportService.addData(hotelBO.getId());
        }
        log.info("end  nightAuditor +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }


}