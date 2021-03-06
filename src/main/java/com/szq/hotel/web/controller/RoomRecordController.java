package com.szq.hotel.web.controller;
import com.szq.hotel.entity.bo.AdminBO;
import com.szq.hotel.entity.bo.RoomRecordBO;
import com.szq.hotel.entity.dto.ResultDTOBuilder;
import com.szq.hotel.query.QueryInfo;
import com.szq.hotel.service.RoomRecordService;
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
@RequestMapping("/roomRecord")
public class RoomRecordController extends BaseCotroller {
    static final Logger log = LoggerFactory.getLogger(RoomRecordController.class);

    @Resource
    private RoomRecordService roomRecordService;

    @RequestMapping("/selectRoomRecord")
    public void selectRoomRecord(Integer id,Integer pageNo,Integer pageSize, HttpServletRequest request, HttpServletResponse response){
        try {
            //参数验证
            if (id == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }

            Map<String,Object> map=new HashMap<String, Object>();
            map.put("id",id);

            QueryInfo queryInfo = getQueryInfo(pageNo,pageSize);
            if(queryInfo != null){
                map.put("pageSize",queryInfo.getPageSize());
                map.put("pageOffset",queryInfo.getPageOffset());
            }

            List<RoomRecordBO> roomRecordBOList = roomRecordService.selectRoomRecord(map);
            Integer count = roomRecordService.selectRoomRecordCount(id);
            Map<String,Object> resultMap =new HashMap<String, Object>();
            resultMap.put("roomRecordBOList",roomRecordBOList);
            resultMap.put("count",count);
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(resultMap));
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






