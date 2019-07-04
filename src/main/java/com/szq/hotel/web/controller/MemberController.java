package com.szq.hotel.web.controller;
import com.szq.hotel.entity.bo.AdminBO;
import com.szq.hotel.entity.bo.MemberBO;
import com.szq.hotel.entity.dto.ResultDTOBuilder;
import com.szq.hotel.query.QueryInfo;
import com.szq.hotel.service.MemberService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/member")
public class MemberController extends BaseCotroller {
    private static final Logger log = LoggerFactory.getLogger(MemberController.class);

    @Resource
    private MemberService memberService;

    /**
     * 新增会员
     * @param memberBO
     * @param request
     * @param response
     */
    @RequestMapping("/addMember")
    public void  addmember(MemberBO memberBO, HttpServletRequest request, HttpServletResponse response){
        try {

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
            if (StringUtils.isEmpty(memberBO.getName())||StringUtils.isEmpty(memberBO.getCertificateNumber())||StringUtils.isEmpty(memberBO.getCertificateType())||StringUtils.isEmpty(memberBO.getPhone())){
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return ;
            }

            memberService.addMember(memberBO,loginAdmin.getId());

            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("添加会员成功！"));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;
        }catch (Exception e){
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000004"));
            super.safeJsonPrint(response, result);
            log.error("addMemberException",e);
        }
    }

    /**
     * 删除会员
     * @param id
     * @param request
     * @param response
     */
    @RequestMapping("/deleteMember")
    public void deleteMember(Integer id,HttpServletRequest request, HttpServletResponse response){
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

            if(id==null){
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }

            memberService.deleteMember(id);

            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("删除会员成功！"));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;

        }catch (Exception e){
            log.error("deleteMemberException",e);
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000006"));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;
        }
    }

    /**
     * 修改会员信息
     * @param memberBO
     * @param request
     * @param response
     */
    @RequestMapping("/updateMember")
    public void updateMember(MemberBO memberBO,HttpServletRequest request, HttpServletResponse response){
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
            if (memberBO.getId() == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }

            memberService.updateMember(memberBO,loginAdmin.getId());
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("修改会员信息成功！"));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;
        }catch (Exception e){
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, result);
            log.error("updateMemberException",e);
        }

    }

    /**
     * 条件分页查询会员用户信息
     * @param memberBO
     * @param request
     * @param response
     */
    @RequestMapping("/selectMember")
    public void selectMember(MemberBO memberBO,HttpServletRequest request, HttpServletResponse response){
        try {
            log.info(request.getRequestURI());
            log.info("param:{}", JsonUtils.getJsonString4JavaPOJO(request.getParameterMap()));
            AdminBO loginAdmin = super.getLoginAdmin(request);
            log.info("user{}", loginAdmin);
            if (loginAdmin == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            Map<String,Object> map=new HashMap<String, Object>();
            map.put("memberCardId",memberBO.getMemberCardId());
            map.put("phone",memberBO.getPhone());
            map.put("certificateType",memberBO.getCertificateType());
            map.put("certificateNumber",memberBO.getCertificateNumber());
            map.put("integral",memberBO.getIntegral());
            map.put("storedValue",memberBO.getStoredValue());
            map.put("createUserId",memberBO.getCreateUserId());
            map.put("name",memberBO.getName());
            map.put("birthday",memberBO.getBirthday());
            map.put("gender",memberBO.getGender());
            map.put("address",memberBO.getAddress());
            map.put("salesman",memberBO.getSalesman());


            QueryInfo queryInfo = getQueryInfo(memberBO.getPageNo(),memberBO.getPageSize());
            if(queryInfo != null){
                map.put("pageSize",queryInfo.getPageSize());
                map.put("pageIndex",queryInfo.getPageOffset());
            }

            List<MemberBO> memberBOS = memberService.selectMember(map);

            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(memberBOS));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;
        }catch (Exception e){
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, result);
            log.error("selectmemberException",e);
        }
    }


}




