package com.szq.hotel.web.controller;

import com.szq.hotel.common.constants.Constants;
import com.szq.hotel.entity.bo.*;
import com.szq.hotel.entity.dto.ResultDTOBuilder;
import com.szq.hotel.entity.param.OrderParam;
import com.szq.hotel.query.QueryInfo;
import com.szq.hotel.service.CashierSummaryService;
import com.szq.hotel.service.CheckInPersonService;
import com.szq.hotel.service.OrderService;
import com.szq.hotel.util.JsonUtils;
import com.szq.hotel.web.controller.base.BaseCotroller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
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
            //验证所有入住人中是否有重复入住的
            Set<String> idSet=new HashSet<String>();
            List<String> idList=new ArrayList<String>();

            for (OrderChildBO order:list) {
                List<CheckInPersonBO> personBOS=order.getCheckInPersonBOS();
                for (CheckInPersonBO personBO:personBOS){
                    idSet.add(personBO.getCertificateNumber());
                    idList.add(personBO.getCertificateNumber());
                    if(checkInPersonService.checkId(personBO.getCertificateNumber(),orderBO.getId())>0){
                        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000094" , personBO.getCertificateNumber()+"此证件信息为在住状态")) ;
                        super.safeJsonPrint(response, result);
                        return;
                    }
                }
            }

            if(idList.size()>idSet.size()){
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000094" , "入住证件信息重复")) ;
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
     * 根据主订单id查询房间信息（客帐管理）
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

    //根据子订单id查询房间信息消费信息（客帐管理）【王洋】
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
     * */
    @RequestMapping("/pay")
    public void pay(BigDecimal money, String payType,Integer id, HttpServletRequest request, HttpServletResponse response){
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

        //获取主订单信息
        OrderBO orderBO=orderService.getOrderById(orderChildResult.getOrderId());

        //添加报表记录
//        cashierSummaryService.addCheck(money,payType,orderBO.getOrderNumber(),userInfo.getId(),
//                "name","",orderBO.getOrderType(),
//                "","","");

        //修改子订单信息
        OrderChildBO orderChildBO=new OrderChildBO();
        orderChildBO.setId(id);
        orderChildBO.setOrderState(Constants.ADMISSIONS.getValue());
        //orderChildBO.setRoomRate(cs.getMoney());

        orderService.updOrderChild(orderChildBO);
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
