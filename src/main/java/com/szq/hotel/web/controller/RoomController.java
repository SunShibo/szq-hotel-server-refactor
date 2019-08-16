package com.szq.hotel.web.controller;

import com.szq.hotel.dao.RoomDAO;
import com.szq.hotel.dao.RoomRecordDAO;
import com.szq.hotel.entity.bo.*;
import com.szq.hotel.entity.dto.DateRoomDTO;
import com.szq.hotel.entity.dto.ResultDTOBuilder;
import com.szq.hotel.entity.dto.RoomStateDTO;
import com.szq.hotel.query.QueryInfo;
import com.szq.hotel.service.RoomExcelService;
import com.szq.hotel.service.RoomRecordService;
import com.szq.hotel.service.RoomService;
import com.szq.hotel.util.*;
import com.szq.hotel.web.controller.base.BaseCotroller;
import io.netty.handler.codec.http.HttpObject;
import jdk.nashorn.internal.runtime.linker.LinkerCallSite;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.omg.PortableServer.AdapterActivator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private RoomDAO roomDao;
    @Resource
    private RoomRecordDAO roomRecordDAO;
    @Resource
    private RoomExcelService roomExcelService;

    @RequestMapping("/roomExcel")
    public void roomExcel(HttpServletRequest request, HttpServletResponse response){
        AdminBO loginAdmin = super.getLoginAdmin(request);
        log.info("user{}", loginAdmin);
        if (loginAdmin == null) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002"));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
            return;
        }
        ServletOutputStream out = null;
        try {
             out=response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            SimpleDateFormat formatter   =   new   SimpleDateFormat   ("yyyyMMddHHmmss");
            Date curDate   =   new   Date(System.currentTimeMillis());//获取当前时间
            String   str   =   formatter.format(curDate);
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode("客房信息" +str+ ".xls", "UTF-8"));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        String[] titles = { "序号","楼栋", "楼层", "房型","房态", "维修状态" ,"锁房状态","锁房开始时间","锁房结束时间","备注"};
        try {
            roomExcelService.export(titles,out,loginAdmin.getHotelId());
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("导出成功！"));
            safeTextPrint(response, json);
            return ;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/queryRoom")
    public void queryRoom(HttpServletRequest request, HttpServletResponse response, Integer pageNo,
                          Integer pageSize, Integer floorId, String roomName, Integer roomTypeId, Integer hotelId, Integer roomId) {
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

        condition.put("floorId", floorId);
        condition.put("roomName", roomName);
        condition.put("roomTypeId", roomTypeId);
        condition.put("hotelId", loginAdmin.getHotelId());
        condition.put("roomId", roomId);

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
        record.setRoomMajorState("vacant");
        record.setRoomState("no");
        roomService.insertSelective(record);

        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("添加成功"));
        log.info("return:{}", result);
        super.safeJsonPrint(response, result);
        return;
    }

    @RequestMapping("/updateByPrimaryKeySelective")
    public void updateByPrimaryKeySelective(HttpServletRequest request, HttpServletResponse response, RoomBO record) {
        log.info("updateByPrimaryKeySelective****************************");
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
        List<RoomBO> roomBOS = roomService.selecrState(idArr);
        if(!CollectionUtils.isEmpty(roomBOS)){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000300","房间状态不可删除"));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
            return;
        }
        roomService.deleteByPrimaryKey(idArr);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("删除成功"));
        super.safeJsonPrint(response, result);
        return;
    }

    @RequestMapping("/updatelockRoomClose")
    public void updatelockRoomClose(HttpServletRequest request, HttpServletResponse response, String byId, String startTime,
                                    String endTime, String state, String remark) {
        log.info("updatelockRoomClose*********************************");
        AdminBO loginAdmin = super.getLoginAdmin(request);

        if (loginAdmin == null) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002"));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
            return;
        }
        Integer[] idArr = JsonUtils.getIntegerArray4Json(byId);

        for (int i = 0 ; i < idArr.length; i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("state", state);
            map.put("startTime", startTime);
            map.put("endTime", endTime);
            map.put("remark", remark);
            map.put("ids", idArr[i]);
            map.put("userId", loginAdmin.getId());
            roomService.updatelockRoomClose(map);
        }

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

        Integer[] idArr = JsonUtils.getIntegerArray4Json(byId);

        for (int i = 0 ; i < idArr.length; i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("state", state);
            map.put("ids", idArr[i]);
            map.put("userId", loginAdmin.getId());
            roomService.updatelockRoomOpen(map);
        }

        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("解锁成功"));
        log.info("result:{}", result);
        super.safeJsonPrint(response, result);
        return;
    }

    @RequestMapping("/queryRoomTypeCount")
    public void queryRoomTypeCount(HttpServletRequest request, HttpServletResponse response, Integer id) {
        log.info("queryRoomTypeCount************************************************");
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
    public void updateroomMajorState(HttpServletRequest request, HttpServletResponse response, Integer id, String state, String remark) {
        log.info("updateroomMajorState**********************************");
        AdminBO loginAdmin = super.getLoginAdmin(request);
        if (loginAdmin == null) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002"));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
            return;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        Jedis jedis = RedisConnectFactory.getJedis();
        String requestId = request.getSession().getId();
        System.err.println(requestId);
        if (!(RedisTool.tryGetDistributedLock(jedis, "500", requestId, 5000))) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("系统繁忙,请重试"));
            super.safeJsonPrint(response, result);
            return;
        }
        map.put("id", id);
        map.put("state", state);
        map.put("userId", loginAdmin.getId());
        map.put("remark", remark);
        roomService.updateroomMajorState(map);


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
        log.info("start***************************************quertRm****************************************");
        AdminBO loginUser = super.getLoginAdmin(request);
        String startTime = checkTime.replaceAll("/", "-");
        String enTime =  endTime.replaceAll("/", "-");
        if (loginUser == null) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002"));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
            return;
        }
        if (StringUtils.isEmpty(phone)) {
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000201"));
            super.safeHtmlPrint(response, json);
            return;
        }

        int i = DateUtils.parseDate(checkTime,"yyyy/MM/dd HH:mm:ss").compareTo(DateUtils.parseDate(endTime,"yyyy/MM/dd HH:mm:ss"));
        System.err.println("开始结束时间标识:"+i);
        if(i != -1 ){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000302"));
            super.safeHtmlPrint(response, json);
            return;
        }

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

        }
        if ("free".equals(roomAuxiliaryStatus)) {
            map.put("roomAuxiliaryStatusStand", "yes");
        }

        map.put("checkTime", startTime);
        map.put("hotelId", loginUser.getHotelId());
        map.put("endTime", enTime);
        map.put("roomTypeId", roomTypeId);
        map.put("phone", phone);


        String format = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
        String check = checkTime.substring(0,10);
        boolean equals = format.equals(check);

        if ("yes".equals(state) || equals ) {
            map.put("roomMajorState", "vacant");
        }

        List<List<RmBO>> lists = roomService.queryRm(map);
        //根据全天房手机号查询预约入住房间
        List<RmBO> rmBOS = roomService.queryUserRoom(loginUser.getHotelId(), phone);
        lists.add(rmBOS);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(lists));
        super.safeJsonPrint(response, result);
        return;
    }



    /**
     * 公共 拆分时间段方法
     * @param checkTime
     * @param endTime
     * @param state
     * @return
     */
    private List<Time> isPublicTime(Date checkTime, Date endTime, String state){
        //获取checkTime 14:00:00
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(checkTime);
        String format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(endTime);
        String sub = format.substring(0, 10);
        String su =  sub + " 14:00:00";

        //开始时间当天14点
        Date date = DateUtils.parseDate(su, "yyyy-MM-dd HH:mm:ss");

        List<Time> list = new ArrayList<Time>();

        if("hour".equals(state)){
            Time time = new Time();
            time.setStartTime(checkTime);
            time.setEndTime(endTime);
            list.add(time);
        } else {
            int i = date.compareTo(checkTime);
            System.err.println("i"+i);
            if(i == 1){
                //开始时间获取上一天
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(checkTime);
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                checkTime = calendar.getTime();
                String format1 = new SimpleDateFormat("yyyy-MM-dd").format(checkTime);
                String date1 = format1 + " 14:00:00";
                List<String> strings = roomService.querSeTime(DateUtils.parseDate(date1, "yyyy-MM-dd HH:mm:ss"), DateUtils.parseDate(format2, "yyyy-MM-dd HH:mm:ss"));
                list = roomService.timeDate2(DateUtils.parseDate(date1, "yyyy-MM-dd HH:mm:ss"),
                        DateUtils.parseDate(format2, "yyyy-MM-dd HH:mm:ss"),
                        strings.size()-1 );
            } else {
                List<String> strings = roomService.querSeTime(DateUtils.parseDate(su, "yyyy-MM-dd HH:mm:ss"), DateUtils.parseDate(format2, "yyyy-MM-dd HH:mm:ss"));
                list = roomService.timeDate2(DateUtils.parseDate(su, "yyyy-MM-dd HH:mm:ss"),
                        DateUtils.parseDate(format2, "yyyy-MM-dd HH:mm:ss"),
                        strings.size()-1);
            }
        }
        return list;
    }

    @RequestMapping("/queryRoomTypeNum")
    public void queryRoomTypeNum(HttpServletRequest request, HttpServletResponse response, String checkTime,
                                 String endTime, String roomTypeId, String roomAuxiliaryStatus, String phone, String state) {
       checkTime = checkTime.replaceAll("/", "-");
       endTime = endTime.replaceAll("/", "-");
       log.info("queryRoomTypeNum*********************************************");
       AdminBO loginUser = super.getLoginAdmin(request);

        System.err.println("roomAuxiliaryStatus:"+roomAuxiliaryStatus);

        if (StringUtils.isEmpty(phone)) {
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000200"));
            super.safeHtmlPrint(response, json);
            return;
        }
        if (StringUtils.isEmpty(phone)) {
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000201"));
            super.safeHtmlPrint(response, json);
            return;
        }
        if (StringUtils.isEmpty(roomAuxiliaryStatus)) {
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeHtmlPrint(response, json);
            return;
        }

        int i = DateUtils.parseDate(checkTime,"yyyy-MM-dd HH:mm:ss").compareTo(DateUtils.parseDate(endTime,"yyyy-MM-dd HH:mm:ss"));
        System.err.println("开始结束时间标识:"+i);
        if(i != -1 ){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000302"));
            super.safeHtmlPrint(response, json);
            return;
        }

        List<Time> times = isPublicTime(DateUtils.parseDate(checkTime, "yyyy-MM-dd HH:mm:ss"), DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm:ss"), roomAuxiliaryStatus);

        Map<String, Object> map = new HashMap<String, Object>();
        if ("day".equals(roomAuxiliaryStatus)) {
           /* map.put("roomAuxiliaryStatus", "yes");
            map.put("roomAuxiliaryStatusStand", "yes");*/
        }
        if ("hour".equals(roomAuxiliaryStatus)) {
            map.put("roomAuxiliaryStatus", "yes");

        }
        if ("free".equals(roomAuxiliaryStatus)) {
            map.put("roomAuxiliaryStatusStand", "yes");
        }

        map.put("hotelId", loginUser.getHotelId());
        map.put("roomTypeId", roomTypeId);
        map.put("hotelId", loginUser.getHotelId());
        map.put("phone", phone);

        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String check = checkTime.substring(0,10);
        boolean equals = format.equals(check);
        System.err.println("format:"+format);
        System.err.println("check:"+check);
        System.err.println("equals:"+equals);


        if ("yes".equals(state) || equals ) {
            map.put("roomMajorState", "vacant");
        }

        log.info("times:{}",times);
        List<RoomTypeNumBO> l = new ArrayList<RoomTypeNumBO>();
        System.err.println("times"+times);
        List<RtBO> rtBOS = roomDao.queryRt(loginUser.getHotelId());
        for (RtBO rtBO : rtBOS) {
            //存放同一房型不同时间段可用数量的集合
            List<RoomTypeNumBO> list = new ArrayList<RoomTypeNumBO>();
            for (Time time : times) {
                map.put("checkTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time.getStartTime()));
                map.put("endTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time.getEndTime()));
                //同一时间段内同一房型的可用数量
                RoomTypeNumBO roomTypeNumBO = roomService.queryRoomTypeNum2(map, rtBO);
                log.info("同一时间段内同一房型的可用数量:{}",roomTypeNumBO);
                list.add(roomTypeNumBO);
            }
            System.err.println(list);
            RoomTypeNumBO min = Collections.min(list);
            System.err.println("最小值是:{}"+min);
            l.add(min);
        }
        System.err.println(rtBOS);

       /* List<Integer> integers = roomService.queryRoomTypeAndId(loginUser.getHotelId(), phone);
        System.out.println();
        for (RoomTypeNumBO roomTypeNumBO : l) {
            for (Integer integer : integers) {
                if(roomTypeNumBO.getId().equals(integer)){
                    roomTypeNumBO.setCount(roomTypeNumBO.getCount()+1);
                }
            }
        }*/

        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(l));
        super.safeJsonPrint(response, result);
        log.info("return:{}", request);
        return;
    }


    @RequestMapping("/queryRt")
    public void queryRt(HttpServletRequest request, HttpServletResponse response) {
        log.info("queryRt*************************************");
        AdminBO loginUser = super.getLoginAdmin(request);
        List<RtBO> list = roomService.queryRt(loginUser.getHotelId());
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(list));
        super.safeJsonPrint(response, result);
        log.info("return:{}", result);
        return;
    }

    /**
     * 修改维修房
     * @param id
     * @param request
     * @param response
     */
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

        checkTime = checkTime.replaceAll("/", "-");
        endTime = endTime.replaceAll("/", "-");
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
        Map<String, Object> map = roomService.querySc2(checkTime, endTime, loginAdmin.getHotelId());
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(map));
        super.safeJsonPrint(response, result);
        log.info("return:{}", result);
        return;
    }


    @RequestMapping("/querySs")
    public void querySs(HttpServletRequest request, HttpServletResponse response, String checkTime, String endTime) {
        log.info("querySs************************************************");
        AdminBO loginAdmin = super.getLoginAdmin(request);
        checkTime = checkTime.replaceAll("/","-");
        endTime = endTime.replaceAll("/","-");
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
        List<List<DateRoomDTO>> lists = roomService.querySs2(checkTime, endTime, loginAdmin.getHotelId());
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(lists));
        super.safeJsonPrint(response, result);
        log.info("return:{}", result);
        return;
    }


    @RequestMapping("/updatelockRoomState")
    public void updatelockRoomState(HttpServletRequest request, HttpServletResponse response,
                                    String startTime, String endTime,
                                    String roomId, String state, String remark) {
        log.info("updatelockRoomState*************************************");
        AdminBO loginAdmin = super.getLoginAdmin(request);
        if (loginAdmin == null) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002"));
            super.safeJsonPrint(response, result);
            return;
        }
        if (roomId == null) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, result);
            return;
        }
        if (state == null) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, result);
            return;
        }
        Integer[] idArr = JsonUtils.getIntegerArray4Json(roomId);

        List<Integer> list = Arrays.asList(idArr);
        List<Integer> arrList = new ArrayList(list);

        Jedis jedis = RedisConnectFactory.getJedis();
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
                return;
            }
            if (endTime == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                return;
            }
            for (Integer integer : arrList) {
                RoomBO roomBO = roomDao.selectByPrimaryKey(integer);
                Map<String, Object> mp = new HashMap<String, Object>();
                mp.put("createTime", new Date());
                mp.put("createUserId",loginAdmin.getId());
                mp.put("roomId", roomBO.getId());
                mp.put("virginState", roomBO.getRoomMajorState());
                mp.put("newState",  roomBO.getRoomMajorState());
                mp.put("remark", remark);
                roomRecordDAO.insertRoomState(mp);
                roomService.closeRoom(startTime, endTime, integer, remark);
            }
        }

        //开锁
        if ("ope".equals(state)) {
            for (Integer integer : arrList) {
                RoomBO roomBO = roomDao.selectByPrimaryKey(integer);
                Map<String, Object> mp = new HashMap<String, Object>();
                mp.put("createTime", new Date());
                mp.put("createUserId",loginAdmin.getId());
                mp.put("roomId", roomBO.getId());
                mp.put("virginState", roomBO.getRoomMajorState());
                mp.put("newState",  roomBO.getRoomMajorState());
                mp.put("remark", remark);
                roomRecordDAO.insertRoomState(mp);
                roomService.opeRoom(arrList, remark);
            }
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

    /**
     * 首页侧边栏
     * @param request
     * @param response
     */
    @RequestMapping("/todayPictureView")
    public void todayPictureView(HttpServletRequest request, HttpServletResponse response) {
        log.info("todayPictureView*****************************************************************");
        AdminBO loginAdmin = super.getLoginAdmin(request);
        if(loginAdmin == null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002"));
            super.safeJsonPrint(response, result);
            return;
        }
        Map<String, Object> map = roomService.todayPictureView(loginAdmin.getHotelId());
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(map));
        super.safeJsonPrint(response, result);
        return;
    }

    /**
     * 预约房间反显
     * @param request
     * @param response
     * @param orderId
     */
    @RequestMapping("/queryRoomFx")
    public void queryRoomFx(HttpServletRequest request, HttpServletResponse response, Integer orderId) {
        log.info("queryRoomFx************************************************************************");
        log.info("orderId:{}", orderId);

        AdminBO loginAdmin = super.getLoginAdmin(request);

        log.info("loginAdmin:{}", loginAdmin);

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


    @RequestMapping("/test")
    public void test() throws Exception {
        boolean date = roomService.isDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2000-01-01 06:00:00"), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2000-01-02 06:00:00"),
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2000-01-02 06:00:00"),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2000-01-03 05:59:59"));
        System.out.println(date);

    }


    @RequestMapping("/selectRoom")
    public void selectRoom(HttpServletRequest request, HttpServletResponse response, Integer id){
        AdminBO loginAdmin = super.getLoginAdmin(request);
        log.info("loginAdmin:{}", loginAdmin);
        log.info("id:{}", id);
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
        RmBO rmBO = roomService.selectRoom(id, loginAdmin.getHotelId());
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(rmBO));
        super.safeJsonPrint(response, result);
        log.info("return:{}", result);
        return;
    }
}