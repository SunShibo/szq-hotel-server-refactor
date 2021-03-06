package com.szq.hotel.service;

import com.szq.hotel.common.constants.Constants;
import com.szq.hotel.dao.ChildOrderDAO;
import com.szq.hotel.entity.bo.*;
import com.szq.hotel.entity.param.PayTypeBO;
import com.szq.hotel.util.IDBuilder;
import com.szq.hotel.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@Service("ChildOrderService")
@Transactional
public class ChildOrderService {
    private static final Logger log = LoggerFactory.getLogger(ChildOrderService.class);
    @Resource
    private ChildOrderDAO childOrderDAO;
    @Resource
    private OrderRecordService orderRecordService;
    @Resource
    private CashierSummaryService cashierSummaryService;
    @Resource
    private MemberService memberService;
    @Resource
    private HotelService hotelService;

    /**
     * 押金
     *
     * @param payType
     * @param orderChildId
     * @param money
     */
    public void addCashPledge(String payType, Integer orderChildId, BigDecimal money, Integer userId, Integer hotelId, String certificateNumber) {
        log.info("start addCashPledge........................................");
        log.info("payType:{}\torderChildId:{}\tmoney:{}\tuserId:{}", payType, orderChildId, money, userId);
        //生成记录
        ChildOrderBO childOrderBO = this.queryOrderChildById(orderChildId);
        Integer mainId = this.queryOrderChildMain(childOrderBO.getAlRoomCode());
        orderRecordService.addOrderRecord(orderChildId, "入住押金", payType, money, Constants.CASHPLEDGE.getValue(), userId, null, Constants.NO.getValue());
        //增加金额
        if (Constants.CASH.getValue().equals(payType)) {
            log.info("increaseCashCashPledge...............................");
            childOrderDAO.increaseCashCashPledge(mainId, money);
        } else {
            log.info("increaseOtherCashPledge...............................");
            childOrderDAO.increaseOtherCashPledge(mainId, money);
        }

        ChildOrderBO order = childOrderDAO.queryOrderChildById(mainId);
        //报表
        cashierSummaryService.addCheck(money, payType, IDBuilder.getOrderNumber(), userId, order.getName(), order.getOTA(),
                order.getPassengerSource(), order.getChannel(), order.getRoomName(), order.getRoomTypeName(), null, hotelId);

        if (payType.equals(Constants.STORED.getValue())) {
            memberService.storedValuePay(certificateNumber, money, "入住押金", "储值支付", new BigDecimal("0"), userId);
            //修改主订单userId
            childOrderDAO.updateOrderUserId(orderChildId, memberService.selectMemberByCerNumber(certificateNumber).getId());
        }
        log.info("end  addCashPledge........................................");
    }


    /**
     * 入账
     *
     * @param orderChildId 子订单id
     * @param money        金额
     * @param designation  费用名称
     * @param type         消费类型F
     * @param userId       用户id
     * @param hotelId      酒店id
     */
    public void recorded(Integer orderChildId, BigDecimal money, String designation, String type, Integer userId, Integer hotelId) {
        log.info("start recorded........................................");
        log.info("orderChildId:{}\tmoney:{}\tdesignation:{}\ttype:{}\tuserId:{}\thotelId:{}", orderChildId, money, designation, type, userId, hotelId);
        //生成记录
        orderRecordService.addOrderRecord(orderChildId, designation, null, new BigDecimal("-1").multiply(money), type, userId, null, Constants.NO.getValue());
        ChildOrderBO childOrderBO = queryOrderChildById(orderChildId);
        Integer integer = queryOrderChildMain(childOrderBO.getAlRoomCode());
        ChildOrderBO main = queryOrderChildById(integer);
        if (type.equals(Constants.ROOMRATE.getValue())) {
            log.info("increaseRoomRate...............................");
            childOrderDAO.increaseRoomRate(main.getId(), money);
        } else {
            log.info("increaseOtherRate...............................");
            childOrderDAO.increaseOtherRate(main.getId(), money);
        }

        //报表
        cashierSummaryService.addAccount(type, money, main.getOrderNumber(), userId, main.getName(), main.getOTA(),
                main.getPassengerSource(), main.getChannel(), main.getRoomName(), main.getRoomTypeName(), designation, hotelId);
        log.info("end  recorded........................................");

    }

