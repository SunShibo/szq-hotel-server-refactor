package com.szq.hotel.web.controller;

import com.szq.hotel.entity.bo.HouseTypeBO;
import com.szq.hotel.entity.dto.ResultDTOBuilder;
import com.szq.hotel.service.HouseTypeService;
import com.szq.hotel.util.JsonUtils;
import com.szq.hotel.web.controller.base.BaseCotroller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: Bin Wang
 */
@Controller
@RequestMapping("/houseType")
public class HouseTypeController extends BaseCotroller {

    @Resource
    private HouseTypeService houseTypeService;

    @RequestMapping("/queryHouseType")
    public void queryHouseType(HttpServletRequest request, HttpServletResponse response, Integer id){
        List<HouseTypeBO> list = houseTypeService.queryHouseTypeList(id);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(list)) ;
        super.safeJsonPrint(response, result);
        return;
    }

    @RequestMapping("/insetHouseType")
    public void insetHouseType(HttpServletRequest request, HttpServletResponse response, HouseTypeBO record){
        houseTypeService.insertSelective(record);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("添加成功")) ;
        super.safeJsonPrint(response, result);
        return;
    }

    @RequestMapping("/updateHouseType")
    public void updateHouseType(HttpServletRequest request, HttpServletResponse response, HouseTypeBO record){
        houseTypeService.updateByPrimaryKeySelective(record);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("修改成功")) ;
        super.safeJsonPrint(response, result);
        return;
    }

    @RequestMapping("/delete")
    public void delete(HttpServletRequest request, HttpServletResponse response, Integer id){
        houseTypeService.delete(id);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("删除成功")) ;
        super.safeJsonPrint(response, result);
        return;
    }
}
