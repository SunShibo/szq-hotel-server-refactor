package com.szq.hotel.service;

import com.szq.hotel.common.constants.Constants;
import com.szq.hotel.dao.UpdatePriceDAO;
import com.szq.hotel.entity.bo.AddPriceBO;
import com.szq.hotel.entity.bo.EverydayRoomPriceBO;
import com.szq.hotel.entity.bo.MemberDiscountBO;
import com.szq.hotel.entity.bo.RoomTypeBO;
import com.szq.hotel.util.DateUtils;
import com.szq.hotel.util.JsonUtils;
import com.szq.hotel.util.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 *  楼层
 */
@Service("UpdatePriceService")
@Transactional
public class UpdatePriceService {
    private static final Logger log = LoggerFactory.getLogger(UpdatePriceService.class);

    @Resource
    private RoomTypeService roomTypeService;
    @Resource
    private RoomService roomService;
    @Resource
    private UpdatePriceDAO  updatePriceDAO;

    public List<Map<String, Object>> updatePrice(String phone, Date startTime, Integer dayNum, String typeIds, Integer orderId, String checkType,Integer hotelId) {
        log.info("start  updatePrice......................................................................................");
        log.info("phone:{}\tstartTime:{}\tdayNum:{}\ttypeIds:{}\torderId:{}\tcheckType:{}\thotelId:{}\t",phone,startTime,dayNum,typeIds,orderId,checkType,hotelId);
        Date beforeSix = DateUtils.getBeforeSix(startTime);

        String[] split = typeIds.split(",");
        List<Map<String,Object>> list=new ArrayList<Map<String, Object>>();
        //时间选择上一天
        for(int x=0;x<split.length;x++) {
            //查询基础价格
            double basicPrice=0.00;

            List<RoomTypeBO> roomTypeBOS = roomTypeService.queryRoomTypeList(Integer.parseInt(split[x]), hotelId);
            MemberDiscountBO memberDiscountBO = roomService.queryMember(phone);
            if(checkType.equals("day")) {
                if (memberDiscountBO != null && memberDiscountBO.getDiscount()!=null) {
                    basicPrice = roomTypeBOS.get(0).getBasicPrice() * memberDiscountBO.getDiscount();
                }else{
                    basicPrice = roomTypeBOS.get(0).getBasicPrice();
                }
            }else if(checkType.equals("hour")){
                if (memberDiscountBO != null && memberDiscountBO.getDiscount()!=null) {
                    basicPrice=roomTypeBOS.get(0).getHourRoomPrice() * memberDiscountBO.getDiscount();
                }else{
                    basicPrice = roomTypeBOS.get(0).getHourRoomPrice();
                }
            }
            //向上取整 bd.setScale( 0, BigDecimal.ROUND_UP ).longValue()
            basicPrice = new BigDecimal(basicPrice).setScale( 0, BigDecimal.ROUND_UP ).doubleValue();
            log.info("basicPrice:{}",basicPrice);

            //查询数据库中保存的价格
            Map<String, Object> queryPrice = this.queryPrice(orderId, Integer.parseInt(split[x]));
            Map<String, Object> queryChildPrice = this.queryChildPrice(orderId, Integer.parseInt(split[x]));

            log.info("queryPrice:{}", JsonUtils.getJsonString4JavaPOJO(queryPrice));

            Map<String,Object>  map=new HashMap<String, Object>();
            map.put("roomTypeName",roomTypeBOS.get(0).getRoomTypeName());//房型名称
            map.put("price",basicPrice);
            map.put("roomTypeId",roomTypeBOS.get(0).getId());

            for (int i = 0; i < dayNum; i++) {
                String addDate = DateUtils.getAddDate(beforeSix, i);
                map.put("y" + addDate, basicPrice);
                if(queryPrice!=null && queryPrice.get(addDate)!=null) {
                    map.put(addDate, queryPrice.get(addDate));
                    if(i==0){
                        map.put("price",queryPrice.get(addDate));  //第一天有优惠价,覆盖之前的价格
                    }
                 }else if(queryChildPrice!=null && queryChildPrice.get(addDate)!=null) {
                    map.put(addDate, queryChildPrice.get(addDate));
                    if(i==0){
                        map.put("price",queryChildPrice.get(addDate));  //第一天有优惠价,覆盖之前的价格
                    }
                }else{
                    map.put(addDate,basicPrice);
                }
            }
            list.add(map);
        }

        log.info("end  updatePrice......................................................................................");
        return list;
    }

    private Map<String, Object> queryPrice(Integer orderId, Integer i) {

        List<EverydayRoomPriceBO> everydayRoomPriceBOS = updatePriceDAO.queryPrice(orderId, i);
        return everydayRoomPriceBOSTOMap(everydayRoomPriceBOS);
    }

    private Map<String,Object> queryChildPrice(Integer orderId,Integer roomTypeId){
        return everydayRoomPriceBOSTOMap( updatePriceDAO.queryChildPrice(orderId, roomTypeId));
    }


    private Map<String,Object>   everydayRoomPriceBOSTOMap(List<EverydayRoomPriceBO> list){
        Map<String,Object>  map=new HashMap<String, Object>();
        if(list!=null && list.size()>0) {
            for (EverydayRoomPriceBO price : list) {
                map.put(DateUtils.getAddDate(price.getTime(),0),price.getMoney());
            }
        }
        return  map;
    }
    /**
     * 保存改价
     * @param data
     */
    public void addPrice(String data) {
        log.info("start  addPrice......................................................................................");
        log.info("param:{}",data);
        List<AddPriceBO> addPriceBOS = JsonUtils.getJSONtoList(data, AddPriceBO.class);
        for (int i=0;i<addPriceBOS.size();i++){
            AddPriceBO addPriceBO = addPriceBOS.get(i);
            //查询这个订单下的同一个房型的子订单id
            log.info("delePrice...................................................................");
            updatePriceDAO.delePrice(addPriceBO.getRoomTypeId());

            Map<String,Object> paramMap=new HashMap<String, Object>();
            paramMap.put("orderId",addPriceBO.getOrderId());
            paramMap.put("list",addPriceBO.getPriceBOS());
            paramMap.put("roomTypeId",addPriceBO.getRoomTypeId());
            //保存每日房价
            log.info("addPrice.....................................................................");
            updatePriceDAO.addPrice(paramMap);

        }
        log.info("end  addPrice......................................................................................");
    }


    /**
     * 判断一间房不是小时房或不是免费房
     */
    public  String queryCheckType(String checkType,String roomIds){
        log.info("start  queryCheckType......................................................................................");
        log.info("checkType:{}\troomIds:{}",checkType,roomIds);
        //小时房
        if(checkType.equals(Constants.HOUR.getValue())){
            List<String> strings = updatePriceDAO.queryHour(StringUtils.strToList(roomIds));
            if(strings!=null && strings.size()>0){
                return strings.toString()+": 不是钟点房";
            }
        }else if(checkType.equals(Constants.FREE.getValue())){
            List<String> strings = updatePriceDAO.queryFree(StringUtils.strToList(roomIds));
            if(strings!=null && strings.size()>0){
                return strings.toString()+": 不是免费房";
            }
        }
        log.info("end  queryCheckType....................................................................................");
        return  null;

    }

}