    /**
     * 查询挂账信息
     *
     * @param roomName
     * @return
     */
    public List<BuyerBuyingBO> querySuspend(String roomName) {
        return childOrderDAO.querySuspend(roomName);
    }

    /**
     * 冲减
     *
     * @param orderChildId
     * @param money
     * @param remark
     * @param hotelId
     */
    public void free(Integer orderChildId, BigDecimal money, String remark, Integer userId, Integer hotelId, String type) {
        log.info("start free........................................");
        log.info("orderChildId:{}\tmoney:{}\tremark:{}\tuserId:{}\thotelId:{}\ttype:{}", orderChildId, money, remark, userId, hotelId, type);
        //生成记录
        orderRecordService.addOrderRecord(orderChildId, remark, null, money, type, userId, null, Constants.NO.getValue());
        log.info("free...................................");
        //变为负数
        BigDecimal negation = money.multiply(new BigDecimal("-1"));

        childOrderDAO.free(orderChildId, negation);

        ChildOrderBO order = childOrderDAO.queryOrderChildById(orderChildId);
        //报表
        cashierSummaryService.addFree(negation, order.getOrderNumber(), userId, order.getName(), order.getOTA(), order.getPassengerSource(),
                order.getChannel(), order.getRoomName(), order.getRoomTypeName(), remark, hotelId, type);
        log.info("end  free..........................................");
    }

    /**
     * 查询子订单
     *
     * @param childId
     * @return
     */
    public ChildOrderBO queryOrderChildById(Integer childId) {
        return childOrderDAO.queryOrderChildById(childId);
    }

    /**
     * 通过联房码查询主账房
     */
    public Integer queryOrderChildMain(String code) {
        log.info("queryOrderChildMain.......................................................");
        System.out.println("==========" + code);
        return childOrderDAO.queryOrderChildMain(code);
    }

    /**
     * @param ids       详情id
     * @param shiftToId 转入id
     * @param rollOutId 转出id
     */
    public void transferAccounts(Integer userId, String ids, Integer shiftToId, Integer rollOutId, boolean flag) {
        log.info("start transferAccounts.........................................................");
        log.info("userId:{}\tids:{}\tshiftToId:{}\trollOutId:{}", userId, ids, shiftToId, rollOutId);
        String[] split = ids.split(",");
        for (int i = 0; i < split.length; i++) {
            if (flag) {
                ChildOrderBO childOrderBO1 = this.queryOrderChildById(shiftToId);
                shiftToId = this.queryOrderChildMain(childOrderBO1.getAlRoomCode());  //转入到主订单中！
            }
            OrderRecoredBO orderRecoredBO = orderRecordService.queryOrderRecordById(Integer.parseInt(split[i]));

            //押金
            if (Constants.CASHPLEDGE.getValue().equals(orderRecoredBO.getProject())) {
                log.info("start transferAccounts....CASHPLEDGE.....................................................");
                //现金
                if (Constants.CASH.getValue().equals(orderRecoredBO.getPayType())) {
                    log.info("start transferAccounts....CASH.....................................................");
                    childOrderDAO.increaseCashCashPledge(shiftToId, orderRecoredBO.getMoney());
                    childOrderDAO.reduceCashCashPledge(rollOutId, orderRecoredBO.getMoney());
                    System.out.println(orderRecoredBO.getInfo() + "==" + orderRecoredBO.getMoney());
                } else {
                    //非现金
                    log.info("start transferAccounts....OtherCashPledge.....................................................");
                    childOrderDAO.increaseOtherCashPledge(shiftToId, orderRecoredBO.getMoney());
                    childOrderDAO.reduceOtherCashPledge(rollOutId, orderRecoredBO.getMoney());
                    System.out.println(orderRecoredBO.getInfo() + "==" + orderRecoredBO.getMoney());
                }

            } else if (Constants.ROOMRATE.getValue().equals(orderRecoredBO.getProject())) {
                //房费
                log.info("start transferAccounts....ROOMRATE.....................................................");
                childOrderDAO.reduceRoomRate(shiftToId, orderRecoredBO.getMoney());
                childOrderDAO.increaseRoomRate(rollOutId, orderRecoredBO.getMoney());

            } else if (Constants.COMMODITY.getValue().equals(orderRecoredBO.getProject()) || Constants.COMPENSATE.getValue().equals(orderRecoredBO.getProject())
                    || Constants.APPLYCARD.getValue().equals(orderRecoredBO.getProject()) || Constants.TIMEOUTCOST.getValue().equals(orderRecoredBO.getProject())
            ) {
                //商品 赔偿  办卡 超时费
                log.info("start transferAccounts....OtherRate.....................................................");
                childOrderDAO.reduceOtherRate(shiftToId, orderRecoredBO.getMoney());
                childOrderDAO.increaseOtherRate(rollOutId, orderRecoredBO.getMoney());

            } else if (Constants.ROOMRATEFREE.getValue().equals(orderRecoredBO.getProject()) ||
                    Constants.MITIGATE.getValue().equals(orderRecoredBO.getProject()) || Constants.COMMODITYFREE.getValue().equals(orderRecoredBO.getProject())
                    || Constants.COMPENSATIONFREE.getValue().equals(orderRecoredBO.getProject())) {
                log.info("start transferAccounts....free.....................................................");
                //免单 超时费减免
                childOrderDAO.reducefree(shiftToId, orderRecoredBO.getMoney());
                childOrderDAO.free(rollOutId, orderRecoredBO.getMoney());
            }

            ChildOrderBO childOrderBO = childOrderDAO.queryOrderChildById(rollOutId);
            orderRecoredBO.setInfo(orderRecoredBO.getInfo() + "(" + childOrderBO.getRoomName() + "转入)");
            orderRecoredBO.setOrderChildId(shiftToId);
            orderRecoredBO.setCreateUserId(userId);
            orderRecordService.updateRecord(orderRecoredBO);

        }

        log.info("end transferAccounts.........................................................");
    }

