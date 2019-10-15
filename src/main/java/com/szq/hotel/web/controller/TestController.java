package com.szq.hotel.web.controller;
import com.szq.hotel.common.constants.Constants;
import com.szq.hotel.dao.HotelDAO;
import com.szq.hotel.entity.bo.*;
import com.szq.hotel.entity.dto.ResultDTOBuilder;
import com.szq.hotel.job.CheckOrderState;
import com.szq.hotel.service.*;
import com.szq.hotel.util.DateUtils;
import com.szq.hotel.util.JsonUtils;
import com.szq.hotel.util.redisUtils.RedisLock;
import com.szq.hotel.web.controller.base.BaseCotroller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;


/**
 * @author Shibo Sun
 *         主机controller
 */
@Controller
@RequestMapping("/test")
public class TestController extends BaseCotroller {
    private static final Logger log = LoggerFactory.getLogger(CheckOrderState.class);
    @Resource
    private ChildOrderService childOrderService;
    @Resource
    private OrderRecordService orderRecordService;
    @Resource
    private CashierSummaryService cashierSummaryService;
    @Resource
    ManagerDailyService managerDailyService;
    @Resource
    HotelDAO hotelDAO;
    @Resource
    ManagementReportService managementReportService;
    @Resource
    private NightAuditService nightAuditService;
    @Resource
    IncomeService incomeService;


    @RequestMapping("/start")
    public void nightAuditor2() {
        log.info("start  nightAuditor +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        List<RoomRateBO> roomRateBOS = childOrderService.queryOrderChild();
        log.info("roomRateBOS:{}", roomRateBOS);
        for (int i = 0; i < roomRateBOS.size(); i++) {
            RoomRateBO roomRateBO = roomRateBOS.get(i);
            log.info("roomRateBO:{} , i:{}", roomRateBO, i);
            List<EverydayRoomPriceBO> everydayRoomPriceBOS = childOrderService.queryRoomPrice(roomRateBO.getId(), DateUtils.getNow());
            log.info("everydayRoomPriceBOS:{}", everydayRoomPriceBOS);
            if (everydayRoomPriceBOS != null && everydayRoomPriceBOS.size() > 0) {
                for (EverydayRoomPriceBO priceBO : everydayRoomPriceBOS) {
                    log.info("start  room price  ...............................................");
                    //生成房费     //生成在主账房里
                    Integer integer = childOrderService.queryOrderChildMain(roomRateBO.getAlRoomCode());
                    childOrderService.increaseRoomRate(integer, priceBO.getMoney());
                    orderRecordService.addOrderRecord(priceBO.getOrderChildId(), DateUtils.format(priceBO.getTime()) + "房费",
                            null, priceBO.getMoney().multiply(new BigDecimal("-1")), Constants.ROOMRATE.getValue(), 1, "1天", Constants.NO.getValue());
                    //把夜审状态修改回去
                    log.info("update  room price  status................................................");
                    childOrderService.updateRoomPrice(priceBO.getId());
                    //生成报表
                    log.info("start  make a repor.....................................................");
                    CommonBO commonBO = childOrderService.queryChildName(priceBO.getOrderChildId());
                    cashierSummaryService.addRoomRate(priceBO.getMoney(), roomRateBO.getOrderNumber(), 1, commonBO.getName(),
                            roomRateBO.getOTA(), roomRateBO.getPassengerSource(), roomRateBO.getChannel(), roomRateBO.getRoomName(),
                            roomRateBO.getRoomTypeName(), roomRateBO.getHotelId());
                    //记录夜核房晚数
                    Integer person = childOrderService.queryPersonNumber(priceBO.getOrderChildId());
                    nightAuditService.addAudit(priceBO.getOrderChildId(), roomRateBO.getHotelId(), person, roomRateBO.getChannel());
                }
            }
        }
        List<HotelBO> hotelBOS = hotelDAO.queryHotel();
        for (HotelBO hotelBO : hotelBOS) {
            incomeService.addIncome(hotelBO.getId());
            //管理层报表
            managementReportService.addData(hotelBO.getId());

            managerDailyService.insertManagerDaliy(hotelBO.getId());
        }
        log.info("end  nightAuditor +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");


}
private int i=0;
@RequestMapping("/Locktest")
    public void test(HttpServletResponse  response) throws InterruptedException {
    RedisLock lock = new RedisLock();
  //  lock.lock("one");
    log.info("start  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

    if(i==0){
        Thread.sleep(5000);
        i++;
    }
    log.info("end  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
   // lock.releaseLock("one");
    String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(i));
    super.safeJsonPrint(response, result);
}

}