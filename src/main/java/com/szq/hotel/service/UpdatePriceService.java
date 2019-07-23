package com.szq.hotel.service;

import com.szq.hotel.dao.UpdatePriceDAO;
import com.szq.hotel.entity.bo.AddPriceBO;
import com.szq.hotel.entity.bo.EverydayRoomPriceBO;
import com.szq.hotel.entity.bo.MemberDiscountBO;
import com.szq.hotel.entity.bo.RoomTypeBO;
import com.szq.hotel.util.DateUtils;
import com.szq.hotel.util.JsonUtils;
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
            //四舍五入
            basicPrice = new BigDecimal(basicPrice).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            log.info("basicPrice:{}",basicPrice);

            //查询数据库中保存的价格
            Map<String, Object> queryPrice = this.queryPrice(orderId, Integer.parseInt(split[x]));
            log.info("queryPrice:{}", JsonUtils.getJsonString4JavaPOJO(queryPrice));

            Map<String,Object>  map=new HashMap<String, Object>();
            map.put("roomTypeName",roomTypeBOS.get(0).getRoomTypeName());//房型名称
            map.put("price",roomTypeBOS.get(0).getBasicPrice());
            map.put("roomTypeId",roomTypeBOS.get(0).getId());

            for (int i = 0; i < dayNum; i++) {
                String addDate = DateUtils.getAddDate(beforeSix, i);
                map.put("y" + addDate, basicPrice);
                if(queryPrice!=null && queryPrice.get(addDate)!=null) {
                    map.put(addDate, queryPrice.get(addDate));
                }else{
                    map.put(addDate,basicPrice);
                }
            }
            list.add(map);
        }

        log.info("end  updatePrice......................................................................................");
        return list;
    }

    private Map<String, Object> queryPrice(Integer orderId, int i) {
        Map<String,Object>  map=new HashMap<String, Object>();
        List<EverydayRoomPriceBO> everydayRoomPriceBOS = updatePriceDAO.queryPrice(orderId, i);
        if(everydayRoomPriceBOS!=null && everydayRoomPriceBOS.size()>0) {
            for (EverydayRoomPriceBO price : everydayRoomPriceBOS) {
                map.put(DateUtils.format(price.getTime()),price.getMoney());
            }
        }
        return map;
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
            List<Integer> childId = updatePriceDAO.queryChildId(addPriceBO.getOrderId(), addPriceBO.getRoomTypeId());
            for(Integer ids:childId){
                //删除每日房价
                log.info("delePrice...................................................................");
                updatePriceDAO.delePrice(ids);
                Map<String,Object> paramMap=new HashMap<String, Object>();
                paramMap.put("childId",ids);
                paramMap.put("list",addPriceBO.getPriceBOS());
                //保存每日房价
                log.info("addPrice.....................................................................");
                updatePriceDAO.addPrice(paramMap);
            }
        }

        log.info("end  addPrice......................................................................................");
    }


}