    /**
     * 单项结账查询
     */
    public Map<String, Object> queryChildleAccounts(String ids) {
        log.info("start queryChildleAccounts.........................................................");
        log.info("ids:{}", ids);
        List<Integer> list = StringUtils.strToList(ids);
        log.info("query PayType.................................................................");
        //初始化默认值
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("payType", new HashSet<String>());

        //有可能该房间没有订单详情,(被转走或都结账了)
        if (list != null) {
            Set<String> payType = orderRecordService.queryPayType(list);
            if (payType.isEmpty()) {
                payType.add(Constants.CASH.getValue());
            }
            payType.removeAll(Collections.singleton(null));
            payType.remove(Constants.INTEGRAL.getValue());
            log.info("PayType:{}", payType);
            resultMap.put("payType", payType);
        }
        //查询消费多少

        double consumption = 0;
        double pay = 0;
        if (list != null) {
            log.info("query consumption.................................................................");
            consumption = orderRecordService.consumption(list);
            log.info("consumption:{}", consumption);
            //查询交了多少
            log.info("query pay.................................................................");
            pay = orderRecordService.pay(list);
            log.info("pay:{}", pay);
        }

        if ((consumption + pay) > 0) {
            //退
            resultMap.put("status", "no");
        } else {
            //收
            resultMap.put("status", "yes");
        }
        resultMap.put("money", new BigDecimal(consumption + pay).abs());

        log.info("end queryChildleAccounts.........................................................");
        return resultMap;
    }


