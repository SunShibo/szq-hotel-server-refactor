package com.szq.hotel.service;

import com.szq.hotel.common.constants.Constants;
import com.szq.hotel.dao.CheckInPersonDAO;
import com.szq.hotel.dao.OrderDAO;
import com.szq.hotel.entity.bo.*;
import com.szq.hotel.entity.param.OrderParam;
import com.szq.hotel.util.IDBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service("OrderService")
@Transactional
public class OrderService {

    @Resource
    OrderDAO orderDAO;

    @Resource
    CheckInPersonDAO checkInPersonDAO;

    @Resource
    EverydayRoomPriceService everydayRoomPriceService;

    //添加主订单 返回订单id
    public Integer addOrder(OrderBO orderBO) {
        //生成订单号
        IDBuilder idBuilder = new IDBuilder(10, 10);
        String orderNumber = idBuilder.nextId() + "";
        orderBO.setOrderNumber(orderNumber);
        return orderDAO.addOrder(orderBO);
    }

    //添加子订单
    public Integer addOrderChild(List<OrderChildBO> orderChildBOList, Integer orderId, Integer hotelId) {
        //联房码
        String alRoomCode = UUID.randomUUID().toString();
        for (OrderChildBO orderChild : orderChildBOList) {
            //添加子订单 返回订单id
            orderChild.setOrderId(orderId);//主订单id
            orderChild.setAlRoomCode(alRoomCode);//联房
            orderChild.setOrderState(Constants.RESERVATION.getValue());//状态
            orderDAO.addOrderChild(orderChild);//返回子订单id

            //这个房型下的每日价格
            List<EverydayRoomPriceBO> everydayRoomPriceBOList = orderChild.getEverydayRoomPriceBOS();
            for (EverydayRoomPriceBO everydayRoomPriceBO : everydayRoomPriceBOList) {
                orderChild.setOrderId(orderChild.getId());
                everydayRoomPriceService.addEverydayRoomPrice(everydayRoomPriceBO);
            }

        }
        return 0;
    }

    //预约入住
    public Integer updOrderInfo(List<OrderChildBO> orderChildBOList, OrderBO orderBO) {
        //修改主订单信息
        orderDAO.updOrder(orderBO);
        //删除 旧价格
        //删除旧入住人
        List<OrderChildBO> orderChildBOS = orderDAO.getOrderChildById(orderBO.getId());
        for (OrderChildBO orderChildBO : orderChildBOS) {
            everydayRoomPriceService.delEverydayRoomById(orderChildBO.getId());
            checkInPersonDAO.delCheckInPersonById(orderChildBO.getId());
        }

        //删除 旧子订单以及重新插入新子订单 新价格 入住人的信息
        orderDAO.delOrderChild(orderBO.getId());
        this.addOrderChild(orderChildBOList, orderBO);
        return 0;
    }

    //直接入住

    //添加子订单 入住人 每日价格
    public Integer addOrderChild(List<OrderChildBO> orderChildBOList, OrderBO orderBO) {
        //联房码
        String alRoomCode = UUID.randomUUID().toString();
        for (OrderChildBO orderChild : orderChildBOList) {
            //添加子订单 返回订单id
            orderChild.setOrderId(orderBO.getId());//主订单id
            orderChild.setAlRoomCode(alRoomCode);//联房
            orderChild.setOrderState(Constants.RESERVATION.getValue());//状态
            orderChild.setStartTime(orderBO.getCheckTime());//入住时间
            orderChild.setEndTime(orderBO.getCheckOutTime());//离店时间
            orderDAO.addOrderChild(orderChild);//返回子订单id

            //这个房型下的每日价格
            List<EverydayRoomPriceBO> everydayRoomPriceBOList = orderChild.getEverydayRoomPriceBOS();
            for (EverydayRoomPriceBO everydayRoomPriceBO : everydayRoomPriceBOList) {
                everydayRoomPriceBO.setOrderChildId(orderChild.getId());
                everydayRoomPriceService.addEverydayRoomPrice(everydayRoomPriceBO);
            }
            //这个房间的入住人 预约的时候为null
            List<CheckInPersonBO> checkInPersonBOS = orderChild.getCheckInPersonBOS();
            if (checkInPersonBOS != null) {
                for (CheckInPersonBO person : checkInPersonBOS) {
                    person.setStatus(Constants.CHECKIN.getValue());
                    checkInPersonDAO.addCheckInPerson(person);
                }
            }


        }
        return 0;
    }

