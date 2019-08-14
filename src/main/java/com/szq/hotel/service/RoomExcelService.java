package com.szq.hotel.service;

import com.szq.hotel.dao.RoomDAO;
import com.szq.hotel.entity.bo.ExportMemberResultBO;
import com.szq.hotel.entity.bo.RoomExportBO;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: Wang bin
 * @date: Created in 14:52 2019/8/12
 */
@Service
public class RoomExcelService {

    @Resource
    private RoomDAO roomDAO;
    
    public void export(String[] titles, ServletOutputStream out, Integer hotelId) throws Exception{

        try{
            // 第一步，创建一个workbook，对应一个Excel文件
            HSSFWorkbook workbook = new HSSFWorkbook();

            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
            HSSFSheet hssfSheet = workbook.createSheet("客房报表");

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

            List<RoomExportBO> list = this.exportRoom(hotelId);
            System.err.println(list);
            if(list==null){
                return;
            }

            for (int i = 0; i < list.size(); i++) {
                row = hssfSheet.createRow(i+1);
                //  Vehicle vehicle = list.get(i);
                RoomExportBO roomExportBO = list.get(i);
                // 第六步，创建单元格，并设置值

                String  cartNumber = "";
                if(roomExportBO.getRoomName()!= null){
                    cartNumber= roomExportBO.getRoomName();
                }
                row.createCell(0).setCellValue(cartNumber);


                String hotelName= roomExportBO.getHotelName();

                row.createCell(1).setCellValue(hotelName);


                String name = "";
                if( roomExportBO.getFloorName() != null){
                    name= roomExportBO.getFloorName();
                }
                row.createCell(2).setCellValue(name);

                String memberLevelName = "";
                if( roomExportBO.getRoomTypeName() != null){
                    memberLevelName = roomExportBO.getRoomTypeName();
                }
                row.createCell(3).setCellValue(memberLevelName);

                String birthday = "";
                if( roomExportBO.getRoomMajorState() != null){
                    birthday=roomExportBO.getRoomMajorState();

                }
                row.createCell(4).setCellValue(birthday);

                String phone = "";
                if( roomExportBO.getRoomState() != null){
                    phone= roomExportBO.getRoomState();
                }
                row.createCell(5).setCellValue(phone);


                String memberDiscount = "";
                if( roomExportBO.getLockRoomState() != null){
                    memberDiscount= roomExportBO.getLockRoomState();
                }
                row.createCell(6).setCellValue(memberDiscount);


                String sumStoreValue ="";
                if (roomExportBO.getLockRoomStartTime()!=null) {
                    sumStoreValue = roomExportBO.getLockRoomStartTime();
                }
                row.createCell(7).setCellValue(sumStoreValue);


                String storeValueBalance = "";
                if(roomExportBO.getLockRoomEndTime() != null){
                    storeValueBalance= roomExportBO.getLockRoomEndTime();
                }
                row.createCell(8).setCellValue(storeValueBalance);

                String a = "";
                if(roomExportBO.getRemark() != null){
                    a= roomExportBO.getRemark();
                }
                row.createCell(9).setCellValue(a);

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

    private List<RoomExportBO> exportRoom(Integer hotelId) {
        return roomDAO.roomExcel(hotelId);
    }
}
