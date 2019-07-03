package com.szq.hotel.web.controller;
import com.szq.hotel.entity.bo.ClassesBO;
import com.szq.hotel.entity.dto.ResultDTOBuilder;
import com.szq.hotel.service.ClassesService;
import com.szq.hotel.service.TestService;
import com.szq.hotel.util.JsonUtils;
import com.szq.hotel.web.controller.base.BaseCotroller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * 班次
 */
@Controller
@RequestMapping("/classes")
public class ClassesController extends BaseCotroller {
    @Resource
    private ClassesService classesService;

    /**
     * 查询所有
     */
    @RequestMapping("/getClasses")
    public void getClasses(HttpServletResponse response){
        List<ClassesBO> classesBOS = classesService.getAllClassesBO();
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(classesBOS)) ;
        super.safeJsonPrint(response, result);
    }

}




