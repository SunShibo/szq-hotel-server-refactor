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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        orderRecordService.addOrderRecord(orderChildId, "入住押金", payType, money, Constants.CASHPLEDGE.getValue(), userId, null, Constants.NO.getValue());
        //增加金额
        if (Constants.CASH.getValue().equals(payType)) {
            log.info("increaseCashCashPledge...............................");
            childOrderDAO.increaseCashCashPledge(orderChildId, money);
        } else {
            log.info("increaseOtherCashPledge...............................");
            childOrderDAO.increaseOtherCashPledge(orderChildId, money);
        }

        ChildOrderBO order = childOrderDAO.queryOrderChildById(orderChildId);
        //报表
        cashierSummaryService.addCheck(money, payType, IDBuilder.getOrderNumber(), userId, order.getName(), order.getOTA(),
                order.getChannel(), order.getPassengerSource(), order.getRoomName(), order.getRoomTypeName(), null, hotelId);

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
        if (type.equals(Constants.ROOMRATE.getValue())) {
            log.info("increaseRoomRate...............................");
            childOrderDAO.increaseRoomRate(orderChildId, money);
        } else {
            log.info("increaseOtherRate...............................");
            childOrderDAO.increaseOtherRate(orderChildId, money);
        }

        ChildOrderBO order = childOrderDAO.queryOrderChildById(orderChildId);
        //报表
        cashierSummaryService.addAccount(type, money, order.getOrderNumber(), userId, order.getName(), order.getOTA(), order.getChannel(),
                order.getPassengerSource(), order.getRoomName(), order.getRoomTypeName(), designation, hotelId);
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
    public void free(Integer orderChildId, BigDecimal money, String remark, Integer userId, Integer hotelId,String type) {
        log.info("start free........................................");
        log.info("orderChildId:{}\tmoney:{}\tremark:{}\tuserId:{}\thotelId:{}\ttype:{}", orderChildId, money, remark, userId, hotelId,type);
        //生成记录
        orderRecordService.addOrderRecord(orderChildId, remark, null, money,type, userId, null, Constants.NO.getValue());
        log.info("free...................................");
        //变为负数
        BigDecimal negation = money.multiply(new BigDecimal("-1"));

        childOrderDAO.free(orderChildId, negation);

        ChildOrderBO order = childOrderDAO.queryOrderChildById(orderChildId);
        //报表
        cashierSummaryService.addFree(negation, order.getOrderNumber(), userId, order.getName(), order.getOTA(), order.getChannel(),
                order.getPassengerSource(), order.getRoomName(), order.getRoomTypeName(), remark, hotelId,type);
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
        return childOrderDAO.queryOrderChildMain(code);
    }

    /**
     * @param ids       详情id
     * @param shiftToId 转入id
     * @param rollOutId 转出id
     */
    public void transferAccounts(Integer userId, String ids, Integer shiftToId, Integer rollOutId) {
        log.info("start transferAccounts.........................................................");
        log.info("userId:{}\tids:{}\tshiftToId:{}\trollOutId:{}", userId, ids, shiftToId, rollOutId);
        String[] split = ids.split(",");
        for (int i = 0; i < split.length; i++) {
            OrderRecoredBO orderRecoredBO = orderRecordService.queryOrderRecordById(split.length);

            //押金
            if (Constants.CASHPLEDGE.getValue().equals(orderRecoredBO.getProject())) {
                log.info("start transferAccounts....CASHPLEDGE.....................................................");
                //现金
                if (Constants.CASH.getValue().equals(orderRecoredBO.getPayType())) {
                    log.info("start transferAccounts....CASH.....................................................");
                    childOrderDAO.increaseCashCashPledge(shiftToId, orderRecoredBO.getMoney());
                    childOrderDAO.reduceCashCashPledge(rollOutId, orderRecoredBO.getMoney());
                } else {
                    //非现金
                    log.info("start transferAccounts....OtherCashPledge.....................................................");
                    childOrderDAO.increaseOtherCashPledge(shiftToId, orderRecoredBO.getMoney());
                    childOrderDAO.reduceOtherCashPledge(rollOutId, orderRecoredBO.getMoney());
                }

            } else if (Constants.ROOMRATE.getValue().equals(orderRecoredBO.getProject())) {
                //房费
                log.info("start transferAccounts....ROOMRATE.....................................................");
                childOrderDAO.increaseRoomRate(shiftToId, orderRecoredBO.getMoney());
                childOrderDAO.reduceRoomRate(rollOutId, orderRecoredBO.getMoney());

            } else if (Constants.COMMODITY.getValue().equals(orderRecoredBO.getProject()) || Constants.COMPENSATE.getValue().equals(orderRecoredBO.getProject())
                    || Constants.TIMEOUTCOST.getValue().equals(orderRecoredBO.getProject())) {
                //商品 赔偿 超时费
                log.info("start transferAccounts....OtherRate.....................................................");
                childOrderDAO.increaseOtherRate(shiftToId, orderRecoredBO.getMoney());
                childOrderDAO.reduceOtherRate(rollOutId, orderRecoredBO.getMoney());

            } else if (Constants.ROOMRATEFREE.getValue().equals(orderRecoredBO.getProject()) ||
                    Constants.MITIGATE.getValue().equals(orderRecoredBO.getProject())||Constants.COMMODITYFREE.getValue().equals(orderRecoredBO.getProject())
                    ||  Constants.COMPENSATIONFREE.getValue().equals(orderRecoredBO.getProject())) {
                log.info("start transferAccounts....free.....................................................");
                //免单 超时费减免
                childOrderDAO.free(shiftToId, orderRecoredBO.getMoney());
                childOrderDAO.reducefree(rollOutId, orderRecoredBO.getMoney());
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
        List<String> payType = orderRecordService.queryPayType(list);
        payType.removeAll(Collections.singleton(null));
        log.info("PayType:{}", payType);
        //查询消费多少
        log.info("query consumption.................................................................");
        double consumption = orderRecordService.consumption(list);
        log.info("consumption:{}", consumption);
        //查询交了多少
        log.info("query pay.................................................................");
        double pay = orderRecordService.pay(list);
        log.info("pay:{}", pay);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if ((consumption + pay) > 0) {
            //退
            resultMap.put("status", "no");
        } else {
            //收
            resultMap.put("status", "yes");
        }
        resultMap.put("payType", payType);
        resultMap.put("money", consumption + pay);

        log.info("end queryChildleAccounts.........................................................");
        return resultMap;
    }


    /**
     * 子订单结账
     *
     * @param userId
     * @param chilId
     * @param ids
     * @param payType 修改子订单中的所有结账项目,对应的数据
     *                判断收款还是退款,增加结算订单和报表
     */
    public void childleAccounts(Integer hotelId, Integer userId, Integer chilId, String ids, String payType, PayTypeBO param, String status) {
        String[] split = ids.split(",");
        for (int i = 0; i < split.length; i++) {
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
                childOrderDAO.reduceRoomRate(chilId, orderRecoredBO.getMoney());

            } else if (Constants.COMMODITY.getValue().equals(orderRecoredBO.getProject()) || Constants.COMPENSATE.getValue().equals(orderRecoredBO.getProject())
                    || Constants.TIMEOUTCOST.getValue().equals(orderRecoredBO.getProject())) {
                //商品 赔偿 超时费
                log.info("start reduceOtherRate....OtherRate.....................................................");
                childOrderDAO.reduceOtherRate(chilId, orderRecoredBO.getMoney());
            } else  if (Constants.ROOMRATEFREE.getValue().equals(orderRecoredBO.getProject()) ||
                    Constants.MITIGATE.getValue().equals(orderRecoredBO.getProject())||Constants.COMMODITYFREE.getValue().equals(orderRecoredBO.getProject())
                    ||  Constants.COMPENSATIONFREE.getValue().equals(orderRecoredBO.getProject())) {
                log.info("start reducefree....reducefree.....................................................");
                //免单 超时费减免
                childOrderDAO.reducefree(chilId, orderRecoredBO.getMoney());
            }

        }

        ChildOrderBO childOrderBO = childOrderDAO.queryOrderChildById(chilId);
        if (status.equals("yes")) {
            log.info("start gathering........................................................");
            //收款
            orderRecordService.addOrderRecord(chilId, Constants.CONSUMPTIONITEM.getValue(), payType, param.getMoney(), Constants.SETTLE.getValue(), userId, null, Constants.YES.getValue());
            cashierSummaryService.addAccounts(param.getMoney(), childOrderBO.getOrderNumber(), userId, childOrderBO.getName(), childOrderBO.getOTA(), payType,
                    childOrderBO.getChannel(), childOrderBO.getPassengerSource(), childOrderBO.getRoomName(), childOrderBO.getRoomTypeName(),
                    Constants.CONSUMPTIONITEM.getValue(), hotelId);
            //储值支付
            if (payType.equals(Constants.STORED.getValue())) {
                memberService.storedValuePay(param.getCertificateNumber(), param.getMoney(), Constants.CONSUMPTIONITEM.getValue(), "储值支付", new BigDecimal("0"), userId);
            }
            //积分减免
            if (param.getIntegral() != null) {
                memberService.integralBreaks(param.getCertificateNumber(), param.getIntegral(), Constants.CONSUMPTIONITEM.getValue(), "积分支付", userId);
                cashierSummaryService.addAccounts(param.getIntegral(), childOrderBO.getOrderNumber(), userId, childOrderBO.getName(), childOrderBO.getOTA(), Constants.INTEGRAL.getValue(),
                        childOrderBO.getChannel(), childOrderBO.getPassengerSource(), childOrderBO.getRoomName(), childOrderBO.getRoomTypeName(),
                        Constants.CONSUMPTIONITEM.getValue(), hotelId);
            }
        } else {
            //退款
            this.outMoney(chilId, param, userId, childOrderBO, hotelId);
        }


        orderRecordService.closedAccount(StringUtils.strToList(ids));
        // 是会员应增加相对应积分
        if (childOrderBO.getMembersId() != null) {
            memberService.accountIntegral(childOrderBO.getMembersId(), new BigDecimal(orderRecordService.consumption(StringUtils.strToList(ids))), "结账", userId);
        }
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
                    childOrderBO.getChannel(), childOrderBO.getPassengerSource(), childOrderBO.getRoomName(), childOrderBO.getRoomTypeName(),
                    Constants.CONSUMPTIONITEM.getValue(), hotelId);
            //储值支付
            if (payType.equals(Constants.STORED.getValue())) {
                memberService.storedValuePay(param.getCertificateNumber(), param.getMoney(), Constants.CONSUMPTIONITEM.getValue(), "储值支付", new BigDecimal("0"), userId);
            }

            //积分减免
            if (param.getIntegral() != null) {
                memberService.integralBreaks(param.getCertificateNumber(), param.getIntegral(), Constants.CONSUMPTIONITEM.getValue(), "积分支付", userId);
                cashierSummaryService.addAccounts(param.getIntegral(), childOrderBO.getOrderNumber(), userId, childOrderBO.getName(), childOrderBO.getOTA(), Constants.INTEGRAL.getValue(),
                        childOrderBO.getChannel(), childOrderBO.getPassengerSource(), childOrderBO.getRoomName(), childOrderBO.getRoomTypeName(),
                        Constants.CONSUMPTIONITEM.getValue(), hotelId);
            }
        } else {
            this.outMoney(childMain, param, userId, childOrderBO, hotelId);
        }

        //修改所有订单为已经结账
        orderRecordService.completeAccount(StringUtils.strToList(ids));
        // 是会员应增加相对应积分
        if (childOrderBO.getMembersId() != null) {
            ChildOrderBO newChild = childOrderDAO.queryOrderChildById(childMain);
            memberService.accountIntegral(childOrderBO.getMembersId(), newChild.getPayCashNum().add(newChild.getOtherPayNum()), "结账", userId);
        }
        log.info("end accounts........................................................");
    }


    /**
     * 退款方法
     */
    public void outMoney(Integer childId,PayTypeBO    param, Integer userId, ChildOrderBO childOrderBO, Integer hotelId) {
        //退款
        log.info("start outMoney...........................................................");
        if (param.getCash() != null) {  //现金
            log.info("start.....refund...cash........................................................");
            orderRecordService.addOrderRecord(childId, Constants.CONSUMPTIONITEM.getValue(), Constants.CASH.getValue(), param.getCash().
                    multiply(new BigDecimal("-1")), Constants.SETTLE.getValue(), userId, null, Constants.YES.getValue());
            cashierSummaryService.addAccounts(param.getCash().multiply(new BigDecimal("-1")), childOrderBO.getOrderNumber(), userId, childOrderBO.getName(), childOrderBO.getOTA(),
                    Constants.CASH.getValue(), childOrderBO.getChannel(), childOrderBO.getPassengerSource(), childOrderBO.getRoomName(), childOrderBO.getRoomTypeName(),
                    Constants.CONSUMPTIONITEM.getValue(), hotelId);
        }
        if (param.getCart() != null) {  //银行卡
            log.info("start.....refund...cart........................................................");
            orderRecordService.addOrderRecord(childId, Constants.CONSUMPTIONITEM.getValue(), Constants.CART.getValue(), param.getCash().
                    multiply(new BigDecimal("-1")), Constants.SETTLE.getValue(), userId, null, Constants.YES.getValue());
            cashierSummaryService.addAccounts(param.getCash().multiply(new BigDecimal("-1")), childOrderBO.getOrderNumber(), userId, childOrderBO.getName(), childOrderBO.getOTA(),
                    Constants.CART.getValue(), childOrderBO.getChannel(), childOrderBO.getPassengerSource(), childOrderBO.getRoomName(), childOrderBO.getRoomTypeName(),
                    Constants.CONSUMPTIONITEM.getValue(), hotelId);
        }
        if (param.getWechat() != null) {  //微信
            log.info("start.....refund...wechat........................................................");
            orderRecordService.addOrderRecord(childId, Constants.CONSUMPTIONITEM.getValue(), Constants.WECHAT.getValue(), param.getCash().
                    multiply(new BigDecimal("-1")), Constants.SETTLE.getValue(), userId, null, Constants.YES.getValue());
            cashierSummaryService.addAccounts(param.getCash().multiply(new BigDecimal("-1")), childOrderBO.getOrderNumber(), userId, childOrderBO.getName(), childOrderBO.getOTA(),
                    Constants.WECHAT.getValue(), childOrderBO.getChannel(), childOrderBO.getPassengerSource(), childOrderBO.getRoomName(), childOrderBO.getRoomTypeName(),
                    Constants.CONSUMPTIONITEM.getValue(), hotelId);
        }
        if (param.getAlipay() != null) {  //支付宝
            log.info("start.....refund...alipay........................................................");
            orderRecordService.addOrderRecord(childId, Constants.CONSUMPTIONITEM.getValue(), Constants.ALIPAY.getValue(), param.getCash().
                    multiply(new BigDecimal("-1")), Constants.SETTLE.getValue(), userId, null, Constants.YES.getValue());
            cashierSummaryService.addAccounts(param.getCash().multiply(new BigDecimal("-1")), childOrderBO.getOrderNumber(), userId, childOrderBO.getName(), childOrderBO.getOTA(),
                    Constants.ALIPAY.getValue(), childOrderBO.getChannel(), childOrderBO.getPassengerSource(), childOrderBO.getRoomName(), childOrderBO.getRoomTypeName(),
                    Constants.CONSUMPTIONITEM.getValue(), hotelId);
        }
        if (param.getOther() != null) {  //其他
            log.info("start.....refund...other........................................................");
            orderRecordService.addOrderRecord(childId, Constants.CONSUMPTIONITEM.getValue(), Constants.ALIPAY.getValue(), param.getCash().
                    multiply(new BigDecimal("-1")), Constants.OTHER.getValue(), userId, null, Constants.YES.getValue());
            cashierSummaryService.addAccounts(param.getCash().multiply(new BigDecimal("-1")), childOrderBO.getOrderNumber(), userId, childOrderBO.getName(), childOrderBO.getOTA(),
                    Constants.OTHER.getValue(), childOrderBO.getChannel(), childOrderBO.getPassengerSource(), childOrderBO.getRoomName(), childOrderBO.getRoomTypeName(),
                    Constants.CONSUMPTIONITEM.getValue(), hotelId);
        }
        if (param.getStored() != null) {  //储值
            log.info("start.....refund...stored........................................................");
            if (childOrderBO.getMembersId() != null) {
                memberService.accountStoreValue(childOrderBO.getMembersId(), param.getStored(), "结账", userId);
            }
            orderRecordService.addOrderRecord(childId, Constants.CONSUMPTIONITEM.getValue(), Constants.ALIPAY.getValue(), param.getCash().
                    multiply(new BigDecimal("-1")), Constants.STORED.getValue(), userId, null, Constants.YES.getValue());
            cashierSummaryService.addAccounts(param.getCash().multiply(new BigDecimal("-1")), childOrderBO.getOrderNumber(), userId, childOrderBO.getName(), childOrderBO.getOTA(),
                    Constants.STORED.getValue(), childOrderBO.getChannel(), childOrderBO.getPassengerSource(), childOrderBO.getRoomName(), childOrderBO.getRoomTypeName(),
                    Constants.CONSUMPTIONITEM.getValue(), hotelId);
        }


        log.info("end outMoney...........................................................");
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
    public List<EverydayRoomPriceBO> queryRoomPrice(Integer childId,String time) {
        return childOrderDAO.queryRoomPrice(childId,time);
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
       return  childOrderDAO.queryChildName(childId);
    }

    /**
     * 修改夜审过的每日房费状态
     * @param id
     */
    public void updateRoomPrice(Integer id) {
        childOrderDAO.updateRoomPrice(id);
    }
}