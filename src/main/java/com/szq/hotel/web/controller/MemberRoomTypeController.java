package com.szq.hotel.web.controller;
import com.szq.hotel.entity.bo.*;
import com.szq.hotel.entity.dto.ResultDTOBuilder;
import com.szq.hotel.service.MemberRoomTypeService;
import com.szq.hotel.service.RoomTypeService;
import com.szq.hotel.service.TestService;
import com.szq.hotel.util.JsonUtils;
import com.szq.hotel.util.StringUtils;
import com.szq.hotel.web.controller.base.BaseCotroller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;


@Controller
@RequestMapping("/memberRoomType")
public class MemberRoomTypeController extends BaseCotroller {
    private static final Logger log = LoggerFactory.getLogger(MemberRoomTypeController.class);
    @Resource
    private MemberRoomTypeService memberRoomTypeService;
    @Resource
    private RoomTypeService roomTypeService;




    /**
     * 修改会员折扣
     * @param memberLevelId
     * @param discount
     * @param request
     * @param response
     */
    @RequestMapping("/updateMemberRoomType")
    public void updateMemberRoomType(Integer memberLevelId, BigDecimal discount, HttpServletRequest request, HttpServletResponse response){
        try {
            log.info(request.getRequestURI());
            log.info("param:{}", JsonUtils.getJsonString4JavaPOJO(request.getParameterMap()));
            AdminBO loginAdmin = super.getLoginAdmin(request);
            log.info("user{}",loginAdmin);
            if (loginAdmin == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }
            if (memberLevelId== null||discount==null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }
                MemberLevelBO memberLevelBO = new MemberLevelBO();
                memberLevelBO.setDiscount(discount);
                memberLevelBO.setId(memberLevelId);
                memberRoomTypeService.updateMemberRoomType(memberLevelBO,loginAdmin.getId());




            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("修改会员折扣成功！"));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;

        }catch (Exception e){
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, result);
            log.error("updateMemberLevelException",e);
        }
    }

    /**
     * 查询会员折扣
     * @param request
     * @param response
     */
    @RequestMapping("/selectMemberRoomType")
    public void selectMemberRoomType(Integer memberLevelId,HttpServletRequest request, HttpServletResponse response){
        try {
            log.info(request.getRequestURI());
            log.info("param:{}", JsonUtils.getJsonString4JavaPOJO(request.getParameterMap()));
            AdminBO loginAdmin = super.getLoginAdmin(request);
            log.info("user{}",loginAdmin);
            if (loginAdmin == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }
            //参数验证
            if (memberLevelId==null){
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }

            MemberLevelBO memberLevelBO = memberRoomTypeService.selectMemberRoomType(memberLevelId);

            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(memberLevelBO.getDiscount()));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;

        }catch (Exception e){
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, result);
            log.error("selectmemberLevelException",e);
        }
    }
}