    /**
     * 单项结账
     *
     * @param userId
     * @param chilId
     * @param ids
     * @param payType 修改子订单中的所有结账项目,对应的数据
     *                判断收款还是退款,增加结算订单和报表
     */
    public String childleAccounts(Integer hotelId, Integer userId, Integer chilId, String ids, String payType, PayTypeBO param, String status) {
        StringBuffer buffer = new StringBuffer("");
        String[] split = ids.split(",");
        for (int i = 0; i < split.length; i++) {
            buffer.append(split[i]).append(",");
            OrderRecoredBO orderRecoredBO = orderRecordService.queryOrderRecordById(Integer.parseInt(split[i]));
            //押金
            if (Constants.CASHPLEDGE.getValue().equals(orderRecoredBO.getProject())) {
                log.info("start transferAccounts....CASHPLEDGE.....................................................");
                //现金
                if (Constants.CASH.getValue().equals(orderRecoredBO.getPayType())) {
                    log.info("start reduceCashCashPledge....CASH.....................................................");
                    childOrderDAO.reduceCashCashPledge(chilId, orderRecoredBO.getMoney());
                } else {
                    //非现金
                    log.info("start reduceOtherCashPledge....OtherCashPledge.....................................................");
                    childOrderDAO.reduceOtherCashPledge(chilId, orderRecoredBO.getMoney());
                }

            } else if (Constants.ROOMRATE.getValue().equals(orderRecoredBO.getProject())) {

                //房费
                log.info("start reduceRoomRate....ROOMRATE.....................................................");
                childOrderDAO.increaseRoomRate(chilId, orderRecoredBO.getMoney());

            } else if (Constants.COMMODITY.getValue().equals(orderRecoredBO.getProject()) || Constants.COMPENSATE.getValue().equals(orderRecoredBO.getProject())
                    || Constants.TIMEOUTCOST.getValue().equals(orderRecoredBO.getProject()) || Constants.APPLYCARD.getValue().equals(orderRecoredBO.getProject())) {
                //商品 赔偿 超时费 办卡
                log.info("start reduceOtherRate....OtherRate.....................................................");
                childOrderDAO.increaseOtherRate(chilId, orderRecoredBO.getMoney());
            } else if (Constants.ROOMRATEFREE.getValue().equals(orderRecoredBO.getProject()) ||
                    Constants.MITIGATE.getValue().equals(orderRecoredBO.getProject()) || Constants.COMMODITYFREE.getValue().equals(orderRecoredBO.getProject())
                    || Constants.COMPENSATIONFREE.getValue().equals(orderRecoredBO.getProject())) {
                log.info("start reducefree....reducefree.....................................................");
                //免单
                childOrderDAO.free(chilId, orderRecoredBO.getMoney());
            }

        }

        ChildOrderBO childOrderBO = childOrderDAO.queryOrderChildById(chilId);
        if (status.equals("yes")) {
            log.info("start gathering........................................................");
            //收款
            Integer id = orderRecordService.addOrderRecord(chilId, Constants.CONSUMPTIONITEM.getValue(), payType, param.getMoney(), Constants.SETTLE.getValue(), userId, null, Constants.YES.getValue());
            cashierSummaryService.addAccounts(param.getMoney(), childOrderBO.getOrderNumber(), userId, childOrderBO.getName(), childOrderBO.getOTA(), payType,
                    childOrderBO.getPassengerSource(), childOrderBO.getChannel(), childOrderBO.getRoomName(), childOrderBO.getRoomTypeName(),
                    Constants.CONSUMPTIONITEM.getValue(), hotelId);
            buffer.append(id).append(",");
            //储值支付
            if (payType.equals(Constants.STORED.getValue())) {
                memberService.storedValuePay(param.getCertificateNumber(), param.getMoney(), Constants.CONSUMPTIONITEM.getValue(), "储值支付", new BigDecimal(0), userId);
            }
            //积分减免
            if (param.getIntegral() != null) {
                Integer intId = orderRecordService.addOrderRecord(chilId, Constants.CONSUMPTIONITEM.getValue(), Constants.INTEGRAL.getValue(), param.getIntegral(), Constants.SETTLE.getValue(), userId, null, Constants.YES.getValue());
                buffer.append(intId).append(",");
                memberService.integralBreaks(param.getCertificateNumber(), param.getIntegral(), Constants.CONSUMPTIONITEM.getValue(), "积分支付", userId);
                cashierSummaryService.addAccounts(param.getIntegral(), childOrderBO.getOrderNumber(), userId, childOrderBO.getName(), childOrderBO.getOTA(), Constants.INTEGRAL.getValue(),
                        childOrderBO.getPassengerSource(), childOrderBO.getChannel(), childOrderBO.getRoomName(), childOrderBO.getRoomTypeName(),
                        Constants.CONSUMPTIONITEM.getValue(), hotelId);
            }
        } else {
            //退款
            String str = this.outMoney(chilId, param, userId, childOrderBO, hotelId);
            buffer.append(str);
        }


        orderRecordService.closedAccount(StringUtils.strToList(ids));
        // 是会员应增加相对应积分
        if (childOrderBO.getMembersId() != null) {
            if (status.equals("yes") && param.getMoney().compareTo(new BigDecimal("0")) == 1) {
                BigDecimal bigDecimal = memberService.accountIntegral(childOrderBO.getMembersId(), param.getMoney(), "结账", userId);
                cashierSummaryService.addAccounts(bigDecimal, childOrderBO.getOrderNumber(), userId, childOrderBO.getName(), childOrderBO.getOTA(), Constants.INTEGRAL.getValue(),
                        childOrderBO.getPassengerSource(), childOrderBO.getChannel(), childOrderBO.getRoomName(), childOrderBO.getRoomTypeName(),
                        Constants.CONSUMPTIONITEM.getValue(), hotelId);
            }
        }
        return buffer.toString();
    }


