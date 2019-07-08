package com.szq.hotel.web.controller;
import com.szq.hotel.entity.bo.AdminBO;
import com.szq.hotel.entity.bo.MemberCardBO;
import com.szq.hotel.entity.bo.MemberCardResultBO;
import com.szq.hotel.entity.dto.ResultDTOBuilder;
import com.szq.hotel.query.QueryInfo;
import com.szq.hotel.service.MemberCardService;
import com.szq.hotel.util.ExcelUtil;
import com.szq.hotel.util.IntegerUtil;
import com.szq.hotel.util.JsonUtils;
import com.szq.hotel.util.StringUtils;
import com.szq.hotel.web.controller.base.BaseCotroller;
import org.apache.poi.hssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.tagext.PageData;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@RequestMapping("/memberCard")
public class MemberCardController extends BaseCotroller {
    private static final Logger log = LoggerFactory.getLogger(MemberCardController.class);


    @Resource
    private MemberCardService memberCardService;

    /*
        批量添加会员卡
    */
    @RequestMapping("/addMemberCard")
    public void  addMemberCard(Integer memberLevelId, String cardNumber, BigDecimal money,HttpServletRequest request, HttpServletResponse response){
        try{
            log.info(request.getRequestURI());
            log.info("param:{}", JsonUtils.getJsonString4JavaPOJO(request.getParameterMap()));
            AdminBO loginAdmin = super.getLoginAdmin(request);
            log.info("user{}",loginAdmin);
            if (loginAdmin == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }
            //验证参数
            if (memberLevelId==null ||StringUtils.isEmpty(cardNumber)|| money==null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }

            String[] value =cardNumber.split(",");

            List<String> list = new ArrayList<String>();
            for (int i = 0; i <value.length; i++)
            {
                list.add((value[i]));
            }



            boolean isRepeat = list.size() != new HashSet<String>(list).size();
            if (isRepeat){
                String result=JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response,result);
                log.info("result{}",result);
                return;
            }

            List<MemberCardBO> memberCardBOS = memberCardService.queryCartByCartList(list);
            if (!memberCardBOS.isEmpty()){
                String result=JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000007"));
                super.safeJsonPrint(response,result);
                log.info("result{}",result);
                return;
            }

            HashMap<String,Object> map =new HashMap<String, Object>();
            map.put("money",money);
            map.put("memberLevelId",memberLevelId);
            map.put("list",list);
            memberCardService.addMemberCard(map);

            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("批量添加会员卡成功！"));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;

        }catch (Exception e){
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000004"));
            super.safeJsonPrint(response, result);
            log.error("addMemberCardException",e);
        }
    }

    /**
     * 删除会员卡
     * @param id
     * @param request
     * @param response
     */
    @RequestMapping("/deleteMemberCard")
    public void deleteMemberCard(Integer id,HttpServletRequest request, HttpServletResponse response){
        try{
            log.info(request.getRequestURI());
            log.info("param:{}", JsonUtils.getJsonString4JavaPOJO(request.getParameterMap()));
            AdminBO loginAdmin = super.getLoginAdmin(request);
            if (loginAdmin == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }

            if(id==null){
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }

            memberCardService.deleteMemberCard(id);

            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("删除会员卡成功！"));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;

        }catch (Exception e){
            log.error("deleteMemberCardException",e);
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000003"));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;

        }
    }

    /**
     * 修改会员卡
     * @param memberCardBO
     * @param request
     * @param response
     */
    @RequestMapping("/updateMemberCard")
    public void updateMemberCard(MemberCardBO memberCardBO,HttpServletRequest request, HttpServletResponse response){
        try {
            log.info(request.getRequestURI());
            log.info("param:{}", JsonUtils.getJsonString4JavaPOJO(request.getParameterMap()));
            AdminBO loginAdmin = super.getLoginAdmin(request);
            log.info("user{}",loginAdmin);
            if (loginAdmin == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }
            if (memberCardBO.getId() == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }

            memberCardService.updateMemberCard(memberCardBO);
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("修改会员卡成功"));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;

        }catch (Exception e){
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, result);
            log.error("updateMemberCardException",e);
        }
    }

    /*
     * 条件分页查询会员卡
     */
    @RequestMapping("/selectMemberCard")
    public void selectMemberCard(String state,Integer memberLevelId,BigDecimal money,Integer cardNumber,Integer pageNo, Integer pageSize,HttpServletRequest request, HttpServletResponse response){
        try {
            log.info(request.getRequestURI());
            log.info("param:{}", JsonUtils.getJsonString4JavaPOJO(request.getParameterMap()));
            AdminBO loginAdmin = super.getLoginAdmin(request);
            log.info("user{}", loginAdmin);
            if (loginAdmin == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            Map<String,Object> map=new HashMap<String, Object>();
            map.put("state",state);
            map.put("memberLevelId",memberLevelId);
            map.put("money",money);
            map.put("cardNumber",cardNumber);

            QueryInfo queryInfo = getQueryInfo(pageNo,pageSize);
            if(queryInfo != null){
                map.put("pageSize",queryInfo.getPageSize());
                map.put("pageOffset",queryInfo.getPageOffset());
            }

            List<MemberCardBO> memberCardBOS = memberCardService.selectMemberCard(map);
            Integer count = memberCardService.getCount(map);
            Map<String,Object> resultMap=new HashMap<String, Object>();
            resultMap.put("memberCardBOS",memberCardBOS);
            resultMap.put("count",count);

            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(resultMap));
            super.safeJsonPrint(response, result);
            log.info("result{}",result);
            return;
        }catch (Exception e){
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, result);
            log.error("selectMemberCardException",e);
        }
    }

    /**
     * Excel导入会员卡
     */
    @RequestMapping("/importMemberCard")
    public void importMemberCard(@RequestParam(value="file",required = false)MultipartFile file, HttpServletRequest  request, HttpServletResponse  response) {
        try {
            log.info(request.getRequestURI());
            log.info("param:{}", JsonUtils.getJsonString4JavaPOJO(request.getParameterMap()));
            AdminBO loginAdmin = super.getLoginAdmin(request);
            log.info("user{}", loginAdmin);
            if (loginAdmin == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            //参数验证
            if (file == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                log.info("result{}",result);
                return;
            }

           String json = memberCardService.readExcelFile(file);
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(json));
            super.safeJsonPrint(response, result);

        } catch (Exception e) {
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, result);
            log.error("importMemberCardException", e);
        }
    }

    /**
     * 导出会员卡
     * @return
     */
    @RequestMapping("/exportMemberCard")
    public void download(String name,BigDecimal money,Integer cardNumber,String state,HttpServletResponse response, HttpServletRequest request){
        try {
            log.info(request.getRequestURI());
            log.info("param:{}", JsonUtils.getJsonString4JavaPOJO(request.getParameterMap()));
            AdminBO loginAdmin = super.getLoginAdmin(request);
            log.info("user{}", loginAdmin);
            if (loginAdmin == null) {
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002"));
                super.safeJsonPrint(response, result);
                log.info("result{}", result);
                return;
            }
            ServletOutputStream out=response.getOutputStream();
            try {
                SimpleDateFormat   formatter   =   new   SimpleDateFormat   ("yyyyMMddHHmmss");
                Date   curDate   =   new   Date(System.currentTimeMillis());//获取当前时间
                String   str   =   formatter.format(curDate);
                response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode("会员卡信息" +str+ ".xls", "UTF-8"));
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }

            String[] titles = { "卡号", "价格", "会员卡级别id", "卡状态" ,"售出时间"};
            export(titles, out,state,name,money,cardNumber);
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(""));
            safeTextPrint(response, json);
        } catch (Exception e) {
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, result);
            log.error("exportMemberCardException", e);
        }
    }


    public void export(String[] titles, ServletOutputStream out,String state,String name,BigDecimal money,Integer cardNumber) throws Exception{

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

            List<MemberCardResultBO> list = memberCardService.exportMemberCard(map);

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



}




