package com.szq.hotel.web.controller;
import com.szq.hotel.entity.bo.AdminBO;
import com.szq.hotel.entity.bo.ClassesBO;
import com.szq.hotel.entity.dto.ResultDTOBuilder;
import com.szq.hotel.service.ClassesService;
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
import java.util.List;


/**
 * 班次
 */
@Controller
@RequestMapping("/classes")
public class ClassesController extends BaseCotroller {
    private static final Logger log = LoggerFactory.getLogger(ClassesController.class);

    @Resource
    private ClassesService classesService;

    /**
     * 登录查询
     */
    @RequestMapping("/getClasses")
    public void getClasses(HttpServletRequest request,HttpServletResponse response, Integer hotelId){
        try {
            log.info(request.getRequestURI());
            log.info("param:{}", JsonUtils.getJsonString4JavaPOJO(request.getParameterMap()));
            AdminBO loginAdmin = super.getLoginAdmin(request);
            log.info("user{}",loginAdmin);
            //不是登录时不传hotelId  这时查询当前用户登录的酒店班次
            if(hotelId==null){
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                return;
            }

            List<ClassesBO> classesBOS = classesService.queryClasses(hotelId);
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(classesBOS)) ;
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;

        }catch (Exception e){
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000005"));
            super.safeJsonPrint(response, result);
            log.error("getClassesException",e);
        }

    }


    /**
     * 查询所有班次
     */
    @RequestMapping("/queryClasses")
    public void queryClasses(HttpServletRequest request,HttpServletResponse response, Integer hotelId){
        try {
            log.info(request.getRequestURI());
            log.info("param:{}", JsonUtils.getJsonString4JavaPOJO(request.getParameterMap()));
            AdminBO loginAdmin = super.getLoginAdmin(request);
            log.info("user{}",loginAdmin);
            //不是登录时不传hotelId  这时查询当前用户登录的酒店班次
            List<ClassesBO> classesBOS = classesService.queryClasses(hotelId);
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(classesBOS)) ;
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;

        }catch (Exception e){
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000005"));
            super.safeJsonPrint(response, result);
            log.error("queryClasse",e);
        }

    }

    /**
     * 添加班次
     */
    @RequestMapping("/addClasses")
    public void addClasses(HttpServletRequest request,HttpServletResponse response,ClassesBO classesBO){
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
            if ( StringUtils.isEmpty(classesBO.getStartTime()) ||  StringUtils.isEmpty(classesBO.getEndTime()) || StringUtils.isEmpty(classesBO.getClassesName()) ) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }

            classesService.addClasses(classesBO,loginAdmin.getId());
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("")) ;
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;

        }catch (Exception e){
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000005"));
            super.safeJsonPrint(response, result);
            log.error("addClasses",e);
        }

    }


    /**
     * 修改班次
     */
    @RequestMapping("/updateClasses")
    public void updateClasses(HttpServletRequest request,HttpServletResponse response,ClassesBO classesBO){
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

            if (classesBO.getId() == null || StringUtils.isEmpty(classesBO.getStartTime()) ||  StringUtils.isEmpty(classesBO.getEndTime()) || StringUtils.isEmpty(classesBO.getClassesName()) ) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }

            classesService.updateClasses(classesBO,loginAdmin.getId());
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("")) ;
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;

        }catch (Exception e){
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000005"));
            super.safeJsonPrint(response, result);
            log.error("updateClasses",e);
        }

    }


    /**
     * 删除班次
     */
    @RequestMapping("/deleteClasses")
    public void deleteClasses(HttpServletRequest request,HttpServletResponse response,Integer id){
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

            if (id == null ) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }

            //只有一个班次不能删除
            if (classesService.queryCount(loginAdmin.getHotelId())==1 ) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000010"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }

            classesService.deleteClasses(id);
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("")) ;
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;

        }catch (Exception e){
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000005"));
            super.safeJsonPrint(response, result);
            log.error("deleteClasses",e);
        }
    }

}