    //根据订单id查询子订单 以及子订单房价信息 入住人员信息
    public List<OrderChildBO> getOrderChildById(OrderBO orderBO) {
        //查询子订单信息
        List<OrderChildBO> orderChildBOS = orderDAO.getOrderChildById(orderBO.getId());
        if (orderChildBOS.size() == 0) {
            return null;
        }
        BigDecimal totalPrice = new BigDecimal(0);//总房价
        for (OrderChildBO orderChild : orderChildBOS) {
            //查询房间信息 计算房价
            List<EverydayRoomPriceBO> everydayList =
                    everydayRoomPriceService.getEverydayRoomById(orderChild.getId());
            BigDecimal money = new BigDecimal(0);
            for (EverydayRoomPriceBO every : everydayList) {
                money = money.add(every.getMoney());
            }
            //每个房间的房价
            orderChild.setRoomRate(money);
            orderChild.setEverydayRoomPriceBOS(everydayList);

            //总房价信息
            totalPrice = totalPrice.add(money);

            //查询入住人员信息
            List<CheckInPersonBO> checkInPersonBOS = checkInPersonDAO.getCheckInPersonById(orderChild.getId());
            orderChild.setCheckInPersonBOS(checkInPersonBOS);

        }
        //总房价
        orderBO.setTotalPrice(totalPrice);
        return orderChildBOS;
    }

    //根据身份证号 手机号查询预约信息
    public OrderBO getOrderInfo(String idNumber, String mobile) {
        String date = this.getDate();
        //主订单信息
        OrderBO orderBO = orderDAO.getOrderByIdOrMobile(idNumber, mobile, date);
        if (orderBO == null || orderBO.getOrderNumber() == null) {
            return null;
        }
        //添加子订单信息
        List<OrderChildBO> orderChildBOS = this.getOrderChildById(orderBO);
        orderBO.setOrderChildBOS(orderChildBOS);
        return orderBO;
    }

    //修改子订单
    public Integer updOrderChild(OrderChildBO orderChildBO) {
        return orderDAO.updOrderChild(orderChildBO);
    }

    //返回当天最早时间 也就是当天6点后要多算一天
    public String getDate() {
        SimpleDateFormat simp = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        //获取当前小时
        int hour = c.get(Calendar.HOUR_OF_DAY);
        if (hour < 6) {
            //当前在六点之前
            c.set(Calendar.DATE, c.get(Calendar.DATE) - 1);
            String str = simp.format(c.getTime());
            return str;
        } else {
            c.set(Calendar.DATE, c.get(Calendar.DATE) + 1);
            String str = simp.format(c.getTime());
            return str + " 06:00:00";
        }
    }


    /**
     * 订单列表
     *
     * @param param
     * @return
     */
    public List<OrderListBO> queryOrderList(OrderParam param) {
        List<OrderListBO> orderListBOS = orderDAO.queryOrderList(param);
        //每单首天房价
        if (orderListBOS != null && orderListBOS.size() > 0) {
            for (int i = 0, size = orderListBOS.size(); i < size; i++) {
                OrderListBO orderListBO = orderListBOS.get(i);
                orderListBO.setUnitPrice(orderDAO.queryUnitPrice(orderListBO.getId()));
            }
        }
        return orderListBOS;
    }

}
