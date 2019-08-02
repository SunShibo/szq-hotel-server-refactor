package com.szq.hotel.web.controller;

import com.szq.hotel.common.constants.Constants;
import com.szq.hotel.entity.bo.*;
import com.szq.hotel.entity.dto.ResultDTOBuilder;
import com.szq.hotel.entity.param.OrderChildBackupParam;
import com.szq.hotel.entity.param.OrderParam;
import com.szq.hotel.entity.result.CheckInInfoResult;
import com.szq.hotel.entity.result.OrderResult;
import com.szq.hotel.query.QueryInfo;
import com.szq.hotel.service.*;
import com.szq.hotel.util.JsonUtils;
import com.szq.hotel.util.RedisTool;
import com.szq.hotel.web.controller.base.BaseCotroller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

@Controller
@RequestMapping("/order")
public class OrderController extends BaseCotroller {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    @Resource
    OrderService orderService;

    @Resource
    CheckInPersonService checkInPersonService;

    @Resource
    CashierSummaryService cashierSummaryService;

    @Resource
    OrderRecordService orderRecordService;

    @Resource
    RoomService roomService;

    @Resource
    MemberService memberService;

    @Resource
    EverydayRoomPriceService everydayRoomPriceService;

    /**
     * 房间预定 预约入住 直接入住 修改
     *
     * @param orderBO        预约信息
     * @param OrderChildJSON json格式的预定的房间信息
     * @param type           预约 或者 预约入住 或者 直接入住
     */
    @RequestMapping("/reservationRoom")
    public void reservationRoom(HttpServletRequest request, HttpServletResponse response,
                                OrderBO orderBO, String OrderChildJSON, String type) {
        try {
            log.info(request.getRequestURI());
            log.info("param:{}", JsonUtils.getJsonString4JavaPOJO(request.getParameterMap()));
            Jedis jedis = new Jedis();
            String requestId = request.getSession().getId();
            if (!(RedisTool.tryGetDistributedLock(jedis, "500", requestId, 1000))) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("系统繁忙,请重试"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }

            //验证管理员
            AdminBO userInfo = super.getLoginAdmin(request);
            if (userInfo == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002", "用户没有登录"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            //验证参数
            if (orderBO == null || OrderChildJSON == null || type == null || type.length() == 0 || orderBO.getPhone() == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }

            orderBO.setClerkOrderingId(userInfo.getId());
            orderBO.setHotelId(userInfo.getHotelId());
            List<OrderChildBO> list = JsonUtils.getJSONtoList(OrderChildJSON, OrderChildBO.class);
            Map<String, Object> resultMap = new HashMap<String, Object>();

            //bug判断房型是否够用 房间预定和 预约修改都要判断房型 房间是否够用 与预约中的是不是不会冲突
            for (OrderChildBO orderChildBO : list) {
                boolean bool = orderService.getOrderChildCountByRoomIdByTime(orderChildBO.getRoomId(), orderChildBO.getRoomTypeId(),
                         orderBO.getCheckOutTime(),orderBO.getCheckTime(), userInfo.getHotelId());
                if (!bool) {
                    String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000097", "入住日期冲突"));
                    super.safeJsonPrint(response, result);
                    log.info("result{}", result);
                    return;
                }
            }

            if (type.equals("reservation") || type.equals("directly")) {
                String result = this.checkInPerson(list);
                if (result != null) {
                    super.safeJsonPrint(response, result);
                    log.info("result{}", result);
                    return;
                }

                for (OrderChildBO order : list) {
                    RoomBO roomBO = roomService.getRoomBo(order.getRoomId());
                    if (roomBO == null || roomBO.getId() == null) {
                        result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"), "房间异常");
                        super.safeJsonPrint(response, result);
                        log.info("result{}", result);
                        return;
                    }
                    String status = roomBO.getRoomMajorState();
                    if (!status.equals(Constants.VACANT.getValue())) {
                        result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000097"), "房间状态异常，不能入住");
                        super.safeJsonPrint(response, result);
                        log.info("result{}", result);
                        return;
                    }

                }
            }

            if (type.equals("roomReservation")) {
                //房间预约
                orderService.addOrderInfo(orderBO, list);
            } else if (type.equals("reservation")) {
                //预约入住
                orderService.reservation(list, orderBO);
            } else if (type.equals("directly")) {
                //直接入住
                orderService.addOrderInfo(orderBO, list);
                resultMap.put("orderId", orderBO.getId());
            } else if (type.equals("updateInfo")) {
                //预约修改
                orderService.updateInfo(list, orderBO);
            }

            Thread.sleep(1000);
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(resultMap));
            super.safeJsonPrint(response, result);
            RedisTool.releaseDistributedLock(jedis, "500", requestId);
            log.info("result{}", result);
        } catch (Exception e) {
            log.error("reservationRoomException", e);
        }
    }