    //判读是否有未退房的(结账)
    public boolean ifCheckOut(String chidIds) {
        List<Integer> list = StringUtils.strToList(chidIds);
        return childOrderDAO.ifCheckOut(list) > 0;
    }

    public Map<String, Object> queryAccounts(String chilIds) {
        List<Integer> list = orderRecordService.queryRecordIds(StringUtils.strToList(chilIds));
        return queryChildleAccounts(StringUtils.listToStr(list, ","));
    }


    /**
     * 总结账
     */
    public void accounts(Integer hotelId, Integer userId, String ids, String payType, PayTypeBO param, String status) {
        log.info("start accounts........................................................");
        //找出主订单
        String[] split = ids.split(",");

        ChildOrderBO child = childOrderDAO.queryOrderChildById(Integer.parseInt(split[0]));
        Integer childMain = this.queryOrderChildMain(child.getAlRoomCode());
        ChildOrderBO childOrderBO = childOrderDAO.queryOrderChildById(childMain);
        if (status.equals("yes")) {
            log.info("start gathering........................................................");
            if (payType.equals(Constants.CASH.getValue())) {
                //如果是现金
                childOrderDAO.increaseCashCashPledge(childMain, param.getMoney());
            } else {
                childOrderDAO.increaseOtherCashPledge(childMain, param.getMoney());
            }

            //收款
            orderRecordService.addOrderRecord(childMain, Constants.CONSUMPTIONITEM.getValue(), payType, param.getMoney(), Constants.SETTLE.getValue(), userId, null, Constants.YES.getValue());
            cashierSummaryService.addAccounts(param.getMoney(), childOrderBO.getOrderNumber(), userId, childOrderBO.getName(), childOrderBO.getOTA(), payType,
                    childOrderBO.getPassengerSource(), childOrderBO.getChannel(), childOrderBO.getRoomName(), childOrderBO.getRoomTypeName(),
                    Constants.CONSUMPTIONITEM.getValue(), hotelId);
            //储值支付
            if (payType.equals(Constants.STORED.getValue())) {
                memberService.storedValuePay(param.getCertificateNumber(), param.getMoney(), Constants.CONSUMPTIONITEM.getValue(), "储值支付", new BigDecimal(0), userId);
            }

            //积分减免
            if (param.getIntegral() != null) {
                orderRecordService.addOrderRecord(childMain, Constants.CONSUMPTIONITEM.getValue(), Constants.INTEGRAL.getValue(), param.getIntegral(), Constants.SETTLE.getValue(), userId, null, Constants.YES.getValue());
                childOrderDAO.free(childMain, param.getIntegral().multiply(new BigDecimal("-1")));
                memberService.integralBreaks(param.getCertificateNumber(), param.getIntegral(), Constants.CONSUMPTIONITEM.getValue(), "积分支付", userId);
                cashierSummaryService.addAccounts(param.getIntegral(), childOrderBO.getOrderNumber(), userId, childOrderBO.getName(), childOrderBO.getOTA(), Constants.INTEGRAL.getValue(),
                        childOrderBO.getPassengerSource(), childOrderBO.getChannel(), childOrderBO.getRoomName(), childOrderBO.getRoomTypeName(),
                        Constants.CONSUMPTIONITEM.getValue(), hotelId);
            }
        } else {
            if (param.getCash() != null) {
                childOrderDAO.reduceCashCashPledge(childMain, param.getCash());
            }
            if (param.getCart() != null) {
                childOrderDAO.reduceOtherCashPledge(childMain, param.getCart());
            }
            if (param.getWechat() != null) {
                childOrderDAO.reduceOtherCashPledge(childMain, param.getWechat());
            }
            if (param.getAlipay() != null) {
                childOrderDAO.reduceOtherCashPledge(childMain, param.getAlipay());
            }
            if (param.getStored() != null) {
                childOrderDAO.reduceOtherCashPledge(childMain, param.getStored());
            }
            if (param.getOther() != null) {
                childOrderDAO.reduceOtherCashPledge(childMain, param.getOther());
            }
            this.outMoney(childMain, param, userId, childOrderBO, hotelId);
        }

        //修改所有订单为已经结账
        orderRecordService.completeAccount(StringUtils.strToList(ids));
        // 是会员应增加相对应积分
        if (childOrderBO.getMembersId() != null) {
            ChildOrderBO newChild = childOrderDAO.queryOrderChildById(childMain);
            if ((newChild.getPayCashNum().add(newChild.getOtherPayNum())).compareTo(new BigDecimal("0")) == 1) {
                BigDecimal bigDecimal = memberService.accountIntegral(childOrderBO.getMembersId(), newChild.getPayCashNum().add(newChild.getOtherPayNum()), "结账", userId);
                cashierSummaryService.addAccounts(bigDecimal.multiply(new BigDecimal("-1")), childOrderBO.getOrderNumber(), userId, childOrderBO.getName(), childOrderBO.getOTA(), Constants.INTEGRAL.getValue(),
                        childOrderBO.getPassengerSource(), childOrderBO.getChannel(), childOrderBO.getRoomName(), childOrderBO.getRoomTypeName(),
                        Constants.CONSUMPTIONITEM.getValue(), hotelId);
            }
        }
        log.info("end accounts........................................................");
    }


