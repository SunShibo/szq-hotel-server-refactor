package com.szq.hotel.web.controller;

import com.szq.hotel.entity.bo.*;
import com.szq.hotel.entity.dto.ResultDTOBuilder;
import com.szq.hotel.query.QueryInfo;
import com.szq.hotel.service.RoomRecordService;
import com.szq.hotel.service.RoomService;
import com.szq.hotel.util.JsonUtils;
import com.szq.hotel.util.RedisTool;
import com.szq.hotel.util.StringUtils;
import com.szq.hotel.web.controller.base.BaseCotroller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    @Resource
    private RoomRecordService roomRecordService;

    @RequestMapping("/queryRoom")
    public void queryRoom(HttpServletRequest request, HttpServletResponse response, Integer pageNo,
                          Integer pageSize, Integer floorId, Integer roomName, Integer roomTypeId, Integer hotelId) {
        AdminBO loginAdmin = super.getLoginAdmin(request);
        QueryInfo queryInfo = getQueryInfo(pageNo, pageSize);
        //创建一个用于封装sql条件的map集合
        Map<String, Object> condition = new HashMap<String, Object>();
        if (queryInfo != null) {
            //把pageOffset 页数,pageSize每页的条数放入map集合中
            condition.put("pageNo", queryInfo.getPageOffset());
            condition.put("pageSize", queryInfo.getPageSize());
        }

        condition.put("floorId", floorId);
        condition.put("roomName", roomName);
        condition.put("roomTypeId", roomTypeId);
        condition.put("hotelId", loginAdmin.getHotelId());

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

    /**
     * 修改房间主状态
     * @param request
     * @param response
     * @param id
     * @param state
     */
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
    public void queryRm(HttpServletRequest request, HttpServletResponse response, String checkTime,
                        String endTime, String roomTypeId, String roomAuxiliaryStatus,
                        String roomAuxiliaryStatusStand){
        AdminBO loginUser = super.getLoginAdmin(request);
        log.info("进入此方法");
        Map<String, Object> map = new HashMap<String,Object>();
        log.info("checkTime:{}",checkTime);
        log.info("endTime:{}",endTime);
        log.info("roomTypeId:{}",roomTypeId);
        log.info("roomAuxiliaryStatus:{}",roomAuxiliaryStatus);
        log.info("roomAuxiliaryStatusStand:{}",roomAuxiliaryStatusStand);
        log.info("hotelId:{}",loginUser.getHotelId());

        map.put("checkTime", checkTime);
        map.put("hotelId", loginUser.getHotelId());
        map.put("endTime", endTime);
        map.put("roomTypeId", roomTypeId);
        map.put("roomAuxiliaryStatus", roomAuxiliaryStatus);
        map.put("roomAuxiliaryStatusStand", roomAuxiliaryStatusStand);


        Map<String, Object> mp = roomService.queryRm(map);

        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(mp));
        super.safeJsonPrint(response, result);
        return;
    }


    @RequestMapping("/queryRoomTypeNum")
    public void queryRoomTypeNum(HttpServletRequest request, HttpServletResponse response, String checkTime,
                        String endTime, String roomTypeId, String roomAuxiliaryStatus,
                        String roomAuxiliaryStatusStand){
        AdminBO loginUser = super.getLoginAdmin(request);
        log.info("进入此方法");
        Map<String, Object> map = new HashMap<String,Object>();
        log.info("checkTime:{}",checkTime);
        log.info("endTime:{}",endTime);
        log.info("roomTypeId:{}",roomTypeId);
        log.info("roomAuxiliaryStatus:{}",roomAuxiliaryStatus);
        log.info("roomAuxiliaryStatusStand:{}",roomAuxiliaryStatusStand);
        log.info("hotelId:{}",loginUser.getHotelId());

        map.put("checkTime", checkTime);
        map.put("hotelId", loginUser.getHotelId());
        map.put("endTime", endTime);
        map.put("roomTypeId", roomTypeId);
        map.put("roomAuxiliaryStatus", roomAuxiliaryStatus);
        map.put("roomAuxiliaryStatusStand", roomAuxiliaryStatusStand);

        Map<String, Object> mp  = roomService.queryRoomTypeNum(map);

        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(mp));
        super.safeJsonPrint(response, result);
        return;

    }


    @RequestMapping("/queryRt")
    public void queryRt(HttpServletRequest request, HttpServletResponse response){
        AdminBO loginUser = super.getLoginAdmin(request);
        log.info("loginUser:{}",loginUser);
        log.info("loginUser:{}",loginUser.getHotelId());
        List<RtBO> list =  roomService.queryRt(loginUser.getHotelId());
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(list));
        super.safeJsonPrint(response, result);
        return;
    }
@RequestMapping("/updateRoomMaintain")
    public void updateRoomState(Integer id,HttpServletRequest request,HttpServletResponse response){
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
        if (id== null) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;
        }
        //执行修改房间是否维修
        roomService.updateRoomState(id,loginAdmin.getId());

        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("修改房间维修状态成功！"));
        super.safeJsonPrint(response, result);
        log.info("result{}",result);
        return;

    }catch (Exception e){
        e.getStackTrace();
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
        super.safeJsonPrint(response, result);
        log.error("updateRoomMaintainException",e);
    }
    }
@RequestMapping("/updateRoomRemark")
   public void updateRoomRemark(Integer id,String remark,HttpServletRequest request,HttpServletResponse response){
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
        if (id== null|| StringUtils.isEmpty(remark)) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;
        }
        //执行修改房间备注
        roomService.updateRoomRemark(id,remark,loginAdmin.getId());

        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("修改房间备注成功！"));
        super.safeJsonPrint(response, result);
        log.info("result{}",result);
        return;

    }catch (Exception e){
        e.getStackTrace();
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
        super.safeJsonPrint(response, result);
        log.error("updateRoomRemarkException",e);
    }
    }

    /**
     * 查询房屋信息
     * @param id
     * @param request
     * @param response
     */
    @RequestMapping("/getRoomMessage")
    public void getRoomMessage(Integer id,HttpServletRequest request,HttpServletResponse response){
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
            if (id== null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }

            RoomMessageBO roomMessageBO = roomService.getRoomMessage(id);
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(roomMessageBO));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;

        }catch (Exception e){
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, result);
            log.error("getRoomMessageException",e);
        }

    }


}
