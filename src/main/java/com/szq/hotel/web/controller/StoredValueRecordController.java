package com.szq.hotel.web.controller;

import com.szq.hotel.service.IntegralRecordService;
import com.szq.hotel.service.StoredValueRecordService;
import com.szq.hotel.web.controller.base.BaseCotroller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/storedValueRecord")
public class StoredValueRecordController extends BaseCotroller{
    @Resource
    private StoredValueRecordService storedValueRecordService;

}
