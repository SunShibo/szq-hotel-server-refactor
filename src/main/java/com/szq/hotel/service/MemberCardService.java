package com.szq.hotel.service;

import com.szq.hotel.common.constants.Constants;
import com.szq.hotel.dao.MemberCardDAO;
import com.szq.hotel.entity.bo.MemberCardBO;
import com.szq.hotel.web.controller.MemberCardController;
import net.sf.json.JSONArray;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.math.BigDecimal;
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
     * @param file
     */
    public Integer importMemberCard(MultipartFile file) {
        int rows = 1;
        try {
            Workbook wb;
            try {
                wb = new XSSFWorkbook(file.getInputStream());
            }catch (Exception e){
                try {
                    wb = new HSSFWorkbook(file.getInputStream());
                }catch (Exception e1){
                    return -1;
                }
            }
            //开始解析
            Sheet sheet = wb.getSheetAt(0);     //读取sheet 0
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                rows++;
                Row row = sheet.getRow(i);// 每一行
                if(row==null){
                    break;
                }
                MemberCardBO memberCardBO = new MemberCardBO();
                memberCardBO.setCardNumber(this.analysisCardNumber(row.getCell(0)));
                BigDecimal bigDecimal = new BigDecimal(this.analysisMoney(row.getCell(1)));
                memberCardBO.setMoney(bigDecimal);
                memberCardBO.setMemberLevelId(Integer.parseInt(this.analysisMemberLevelId(row.getCell(2))));

                memberCardDAO.addMemberCardTest(memberCardBO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return rows;
        }
        return null;
    }
    //会员级别解析
    private String analysisMemberLevelId(Cell cell) throws Exception {
        cell.setCellType(CellType.STRING);
        String stage = cell.getStringCellValue().trim();
        List<Integer> list = new ArrayList<Integer>();
        String[] split = stage.split("\\)");
        for (String str : split) {
            String[] memberLevelIds = str.trim().split("\\(");
            for (String memberLevelId : memberLevelIds) {
                if (memberLevelId != null && memberLevelId.length() > 0) {
                    list.add(Integer.parseInt(memberLevelId));
                }
            }
        }
        return JSONArray.fromObject(list).toString();
    }

    //卡号解析
    private String analysisCardNumber(Cell cell) throws Exception {
        cell.setCellType(CellType.STRING);
        String stage = cell.getStringCellValue().trim();
        List<Integer> list = new ArrayList<Integer>();
        String[] split = stage.split("\\)");
        for (String str : split) {
            String[] cardNumbers = str.trim().split("\\(");
            for (String cardNumber : cardNumbers) {
                if (cardNumber != null && cardNumber.length() > 0) {
                    list.add(Integer.parseInt(cardNumber));
                }
            }
        }
        return JSONArray.fromObject(list).toString();
    }
    //售价解析
    private String analysisMoney(Cell cell) throws Exception {
        cell.setCellType(CellType.STRING);
        String stage = cell.getStringCellValue().trim();
        List<Integer> list = new ArrayList<Integer>();
        String[] split = stage.split("\\)");
        for (String str : split) {
            String[] moneys = str.trim().split("\\(");
            for (String money : moneys) {
                if (money != null && money.length() > 0) {
                    list.add(Integer.parseInt(money));
                }
            }
        }
        return JSONArray.fromObject(list).toString();
    }

}
