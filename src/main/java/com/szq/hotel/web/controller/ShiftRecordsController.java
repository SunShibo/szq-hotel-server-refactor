package com.szq.hotel.web.controller;

import com.szq.hotel.common.constants.Constants;
import com.szq.hotel.common.constants.SysConstants;
import com.szq.hotel.entity.bo.AdminBO;
import com.szq.hotel.entity.bo.ShiftRecordsBO;
import com.szq.hotel.entity.dto.ResultDTOBuilder;
import com.szq.hotel.query.QueryInfo;
import com.szq.hotel.service.ShiftRecordsService;
import com.szq.hotel.util.JsonUtils;
import com.szq.hotel.util.StringUtils;
import com.szq.hotel.util.redisUtils.RedissonHandler;
import com.szq.hotel.web.controller.base.BaseCotroller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *  交班
 */
@Controller
@RequestMapping("/shiftRecords")
public class ShiftRecordsController extends BaseCotroller {
    private static final Logger log = LoggerFactory.getLogger(ShiftRecordsController.class);

    @Resource
    private ShiftRecordsService shiftRecordsService;

    /**
     * 交班
     */
    @RequestMapping("/shifRecord")
    public void addHotel(HttpServletRequest request, HttpServletResponse response, String type) {
        try {
            AdminBO loginAdmin = super.getLoginAdmin(request);
            //验证参数
            if (StringUtils.isEmpty(type)) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }

            ShiftRecordsBO shiftRecordsBO = shiftRecordsService.shifRecord(type, loginAdmin.getId());

            if (Constants.YES.getValue().equals(type)) {
                log.info("start log out .................................");
                //退出登录
                String clientLoginID = super.getClientLoginID(request);
                if (clientLoginID != null) {
                    String key = super.createKey(clientLoginID, SysConstants.CURRENT_LOGIN_USER);
                    //从redis中删除用户信息
                    log.info("start delete key .................................");
                    RedissonHandler.getInstance().delete(key);
                    //删除cookie
                    log.info("start delete 删除cookie .................................");
                    super.removeCookie(request, response, key);
                }
                log.info("end log out .................................");
            }

            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(shiftRecordsBO));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
            return;

        } catch (Exception e) {
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000005"));
            super.safeJsonPrint(response, result);
            log.error("shifRecord", e);
        }

    }


    /**
     * 通过id 查询一条交班记录
     */
    @RequestMapping("/queryDetails")
    public void addHotel(HttpServletRequest request, HttpServletResponse response, Integer id) {
        try {
            //验证参数
            if (id == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }

            ShiftRecordsBO shiftRecordsBO = shiftRecordsService.queryShiftRecordsById(id);

            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(shiftRecordsBO));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
            return;

        } catch (Exception e) {
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000005"));
            super.safeJsonPrint(response, result);
            log.error("queryDetails", e);
        }
    }

    /**
     * 查询交班记录
     */
    @RequestMapping("/queryShiftRecordList")
    public void queryShiftRecordList(HttpServletRequest request, HttpServletResponse response, Integer pageNo, Integer pageSize, Date startTime, Date endTime, String name) {
        try {
            AdminBO loginAdmin = super.getLoginAdmin(request);
            //计算分页
            Map<String, Object> queryMap = new HashMap<String, Object>();
            QueryInfo queryInfo = getQueryInfo(pageNo, pageSize);
            if (queryInfo != null) {
                queryMap.put("pageSize", queryInfo.getPageSize());
                queryMap.put("pageOffset", queryInfo.getPageOffset());
            }
            //入参
            queryMap.put("startTime", startTime);
            queryMap.put("endTime", endTime);
            queryMap.put("name", name);
            queryMap.put("hotelId", loginAdmin.getHotelId());

            List<ShiftRecordsBO> shiftRecordsBOS = shiftRecordsService.queryShiftRecordList(queryMap);
            int count = shiftRecordsService.queryShiftRecordCount(queryMap);
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("data", shiftRecordsBOS);
            resultMap.put("count", count);

            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(resultMap));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
            return;

        } catch (Exception e) {
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000005"));
            super.safeJsonPrint(response, result);
            log.error("queryShiftRecordList", e);
        }
    }


}
