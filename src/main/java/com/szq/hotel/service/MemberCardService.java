package com.szq.hotel.service;

import com.szq.hotel.common.constants.Constants;
import com.szq.hotel.dao.MemberCardDAO;
import com.szq.hotel.entity.bo.MemberCardBO;
import com.szq.hotel.util.ReadExcelUtil;
import com.szq.hotel.web.controller.MemberCardController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;


@Service("memberCardService")
@Transactional
public class MemberCardService {
    private static final Logger log = LoggerFactory.getLogger(MemberCardController.class);

    @Resource
    private MemberCardDAO memberCardDAO;

    /*
        批量添加会员卡
    */
    public void  addMemberCard(Map<String,Object> map){
        memberCardDAO.addMemberCard(map);
    }
    /*
        查询会员卡是否存在
     */
    public List<MemberCardBO> queryCartByCartList(List<String> list){
        return memberCardDAO.queryCartByCartList(list);
    }
    /*
        删除会员卡
     */
    public void deleteMemberCard(Integer id){
        log.info("start================deleteMemberCard");
        log.info("id{}",id);

        MemberCardBO memberCardBO = memberCardDAO.queryMemberCardById(id);
        if(memberCardBO==null){
            return;
        }

        memberCardBO.setState(Constants.NO.getValue());
        memberCardDAO.updateMemberCard(memberCardBO);
        log.info("end===================deleteMemberCard");
    }

    /**
     * 修改会员级别
     * @param memberCardBO
     */
    public void updateMemberCard(MemberCardBO memberCardBO){
        log.info("start================updateMemberCard");
        log.info("param{}",memberCardBO);
        memberCardDAO.updateMemberCard(memberCardBO);
        log.info("end===================updateMemberCard");
    }

    /*
        条件分页查询会员卡
     */
    public List<MemberCardBO> selectMemberCard(Map<String,Object> map){
        return memberCardDAO.selectMemberCard(map);
    }
    /*
        查询条数
     */
    public Integer getCount(Map<String,Object> map){
        return memberCardDAO.getCount(map);
    }

    /**
     * Excel 导入会员卡
     *
     * @param
     */
    public String readExcelFile(MultipartFile file) {
        String result = "";
        //创建处理EXCEL的类
        ReadExcelUtil readExcelUtil = new ReadExcelUtil();
        //解析excel，获取上传的事件单
        List<Map<String, Object>> memberCardList = readExcelUtil.getExcelInfo(file);
        //至此已经将excel中的数据转换到list里面了,接下来就可以操作list,可以进行保存到数据库,或者其他操作,
        MemberCardBO memberCardBO = new MemberCardBO();
        List<String> list = new ArrayList<String>();
        for(Map<String, Object> memberCard:memberCardList) {
            memberCardBO.setCardNumber(memberCard.get("cardNumber").toString());
            memberCardBO.setMoney(getBigDecimal(memberCard.get("money")));
            memberCardBO.setMemberLevelId(getIntegerByObject(memberCard.get("memberLevelId")));
            list.add(memberCardBO.getCardNumber());
        }
        List<MemberCardBO> memberCardBOS = memberCardDAO.queryCartByCartList(list);

        if (memberCardBOS.size()!=0){
            result = "会员卡已经存在";
        }else {
            for (Map<String, Object> memberCard : memberCardList) {

                memberCardBO.setCardNumber(memberCard.get("cardNumber").toString());
                memberCardBO.setMoney(getBigDecimal(memberCard.get("money")));
                memberCardBO.setMemberLevelId(getIntegerByObject(memberCard.get("memberLevelId")));

                list.add(memberCardBO.getCardNumber());
                int ret = memberCardDAO.addMemberCardTest(memberCardBO);
                if (ret == 0) {
                    result = "插入数据库失败";
                }

            }
            if (memberCardList != null && !memberCardList.isEmpty()) {
                result = "上传成功！";
            } else {
                result = "上传失败！";
            }
        }

        return result;
    }
    /*
        将object对象转换为BigDecimal
     */
    public static BigDecimal getBigDecimal(Object value) {
        BigDecimal ret = null;
        if (value != null) {
            if (value instanceof BigDecimal) {
                ret = (BigDecimal) value;
            } else if (value instanceof String) {
                ret = new BigDecimal((String) value);
            } else if (value instanceof BigInteger) {
                ret = new BigDecimal((BigInteger) value);
            } else if (value instanceof Number) {
                ret = new BigDecimal(((Number) value).doubleValue());
            } else {
                throw new ClassCastException("Not possible to coerce [" + value + "] from class " + value.getClass() + " into a BigDecimal.");
            }
        }
        return ret;
    }
    /*
        将object对象转换为Integer
     */
    public static Integer getIntegerByObject(Object object){
        Integer in = null;

        if(object!=null){
            if(object instanceof Integer){
                in = (Integer)object;
            }else if(object instanceof String){
                in = Integer.parseInt((String)object);
            }else if(object instanceof BigDecimal){
                in = ((BigDecimal)object).intValue();
            }else if(object instanceof Long){
                in = ((Long)object).intValue();
            }
        }

        return in;
    }

}
