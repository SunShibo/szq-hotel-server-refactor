package com.szq.hotel.web.controller;
import com.szq.hotel.common.constants.Constants;
import com.szq.hotel.dao.CheckInPersonDAO;
import com.szq.hotel.entity.bo.CheckInPersonBO;
import com.szq.hotel.entity.bo.CommonBO;
import com.szq.hotel.entity.bo.EverydayRoomPriceBO;
import com.szq.hotel.entity.bo.RoomRateBO;
import com.szq.hotel.service.*;
import com.szq.hotel.util.DateUtils;
import com.szq.hotel.web.controller.base.BaseCotroller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author Shibo Sun
 *         主机controller
 */
@Controller
@RequestMapping("/test")
public class TestController extends BaseCotroller {

    @Resource
    private ChildOrderService childOrderService;
    @Resource
    private OrderRecordService  orderRecordService;
    @Resource
    private CashierSummaryService cashierSummaryService;
    @Resource
    TestService testService;
    @Resource
    CheckInPersonService checkInPersonService;

    @RequestMapping("/start")
    public void queryVersion(){
        List<RoomRateBO> roomRateBOS = childOrderService.queryOrderChild();
        for(int i=0;i<roomRateBOS.size();i++){
            RoomRateBO roomRateBO = roomRateBOS.get(i);
            List<EverydayRoomPriceBO> everydayRoomPriceBOS = childOrderService.queryRoomPrice(roomRateBO.getId(),DateUtils.getFourPointsStr());
            if(everydayRoomPriceBOS!=null && everydayRoomPriceBOS.size()>0){
                for(EverydayRoomPriceBO priceBO:everydayRoomPriceBOS){
                    //生成房费
                    childOrderService.increaseRoomRate(priceBO.getOrderChildId(),priceBO.getMoney());
                    orderRecordService.addOrderRecord(priceBO.getOrderChildId(), DateUtils.format(priceBO.getTime())+"房费",
                            null,priceBO.getMoney(),Constants.ROOMRATE.getValue(),1,"1天", Constants.NO.getValue());
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

    @RequestMapping("/test")
    public void test(){
        testService.test1();
    }
}




