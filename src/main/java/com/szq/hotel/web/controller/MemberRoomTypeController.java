package com.szq.hotel.web.controller;
import com.szq.hotel.entity.bo.AdminBO;
import com.szq.hotel.entity.bo.MemberRoomTypeBO;
import com.szq.hotel.entity.dto.ResultDTOBuilder;
import com.szq.hotel.service.MemberRoomTypeService;
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
import java.util.List;


@Controller
@RequestMapping("/memberRoomType")
public class MemberRoomTypeController extends BaseCotroller {
    private static final Logger log = LoggerFactory.getLogger(MemberRoomTypeController.class);
    @Resource
    private MemberRoomTypeService memberRoomTypeService;

    /**
     * 新增会员房型折扣
     * @param memberLevelId
     * @param roomTypeId
     * @param price
     * @param discount
     * @param request
     * @param response
     */
    @RequestMapping("/addMemberRoomType")
    public void addMemberRoomType(Integer memberLevelId, Integer roomTypeId, BigDecimal price, BigDecimal discount, HttpServletRequest request, HttpServletResponse response){
        try{
            log.info(request.getRequestURI());
            log.info("param:{}", JsonUtils.getJsonString4JavaPOJO(request.getParameterMap()));
            //获取管理员对象
            AdminBO loginAdmin = super.getLoginAdmin(request);
            log.info("user{}",loginAdmin);
            if (loginAdmin==null){
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return ;
            }
            //参数验证
            if (memberLevelId==null||roomTypeId==null||discount==null){
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return ;
            }
            MemberRoomTypeBO memberRoomTypeBO = new MemberRoomTypeBO();
            memberRoomTypeBO.setMemberLevelId(memberLevelId);
            memberRoomTypeBO.setRoomTypeId(roomTypeId);
            memberRoomTypeBO.setDiscount(discount);
            memberRoomTypeBO.setPrice(price);

            memberRoomTypeService.addMemberRoomType(memberRoomTypeBO,loginAdmin.getId());

            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("添加会员房型折扣成功！"));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;

        }catch (Exception e){
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000004"));
            super.safeJsonPrint(response, result);
            log.error("addMemberLevelException",e);
        }
    }

    /**
     * 修改会员房型折扣
     * @param memberLevelId
     * @param roomTypeId
     * @param discount
     * @param request
     * @param response
     */
    @RequestMapping("/updateMemberRoomType")
    public void updateMemberRoomType(Integer memberLevelId, Integer roomTypeId, BigDecimal discount, HttpServletRequest request, HttpServletResponse response){
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
            if (memberLevelId== null||roomTypeId==null||discount==null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }
            MemberRoomTypeBO memberRoomTypeBO = new MemberRoomTypeBO();
            memberRoomTypeBO.setMemberLevelId(memberLevelId);
            memberRoomTypeBO.setRoomTypeId(roomTypeId);
            memberRoomTypeBO.setDiscount(discount);
            memberRoomTypeService.updateMemberRoomType(memberRoomTypeBO,loginAdmin.getId());

            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("修改会员房型折扣成功！"));
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
     * 查询会员房型折扣
     * @param request
     * @param response
     */
    @RequestMapping("/selectMemberRoomType")
    public void selectMemberRoomType(Integer memberLevelId, Integer roomTypeId,HttpServletRequest request, HttpServletResponse response){
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
            if (memberLevelId==null||roomTypeId==null){
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }

            BigDecimal dis = memberRoomTypeService.selectMemberRoomType(memberLevelId,roomTypeId);

            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(dis));
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




