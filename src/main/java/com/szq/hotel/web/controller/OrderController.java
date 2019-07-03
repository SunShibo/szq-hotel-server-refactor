package com.szq.hotel.web.controller;

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

@Controller
@RequestMapping("/order")
public class OrderController extends BaseCotroller {

    @Resource
    OrderService orderService;

    /**
     * 房间预定
     *
     * */
    @RequestMapping("/reservationRoom")
    public void adminLogin(HttpServletRequest request, HttpServletResponse response,
                           String mobile, String password) {
        //验证参数
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001", "参数异常！"));
            super.safeJsonPrint(response, result);
            return;
        }
    }
}
