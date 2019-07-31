package com.szq.hotel.web.controller;
import com.szq.hotel.entity.bo.AdminBO;
import com.szq.hotel.entity.dto.ResultDTOBuilder;
import com.szq.hotel.service.CheckInPersonService;
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


@Controller
@RequestMapping("/checkInPerson")
public class CheckInPersonController extends BaseCotroller {
    private static final Logger log = LoggerFactory.getLogger(CheckInPersonController.class);
    @Resource
    private CheckInPersonService checkInPersonService;

    /**
     * 添加同来人
     * @param orderChildId 子订单id
     * @param name 名称
     * @param gender 性别
     * @param phone 手机号
     * @param certificateNumber 证件号
     * @param certificateType 证件类型
     * @param status 状态
     * @param remark 备注
     * @param request
     * @param response
     */
    @RequestMapping("/addCheckInPerson")
    public void addCheckInPerson(Integer orderChildId,String name,String gender,String phone,String certificateNumber,Integer certificateType,String status,String remark,HttpServletRequest request, HttpServletResponse response){
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
            if (orderChildId==null||StringUtils.isEmpty(name)||StringUtils.isEmpty(phone)||StringUtils.isEmpty(certificateNumber)||certificateType == null){
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return ;
            }
            checkInPersonService.addCheckInPerson(orderChildId, name, gender, phone, certificateNumber, certificateType, status, remark,loginAdmin.getId());


            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("添加成功！"));
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
     * 修改同来人
     * @param id
     * @param name 名称
     * @param gender 性别
     * @param phone 手机号
     * @param certificateNumber 证件号
     * @param certificateType 证件类型
     * @param status 状态
     * @param remark 备注
     * @param request
     * @param response
     */
    @RequestMapping("/updCheckInPerson")
    public void updCheckInPerson(Integer id,String name,String gender,String phone,String certificateNumber,Integer certificateType,String status,String remark,HttpServletRequest request, HttpServletResponse response){

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
            if (id == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }

            checkInPersonService.updCheckInPerson(id,name, gender, phone, certificateNumber, certificateType, status, remark);
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("修改成功！"));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;

        }catch (Exception e){
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, result);
            log.error("updCheckInPersonException",e);
        }
    }

}




