package com.szq.hotel.service;

import com.szq.hotel.common.constants.Constants;
import com.szq.hotel.dao.HomeDAO;
import com.szq.hotel.entity.bo.*;
import com.szq.hotel.util.DateUtils;
import com.szq.hotel.util.IDBuilder;
import com.szq.hotel.util.JsonUtils;
import com.szq.hotel.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  首页
 */
@Service("HomeService")
@Transactional
public class HomeService {
    private static final Logger log = LoggerFactory.getLogger(HomeService.class);

    @Resource
    private HomeDAO  homeDAO;


    /**
     * 首页查询
     * @param hotelId    酒店id
     * @param vacant     空房
     * @param inthe      在住
     * @param timeout    超时
     * @param dirty      脏房
     * @Param subscribe  预约中
     * @param departure  预离店
     * @param maintain   维修
     * @param shop       门店锁

     */
  //  * @param network    网络锁
    public List<FloorRoomBO> home(HomeTypeBO typeBO,Integer hotelId, String vacant, String inthe, String timeout,
                                  String dirty, String subscribe, String departure, String maintain, String shop,
                                /*  String network,*/ String types){
        log.info("start home.............................................");
        log.info("vacant:{}\tinthe:{}\ttimeout:{}\tdirty:{}\tsubscribe:{}\tdeparture:{}\tmaintain:{}" +
                "\tshop:{}\tnetwork:{}\t",vacant,inthe,timeout,dirty,subscribe,departure,maintain,shop/*,network*/);

        Map<String,Object> paramMap=new HashMap<String, Object>();
        paramMap.put("hotelId",hotelId) ;
        paramMap.put("vacant",vacant);
        paramMap.put("inthe",inthe);
        paramMap.put("timeout",timeout);
        paramMap.put("dirty",dirty);
        paramMap.put("subscribe",subscribe);
        paramMap.put("departure",departure);
        paramMap.put("maintain",maintain);
        paramMap.put("shop",shop);
      /*  paramMap.put("network",network);*/
        if(!StringUtils.isEmpty(types))
             paramMap.put("type",types.split(","));
        paramMap.put("startTime",DateUtils.getThisBrightSiceStringData(new Date()));
        paramMap.put("endTime",DateUtils.getBrightSiceStringData());

        Date startTime=new Date();
        Date endTime= DateUtils. getDataByStr(DateUtils.getBrightSiceStringData());

        log.info("homeDAO.home..................................");
        log.info("param:{}", JsonUtils.getJsonString4JavaPOJO(paramMap));
        List<FloorRoomBO> home = homeDAO.home(paramMap);

        for(int floori=0;floori<home.size();floori++){
            for(int roomi=0;roomi<home.get(floori).getRooms().size();roomi++){
                try {
                    HomeRoomBO homeRoomBO = home.get(floori).getRooms().get(roomi);
                    //判断是否有预约
                    paramMap.put("roomId", homeRoomBO.getRoomId());
                    Integer count = homeDAO.querySubStatus(paramMap);
                    if (count !=null) {
                        homeRoomBO.setMakeStatus(count);
                        typeBO.setSubscribe();
                    }
                    //是否是在住中
                    if (homeRoomBO.getStatus().equals(Constants.INTHE.getValue()) || homeRoomBO.getStatus().equals(Constants.TIMEOUT.getValue())) {
                        HomeOrderBO homeOrderBO = homeDAO.queryChildOrder(paramMap);
                        homeRoomBO.setChildId(homeOrderBO.getId());
                        homeRoomBO.setCheckType(homeOrderBO.getCheckType());
                        //余额不足  押金小于消费
                        if (homeOrderBO.getCash() < homeOrderBO.getTotal()) {
                            homeRoomBO.setBalance(Constants.YES.getValue());
                        }
                        //预离状态
                        if (homeOrderBO.getEndTime().compareTo(startTime) == 1 && endTime.compareTo(homeOrderBO.getEndTime()) == 1) {
                            homeRoomBO.setOutStatus(Constants.YES.getValue());
                        }
                    }
                    //设置状态数量
                    //空房
                    if(homeRoomBO.getStatus().equals(Constants.VACANT.getValue())){
                        typeBO.setVacant();
                    }
                    //入驻中
                    if(homeRoomBO.getStatus().equals(Constants.INTHE.getValue())){
                        typeBO.setInthe();
                    }
                    //超时
                    if(homeRoomBO.getStatus().equals(Constants.TIMEOUT.getValue())){
                        typeBO.setTimeout();
                    }
                    //脏房
                    if(homeRoomBO.getStatus().equals(Constants.DIRTY.getValue())){
                        typeBO.setDirty();
                    }
                    //预约中
                    if(Constants.YES.getValue().equals(homeRoomBO.getMakeStatus())){
                        typeBO.setMaintain();
                    }
                    //预离
                    if(Constants.YES.getValue().equals(homeRoomBO.getOutStatus())){
                        typeBO.setDeparture();
                    }
                    //维修
                    if(Constants.YES.getValue().equals(homeRoomBO.getMaintain())){
                        typeBO.setMaintain();
                    }
                    //门店锁房
                    if(Constants.YES.getValue().equals(homeRoomBO.getLockRoomState())){
                        typeBO.setShop();
                    }
                  /*  //网络锁房
                    if(Constants.NETWORK.getValue().equals(homeRoomBO.getLockRoomState())){
                        typeBO.setNetwork();
                    }*/

                }catch (Exception e){
                    e.printStackTrace();
                    log.error("homeException",e);
                    continue;
                }
            }
        }

        log.info("result:{}",home);
        log.info("end home.............................................");
        return home;
    }


    /**
     * 查询房间数量
     */
    public List<HomeRoomTypeBO> queryRoomTypeNum(Integer hotelId){
        return  homeDAO.queryRoomTypeNum(hotelId);
    }




}