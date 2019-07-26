package com.szq.hotel.service;

import com.sun.org.apache.xpath.internal.operations.Or;
import com.szq.hotel.common.constants.Constants;
import com.szq.hotel.dao.*;
import com.szq.hotel.entity.bo.*;
import com.szq.hotel.entity.param.OrderChildBackupParam;
import com.szq.hotel.entity.param.OrderParam;
import com.szq.hotel.entity.result.CheckInInfoResult;
import com.szq.hotel.entity.result.CheckRoomPersonResult;
import com.szq.hotel.entity.result.OrderResult;
import com.szq.hotel.pop.Constant;
import com.szq.hotel.util.DateUtils;
import com.szq.hotel.util.IDBuilder;
import com.szq.hotel.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Resource
    RoomDAO roomDAO;

    @Resource
    CashierSummaryService cashierSummaryService;

    @Resource
    CheckInPersonService checkInPersonService;

    @Resource
    MemberService memberService;

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    //添加主订单 子订单 入住人 每日价格
    public String addOrderInfo(OrderBO orderBO, List<OrderChildBO> orderChildBOList) {
        log.info("start addOrderInfo..................................");
        log.info("orderBO:{}\torderChildBOList:{}", orderBO, orderChildBOList);
        //生成订单号
        IDBuilder idBuilder = new IDBuilder(10, 10);
        String orderNumber = idBuilder.nextId() + "";
        orderBO.setOrderNumber(orderNumber);
        //添加主订单 携带回订单id
        orderDAO.addOrder(orderBO);

        //联房码
        String alRoomCode = UUID.randomUUID().toString();
        //总房价
        BigDecimal totalPrice = new BigDecimal(0);
        for (OrderChildBO orderChild : orderChildBOList) {
            //添加子订单
            orderChild.setOrderId(orderBO.getId());//主订单id
            orderChild.setAlRoomCode(alRoomCode);//联房码
            //预约状态
            if(orderChild.getCheckInPersonBOS()!=null&&orderChild.getCheckInPersonBOS().size()!=0){
                orderChild.setOrderState(Constants.NOTPAY.getValue());//状态
            }else{
                orderChild.setOrderState(Constants.RESERVATION.getValue());//状态
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
            //这个房间的入住人 预约的时候为null
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

        log.info("result:{}", orderBO.getOrderNumber());
        log.info("end addOrderInfo.............................................");
        return orderBO.getOrderNumber();
    }

    //预定入住
    public void reservation(List<OrderChildBO> orderChildBOList, OrderBO orderBO) {
        log.info("start addOrderInfo..................................");
        log.info("orderBO:{}\torderChildBOList:{}", orderBO, orderChildBOList);

        //查询预约中的联房码
        String alRoomCode = orderDAO.getOrderChildAlRoomCode(orderBO.getId());

        for (OrderChildBO orderChildBO : orderChildBOList) {
            //根据房间查询子订单
            OrderChildBO orderChildResult = orderDAO.getResOrderChildByRoomId(orderChildBO.getRoomId(), orderBO.getId());
            //根据房间没有这个订单再去根据房型查询
            if (orderChildResult == null || orderChildResult.getId() == null) {
                orderChildResult = orderDAO.getResOrderChildByRoomTypeId(orderChildBO.getRoomTypeId(), orderBO.getId());
            }
            //子订单id
            if (orderChildResult != null && orderChildResult.getId() != null) {
                orderChildBO.setId(orderChildResult.getId());
            } else {
                orderChildBO.setId(null);
            }

            //查询不到代表新增子订单
            if (orderChildBO == null || orderChildBO.getId() == null) {
                //新增订单状态
                orderChildBO.setStartTime(orderBO.getCheckTime());
                orderChildBO.setEndTime(orderBO.getCheckOutTime());
                orderChildBO.setOrderState(Constants.NOTPAY.getValue());//状态
                orderChildBO.setAlRoomCode(alRoomCode);
                orderChildBO.setOrderId(orderBO.getId());
                orderDAO.addOrderChild(orderChildBO);
            } else {
                //修改房价信息
                List<EverydayRoomPriceBO> everydayRoomPriceBOList = orderChildBO.getEverydayRoomPriceBOS();
                if (everydayRoomPriceBOList != null) {
                    //删除这个订单旧的每日房价
                    everydayRoomPriceDAO.delEverydayRoomById(orderChildBO.getId());
                }
                //修改订单状态
                orderChildBO.setStartTime(orderBO.getCheckTime());
                orderChildBO.setEndTime(orderBO.getCheckOutTime());
                orderChildBO.setOrderState(Constants.NOTPAY.getValue());//状态
                orderChildBO.setAlRoomCode(alRoomCode);
                orderDAO.updOrderChild(orderChildBO);

            }
            //房间状态修改为在住状态
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", orderChildBO.getRoomId());
            map.put("state", Constants.INTHE.getValue());
            roomService.updateroomMajorState(map);

            System.err.println(orderChildBO.getId());
            //添加入住人
            List<CheckInPersonBO> checkInPersonBOS = orderChildBO.getCheckInPersonBOS();
            if (checkInPersonBOS != null) {
                for (CheckInPersonBO person : checkInPersonBOS) {
                    person.setOrderChildId(orderChildBO.getId());
                    person.setStatus(Constants.CHECKIN.getValue());
                    checkInPersonDAO.addCheckInPerson(person);
                }
            }

            //添加每日房价信息
            List<EverydayRoomPriceBO> everydayRoomPriceBOList = orderChildBO.getEverydayRoomPriceBOS();
            if (everydayRoomPriceBOList != null) {
                for (EverydayRoomPriceBO roomPrice : everydayRoomPriceBOList) {
                    roomPrice.setOrderChildId(orderChildBO.getId());
                    everydayRoomPriceDAO.addEverydayRoomPrice(roomPrice);
                }
            }
        }
        orderDAO.updOrder(orderBO);
        log.info("result:{}", orderBO.getOrderNumber());
        log.info("end addOrderInfo.............................................");
    }

    //预约修改
    public void updateInfo(List<OrderChildBO> orderChildBOList, OrderBO orderBO) {
        //查询出旧预约中子订单信息
        List<OrderChildBO> orderChildBOListOld = orderDAO.getOrderChildByOrderId2(orderBO.getId(), Constants.RESERVATION.getValue());
        //获取旧联房码
        String alRoomCode = orderChildBOListOld.get(0).getAlRoomCode();
        //旧预约信息和新预约信息 预约的类型不一样
        if ((orderChildBOListOld.get(0).getRoomId() == null && orderChildBOList.get(0).getRoomId() != null) ||
                ((orderChildBOListOld.get(0).getRoomId() != null && orderChildBOList.get(0).getRoomId() == null))) {
            //所有旧预约信息 变为取消
            for (OrderChildBO orderChildOld : orderChildBOListOld) {
                orderChildOld.setOrderState(Constants.CANCEL.getValue());
                orderDAO.updOrderChild(orderChildOld);
            }
            //添加新预约信息
            for (OrderChildBO orderChildBO : orderChildBOList) {
                this.addOrderChildEveryRoomPrice(orderChildBO, orderBO, alRoomCode);
            }

        } else if (orderChildBOListOld.get(0).getRoomId() == null && orderChildBOList.get(0).getRoomId() == null) {
            //如果新的子订单房型 和 旧房型对应上 则新修改子订单信息
            for (OrderChildBO orderChildNew : orderChildBOList) {
                for (OrderChildBO orderChildOld : orderChildBOListOld) {
                    if (orderChildNew.getRoomTypeId() == orderChildOld.getRoomTypeId() && !orderChildOld.getOrderState().equals("yes")) {
                        orderChildNew.setId(orderChildOld.getId());
                        orderDAO.updOrderChild(orderChildNew);
                        orderChildOld.setOrderState("yes");
                        orderChildNew.setOrderState("yes");
                    }
                }
            }
            //添加新子订单
            for (OrderChildBO orderChildNew : orderChildBOList) {
                if (!orderChildNew.getOrderState().equals("yes")) {
                    this.addOrderChildEveryRoomPrice(orderChildNew, orderBO, alRoomCode);
                }
            }
            //旧房型如果和新房型对应不上 取消这个旧子订单
            for (OrderChildBO orderChildOld : orderChildBOListOld) {
                orderChildOld.setOrderState(Constants.CANCEL.getValue());
                orderDAO.updOrderChild(orderChildOld);
            }
        } else if (orderChildBOListOld.get(0).getRoomId() != null && orderChildBOList.get(0).getRoomId() != null) {
            //如果新的子订单房间 和 旧房间对应上 则新修改子订单信息
            for (OrderChildBO orderChildNew : orderChildBOList) {
                for (OrderChildBO orderChildOld : orderChildBOListOld) {
                    if (orderChildNew.getRoomId() == orderChildOld.getRoomId() && !orderChildOld.getOrderState().equals("yes")) {
                        orderChildNew.setId(orderChildOld.getId());
                        orderDAO.updOrderChild(orderChildNew);
                        orderChildOld.setOrderState("yes");
                        orderChildNew.setOrderState("yes");

                        //删除旧每日房价
                        everydayRoomPriceDAO.delEverydayRoomById(orderChildOld.getId());
                        //添加新的每日房价
                        List<EverydayRoomPriceBO> everydayRoomPriceBOList=orderChildNew.getEverydayRoomPriceBOS();
                        for (EverydayRoomPriceBO everydayRoomPriceBO:everydayRoomPriceBOList){
                            everydayRoomPriceBO.setOrderChildId(orderChildOld.getId());
                            everydayRoomPriceDAO.addEverydayRoomPrice(everydayRoomPriceBO);
                        }
                    }
                }
            }
            //添加新子订单
            for (OrderChildBO orderChildNew : orderChildBOList) {
                if (!"yes".equals(orderChildNew.getOrderState())) {
                    this.addOrderChildEveryRoomPrice(orderChildNew,orderBO,alRoomCode);
                }
            }
            //旧房型如果和新房型对应不上 取消这个旧子订单
            for (OrderChildBO orderChildOld : orderChildBOListOld) {
                orderChildOld.setOrderState(Constants.CANCEL.getValue());
                orderDAO.updOrderChild(orderChildOld);
            }
        }
        //修改主订单信息
        orderDAO.updOrder(orderBO);
    }

    //添加预约信息 子订单信息 每日房价信息
    public void addOrderChildEveryRoomPrice(OrderChildBO orderChildBO,OrderBO orderBO,String alRoomCode){
        //添加子订单
        orderChildBO.setOrderId(orderBO.getId());//主订单id
        orderChildBO.setAlRoomCode(alRoomCode);//联房码
        orderChildBO.setOrderState(Constants.RESERVATION.getValue());//状态
        orderChildBO.setStartTime(orderBO.getCheckTime());//入住时间
        orderChildBO.setEndTime(orderBO.getCheckOutTime());//离店时间
        orderDAO.addOrderChild(orderChildBO);//返回子订单id

        //这个房型下的每日价格
        List<EverydayRoomPriceBO> everydayRoomPriceBOList = orderChildBO.getEverydayRoomPriceBOS();
        if (everydayRoomPriceBOList != null && everydayRoomPriceBOList.size() != 0) {
            for (EverydayRoomPriceBO everydayRoomPriceBO : everydayRoomPriceBOList) {
                everydayRoomPriceBO.setOrderChildId(orderChildBO.getId());
                everydayRoomPriceDAO.addEverydayRoomPrice(everydayRoomPriceBO);
            }
        }
    }


    //根据身份证号 手机号查询预约信息
    public OrderBO getOrderInfo(String idNumber, String mobile, Integer hotelId) {
        String date = this.getDate();
        //主订单信息
        OrderBO orderBO = orderDAO.getOrderByIdOrMobile(idNumber, mobile, date, hotelId);
        if (orderBO == null || orderBO.getOrderNumber() == null) {
            return null;
        }
        //查询子订单信息 返回所有预约中的子订单
        List<OrderChildBO> orderChildBOS = this.getNoCheckOrderChildById(orderBO);
        if (orderChildBOS == null || orderChildBOS.size()==0) {
            return null;
        }
        orderBO.setOrderChildBOS(orderChildBOS);
        return orderBO;
    }

    //根据订单id查询所有预约中的子订单 房间信息 房型信息 以及子订单每日房价信息
    public List<OrderChildBO> getNoCheckOrderChildById(OrderBO orderBO) {
        //查询预约中的子订单信息
        List<OrderChildBO> noCheckOrderChildBOS = orderDAO.getOrderChildByOrderId4(orderBO.getId(), Constants.RESERVATION.getValue());
        if (noCheckOrderChildBOS == null || noCheckOrderChildBOS.size() == 0) {
            return null;
        }
        List<OrderChildBO> orderChildBOS = null;
        //按房型选择
        if (noCheckOrderChildBOS.get(0).getRoomId() == null) {
            orderChildBOS = orderDAO.getOrderChildByOrderId2(orderBO.getId(), Constants.RESERVATION.getValue());
        } else {
            orderChildBOS = orderDAO.getOrderChildByOrderId3(orderBO.getId(), Constants.RESERVATION.getValue());
        }
        if (orderChildBOS == null || orderChildBOS.size() == 0) {
            return null;
        }
        for (OrderChildBO orderChild : orderChildBOS) {
            //每日房价信息
            List<EverydayRoomPriceBO> everydayList =
                    everydayRoomPriceDAO.getEverydayRoomById(orderChild.getId());
            orderChild.setEverydayRoomPriceBOS(everydayList);
        }
        //预约中的子订单
        return orderChildBOS;
    }

    //根据订单id查询子订单 以及子订单房价信息 入住人员信息
    public List<OrderChildBO> getOrderChildById(OrderBO orderBO) {
        //查询子订单信息
        List<OrderChildBO> orderChildBOS = orderDAO.getOrderChildByOrderId2(orderBO.getId(), Constants.RESERVATION.getValue());
        if (orderChildBOS.size() == 0) {
            return null;
        }
        BigDecimal totalPrice = new BigDecimal(0);//总房价
        for (OrderChildBO orderChild : orderChildBOS) {
            if (!orderChild.getOrderState().equals(Constants.CANCEL.getValue())) {
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
                List<CheckInPersonBO> checkInPersonBOS = checkInPersonDAO.getCheckInPersonById(orderChild.getId(), null);
                orderChild.setCheckInPersonBOS(checkInPersonBOS);
            }

        }
        //总房价
        orderBO.setTotalPrice(totalPrice);
        return orderChildBOS;
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

    //取消预约中的子订单
    public void closeOrderChild(String orderChildIdS){
        String[] orderChildS=orderChildIdS.split(",");
        for (int i=0;i<orderChildS.length;i++){
            OrderChildBO orderChildBO=new OrderChildBO();
            orderChildBO.setId(new Integer(orderChildS[i]));
            orderChildBO.setOrderState(Constants.CANCEL.getValue());
            orderDAO.updOrderChild(orderChildBO);
        }

    }

    //根据主订单id查询房间信息（客帐管理）
    public List<OrderChildBO> getRoomInfoById(Integer orderId) {
        List<OrderChildBO> orderChildBOS=orderDAO.getRoomInfoById(orderId);
        int index=-1;
        OrderChildBO orderChildBO=null;
        for (int i=0;i<orderChildBOS.size();i++) {
            if(orderChildBOS.get(i).getMain()!=null&&orderChildBOS.get(i).getMain().equals("yes")){
                index=i;
                orderChildBO=orderChildBOS.get(i);
                break;
            }
        }
        if(index!=-1){
            orderChildBOS.remove(index);
            orderChildBOS.add(0,orderChildBO);
        }
        return orderChildBOS;
    }

    //根据子订单id查询房间信息消费信息(客帐管理)
    public OrderChildBO getOrderInfoById(Integer id) {
        //消费金额
        OrderChildBO orderChildBO = orderDAO.getOrderChildById(id);
        //查询房态
        RoomBO roomBO = roomDAO.getRoomBo(orderChildBO.getRoomId());
        orderChildBO.setRoomMajorState(roomBO.getRoomMajorState());
        //消费记录
        List<OrderRecoredBO> recordBOS = orderRecordDAO.queryOrderRecord(id);
        orderChildBO.setOrderRecoredBOS(recordBOS);
        return orderChildBO;
    }

    //根据主订单id 查询预约中的子订单
    public List<OrderChildBO> getSubscribeOrderChild(Integer orderId){
       return orderDAO.getSubscribeOrderChild(orderId,Constants.RESERVATION.getValue());
    }

    //获取入住支付信息
    public List<OrderChildBO> getPayInfo(Integer orderId) {
        List<OrderChildBO> orderChildBOS=orderDAO.getPayInfo(orderId);
        for (OrderChildBO orderChildBO:orderChildBOS){
            MemberBO memberBO=memberService.selectMemberByCerNumber(orderChildBO.getCertificateNumber());
            if(memberBO!=null){
                MemberResultBO memberResultBO=memberService.getMemberCardNumber(memberBO.getId());
                if (memberResultBO.getType().equals("yes")){
                    orderChildBO.setNameStatus("yes");
                }else{
                    orderChildBO.setNameStatus("no");
                }
            }else{
                orderChildBO.setNameStatus("no");
            }
        }

        return orderChildBOS;
    }

    //根据子订单id查询子订单
    public OrderChildBO getOrderChildById(Integer orderChildId) {
        try {
            OrderChildBO orderChildBO = orderDAO.getOrderChildById(orderChildId);
            //获取当前
            Date currentTime = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = df.format(currentTime);
            Date currentTime_2 = df.parse(dateString);

            //计算超时费
            if (currentTime_2.getTime() > orderChildBO.getEndTime().getTime()) {
                Long minute = DateUtils.getQuotMinute(currentTime_2, orderChildBO.getEndTime());
                RoomTypeBO roomTypeBO = roomTypeDAO.getRoomType(orderChildBO.getRoomTypeId());
                //超过4小时
                if (minute <= 4) {
                    orderChildBO.setTimeoutRate(new BigDecimal(roomTypeBO.getBasicPrice()).divide(new BigDecimal(2)));
                } else {
                    orderChildBO.setTimeoutRate(new BigDecimal(roomTypeBO.getBasicPrice()));
                }
            }
            //计算房费

            return orderChildBO;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }


    }

    //获取在住报表
    public List<OrderResult> getCheckInReport(Integer hotelId) {
        return orderDAO.getCheckInReport(hotelId);
    }

    //获取预离店报表
    public List<OrderResult> getCheckOutReport(Date beforeTime, Date afterTime, Integer pageNo, Integer pageSize, Integer hotelId) {
        return orderDAO.getCheckOutReport(beforeTime, afterTime, pageNo, pageSize, hotelId);
    }

    //获取预离店总数
    public Integer getCheckOutReportCount(Date beforeTime, Date afterTime, Integer hotelId) {
        return orderDAO.getCheckOutReportCount(beforeTime, afterTime, hotelId).size();
    }

    //把入住未支付超过15分钟的子订单关闭
    public Integer closeOrder() {
        return orderDAO.closeOrder();
    }

    //首页查询在住信息
    public CheckInInfoResult getCheckInInfo(Integer roomId) {
        //在住信息
        CheckInInfoResult checkInInfoResult = orderDAO.getOrderChildByRoomId(roomId);
        if(checkInInfoResult.getPracticalDepartureTime()!=null){
            checkInInfoResult.setEndTime(checkInInfoResult.getPracticalDepartureTime());
        }
        if (checkInInfoResult == null) {
            return null;
        }
        //同住人信息
        List<CheckInPersonBO> checkInPersonBOS = checkInPersonDAO.getCheckInPersonById(checkInInfoResult.getOrderChildId(), Constants.CHECKIN.getValue());
        checkInInfoResult.setCheckInPersonBOS(checkInPersonBOS);
        //联房信息
        List<CheckRoomPersonResult> checkRoomPersonResults = orderDAO.getOrderRoomByCode(checkInInfoResult.getAlRoomCode());
        checkInInfoResult.setCheckRoomPersonResults(checkRoomPersonResults);
        return checkInInfoResult;
    }

    public CheckInInfoResult getReservationInfo(Integer roomId){
        return orderDAO.getReservationInfo(roomId);
    }
    //修改在住信息
    public void updCheckInInfo(Integer orderId, String channel, String OTA,
                               Integer orderChildId, Date entTime, String remark,
                               String checkInPersonJson, String everyDayRoomPrice) throws ParseException {
        //如果客源传过来修改客源 ota
        if (orderId != null && !orderId.equals("")) {
            OrderBO orderBO = new OrderBO();
            orderBO.setId(orderId);
            orderBO.setChannel(channel);
            orderBO.setOTA(OTA);
            orderDAO.updOrder(orderBO);
        }
        //如果子订单传过来修改离店时间 修改房间备注
        if (orderChildId != null && !orderChildId.equals("")) {
            //修改时间 计算多添加的时间 添加每日房价
            if (entTime != null && !entTime.equals("")) {
                //最后一天的价格
                List<EverydayRoomPriceBO> everydayRoomPriceBOS = everydayRoomPriceDAO.getEverydayRoomById(orderChildId);
                if (everydayRoomPriceBOS == null || everydayRoomPriceBOS.size() == 0) {
                    return;
                }
                BigDecimal oldMoney = everydayRoomPriceBOS.get(everydayRoomPriceBOS.size() - 1).getMoney();
                //续租时间 天数
                OrderChildBO oldOrderChild = orderDAO.getOrderChildById(orderChildId);
                if (oldOrderChild == null) {
                    return;
                }
                Long day = DateUtils.getQuot(entTime, oldOrderChild.getEndTime());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                for (int i = 1; i <= day; i++) {
                    EverydayRoomPriceBO everydayRoomPriceBO = new EverydayRoomPriceBO();
                    everydayRoomPriceBO.setOrderChildId(orderChildId);
                    everydayRoomPriceBO.setMoney(oldMoney);

                    Date newTime = DateUtils.getAppointDate(oldOrderChild.getEndTime(), i - 1);
                    everydayRoomPriceBO.setTime(simpleDateFormat.parse(simpleDateFormat.format(newTime)));
                    everydayRoomPriceDAO.addEverydayRoomPrice(everydayRoomPriceBO);
                }

            }
            OrderChildBO orderChildBO = new OrderChildBO();
            orderChildBO.setId(orderChildId);
            orderChildBO.setRemark(remark);
            //修改实际离店时间
            orderChildBO.setPracticalDepartureTime(entTime);
            orderDAO.updOrderChild(orderChildBO);

            //添加同住人
            if (checkInPersonJson != null && !checkInPersonJson.equals("")) {
                List<CheckInPersonBO> list = JsonUtils.getJSONtoList(checkInPersonJson, CheckInPersonBO.class);
                for (CheckInPersonBO checkInPersonBO : list) {
                    checkInPersonBO.setStatus(Constants.CHECKIN.getValue());
                    checkInPersonBO.setOrderChildId(orderChildId);
                    checkInPersonDAO.addCheckInPerson(checkInPersonBO);
                }
            }

            //修改每日房价
            if (everyDayRoomPrice != null && !everyDayRoomPrice.equals("")) {
                List<EverydayRoomPriceBO> list = JsonUtils.getJSONtoList(everyDayRoomPrice, EverydayRoomPriceBO.class);
                for (EverydayRoomPriceBO everydayRoomPriceBO : list) {
                    everydayRoomPriceDAO.updEverydayRoomPrice(everydayRoomPriceBO);
                }
            }
        }

    }


    //查询所有可用联房
    public List<CheckInPersonBO> getAlRoom(Integer roomId, Integer hotelId) {
        return orderDAO.getAlRoom(roomId, hotelId);
    }

    //解除联房
    public void updAlRoom(Integer[] idArr) {
        for (int i = 0; i < idArr.length; i++) {
            //修改联房码 解除联房 设置为主账房
            OrderChildBO orderChildBO = new OrderChildBO();
            orderChildBO.setId(idArr[i]);
            orderChildBO.setAlRoomCode(UUID.randomUUID().toString());
            orderChildBO.setMain("yes");
            orderDAO.updOrderChild(orderChildBO);
        }

    }

    //联房
    public void addAlRoom(Integer orderChildId, String orderChildIds, Integer userId) {
        //旧主账房
        String[] orderChildIdArr = orderChildIds.split(",");
        //新主账房
        OrderChildBO orderChildBONew = orderDAO.getOrderChildById(orderChildId);
        //旧主账房 取消主账房 联房码修改为新主账房的联房码
        for (int i = 0; i < orderChildIdArr.length; i++) {
            //主账房的账单记录id
            String ids = "";
            List<OrderRecoredBO> orderRecoredBO = orderRecordService.queryOrderRecord(new Integer(orderChildIdArr[i]));
            for (int y = 0; y < orderRecoredBO.size(); y++) {
                ids = ids + orderRecoredBO.get(y).getId() + ",";
            }
            //房间消费转账到新的主账房 添加消费记录
            childOrderService.transferAccounts(userId, ids, orderChildId, new Integer(orderChildIdArr[i]));

            //修改子帐房联房码
            OrderChildBO orderChildBO = orderDAO.getOrderChildById(new Integer(orderChildIdArr[i]));
            List<OrderChildBO> orderChildBOList = orderDAO.getOrderByCode(orderChildBO.getAlRoomCode(), "no");
            for (OrderChildBO child : orderChildBOList) {
                child.setAlRoomCode(orderChildBONew.getAlRoomCode());
                orderDAO.updOrderChild(child);
            }

            //修改主账房信息
            OrderChildBO mainOrder = new OrderChildBO();
            mainOrder.setId(new Integer(orderChildIdArr[i]));
            mainOrder.setMain("no");
            mainOrder.setAlRoomCode(orderChildBONew.getAlRoomCode());
            orderDAO.updOrderChild(mainOrder);
        }

    }

    //获取子订单剩余租期价格
    public List<EverydayRoomPriceBO> getRemainingLease(Integer orderChildId) throws ParseException {
        //获取今天四点
        Calendar c4 = Calendar.getInstance();
        c4.set(Calendar.HOUR_OF_DAY, 04);
        c4.set(Calendar.MINUTE, 0);
        c4.set(Calendar.SECOND, 0);
        Date m4 = c4.getTime();
        //获取当前
        Date currentTime = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = df.format(currentTime);
        Date currentTime_2 = df.parse(dateString);
        Calendar calendar = Calendar.getInstance();

        DateFormat time = new SimpleDateFormat("yyyy-MM-dd");
        //超过四点则昨天的夜审过了
        if (currentTime_2.getTime() > m4.getTime()) {
            calendar.add(Calendar.DATE, -1); //得到前一天
        } else {
            calendar.add(Calendar.DATE, -2); //得到前两天
        }
        return everydayRoomPriceDAO.getRemainingEverydayRoomById(time.format(calendar.getTime()), orderChildId);
    }

    //换房
    public void changeRoom(OrderChildBO orderChildBO, List<EverydayRoomPriceBO> everydayRoomPrice) {
        //修改每日房价
        for (EverydayRoomPriceBO e : everydayRoomPrice) {
            everydayRoomPriceDAO.updEverydayRoomPrice(e);
        }
        //加操作日志
        //修改房间状态
        OrderChildBO orderChildOld = orderDAO.getOrderChildById(orderChildBO.getRoomId());
        Map<String, Object> map = new HashMap<String, Object>();
        //之前的房间修改为脏房
        map.put("id", orderChildOld.getRoomId());
        map.put("state", Constants.DIRTY.getValue());
        roomService.updateroomMajorState(map);
        //新住的修改为在住
        map.put("id", orderChildBO.getRoomId());
        map.put("state", Constants.INTHE.getValue());
        roomService.updateroomMajorState(map);
        //修改房间 房型
        orderDAO.updOrderChild(orderChildBO);
    }

    //退房
    public void checkOut(Integer orderChildId, BigDecimal money, Integer userId, Integer hotelId) throws ParseException {
        //备份
        OrderChildBackupParam backup = new OrderChildBackupParam();

        //当前时间
        SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat hh = new SimpleDateFormat("HH");
        Calendar calendar = Calendar.getInstance();
        Date currentTimeDate = calendar.getTime();
        Date currentTime = ymdhms.parse(ymdhms.format(currentTimeDate));
        //获取今天凌晨四点
        calendar.set(Calendar.HOUR_OF_DAY, 04);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date m4 = calendar.getTime();
        //获取今天凌晨六点
        calendar.set(Calendar.HOUR_OF_DAY, 06);
        Date m6 = calendar.getTime();
        //获取下午两点
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.MINUTE, 0);
        calendar2.set(Calendar.SECOND, 0);

        //获取下午两点
        calendar2.set(Calendar.HOUR_OF_DAY, 14);
        Date m2 = calendar2.getTime();
        //订单信息
        OrderChildBO orderChildBO = orderDAO.getOrderChildById(orderChildId);
        //六点后入住 判断当天房费滚没滚 有可能当天入住 当天就退房了
        if (orderChildBO.getStartTime().getTime() > m6.getTime() && ymd.format(currentTimeDate).equals(ymd.format(orderChildBO.getStartTime()))) {
            this.addOrderChildRecordAndRoomRate(backup, currentTimeDate, orderChildBO, userId);
        }
        //入住时间不是当天的凌晨四点以前 并且 退房时间小于凌晨四点没有夜审，所以滚出退房这天的房费
        if (orderChildBO.getStartTime().getTime() > m4.getTime() && new Integer(hh.format(currentTimeDate)) < 4) {
            Date beforeDate = DateUtils.getYesTaday();
            this.addOrderChildRecordAndRoomRate(backup, beforeDate, orderChildBO, userId);
        }

        //获取离店时间
        Date endTime = orderChildBO.getPracticalDepartureTime() == null ? orderChildBO.getEndTime() : orderChildBO.getPracticalDepartureTime();
        //判断是否超时
        if (currentTime.compareTo(endTime) <= 0) {
            Date currentDate = ymd.parse(ymd.format(currentTimeDate));
            Date endDate = ymd.parse(ymd.format(endTime));
            List<EverydayRoomPriceBO> everydayRoomPriceBOList = everydayRoomPriceDAO.getEverydayRoomById(orderChildId);
            //判断是否提前退房
            if (currentDate.compareTo(endDate) < 0 && everydayRoomPriceBOList.size() > 1 && currentTime.compareTo(m2) > 0) {
                this.addOrderChildRecordAndRoomRate2(backup, currentTime, orderChildBO, userId);
            }
            backup.setRoomMajorState(Constants.INTHE.getValue());
        } else {
            //超时滚房费
            this.addOrderChildRecordAndRoomRate3(backup, endTime, orderChildBO, userId, money);
            backup.setRoomMajorState(Constants.TIMEOUT.getValue());
        }


        List<CheckInPersonBO> checkInPersonBOS = checkInPersonService.getCheckInPersonById(orderChildBO.getId(), Constants.CHECKIN.getValue());
        CheckInPersonBO checkInPersonBO = checkInPersonBOS.get(0);
        OrderChildBO orderChildResult = this.getOrderChildById(orderChildId);
        //添加收银汇总
        OrderBO orderBO = orderDAO.getOrderById(orderChildBO.getOrderId());
        cashierSummaryService.addCheck(backup.getRoomRate(), null, orderBO.getOrderNumber(), userId,
                checkInPersonBO.getName(), orderBO.getOTA(), orderBO.getOrderType(),
                orderBO.getChannel(), orderChildResult.getRoomName(), orderChildResult.getRoomTypeName(),
                "房费", hotelId);

        cashierSummaryService.addCheck(backup.getOtherRate(), null, orderBO.getOrderNumber(), userId,
                checkInPersonBO.getName(), orderBO.getOTA(), orderBO.getOrderType(),
                orderBO.getChannel(), orderChildResult.getRoomName(), orderChildResult.getRoomTypeName(),
                "超时费", hotelId);


        //添加备份信息
        backup.setOrderState(orderChildBO.getOrderState());
        backup.setEndTime(orderChildBO.getEndTime());
        backup.setPracticalDepartureTime(orderChildBO.getPracticalDepartureTime());
        backup.setId(orderChildId);
        orderDAO.addOrderChildBackup(backup);

        //修改房间状态
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", orderChildBO.getRoomId());
        map.put("state", Constants.DIRTY.getValue());
        roomService.updateroomMajorState(map);

        //修改入住人状态
        checkInPersonDAO.updPersonCheckOut(orderChildId, Constants.CHECKOUT.getValue());

        //如果是子帐房退房需要把超时费用 和 房费 记录到主账房
        //查询这间房的主账房
        Integer mainId=childOrderService.queryOrderChildMain(orderChildBO.getAlRoomCode());
        OrderChildBO orderChildBOMain=orderDAO.getOrderChildById(mainId);
        OrderChildBO orderChildBOMoney=new OrderChildBO();
        orderChildBOMoney.setOtherRate(orderChildBOMain.getOtherRate().add(orderChildBO.getOtherRate()));
        orderChildBOMoney.setRoomRate(orderChildBOMain.getRoomRate().add(orderChildBO.getRoomRate()));
        orderChildBOMoney.setId(mainId);
        orderDAO.updOrderChild(orderChildBOMoney);

        //修改订单状态
        OrderChildBO orderChildBOState=new OrderChildBO();
        orderChildBOState.setOrderState(Constants.notpaid.getValue());
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        orderChildBOState.setPracticalDepartureTime(dateTimeFormat.parse(dateTimeFormat.format(new Date())));
        orderChildBOState.setId(orderChildBO.getId());
        orderDAO.updOrderChild(orderChildBOState);

    }

    //超时滚房费 根据当前时间计算超时房费
    public void addOrderChildRecordAndRoomRate3(OrderChildBackupParam backup, Date endTime, OrderChildBO orderChild, Integer userId, BigDecimal money) throws ParseException {
        if(money==null) {
            money=new BigDecimal(0);
        }
        SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
        //房型信息
        RoomTypeBO roomTypeBO = roomTypeDAO.getRoomType(orderChild.getRoomTypeId());
        //计算超时费
        Date currentTimeDate = new Date();
        Long minute = DateUtils.getQuotMinute(currentTimeDate, orderChild.getEndTime());
        //超过4小时
        if (minute <= 4 * 60) {
            //加记录
            orderRecordService.addOrderRecord(orderChild.getId(), ymd.format(currentTimeDate) + "半天超时费，减免" + money + "元",
                    null, new BigDecimal(roomTypeBO.getBasicPrice()).divide(new BigDecimal(2)).negate(), Constants.TIMEOUTCOST.getValue(),
                    userId, null, "no");
            orderChild.setOtherRate(new BigDecimal(roomTypeBO.getBasicPrice()).divide(new BigDecimal(2)).subtract(money));
            //备份超时费
            backup.setOtherRate(new BigDecimal(roomTypeBO.getBasicPrice()).divide(new BigDecimal(2)));
        } else {
            //加记录
            orderRecordService.addOrderRecord(orderChild.getId(), ymd.format(currentTimeDate) + "全天超时费，减免" + money + "元",
                    null, new BigDecimal(roomTypeBO.getBasicPrice()).negate(), Constants.TIMEOUTCOST.getValue(),
                    userId, null, "no");

            orderChild.setOtherRate(new BigDecimal(roomTypeBO.getBasicPrice()).subtract(money));
            //备份超时费
            backup.setOtherRate(new BigDecimal(roomTypeBO.getBasicPrice()));
        }
    }

    //提前离店 按超时滚房费 根据时间 子订单id 查询这天的房价 累计到子订单其他房费
    public void addOrderChildRecordAndRoomRate2(OrderChildBackupParam backup, Date date, OrderChildBO orderChild, Integer userId) {
        SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
        //这天的房价信息
        EverydayRoomPriceBO everydayRoomPriceBO = everydayRoomPriceDAO.getRemainingEverydayRoomByIdAndTime(ymd.format(date), orderChild.getId());
        //退房时间超过下午两点
        Calendar c = Calendar.getInstance();
        if (new Integer(c.get(Calendar.HOUR_OF_DAY)) > 14) {
            //超时费
            orderChild.setOtherRate(everydayRoomPriceBO.getMoney());
            //备份超时费
            backup.setOtherRate(everydayRoomPriceBO.getMoney());
            //加记录
            orderRecordService.addOrderRecord(orderChild.getId(), ymd.format(everydayRoomPriceBO.getTime()) + "提前退房收全天超时费",
                    null, everydayRoomPriceBO.getMoney().negate(), Constants.TIMEOUTCOST.getValue(),
                    userId, null, "no");
        } else {
            //加记录
            orderRecordService.addOrderRecord(orderChild.getId(), ymd.format(everydayRoomPriceBO.getTime()) + "提前退房收半天超时费",
                    null, everydayRoomPriceBO.getMoney().divide(new BigDecimal(2)).negate(), Constants.TIMEOUTCOST.getValue(),
                    userId, null, "no");
            //超时费
            orderChild.setOtherRate(everydayRoomPriceBO.getMoney().divide(new BigDecimal(2)));
            //备份超时费
            backup.setOtherRate(everydayRoomPriceBO.getMoney().divide(new BigDecimal(2)));
        }
    }

    //正常滚房费 根据时间 子订单id 查询这天的房价 累计到子订单房费 添加房费记录
    public void addOrderChildRecordAndRoomRate(OrderChildBackupParam backup, Date date, OrderChildBO orderChildBO, Integer userId) {
        SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
        //这天的房价信息
        EverydayRoomPriceBO everydayRoomPriceBO = everydayRoomPriceDAO.getRemainingEverydayRoomByIdAndTime(ymd.format(date), orderChildBO.getId());
        orderRecordService.addOrderRecord(orderChildBO.getId(), ymd.format(everydayRoomPriceBO.getTime()) + "房费",
                null, everydayRoomPriceBO.getMoney().negate(), Constants.ROOMRATE.getValue(),
                userId, "1天", "no");
        //修改房费
        orderChildBO.setRoomRate(everydayRoomPriceBO.getMoney());
        //备份当时交的房费 离店时间
        backup.setRoomRate(everydayRoomPriceBO.getMoney());
    }

    //退房回滚 子订单信息都撤回原数据 添加回滚记录
    public void checkOutRollback(Integer orderChildId, Integer userId, Integer hotelId) {
        //备份的信息
        OrderChildBackupParam backup = orderDAO.getOrderChildBackup(orderChildId);
        //订单信息
        OrderChildBO orderChildBOResult = orderDAO.getOrderChildById(orderChildId);

        //修改回主账房金额
        OrderChildBO orderChildBOMoney =new OrderChildBO();
        Integer mainId=childOrderService.queryOrderChildMain(orderChildBOResult.getAlRoomCode());
        OrderChildBO orderChildBOMoneyResult = orderDAO.getOrderChildById(mainId);
        orderChildBOMoney.setId(mainId);
        orderChildBOMoney.setRoomRate(orderChildBOMoneyResult.getRoomRate().subtract(backup.getRoomRate()));
        System.err.println(orderChildBOMoney.getRoomRate());
        orderChildBOMoney.setOtherRate(orderChildBOMoneyResult.getOtherRate().subtract(backup.getOtherRate()));
        System.err.println(orderChildBOMoney.getOtherRate());
        orderDAO.updOrderChild(orderChildBOMoney);

        //修改回子订单
        OrderChildBO orderChildBO =new OrderChildBO();
        orderChildBO.setOrderState(backup.getOrderState());
        orderChildBO.setEndTime(backup.getEndTime());
        orderChildBO.setPracticalDepartureTime(backup.getPracticalDepartureTime());
        orderChildBO.setId(backup.getId());
        orderDAO.updOrderChildNoValidation(orderChildBO);


        //修改房态
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", orderChildBOResult.getRoomId());
        map.put("state", backup.getRoomMajorState());
        roomService.updateroomMajorState(map);

        //修改入住人状态
        checkInPersonDAO.updPersonCheckOut(orderChildId, Constants.CHECKIN.getValue());

        //添加回滚记录
        orderRecordService.addOrderRecord(orderChildId, "误操作回滚(房费)",
                null, backup.getRoomRate(), null,
                userId, "1天", "no");

        //添加反向收银汇总
        OrderBO orderBO = orderDAO.getOrderById(orderChildBOResult.getOrderId());
        List<CheckInPersonBO> checkInPersonBOS = checkInPersonDAO.getCheckInPersonById(orderChildId, Constants.CHECKIN.getValue());
        CheckInPersonBO checkInPersonBO = checkInPersonBOS.get(0);
        OrderChildBO orderChildResult = this.getOrderChildById(orderChildId);
        cashierSummaryService.addCheck(backup.getRoomRate().multiply(new BigDecimal(-1)), null, orderBO.getOrderNumber(), userId,
                checkInPersonBO.getName(), orderBO.getOTA(), orderBO.getOrderType(),
                orderBO.getChannel(), orderChildResult.getRoomName(), orderChildResult.getRoomTypeName(),
                "房费回滚", hotelId);
        cashierSummaryService.addCheck(backup.getOtherRate().multiply(new BigDecimal(-1)), null, orderBO.getOrderNumber(), userId,
                checkInPersonBO.getName(), orderBO.getOTA(), orderBO.getOrderType(),
                orderBO.getChannel(), orderChildResult.getRoomName(), orderChildResult.getRoomTypeName(),
                "超时费回滚", hotelId);

        //退房回滚
        orderDAO.delOrderChildBackup(orderChildId);
    }

    //获取超时的子订单 修改他们的房间状态
    public void updTimeOutOrder() {
        List<OrderChildBO> orderChildBOS = orderDAO.getTimeOutOrder(Constants.ADMISSIONS.getValue());
        for (OrderChildBO orderChildBO : orderChildBOS) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", orderChildBO.getRoomId());
            map.put("state", Constants.TIMEOUT.getValue());
            roomService.updateroomMajorState(map);

        }

        List<OrderChildBO> orderChildBOS2 = orderDAO.getTimeOutOrder2(Constants.ADMISSIONS.getValue());
        for (OrderChildBO orderChildBO : orderChildBOS2) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", orderChildBO.getRoomId());
            map.put("state", Constants.TIMEOUT.getValue());
            roomService.updateroomMajorState(map);

        }
    }



    //把已经有入住人 ，订单状态为未支付的房间改为已入住 房间修改为在住
    public void updateOrderChildRoom(Integer id) {
        List<OrderChildBO> orderChildBOS = orderDAO.getOrderChildByOrderId5(id, Constants.NOTPAY.getValue());
        for (OrderChildBO orderChildBO : orderChildBOS) {
            orderChildBO.setOrderState(Constants.ADMISSIONS.getValue());
            orderDAO.updOrderChild(orderChildBO);

            //把入住的房间修改为在住
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", orderChildBO.getRoomId());
            map.put("state", Constants.INTHE.getValue());
            roomService.updateroomMajorState(map);
        }
    }

    //查询备份信息
    public OrderChildBackupParam getOrderChildBackup(Integer id) {
        return orderDAO.getOrderChildBackup(id);
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


    public int queryOrderListCount(OrderParam param) {
        return orderDAO.queryOrderListCount(param);
    }
}