    /**
     * 退款方法
     */
    public String outMoney(Integer childId, PayTypeBO param, Integer userId, ChildOrderBO childOrderBO, Integer hotelId) {
        StringBuffer buffer = new StringBuffer("");
        //退款
        log.info("start outMoney...........................................................");
        if (param.getCash() != null) {  //现金
            log.info("start.....refund...cash........................................................");
            Integer id = orderRecordService.addOrderRecord(childId, Constants.CONSUMPTIONITEM.getValue(), Constants.CASH.getValue(), param.getCash().
                    multiply(new BigDecimal("-1")), Constants.SETTLE.getValue(), userId, null, Constants.YES.getValue());
            cashierSummaryService.addAccounts(param.getCash().multiply(new BigDecimal("-1")), childOrderBO.getOrderNumber(), userId, childOrderBO.getName(), childOrderBO.getOTA(),
                    Constants.CASH.getValue(), childOrderBO.getPassengerSource(), childOrderBO.getChannel(), childOrderBO.getRoomName(), childOrderBO.getRoomTypeName(),
                    Constants.CONSUMPTIONITEM.getValue(), hotelId);
            buffer.append(id).append(",");
        }
        if (param.getCart() != null) {  //银行卡
            log.info("start.....refund...cart........................................................");
            Integer id = orderRecordService.addOrderRecord(childId, Constants.CONSUMPTIONITEM.getValue(), Constants.CART.getValue(), param.getCart().
                    multiply(new BigDecimal("-1")), Constants.SETTLE.getValue(), userId, null, Constants.YES.getValue());
            cashierSummaryService.addAccounts(param.getCart().multiply(new BigDecimal("-1")), childOrderBO.getOrderNumber(), userId, childOrderBO.getName(), childOrderBO.getOTA(),
                    Constants.CART.getValue(), childOrderBO.getPassengerSource(), childOrderBO.getChannel(), childOrderBO.getRoomName(), childOrderBO.getRoomTypeName(),
                    Constants.CONSUMPTIONITEM.getValue(), hotelId);
            buffer.append(id).append(",");
        }
        if (param.getWechat() != null) {  //微信
            log.info("start.....refund...wechat........................................................");
            Integer id = orderRecordService.addOrderRecord(childId, Constants.CONSUMPTIONITEM.getValue(), Constants.WECHAT.getValue(), param.getWechat().
                    multiply(new BigDecimal("-1")), Constants.SETTLE.getValue(), userId, null, Constants.YES.getValue());
            cashierSummaryService.addAccounts(param.getWechat().multiply(new BigDecimal("-1")), childOrderBO.getOrderNumber(), userId, childOrderBO.getName(), childOrderBO.getOTA(),
                    Constants.WECHAT.getValue(), childOrderBO.getPassengerSource(), childOrderBO.getChannel(), childOrderBO.getRoomName(), childOrderBO.getRoomTypeName(),
                    Constants.CONSUMPTIONITEM.getValue(), hotelId);
            buffer.append(id).append(",");
        }
        if (param.getAlipay() != null) {  //支付宝
            log.info("start.....refund...alipay........................................................");
            Integer id = orderRecordService.addOrderRecord(childId, Constants.CONSUMPTIONITEM.getValue(), Constants.ALIPAY.getValue(), param.getAlipay().
                    multiply(new BigDecimal("-1")), Constants.SETTLE.getValue(), userId, null, Constants.YES.getValue());
            cashierSummaryService.addAccounts(param.getAlipay().multiply(new BigDecimal("-1")), childOrderBO.getOrderNumber(), userId, childOrderBO.getName(), childOrderBO.getOTA(),
                    Constants.ALIPAY.getValue(), childOrderBO.getPassengerSource(), childOrderBO.getChannel(), childOrderBO.getRoomName(), childOrderBO.getRoomTypeName(),
                    Constants.CONSUMPTIONITEM.getValue(), hotelId);
            buffer.append(id).append(",");
        }
        if (param.getOther() != null) {  //其他
            log.info("start.....refund...other........................................................");
            Integer id = orderRecordService.addOrderRecord(childId, Constants.CONSUMPTIONITEM.getValue(), Constants.ALIPAY.getValue(), param.getOther().
                    multiply(new BigDecimal("-1")), Constants.SETTLE.getValue(), userId, null, Constants.YES.getValue());
            cashierSummaryService.addAccounts(param.getOther().multiply(new BigDecimal("-1")), childOrderBO.getOrderNumber(), userId, childOrderBO.getName(), childOrderBO.getOTA(),
                    Constants.OTHER.getValue(), childOrderBO.getPassengerSource(), childOrderBO.getChannel(), childOrderBO.getRoomName(), childOrderBO.getRoomTypeName(),
                    Constants.CONSUMPTIONITEM.getValue(), hotelId);
            buffer.append(id).append(",");
        }
        if (param.getStored() != null) {  //储值
            log.info("start.....refund...stored........................................................");
            if (childOrderBO.getMembersId() != null) {
                memberService.accountStoreValue(childOrderBO.getMembersId(), param.getStored(), "结账", userId);
            }
            Integer id = orderRecordService.addOrderRecord(childId, Constants.CONSUMPTIONITEM.getValue(), Constants.STORED.getValue(), param.getStored().
                    multiply(new BigDecimal("-1")), Constants.SETTLE.getValue(), userId, null, Constants.YES.getValue());
            cashierSummaryService.addAccounts(param.getStored().multiply(new BigDecimal("-1")), childOrderBO.getOrderNumber(), userId, childOrderBO.getName(), childOrderBO.getOTA(),
                    Constants.STORED.getValue(), childOrderBO.getPassengerSource(), childOrderBO.getChannel(), childOrderBO.getRoomName(), childOrderBO.getRoomTypeName(),
                    Constants.CONSUMPTIONITEM.getValue(), hotelId);
            buffer.append(id).append(",");
        }

        log.info("end outMoney...........................................................");
        return buffer.toString();

    }


