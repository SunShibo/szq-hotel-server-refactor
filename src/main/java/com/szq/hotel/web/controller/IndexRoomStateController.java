package com.szq.hotel.web.controller;

import com.szq.hotel.web.controller.base.BaseCotroller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Bin Wang
 * @date: Created in 11:06 2019/7/11
 */
@RequestMapping("/indexRoomState")
public class IndexRoomStateController extends BaseCotroller {


    @RequestMapping("/roomState")
    public void roomState(HttpServletRequest request, HttpServletResponse response, Integer roomId){
        
    }
}