    /**
     * 检查证件号是否重复
     *
     * @param list 入住人
     */
    public String checkInPerson(List<OrderChildBO> list) {
        //验证所有入住人中是否有重复入住的
        Set<String> idSet = new HashSet<String>();
        List<String> idList = new ArrayList<String>();
        for (OrderChildBO order : list) {
            List<CheckInPersonBO> personBOS = order.getCheckInPersonBOS();
            if (personBOS == null || personBOS.size() == 0) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001", "入住信息不全"));
                return result;
            }
            for (CheckInPersonBO personBO : personBOS) {
                idSet.add(personBO.getCertificateNumber());
                idList.add(personBO.getCertificateNumber());
                if (checkInPersonService.checkId(personBO.getCertificateNumber(), order.getId()) > 0) {
                    String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000094", personBO.getCertificateNumber() + "此证件信息为在住状态"));
                    return result;
                }
                if (personBO.getCertificateNumber() == null || personBO.getCertificateNumber().equals("") || personBO.getPhone() == null || personBO.getPhone().equals("")) {
                    String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001", "入住信息不全"));
                    return result;
                }
            }
        }

        if (idList.size() > idSet.size()) {
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000094", "入住证件信息重复"));
            return result;
        }
        return null;
    }

    /**
     * 取消预约订单
     *
     * @param orderChildIdS 主订单id
     */
    @RequestMapping("closeOrder")
    public void closeOrder(String orderChildIdS, HttpServletResponse response, HttpServletRequest request) {
        try {
            log.info(request.getRequestURI());
            log.info("param:{}", JsonUtils.getJsonString4JavaPOJO(request.getParameterMap()));
            //验证管理员
            AdminBO userInfo = super.getLoginAdmin(request);
            if (userInfo == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002", "用户没有登录"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            //验证参数
            if (orderChildIdS == null || orderChildIdS.equals("")) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001", "参数异常"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            orderService.closeOrderChild(orderChildIdS);
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(null));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
        } catch (Exception e) {
            log.error("closeOrder", e);
        }
    }


    /**
     * 预约入住 获取信息
     * 根据身份证号 手机号查询预约信息
     *
     * @param idNumber 身份证号
     * @param mobile   手机号
     */
    @RequestMapping("/getReservationRoomInfo")
    public void getReservationRoomInfo(HttpServletRequest request, HttpServletResponse response, String idNumber, String mobile) {
        try {
            log.info(request.getRequestURI());
            log.info("param:{}", JsonUtils.getJsonString4JavaPOJO(request.getParameterMap()));
            //验证管理员
            AdminBO userInfo = super.getLoginAdmin(request);
            if (userInfo == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002", "用户没有登录"));
                log.info("result{}", result);
                super.safeJsonPrint(response, result);
                return;
            }
            //验证参数
            if ((idNumber == null || idNumber.length() == 0) && (mobile == null || mobile.length() == 0)) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001", "参数异常"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            //查询信息
            OrderBO orderBO = orderService.getOrderInfo(idNumber, mobile, userInfo.getHotelId());
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(orderBO));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
        } catch (Exception e) {
            log.error("getReservationRoomInfo", e);
        }
    }

    /**
     * 通过订单id获取订单的信息
     *
     * @param orderId 订单号
     */
    @RequestMapping("/getOrderById")
    public void getOrderById(Integer orderId, HttpServletRequest request, HttpServletResponse response) {
        try {
            log.info(request.getRequestURI());
            log.info("param:{}", JsonUtils.getJsonString4JavaPOJO(request.getParameterMap()));
            //验证管理员
            AdminBO userInfo = super.getLoginAdmin(request);
            if (userInfo == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002", "用户没有登录"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            //验证参数
            if (orderId == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001", "参数异常"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            OrderBO orderBO = orderService.getOrderById(orderId);
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(orderBO));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
        } catch (Exception e) {
            log.error("getOrderById", e);
        }
    }

    /**
     * 根据主订单id查询房间信息(客帐管理)
     *
     * @param alCode 联房码
     */
    @RequestMapping("/getRoomInfoById")
    public void getRoomInfoById(String alCode, HttpServletRequest request, HttpServletResponse response) {
        try {
            log.info(request.getRequestURI());
            log.info("param:{}", JsonUtils.getJsonString4JavaPOJO(request.getParameterMap()));
            //验证管理员
            AdminBO userInfo = super.getLoginAdmin(request);
            if (userInfo == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002", "用户没有登录"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            //验证参数
            if (alCode == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001", "参数异常"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            List<OrderChildBO> orderBO = orderService.getRoomInfoById(alCode);
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(orderBO));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
        } catch (Exception e) {
            log.error("getRoomInfoById", e);
        }
    }

    /**
     * 根据子订单id查询账单信息(客帐管理)
     *
     * @param orderChildId 子订单id
     */
    @RequestMapping("/getOrderInfoById")
    public void getOrderInfoById(Integer orderChildId, HttpServletRequest request, HttpServletResponse response) {
        try {
            log.info(request.getRequestURI());
            log.info("param:{}", JsonUtils.getJsonString4JavaPOJO(request.getParameterMap()));
            //验证管理员
            AdminBO userInfo = super.getLoginAdmin(request);
            if (userInfo == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002", "用户没有登录"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            //验证参数
            if (orderChildId == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001", "参数异常"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            OrderChildBO orderBO = orderService.getOrderInfoById(orderChildId);
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(orderBO));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
        } catch (Exception e) {
            log.error("getOrderInfoById", e);
        }

    }

    /**
     * 获取预约中的子订单
     *
     * @param orderId 主订单id
     */
    @RequestMapping("/getSubscribeOrderChild")
    public void getSubscribeOrderChild(Integer orderId, HttpServletRequest request, HttpServletResponse response) {
        try {
            log.info(request.getRequestURI());
            log.info("param:{}", JsonUtils.getJsonString4JavaPOJO(request.getParameterMap()));
            //验证管理员
            AdminBO userInfo = super.getLoginAdmin(request);
            if (userInfo == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002", "用户没有登录"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            //验证参数
            if (orderId == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001", "参数异常"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            List<OrderChildBO> orderBO = orderService.getSubscribeOrderChild(orderId);
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(orderBO));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
        } catch (Exception e) {
            log.error("getOrderInfoById", e);
        }

    }

    /**
     * 入住支付
     *
     * @param id      子订单id 也就是主账房
     * @param money   实付金额
     * @param payType 支付方式
     * @param name    付款人姓名
     */
    @RequestMapping("/pay")
    public void pay(BigDecimal money, String payType, Integer id, String name, String certificateNumber, HttpServletRequest request, HttpServletResponse response) {
        try {
            log.info(request.getRequestURI());
            log.info("param:{}", JsonUtils.getJsonString4JavaPOJO(request.getParameterMap()));
            //验证管理员
            AdminBO userInfo = super.getLoginAdmin(request);
            if (userInfo == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002", "用户没有登录"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            //验证参数
            if (id == null || money == null || payType == null || payType.equals("")) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001", "参数异常"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            //获取子订单信息
            OrderChildBO orderChildResult = orderService.getOrderChildById(id);
            if (orderChildResult == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001", "此订单异常"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }

            //判断是否是会员卡储值支付
            if (payType.equals(Constants.STORED.getValue())) {
                memberService.storedValuePay(certificateNumber, money, "入住支付", Constants.ROOMRATE.getValue(), null, userInfo.getId());
            }

            //获取主订单信息
            OrderBO orderBO = orderService.getOrderById(orderChildResult.getOrderId());

            //添加收银汇总
            cashierSummaryService.addCheck(money, payType, orderBO.getOrderNumber(), userInfo.getId(),
                    name, orderBO.getOTA(), orderBO.getOrderType(),
                    orderBO.getChannel(), orderChildResult.getRoomName(), orderChildResult.getRoomTypeName(),
                    null, userInfo.getHotelId());

            //修改子订单信息
            OrderChildBO orderChildBO = new OrderChildBO();
            orderChildBO.setId(id);
            if (payType.equals(Constants.CASH.getValue())) {
                orderChildBO.setPayCashNum(money);
            } else {
                orderChildBO.setOtherPayNum(money);
            }
            orderChildBO.setOrderState(Constants.ADMISSIONS.getValue());
            orderChildBO.setMain("yes");
            orderService.updOrderChild(orderChildBO);
            //把入住的房间修改为在住
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", orderChildResult.getRoomId());
            map.put("state", Constants.INTHE.getValue());
            roomService.updateroomMajorState(map);

            //修改一起入住的订单信息
            orderService.updateOrderChildRoom(orderBO.getId());

            //插入订单记录
            orderRecordService.addOrderRecord(orderChildBO.getId(), Constants.INTHEDEPOSIT.getValue(),
                    payType, money, Constants.CASHPLEDGE.getValue(), userInfo.getId(), null, Constants.NO.getValue());

            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("支付成功"));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
        } catch (Exception e) {
            log.error("pay", e);
        }

    }


    /**
     * 入住成功支付页面
     *
     * @param orderId 订单id
     */
    @RequestMapping("/getPayInfo")
    public void getPayInfo(Integer orderId, HttpServletRequest request, HttpServletResponse response) {
        try {
            log.info(request.getRequestURI());
            log.info("param:{}", JsonUtils.getJsonString4JavaPOJO(request.getParameterMap()));
            //验证管理员
            AdminBO userInfo = super.getLoginAdmin(request);
            if (userInfo == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002", "用户没有登录"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            //验证参数
            if (orderId == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001", "参数异常"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            List<OrderChildBO> orderChildBOS = orderService.getPayInfo(orderId);
            //所有支付人
            for (OrderChildBO orderChild : orderChildBOS) {
                if ("yes".equals(orderChild.getMain())) {
                    List<OrderChildBO> oneOrderChild = new ArrayList<OrderChildBO>();
                    oneOrderChild.add(orderChild);
                    String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(oneOrderChild));
                    super.safeJsonPrint(response, result);
                    log.info("result{}", result);
                    return;
                }
            }
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(orderChildBOS));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
        } catch (Exception e) {
            log.error("getPayInfo", e);

        }
    }

    /**
     * 入住待支付 订单列表 支付页面
     *
     * @param orderId 订单id
     */
    @RequestMapping("/getOrderChildPayInfo")
    public void getOrderChildPayInfo(Integer orderId, HttpServletRequest request, HttpServletResponse response) {
        try {
            log.info(request.getRequestURI());
            log.info("param:{}", JsonUtils.getJsonString4JavaPOJO(request.getParameterMap()));
            //验证管理员
            AdminBO userInfo = super.getLoginAdmin(request);
            if (userInfo == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002", "用户没有登录"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            //验证参数
            if (orderId == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001", "参数异常"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            List<OrderChildBO> orderChildBOS = orderService.getPayInfo(orderId);
            System.err.println(orderChildBOS.size() + "id" + orderId);
            List<OrderChildBO> oneOrderChild = new ArrayList<OrderChildBO>();
            BigDecimal totalPrice = new BigDecimal(0);
            //所有未支付的子订单
            for (OrderChildBO orderChild : orderChildBOS) {
                //查找主账房
                if ("yes".equals(orderChild.getMain())) {
                    oneOrderChild.add(orderChild);
                }
                if (orderChild.getOrderState().equals(Constants.NOTPAY.getValue())) {
                    List<EverydayRoomPriceBO> everydayRoomPriceBOList = everydayRoomPriceService.getEverydayRoomById(orderChild.getId());
                    for (EverydayRoomPriceBO e : everydayRoomPriceBOList) {
                        totalPrice = totalPrice.add(e.getMoney());
                    }
                }

            }

            Map<String, Object> map = new HashMap<String, Object>();
            if (oneOrderChild.size() > 0) {
                map.put("orderChildBOS", oneOrderChild);
            } else {
                map.put("orderChildBOS", orderChildBOS);
            }
            map.put("totalPrice", totalPrice);
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(map));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
        } catch (Exception e) {
            log.error("getPayInfo", e);

        }
    }


    /**
     * 获取在住报表
     */
    @RequestMapping("/getCheckInReport")
    public void getCheckInReport(HttpServletRequest request, HttpServletResponse response) {
        try {
            log.info(request.getRequestURI());
            log.info("param:{}", JsonUtils.getJsonString4JavaPOJO(request.getParameterMap()));

            //验证管理员
            AdminBO userInfo = super.getLoginAdmin(request);
            if (userInfo == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002", "用户未登录"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }

            List<OrderResult> results = orderService.getCheckInReport(userInfo.getHotelId());
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(results));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
        } catch (Exception e) {
            log.error("getCheckInReport", e);
        }
    }

    /**
     * 获取预离报表
     *
     * @param beforeTime 在这个时间之前 最大时间
     * @param afterTime  在这个时间之后 最小时间
     */
    @RequestMapping("/getCheckOutReport")
    public void getCheckOutReport(Date beforeTime, Date afterTime, Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response) {
        try {
            log.info(request.getRequestURI());
            log.info("param:{}", JsonUtils.getJsonString4JavaPOJO(request.getParameterMap()));
            //验证管理员
            AdminBO userInfo = super.getLoginAdmin(request);
            if (userInfo == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002", "用户未登录"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            Map<String, Object> resultMap = new HashMap<String, Object>();
            QueryInfo queryInfo = getQueryInfo(pageNo, pageSize);
            List<OrderResult> list = orderService.getCheckOutReport(beforeTime, afterTime, queryInfo.getPageOffset(), queryInfo.getPageSize(), userInfo.getHotelId());
            Integer count = orderService.getCheckOutReportCount(beforeTime, afterTime, userInfo.getHotelId());

            resultMap.put("list", list);
            resultMap.put("count", count);
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(resultMap));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
        } catch (Exception e) {
            log.error("getCheckOutReport", e);
        }

    }

    /**
     * 在住信息查询
     */
    @RequestMapping("/getCheckInInfo")
    public void getCheckInInfo(Integer roomId, HttpServletRequest request, HttpServletResponse response) {
        try {
            log.info(request.getRequestURI());
            log.info("param:{}", JsonUtils.getJsonString4JavaPOJO(request.getParameterMap()));
            //验证管理员
            AdminBO userInfo = super.getLoginAdmin(request);
            if (userInfo == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002", "用户未登录"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            //验证参数
            if (roomId == null || roomId.equals("")) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001", "参数异常"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            CheckInInfoResult checkInInfoResult = orderService.getCheckInInfo(roomId, userInfo.getHotelId());
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(checkInInfoResult));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
        } catch (Exception e) {
            log.error("getCheckInInfo", e);
        }

    }

    /**
     * 预约信息查询
     */
    @RequestMapping("/getReservationInfo")
    public void getReservationInfo(Integer roomId, HttpServletRequest request, HttpServletResponse response) {
        try {
            log.info(request.getRequestURI());
            log.info("param:{}", JsonUtils.getJsonString4JavaPOJO(request.getParameterMap()));
            //验证管理员
            AdminBO userInfo = super.getLoginAdmin(request);
            if (userInfo == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002", "用户未登录"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            //验证参数
            if (roomId == null || roomId.equals("")) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001", "参数异常"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            CheckInInfoResult checkInInfoResult = orderService.getReservationInfo(roomId, userInfo.getHotelId());
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(checkInInfoResult));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
        } catch (Exception e) {
            log.error("getCheckInInfo", e);
        }

    }

    /**
     * 修改在住信息
     *
     * @param orderId           主订单id
     * @param channel           客源
     * @param OTA               OTA
     * @param orderChildId      子订单id
     * @param endTime           离店时间
     * @param remark            房间备注
     * @param checkInPersonJson 入住人信息
     */
    @RequestMapping("/updCheckInInfo")
    public void updCheckInInfo(Integer orderId, String channel, String OTA,
                               Integer orderChildId, @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss") Date endTime, String remark,
                               String checkInPersonJson, HttpServletResponse response,
                               String everyDayRoomPrice, HttpServletRequest request) {
        try {
            log.info(request.getRequestURI());
            log.info("param:{}", JsonUtils.getJsonString4JavaPOJO(request.getParameterMap()));
            //验证管理员
            AdminBO userInfo = super.getLoginAdmin(request);
            if (userInfo == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002", "用户未登录"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }

            //验证入住人
            if (checkInPersonJson != null && !checkInPersonJson.equals("")) {
                List<CheckInPersonBO> list = JsonUtils.getJSONtoList(checkInPersonJson, CheckInPersonBO.class);
                OrderChildBO orderChildBO = new OrderChildBO();
                orderChildBO.setId(orderChildId);
                orderChildBO.setCheckInPersonBOS(list);
                List<OrderChildBO> orderChildBOS = new ArrayList<OrderChildBO>();
                orderChildBOS.add(orderChildBO);
                String result = this.checkInPerson(orderChildBOS);
                if (result != null) {
                    super.safeJsonPrint(response, result);
                    log.info("result{}", result);
                    return;
                }
            }
            OrderChildBO orderChildBO = orderService.getOrderChildById(orderChildId);
            //bug验证续租是否冲突
            Date startTime=orderChildBO.getPracticalDepartureTime()==null?orderChildBO.getEndTime():orderChildBO.getPracticalDepartureTime();
            boolean bool = orderService.getOrderChildCountByRoomIdByTime(orderChildBO.getRoomId(), orderChildBO.getRoomTypeId(),
                    endTime, startTime, userInfo.getHotelId());
            if (!bool) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000097", "续租日期冲突"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }

            orderService.updCheckInInfo(orderId, channel, OTA, orderChildId, endTime, remark, checkInPersonJson, everyDayRoomPrice);
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("修改成功"));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
            return;
        } catch (Exception e) {
            log.error("updCheckInInfo", e);
        }
    }

    /**
     * 查询所有可用联房
     */
    @RequestMapping("/getAlRoom")
    public void getAlRoom(String alCode, HttpServletRequest request, HttpServletResponse response) {
        //验证管理员
        try {
            log.info(request.getRequestURI());
            log.info("param:{}", JsonUtils.getJsonString4JavaPOJO(request.getParameterMap()));
            AdminBO userInfo = super.getLoginAdmin(request);
            if (userInfo == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002", "用户未登录"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            //验证参数
            if (alCode == null || alCode.equals("")) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001", "参数异常"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            List<CheckInPersonBO> checkInPersonBOS = orderService.getAlRoom(userInfo.getHotelId(), alCode);
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(checkInPersonBOS));
            super.safeJsonPrint(response, result);
        } catch (Exception e) {
            log.error("getAlRoom", e);
        }
    }

    /**
     * 解除联房
     */
    @RequestMapping("/updAlRoom")
    public void updAlRoom(String orderChildIds, HttpServletRequest request, HttpServletResponse response) {
        try {
            log.info(request.getRequestURI());
            log.info("param:{}", JsonUtils.getJsonString4JavaPOJO(request.getParameterMap()));
            //验证管理员
            AdminBO userInfo = super.getLoginAdmin(request);
            if (userInfo == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002", "用户未登录"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            //验证参数
            if (orderChildIds == null || orderChildIds.length() < 2) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001", "参数异常"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            Integer[] idArr = JsonUtils.getIntegerArray4Json(orderChildIds);
            orderService.updAlRoom(idArr);
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(null));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
        } catch (Exception e) {
            log.error("updAlRoom", e);
        }
    }

    /**
     * 联房 自动转账 转账记录
     */
    @RequestMapping("/addAlRoom")
    public void addAlRoom(Integer orderChildId, String orderChildIds, HttpServletRequest request, HttpServletResponse response) {
        try {
            //验证管理员
            AdminBO userInfo = super.getLoginAdmin(request);
            if (userInfo == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002", "用户未登录"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            //验证参数
            if (orderChildId == null || orderChildId.equals("") || orderChildIds == null || orderChildIds.length() == 0) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001", "参数异常"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            orderService.addAlRoom(orderChildId, orderChildIds, userInfo.getId());
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(null));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
        } catch (Exception e) {
            log.error("addAlRoom", e);
        }
    }

    /**
     * 检查身份证号是否在住
     *
     * @param id 身份证号
     */
    @RequestMapping("/checkId")
    public void checkId(String id, HttpServletRequest request, HttpServletResponse response) {
        try {
            log.info(request.getRequestURI());
            log.info("param:{}", JsonUtils.getJsonString4JavaPOJO(request.getParameterMap()));

            //验证管理员
            AdminBO userInfo = super.getLoginAdmin(request);
            if (userInfo == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002", "用户没有登录"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            //验证参数
            if (id == null || id.length() == 0) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001", "参数异常"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            Integer count = checkInPersonService.checkId(id, null);
            if (count > 0) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000094", "身份证信息为在住状态"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
            } else {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("未在住"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
            }
        } catch (Exception e) {
            log.error("checkId", e);
        }

    }

    /**
     * 获取剩余每日房价
     *
     * @param orderChildId 子订单号
     */
    @RequestMapping("/getRemainingLease")
    public void getRemainingLease(Integer orderChildId, HttpServletRequest request, HttpServletResponse response) throws ParseException {
        try {
            log.info(request.getRequestURI());
            log.info("param:{}", JsonUtils.getJsonString4JavaPOJO(request.getParameterMap()));
            //验证管理员
            AdminBO userInfo = super.getLoginAdmin(request);
            if (userInfo == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002", "用户没有登录"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            //验证参数
            if (orderChildId == null || orderChildId.equals("")) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001", "参数异常"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            List<EverydayRoomPriceBO> everydayRoomPriceBOList = orderService.getRemainingLease(orderChildId);
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(everydayRoomPriceBOList));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
        } catch (Exception e) {
            log.error("getRemainingLease", e);
        }
    }

    /**
     * 换房 修改房间 每日房价
     *
     * @param orderChildBO      子订单信息
     * @param everydayRoomPrice 房价信息
     */
    @RequestMapping("/changeRoom")
    public void changeRoom(OrderChildBO orderChildBO, String everydayRoomPrice, HttpServletRequest request, HttpServletResponse response) throws ParseException {
        try {
            log.info(request.getRequestURI());
            log.info("param:{}", JsonUtils.getJsonString4JavaPOJO(request.getParameterMap()));

            //验证管理员
            AdminBO userInfo = super.getLoginAdmin(request);
            if (userInfo == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002", "用户没有登录"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            //验证参数
            if (orderChildBO == null || orderChildBO.getId() == null || everydayRoomPrice == null || everydayRoomPrice.length() < 2) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001", "参数异常"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            List<EverydayRoomPriceBO> everydayRoomPriceBOList = JsonUtils.getJSONtoList(everydayRoomPrice, EverydayRoomPriceBO.class);
            orderService.changeRoom(orderChildBO, everydayRoomPriceBOList);
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(null));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
        } catch (Exception e) {
            log.error("changeRoom", e);
        }
    }

    /**
     * 退房 获取退房信息
     *
     * @param orderChildId 子订单id
     */
    @RequestMapping("/getCheckOutInfo")
    public void getCheckOutInfo(Integer orderChildId, HttpServletRequest request, HttpServletResponse response) throws ParseException {
        try {
            log.info(request.getRequestURI());
            log.info("param:{}", JsonUtils.getJsonString4JavaPOJO(request.getParameterMap()));

            //验证管理员
            AdminBO userInfo = super.getLoginAdmin(request);
            if (userInfo == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002", "用户没有登录"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            //验证参数
            if (orderChildId == null || orderChildId.equals("")) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001", "参数异常"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            //验证是否已经退房
            OrderChildBackupParam orderChildBackupParam = orderService.getOrderChildBackup(orderChildId);
            if (orderChildBackupParam != null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000095", "不能重复退房"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }

            OrderChildBO orderChildBO = orderService.getCheckOutInfo(orderChildId);
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(orderChildBO));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
        } catch (Exception e) {
            log.error("getCheckOutInfo", e);
        }
    }

    /**
     * 退房
     *
     * @param orderChildId 子订单id
     * @param money        超时减免
     */
    @RequestMapping("/checkOut")
    public void checkOut(Integer orderChildId, BigDecimal money, HttpServletRequest request, HttpServletResponse response) throws ParseException {
        try {
            log.info(request.getRequestURI());
            log.info("param:{}", JsonUtils.getJsonString4JavaPOJO(request.getParameterMap()));
            //验证管理员
            AdminBO userInfo = super.getLoginAdmin(request);
            if (userInfo == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002", "用户没有登录"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            //验证参数
            if (orderChildId == null || orderChildId.equals("")) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001", "参数异常"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }

            orderService.checkOut(orderChildId, money, userInfo.getId(), userInfo.getHotelId());
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(null));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
        } catch (Exception e) {
            log.error("checkOut", e);
        }
    }

    /**
     * 退房回滚
     *
     * @param orderChildId 子订单id
     */
    @RequestMapping("/checkOutRollback")
    public void checkOutRollback(Integer orderChildId, HttpServletRequest request, HttpServletResponse response) throws ParseException {
        try {
            log.info(request.getRequestURI());
            log.info("param:{}", JsonUtils.getJsonString4JavaPOJO(request.getParameterMap()));

            //验证管理员
            AdminBO userInfo = super.getLoginAdmin(request);
            if (userInfo == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002", "用户没有登录"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            //验证参数
            if (orderChildId == null || orderChildId.equals("")) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001", "参数异常"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            //验证是否已经退房
            OrderChildBackupParam orderChildBackupParam = orderService.getOrderChildBackup(orderChildId);
            if (orderChildBackupParam == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000096", "该房间未退房"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }

            orderService.checkOutRollback(orderChildId, userInfo.getId(), userInfo.getHotelId());
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(null));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
        } catch (Exception e) {
            log.error("checkOutRollback", e);
        }
    }

    /**
     * 订单列表
     */
    @RequestMapping("/queryOrderList")
    public void queryOrderList(HttpServletRequest request, HttpServletResponse response, OrderParam param) {
        try {
            log.info(request.getRequestURI());
            log.info("param:{}", JsonUtils.getJsonString4JavaPOJO(request.getParameterMap()));
            AdminBO loginAdmin = super.getLoginAdmin(request);
            log.info("user{}", loginAdmin);
            if (loginAdmin == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }

            //计算分页
            QueryInfo queryInfo = getQueryInfo(param.getPageNo(), param.getPageSize());
            param.setPageOffset(queryInfo.getPageOffset());
            param.setPageSize(queryInfo.getPageSize());
            param.setHotelId(loginAdmin.getHotelId());
            List<OrderListBO> orderListBOS = orderService.queryOrderList(param);
            int count = orderService.queryOrderListCount(param);
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("data", orderListBOS);
            resultMap.put("count", count);

            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(resultMap));
            super.safeJsonPrint(response, result);
            log.info("result{}", result);
            return;
        } catch (Exception e) {
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000004"));
            super.safeJsonPrint(response, result);
            log.error("queryOrderList", e);
        }
    }
}
