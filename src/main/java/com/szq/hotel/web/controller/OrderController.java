package com.szq.hotel.web.controller;

import com.szq.hotel.entity.bo.AdminBO;
import com.szq.hotel.entity.bo.OrderBO;
import com.szq.hotel.entity.dto.ResultDTOBuilder;
import com.szq.hotel.service.OrderService;
import com.szq.hotel.util.JsonUtils;
import com.szq.hotel.util.StringUtils;
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
     主订单 需要的
     预约人姓名
     预约人证件类型
     预约人证件号
     预约人电话

     入住方式
     接单员

     入住时间
     离店时间
     共计几天

     房间号
     订单类型 预约入住和直接入住
     *
     * */
    @RequestMapping("/reservationRoom")
    public void adminLogin(HttpServletRequest request, HttpServletResponse response,
            OrderBO orderBO ){
        AdminBO userInfo = super.getLoginUser(request) ;
        if(userInfo == null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001" , "用户没有登录")) ;
            super.safeJsonPrint(response, result);
            return ;
        }

    }

}
