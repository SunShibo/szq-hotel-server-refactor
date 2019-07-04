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
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController extends BaseCotroller {

    @Resource
    OrderService orderService;

    /**
     * 房间预定
     * @param orderBO 预约信息
     * @param OrderChildJSON json格式的预定的房间信息
     * */
    @RequestMapping("/reservationRoom")
    public void adminLogin(HttpServletRequest request, HttpServletResponse response,
                           OrderBO orderBO,String OrderChildJSON) {
        //验证管理员
        AdminBO userInfo = super.getLoginUser(request) ;
        if(userInfo == null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002" , "用户没有登录")) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        //验证参数
        if(orderBO== null||OrderChildJSON==null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002" , "用户没有登录")) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        //插入订单信息
        orderBO.setClerkOrderingId(userInfo.getId());
        orderBO.setHotelId(userInfo.getHotelId());
        orderService.addOrder(orderBO);

        //插入子订单信息 以及每日房价信息
        List<OrderChildBO> list = JsonUtils.getJSONtoList(OrderChildJSON, OrderChildBO.class);
        orderService.addOrderChild(list,orderBO.getId(),userInfo.getHotelId());
    }

}
