package com.szq.hotel.service;

import com.szq.hotel.common.constants.Constants;
import com.szq.hotel.dao.*;
import com.szq.hotel.entity.bo.*;
import com.szq.hotel.entity.param.OrderChildBackupParam;
import com.szq.hotel.entity.param.OrderParam;
import com.szq.hotel.entity.result.CheckInInfoResult;
import com.szq.hotel.entity.result.CheckRoomPersonResult;
import com.szq.hotel.entity.result.OrderResult;
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

    @Resource
    MemberLevelService memberLevelService;

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    //添加主订单 子订单 入住人 每日价格
    public String addOrderInfo(OrderBO orderBO, List<OrderChildBO> orderChildBOList, Integer userId) {
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
        System.err.println("orderChildBOList======" + orderChildBOList.size());
        for (OrderChildBO orderChild : orderChildBOList) {
            //添加子订单
            orderChild.setOrderId(orderBO.getId());//主订单id
            orderChild.setAlRoomCode(alRoomCode);//联房码
            //预约状态
            if (orderChild.getCheckInPersonBOS() != null && orderChild.getCheckInPersonBOS().size() != 0) {
                orderChild.setOrderState(Constants.NOTPAY.getValue());//状态
            } else {
                orderChild.setOrderState(Constants.RESERVATION.getValue());//状态
            }
            orderChild.setStartTime(orderBO.getCheckTime());//入住时间
            orderChild.setEndTime(orderBO.getCheckOutTime());//离店时间
            orderDAO.addOrderChild(orderChild);//返回子订单id
            orderDAO.updOrderChildUpdateTime(orderChild.getId());//修改支付时间
            //这个房型下的每日价格
            List<EverydayRoomPriceBO> everydayRoomPriceBOList = orderChild.getEverydayRoomPriceBOS();
            if (everydayRoomPriceBOList != null && everydayRoomPriceBOList.size() != 0) {
                for (EverydayRoomPriceBO everydayRoomPriceBO : everydayRoomPriceBOList) {
                    if (everydayRoomPriceBO.getMoney() == null) {
                        everydayRoomPriceBO.setMoney(new BigDecimal(0));
                    }
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
                map.put("remark", "直接入住");
                System.err.println("userId" + userId);
                map.put("userId", userId);
                roomService.updateroomMajorState(map);
            }
//            else {
//                if (orderChild.getRoomId() != null) {
//                    Map<String, Object> map = new HashMap<String, Object>();
//                    map.put("id", orderChild.getRoomId());
//                    map.put("remark", "房间预约");
//                    roomService.updateroomMajorState(map);
//                }
//            }

        }
        //修改总房价
        OrderBO order = orderDAO.getOrderById(orderBO.getId());
        order.setTotalPrice(totalPrice);
        orderDAO.updOrder(order);

        log.info("result:{}", orderBO.getOrderNumber());
        log.info("end addOrderInfo.............................................");
        return orderBO.getOrderNumber();
    }

    //预定入住
    public void reservation(List<OrderChildBO> orderChildBOList, OrderBO orderBO, Integer userId) {
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
                orderDAO.updOrderChildUpdateTime(orderChildBO.getId());//修改支付时间

            }
            //房间状态修改为在住状态
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", orderChildBO.getRoomId());
            map.put("state", Constants.INTHE.getValue());
            map.put("remark", "预约入住");
            map.put("userId", userId);
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
                    if (roomPrice.getMoney() == null) {
                        roomPrice.setMoney(new BigDecimal(0));
                    }
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

        //前端应该传null 有时候会传过来0
        if (orderChildBOList.get(0).getRoomId() != null && orderChildBOList.get(0).getRoomId() == 0) {
            orderChildBOList.get(0).setRoomId(null);
        }
        //旧预约信息和新预约信息 预约的类型不一样
        if ((orderChildBOListOld.get(0).getRoomId() == null && orderChildBOList.get(0).getRoomId() != null) ||
                ((orderChildBOListOld.get(0).getRoomId() != null && orderChildBOList.get(0).getRoomId() == null))) {
            System.err.println("11111111111111");
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
                    if (orderChildNew.getRoomTypeId().equals(orderChildOld.getRoomTypeId()) && !("yes").equals(orderChildOld.getOrderState()) && !"yes".equals(orderChildNew.getOrderState())) {
                        orderChildNew.setId(orderChildOld.getId());
                        orderChildNew.setStartTime(orderBO.getCheckTime());
                        orderChildNew.setEndTime(orderBO.getCheckOutTime());
                        orderDAO.updOrderChild(orderChildNew);
                        orderChildOld.setOrderState("yes");
                        orderChildNew.setOrderState("yes");

                        //删除旧每日房价
                        everydayRoomPriceDAO.delEverydayRoomById(orderChildOld.getId());
                        //添加新的每日房价
                        List<EverydayRoomPriceBO> everydayRoomPriceBOList = orderChildNew.getEverydayRoomPriceBOS();
                        for (EverydayRoomPriceBO everydayRoomPriceBO : everydayRoomPriceBOList) {
                            if (everydayRoomPriceBO.getMoney() == null) {
                                everydayRoomPriceBO.setMoney(new BigDecimal(0));
                            }
                            everydayRoomPriceBO.setOrderChildId(orderChildOld.getId());
                            everydayRoomPriceDAO.addEverydayRoomPrice(everydayRoomPriceBO);
                        }
                    }
                }
            }
            //添加新子订单
            for (OrderChildBO orderChildNew : orderChildBOList) {
                if (!("yes").equals(orderChildNew.getOrderState())) {
                    this.addOrderChildEveryRoomPrice(orderChildNew, orderBO, alRoomCode);
                }
            }
            //旧房型如果和新房型对应不上 取消这个旧子订单
            for (OrderChildBO orderChildOld : orderChildBOListOld) {
                if (!("yes").equals(orderChildOld.getOrderState())) {
                    orderChildOld.setOrderState(Constants.CANCEL.getValue());
                    orderDAO.updOrderChild(orderChildOld);
                }
            }
        } else if (orderChildBOListOld.get(0).getRoomId() != null && orderChildBOList.get(0).getRoomId() != null) {
            //如果新的子订单房间 和 旧房间对应上 则新修改子订单信息
            for (OrderChildBO orderChildNew : orderChildBOList) {
                for (OrderChildBO orderChildOld : orderChildBOListOld) {
                    if (orderChildNew.getRoomId().equals(orderChildOld.getRoomId()) && !"yes".equals(orderChildOld.getOrderState()) && !("yes").equals(orderChildNew.getOrderState())) {
                        orderChildNew.setId(orderChildOld.getId());
                        orderChildNew.setId(orderChildOld.getId());
                        orderChildNew.setStartTime(orderBO.getCheckTime());
                        orderChildNew.setEndTime(orderBO.getCheckOutTime());
                        orderDAO.updOrderChild(orderChildNew);
                        orderChildOld.setOrderState("yes");
                        orderChildNew.setOrderState("yes");
                        //删除旧每日房价
                        everydayRoomPriceDAO.delEverydayRoomById(orderChildOld.getId());
                        //添加新的每日房价
                        List<EverydayRoomPriceBO> everydayRoomPriceBOList = orderChildNew.getEverydayRoomPriceBOS();
                        for (EverydayRoomPriceBO everydayRoomPriceBO : everydayRoomPriceBOList) {
                            everydayRoomPriceBO.setOrderChildId(orderChildOld.getId());
                            if (everydayRoomPriceBO.getMoney() == null) {
                                everydayRoomPriceBO.setMoney(new BigDecimal(0));
                            }
                            everydayRoomPriceDAO.addEverydayRoomPrice(everydayRoomPriceBO);
                        }
                    }
                }
            }
            //添加新子订单
            for (OrderChildBO orderChildNew : orderChildBOList) {
                if (!"yes".equals(orderChildNew.getOrderState())) {
                    System.err.println("loke" + orderChildNew.getOrderState());
                    this.addOrderChildEveryRoomPrice(orderChildNew, orderBO, alRoomCode);
                }
            }
            //旧房型如果和新房型对应不上 取消这个旧子订单
            for (OrderChildBO orderChildOld : orderChildBOListOld) {
                if (!("yes").equals(orderChildOld.getOrderState())) {
                    orderChildOld.setOrderState(Constants.CANCEL.getValue());
                    orderDAO.updOrderChild(orderChildOld);
                }
            }
        }
        //修改主订单信息
        orderDAO.updOrder(orderBO);
    }

    //添加预约信息 子订单信息 每日房价信息
    public void addOrderChildEveryRoomPrice(OrderChildBO orderChildBO, OrderBO orderBO, String alRoomCode) {
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
                if (everydayRoomPriceBO.getMoney() == null) {
                    everydayRoomPriceBO.setMoney(new BigDecimal(0));
                }
                everydayRoomPriceDAO.addEverydayRoomPrice(everydayRoomPriceBO);
            }
        }
    }


    //根据身份证号 手机号查询预约信息
    public OrderBO getOrderInfo(String idNumber, String mobile, Integer hotelId) {
        //String date = this.getDate();
        //当天最后时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = dateFormat.format(DateUtils.getCurrentDayEndTime(null));

        //主订单信息
        OrderBO orderBO = orderDAO.getOrderByIdOrMobile(idNumber, mobile, date, hotelId);
        if (orderBO == null || orderBO.getOrderNumber() == null) {
            return null;
        }
        //查询子订单信息 返回所有预约中的子订单
        List<OrderChildBO> orderChildBOS = this.getNoCheckOrderChildById(orderBO);
        if (orderChildBOS == null || orderChildBOS.size() == 0) {
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
        List<OrderChildBO> orderChildBOS = orderDAO.getOrderChildByOrderId2(orderBO.getId(), Constants.RESERVATION.getValue());
        //按房型选择
//        if (noCheckOrderChildBOS.get(0).getRoomId() == null) {
//            orderChildBOS = orderDAO.getOrderChildByOrderId2(orderBO.getId(), Constants.RESERVATION.getValue());
//        } else {
//            orderChildBOS = orderDAO.getOrderChildByOrderId3(orderBO.getId(), Constants.RESERVATION.getValue());
//        }
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

    //返回当天时间 年月日
    public String getDate() {
        SimpleDateFormat simp = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        //获取当前小时
        int hour = c.get(Calendar.HOUR_OF_DAY);
        System.err.println("hour" + hour);
        if (hour < 6) {
            //当前在六点之前
            c.set(Calendar.DATE, c.get(Calendar.DATE) - 1);
        }
        String str = simp.format(c.getTime());
        return str;
    }

    //根据订单id查询订单信息
    public OrderBO getOrderById(Integer orderId) {
        OrderBO orderBO = orderDAO.getOrderById(orderId);
        orderBO.setOrderChildBOS(this.getOrderChildById(orderBO));
        return orderBO;
    }

    //取消预约中 入住待支付的子订单
    public void closeOrderChild(String orderChildIdS, Integer userId) {
        String[] orderChildS = orderChildIdS.split(",");
        for (int i = 0; i < orderChildS.length; i++) {
            OrderChildBO orderChildResult = orderDAO.getOrderChildById(new Integer(orderChildS[i]));
            //关闭子订单
            OrderChildBO orderChildBO = new OrderChildBO();
            orderChildBO.setId(new Integer(orderChildS[i]));
            orderChildBO.setOrderState(Constants.CANCEL.getValue());
            orderDAO.updOrderChild(orderChildBO);
            //修改入住人状态
            checkInPersonDAO.updPersonCheckOut(orderChildBO.getId(), Constants.CHECKOUT.getValue());
            //修改房态
            if (orderChildResult.getRoomId() != null) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", orderChildResult.getRoomId());
                map.put("state", Constants.VACANT.getValue());
                map.put("remark", "取消订单");
                map.put("userId", userId);
                roomService.updateroomMajorState(map);
            }

        }

    }

    //根据主订单id查询房间信息（客帐管理）
    public List<OrderChildBO> getRoomInfoById(String alCode) {
        List<OrderChildBO> orderChildBOS = orderDAO.getRoomInfoById(alCode);
        int index = -1;
        OrderChildBO orderChildBO = null;
        for (int i = 0; i < orderChildBOS.size(); i++) {
            if (orderChildBOS.get(i).getMain() != null && orderChildBOS.get(i).getMain().equals("yes")) {
                index = i;
                orderChildBO = orderChildBOS.get(i);
                break;
            }
        }
        if (index != -1) {
            orderChildBOS.remove(index);
            orderChildBOS.add(0, orderChildBO);
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
    public List<OrderChildBO> getSubscribeOrderChild(Integer orderId) {
        return orderDAO.getSubscribeOrderChild(orderId, Constants.RESERVATION.getValue());
    }

    //获取入住支付信息
    public List<OrderChildBO> getPayInfo(Integer orderId) {
        List<OrderChildBO> orderChildBOS = orderDAO.getPayInfo(orderId);
        for (OrderChildBO orderChildBO : orderChildBOS) {
            MemberBO memberBO = memberService.selectMemberByCerNumber(orderChildBO.getCertificateNumber());
            System.err.println(memberBO.getId());
            if (memberBO != null) {
                MemberResultBO memberResultBO = memberService.getMemberCardNumber(memberBO.getId());
                System.err.println(memberResultBO.getType());
                if (memberResultBO.getType().equals("yes")) {
                    orderChildBO.setNameStatus("yes");
                } else {
                    orderChildBO.setNameStatus("no");
                }
            } else {
                orderChildBO.setNameStatus("no");
            }
        }

        return orderChildBOS;
    }

    //根据子订单id查询子订单
    public OrderChildBO getOrderChildById(Integer orderChildId) {
        OrderChildBO orderChildBO = orderDAO.getOrderChildById(orderChildId);
        return orderChildBO;
    }


    //获取在住报表
    public List<OrderResult> getCheckInReport(Integer hotelId,Date startTime,Date endTime) {
        return orderDAO.getCheckInReport(hotelId,startTime,endTime);
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
    public void closeOrder(Integer userId) {
        List<OrderChildBO> idList = orderDAO.getCloseOrder();
        if (idList != null && idList.size() != 0) {
            for (OrderChildBO orderChildBO : idList) {
                //修改入住人状态
                checkInPersonDAO.updPersonCheckOut(orderChildBO.getId(), Constants.CHECKOUT.getValue());
                //修改房间状态
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", orderChildBO.getRoomId());
                map.put("state", Constants.VACANT.getValue());
                map.put("remark", "入住未支付，支付时间以过");
                map.put("userId", userId);
                roomService.updateroomMajorState(map);
            }
            //修改订单状态
            orderDAO.closeOrder();

        }

    }

    //首页查询在住信息
    public CheckInInfoResult getCheckInInfo(Integer roomId, Integer hotelId) throws ParseException {
        //当天日期
        SimpleDateFormat simp = new SimpleDateFormat("yyyy-MM-dd");
        Date currDate = simp.parse(this.getDate());
        OrderChildBO orderChildBO = orderDAO.getOrderChildByRoomIdNoTime(roomId, hotelId);
        if (orderChildBO == null) {
            return null;
        }
        Date endTime = orderChildBO.getPracticalDepartureTime() == null ? orderChildBO.getEndTime() : orderChildBO.getPracticalDepartureTime();
        Long minute = DateUtils.getQuotMinute(endTime, orderChildBO.getStartTime());
        //正常小时房 就不能-1了 跨天小时房需要-1 全天房
        if (simp.format(endTime).equals(simp.format(currDate))) {
            if (minute > 4 * 60) {
                currDate = DateUtils.getAppointDate(currDate, -1);
            }

        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(orderChildBO.getStartTime());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        //超过4小时
        System.err.println("入住小时:" + hour);
        if (minute <= 4 * 60 && hour < 6 && hour > 2) {
            currDate = DateUtils.getAppointDate(currDate, -1);
        }
        System.err.println("房价时间" + simp.format(currDate));
        //在住信息
        CheckInInfoResult checkInInfoResult = orderDAO.getOrderChildByRoomId(roomId, simp.format(currDate), hotelId);
        if (checkInInfoResult == null) {
            //当日房价查询不到 则查询末日房价
            if (minute <= 4 * 60 && hour < 6 && hour > 2) {
                checkInInfoResult = orderDAO.getOrderChildByRoomId(roomId, simp.format(endTime), hotelId);
            } else {
                checkInInfoResult = orderDAO.getOrderChildByRoomId(roomId, simp.format(DateUtils.getAppointDate(endTime, -1)), hotelId);
            }
        }
        checkInInfoResult.setEndTime(endTime);
        //同住人信息
        List<CheckInPersonBO> checkInPersonBOS = checkInPersonDAO.getCheckInPersonById(checkInInfoResult.getOrderChildId(), Constants.CHECKIN.getValue());
        checkInInfoResult.setCheckInPersonBOS(checkInPersonBOS);
        //联房信息
        List<CheckRoomPersonResult> checkRoomPersonResults = orderDAO.getOrderRoomByCode(checkInInfoResult.getAlRoomCode());
        checkInInfoResult.setCheckRoomPersonResults(checkRoomPersonResults);
        return checkInInfoResult;
    }

    //查询预约信息
    public CheckInInfoResult getReservationInfo(Integer roomId, Integer hotelId) {
        return orderDAO.getReservationInfo(roomId, hotelId);
    }

    //传入日期 获取对于酒店来说的日期ymd
    public Date getHotelDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour2 = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour2 <= 6) {
            calendar.add(Calendar.DATE, -1);
        }
        return calendar.getTime();
    }

    //修改在住信息
    public void updCheckInInfo(Integer orderId, String channel, String OTA,
                               Integer orderChildId, Date entTime, String remark,
                               String checkInPersonJson, String everyDayRoomPrice, Integer userId) throws ParseException {


        //如果客源传过来修改客源 ota
        if (orderId != null && !orderId.equals("")) {
            OrderBO orderBO = new OrderBO();
            orderBO.setId(orderId);
            orderBO.setChannel(channel);
            orderBO.setOTA(OTA);
            orderDAO.updOrder(orderBO);
        }
        //如果子订单传过来 修改离店时间 修改房间备注
        if (orderChildId != null && !orderChildId.equals("")) {
            //续租时间 天数
            System.out.println("orderChildId" + orderChildId);
            OrderChildBO oldOrderChild = orderDAO.getOrderChildById(orderChildId);
            if (oldOrderChild == null) {
                return;
            }
            Date endTime = oldOrderChild.getPracticalDepartureTime() == null ? oldOrderChild.getEndTime() : oldOrderChild.getPracticalDepartureTime();
            Long minute = DateUtils.getQuotMinute(endTime, oldOrderChild.getStartTime());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(entTime);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            if (hour <= 6) {
                calendar.add(Calendar.DATE, -1);
            }
            Date newEndTime = calendar.getTime();


            Date oldEndTime = this.getHotelDate(endTime);

            System.err.println("看这个" + simpleDateFormat.format(newEndTime));
            System.err.println("看这个" + simpleDateFormat.format(oldEndTime));
            Long day = DateUtils.getQuot(simpleDateFormat.parse(simpleDateFormat.format(newEndTime)), simpleDateFormat.parse(simpleDateFormat.format(oldEndTime)));

            //全天房续住
            if (minute > 4 * 60) {
                //最后一天的价格
                List<EverydayRoomPriceBO> everydayRoomPriceBOS = everydayRoomPriceDAO.getEverydayRoomById(orderChildId);
                if (everydayRoomPriceBOS == null || everydayRoomPriceBOS.size() == 0) {
                    return;
                }
                //末日价格
                BigDecimal oldMoney = everydayRoomPriceBOS.get(everydayRoomPriceBOS.size() - 1).getMoney();
                for (int i = 1; i <= day; i++) {
                    EverydayRoomPriceBO everydayRoomPriceBO = new EverydayRoomPriceBO();
                    everydayRoomPriceBO.setOrderChildId(orderChildId);
                    everydayRoomPriceBO.setMoney(oldMoney);
                    Date newTime = DateUtils.getAppointDate(oldEndTime, i - 1);
                    everydayRoomPriceBO.setTime(simpleDateFormat.parse(simpleDateFormat.format(newTime)));
                    System.err.println("全天房日期" + simpleDateFormat.format(newTime));
                    everydayRoomPriceDAO.addEverydayRoomPrice(everydayRoomPriceBO);
                }
            } else {
                //小时房续住
                //没多住就直接改价
                if (day > 0) {
                    //计算这天的房价
                    //获取预约人 获取预约人会员折扣 计算房价
                    OrderBO orderBO = orderDAO.getOrderById(oldOrderChild.getOrderId());
                    MemberBO memberBO = memberService.selectMemberByPhone(orderBO.getPhone());
                    BigDecimal discount = new BigDecimal(1);
                    if (memberBO != null) {
                        MemberLevelBO memberLevelBO = memberLevelService.getLevelByCardId(memberBO.getMemberCardId());
                        //折扣
                        discount = memberLevelBO.getDiscount();
                    }

                    //房价
                    RoomTypeBO roomTypeBO = roomTypeDAO.getRoomType(oldOrderChild.getRoomTypeId());
                    BigDecimal roomMoney = new BigDecimal(roomTypeBO.getBasicPrice());

                    //计算多住几天
                    for (int i = 1; i <= day; i++) {
                        EverydayRoomPriceBO everydayRoomPriceBO = new EverydayRoomPriceBO();
                        everydayRoomPriceBO.setOrderChildId(orderChildId);
                        everydayRoomPriceBO.setMoney(roomMoney.multiply(discount));
                        Date newTime = DateUtils.getAppointDate(endTime, i);
                        everydayRoomPriceBO.setTime(simpleDateFormat.parse(simpleDateFormat.format(newTime)));
                        System.err.println("小时房日期" + simpleDateFormat.format(newTime));
                        everydayRoomPriceDAO.addEverydayRoomPrice(everydayRoomPriceBO);
                    }
                }

            }

            OrderChildBO orderChildBO = new OrderChildBO();
            orderChildBO.setId(orderChildId);
            orderChildBO.setRemark(remark);
            //修改实际离店时间
            orderChildBO.setPracticalDepartureTime(entTime);
            //判断 是否续租超过一天
            //获取离店时间是哪号 获取续住的离店时间是哪号
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(oldOrderChild.getEndTime());
            int hour2 = calendar2.get(Calendar.HOUR_OF_DAY);
            if (hour2 <= 6) {
                calendar2.add(Calendar.DATE, -1);
            }
            int oldCheckOutDay = calendar2.get(Calendar.DATE);
            int newCheckOutDay = calendar.get(Calendar.DATE);

            if (newCheckOutDay - oldCheckOutDay > 0) {
                orderChildBO.setEndTime(entTime);
            }
            orderDAO.updOrderChild(orderChildBO);

            //修改房态
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", oldOrderChild.getRoomId());
            if (entTime.compareTo(new Date()) > 0) {
                map.put("state", Constants.INTHE.getValue());
            }
            map.put("remark", "续租");
            map.put("userId", userId);
            roomService.updateroomMajorState(map);

            //添加同住人
            if (checkInPersonJson != null && !checkInPersonJson.equals("")) {
                List<CheckInPersonBO> list = JsonUtils.getJSONtoList(checkInPersonJson, CheckInPersonBO.class);

                //删除
                checkInPersonDAO.delCheckInPersonById(orderChildId);
                //全部修改
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
    public List<CheckInPersonBO> getAlRoom(Integer hotelId, String alCode) {
        return orderDAO.getAlRoom(hotelId, alCode);
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
    public void addAlRoom(Integer orderChildId, String orderChildIds, Integer userId) throws Exception {
        //旧主账房
        String[] orderChildIdArr = orderChildIds.split(",");
        //新主账房
        OrderChildBO orderChildBONew = orderDAO.getOrderChildById(orderChildId);
        //旧主账房 取消主账房 联房码修改为新主账房的联房码
        for (int i = 0; i < orderChildIdArr.length; i++) {
            //主账房的账单记录id
            String ids = "";
            System.err.println("orderChildIdArr[" + i + "]" + orderChildIdArr[i]);
            List<OrderRecoredBO> orderRecoredBO = orderRecordService.queryOrderRecord(new Integer(orderChildIdArr[i]));
            System.err.println("orderRecoredBO.size" + orderRecoredBO.size());
            for (int y = 0; y < orderRecoredBO.size(); y++) {
                ids = ids + orderRecoredBO.get(y).getId() + ",";
            }
            System.err.println("ids" + ids);
            //房间消费转账到新的主账房 添加消费记录
            if (!ids.equals("")) {
                System.err.println("orderChildId" + orderChildId);
                childOrderService.transferAccounts(userId, ids, orderChildId, new Integer(orderChildIdArr[i]));
            }

            //修改旧联房码
            OrderChildBO orderChildBO = orderDAO.getOrderChildById(new Integer(orderChildIdArr[i]));
            List<OrderChildBO> orderChildBOList = orderDAO.getOrderByCode(orderChildBO.getAlRoomCode(), null);
            for (OrderChildBO child : orderChildBOList) {
                child.setPayCashNum(new BigDecimal(0));
                child.setOtherPayNum(new BigDecimal(0));
                child.setAlRoomCode(orderChildBONew.getAlRoomCode());
                child.setMain("no");
                orderDAO.updOrderChild(child);
            }
        }
        //根据新主账房 查询出来这个联房码的旧主账房
        OrderChildBO orderChildBo = orderDAO.getOrderChildById2(orderChildId);
        List<OrderChildBO> orderChildBOList = orderDAO.getOrderByCode(orderChildBo.getAlRoomCode(), null);
        for (OrderChildBO child : orderChildBOList) {
            child.setPayCashNum(new BigDecimal(0));
            child.setOtherPayNum(new BigDecimal(0));
            child.setAlRoomCode(orderChildBONew.getAlRoomCode());
            child.setMain("no");
            orderDAO.updOrderChild(child);
        }

        //设置新主账房
        OrderChildBO mainOrderChild = new OrderChildBO();
        mainOrderChild.setId(orderChildId);
        mainOrderChild.setMain("yes");
        orderDAO.updOrderChild(mainOrderChild);
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
        //换房时候没到凌晨四点 也就是说昨天的房费没夜审 则也能查出昨天的房价
        if (currentTime_2.getTime() < m4.getTime()) {
            calendar.add(Calendar.DATE, -1); //得到前一天
        }

        System.err.println(time.format(calendar.getTime()));
        return everydayRoomPriceDAO.getRemainingEverydayRoomById(time.format(calendar.getTime()), orderChildId);
    }

    //换房
    public void changeRoom(OrderChildBO orderChildBO, List<EverydayRoomPriceBO> everydayRoomPrice, Integer userId) {
        //修改每日房价
        for (EverydayRoomPriceBO e : everydayRoomPrice) {
            everydayRoomPriceDAO.updEverydayRoomPrice(e);
        }
        //修改房间状态
        OrderChildBO orderChildOld = orderDAO.getOrderChildById(orderChildBO.getId());
        Map<String, Object> map = new HashMap<String, Object>();
        //之前的房间修改为脏房
        map.put("id", orderChildOld.getRoomId());
        map.put("state", Constants.DIRTY.getValue());
        map.put("remark", "由此房换入其他房");
        map.put("userId", userId);
        roomService.updateroomMajorState(map);

        //新住的修改为在住
        map.put("id", orderChildBO.getRoomId());
        map.put("state", Constants.INTHE.getValue());
        map.put("remark", "由其他房换入此房");
        map.put("userId", userId);
        roomService.updateroomMajorState(map);
        //修改房间 房型
        orderDAO.updOrderChild(orderChildBO);
    }

    //根据子订单id查询子订单 退房信息
    public OrderChildBO getCheckOutInfo(Integer orderChildId) {
        try {
            OrderChildBO orderChildBO = orderDAO.getOrderChildById(orderChildId);
            Date endTime = orderChildBO.getPracticalDepartureTime() == null ? orderChildBO.getEndTime() : orderChildBO.getPracticalDepartureTime();
            Long minute = DateUtils.getQuotMinute(endTime, orderChildBO.getStartTime());
            if (minute > 4 * 60) {
                Calendar calendar2 = Calendar.getInstance();
                calendar2.setTime(endTime);
                calendar2.set(Calendar.HOUR_OF_DAY, 14);
                endTime = calendar2.getTime();
            }
            SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat hh = new SimpleDateFormat("HH");

            Calendar calendar = Calendar.getInstance();
            //当前时间
            Date currentTimeDate = calendar.getTime();
            if(calendar.get(Calendar.HOUR_OF_DAY)<6){
                calendar.add(Calendar.DAY_OF_WEEK, -1); //得到前一天
            }
            //当前酒店的时间
            Date hotelDate = new Date();
            if (new Integer(hh.format(currentTimeDate)) < 4) {
                Calendar calendarHotel=Calendar.getInstance();
                calendarHotel.setTime(hotelDate);
                calendarHotel.set(Calendar.MINUTE, 59);
                calendarHotel.set(Calendar.SECOND, 59);
                calendarHotel.set(Calendar.HOUR_OF_DAY, 23);
                hotelDate = DateUtils.getBeforeDay(calendarHotel.getTime(), -1);
            }
            //获取今天凌晨六点
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.HOUR_OF_DAY, 06);
            Date m6 = calendar.getTime();
            //获取下午两点
            calendar.set(Calendar.HOUR_OF_DAY, 14);
            Date m2 = calendar.getTime();
            //获取订单信息 并清空金额
            orderChildBO.setOtherRate(new BigDecimal(0));
            orderChildBO.setRoomRate(new BigDecimal(0));
            Date startTime = orderChildBO.getStartTime();
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(startTime);
            int hour = calendar1.get(Calendar.HOUR_OF_DAY);
            if (hour < 4) {
                calendar1.add(Calendar.DAY_OF_WEEK, -1); //得到前一天
                startTime = calendar1.getTime();
            }

            //当天退房
            if (ymd.format(hotelDate).equals(ymd.format(startTime))) {
                Date date=hotelDate;
                Calendar calendar2 = Calendar.getInstance();
                calendar2.setTime(hotelDate);
                int hour2 = calendar2.get(Calendar.HOUR_OF_DAY);
                if (hour2 >= 4&&hour2<6) {
                    calendar2.add(calendar2.DAY_OF_WEEK, -1); //得到前一天
                    date = calendar2.getTime();
                }
                //这天的房价信息
                EverydayRoomPriceBO everydayRoomPriceBO = everydayRoomPriceDAO.getRemainingEverydayRoomByIdAndTime(ymd.format(date), orderChildBO.getId());
                //bug:六点前 四点后入住算前一天没有滚房费 然后退房时候需滚出前天房费 目前认为 不滚也是合理的吧
                if(hour>4&&hour<6){
                    Date beforeDate=DateUtils.getAppointDate(date,-1);
                    //这天的房价信息
                    EverydayRoomPriceBO everydayRoomPriceBO2 = everydayRoomPriceDAO.getRemainingEverydayRoomByIdAndTime(ymd.format(beforeDate), orderChildBO.getId());
                    orderChildBO.setRoomRate(orderChildBO.getRoomRate().add(everydayRoomPriceBO2.getMoney()));
                }

                //修改房费
                orderChildBO.setRoomRate(orderChildBO.getRoomRate().add(everydayRoomPriceBO.getMoney()));
            }


            //计算超时费信息
            if (currentTimeDate.compareTo(endTime) < 0) {
                Date currentDate = ymd.parse(ymd.format(hotelDate));
                Date endDate = ymd.parse(ymd.format(endTime));
                //剩余每日房价
                List<EverydayRoomPriceBO> everydayRoomPriceBOList = this.getRemainingLease(orderChildId);
                //提前退房
                System.err.println(currentDate.compareTo(endDate) < 0);
                System.err.println((hotelDate.compareTo(m2) > 0));
                System.err.println(orderChildBO.getStartTime().compareTo(m6) < 0);
                System.err.println(everydayRoomPriceBOList.size() > 1);
                if (currentDate.compareTo(endDate) < 0 && hotelDate.compareTo(m2) > 0 && orderChildBO.getStartTime().compareTo(m6) < 0 && everydayRoomPriceBOList.size() >=1) {
                    //这天的房价信息
                    EverydayRoomPriceBO everydayRoomPriceBO = everydayRoomPriceDAO.getRemainingEverydayRoomByIdAndTime(ymd.format(hotelDate), orderChildBO.getId());
                    //超时时间超过4个小时 按这天房价的全天房价
                    Calendar calendar4 = Calendar.getInstance();
                    Integer currHour = calendar4.get(Calendar.HOUR_OF_DAY);
                    if (currHour <= 4 || currHour >= 18) {
                        //超时费
                        orderChildBO.setTimeoutRate(everydayRoomPriceBO.getMoney());
                    } else {
                        orderChildBO.setTimeoutRate(everydayRoomPriceBO.getMoney().divide(new BigDecimal(2)));
                    }
                }
            } else {
                //房型信息
                RoomTypeBO roomTypeBO = roomTypeDAO.getRoomType(orderChildBO.getRoomTypeId());
                //计算超时费
                Long minute2 = DateUtils.getQuotMinute(currentTimeDate, endTime);
                //超过4小时
                if (minute2 <= 4 * 60) {
                    orderChildBO.setTimeoutRate(new BigDecimal(roomTypeBO.getBasicPrice()).divide(new BigDecimal(2)));
                } else {
                    orderChildBO.setTimeoutRate(new BigDecimal(roomTypeBO.getBasicPrice()));
                }
            }
            return orderChildBO;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }


    }

    //退房
    public void checkOut(Integer orderChildId, BigDecimal money, Integer userId, Integer hotelId) throws ParseException {
        if(money==null){
            money=new BigDecimal(0);
        }
        //备份
        OrderChildBackupParam backup = new OrderChildBackupParam();

        SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat hh = new SimpleDateFormat("HH");
        Calendar calendar = Calendar.getInstance();
        //当前时间
        Date currentTimeDate = calendar.getTime();
        if(calendar.get(Calendar.HOUR_OF_DAY)<6){
            calendar.add(Calendar.DAY_OF_WEEK, -1); //得到前一天
        }
        //当前酒店的时间
        Date hotelDate = new Date();
        if (new Integer(hh.format(currentTimeDate)) < 4) {
            Calendar calendarHotel=Calendar.getInstance();
            calendarHotel.setTime(hotelDate);
            calendarHotel.set(Calendar.MINUTE, 59);
            calendarHotel.set(Calendar.SECOND, 59);
            calendarHotel.set(Calendar.HOUR_OF_DAY, 23);
            hotelDate = DateUtils.getBeforeDay(calendarHotel.getTime(), -1);
        }

        //获取今天凌晨四点
        calendar.set(Calendar.HOUR_OF_DAY, 04);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date m4 = calendar.getTime();
        //获取今天凌晨六点
        calendar.set(Calendar.HOUR_OF_DAY, 06);
        Date m6 = calendar.getTime();
        //获取下午两点
        calendar.set(Calendar.HOUR_OF_DAY, 14);
        Date m2 = calendar.getTime();
        //获取订单信息 并清空金额
        OrderChildBO orderChildBO = orderDAO.getOrderChildById(orderChildId);
        orderChildBO.setOtherRate(new BigDecimal(0));
        orderChildBO.setRoomRate(new BigDecimal(0));

        //为了避免凌晨四点前退房
        Date startTime = orderChildBO.getStartTime();
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(startTime);
        int hour = calendar1.get(Calendar.HOUR_OF_DAY);
        if (hour < 4) {
            calendar1.add(Calendar.DAY_OF_WEEK, -1); //得到前一天
            startTime = calendar1.getTime();
        }
        //获取离店时间
        Date endTime = orderChildBO.getPracticalDepartureTime() == null ? orderChildBO.getEndTime() : orderChildBO.getPracticalDepartureTime();
        Long minute = DateUtils.getQuotMinute(endTime, orderChildBO.getStartTime());
        if (minute > 4 * 60) {
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(endTime);
            calendar2.set(Calendar.HOUR_OF_DAY, 14);
            endTime = calendar2.getTime();
        }

        //当天入住当天退房 滚一天当天房费
        if (ymd.format(hotelDate).equals(ymd.format(startTime))) {
            Date date=hotelDate;
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(hotelDate);
            int hour2 = calendar2.get(Calendar.HOUR_OF_DAY);
            if (hour2 >= 4&&hour2<6) {
                calendar2.add(calendar2.DAY_OF_WEEK, -1); //得到前一天
                date = calendar2.getTime();
            }
            this.addOrderChildRecordAndRoomRate(backup, date, orderChildBO, userId);

            //bug:六点前 四点后入住算前一天没有滚房费 然后退房时候需滚出前天房费 目前认为 不滚也是合理的吧
            if(hour>4&&hour<6){
                Date beforeDate=DateUtils.getAppointDate(date,-1);
                this.addOrderChildRecordAndRoomRate(backup, beforeDate, orderChildBO, userId);
            }
        }

        //未超时
        if (currentTimeDate.compareTo(endTime) < 0) {
            Date currentDate = ymd.parse(ymd.format(hotelDate));
            Date endDate = ymd.parse(ymd.format(endTime));
            //剩余每日房价
            List<EverydayRoomPriceBO> everydayRoomPriceBOList = this.getRemainingLease(orderChildId);
            //提前退房
            System.err.println("hotelDate"+ymdhms.format(hotelDate));
            System.err.println("m2"+ymdhms.format(m2));
            System.err.println("hotelDate.compareTo(m2) > 0"+(hotelDate.compareTo(m2) > 0));
            if (currentDate.compareTo(endDate) < 0 && hotelDate.compareTo(m2) > 0 && orderChildBO.getStartTime().compareTo(m6) < 0 && everydayRoomPriceBOList.size() >= 1) {
                this.addOrderChildRecordAndRoomRate2(backup, hotelDate, orderChildBO, userId, money);
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
        if (orderChildBO.getRoomRate().intValue() > 0) {
            cashierSummaryService.addAccount(Constants.ROOMRATE.getValue(), orderChildBO.getRoomRate(), orderBO.getOrderNumber(), userId,
                    checkInPersonBO.getName(), orderBO.getOTA(), orderBO.getOrderType(),
                    orderBO.getChannel(), orderChildResult.getRoomName(), orderChildResult.getRoomTypeName(),
                    "房费", hotelId);
        }
        if (orderChildBO.getOtherRate().intValue() > 0) {
            cashierSummaryService.addAccount(Constants.TIMEOUTCOST.getValue(), orderChildBO.getOtherRate(), orderBO.getOrderNumber(), userId,
                    checkInPersonBO.getName(), orderBO.getOTA(), orderBO.getChannel(), orderBO.getOrderType(),
                    orderChildResult.getRoomName(), orderChildResult.getRoomTypeName(),
                    "超时费", hotelId);
        }
        if (money.compareTo(new BigDecimal(0)) != 0) {
            cashierSummaryService.addAccount(Constants.MITIGATE.getValue(), money.negate(), orderBO.getOrderNumber(), userId,
                    checkInPersonBO.getName(), orderBO.getOTA(), orderBO.getChannel(), orderBO.getOrderType(),
                    orderChildResult.getRoomName(), orderChildResult.getRoomTypeName(),
                    "超时费减免", hotelId);
        }


        //添加备份信息
        backup.setOrderState(orderChildBO.getOrderState());
        backup.setEndTime(orderChildBO.getEndTime());
        backup.setPracticalDepartureTime(orderChildBO.getPracticalDepartureTime());
        backup.setId(orderChildId);
        backup.setTimeoutRate(money);
        orderDAO.addOrderChildBackup(backup);

        //修改房间状态
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", orderChildBO.getRoomId());
        map.put("state", Constants.DIRTY.getValue());
        map.put("remark", "退房");
        map.put("userId", userId);
        roomService.updateroomMajorState(map);

        //修改入住人状态
        checkInPersonDAO.updPersonCheckOut(orderChildId, Constants.CHECKOUT.getValue());

        //如果是子帐房退房需要把超时费用 和 房费 记录到主账房
        //查询这间房的主账房
        Integer mainId = childOrderService.queryOrderChildMain(orderChildBO.getAlRoomCode());
        OrderChildBO orderChildBOMain = orderDAO.getOrderChildById(mainId);
        OrderChildBO orderChildBOMoney = new OrderChildBO();
        orderChildBOMoney.setOtherRate(orderChildBOMain.getOtherRate().add(orderChildBO.getOtherRate()));
        orderChildBOMoney.setRoomRate(orderChildBOMain.getRoomRate().add(orderChildBO.getRoomRate()));
        orderChildBOMoney.setFreeRateNum(orderChildBOMain.getFreeRateNum().add(orderChildBO.getFreeRateNum().negate()));
        orderChildBOMoney.setId(mainId);
        orderDAO.updOrderChild(orderChildBOMoney);

        //修改订单状态
        OrderChildBO orderChildBOState = new OrderChildBO();
        orderChildBOState.setOrderState(Constants.notpaid.getValue());
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        orderChildBOState.setPracticalDepartureTime(dateTimeFormat.parse(dateTimeFormat.format(new Date())));
        orderChildBOState.setId(orderChildBO.getId());
        orderDAO.updOrderChild(orderChildBOState);

    }

    //超时滚房费 根据当前时间计算超时房费
    public void addOrderChildRecordAndRoomRate3(OrderChildBackupParam backup, Date endTime, OrderChildBO orderChild, Integer userId, BigDecimal money) throws ParseException {
        SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
        //房型信息
        RoomTypeBO roomTypeBO = roomTypeDAO.getRoomType(orderChild.getRoomTypeId());
        //计算超时费
        Date currentTimeDate = new Date();
        Long minute = DateUtils.getQuotMinute(currentTimeDate, endTime);
        //超过4小时
        if (minute <= 4 * 60) {
            orderChild.setOtherRate(new BigDecimal(roomTypeBO.getBasicPrice()).divide(new BigDecimal(2)));
            orderChild.setFreeRateNum(money);
            backup.setOtherRate(new BigDecimal(roomTypeBO.getBasicPrice()).divide(new BigDecimal(2)));
            backup.setTimeoutRate(money);
            //加记录
            Integer recordId = orderRecordService.addOrderRecord(orderChild.getId(), ymd.format(currentTimeDate) + "半天超时费",
                    null, orderChild.getOtherRate().negate(), Constants.TIMEOUTCOST.getValue(),
                    userId, null, "no");
            backup.setOtherRateRecordId(recordId);

            //减免费用
            if (money.compareTo(new BigDecimal(0)) != 0) {
                Integer recordId2 = orderRecordService.addOrderRecord(orderChild.getId(),  "超时减免",
                        null, money, Constants.MITIGATE.getValue(),
                        userId, null, "no");
                backup.setTimeoutRateRecordId(recordId2);
            }
        } else {
            orderChild.setOtherRate(new BigDecimal(roomTypeBO.getBasicPrice()));
            orderChild.setFreeRateNum(money);
            backup.setOtherRate(orderChild.getOtherRate());
            backup.setTimeoutRate(money);
            //加记录
            Integer recordId = orderRecordService.addOrderRecord(orderChild.getId(), ymd.format(currentTimeDate) + "全天超时费",
                    null, orderChild.getOtherRate().negate(), Constants.TIMEOUTCOST.getValue(),
                    userId, null, "no");
            backup.setOtherRateRecordId(recordId);

            //减免费用
            if (money.compareTo(new BigDecimal(0)) != 0) {
                Integer recordId2 = orderRecordService.addOrderRecord(orderChild.getId(), "超时减免",
                        null, money, Constants.MITIGATE.getValue(),
                        userId, null, "no");
                backup.setTimeoutRateRecordId(recordId2);
            }
        }
    }

    //提前离店 按超时滚房费 根据时间 子订单id 查询这天的房价 累计到子订单其他房费
    public void addOrderChildRecordAndRoomRate2(OrderChildBackupParam backup, Date date, OrderChildBO orderChild, Integer userId, BigDecimal money) {
        SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
        //这天的房价信息
        EverydayRoomPriceBO everydayRoomPriceBO = everydayRoomPriceDAO.getRemainingEverydayRoomByIdAndTime(ymd.format(date), orderChild.getId());
        //超时时间超过4个小时 按这天房价的全天房价
        Calendar calendar = Calendar.getInstance();
        Integer currHour = calendar.get(Calendar.HOUR_OF_DAY);
        if (currHour <= 4 || currHour >= 18) {
            //超时费
            orderChild.setOtherRate(everydayRoomPriceBO.getMoney());
            //减免费用
            orderChild.setFreeRateNum(money);
            //备份超时费
            backup.setOtherRate(everydayRoomPriceBO.getMoney());
            //备份减免
            backup.setTimeoutRate(money);
            //加记录
            Integer recordId = orderRecordService.addOrderRecord(orderChild.getId(), ymd.format(everydayRoomPriceBO.getTime()) + "提前退房收全天超时费",
                    null, everydayRoomPriceBO.getMoney().negate(), Constants.TIMEOUTCOST.getValue(),
                    userId, null, "no");
            backup.setOtherRateRecordId(recordId);
            //减免费用
            if (money.compareTo(new BigDecimal(0)) != 0) {
                Integer recordId2 = orderRecordService.addOrderRecord(orderChild.getId(), "超时减免",
                        null, money, Constants.MITIGATE.getValue(),
                        userId, null, "no");
                backup.setTimeoutRateRecordId(recordId2);
            }

        } else {
            //超时费
            orderChild.setOtherRate(everydayRoomPriceBO.getMoney().divide(new BigDecimal(2)));
            //减免费用
            orderChild.setFreeRateNum(money);
            //备份超时费
            backup.setOtherRate(everydayRoomPriceBO.getMoney().divide(new BigDecimal(2)));
            //备份减免
            backup.setTimeoutRate(money);
            //加记录
            Integer recordId = orderRecordService.addOrderRecord(orderChild.getId(), ymd.format(everydayRoomPriceBO.getTime()) + "提前退房收半天超时费",
                    null, everydayRoomPriceBO.getMoney().divide(new BigDecimal(2)).negate(), Constants.TIMEOUTCOST.getValue(),
                    userId, null, "no");
            backup.setOtherRateRecordId(recordId);
            //减免费用
            if (money.compareTo(new BigDecimal(0)) != 0) {
                Integer recordId2 = orderRecordService.addOrderRecord(orderChild.getId(), "超时减免",
                        null, money, Constants.MITIGATE.getValue(),
                        userId, null, "no");
                backup.setTimeoutRateRecordId(recordId2);
            }

        }
    }

    //正常滚房费 根据时间 子订单id 查询这天的房价 累计到子订单房费 添加房费记录
    public void addOrderChildRecordAndRoomRate(OrderChildBackupParam backup, Date date, OrderChildBO orderChildBO, Integer userId) {
        SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
        //这天的房价信息
        EverydayRoomPriceBO everydayRoomPriceBO = everydayRoomPriceDAO.getRemainingEverydayRoomByIdAndTime(ymd.format(date), orderChildBO.getId());
        Integer recordId = orderRecordService.addOrderRecord(orderChildBO.getId(), ymd.format(everydayRoomPriceBO.getTime()) + "房费",
                null, everydayRoomPriceBO.getMoney().negate(), Constants.ROOMRATE.getValue(),
                userId, "1天", "no");
        //修改房费
        orderChildBO.setRoomRate(everydayRoomPriceBO.getMoney().add(orderChildBO.getRoomRate()));
        //备份房费
        backup.setRoomRate(everydayRoomPriceBO.getMoney().add(backup.getRoomRate()));
        backup.setRoomRateRecordId(recordId);
    }

    //退房回滚 子订单信息都撤回原数据 添加回滚记录
    public void checkOutRollback(Integer orderChildId, Integer userId, Integer hotelId) {
        //备份的信息
        OrderChildBackupParam backup = orderDAO.getOrderChildBackup(orderChildId);
        if (backup.getOtherRateRecordId() != null) {
            OrderRecoredBO orderRecoredBO = orderRecordService.queryOrderRecordById(backup.getOtherRateRecordId());
            if (orderRecoredBO.getState().equals("yes")) {
                backup.setOtherRate(new BigDecimal(0));
            }
        }
        if (backup.getRoomRateRecordId() != null) {
            OrderRecoredBO orderRecoredBO = orderRecordService.queryOrderRecordById(backup.getRoomRateRecordId());
            if (orderRecoredBO.getState().equals("yes")) {
                backup.setRoomRate(new BigDecimal(0));
            }
        }
        if (backup.getTimeoutRateRecordId() != null) {
            OrderRecoredBO orderRecoredBO = orderRecordService.queryOrderRecordById(backup.getTimeoutRateRecordId());
            if (orderRecoredBO.getState().equals("yes")) {
                backup.setTimeoutRate(new BigDecimal(0));
            }
        }

        //订单信息
        OrderChildBO orderChildBOResult = orderDAO.getOrderChildById(orderChildId);

        //修改回主账房金额
        OrderChildBO orderChildBOMoney = new OrderChildBO();
        Integer mainId = childOrderService.queryOrderChildMain(orderChildBOResult.getAlRoomCode());
        OrderChildBO orderChildBOMoneyResult = orderDAO.getOrderChildById(mainId);
        orderChildBOMoney.setId(mainId);
        orderChildBOMoney.setRoomRate(orderChildBOMoneyResult.getRoomRate().subtract(backup.getRoomRate()));
        orderChildBOMoney.setOtherRate(orderChildBOMoneyResult.getOtherRate().subtract(backup.getOtherRate()));
        System.err.println("backup.getTimeoutRate()"+backup.getTimeoutRate());
        orderChildBOMoney.setFreeRateNum(orderChildBOMoneyResult.getFreeRateNum().add(backup.getTimeoutRate()));
        orderDAO.updOrderChildMoney(orderChildBOMoney);

        //修改回子订单
        OrderChildBO orderChildBO = new OrderChildBO();
        orderChildBO.setOrderState(backup.getOrderState());
        orderChildBO.setEndTime(backup.getEndTime());
        orderChildBO.setPracticalDepartureTime(backup.getPracticalDepartureTime());
        orderChildBO.setId(backup.getId());
        orderDAO.updOrderChildNoValidation(orderChildBO);


        //修改房态
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", orderChildBOResult.getRoomId());
        map.put("state", backup.getRoomMajorState());
        map.put("remark", "退房回滚");
        map.put("userId", userId);
        roomService.updateroomMajorState(map);

        //修改入住人状态
        checkInPersonDAO.updPersonCheckOut(orderChildId, Constants.CHECKIN.getValue());

        //添加回滚记录
        if (backup.getRoomRate().compareTo(new BigDecimal(0)) != 0) {
            orderRecordService.addOrderRecord(orderChildId, "误操作回滚(房费)",
                    null, backup.getRoomRate(), Constants.ROOMRATE.getValue(),
                    userId, "1天", "no");
        }

        //添加超时费回滚记录
        if (backup.getOtherRate().compareTo(new BigDecimal(0)) != 0) {
            orderRecordService.addOrderRecord(orderChildId, "误操作回滚(超时费)",
                    null, backup.getOtherRate(), Constants.TIMEOUTCOST.getValue(),
                    userId, null, "no");
        }

        //添加免减费回滚记录
        if (backup.getTimeoutRate().compareTo(new BigDecimal(0)) != 0) {
            orderRecordService.addOrderRecord(orderChildId, "误操作回滚(超时费减免)",
                    null, backup.getTimeoutRate().multiply(new BigDecimal(-1)), Constants.MITIGATE.getValue(),
                    userId, null, "no");
        }

        //添加反向收银汇总
        OrderBO orderBO = orderDAO.getOrderById(orderChildBOResult.getOrderId());
        List<CheckInPersonBO> checkInPersonBOS = checkInPersonDAO.getCheckInPersonById(orderChildId, Constants.CHECKIN.getValue());
        CheckInPersonBO checkInPersonBO = checkInPersonBOS.get(0);
        OrderChildBO orderChildResult = this.getOrderChildById(orderChildId);

        if (backup.getRoomRate().intValue() != 0) {
            cashierSummaryService.addAccount(Constants.ROOMRATEFREE.getValue(), backup.getRoomRate().multiply(new BigDecimal(-1)), orderBO.getOrderNumber(), userId,
                    checkInPersonBO.getName(), orderBO.getOTA(), orderBO.getOrderType(),
                    orderBO.getChannel(), orderChildResult.getRoomName(), orderChildResult.getRoomTypeName(),
                    "房费回滚", hotelId);
        }

        if (backup.getOtherRate().intValue() != 0) {
            cashierSummaryService.addAccount(Constants.TIMEOUTCOST.getValue(), backup.getOtherRate().multiply(new BigDecimal(-1)), orderBO.getOrderNumber(), userId,
                    checkInPersonBO.getName(), orderBO.getOTA(), orderBO.getOrderType(),
                    orderBO.getChannel(), orderChildResult.getRoomName(), orderChildResult.getRoomTypeName(),
                    "超时费回滚", hotelId);
        }

        if (backup.getTimeoutRate().intValue() != 0) {
            cashierSummaryService.addAccount(Constants.MITIGATE.getValue(), backup.getTimeoutRate(), orderBO.getOrderNumber(), userId,
                    checkInPersonBO.getName(), orderBO.getOTA(), orderBO.getChannel(), orderBO.getOrderType(),
                    orderChildResult.getRoomName(), orderChildResult.getRoomTypeName(),
                    "超时费减免回滚", hotelId);
        }

        //退房回滚
        orderDAO.delOrderChildBackup(orderChildId);
    }

    //获取超时的子订单 修改他们的房间状态
    public void updTimeOutOrder(Integer userId) {
        List<OrderChildBO> orderChildBOS = orderDAO.getTimeOutOrder(Constants.ADMISSIONS.getValue());
        for (OrderChildBO orderChildBO : orderChildBOS) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", orderChildBO.getRoomId());
            map.put("state", Constants.TIMEOUT.getValue());
            map.put("remark", "入住超时");
            map.put("userId", userId);
            roomService.updateroomMajorState(map);
        }

    }


    //把已经有入住人 ，订单状态为未支付的房间改为已入住 房间修改为在住
    public void updateOrderChildRoom(Integer id, Integer userId) {
        List<OrderChildBO> orderChildBOS = orderDAO.getOrderChildByOrderId5(id, Constants.NOTPAY.getValue());
        for (OrderChildBO orderChildBO : orderChildBOS) {
            orderChildBO.setOrderState(Constants.ADMISSIONS.getValue());
            orderDAO.updOrderChild(orderChildBO);

            //把入住的房间修改为在住
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", orderChildBO.getRoomId());
            map.put("state", Constants.INTHE.getValue());
            map.put("remark", "入住支付");
            map.put("userId", userId);
            roomService.updateroomMajorState(map);
        }
    }

    //查询备份信息
    public OrderChildBackupParam getOrderChildBackup(Integer id) {
        return orderDAO.getOrderChildBackup(id);
    }


    /**
     * 验证这个房间或者房型  是否可以续租或者入住 是否会与预约中的房间房型发生冲突
     *
     * @param roomId               房间id 没选房间可以传null
     * @param roomType             房型id
     * @param endTime              离店时间
     * @param startTime            入住时间
     * @param reservationRoomCount 这个房型要入住的数量
     * @param roomId               房间id
     * @param orderId              主订单id 没有可以为null
     */
    public boolean getOrderChildCountByRoomIdByTime(Integer roomId, Integer roomType, Date endTime, Date startTime, Integer reservationRoomCount, Integer orderId, Integer hotelId) {
        //验证房间
        if (roomId != null) {
            Integer roomCount = orderDAO.getOrderChildCountByRoomIdByTime(roomId, DateUtils.longDate(endTime), DateUtils.longDate(startTime), orderId, hotelId);
            if (roomCount > 0) {
                return false;
            }
        }

        //bug验证房型
        if (roomType != null) {
            //拆分获取多段结束日期
            List<String> dateList = new ArrayList<String>();
            dateList.add(DateUtils.longDate(startTime));
            int count = 1;
            while (true) {
                Date afterDate = DateUtils.getAppointDate(startTime, count);
                if (afterDate.compareTo(endTime) <= 0) {
                    dateList.add(DateUtils.longDate(afterDate));
                    count++;
                } else {
                    break;
                }
            }
            //获取可用房间数
            Integer roomCount = orderDAO.getRoomCountByRoomTypeIdByTime(roomType, DateUtils.longDate(endTime), DateUtils.longDate(startTime), hotelId, roomId);
            for (int i = 0; i < dateList.size() - 1; i++) {
                //获取订单数
                Integer orderCount = orderDAO.getOrderChildCountByRoomTypeIdByTime(roomType, dateList.get(i + 1),
                        dateList.get(i), orderId, hotelId);
                if (roomCount - orderCount < reservationRoomCount) {
                    return false;
                }
            }

        }

        return true;
    }

    //获取主订单信息
    public OrderBO getOrderBoByOrderId(Integer orderId) {
        return orderDAO.getOrderById(orderId);
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
