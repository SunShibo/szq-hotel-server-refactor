package com.szq.hotel.job;

import com.szq.hotel.common.constants.Constants;
import com.szq.hotel.entity.bo.CommonBO;
import com.szq.hotel.entity.bo.EverydayRoomPriceBO;
import com.szq.hotel.entity.bo.RoomRateBO;
import com.szq.hotel.service.*;
import com.szq.hotel.util.DateUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Component("CheckOrderState")
public class CheckOrderState {

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



    int count = 0;
    @Scheduled(cron = "0 0/5 * * * *")    // 13.15 启动项目
    public void CheckOrderState() {
        count++;
        try {
            //关闭未支付的
            orderService.closeOrder();
            //解除锁房
            roomService.updRoom();
            //入住超时修改房态
            orderService.updTimeOutOrder();

            System.err.println("执行" + count + "次当前时间:" + new Date());
        } catch (Exception e) {
            System.out.println("修改失败");
        }
    }

    /**
     * 每天4点滚房费
     */
    @Scheduled(cron = "0 0 4 * * *")    // 13.15 启动项目
    public void nightAuditor() {

            List<RoomRateBO> roomRateBOS = childOrderService.queryOrderChild();
            for(int i=0;i<roomRateBOS.size();i++){
                RoomRateBO roomRateBO = roomRateBOS.get(i);
                List<EverydayRoomPriceBO> everydayRoomPriceBOS = childOrderService.queryRoomPrice(roomRateBO.getId(), DateUtils.getFourPointsStr());
                if(everydayRoomPriceBOS!=null && everydayRoomPriceBOS.size()>0){
                    for(EverydayRoomPriceBO priceBO:everydayRoomPriceBOS){
                        //生成房费
                        childOrderService.increaseRoomRate(priceBO.getOrderChildId(),priceBO.getMoney());
                        orderRecordService.addOrderRecord(priceBO.getOrderChildId(), DateUtils.format(priceBO.getTime())+"房费",
                                null,priceBO.getMoney(), Constants.ROOMRATE.getValue(),1,"1天", Constants.NO.getValue());
                        //把夜审状态修改回去
                        childOrderService.updateRoomPrice(priceBO.getId());
                        //生成报表
                        CommonBO commonBO = childOrderService.queryChildName(priceBO.getOrderChildId());
                        cashierSummaryService.addRoomRate(priceBO.getMoney(),roomRateBO.getOrderNumber(),1,commonBO.getName(),
                                roomRateBO.getOTA(),roomRateBO.getChannel(),roomRateBO.getPassengerSource(),roomRateBO.getRoomName(),
                                roomRateBO.getRoomTypeName(),roomRateBO.getHotelId());
                    }
            }
        }
    }


}