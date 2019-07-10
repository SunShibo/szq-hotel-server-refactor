package com.szq.hotel.web.controller;

import com.szq.hotel.entity.bo.AdminBO;
import com.szq.hotel.entity.bo.IntegralRecordBO;
import com.szq.hotel.entity.bo.StoredValueRecordBO;
import com.szq.hotel.entity.dto.ResultDTOBuilder;
import com.szq.hotel.query.QueryInfo;
import com.szq.hotel.service.StoredValueRecordService;
import com.szq.hotel.util.JsonUtils;
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
@RequestMapping("/storedValueRecord")
public class StoredValueRecordController extends BaseCotroller{
    private static final Logger log = LoggerFactory.getLogger(StoredValueRecordController.class);
    @Resource
    private StoredValueRecordService storedValueRecordService;

    /**
     * 查询储值记录
     * @param pageNo
     * @param pageSize
     * @param request
     * @param response
     */
    @RequestMapping("/getStoredValueRecord")
    public void getStoredValueRecord(Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response) {
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
            QueryInfo queryInfo = getQueryInfo(pageNo,pageSize);
            if(queryInfo != null){
                map.put("pageSize",queryInfo.getPageSize());
                map.put("pageOffset",queryInfo.getPageOffset());
            }
            List<StoredValueRecordBO> storedValueRecordBOS = storedValueRecordService.getStoredValueRecord(map);
            Integer count = storedValueRecordService.getStoredValueRecordCount(map);
            Map<String,Object> resultMap=new HashMap<String, Object>();
            resultMap.put("storedValueRecordBOS",storedValueRecordBOS);
            resultMap.put("count",count);
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(resultMap));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;
        }catch (Exception e){
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000004"));
            super.safeJsonPrint(response, result);
            log.error("getStoredValueRecordException",e);
        }

    }

}