    /**
     * 查询所有在住中的订单
     */
    public List<RoomRateBO> queryOrderChild() {
        return childOrderDAO.queryOrderChild();
    }

    /**
     * 查询没有夜审过的房费
     */
    public List<EverydayRoomPriceBO> queryRoomPrice(Integer childId, String time) {
        return childOrderDAO.queryRoomPrice(childId, time);
    }

    /**
     * 添加房费
     */
    public void increaseRoomRate(Integer childId, BigDecimal money) {
        childOrderDAO.increaseRoomRate(childId, money);
    }

    /**
     * 查询子订单首个在主人信息
     */
    public CommonBO queryChildName(Integer childId) {
        return childOrderDAO.queryChildName(childId);
    }

    /**
     * 修改夜审过的每日房费状态
     *
     * @param id
     */
    public void updateRoomPrice(Integer id) {
        childOrderDAO.updateRoomPrice(id);
    }


    /**
     * 单项结账打印
     */
    public Map<String, Object> alonePrint(String ids, Integer hotelId) {
        log.info("start  alonePrint.........................................");
        log.info("ids:{}\thotelId:{}", ids, hotelId);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<Integer> id = StringUtils.strToList(ids);
        //酒店信息
        HotelBO hotelBO = hotelService.queryHotelById(hotelId);
        PrintHotelBO printHotelBO = new PrintHotelBO();
        printHotelBO.setAddress(hotelBO.getSite());
        printHotelBO.setPhone(hotelBO.getPhone());

        //所有消费项目
        List<OrderRecoredBO> orderRecoredBOS = orderRecordService.queryOrderRecordByList(id);
        BigDecimal bigDecimal = orderRecordService.queryAloneMoney(id);
        printHotelBO.setMoney(bigDecimal);
        resultMap.put("hotel", printHotelBO);
        resultMap.put("data", orderRecoredBOS);
        log.info("end  alonePrint.........................................");
        return resultMap;

    }

