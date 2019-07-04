package com.szq.hotel.web.controller;

import com.szq.hotel.entity.bo.AdminBO;
import com.szq.hotel.entity.bo.OrderBO;
import com.szq.hotel.entity.bo.OrderChildBO;
import com.szq.hotel.entity.dto.ResultDTOBuilder;
import com.szq.hotel.service.OrderService;
import com.szq.hotel.util.JsonUtils;
import com.szq.hotel.web.controller.base.BaseCotroller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.alibaba.fastjson.JSON;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/order")
public class OrderController extends BaseCotroller {

    @Resource
    OrderService orderService;

    /**
     * 房间预定
     * @param orderBO 预约信息
     * @param orderChildJSON json格式的预定的房间信息
     * @param EverydayRoomPriceJSON json格式的预定的房型的没日的价格
     * */
    @RequestMapping("/reservationRoom")
    public void adminLogin(HttpServletRequest request, HttpServletResponse response,
                           OrderBO orderBO,String orderChildJSON,String EverydayRoomPriceJSON) {
        //验证管理员
        AdminBO userInfo = super.getLoginUser(request) ;
        if(userInfo == null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002" , "用户没有登录")) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        //验证参数
        if(orderBO== null||orderChildJSON==null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002" , "用户没有登录")) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        //插入订单信息
        orderBO.setClerkOrderingId(userInfo.getId());
        Integer orderId = orderService.addOrder(orderBO);

        //插入子订单信息 多个房间或者房型
        List<OrderChildBO> orderChildBOList = JsonUtils.getJSONtoList(orderChildJSON,OrderChildBO.class);
        orderService.addOrderChild(orderChildBOList,orderId,userInfo.getHotelId());
    }

}
