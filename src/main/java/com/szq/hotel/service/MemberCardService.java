package com.szq.hotel.service;

import com.szq.hotel.common.constants.Constants;
import com.szq.hotel.dao.MemberCardDAO;
import com.szq.hotel.entity.bo.MemberCardBO;
import com.szq.hotel.entity.bo.MemberCardResultBO;
import com.szq.hotel.entity.bo.MemberLevelBO;
import com.szq.hotel.util.ObjectUtil;
import com.szq.hotel.util.ReadCardExcelUtil;
import com.szq.hotel.web.controller.MemberCardController;
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


@Service("memberCardService")
@Transactional
public class MemberCardService {
    private static final Logger log = LoggerFactory.getLogger(MemberCardController.class);

    @Resource
    private MemberCardDAO memberCardDAO;
    @Resource
    private MemberLevelService memberLevelService;

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
        条件查询会员卡
     */
    public List<MemberCardBO> conditionSelectMemberCard(Map<String,Object> map){
        return memberCardDAO.conditionSelectMemberCard(map);
    }
    /*
        导出会员卡
     */
    public List<MemberCardResultBO> exportMemberCard(Map<String,Object> map){
        return memberCardDAO.exportMemberCard(map);
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
        ReadCardExcelUtil readCardExcelUtil = new ReadCardExcelUtil();
        //解析excel，获取上传的事件单
        List<Map<String, Object>> memberCardList = readCardExcelUtil.getExcelInfo(file);
        //至此已经将excel中的数据转换到list里面了,接下来就可以操作list,可以进行保存到数据库,或者其他操作,
        MemberCardBO memberCardBO = new MemberCardBO();
        //MemberLevelBO memberLevelBO = new MemberLevelBO();
        List<String> list = new ArrayList<String>();
        for(Map<String, Object> memberCard:memberCardList) {
            memberCardBO.setCardNumber(memberCard.get("cardNumber").toString());
            list.add(memberCardBO.getCardNumber());
            //查询会员级别是否
            MemberLevelBO memberLevelBO = memberLevelService.selectMemberLevelByName(memberCard.get("memberLevelName").toString());
            if (memberLevelBO==null){
                 return "会员级别不存在";
            }
        }
        List<MemberCardBO> memberCardBOS = memberCardDAO.queryCartByCartList(list);
        if (memberCardBOS.size()!=0){
            result = "会员卡已经存在";
        }else {
            for (Map<String, Object> memberCard : memberCardList) {

                memberCardBO.setCardNumber(memberCard.get("cardNumber").toString());
                memberCardBO.setMoney(ObjectUtil.getBigDecimal(memberCard.get("money")));

                //查询会员级别是否存在
                MemberLevelBO memberLevelBO = memberLevelService.selectMemberLevelByName(memberCard.get("memberLevelName").toString());
                if (memberLevelBO!=null) {
                    memberCardBO.setMemberLevelId(memberLevelBO.getId());
                }
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

    /**
     * 导出会员卡
     * @param titles
     * @param out
     * @param state
     * @param name
     * @param money
     * @param cardNumber
     * @throws Exception
     */
    public void export(String[] titles, ServletOutputStream out, String state, String name, BigDecimal money, Integer cardNumber) throws Exception{

        try{
            // 第一步，创建一个workbook，对应一个Excel文件
            HSSFWorkbook workbook = new HSSFWorkbook();

            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
            HSSFSheet hssfSheet = workbook.createSheet("会员卡信息");

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
            Map<String,Object> map=new HashMap<String, Object>();
            map.put("state",state);
            map.put("name",name);
            map.put("money",money);
            map.put("cardNumber",cardNumber);

            List<MemberCardResultBO> list = this.exportMemberCard(map);

            if(list==null){
                return;
            }


            for (int i = 0; i < list.size(); i++) {
                row = hssfSheet.createRow(i+1);
                //  Vehicle vehicle = list.get(i);
                MemberCardResultBO memberCardResultBO =list.get(i);
                // 第六步，创建单元格，并设置值
                //卡号
                String  cartNumber = "";
                if(memberCardResultBO.getCardNumber()!= null){
                    cartNumber= memberCardResultBO.getCardNumber();
                }
                row.createCell(0).setCellValue(cartNumber);

                //价格
                String Money = "";
                if( memberCardResultBO.getMoney()!= null){

                    Money= memberCardResultBO.getMoney().toString();
                }
                row.createCell(1).setCellValue(Money);

                //会员卡级别名称
                String Name = "";
                if( memberCardResultBO.getName() != null){
                    Name= memberCardResultBO.getName() .toString();
                }
                row.createCell(2).setCellValue(Name);

                //卡状态
                String cardState = "";
                if( memberCardResultBO.getState() != null){
                    if ("unsold".equals(memberCardResultBO.getState())){
                        cardState="未售出";
                    }
                    if ("use".equals(memberCardResultBO.getState())){
                        cardState="使用中";
                    }
                    if ("freeze".equals(memberCardResultBO.getState())){
                        cardState="冻结";
                    }
                }
                //未售出unsold/使用中use/冻结freeze/删除no
                row.createCell(3).setCellValue(cardState);

                //会员卡售出时间
                String sellingTime = "";
                if( memberCardResultBO.getSellingTime() != null){
                    Date date= memberCardResultBO.getSellingTime();
                    SimpleDateFormat simp=new SimpleDateFormat("yyyy-MM-dd");
                    sellingTime=simp.format(date);

                }
                row.createCell(4).setCellValue(sellingTime);
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

    /*
            通过会员id查找会员卡信息
         */
    public MemberCardBO getCardByMemberId(Integer memberId){
        return memberCardDAO.getCardByMemberId(memberId);
    }

    /*
        查询会员卡卡号和售价
     */
    public MemberCardBO getMemberNumberMoney(Integer memberLevelId){
        return memberCardDAO.getMemberNumberMoney(memberLevelId);
    }

    /*
        更新会员卡售出时间
     */
    public void updateSellingTime(String cardNumber){
        memberCardDAO.updateSellingTime(cardNumber);
    }
    public MemberCardBO getCardNumber(String cardNumber){
        return memberCardDAO.getCardNumber(cardNumber);
    }

    /*
        Excel导入会员添加会员卡信息
     */
    public void addMemberCardExcel(MemberCardBO memberCardBO){
        memberCardDAO.addMemberCardExcel(memberCardBO);
    }
    //通过卡号修改会员卡状态和售出时间
    void updateSellingTimeByNum(Map<String,Object> map){
        memberCardDAO.updateSellingTimeByNum(map);
    }

}
