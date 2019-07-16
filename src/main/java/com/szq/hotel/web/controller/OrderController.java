package com.szq.hotel.web.controller;

import com.szq.hotel.common.constants.Constants;
import com.szq.hotel.entity.bo.*;
import com.szq.hotel.entity.dto.ResultDTOBuilder;
import com.szq.hotel.entity.param.OrderParam;
import com.szq.hotel.entity.result.CheckInInfoResult;
import com.szq.hotel.entity.result.OrderResult;
import com.szq.hotel.query.QueryInfo;
import com.szq.hotel.service.CashierSummaryService;
import com.szq.hotel.service.CheckInPersonService;
import com.szq.hotel.service.OrderRecordService;
import com.szq.hotel.service.OrderService;
import com.szq.hotel.util.JsonUtils;
import com.szq.hotel.web.controller.base.BaseCotroller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

@Controller
@RequestMapping("/order")
public class OrderController extends BaseCotroller {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    @Resource
    OrderService orderService;

    @Resource
    CheckInPersonService checkInPersonService;

    @Resource
    CashierSummaryService cashierSummaryService;

    @Resource
    OrderRecordService orderRecordService;
    /**
     * 房间预定 预约入住 直接入住 修改
     * @param orderBO 预约信息
     * @param OrderChildJSON json格式的预定的房间信息
     * @param type 预约 或者 预约入住 或者 直接入住
     * */
    @RequestMapping("/reservationRoom")
    public void reservationRoom(HttpServletRequest request, HttpServletResponse response,
                                OrderBO orderBO,String OrderChildJSON,String type) {
        //验证管理员
        AdminBO userInfo = super.getLoginAdmin(request) ;
        if(userInfo == null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002" , "用户没有登录")) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        //验证参数
        if(orderBO== null||OrderChildJSON==null||type==null||type.length()==0){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001" )) ;
            super.safeJsonPrint(response, result);
            return ;
        }

        orderBO.setClerkOrderingId(userInfo.getId());
        orderBO.setHotelId(userInfo.getHotelId());
        List<OrderChildBO> list = JsonUtils.getJSONtoList(OrderChildJSON, OrderChildBO.class);

        Map<String,Object> resultMap=new HashMap<String, Object>();

        //检查入住信息是否正确 证件号是否有重复
        if(!type.equals("roomReservation")){
            String result=this.checkInPerson(list,orderBO.getId());
           if( result!=null){
               super.safeJsonPrint(response, result);
               return;
           }
        }

        if(type.equals("roomReservation")){
            //房间预约
            orderService.addOrder(orderBO);
            orderService.addOrderAllInfo(list,orderBO);
        }else if(type.equals("reservation")){
            //预约入住
            orderService.updOrderInfo(list,orderBO);
        }else if(type.equals("directly")){
            //直接入住
            orderService.addOrder(orderBO);
            orderService.addOrderAllInfo(list,orderBO);
            resultMap.put("orderNumber",orderBO.getOrderNumber());
        }

        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(resultMap)) ;
        super.safeJsonPrint(response, result);

    }


