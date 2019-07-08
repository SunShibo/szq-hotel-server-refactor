package com.szq.hotel.web.controller;

import com.szq.hotel.entity.bo.AdminBO;
import com.szq.hotel.entity.bo.OrderBO;
import com.szq.hotel.entity.bo.OrderChildBO;
import com.szq.hotel.entity.bo.OrderListBO;
import com.szq.hotel.entity.dto.ResultDTOBuilder;
import com.szq.hotel.entity.param.OrderParam;
import com.szq.hotel.query.QueryInfo;
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
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController extends BaseCotroller {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    @Resource
    OrderService orderService;

    /**
     * 房间预定 或者 预约入住
     * @param orderBO 预约信息
     * @param OrderChildJSON json格式的预定的房间信息
     * @param type 预约 或者 预约入住 或者 直接入住
     * */
    @RequestMapping("/reservationRoom")
    public void reservationRoom(HttpServletRequest request, HttpServletResponse response,
                                OrderBO orderBO,String OrderChildJSON,String type) {
        //验证管理员
        AdminBO userInfo = super.getLoginUser(request) ;
        if(userInfo == null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002" , "用户没有登录")) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        //验证参数
        if(orderBO== null||OrderChildJSON==null||type==null||type.length()==0){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002" , "用户没有登录")) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        if(type.equals("reservation")){
            //插入订单信息
            orderBO.setClerkOrderingId(userInfo.getId());
            orderBO.setHotelId(userInfo.getHotelId());
            orderService.addOrder(orderBO);

            //插入子订单信息 以及每日房价信息
            List<OrderChildBO> list = JsonUtils.getJSONtoList(OrderChildJSON, OrderChildBO.class);
            orderService.addOrderChild(list,orderBO);
        }else{
            //预约入住 修改订单 子订单 房价 入住人 等信息
            List<OrderChildBO> list = JsonUtils.getJSONtoList(OrderChildJSON, OrderChildBO.class);
            orderService.updOrderInfo(list,orderBO);

        }
    }

    /**
     * 预约入住 获取信息
     * 根据身份证号 手机号查询预约信息
     * @param idNumber 身份证号
     * @param mobile 手机号
     * */
    @RequestMapping("/getReservationRoomInfo")
    public void getReservationRoomInfo(HttpServletRequest request, HttpServletResponse response,String idNumber,String mobile){
        //验证管理员
//        AdminBO userInfo = super.getLoginUser(request) ;
//        if(userInfo == null){
//            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002" , "用户没有登录")) ;
//            super.safeJsonPrint(response, result);
//            return ;
//        }
        //验证参数
        if((idNumber== null||idNumber.length()==0)&&(mobile==null||mobile.length()==0)){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001" , "参数异常")) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        //查询信息
        OrderBO orderBO = orderService.getOrderInfo(idNumber,mobile);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(orderBO)) ;
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
