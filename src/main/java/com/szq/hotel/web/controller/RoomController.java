package com.szq.hotel.web.controller;

import com.szq.hotel.entity.bo.RoomBO;
import com.szq.hotel.entity.bo.RoomTypeCountBO;
import com.szq.hotel.entity.dto.ResultDTOBuilder;
import com.szq.hotel.query.QueryInfo;
import com.szq.hotel.service.RoomService;
import com.szq.hotel.util.JsonUtils;
import com.szq.hotel.util.RedisConnectFactory;
import com.szq.hotel.util.RedisTool;
import com.szq.hotel.web.controller.base.BaseCotroller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: Bin Wang
 * @date: Created in 13:03 2019/7/5
 */
@Controller
@RequestMapping("/room")
public class RoomController extends BaseCotroller {



    @Resource
    private RoomService roomService;

    @RequestMapping("/queryRoom")
    public void queryRoom(HttpServletRequest request, HttpServletResponse response, Integer pageNo,
                          Integer pageSize, Integer floorId, Integer roomId, Integer roomTypeId, Integer hotelId){
        QueryInfo queryInfo = getQueryInfo(pageNo, pageSize);
        //创建一个用于封装sql条件的map集合
        Map<String, Object> condition = new HashMap<String, Object>();
        if (queryInfo != null) {
            //把pageOffset 页数,pageSize每页的条数放入map集合中
            condition.put("pageOffset", queryInfo.getPageOffset());
            condition.put("pageSize", queryInfo.getPageSize());
        }

        condition.put("floorId",floorId);
        condition.put("roomId", roomId);
        condition.put("roomTypeId", roomTypeId);
        condition.put("hotelId", hotelId);

        Map<String, Object> map = roomService.queryRoom(condition);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(map)) ;
        super.safeJsonPrint(response, result);
        return;
    }

    @RequestMapping("/insertSelective")
    public void insertSelective(HttpServletRequest request, HttpServletResponse response, RoomBO record){
        roomService.insertSelective(record);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("添加成功")) ;
        super.safeJsonPrint(response, result);
        return;
    }

    @RequestMapping("/updateByPrimaryKeySelective")
    public void updateByPrimaryKeySelective(HttpServletRequest request, HttpServletResponse response, RoomBO record){
        roomService.updateByPrimaryKeySelective(record);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("修改成功")) ;
        super.safeJsonPrint(response, result);
        return;
    }

    @RequestMapping("/deleteByPrimaryKey")
    public void deleteByPrimaryKey(HttpServletRequest request, HttpServletResponse response, String byId){
        Integer[] idArr = JsonUtils.getIntegerArray4Json(byId);
        roomService.deleteByPrimaryKey(idArr);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("删除成功")) ;
        super.safeJsonPrint(response, result);
        return;
    }

    @RequestMapping("/updatelockRoomClose")
    public void updatelockRoomClose(HttpServletRequest request, HttpServletResponse response, String byId){
        Integer[] idArr = JsonUtils.getIntegerArray4Json(byId);
        roomService.updatelockRoomClose(idArr);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("锁房成功")) ;
        super.safeJsonPrint(response, result);
        return;
    }

    @RequestMapping("/updatelockRoomOpen")
    public void updatelockRoomOpen(HttpServletRequest request, HttpServletResponse response, String byId){
        Integer[] idArr = JsonUtils.getIntegerArray4Json(byId);
        roomService.updatelockRoomOpen(idArr);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("解锁成功")) ;
        super.safeJsonPrint(response, result);
        return;
    }

    @RequestMapping("/queryRoomTypeCount")
    public void queryRoomTypeCount(HttpServletRequest request, HttpServletResponse response, Integer id){
        List<RoomTypeCountBO> roomTypeCountBOS = roomService.queryRoomTypeCount(id);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(roomTypeCountBOS)) ;
        super.safeJsonPrint(response, result);
        return;
    }

    @RequestMapping("/updateroomMajorState")
    public void updateroomMajorState(HttpServletRequest request, HttpServletResponse response, Integer id, String state){
        Map<String,Object> map = new HashMap<String, Object>();
       Jedis jedis = new Jedis();

        UUID requestId = UUID.randomUUID();
        System.err.println(requestId);
        if(!(RedisTool.tryGetDistributedLock(jedis,"500",requestId.toString(),5000))){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("请重试")) ;
            super.safeJsonPrint(response, result);
            return;
        }

        map.put("id",id);
        map.put("state", state);
        roomService.updateroomMajorState(map);
        /*try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("操作成功")) ;
            super.safeJsonPrint(response, result);
            return;
    }
}
