package com.szq.hotel.test;

import com.alibaba.fastjson.JSON;
import com.szq.hotel.entity.bo.CheckInPersonBO;
import com.szq.hotel.entity.bo.EverydayRoomPriceBO;
import com.szq.hotel.entity.bo.OrderChildBO;
import com.szq.hotel.util.JsonUtils;
import com.szq.hotel.web.controller.OrderController;

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

        CheckInPersonBO checkInPersonBO=new CheckInPersonBO();
        checkInPersonBO.setName("男1");
        checkInPersonBO.setGender("男");
        checkInPersonBO.setPhone("6666");
        checkInPersonBO.setCertificateNumber("66666");
        checkInPersonBO.setRemark("男1的备注");
        CheckInPersonBO checkInPersonBO2=new CheckInPersonBO();
        checkInPersonBO2.setName("女1");
        checkInPersonBO2.setGender("女");
        checkInPersonBO2.setPhone("55555");
        checkInPersonBO2.setCertificateNumber("5555555");
        checkInPersonBO2.setRemark("女1的备注");
        List<CheckInPersonBO> checkInPersonBOS=new ArrayList<CheckInPersonBO>();
        checkInPersonBOS.add(checkInPersonBO);
        checkInPersonBOS.add(checkInPersonBO2);
        childBO.setCheckInPersonBOS(checkInPersonBOS);
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



        CheckInPersonBO checkInPersonBO3=new CheckInPersonBO();
        checkInPersonBO3.setName("男3");
        checkInPersonBO3.setGender("男");
        checkInPersonBO3.setPhone("33333");
        checkInPersonBO3.setCertificateNumber("33333");
        checkInPersonBO3.setRemark("男3的备注");
        CheckInPersonBO checkInPersonBO4=new CheckInPersonBO();
        checkInPersonBO4.setName("女4");
        checkInPersonBO4.setGender("女");
        checkInPersonBO4.setPhone("55555");
        checkInPersonBO4.setCertificateNumber("5555555");
        checkInPersonBO4.setRemark("女4的备注");
        List<CheckInPersonBO> checkInPersonBOS2=new ArrayList<CheckInPersonBO>();
        checkInPersonBOS2.add(checkInPersonBO3);
        checkInPersonBOS2.add(checkInPersonBO4);
        childBO2.setCheckInPersonBOS(checkInPersonBOS2);
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

    public static void main2(String args[]){
        OrderController orderController=new OrderController();
        //System.err.println(orderController.getDate());
    }
}