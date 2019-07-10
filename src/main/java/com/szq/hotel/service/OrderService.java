package com.szq.hotel.service;

import com.szq.hotel.common.constants.Constants;
import com.szq.hotel.dao.CheckInPersonDAO;
import com.szq.hotel.dao.EverydayRoomPriceDAO;
import com.szq.hotel.dao.OrderDAO;
import com.szq.hotel.entity.bo.*;
import com.szq.hotel.entity.param.OrderParam;
import com.szq.hotel.util.IDBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("OrderService")
@Transactional
public class OrderService {

    @Resource
    OrderDAO orderDAO;

    @Resource
    CheckInPersonDAO checkInPersonDAO;

    @Resource
    EverydayRoomPriceDAO everydayRoomPriceDAO;

    @Resource
    RoomService roomService;

    //添加主订单 携带订单id
    public void addOrder(OrderBO orderBO) {
        //生成订单号
        IDBuilder idBuilder = new IDBuilder(10, 10);
        String orderNumber = idBuilder.nextId() + "";
        orderBO.setOrderNumber(orderNumber);
        orderDAO.addOrder(orderBO);
    }

    //预定入住
    public void updOrderInfo(List<OrderChildBO> orderChildBOList, OrderBO orderBO) {
        //修改主订单信息
        orderDAO.updOrder(orderBO);
        //修改子订单信息
        List<OrderChildBO> orderChildBOS = orderDAO.getOrderChildById(orderBO.getId());//旧的子订单信息
        for (OrderChildBO orderChildBONew : orderChildBOList) {
            if(orderChildBONew.getId()==null){
                //新选的房间或者修改了房间的房型 id传过来为null 代表子订单
                orderDAO.addOrderChild(orderChildBONew);
                List<CheckInPersonBO> checkInPersonBOS=orderChildBONew.getCheckInPersonBOS();
                List<EverydayRoomPriceBO> everydayRoomPriceBOList=orderChildBONew.getEverydayRoomPriceBOS();
                if(checkInPersonBOS!=null){
                    for (CheckInPersonBO person:checkInPersonBOS) {
                        checkInPersonDAO.addCheckInPerson(person);
                    }
                }
                if(everydayRoomPriceBOList!=null){
                    for (EverydayRoomPriceBO roomPrice:everydayRoomPriceBOList) {
                        everydayRoomPriceDAO.addEverydayRoomPrice(roomPrice);
                    }
                }
                //房间状态修改为在住状态
                Map<String,Object> map = new HashMap<String, Object>();
                map.put("id",orderChildBONew.getRoomId());
                map.put("state", Constants.VACANT.getValue());
                roomService.updateroomMajorState(map);
                continue;
            }
            for (OrderChildBO orderChildOld : orderChildBOS) {
                if(orderChildOld.getId()!=orderChildBONew.getId()){
                    //原本预定的被删除了，或者因为修改了房型，id没有传过来 代表已经取消
                    OrderChildBO orderChildCancel=new OrderChildBO();
                    orderChildCancel.setId(orderChildOld.getId());
                    orderChildCancel.setOrderState(Constants.CANCEL.getValue());
                    orderDAO.updOrderChild(orderChildCancel);
                    //删除那个房间的入住人
                    checkInPersonDAO.delCheckInPersonById(orderChildOld.getId());
                    //房间状态修改为空房 还是脏房
                    Map<String,Object> map = new HashMap<String, Object>();
                    map.put("id",orderChildBONew.getRoomId());
                    map.put("state", Constants.VACANT.getValue());
                    roomService.updateroomMajorState(map);
                    continue;
                }
                if(orderChildOld.getId()==orderChildBONew.getId()){
                    //原本的子订单id和新传过来的子订单id一样 代表没有换房间房型 还作为同一个子订单 修改一边其他信息
                    orderDAO.updOrderChild(orderChildBONew);
                    //修改房价信息
                    List<EverydayRoomPriceBO> everydayRoomPriceBOList=orderChildBONew.getEverydayRoomPriceBOS();
                    if(everydayRoomPriceBOList!=null){
                        for (EverydayRoomPriceBO roomPrice:everydayRoomPriceBOList) {
                            everydayRoomPriceDAO.updEverydayRoomPrice(roomPrice);
                        }
                    }
                    //添加一遍入住人
                    List<CheckInPersonBO> checkInPersonBOS=orderChildBONew.getCheckInPersonBOS();
                    if(checkInPersonBOS!=null){
                        for (CheckInPersonBO person:checkInPersonBOS) {
                            checkInPersonDAO.addCheckInPerson(person);
                        }
                    }
                    continue;
                }
            }
        }

        //传给我json的房间时把子订单id也传给我
        //原本预定的子订单会有订单id，我就可以把这个子订单信息全都修改一边
        //原本预定的子订单被删除了，把状态改变为已关闭
        //新增的子订单没有订单id，重新添加子订单
    }