    /**
     * 打印
     *
     * @param childId
     */
    public Map<String, Object> print(Integer childId, Integer hotelId) {
        log.info("start  print.........................................");
        log.info("childId:{}\thotelId:{}", childId, hotelId);
        Map<String, Object> resultMap = new HashMap<String, Object>();

        //通过联房识别码查出所有子订单
        ChildOrderBO childOrderBO = this.queryOrderChildById(childId);
        List<PrintBO> printBO = this.queryOrderChildByCode(childOrderBO.getAlRoomCode());

        //酒店信息
        HotelBO hotelBO = hotelService.queryHotelById(hotelId);
        PrintHotelBO printHotelBO = new PrintHotelBO();
        printHotelBO.setAddress(hotelBO.getSite());
        printHotelBO.setPhone(hotelBO.getPhone());

        //所有消费项目
        Integer mainId = queryOrderChildMain(childOrderBO.getAlRoomCode());
        List<OrderRecoredBO> orderRecoredBOS = orderRecordService.queryOrderRecordsByChildId(mainId);
        BigDecimal bigDecimal = orderRecordService.queryMoneyByChildId(mainId);
        printHotelBO.setMoney(bigDecimal);
        resultMap.put("hotel", printHotelBO);
        resultMap.put("data", orderRecoredBOS);
        resultMap.put("top", printBO);

        log.info("end  print.........................................");
        return resultMap;
    }


    private List<PrintBO> queryOrderChildByCode(String alRoomCode) {
        List<PrintBO> printBOS = childOrderDAO.queryOrderChildByCode(alRoomCode);
        if (printBOS != null && printBOS.size() > 0) {
            for (int i = 0; i < printBOS.size(); i++) {
                PrintBO printBO = printBOS.get(i);
                CommonBO commonBO = childOrderDAO.queryChildName(printBO.getId());
                if (commonBO != null) {
                    printBO.setName(commonBO.getName());
                }
            }
        }
        return printBOS;
    }


    public Integer queryPersonNumber(Integer childId) {
        return childOrderDAO.queryPersonNumber(childId);
    }

    /**
     * 判断是否联房
     *
     * @param shifoId
     * @param outId
     * @return
     */
    public boolean isAlRoom(Integer shifoId, Integer outId) {
        ChildOrderBO shifo = childOrderDAO.queryOrderChildById(shifoId);
        ChildOrderBO out = childOrderDAO.queryOrderChildById(outId);
        return shifo.getAlRoomCode().equals(out.getAlRoomCode());
    }
}