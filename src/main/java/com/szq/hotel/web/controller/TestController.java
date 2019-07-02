package com.szq.hotel.web.controller;
import com.szq.hotel.entity.dto.ResultDTOBuilder;
import com.szq.hotel.service.TestService;
import com.szq.hotel.util.JsonUtils;
import com.szq.hotel.web.controller.base.BaseCotroller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;


/**
 * @author Shibo Sun
 *         主机controller
 */
@Controller
@RequestMapping("/test")
public class TestController extends BaseCotroller {
    @Resource
    private TestService testService;

    @RequestMapping("/test")
    public void queryVersion(){
        testService.test();
    }

}




