package com.szq.hotel.service;

import com.szq.hotel.common.constants.Constants;
import com.szq.hotel.dao.MemberCardDAO;
import com.szq.hotel.dao.MemberDAO;
import com.szq.hotel.entity.bo.*;
import com.szq.hotel.util.DateUtils;
import com.szq.hotel.util.ReadCardExcelUtil;
import com.szq.hotel.util.ReadMemberExcelUtil;
import com.szq.hotel.web.controller.MemberController;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.hssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("memberService")
@Transactional
public class MemberService {
    private static final Logger log = LoggerFactory.getLogger(MemberController.class);

    @Resource
    private MemberDAO memberDAO;
    @Resource
    private MemberCardDAO memberCardDAO;
    @Resource
    private StoredValueRecordService storedValueRecordService;
    @Resource
    private MemberLevelService memberLevelService;
    @Resource
    private IntegralRecordService integralRecordService;
    @Resource
    private HotelService hotelService;

    /*
        新增会员
     */
    public void addMember(MemberBO memberBO,Integer userId){
        log.info("start================addMember");
        memberBO.setCreateUserId(userId);
        memberDAO.addMember(memberBO);
        log.info("end================addMember");
    }
    /*
        删除会员信息
     */
    public void deleteMember(Integer id){
        log.info("start================deleteMember");
        log.info("id{}",id);

        MemberBO memberBO = memberDAO.queryMemberById(id);
        if(memberBO==null){
            return;
        }
        memberBO.setState(Constants.NO.getValue());
        memberDAO.updateMember(memberBO);
        log.info("end===================deleteMember");
    }
    /*
        修改会员信息
     */
    public void updateMember(MemberBO memberBO,Integer userId){
        log.info("start================updateMember");
        log.info("param{}\tUserId{}",memberBO,userId);
        memberBO.setUpdateUserId(userId);
        memberDAO.updateMember(memberBO);
        log.info("end===================updateMember");
    }

    /*
        条件分页查询会员信息
     */
    public List<MemberBO> selectMember(Map<String,Object> map){
        return memberDAO.selectMember(map);
    }
    /*
        条件分页查询会员信息数量
     */
    public Integer selectMemberCount(Map<String,Object> map){
        return memberDAO.selectMemberCount(map);
    }


    /*
        通过id查询会员信息
     */
    public MemberBO queryMemberById(Integer id){
        return memberDAO.queryMemberById(id);
    }
        /*
            积分增减
         */
    public void integralChange(MemberBO memberBO,Integer userId){
        log.info("start================integralChange");
        log.info("param{}\tUserId{}",memberBO,userId);
        memberBO.setUpdateUserId(userId);
        memberDAO.integralChange(memberBO);
        log.info("end===================integralChange");
    }

    /**
     *  结账增加积分
     * @param memberId 会员id
     * @param money 金额
     * @param remark 备注
     * @param userId 操作人id
     */
    public void accountIntegral(Integer memberId,BigDecimal money,String remark,Integer userId){
        //通过id查询会员信息
        MemberBO memberBO = memberDAO.queryMemberById(memberId);
        Integer memberCardId = memberBO.getMemberCardId();
        //查询改会员所属会员级别
        MemberLevelBO memberLevelBO = memberLevelService.getLevelByCardId(memberCardId);
        BigDecimal consumeGetIntegral = memberLevelBO.getConsumeGetIntegral();
        //积分数 = 消费金额 * 比例
        BigDecimal integral = money.multiply(consumeGetIntegral);
        memberBO.setIntegral(integral);
        memberBO.setUpdateUserId(userId);
        memberDAO.integralChange(memberBO);
        //查询最新
        MemberBO memberBO1 = memberDAO.queryMemberById(memberId);
        String type = "结账增加";
        integralRecordService.addIntegralRecord(memberId,integral,remark,type,memberBO1.getIntegral(),userId);

    }

