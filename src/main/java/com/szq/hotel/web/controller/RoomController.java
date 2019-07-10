package com.szq.hotel.web.controller;

import com.sun.xml.internal.bind.v2.model.core.ID;
import com.szq.hotel.entity.bo.AdminBO;
import com.szq.hotel.entity.bo.RoomBO;
import com.szq.hotel.entity.bo.RoomTypeCountBO;
import com.szq.hotel.entity.dto.ResultDTOBuilder;
import com.szq.hotel.query.QueryInfo;
import com.szq.hotel.service.RoomService;
import com.szq.hotel.util.JsonUtils;
import com.szq.hotel.util.RedisConnectFactory;
import com.szq.hotel.util.RedisTool;
import com.szq.hotel.web.controller.base.BaseCotroller;
import org.omg.CORBA.OBJ_ADAPTER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.*;


/**
 * @Author: Bin Wang
 * @date: Created in 13:03 2019/7/5
 */
@Controller
@RequestMapping("/room")
public class RoomController extends BaseCotroller {

    static final Logger log = LoggerFactory.getLogger(RoomController.class);

    @Resource
    private RoomService roomService;

    @RequestMapping("/queryRoom")
    public void queryRoom(HttpServletRequest request, HttpServletResponse response, Integer pageNo,
                          Integer pageSize, Integer floorId, Integer roomId, Integer roomTypeId, Integer hotelId) {
        QueryInfo queryInfo = getQueryInfo(pageNo, pageSize);
        //创建一个用于封装sql条件的map集合
        Map<String, Object> condition = new HashMap<String, Object>();
        if (queryInfo != null) {
            //把pageOffset 页数,pageSize每页的条数放入map集合中
            condition.put("pageOffset", queryInfo.getPageOffset());
            condition.put("pageSize", queryInfo.getPageSize());
        }

        condition.put("floorId", floorId);
        condition.put("roomId", roomId);
        condition.put("roomTypeId", roomTypeId);
        condition.put("hotelId", hotelId);

        Map<String, Object> map = roomService.queryRoom(condition);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(map));
        super.safeJsonPrint(response, result);
        return;
    }

    @RequestMapping("/insertSelective")
    public void insertSelective(HttpServletRequest request, HttpServletResponse response, RoomBO record) {
        roomService.insertSelective(record);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("添加成功"));
        super.safeJsonPrint(response, result);
        return;
    }

    @RequestMapping("/updateByPrimaryKeySelective")
    public void updateByPrimaryKeySelective(HttpServletRequest request, HttpServletResponse response, RoomBO record) {
        roomService.updateByPrimaryKeySelective(record);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("修改成功"));
        super.safeJsonPrint(response, result);
        return;
    }

    @RequestMapping("/deleteByPrimaryKey")
    public void deleteByPrimaryKey(HttpServletRequest request, HttpServletResponse response, String byId) {
        Integer[] idArr = JsonUtils.getIntegerArray4Json(byId);
        roomService.deleteByPrimaryKey(idArr);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("删除成功"));
        super.safeJsonPrint(response, result);
        return;
    }

    @RequestMapping("/updatelockRoomClose")
    public void updatelockRoomClose(HttpServletRequest request, HttpServletResponse response, String byId, String startTime,
                                    String endTime, String state, String remark) {
        Integer[] idArr = JsonUtils.getIntegerArray4Json(byId);
        Map<String, Object>  map = new HashMap<String, Object>();
        map.put("state", state);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("remark", remark);
        map.put("ids", idArr);


        roomService.updatelockRoomClose(map);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("锁房成功"));
        super.safeJsonPrint(response, result);
        return;
    }

    @RequestMapping("/updatelockRoomOpen")
    public void updatelockRoomOpen(HttpServletRequest request, HttpServletResponse response, String byId, String state) {
        Map map = new HashMap<String, Object>();
        Integer[] idArr = JsonUtils.getIntegerArray4Json(byId);
        map.put("state", state);
        map.put("ids", idArr);

        roomService.updatelockRoomOpen(map);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("解锁成功"));
        super.safeJsonPrint(response, result);
        return;
    }

    @RequestMapping("/queryRoomTypeCount")
    public void queryRoomTypeCount(HttpServletRequest request, HttpServletResponse response, Integer id) {
        List<RoomTypeCountBO> roomTypeCountBOS = roomService.queryRoomTypeCount(id);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(roomTypeCountBOS));
        super.safeJsonPrint(response, result);
        return;
    }

    @RequestMapping("/updateroomMajorState")
    public void updateroomMajorState(HttpServletRequest request, HttpServletResponse response, Integer id, String state) {
        Map<String, Object> map = new HashMap<String, Object>();
        Jedis jedis = new Jedis();
        UUID requestId = UUID.randomUUID();
        System.err.println(requestId);
        if (!(RedisTool.tryGetDistributedLock(jedis, "500", requestId.toString(), 5000))) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("请重试"));
            super.safeJsonPrint(response, result);
            return;
        }
        map.put("id", id);
        map.put("state", state);
        roomService.updateroomMajorState(map);
        /*try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("操作成功"));
        super.safeJsonPrint(response, result);
        return;
    }


    @RequestMapping("/quertRm")
    public void queryRm(HttpServletRequest request, HttpServletResponse response, String checkTime){
        AdminBO loginUser = super.getLoginUser(request);
        log.info("进入此方法");
        Map map = new HashMap<String,Object>();
        log.info("checkTime:{}",checkTime);
        map.put("checkTime", checkTime);
        map.put("hotelId", loginUser.getHotelId());
        Map mp = null;
        try {
            mp = roomService.queryRm(map);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(mp));
        super.safeJsonPrint(response, result);
        return;
    }
}
