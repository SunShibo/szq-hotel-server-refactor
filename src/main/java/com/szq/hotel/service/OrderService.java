package com.szq.hotel.service;

import com.szq.hotel.common.constants.Constants;
import com.szq.hotel.dao.*;
import com.szq.hotel.entity.bo.*;
import com.szq.hotel.entity.param.OrderParam;
import com.szq.hotel.entity.result.CheckInInfoResult;
import com.szq.hotel.entity.result.CheckRoomPersonResult;
import com.szq.hotel.entity.result.OrderResult;
import com.szq.hotel.util.DateUtils;
import com.szq.hotel.util.IDBuilder;
import com.szq.hotel.util.JsonUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
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

    @Resource
    OrderRecordDAO orderRecordDAO;

    @Resource
    ChildOrderService childOrderService;

    @Resource
    OrderRecordService orderRecordService;

    @Resource
    RoomTypeDAO roomTypeDAO;

    //添加主订单 携带订单id
    public void addOrder(OrderBO orderBO) {
        //生成订单号
        IDBuilder idBuilder = new IDBuilder(10, 10);
        String orderNumber = idBuilder.nextId() + "";
        orderBO.setOrderNumber(orderNumber);
        orderDAO.addOrder(orderBO);
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
            if (orderChild.getCheckInPersonBOS() == null || orderChild.getCheckInPersonBOS().size() == 0) {
                orderChild.setOrderState(Constants.RESERVATION.getValue());//状态
            } else {
                //入住待支付状态
                orderChild.setOrderState(Constants.NOTPAY.getValue());//状态
            }
            orderChild.setStartTime(orderBO.getCheckTime());//入住时间
            orderChild.setEndTime(orderBO.getCheckOutTime());//离店时间

            orderDAO.addOrderChild(orderChild);//返回子订单id

            //这个房型下的每日价格
            List<EverydayRoomPriceBO> everydayRoomPriceBOList = orderChild.getEverydayRoomPriceBOS();
            if (everydayRoomPriceBOList != null && everydayRoomPriceBOList.size() != 0) {
                for (EverydayRoomPriceBO everydayRoomPriceBO : everydayRoomPriceBOList) {
                    everydayRoomPriceBO.setOrderChildId(orderChild.getId());
                    everydayRoomPriceDAO.addEverydayRoomPrice(everydayRoomPriceBO);

                    //叠加总房价
                    totalPrice = totalPrice.add(everydayRoomPriceBO.getMoney());
                }
            }
            //这个房间的入住人 预约的时候为null 或者稍后入住为null
            List<CheckInPersonBO> checkInPersonBOS = orderChild.getCheckInPersonBOS();
            if (checkInPersonBOS != null && checkInPersonBOS.size() != 0) {
                for (CheckInPersonBO person : checkInPersonBOS) {
                    person.setOrderChildId(orderChild.getId());
                    person.setStatus(Constants.CHECKIN.getValue());
                    checkInPersonDAO.addCheckInPerson(person);
                }
                //房间状态修改为在住状态
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", orderChild.getRoomId());
                map.put("state", Constants.INTHE.getValue());
                roomService.updateroomMajorState(map);
            }
        }
        //修改总房价
        OrderBO order = new OrderBO();
        order.setId(orderBO.getId());
        order.setTotalPrice(totalPrice);
        orderDAO.updOrder(order);

        return orderBO.getOrderNumber();
    }
    //预定入住
    public void updOrderInfo(List<OrderChildBO> orderChildBOList, OrderBO orderBO) {

        //旧的子订单信息
        List<OrderChildBO> orderChildBOS = orderDAO.getOrderChildByOrderId(orderBO.getId());
        //新选的房间或者修改了房间的房型 id传过来为null 代表xin子订单
        for (OrderChildBO orderChildBONew : orderChildBOList) {
            if (orderChildBONew.getId() == null) {
                orderDAO.addOrderChild(orderChildBONew);
                List<CheckInPersonBO> checkInPersonBOS = orderChildBONew.getCheckInPersonBOS();
                List<EverydayRoomPriceBO> everydayRoomPriceBOList = orderChildBONew.getEverydayRoomPriceBOS();
                if (checkInPersonBOS != null) {
                    for (CheckInPersonBO person : checkInPersonBOS) {
                        checkInPersonDAO.addCheckInPerson(person);
                    }
                }
                if (everydayRoomPriceBOList != null) {
                    for (EverydayRoomPriceBO roomPrice : everydayRoomPriceBOList) {
                        everydayRoomPriceDAO.addEverydayRoomPrice(roomPrice);
                    }
                }
                //房间状态修改为在住状态
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", orderChildBONew.getRoomId());
                map.put("state", Constants.INTHE.getValue());
                roomService.updateroomMajorState(map);
            }
            //重新算总价
            //这个房间下的每日价格
            List<EverydayRoomPriceBO> everydayRoomPriceBOList = orderChildBONew.getEverydayRoomPriceBOS();
            if (everydayRoomPriceBOList != null && everydayRoomPriceBOList.size() != 0) {
                for (EverydayRoomPriceBO everydayRoomPriceBO : everydayRoomPriceBOList) {
                    //叠加总房价
                    orderBO.setTotalPrice(orderBO.getTotalPrice().add(everydayRoomPriceBO.getMoney()));
                }
            }
        }
        //修改主订单信息
        orderDAO.updOrder(orderBO);
        //旧子订单没传过来 则子订单关闭
        //旧子订单传过来了 则修改子订单信息
        for (OrderChildBO orderChildOld : orderChildBOS) {// 1 2 3
            boolean orderChildOldBool = true;//这个房间还在不在

            for (OrderChildBO orderChildBONew : orderChildBOList) {//1 2
                if (orderChildOld.getId() == orderChildBONew.getId()) {
                    orderChildOldBool = false;
                    //原本的子订单id和新传过来的子订单id一样 代表没有换房间房型 还作为同一个子订单 修改一边其他信息
                    //修改日期啊 状态啊 一切有可能改动的
                    orderChildBONew.setStartTime(orderBO.getCheckTime());
                    orderChildBONew.setEndTime(orderBO.getCheckOutTime());
                    //预约状态
                    if (orderChildBONew.getCheckInPersonBOS() == null || orderChildBONew.getCheckInPersonBOS().size() == 0) {
                        orderChildBONew.setOrderState(Constants.RESERVATION.getValue());//状态
                    } else {
                        //入住待支付状态
                        orderChildBONew.setOrderState(Constants.NOTPAY.getValue());//状态
                    }
                    orderDAO.updOrderChild(orderChildBONew);
                    //修改房价信息
                    List<EverydayRoomPriceBO> everydayRoomPriceBOList = orderChildBONew.getEverydayRoomPriceBOS();
                    if (everydayRoomPriceBOList != null) {
                        for (EverydayRoomPriceBO roomPrice : everydayRoomPriceBOList) {
                            everydayRoomPriceDAO.updEverydayRoomPrice(roomPrice);
                        }
                    }

                    //入住人信息 逻辑和订单逻辑一样的
                    List<CheckInPersonBO> checkInPersonNewS = orderChildBONew.getCheckInPersonBOS();
                    if (checkInPersonNewS != null) {
                        //查询原来入住人员信息
                        List<CheckInPersonBO> checkInPersonOldS = checkInPersonDAO.getCheckInPersonById(orderChildOld.getId(),null);
                        for (CheckInPersonBO newPerson : checkInPersonNewS) {
                            System.err.println("id==========="+newPerson.getId());
                            //新入住人直接add
                            if (newPerson.getId() == null||newPerson.getId().equals("")) {
                                newPerson.setOrderChildId(orderChildOld.getId());
                                newPerson.setStatus(Constants.CHECKIN.getValue());
                                checkInPersonDAO.addCheckInPerson(newPerson);
                                continue;
                            }

                        }
                        for (CheckInPersonBO oldPerson : checkInPersonOldS) {
                            boolean personOldBool=true;//旧入住人还在不在
                            for (CheckInPersonBO newPerson : checkInPersonNewS){
                                if(oldPerson.getId()==newPerson.getId()){
                                    personOldBool=false;
                                    checkInPersonDAO.updCheckInPerson(newPerson);
                                }
                            }
                            if(personOldBool){
                                oldPerson.setStatus(Constants.CHECKOUT.getValue());
                                checkInPersonDAO.updCheckInPerson(oldPerson);
                            }
                        }

                    }
                }
            }
            System.err.println("bool======================"+orderChildOldBool);
            if (orderChildOldBool) {
                //原本预定的被删除了，或者因为修改了房型，id没有传过来 代表已经取消
                OrderChildBO orderChildCancel = new OrderChildBO();
                orderChildCancel.setId(orderChildOld.getId());
                orderChildCancel.setOrderState(Constants.CANCEL.getValue());
                orderDAO.updOrderChild(orderChildCancel);
                continue;

            }

        }

    }
    //根据订单id查询子订单 以及子订单房价信息 入住人员信息
    public List<OrderChildBO> getOrderChildById(OrderBO orderBO) {
        //查询子订单信息
        List<OrderChildBO> orderChildBOS = orderDAO.getOrderChildByOrderId(orderBO.getId());
        if (orderChildBOS.size() == 0) {
            return null;
        }
        BigDecimal totalPrice = new BigDecimal(0);//总房价
        for (OrderChildBO orderChild : orderChildBOS) {
            if(!orderChild.getOrderState().equals(Constants.CANCEL.getValue())){
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
                List<CheckInPersonBO> checkInPersonBOS = checkInPersonDAO.getCheckInPersonById(orderChild.getId(),null);
                orderChild.setCheckInPersonBOS(checkInPersonBOS);
            }

        }
        //总房价
        orderBO.setTotalPrice(totalPrice);
        return orderChildBOS;
    }
    //根据身份证号 手机号查询预约信息
    public OrderBO getOrderInfo(String idNumber, String mobile,Integer hotelId) {
        String date = this.getDate();
        //主订单信息
        OrderBO orderBO = orderDAO.getOrderByIdOrMobile(idNumber, mobile, date,hotelId);
        if (orderBO == null || orderBO.getOrderNumber() == null) {
            return null;
        }
        //查询子订单信息
        List<OrderChildBO> orderChildBOS = this.getOrderChildById(orderBO);
        for (OrderChildBO orderChildBO : orderChildBOS) {
            if (orderChildBO.getOrderState().equals(Constants.RESERVATION.getValue())) {
                orderBO.setOrderChildBOS(orderChildBOS);
                return orderBO;
            }
        }
        //没有预约中的子订单 视为订单结束
        return null;
    }
    //修改子订单信息
    public Integer updOrderChild(OrderChildBO orderBO) {
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
    public OrderBO getOrderById(Integer orderId) {
        OrderBO orderBO = orderDAO.getOrderById(orderId);
        orderBO.setOrderChildBOS(this.getOrderChildById(orderBO));
        return orderBO;
    }
    //根据主订单id查询房间信息（客帐管理）
    public List<OrderChildBO> getRoomInfoById(Integer orderId){
        return orderDAO.getRoomInfoById(orderId);
    }
    //根据子订单id查询房间信息消费信息(客帐管理)
    public OrderChildBO getOrderInfoById(Integer id){
        //消费金额
        OrderChildBO orderChildBO=orderDAO.getOrderChildById(id);
        //消费记录
        List<OrderRecoredBO> recoredBOS=orderRecordDAO.queryOrderRecord(id);

        orderChildBO.setOrderRecoredBOS(recoredBOS);
        return orderChildBO;
    }
    //根据子订单id查询子订单
    public OrderChildBO getOrderChildById(Integer orderChildId){
        try {
            OrderChildBO orderChildBO=orderDAO.getOrderChildById(orderChildId);
            //OrderBO orderBO=orderDAO.getOrderById(orderChildBO.getOrderId());
            //获取当前
            Date currentTime  = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString=df.format(currentTime);
            Date currentTime_2 = df.parse(dateString);
            //计算超时费
            if(currentTime_2.getTime()>orderChildBO.getEndTime().getTime()){
                Long minute=DateUtils.getQuotMinute(currentTime_2,orderChildBO.getEndTime());
                RoomTypeBO roomTypeBO=roomTypeDAO.getRoomType(orderChildBO.getRoomTypeId());
                //超过4小时
                if(minute<=4){
                    orderChildBO.setTimeoutRate(new BigDecimal(roomTypeBO.getBasicPrice()).divide(new BigDecimal(2)));
                }else{
                    orderChildBO.setTimeoutRate(new BigDecimal(roomTypeBO.getBasicPrice()));
                }
            }
            return orderChildBO;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }


    }
    //获取在住报表
    public List<OrderResult> getCheckInReport(){
        return orderDAO.getCheckInReport();
    }
    //获取预离店报表
    public List<OrderResult> getCheckOutReport(Date beforeTime,Date afterTime,Integer pageNo,Integer pageSize){
        return orderDAO.getCheckOutReport(beforeTime,afterTime,pageNo,pageSize);
    }
    //获取预离店总数
    public Integer getCheckOutReportCount(Date beforeTime,Date afterTime){
        return orderDAO.getCheckOutReportCount(beforeTime,afterTime).size();
    }
    //把入住未支付超过15分钟的子订单关闭
    public Integer closeOrder(){
        return orderDAO.closeOrder();
    }
    //首页查询在住信息
    public CheckInInfoResult getCheckInInfo(Integer roomId){
        //在住信息
        CheckInInfoResult checkInInfoResult=orderDAO.getOrderChildByRoomId(roomId);
        if(checkInInfoResult==null){
            return null;
        }
        //同住人信息
        List<CheckInPersonBO> checkInPersonBOS=checkInPersonDAO.getCheckInPersonById(checkInInfoResult.getOrderChildId(),Constants.CHECKIN.getValue());
        checkInInfoResult.setCheckInPersonBOS(checkInPersonBOS);
        //联房信息
        List<CheckRoomPersonResult> checkRoomPersonResults = orderDAO.getOrderRoomByCode(checkInInfoResult.getAlRoomCode());
        checkInInfoResult.setCheckRoomPersonResults(checkRoomPersonResults);
        return checkInInfoResult;
    }
    //修改在住信息
    public void updCheckInInfo(Integer orderId, String channel, String OTA,
                               Integer orderChildId,Date entTime, String remark,
                               String checkInPersonJson) throws ParseException {
        //如果客源传过来修改客源 ota
        if(orderId!=null&&!orderId.equals("")){
            OrderBO orderBO=new OrderBO();
            orderBO.setId(orderId);
            orderBO.setChannel(channel);
            orderBO.setOTA(OTA);
            orderDAO.updOrder(orderBO);
        }
        //如果离店时间传过来修改离店时间 修改房间备注
        if(orderChildId!=null&&!orderChildId.equals("")){
            //修改时间 计算多添加的时间 添加每日房价
            if(entTime!=null&&!entTime.equals("")){
                //最后一天的价格
                List<EverydayRoomPriceBO> everydayRoomPriceBOS=everydayRoomPriceDAO.getEverydayRoomById(orderChildId);
                if(everydayRoomPriceBOS==null||everydayRoomPriceBOS.size()==0){
                    return;
                }
                BigDecimal oldMoney=everydayRoomPriceBOS.get(everydayRoomPriceBOS.size()-1).getMoney();
                //续租时间 天数
                OrderChildBO oldOrderChild = orderDAO.getOrderChildById(orderChildId);
                if(oldOrderChild==null){
                    return;
                }
                Long day=DateUtils.getQuot(entTime,oldOrderChild.getEndTime());
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
                for (int i=1;i<=day;i++) {
                    EverydayRoomPriceBO everydayRoomPriceBO=new EverydayRoomPriceBO();
                    everydayRoomPriceBO.setOrderChildId(orderChildId);
                    everydayRoomPriceBO.setMoney(oldMoney);

                    Date newTime=DateUtils.getAppointDate(oldOrderChild.getEndTime(),i-1);
                    everydayRoomPriceBO.setTime(simpleDateFormat.parse(simpleDateFormat.format(newTime)));
                    everydayRoomPriceDAO.addEverydayRoomPrice(everydayRoomPriceBO);
                }

            }
            OrderChildBO orderChildBO=new OrderChildBO();
            orderChildBO.setId(orderChildId);
            orderChildBO.setRemark(remark);
            //修改实际离店时间
            orderChildBO.setPracticalDepartureTime(entTime);
            orderDAO.updOrderChild(orderChildBO);

            //修改同住人
            if(checkInPersonJson!=null&&!checkInPersonJson.equals("")){
                List<CheckInPersonBO> list = JsonUtils.getJSONtoList(checkInPersonJson, CheckInPersonBO.class);
                for (CheckInPersonBO checkInPersonBO :list) {
                    checkInPersonBO.setStatus(Constants.CHECKIN.getValue());
                    checkInPersonBO.setOrderChildId(orderChildId);
                    checkInPersonDAO.addCheckInPerson(checkInPersonBO);
                }
            }
        }

    }

    //查询所有可用联房
    public List<CheckInPersonBO> getAlRoom(Integer roomId){
        return orderDAO.getAlRoom(roomId);
    }

    //解除联房
    public void updAlRoom(Integer[] idArr){
        for (int i=0;i<idArr.length;i++){
            //修改联房码 解除联房 设置为主账房
            OrderChildBO orderChildBO=new OrderChildBO();
            orderChildBO.setId(idArr[i]);
            orderChildBO.setAlRoomCode(UUID.randomUUID().toString());
            orderChildBO.setMain("yes");
            orderDAO.updOrderChild(orderChildBO);
        }

    }

    //联房
    public void addAlRoom(Integer orderChildId,String orderChildIds,Integer userId){
        //旧主账房
        String[]orderChildIdArr=orderChildIds.split(",");
        //新主账房
        OrderChildBO orderChildBONew=orderDAO.getOrderChildById(orderChildId);
        //旧主账房 取消主账房 联房码修改为新主账房的联房码
        for (int i=0;i<orderChildIdArr.length;i++){
            //主账房的账单记录id
            String ids="";
            List<OrderRecoredBO> orderRecoredBO = orderRecordService.queryOrderRecord(new Integer(orderChildIdArr[i]));
            for (int y=0;y<orderRecoredBO.size();y++) {
                ids=ids+orderRecoredBO.get(y).getId()+",";
            }
            //房间消费转账到新的主账房 添加消费记录
            childOrderService.transferAccounts(userId,ids,orderChildId,new Integer(orderChildIdArr[i]));

            //修改子帐房联房码
            OrderChildBO orderChildBO=orderDAO.getOrderChildById(new Integer(orderChildIdArr[i]));
            List<OrderChildBO> orderChildBOList=orderDAO.getOrderByCode(orderChildBO.getAlRoomCode(),"no");
            for (OrderChildBO child:orderChildBOList) {
                child.setAlRoomCode(orderChildBONew.getAlRoomCode());
                orderDAO.updOrderChild(child);
            }

            //修改主账房信息
            OrderChildBO mainOrder=new OrderChildBO();
            mainOrder.setId(new Integer(orderChildIdArr[i]));
            mainOrder.setMain("no");
            mainOrder.setAlRoomCode(orderChildBONew.getAlRoomCode());
            orderDAO.updOrderChild(mainOrder);
        }

    }

    //获取子订单剩余租期价格
    public List<EverydayRoomPriceBO> getRemainingLease(Integer orderChildId) throws ParseException {
        //获取今天六点
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 04);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        Date m6 = c.getTime();
        //获取当前
        Date currentTime  = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString=df.format(currentTime);
        Date currentTime_2 = df.parse(dateString);
        Calendar calendar = Calendar.getInstance();

        DateFormat time = new SimpleDateFormat("yyyy-MM-dd");
        //超过六点则昨天的夜审过了
        if(currentTime_2.getTime()>m6.getTime()){
            calendar.add(Calendar.DATE, -1); //得到前一天
        }else{
            calendar.add(Calendar.DATE, -2); //得到前两天
        }
        return everydayRoomPriceDAO.getRemainingEverydayRoomById(time.format(calendar.getTime()),orderChildId);
    }

    //换房
    public void changeRoom(OrderChildBO orderChildBO,List<EverydayRoomPriceBO> everydayRoomPrice){
        //修改房间 房型
        orderDAO.updOrderChild(orderChildBO);
        //修改每日房价
        for (EverydayRoomPriceBO e :everydayRoomPrice) {
            everydayRoomPriceDAO.updEverydayRoomPrice(e);
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


    public  int queryOrderListCount(OrderParam param){
       return  orderDAO.queryOrderListCount(param);
    }
}