    //添加子订单 入住人 每日价格
    public String addOrderAllInfo(List<OrderChildBO> orderChildBOList, OrderBO orderBO) {
        //联房码
        String alRoomCode = UUID.randomUUID().toString();

        //总房价
        BigDecimal totalPrice = new BigDecimal(0);

        for (OrderChildBO orderChild : orderChildBOList) {
            //添加子订单 返回订单id
            orderChild.setOrderId(orderBO.getId());//主订单id
            orderChild.setAlRoomCode(alRoomCode);//联房
            //预约状态
            if(orderChild.getCheckInPersonBOS()==null||orderChild.getCheckInPersonBOS().size()==0){
                orderChild.setOrderState(Constants.RESERVATION.getValue());//状态
            }else {
                //入住待支付状态
                orderChild.setOrderState(Constants.NOTPAY.getValue());//状态
            }
            orderChild.setStartTime(orderBO.getCheckTime());//入住时间
            orderChild.setEndTime(orderBO.getCheckOutTime());//离店时间

            orderDAO.addOrderChild(orderChild);//返回子订单id

            //这个房型下的每日价格
            List<EverydayRoomPriceBO> everydayRoomPriceBOList = orderChild.getEverydayRoomPriceBOS();
            if(everydayRoomPriceBOList != null && everydayRoomPriceBOList.size()!=0){
                for (EverydayRoomPriceBO everydayRoomPriceBO : everydayRoomPriceBOList) {
                    everydayRoomPriceBO.setOrderChildId(orderChild.getId());
                    everydayRoomPriceDAO.addEverydayRoomPrice(everydayRoomPriceBO);

                    //叠加总房价
                    totalPrice=totalPrice.add(everydayRoomPriceBO.getMoney());
                }
            }
            //这个房间的入住人 预约的时候为null 或者稍后入住为null
            List<CheckInPersonBO> checkInPersonBOS = orderChild.getCheckInPersonBOS();
            if (checkInPersonBOS != null && checkInPersonBOS.size()!=0) {
                //房间状态修改为在住状态
                Map<String,Object> map = new HashMap<String, Object>();
                map.put("id",orderChild.getRoomId());
                map.put("state", Constants.VACANT.getValue());
                roomService.updateroomMajorState(map);
                for (CheckInPersonBO person : checkInPersonBOS) {
                    person.setOrderChildId(orderChild.getId());
                    person.setStatus(Constants.CHECKIN.getValue());
                    checkInPersonDAO.addCheckInPerson(person);
                }
            }
        }
        //修改总房价
        OrderBO order=new OrderBO();
        order.setId(orderBO.getId());
        order.setTotalPrice(totalPrice);
        orderDAO.updOrder(order);

        return orderBO.getOrderNumber();
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
                    everydayRoomPriceDAO.getEverydayRoomById(orderChild.getId());
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
        //查询子订单信息
        List<OrderChildBO> orderChildBOS = this.getOrderChildById(orderBO);
        for (OrderChildBO orderChildBO:orderChildBOS) {
            if(orderChildBO.getOrderState().equals(Constants.RESERVATION.getValue())){
                orderBO.setOrderChildBOS(orderChildBOS);
                return orderBO;
            }
        }
        //没有预约中的子订单 视为订单结束
        return null;
    }

    //修改子订单信息
    public Integer updOrderChild(OrderChildBO orderBO){
       return orderDAO.updOrderChild(orderBO);
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

    //根据订单id查询订单信息
    public OrderBO getOrderById(Integer orderId){
        OrderBO orderBO=orderDAO.getOrderById(orderId);
        orderBO.setOrderChildBOS(this.getOrderChildById(orderBO));
        return orderBO;
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