    /**
     * 积分减免
     * @param certificateNumber 证件号
     * @param subtractMoney 减免多少钱
     * @param remark 备注
     * @param type 类型
     * @param userId 操作人id
     */
    public void integralBreaks(String certificateNumber,BigDecimal subtractMoney,String remark,String type,Integer userId){
        //通过证件号获取会员对象
        MemberBO memberBO = this.selectMemberByCerNumber(certificateNumber);
        Integer cardId = memberBO.getMemberCardId();
        //通过会员卡id查找会员级别对象
        MemberLevelBO memberLevelBO = memberLevelService.getLevelByCardId(cardId);
        //得到消费1元获得多少积分
        BigDecimal consumeGetIntegral = memberLevelBO.getConsumeGetIntegral();
        //得出应该减多少积分
        BigDecimal integral = subtractMoney.divide(consumeGetIntegral);
        Map<String,Object> map = new HashMap<String, Object>();
        //进行封装
        map.put("certificateNumber",certificateNumber);
        map.put("integral",integral);
        memberDAO.integralBreaks(map);
        //查询最新
        MemberBO memberBO1 = this.selectMemberByCerNumber(certificateNumber);
        //添加到积分明细
        integralRecordService.addIntegralRecord(memberBO.getId(),integral,remark,type,memberBO1.getIntegral(),userId);


    }
    /*
        储值调整
     */
    public void storedValueChange(MemberBO memberBO,Integer userId){
        log.info("start================storedValueChange");
        log.info("param{}\tUserId{}",memberBO,userId);
        memberBO.setUpdateUserId(userId);
        memberDAO.storedValueChange(memberBO);
        log.info("end===================storedValueChange");
    }
    /*
        结账退储值
     */
    public void accountStoreValue(Integer memberId,BigDecimal money,String remark,Integer userId){
       MemberBO memberBO =  memberDAO.queryMemberById(memberId);
       memberBO.setUpdateUserId(userId);
       memberBO.setStoredValue(money);
       memberDAO.storedValueChange(memberBO);

       MemberBO memberBO1 = memberDAO.queryMemberById(memberId);

       String type = "结账退款";
       storedValueRecordService.addStoredValueRecord(memberId,money,remark,type,null,memberBO1.getStoredValue(),userId);
    }

    /**
     * 储值消费
     * @param id 会员id
     * @param subtractMoney 消费金额
     * @param remark 备注
     * @param type 类型
     * @param presenterMoney 赠送金额
     * @param currentBalance  当前余额
     * @param userId 操作人id
     */
    public void storedValueSubtract(Integer id,BigDecimal subtractMoney,String remark,String type,BigDecimal presenterMoney,BigDecimal currentBalance, Integer userId){
        MemberBO memberBO = new MemberBO();
        memberBO.setId(id);
        memberBO.setStoredValue(subtractMoney);
        memberDAO.storedValueSubtract(memberBO);

        storedValueRecordService.addStoredValueRecord(id,subtractMoney,remark,type,presenterMoney,currentBalance,userId);

    }

    /**
     * 储值支付
     * @param certificateNumber 证件号
     * @param subtractMoney 消费金额
     * @param remark 备注
     * @param type 类型
     * @param presenterMoney 赠送金额
     * @param userId 操作人id
     */
    public void storedValuePay(String certificateNumber,BigDecimal subtractMoney,String remark,String type,BigDecimal presenterMoney,Integer userId){
        MemberBO memberBO = this.selectMemberByCerNumber(certificateNumber);
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("certificateNumber",certificateNumber);
        map.put("subtractMoney",subtractMoney);
        memberDAO.storedValuePay(map);
        MemberBO memberBO1 = this.selectMemberByCerNumber(certificateNumber);

        storedValueRecordService.addStoredValueRecord(memberBO.getId(),subtractMoney,remark,type,presenterMoney,memberBO1.getStoredValue(),userId);

    }

    /*
       通过手机号查询会员信息
    */
    public MemberBO selectMemberByPhone(String phone){
        return memberDAO.selectMemberByPhone(phone);
    }

    /*
        通过证件号号查询会员信息
     */
    public MemberBO selectMemberByCerNumber(String certificateNumber){
        return memberDAO.selectMemberByCerNumber(certificateNumber);
    }
    /*
        通过证件号查询会员id
     */
    Integer getIdByCerNumber(String certificateNumber){
        return memberDAO.getIdByCerNumber(certificateNumber);
    }
    /*
        通过证件号查询储值和积分金额
     */
    public MemberBO getStoreValueIntegral(String certificateNumber){
        return memberDAO.getStoreValueIntegral(certificateNumber);
    }
    /*
        通过id查找会员卡号级别和积分
     */
    public MemberResultBO getMemberCardNumber(Integer memberId){
        return memberDAO.getMemberCardNumber(memberId);
    }

