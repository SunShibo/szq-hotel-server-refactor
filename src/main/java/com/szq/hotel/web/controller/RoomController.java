package com.szq.hotel.web.controller;

import com.szq.hotel.dao.RoomDAO;
import com.szq.hotel.entity.bo.*;
import com.szq.hotel.entity.dto.ResultDTOBuilder;
import com.szq.hotel.entity.dto.RoomStateDTO;
import com.szq.hotel.query.QueryInfo;
import com.szq.hotel.service.RoomRecordService;
import com.szq.hotel.service.RoomService;
import com.szq.hotel.util.JsonUtils;
import com.szq.hotel.util.RedisTool;
import com.szq.hotel.util.StringUtils;
import com.szq.hotel.web.controller.base.BaseCotroller;
import io.netty.handler.codec.http.HttpObject;
import org.omg.PortableServer.AdapterActivator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import redis.clients.jedis.Builder;
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
                          Integer pageSize, Integer floorId, String roomName, Integer roomTypeId, Integer hotelId) {
        log.info("进入queryRoom****************************************");
        AdminBO loginAdmin = super.getLoginAdmin(request);
        if (loginAdmin == null) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002"));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
            return;
        }
        log.info("AdminBO:{}", loginAdmin);
        QueryInfo queryInfo = getQueryInfo(pageNo, pageSize);
        //创建一个用于封装sql条件的map集合
        Map<String, Object> condition = new HashMap<String, Object>();
        if (queryInfo != null) {
            log.info("进入第一个if");
            //把pageOffset 页数,pageSize每页的条数放入map集合中
            condition.put("pageNo", queryInfo.getPageOffset());
            condition.put("pageSize", queryInfo.getPageSize());
        }
        log.info("参数floorid:{}", floorId);
        log.info("参数roomName:{}", roomName);
        log.info("roomTypeId:{}", roomTypeId);
        log.info("hotelId:{}", loginAdmin.getHotelId());


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
        log.info("insertSelective*****************************************************");
        AdminBO loginAdmin = super.getLoginAdmin(request);
        if (loginAdmin == null) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002"));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
            return;
        }
        log.info("loginAdmin:{}", loginAdmin);
        RoomBO roomBO = roomService.queryRoom(record.getRoomName(), loginAdmin.getHotelId());
        if (roomBO != null) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000202"));
            super.safeJsonPrint(response, result);
            return;
        }
        log.info("record:{}", record);
        record.setHotelId(loginAdmin.getHotelId());
        roomService.insertSelective(record);

        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("添加成功"));
        log.info("return:{}", result);
        super.safeJsonPrint(response, result);
        return;
    }

    @RequestMapping("/updateByPrimaryKeySelective")
    public void updateByPrimaryKeySelective(HttpServletRequest request, HttpServletResponse response, RoomBO record) {
        log.info("updateByPrimaryKeySelective****************************");
        log.info("record:{}", request);
        AdminBO loginAdmin = super.getLoginAdmin(request);
        if (loginAdmin == null) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002"));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
            return;
        }
        roomService.updateByPrimaryKeySelective(record);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("修改成功"));
        super.safeJsonPrint(response, result);
        return;
    }

    @RequestMapping("/deleteByPrimaryKey")
    public void deleteByPrimaryKey(HttpServletRequest request, HttpServletResponse response, String byId) {
        //判断要删除的房型下面的房间是否有在住人员订单
        log.info("deleteByPrimaryKey****************************************************");

        AdminBO loginAdmin = super.getLoginAdmin(request);
        if (loginAdmin == null) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002"));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
            return;
        }

        if (super.getLoginAdmin(request) == null) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002"));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
            return;
        }
        Integer[] idArr = JsonUtils.getIntegerArray4Json(byId);
        roomService.deleteByPrimaryKey(idArr);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("删除成功"));
        super.safeJsonPrint(response, result);
        return;
    }

    @RequestMapping("/updatelockRoomClose")
    public void updatelockRoomClose(HttpServletRequest request, HttpServletResponse response, String byId, String startTime,
                                    String endTime, String state, String remark) {
        log.info("updatelockRoomClose*********************************");
        log.info("byId:{}", byId);
        log.info("startTime:{}", startTime);
        log.info("endTime:{}", endTime);
        log.info("state:{}", state);
        log.info("remark:{}", remark);

        AdminBO loginAdmin = super.getLoginAdmin(request);

        if (loginAdmin == null) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002"));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
            return;
        }
        Integer[] idArr = JsonUtils.getIntegerArray4Json(byId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("state", state);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("remark", remark);
        map.put("ids", idArr);


        roomService.updatelockRoomClose(map);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("锁房成功"));
        log.info("result:{}", result);
        super.safeJsonPrint(response, result);
        return;
    }

    @RequestMapping("/updatelockRoomOpen")
    public void updatelockRoomOpen(HttpServletRequest request, HttpServletResponse response, String byId, String state) {
        log.info("updatelockRoomOpen*******************************");
        log.info("byId:{}", byId);
        log.info("state:{}", state);
        AdminBO loginAdmin = super.getLoginAdmin(request);
        if (loginAdmin == null) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002"));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
            return;
        }

        Map map = new HashMap<String, Object>();
        Integer[] idArr = JsonUtils.getIntegerArray4Json(byId);
        map.put("state", state);
        map.put("ids", idArr);

        roomService.updatelockRoomOpen(map);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("解锁成功"));
        log.info("result:{}", result);
        super.safeJsonPrint(response, result);
        return;
    }

    @RequestMapping("/queryRoomTypeCount")
    public void queryRoomTypeCount(HttpServletRequest request, HttpServletResponse response, Integer id) {
        log.info("queryRoomTypeCount************************************************");
        log.info("id:{}", id);
        AdminBO loginAdmin = super.getLoginAdmin(request);
        if (loginAdmin == null) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002"));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
            return;
        }
        List<RoomTypeCountBO> roomTypeCountBOS = roomService.queryRoomTypeCount(id);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(roomTypeCountBOS));
        log.info("return:{}", request);
        super.safeJsonPrint(response, result);
        return;
    }

    /**
     * 修改房间主状态
     *
     * @param request
     * @param response
     * @param id
     * @param state
     */
    @RequestMapping("/updateroomMajorState")
    public void updateroomMajorState(HttpServletRequest request, HttpServletResponse response, Integer id, String state) {
        log.info("updateroomMajorState**********************************");
        log.info("id:{}", id);
        log.info("state:{}", state);
        AdminBO loginAdmin = super.getLoginAdmin(request);
        log.info("loginAdmin:{}", loginAdmin);
        if (loginAdmin == null) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002"));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
            return;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        Jedis jedis = new Jedis();
        String requestId = request.getSession().getId();
        System.err.println(requestId);
        if (!(RedisTool.tryGetDistributedLock(jedis, "500", requestId, 5000))) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("系统繁忙,请重试"));
            super.safeJsonPrint(response, result);
            return;
        }
        map.put("id", id);
        map.put("state", state);
        roomService.updateroomMajorState(map);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("操作成功"));
        super.safeJsonPrint(response, result);
        RedisTool.releaseDistributedLock(jedis, "500", requestId);
        log.info("return:{}", result);
        return;
    }


    @RequestMapping("/quertRm")
    public void queryRm(HttpServletRequest request, HttpServletResponse response, String checkTime,
                        String endTime, String roomTypeId, String roomAuxiliaryStatus,
                        String phone, String state) {
        log.info("quertRm****************************************");
        AdminBO loginUser = super.getLoginAdmin(request);
        log.info("loginUser:{}", loginUser);
        log.info("checkTime:{}", checkTime);
        log.info("endTime:{}", endTime);
        log.info("roomTypeId:{}", roomTypeId);
        log.info("roomAuxiliaryStatus:{}", roomAuxiliaryStatus);
        log.info("phone:{}", phone);
        log.info("state:{}", state);
        if (loginUser == null) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002"));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
            return;
        }
        if (StringUtils.isEmpty(phone)) {
            System.out.println("进入此方法");
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000200"));
            super.safeHtmlPrint(response, json);
            return;
        }
        if (StringUtils.isEmpty(phone)) {
            System.out.println("进入此方法");
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000201"));
            super.safeHtmlPrint(response, json);
            return;
        }
        log.info("进入此方法");
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isEmpty(roomAuxiliaryStatus)) {
            System.out.println("进入此方法");
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeHtmlPrint(response, json);
            return;
        }
        if ("day".equals(roomAuxiliaryStatus)) {
           /* map.put("roomAuxiliaryStatus", "yes");
            map.put("roomAuxiliaryStatusStand", "yes");*/
        }
        if ("hour".equals(roomAuxiliaryStatus)) {
            map.put("roomAuxiliaryStatus", "yes");
            map.put("roomAuxiliaryStatusStand", "no");
        }
        if ("free".equals(roomAuxiliaryStatus)) {
            map.put("roomAuxiliaryStatus", "no");
            map.put("roomAuxiliaryStatusStand", "yes");
        }
        log.info("checkTime:{}", checkTime);
        log.info("endTime:{}", endTime);
        log.info("roomTypeId:{}", roomTypeId);
        log.info("hotelId:{}", loginUser.getHotelId());
        log.info("phone:{}", phone);

        map.put("checkTime", checkTime);
        map.put("hotelId", loginUser.getHotelId());
        map.put("endTime", endTime);
        map.put("roomTypeId", roomTypeId);
        map.put("phone", phone);

        if ("yes".equals(state)) {
            map.put("roomMajorState", "vacant");
        }


        List<List<RmBO>> lists = roomService.queryRm(map);
        //根据全天房手机号查询预约入住房间

        List<RmBO> rmBOS = roomService.queryUserRoom(loginUser.getHotelId(), phone);
        lists.add(rmBOS);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(lists));
        super.safeJsonPrint(response, result);
        log.info("return:{}", result);
        return;
    }


    @RequestMapping("/queryRoomTypeNum")
    public void queryRoomTypeNum(HttpServletRequest request, HttpServletResponse response, String checkTime,
                                 String endTime, String roomTypeId, String roomAuxiliaryStatus, String phone) {
        log.info("queryRoomTypeNum*********************************************");
        AdminBO loginUser = super.getLoginAdmin(request);
        log.info("loginUser:{}", loginUser);
        log.info("checkTime:{}", checkTime);
        log.info("endTime:{}", endTime);
        log.info("roomTypeId:{}", roomTypeId);
        log.info("roomAuxiliarySttatus:{}", roomAuxiliaryStatus);
        log.info("phone:{}", phone);
        if (StringUtils.isEmpty(phone)) {
            System.out.println("进入此方法");
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000200"));
            super.safeHtmlPrint(response, json);
            return;
        }
        if (StringUtils.isEmpty(phone)) {
            System.out.println("进入此方法");
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000201"));
            super.safeHtmlPrint(response, json);
            return;
        }
        if (StringUtils.isEmpty(roomAuxiliaryStatus)) {
            System.out.println("进入此方法");
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeHtmlPrint(response, json);
            return;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        if ("day".equals(roomAuxiliaryStatus)) {
           /* map.put("roomAuxiliaryStatus", "yes");
            map.put("roomAuxiliaryStatusStand", "yes");*/
        }
        if ("hour".equals(roomAuxiliaryStatus)) {
            map.put("roomAuxiliaryStatus", "yes");
            map.put("roomAuxiliaryStatusStand", "no");
        }
        if ("free".equals(roomAuxiliaryStatus)) {
            map.put("roomAuxiliaryStatus", "no");
            map.put("roomAuxiliaryStatusStand", "yes");
        }
        log.info("进入此方法");

        log.info("checkTime:{}", checkTime);
        log.info("endTime:{}", endTime);
        log.info("roomTypeId:{}", roomTypeId);

        log.info("hotelId:{}", loginUser.getHotelId());
        log.info("phone:{}", phone);

        map.put("checkTime", checkTime);
        map.put("hotelId", loginUser.getHotelId());
        map.put("endTime", endTime);
        map.put("roomTypeId", roomTypeId);
        map.put("hotelId", loginUser.getHotelId());
        map.put("phone", phone);

        List<RoomTypeNumBO> roomTypeNumBOS = roomService.queryRoomTypeNum(map);

        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(roomTypeNumBOS));
        super.safeJsonPrint(response, result);
        log.info("return:{}", request);
        return;

    }


    @RequestMapping("/queryRt")
    public void queryRt(HttpServletRequest request, HttpServletResponse response) {
        log.info("queryRt*************************************");
        AdminBO loginUser = super.getLoginAdmin(request);
        log.info("loginUser:{}", loginUser);
        log.info("loginUser:{}", loginUser);
        log.info("loginUser:{}", loginUser.getHotelId());
        List<RtBO> list = roomService.queryRt(loginUser.getHotelId());
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(list));
        super.safeJsonPrint(response, result);
        log.info("return:{}", result);
        return;
    }

    @RequestMapping("/updateRoomMaintain")
    public void updateRoomState(Integer id, HttpServletRequest request, HttpServletResponse response) {
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
            if (id == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            //执行修改房间是否维修
            roomService.updateRoomState(id, loginAdmin.getId());

            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("修改房间维修状态成功！"));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
            return;

        } catch (Exception e) {
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, result);
            log.error("updateRoomMaintainException", e);
        }
    }

    @RequestMapping("/updateRoomRemark")
    public void updateRoomRemark(Integer id, String remark, HttpServletRequest request, HttpServletResponse response) {
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
            if (id == null || StringUtils.isEmpty(remark)) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            //执行修改房间备注
            roomService.updateRoomRemark(id, remark, loginAdmin.getId());

            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("修改房间备注成功！"));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
            return;

        } catch (Exception e) {
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, result);
            log.error("updateRoomRemarkException", e);
        }
    }

    /**
     * 查询房屋信息
     *
     * @param id
     * @param request
     * @param response
     */
    @RequestMapping("/getRoomMessage")
    public void getRoomMessage(Integer id, HttpServletRequest request, HttpServletResponse response) {
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
            if (id == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }

            RoomMessageBO roomMessageBO = roomService.getRoomMessage(id);
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(roomMessageBO));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
            return;

        } catch (Exception e) {
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, result);
            log.error("getRoomMessageException", e);
        }

    }


    @RequestMapping("/queryIndexRoomState")
    public void queryIndexRoomState(HttpServletRequest request, HttpServletResponse response, Integer id) throws Exception {
        List<RoomStateDTO> list = roomService.queryindexRoomState(id);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(list));
        super.safeJsonPrint(response, result);
        log.info("result{}", result);
        return;
    }


    @RequestMapping("/querySc")
    public void querySc(HttpServletRequest request, HttpServletResponse response, String checkTime, String endTime) {
        log.info("querySc************************************************");
        AdminBO loginAdmin = super.getLoginAdmin(request);
        log.info("loginUser:{}", loginAdmin);
        if (loginAdmin == null) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002"));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
            return;
        }
        if (StringUtils.isEmpty(checkTime)) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
            return;
        }
        if (StringUtils.isEmpty(endTime)) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
            return;
        }
        Map<String, Object> map = roomService.querySc(checkTime, endTime, loginAdmin.getHotelId());
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(map));
        super.safeJsonPrint(response, result);
        log.info("return:{}", result);
        return;
    }


    @RequestMapping("/updatelockRoomState")
    public void updatelockRoomState(HttpServletRequest request, HttpServletResponse response,
                                    String startTime, String endTime,
                                    String roomId, String state, String remark) {
        log.info("updatelockRoomState*************************************");
        log.info("startTime:{}", startTime);
        log.info("endTime:{}", endTime);
        log.info("roomId:{]", roomId);
        log.info("state:{}", state);
        log.info("remark:{}", remark);
        AdminBO loginAdmin = super.getLoginAdmin(request);
        log.info("loginAdmin:{}", loginAdmin);
        if (loginAdmin == null) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002"));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
            return;
        }
        if (roomId == null) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
            return;
        }
        if (state == null) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
            return;
        }
        Integer[] idArr = JsonUtils.getIntegerArray4Json(roomId);
        log.info("adArr:{}", idArr);
        List<Integer> list = Arrays.asList(idArr);
        log.info("list:{}", list);
        List<Integer> arrList = new ArrayList(list);
        log.info("arrList:{}", arrList);
        Jedis jedis = new Jedis();
        UUID requestId = UUID.randomUUID();
        System.err.println(requestId);
        if (!(RedisTool.tryGetDistributedLock(jedis, "500", requestId.toString(), 5000))) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("请重试"));
            super.safeJsonPrint(response, result);
            return;
        }
        //锁房
        if ("close".equals(state)) {
            if (startTime == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                log.info("return:{}", result);
                return;
            }
            if (endTime == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                log.info("return:{}", result);
                return;
            }


            roomService.closeRoom(startTime, endTime, arrList, remark);

        }

        //开锁
        if ("ope".equals(state)) {
            roomService.opeRoom(arrList, remark);
        }
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("操作成功"));
        super.safeJsonPrint(response, result);
        RedisTool.releaseDistributedLock(jedis, "500", requestId.toString());
        log.info("return:{}", result);
        return;
    }


    @RequestMapping("/verificationRoom")
    public void verificationRoom(HttpServletRequest request, HttpServletResponse response,
                                 String ids, String state, String checkTime, String endTime) {
        log.info("verificationRoom**********************************");
        AdminBO loginAdmin = super.getLoginAdmin(request);
        log.info("loginAdmin:{}", loginAdmin);
        log.info("ids:{}", ids);
        if (loginAdmin == null) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002"));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
            return;
        }
        if (StringUtils.isEmpty(ids)) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
            return;
        }
        if (StringUtils.isEmpty(checkTime)) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
            return;
        }
        if (StringUtils.isEmpty(endTime)) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
            return;
        }

        Integer[] idArr = JsonUtils.getIntegerArray4Json(ids);
        log.info("idArr:{}", idArr);
        List<Integer> list = Arrays.asList(idArr);
        List<Integer> arrList = new ArrayList(list);
        log.info("list:{}", arrList);
        log.info("state:{}", state);
        log.info("checkTime:{}", checkTime);
        log.info("endTime:{}", endTime);
        Map<String, Object> map = roomService.verificationRoom(arrList, state, checkTime, endTime, loginAdmin.getHotelId());
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(map));
        super.safeJsonPrint(response, result);
        log.info("return:{}", result);
        return;
    }

    @RequestMapping("/todayPictureView")
    public void todayPictureView(HttpServletRequest request, HttpServletResponse response) {
        log.info("todayPictureView*****************************************************************");
        AdminBO loginAdmin = super.getLoginAdmin(request);
        log.info("loginUser:{}", loginAdmin);
        Map<String, Object> map = roomService.todayPictureView(loginAdmin.getHotelId());
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(map));
        super.safeJsonPrint(response, result);
        log.info("return:{}", result);
        return;
    }

    /**
     * 预约房间反显
     *
     * @param request
     * @param response
     * @param orderId
     */
    @RequestMapping("/queryRoomFx")
    public void queryRoomFx(HttpServletRequest request, HttpServletResponse response, Integer orderId) {
        log.info("queryRoomFx************************************************************************");
        log.info("orderId:{}",orderId);

        AdminBO loginAdmin = super.getLoginAdmin(request);

        log.info("loginAdmin:{}",loginAdmin);

        if (loginAdmin == null) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002"));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
            return;
        }

        if (orderId == null) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
            return;
        }

        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(roomService.queryRoomFx(orderId)));
        super.safeJsonPrint(response, result);
        log.info("return:{}", result);
        return;
    }
}