    //检查证件号是否重复
    //@param list 入住人
    //@param orderId 主订单id
    public String checkInPerson(List<OrderChildBO> list,Integer orderId) {

        //验证所有入住人中是否有重复入住的
        Set<String> idSet = new HashSet<String>();
        List<String> idList = new ArrayList<String>();

        for (OrderChildBO order : list) {
            List<CheckInPersonBO> personBOS = order.getCheckInPersonBOS();
            for (CheckInPersonBO personBO : personBOS) {
                idSet.add(personBO.getCertificateNumber());
                idList.add(personBO.getCertificateNumber());
                if (checkInPersonService.checkId(personBO.getCertificateNumber(), orderId) > 0) {
                    String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000094", personBO.getCertificateNumber() + "此证件信息为在住状态"));
                    return result;
                }
            }
        }

        if (idList.size() > idSet.size()) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000094", "入住证件信息重复"));
            return result;
        }
        return null;
    }

    /**
     * 预约入住 获取信息
     * 根据身份证号 手机号查询预约信息
     * @param idNumber 身份证号
     * @param mobile 手机号
     * */
    //如果预约两次后 生成两个订单会发生问题，查不到，应该去订单管理那查询
    //不过我觉得问题没办法避免，除非压根不能预约两次，或者都算一个顶订单
    @RequestMapping("/getReservationRoomInfo")
    public void getReservationRoomInfo(HttpServletRequest request, HttpServletResponse response,String idNumber,String mobile){
        //验证管理员
        AdminBO userInfo = super.getLoginAdmin(request) ;
        if(userInfo == null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002" , "用户没有登录")) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        //验证参数
        if((idNumber== null||idNumber.length()==0)&&(mobile==null||mobile.length()==0)){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001" , "参数异常")) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        //查询信息
        OrderBO orderBO = orderService.getOrderInfo(idNumber,mobile,userInfo.getHotelId());
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(orderBO)) ;
        super.safeJsonPrint(response, result);
    }

    /**
     * 通过订单id获取订单的信息
     * @param orderId 订单号
     * */
    @RequestMapping("/getOrderById")
    public void getOrderById(Integer orderId,HttpServletRequest request, HttpServletResponse response){
        //验证管理员
        AdminBO userInfo = super.getLoginAdmin(request) ;
        if(userInfo == null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002" , "用户没有登录")) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        //验证参数
        if(orderId== null||orderId.equals("")){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001" , "参数异常")) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        OrderBO orderBO=orderService.getOrderById(orderId);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(orderBO)) ;
        super.safeJsonPrint(response, result);
    }

    /**
     * 根据主订单id查询房间信息(客帐管理)
     * @param orderId 订单号
     * */
    @RequestMapping("/getRoomInfoById")
    public void getRoomInfoById(Integer orderId,HttpServletRequest request, HttpServletResponse response){
        //验证管理员
        AdminBO userInfo = super.getLoginAdmin(request) ;
        if(userInfo == null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002" , "用户没有登录")) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        //验证参数
        if(orderId== null||orderId.equals("")){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001" , "参数异常")) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        List<OrderChildBO> orderBO=orderService.getRoomInfoById(orderId);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(orderBO)) ;
        super.safeJsonPrint(response, result);
    }

    //根据子订单id查询房间信息消费信息(客帐管理)
    @RequestMapping("/getOrderInfoById")
    public void getOrderInfoById(Integer orderChildId,HttpServletRequest request, HttpServletResponse response){
        //验证管理员
        AdminBO userInfo = super.getLoginAdmin(request) ;
        if(userInfo == null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002" , "用户没有登录")) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        //验证参数
        if(orderChildId== null||orderChildId.equals("")){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001" , "参数异常")) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        OrderChildBO orderBO=orderService.getOrderInfoById(orderChildId);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(orderBO)) ;
        super.safeJsonPrint(response, result);
    }

    /**
     * 入住支付
     * @param id 子订单id 也就是主账房
     * @param money 实付金额
     * @param payType 支付方式
     * @param name 付款人姓名
     * */
    @RequestMapping("/pay")
    public void pay(BigDecimal money, String payType,Integer id,String name, HttpServletRequest request, HttpServletResponse response){
        //验证管理员
        AdminBO userInfo = super.getLoginAdmin(request) ;
        if(userInfo == null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002" , "用户没有登录")) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        //验证参数
        if(id== null||money==null||payType==null||id.equals("")||money.equals("")||payType.equals("")){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001" , "参数异常")) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        //获取子订单信息
        OrderChildBO orderChildResult=orderService.getOrderChildById(id);
        if(orderChildResult==null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001" , "此订单异常")) ;
            super.safeJsonPrint(response, result);
            return ;
        }

        //获取主订单信息
        OrderBO orderBO=orderService.getOrderById(orderChildResult.getOrderId());

        //添加报表记录
        cashierSummaryService.addCheck(money,payType,orderBO.getOrderNumber(),userInfo.getId(),
                name,orderBO.getOTA(),orderBO.getOrderType(),
                orderBO.getChannel(),orderChildResult.getRoomName(),orderChildResult.getRoomTypeName(),
                null,userInfo.getHotelId());

        //修改子订单信息
        OrderChildBO orderChildBO=new OrderChildBO();
        orderChildBO.setId(id);
        if(payType.equals(Constants.CASH.getValue())){
            orderChildBO.setPayCashNum(money);
        }else{
            orderChildBO.setOtherPayNum(money);
        }
        orderChildBO.setOrderState(Constants.ADMISSIONS.getValue());
        orderChildBO.setMain("yes");
        orderService.updOrderChild(orderChildBO);

        //插入订单记录
        orderRecordService.addOrderRecord(orderChildBO.getId(),Constants.INTHEDEPOSIT.getValue(),
                payType,money,Constants.CASHPLEDGE.getValue(),userInfo.getId(),null);

        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("支付成功")) ;
        super.safeJsonPrint(response, result);
    }


    /**
     * 入住成功支付页面
     * @param orderId 订单id
     * */
    @RequestMapping("/getPayInfo")
    public void getPayInfo(Integer orderId, HttpServletRequest request, HttpServletResponse response) {
        //验证管理员
        AdminBO userInfo = super.getLoginAdmin(request);
        if (userInfo == null) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002", "用户没有登录"));
            super.safeJsonPrint(response, result);
            return;
        }
        //验证参数
        if (orderId == null) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001", "参数异常"));
            super.safeJsonPrint(response, result);
            return;
        }
        OrderBO orderBO=orderService.getOrderById(orderId);
        Map<String,Object> resultMap=new HashMap<String, Object>();
        resultMap.put("totalPrice",orderBO.getTotalPrice());
        List<OrderChildBO> orderChildBOS=orderService.getRoomInfoById(orderId);
        resultMap.put("orderChildBOS",orderChildBOS);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(resultMap));
        super.safeJsonPrint(response, result);
    }

    /**
     * 获取在住报表
     * */
    @RequestMapping("/getCheckInReport")
    public void getCheckInReport(HttpServletRequest request, HttpServletResponse response){
        //验证管理员
        AdminBO userInfo = super.getLoginAdmin(request) ;
        if(userInfo == null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002" , "用户未登录")) ;
            super.safeJsonPrint(response, result);
            return ;
        }

        List<OrderResult> results=orderService.getCheckInReport();
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(results)) ;
        super.safeJsonPrint(response, result);

    }

    /**
     * 获取预离报表
     * @param beforeTime 在这个时间之前 最大时间
     * @param afterTime 在这个时间之后 最小时间
     * */
    @RequestMapping("/getCheckOutReport")
    public void getCheckOutReport(@DateTimeFormat(pattern="yyyy/MM/dd hh:mm:ss")Date beforeTime, @DateTimeFormat(pattern="yyyy/MM/dd hh:mm:ss")Date afterTime,Integer pageNo,Integer pageSize, HttpServletRequest request, HttpServletResponse response){
        //验证管理员
        AdminBO userInfo = super.getLoginAdmin(request) ;
        if(userInfo == null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002" , "用户未登录")) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        Map<String,Object> resultMap=new HashMap<String, Object>();
        QueryInfo queryInfo=getQueryInfo(pageNo,pageSize);
        List<OrderResult> list=orderService.getCheckOutReport(beforeTime,afterTime,queryInfo.getPageOffset(),queryInfo.getPageSize());
        Integer count=orderService.getCheckOutReportCount(beforeTime,afterTime);

        resultMap.put("list",list);
        resultMap.put("count",count);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(resultMap)) ;
        super.safeJsonPrint(response, result);

    }

    /**
     * 在住信息查询
     * */
    @RequestMapping("/getCheckInInfo")
    public void getCheckInInfo(Integer roomId, HttpServletRequest request, HttpServletResponse response){
        //验证管理员
        AdminBO userInfo = super.getLoginAdmin(request) ;
        if(userInfo == null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002" , "用户未登录")) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        //验证参数
        if (roomId == null||roomId.equals("")) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001", "参数异常"));
            super.safeJsonPrint(response, result);
            return;
        }
        CheckInInfoResult checkInInfoResult=orderService.getCheckInInfo(roomId);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(checkInInfoResult));
        super.safeJsonPrint(response, result);
    }

    /**
     * 修改在住信息
     * @param orderId 主订单id
     * @param channel 客源
     * @param OTA OTA
     * @param orderChildId 子订单id
     * @param entTime 离店时间
     * @param remark 房间备注
     * @param checkInPersonJson 入住人信息
     *
     * */
    @RequestMapping("/updCheckInInfo")
    public void updCheckInInfo(Integer orderId,String channel,String OTA,
                               Integer orderChildId, @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")Date entTime,String remark,
                               String checkInPersonJson,HttpServletResponse response,HttpServletRequest request) throws ParseException {
        //验证管理员
        AdminBO userInfo = super.getLoginAdmin(request) ;
        if(userInfo == null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002" , "用户未登录")) ;
            super.safeJsonPrint(response, result);
            return ;
        }

        orderService.updCheckInInfo(orderId,channel,OTA,orderChildId,entTime,remark,checkInPersonJson);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success( "修改成功"));
        super.safeJsonPrint(response, result);
        return;
    }


    /**
     * 查询所有可用联房
     * */
    @RequestMapping("/getAlRoom")
    public void  getAlRoom(Integer roomId,HttpServletRequest request,HttpServletResponse response){
        //验证管理员
        AdminBO userInfo = super.getLoginAdmin(request) ;
        if(userInfo == null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002" , "用户未登录")) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        //验证参数
        if (roomId == null||roomId.equals("")) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001", "参数异常"));
            super.safeJsonPrint(response, result);
            return;
        }
        List<CheckInPersonBO> checkInPersonBOS=orderService.getAlRoom(roomId);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(checkInPersonBOS));
        super.safeJsonPrint(response, result);
    }

    /**
     * 解除联房
     * */
    @RequestMapping("/updAlRoom")
    public void  updAlRoom(String orderChildIds,HttpServletRequest request,HttpServletResponse response){
        //验证管理员
        AdminBO userInfo = super.getLoginAdmin(request) ;
        if(userInfo == null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002" , "用户未登录")) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        //验证参数
        if (orderChildIds == null||orderChildIds.length()<2) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001", "参数异常"));
            super.safeJsonPrint(response, result);
            return;
        }
        Integer[] idArr=JsonUtils.getIntegerArray4Json(orderChildIds);
        orderService.updAlRoom(idArr);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(null));
        super.safeJsonPrint(response, result);
    }

    /**
     * 联房 自动转账 转账记录
     * */
    @RequestMapping("/addAlRoom")
    public void addAlRoom(Integer orderChildId,String orderChildIds, HttpServletRequest request, HttpServletResponse response){
        //验证管理员
        AdminBO userInfo = super.getLoginAdmin(request) ;
        if(userInfo == null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002" , "用户未登录")) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        //验证参数
        if (orderChildId==null||orderChildId.equals("")||orderChildIds == null||orderChildIds.length()==0) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001", "参数异常"));
            super.safeJsonPrint(response, result);
            return;
        }
        orderService.addAlRoom(orderChildId,orderChildIds,userInfo.getId());
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(null));
        super.safeJsonPrint(response, result);
    }

    /**
     * 检查身份证号是否在住
     * @param id 身份证号
     * */
    @RequestMapping("/checkId")
    public void checkId(String id,HttpServletRequest request, HttpServletResponse response){
        //验证管理员
        AdminBO userInfo = super.getLoginAdmin(request) ;
        if(userInfo == null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002" , "用户没有登录")) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        //验证参数
        if(id== null||id.length()==0){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001" , "参数异常")) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        Integer count=checkInPersonService.checkId(id,null);
        if(count>0){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000094","身份证信息为在住状态")) ;
            super.safeJsonPrint(response, result);
        }else{
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("未在住")) ;
            super.safeJsonPrint(response, result);
        }

    }

    /**
     * 获取剩余每日房价
     * @param orderChildId 子订单号
     * */
    @RequestMapping("/getRemainingLease")
    public void getRemainingLease(Integer orderChildId,HttpServletRequest request, HttpServletResponse response) throws ParseException {
        //验证管理员
        AdminBO userInfo = super.getLoginAdmin(request) ;
        if(userInfo == null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002" , "用户没有登录")) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        //验证参数
        if(orderChildId== null||orderChildId.equals("")){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001" , "参数异常")) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        List<EverydayRoomPriceBO> everydayRoomPriceBOList = orderService.getRemainingLease(orderChildId);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(everydayRoomPriceBOList)) ;
        super.safeJsonPrint(response, result);
    }

    /**
     * 换房 修改房间 每日房价
     * @param orderChildBO 子订单信息
     * @param everydayRoomPrice 房价信息
     * */
    @RequestMapping("/changeRoom")
    public void changeRoom(OrderChildBO orderChildBO,String everydayRoomPrice,HttpServletRequest request, HttpServletResponse response) throws ParseException {
        //验证管理员
        AdminBO userInfo = super.getLoginAdmin(request) ;
        if(userInfo == null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002" , "用户没有登录")) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        //验证参数
        if(orderChildBO==null||orderChildBO.getId()==null||everydayRoomPrice==null||everydayRoomPrice.length()<2){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001" , "参数异常")) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        List<EverydayRoomPriceBO> everydayRoomPriceBOList = JsonUtils.getJSONtoList(everydayRoomPrice, EverydayRoomPriceBO.class);
        orderService.changeRoom(orderChildBO,everydayRoomPriceBOList);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(null)) ;
        super.safeJsonPrint(response, result);
    }

    /**
     * 订单列表
     */
    @RequestMapping("/queryOrderList")
    public void queryOrderList(HttpServletRequest request, HttpServletResponse response, OrderParam param) {
        try {
            log.info(request.getRequestURI());
            log.info("param:{}", JsonUtils.getJsonString4JavaPOJO(request.getParameterMap()));
            AdminBO loginAdmin = super.getLoginAdmin(request);
            log.info("user{}", loginAdmin);
            if (loginAdmin == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }

            //计算分页
            QueryInfo queryInfo = getQueryInfo(param.getPageNo(),param.getPageSize());
            param.setPageOffset(queryInfo.getPageOffset());
            param.setPageSize(queryInfo.getPageSize());

            List<OrderListBO> orderListBOS = orderService.queryOrderList(param);


            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(orderListBOS));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
            return;
        } catch (Exception e) {
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000004"));
            super.safeJsonPrint(response, result);
            log.error("addFloorException", e);
         }
       }
    }
