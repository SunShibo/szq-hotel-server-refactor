package com.szq.hotel.test;

import com.alibaba.fastjson.JSON;
import com.szq.hotel.entity.bo.CheckInPersonBO;
import com.szq.hotel.entity.bo.EverydayRoomPriceBO;
import com.szq.hotel.entity.bo.OrderChildBO;
import com.szq.hotel.entity.bo.RoomBO;
import com.szq.hotel.util.DateUtils;
import com.szq.hotel.util.JsonUtils;
import com.szq.hotel.web.controller.OrderController;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class test {
    public  static  void main2(String args[]){
        List<OrderChildBO> OrderChildBOList=new ArrayList<OrderChildBO>();//每日房价信息
        OrderChildBO childBO=new OrderChildBO();
        childBO.setRoomId(6);
        childBO.setRoomTypeId(6);
        childBO.setRemark("房型2换为房型6/房间6");
        List<EverydayRoomPriceBO> everydayRoomPriceBOList=new ArrayList<EverydayRoomPriceBO>();
        EverydayRoomPriceBO everydayRoomPriceBO=new EverydayRoomPriceBO();
        everydayRoomPriceBO.setMoney(new BigDecimal(150));
        everydayRoomPriceBO.setTime(new Date());
        everydayRoomPriceBOList.add(everydayRoomPriceBO);
        EverydayRoomPriceBO everydayRoomPriceBO2=new EverydayRoomPriceBO();
        everydayRoomPriceBO2.setMoney(new BigDecimal(150));
        everydayRoomPriceBO2.setTime(new Date());
        everydayRoomPriceBOList.add(everydayRoomPriceBO2);
        EverydayRoomPriceBO everydayRoomPriceBO3=new EverydayRoomPriceBO();
        everydayRoomPriceBO3.setMoney(new BigDecimal(50));
        everydayRoomPriceBO3.setTime(new Date());
        everydayRoomPriceBOList.add(everydayRoomPriceBO3);
        childBO.setEverydayRoomPriceBOS(everydayRoomPriceBOList);

        CheckInPersonBO checkInPersonBO=new CheckInPersonBO();
        checkInPersonBO.setName("王洋");
        checkInPersonBO.setGender("男");
        checkInPersonBO.setPhone("6666");
        checkInPersonBO.setCertificateNumber("66666");
        checkInPersonBO.setRemark("买瓶水");
        CheckInPersonBO checkInPersonBO2=new CheckInPersonBO();
        checkInPersonBO2.setName("张茜");
        checkInPersonBO2.setGender("女");
        checkInPersonBO2.setPhone("55555");
        checkInPersonBO2.setCertificateNumber("5555555");
        checkInPersonBO2.setRemark("我要奶茶");
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
        EverydayRoomPriceBO everydayRoomPriceBO5=new EverydayRoomPriceBO();
        everydayRoomPriceBO5.setMoney(new BigDecimal(100));
        everydayRoomPriceBO5.setTime(new Date());
        everydayRoomPriceBOList2.add(everydayRoomPriceBO5);
        EverydayRoomPriceBO everydayRoomPriceBO4=new EverydayRoomPriceBO();
        everydayRoomPriceBO4.setMoney(new BigDecimal(200));
        everydayRoomPriceBO4.setTime(new Date());
        everydayRoomPriceBOList2.add(everydayRoomPriceBO4);
        EverydayRoomPriceBO everydayRoomPriceBO6=new EverydayRoomPriceBO();
        everydayRoomPriceBO6.setMoney(new BigDecimal(200));
        everydayRoomPriceBO6.setTime(new Date());
        everydayRoomPriceBOList2.add(everydayRoomPriceBO6);
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
        //childBO2.setCheckInPersonBOS(checkInPersonBOS2);
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

    public static void main(String args[]) throws ParseException {
        String OrderChildJSON="[{\"checkInPersonBOS\":[{\"certificateNumber\":\"131031231291657\",\"certificateType\":\"1\",\"gender\":\"男\",\"name\":\"王洋\",\"phone\":\"6666\",\"remark\":\"王洋说要一瓶水\"},{\"certificateNumber\":\"13102312337832\",\"certificateType\":\"1\",\"gender\":\"女\",\"name\":\"茜茜\",\"phone\":\"55555\",\"remark\":\"女1的备注\"}],\"everydayRoomPriceBOS\":[{\"money\":100,\"time\":\"2019-07-19\"},{\"money\":200,\"time\":\"2019-07-20\"}],\"remark\":\"豪华房不需要任何服务，不要打扰\",\"id\":\"171\",\"roomId\":661,\"roomTypeId\":4}]";

        String json="[{\"basicPrice\":320,\"floorId\":1,\"floorName\":\"一楼\",\"hotelId\":1,\"hotelName\":\"上知湫文化驿站(朝阳店)\",\"hourRoomPrice\":40,\"roomMajorState\":\"inthe\",\"roomName\":\"8\",\"roomType\":\"标准间\",\"roomTypeId\":\"标准间\",\"roomId\":\"8\",\"roomTypeName\":\"标准间\",\"roomNum\":\"8\",\"name\":\"321\",\"credentialNo\":\"321\",\"phoneNumber\":\"312\",\"remark\":\"123\",\"checkInPersonBOS\":[{\"name\":\"321\",\"certificateId\":\"1\",\"credentialNo\":\"321\",\"address\":\"123\",\"phoneNumber\":\"312\",\"remark\":\"123\",\"gender\":\"女\",\"roomId\":\"8\",\"certificateNumber\":\"321\",\"phone\":\"312\",\"certificateType\":\"1\"}],\"everydayRoomPriceBOS\":[]}]";
        String josn="[{\"roomTypeId\":\"1\",\"roomId\":\"8\",\"roomTypeName\":\"标准间\",\"roomNum\":\"8\",\"name\":\"321\",\"credentialNo\":\"321\",\"phoneNumber\":\"312\",\"remark\":\"123\",\"checkInPersonBOS\":[{\"name\":\"321\",\"certificateId\":\"1\",\"credentialNo\":\"321\",\"address\":\"123\",\"phoneNumber\":\"312\",\"remark\":\"123\",\"gender\":\"女\",\"roomId\":\"8\",\"certificateNumber\":\"321\",\"phone\":\"312\",\"certificateType\":\"1\"}],\"everydayRoomPriceBOS\":[]}]";
        List<RoomBO> list = JsonUtils.getJSONtoList(josn, RoomBO.class);
        System.out.println(list.size());
    }
}
