package com.szq.hotel.test;

import com.alibaba.fastjson.JSON;
import com.szq.hotel.entity.bo.EverydayRoomPriceBO;
import com.szq.hotel.entity.bo.OrderChildBO;
import com.szq.hotel.util.JsonUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class test {
    public  static  void main(String args[]){
        List<OrderChildBO> OrderChildBOList=new ArrayList<OrderChildBO>();//每日房价信息
        OrderChildBO childBO=new OrderChildBO();
        childBO.setRoomId(1);
        childBO.setRoomTypeId(2);
        childBO.setRemark("豪华房的备注");
        List<EverydayRoomPriceBO> everydayRoomPriceBOList=new ArrayList<EverydayRoomPriceBO>();
        EverydayRoomPriceBO everydayRoomPriceBO=new EverydayRoomPriceBO();
        everydayRoomPriceBO.setMoney(new BigDecimal(100));
        everydayRoomPriceBO.setTime(new Date());
        everydayRoomPriceBOList.add(everydayRoomPriceBO);
        EverydayRoomPriceBO everydayRoomPriceBO2=new EverydayRoomPriceBO();
        everydayRoomPriceBO2.setMoney(new BigDecimal(200));
        everydayRoomPriceBO2.setTime(new Date());
        everydayRoomPriceBOList.add(everydayRoomPriceBO2);
        childBO.setEverydayRoomPriceBOS(everydayRoomPriceBOList);
        OrderChildBOList.add(childBO);

        OrderChildBO childBO2=new OrderChildBO();
        childBO2.setRoomId(2);
        childBO2.setRoomTypeId(2);
        childBO2.setRemark("大床房的备注");
        List<EverydayRoomPriceBO> everydayRoomPriceBOList2=new ArrayList<EverydayRoomPriceBO>();
        EverydayRoomPriceBO everydayRoomPriceBO3=new EverydayRoomPriceBO();
        everydayRoomPriceBO3.setMoney(new BigDecimal(100));
        everydayRoomPriceBO3.setTime(new Date());
        everydayRoomPriceBOList2.add(everydayRoomPriceBO3);
        EverydayRoomPriceBO everydayRoomPriceBO4=new EverydayRoomPriceBO();
        everydayRoomPriceBO4.setMoney(new BigDecimal(200));
        everydayRoomPriceBO4.setTime(new Date());
        everydayRoomPriceBOList2.add(everydayRoomPriceBO4);
        childBO2.setEverydayRoomPriceBOS(everydayRoomPriceBOList2);
        OrderChildBOList.add(childBO2);

        String json= JSON.toJSONString(OrderChildBOList);
        System.err.println(json);

        List<OrderChildBO> list = JsonUtils.getJSONtoList(json, OrderChildBO.class);
        for (OrderChildBO orderChild:list) {
            //添加子订单 返回订单id
           System.err.println( orderChild.getRoomId());
           System.err.println( orderChild.getRoomTypeId());
            //这个房型下的每日价格
            List<EverydayRoomPriceBO> everydayRoomPriceBOLista =orderChild.getEverydayRoomPriceBOS();
            for (EverydayRoomPriceBO everydayRoomPriceBOe:everydayRoomPriceBOLista) {
                System.err.println(everydayRoomPriceBOe.getTime());
                System.err.println(everydayRoomPriceBOe.getMoney());
            }

        }
    }
}
