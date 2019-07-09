package com.szq.hotel.web.controller;
import com.szq.hotel.service.IntegralRecordService;
import com.szq.hotel.service.TestService;
import com.szq.hotel.web.controller.base.BaseCotroller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;



@Controller
@RequestMapping("/integralRecord")
public class IntegralRecordController extends BaseCotroller {
    @Resource
    private IntegralRecordService integralRecordService;

    @RequestMapping("/addIntegralRecord")
    public void queryVersion(){

    }

}




