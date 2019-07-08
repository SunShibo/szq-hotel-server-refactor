package com.szq.hotel.web.controller;
import com.szq.hotel.entity.bo.AdminBO;
import com.szq.hotel.entity.bo.MemberCardBO;
import com.szq.hotel.entity.dto.ResultDTOBuilder;
import com.szq.hotel.query.QueryInfo;
import com.szq.hotel.service.MemberCardService;
import com.szq.hotel.util.JsonUtils;
import com.szq.hotel.util.StringUtils;
import com.szq.hotel.web.controller.base.BaseCotroller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;


@Controller
@RequestMapping("/memberCard")
public class MemberCardController extends BaseCotroller {
    private static final Logger log = LoggerFactory.getLogger(MemberCardController.class);


    @Resource
    private MemberCardService memberCardService;

    /*
        批量添加会员卡
    */
    @RequestMapping("/addMemberCard")
    public void  addMemberCard(Integer memberLevelId, String[] cardNumber, BigDecimal money,HttpServletRequest request, HttpServletResponse response){
        try{
            log.info(request.getRequestURI());
            log.info("param:{}", JsonUtils.getJsonString4JavaPOJO(request.getParameterMap()));
            AdminBO loginAdmin = super.getLoginAdmin(request);
            log.info("user{}",loginAdmin);
            if (loginAdmin == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }
            //验证参数
            if (memberLevelId==null ||cardNumber.length==0|| money==null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }

            //String[] value =cardNumber.split(",");

            List<String> productSkuId = new ArrayList<String>();


            for (int i = 0; i <cardNumber.length; i++)
            {
                productSkuId.add(cardNumber[i]);
            }
            boolean isRepeat = productSkuId.size() != new HashSet<String>(productSkuId).size();
            if (isRepeat){
                String result=JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response,result);
                log.info("result{}",result);
                return;
            }

            List<MemberCardBO> memberCardBOS = memberCardService.queryCartByCartList(productSkuId);
            if (!memberCardBOS.isEmpty()){
                String result=JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000007"));
                super.safeJsonPrint(response,result);
                log.info("result{}",result);
                return;
            }

            HashMap<String,Object> map =new HashMap<String, Object>();
            map.put("money",money);
            map.put("memberLevelId",memberLevelId);
            map.put("list",productSkuId);
            memberCardService.addMemberCard(map);

            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("批量添加会员卡成功！"));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;

        }catch (Exception e){
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000004"));
            super.safeJsonPrint(response, result);
            log.error("addMemberCardException",e);
        }
    }

    /**
     * 删除会员卡
     * @param id
     * @param request
     * @param response
     */
    @RequestMapping("/deleteMemberCard")
    public void deleteMemberCard(Integer id,HttpServletRequest request, HttpServletResponse response){
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

            memberCardService.deleteMemberCard(id);

            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("删除会员卡成功！"));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;

        }catch (Exception e){
            log.error("deleteMemberCardException",e);
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000003"));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;

        }
    }

    /**
     * 修改会员卡
     * @param memberCardBO
     * @param request
     * @param response
     */
    @RequestMapping("/updateMemberCard")
    public void updateMemberCard(MemberCardBO memberCardBO,HttpServletRequest request, HttpServletResponse response){
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
            if (memberCardBO.getId() == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }

            memberCardService.updateMemberCard(memberCardBO);
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("修改会员卡成功"));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;

        }catch (Exception e){
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, result);
            log.error("updateMemberCardException",e);
        }
    }

    /*
     * 条件分页查询会员卡
     */
    @RequestMapping("/selectMemberCard")
    public void selectMemberCard(String state,Integer memberLevelId,BigDecimal money,Integer cardNumber,Integer pageNo, Integer pageSize,HttpServletRequest request, HttpServletResponse response){
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
            map.put("state",state);
            map.put("memberLevelId",memberLevelId);
            map.put("money",money);
            map.put("cardNumber",cardNumber);

            QueryInfo queryInfo = getQueryInfo(pageNo,pageSize);
            if(queryInfo != null){
                map.put("pageSize",queryInfo.getPageSize());
                map.put("pageOffset",queryInfo.getPageOffset());
            }

            List<MemberCardBO> memberCardBOS = memberCardService.selectMemberCard(map);
            Integer count = memberCardService.getCount(map);
            Map<String,Object> resultMap=new HashMap<String, Object>();
            resultMap.put("memberCardBOS",memberCardBOS);
            resultMap.put("count",count);

            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(resultMap));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;
        }catch (Exception e){
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, result);
            log.error("selectMemberCardException",e);
        }
    }

    /**
     * Excel导入会员卡
     */
    @RequestMapping("/importMemberCard")
    public void importMemberCard(MultipartFile file, HttpServletRequest  request, HttpServletResponse  response) {
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
            //参数验证
            if (file == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }
            String name = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            if (".xlsx".equals(name) || ".xls".equals(name) ) {
                Integer row = memberCardService.importMemberCard(file);

                if(row==null){
                    String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(""));
                    super.safeJsonPrint(response, result);
                }else if(row==-1){
                    String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000010"));
                    super.safeJsonPrint(response, result);
                    return;
                }
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000005"));

                super.safeJsonPrint(response, result);

            }




        } catch (Exception e) {
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, result);
            log.error("importMemberCardException", e);
        }
    }



}