    /**
     * Excel 导入会员
     *
     * @param
     */
    public String readExcelFile(MultipartFile file,Integer userId) {
        String result = "";
        //创建处理EXCEL的类
        ReadMemberExcelUtil readMemberExcelUtil = new ReadMemberExcelUtil();
        //解析excel，获取上传的事件单
        List<Map<String, Object>> memberList = readMemberExcelUtil.getExcelInfo(file);
        //至此已经将excel中的数据转换到list里面了,接下来就可以操作list,可以进行保存到数据库,或者其他操作,
        MemberBO memberBO = new MemberBO();
        MemberCardBO memberCardBO = new MemberCardBO();
        List<String> cardNumberList = new ArrayList<String>();
        List<String> certificateNumberList = new ArrayList<String>();
        List<String> phoneList = new ArrayList<String>();
        for(Map<String, Object> member:memberList) {
            memberCardBO.setCardNumber(member.get("cardNumber").toString());
            cardNumberList.add(memberCardBO.getCardNumber());
            certificateNumberList.add(member.get("certificateNumber").toString());
            phoneList.add(member.get("phone").toString());


            //TODO
        }

        List<MemberCardBO> memberCardBOS = memberCardDAO.getCartByCartList(cardNumberList);
        List<MemberBO> memberBOSNumber = memberDAO.getMemberByNumberList(certificateNumberList);
        List<MemberBO> memberBOSPhone = memberDAO.getMemberByPhoneList(phoneList);
        if (memberBOSNumber.size()!=0){
            result = "会员证件号已存在";
            return result;
        }
        if (memberBOSPhone.size()!=0){
            result = "会员手机号已存在";
            return result;
        }
        if (memberCardBOS.size()==0){
            result = "会员卡号不存在";
        }else {
            for(Map<String, Object> member:memberList) {
                memberBO.setPhone(member.get("phone").toString());
                memberBO.setName(member.get("name").toString());
                memberBO.setBirthday(member.get("birthday").toString());
                memberBO.setCertificateType(member.get("certificateType").toString());
                memberBO.setCertificateNumber(member.get("certificateNumber").toString());
                memberBO.setGender(member.get("gender").toString());
                memberBO.setAddress(member.get("address").toString());
                memberBO.setSalesman(member.get("salesman").toString());
                memberBO.setRemark(member.get("remark").toString());
                memberCardBO.setCardNumber(member.get("cardNumber").toString());
                memberCardBO.setSellingTime(DateUtils.parseDate(member.get("sellingTime").toString(),"yyyy-MM-dd HH:mm:ss") );

                MemberCardBO memberCardBO1 = memberCardDAO.getCardNumber(memberCardBO.getCardNumber());
                memberBO.setMemberCardId(memberCardBO1.getId());

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("cardNumber",member.get("cardNumber").toString());
                map.put("sellingTime",member.get("sellingTime").toString());
                //修改会员卡状态和售出时间
                memberCardDAO.updateSellingTimeByNum(map);
                memberBO.setCreateUserId(userId);
                int ret = memberDAO.importMember(memberBO);
                if (ret == 0) {
                    result = "插入数据库失败";
                    return result;
                }

            }
            if (memberList != null && !memberList.isEmpty()) {
                result = "上传成功！";
            } else {
                result = "上传失败！";
            }
        }

        return result;
    }


