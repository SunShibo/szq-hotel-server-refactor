package com.szq.hotel.web.controller;
import com.szq.hotel.entity.bo.AdminBO;
import com.szq.hotel.entity.bo.MemberLevelBO;
import com.szq.hotel.entity.dto.ResultDTOBuilder;
import com.szq.hotel.service.MemberLevelService;
import com.szq.hotel.service.MemberRoomTypeService;
import com.szq.hotel.service.RoomTypeService;
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
@RequestMapping("/memberLevel")
public class MemberLevelController extends BaseCotroller {
    private static final Logger log = LoggerFactory.getLogger(MemberLevelController.class);

    @Resource
    private MemberLevelService memberLevelService;

    /**
     * 新增会员级别
     * @param memberLevelBO
     * @param request
     * @param response
     */
    @RequestMapping("/addMemberLevel")
    public void  addmemberlevel(MemberLevelBO memberLevelBO,HttpServletRequest request, HttpServletResponse response){
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
            if (StringUtils.isEmpty(memberLevelBO.getName())||StringUtils.isEmpty(memberLevelBO.getType())){
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return ;
            }
            //设置默认折扣
            memberLevelBO.setDiscount(BigDecimal.valueOf((int)1));
            memberLevelService.addMemberLevel(memberLevelBO,loginAdmin.getId());
            MemberLevelBO memberLevelBO1 = memberLevelService.selectMemberLevelByName(memberLevelBO.getName());
            Integer memberLevelId = memberLevelBO1.getId();

            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(memberLevelId));
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
     * 删除会员级别
     * @param memberLevelId
     * @param request
     * @param response
     */
    @RequestMapping("/deleteMemberLevel")
    public void deleteMemberLevel(Integer memberLevelId,HttpServletRequest request, HttpServletResponse response){
        try{
            log.info(request.getRequestURI());
            log.info("param:{}", JsonUtils.getJsonString4JavaPOJO(request.getParameterMap()));
            AdminBO loginAdmin = super.getLoginAdmin(request);
            if (loginAdmin == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }

            if(memberLevelId==null){
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }

            memberLevelService.deleteMemberLevel(memberLevelId,loginAdmin.getId());

            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(""));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;

        }catch (Exception e){
            log.error("deleteMemberLevelException",e);
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000003"));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;

        }
    }
    /**
     * 修改会员级别
     * @param memberLevelBO
     * @param request
     * @param response
     */
    @RequestMapping("/updateMemberLevel")
    public void updateMemberLevel(MemberLevelBO memberLevelBO,HttpServletRequest request, HttpServletResponse response){
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
            if (memberLevelBO.getId() == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }

            memberLevelService.updateMemberLevel(memberLevelBO,loginAdmin.getId());
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(""));
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
     * 查询会员级别
     * @param request
     * @param response
     */
    @RequestMapping("/selectmemberLevel")
    public void selectmember(HttpServletRequest request, HttpServletResponse response){
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

            List<MemberLevelBO> memberLevelBOS = memberLevelService.selectMemberLevel();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(memberLevelBOS));
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