    /**
     * 导出会员
     * @param titles
     * @param out
     * @throws Exception
     */
    public void export(String[] titles, ServletOutputStream out,Integer hotelId) throws Exception{

        try{
            // 第一步，创建一个workbook，对应一个Excel文件
            HSSFWorkbook workbook = new HSSFWorkbook();

            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
            HSSFSheet hssfSheet = workbook.createSheet("会员总报表");

            // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
            HSSFRow row = hssfSheet.createRow(0);
            // 第四步，创建单元格，并设置值表头 设置表头居中
            HSSFCellStyle hssfCellStyle = workbook.createCellStyle();

            //居中样式
            //hssfCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

            HSSFCell hssfCell = null;
            for (int i = 0; i < titles.length; i++) {
                hssfCell = row.createCell(i);//列索引从0开始
                hssfCell.setCellValue(titles[i]);//列名1
                hssfCell.setCellStyle(hssfCellStyle);//列居中显示
            }

            // 第五步，写入实体数据 从数据库查出来大的集合

            List<ExportMemberResultBO> list = this.exportMember();

            if(list==null){
                return;
            }


            for (int i = 0; i < list.size(); i++) {
                row = hssfSheet.createRow(i+1);
                //  Vehicle vehicle = list.get(i);
                ExportMemberResultBO exportMemberResultBO =list.get(i);
                // 第六步，创建单元格，并设置值
                //卡号
                String  cartNumber = "";
                if(exportMemberResultBO.getCardNumber()!= null){
                    cartNumber= exportMemberResultBO.getCardNumber();
                }
                row.createCell(0).setCellValue(cartNumber);

                //注册店

                String hotelName= hotelService.queryHotelById(hotelId).getName();

                row.createCell(1).setCellValue(hotelName);

                //姓名
                String name = "";
                if( exportMemberResultBO.getName() != null){
                    name= exportMemberResultBO.getName();
                }
                row.createCell(2).setCellValue(name);

                //会员级别
                String memberLevelName = "";
                if( exportMemberResultBO.getMemberLevelName() != null){
                    memberLevelName = exportMemberResultBO.getMemberLevelName();
                }
                row.createCell(3).setCellValue(memberLevelName);

                //生日
                String birthday = "";
                if( exportMemberResultBO.getBirthday() != null){
                    birthday=exportMemberResultBO.getBirthday();

                }
                row.createCell(4).setCellValue(birthday);


            //手机号
            String phone = "";
            if( exportMemberResultBO.getPhone() != null){
                phone= exportMemberResultBO.getPhone() ;
            }
            row.createCell(5).setCellValue(phone);

            //会员折扣
            String memberDiscount = "";
            if( exportMemberResultBO.getMemberDiscount() != null){
                memberDiscount= exportMemberResultBO.getMemberDiscount();
            }
            row.createCell(6).setCellValue(memberDiscount);

            //消费合计
            String payCount = "";
            if( exportMemberResultBO.getPayCount() != null){
                payCount= exportMemberResultBO.getPayCount();
            }
            row.createCell(7).setCellValue(payCount);

            //储值总金额
                String sumStoreValue ="";
                if (exportMemberResultBO.getId()!=null) {
                   sumStoreValue = this.getMemberSumStoreValue(exportMemberResultBO.getId()).toString();
                }
            row.createCell(8).setCellValue(sumStoreValue);

            //储值余额
            String storeValueBalance = "";
            if(exportMemberResultBO.getStoreValueBalance() != null){
                storeValueBalance= exportMemberResultBO.getStoreValueBalance();
            }
            row.createCell(9).setCellValue(storeValueBalance);

            //累计积分
            String sumIntegral = "";
            if( exportMemberResultBO.getId() != null){
                sumIntegral= this.getSumIntegral(exportMemberResultBO.getId()).toString();
            }
            row.createCell(10).setCellValue(sumIntegral);
//"卡号", "注册分店", "姓名", "会员级别" ,"生日","手机号","会员折扣","消费合计","储值总金额","储值余额","累计积分","已兑积分","剩余积分","销售员","发卡日期","证件类型","证件号"
            //已兑积分
            String conversionIntegral = "";
                if( exportMemberResultBO.getId() != null){
                    conversionIntegral= this.getConversionIntegral(exportMemberResultBO.getId()).toString();
                }
            row.createCell(11).setCellValue(conversionIntegral);

            //剩余积分
            String integralBalance = "";
                if( exportMemberResultBO.getIntegralBalance() != null){
                    integralBalance= exportMemberResultBO.getIntegralBalance();
                }
            row.createCell(12).setCellValue(integralBalance);

            //销售员
            String salesman = "";
                if( exportMemberResultBO.getSalesman() != null){
                    salesman= exportMemberResultBO.getSalesman();
                }
            row.createCell(13).setCellValue(salesman);

            //发卡日期
            String sellingTime = "";
                if( exportMemberResultBO.getSellingTime() != null){
                    Date date= exportMemberResultBO.getSellingTime();
                    SimpleDateFormat simp=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    sellingTime=simp.format(date);
                }
            row.createCell(14).setCellValue(sellingTime);

            //证件类型
            String certificateType = "";
                if( exportMemberResultBO.getCertificateType() != null){
                    certificateType= exportMemberResultBO.getCertificateType();
                }
            row.createCell(15).setCellValue(certificateType);

            //证件号
            String certificateNumber = "";
                if( exportMemberResultBO.getCertificateNumber() != null){
                    certificateNumber= exportMemberResultBO.getCertificateNumber();
                }
            row.createCell(16).setCellValue(certificateNumber);

            }
            // 第七步，将文件输出到客户端浏览器
            try{
                workbook.write(out);
                out.flush();
                out.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }catch(Exception e){
            e.printStackTrace();
            throw new Exception("导出信息失败！");

        }
    }

    //Excel导出会员
    public List<ExportMemberResultBO> exportMember(){
            return memberDAO.exportMember();
    }

    //通过证件号和手机号查询会员
    public MemberBO getMemberByCerNumber(String phone,String certificateNumber){
        return memberDAO.getMemberByCerNumber(phone,certificateNumber);
    }

    //   消费合计
    public BigDecimal queryPayCount(Integer memberId){
        BigDecimal bigDecimal = memberDAO.queryPayCount(memberId);
        return isNullBig(bigDecimal);
    }
    //  总储值
    public BigDecimal  getMemberSumStoreValue(Integer memberId){
        BigDecimal bigDecimal = memberDAO.getMemberSumStoreValue(memberId);
        return isNullBig(bigDecimal);
    }
    //  已对积分
    public BigDecimal  getConversionIntegral(Integer memberId){
        BigDecimal bigDecimal = memberDAO.getConversionIntegral(memberId);
        return isNullBig(bigDecimal);
    }
    // 累计积分
    public BigDecimal  getSumIntegral(Integer memberId){
        BigDecimal bigDecimal = memberDAO.getSumIntegral(memberId);
        return isNullBig(bigDecimal);
    }

    private BigDecimal  isNullBig(BigDecimal  bigDecimal){
        if(bigDecimal==null){
            return  new BigDecimal(0);
        }
        return bigDecimal;
    }

}
