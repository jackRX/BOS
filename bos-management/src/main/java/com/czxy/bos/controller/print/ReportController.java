package com.czxy.bos.controller.print;

import com.czxy.bos.service.WayBillService;
import com.czxy.bos.take_delivery.WayBill;
import com.czxy.bos.utils.DownloadUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @ClassName ReportController
 * @Author 宋明明
 * @Date 2018/10/12 20:10
 * Version 1.0
 **/
@RestController
@RequestMapping("/report")
public class ReportController {

    @Resource
    private WayBillService wayBillService;

    @GetMapping("/exportXls")
    public void exportXls(HttpServletResponse response) throws IOException {
        List<WayBill> wayBillList=wayBillService.findAllWayBull();
        //1 创建工作簿
        final XSSFWorkbook wb = new XSSFWorkbook();
        //2 创建工作表
        final XSSFSheet sheet = wb.createSheet();

        // 设置列宽---1/256 一个字符的宽度
        sheet.setColumnWidth(0,15*256);
        sheet.setColumnWidth(1,15*256);
        sheet.setColumnWidth(2,15*256);

        sheet.setColumnWidth(3,25*256);
        sheet.setColumnWidth(4,25*256);
        sheet.setColumnWidth(5,25*256);
        sheet.setColumnWidth(6,25*256);
        sheet.setColumnWidth(7,25*256);
        sheet.setColumnWidth(8,25*256);

        int rowNo=0,cellNo=0;
        Row nRow=null;
        Cell nCell=null;
        //3 创建行
        nRow= sheet.createRow(rowNo);
        //4 创建单元格
       nCell = nRow.createCell(cellNo);
        //5 设置内容
        nCell.setCellValue("bos项目运单表统计"+new Date().toLocaleString());
        //6 设置内容格式
        sheet.addMergedRegion(new CellRangeAddress(0,0,(short)0,(short)9));
        //大标题设置
        final CellStyle bigTitleStyle = bigTitleStyle(wb);
        nCell.setCellStyle(bigTitleStyle);
        //小标题打印
        String[] titles={"编号id","运单编号","订单编号","寄件人姓名","寄件人电话","寄件人地址","收件人姓名","收件人电话","收件人地址"};
        rowNo++;
        nRow=sheet.createRow(rowNo);
        for (String title : titles) {
            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(title);
            nCell.setCellStyle(titleStyle(wb));
        }
        /******内容打印*******/
        rowNo++;
        for (WayBill wayBill : wayBillList) {
            cellNo=0;
            nRow= sheet.createRow(rowNo++);
            //4 创建单元格
            //id
            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(wayBill.getId());
            nCell.setCellStyle(contentStyle(wb));
            //wayBillNum
            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(wayBill.getWayBillNum());
            nCell.setCellStyle(contentStyle(wb));
            //orderid
            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(wayBill.getOrderId());
            nCell.setCellStyle(contentStyle(wb));
            //寄件人姓名
            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(wayBill.getSendName());
            nCell.setCellStyle(contentStyle(wb));
            //寄件人电话
            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(wayBill.getSendMobile());
            nCell.setCellStyle(contentStyle(wb));
            //寄件人地址
            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(wayBill.getSendAddress());
            nCell.setCellStyle(contentStyle(wb));
            //收件人姓名
            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(wayBill.getRecName());
            nCell.setCellStyle(contentStyle(wb));
            //收件人电话
            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(wayBill.getRecMobile());
            nCell.setCellStyle(contentStyle(wb));
            //收件人地址
            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(wayBill.getRecAddress());
            nCell.setCellStyle(contentStyle(wb));

        }

        //7 下载
        final DownloadUtil downloadUtil = new DownloadUtil();
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        wb.write(byteArrayOutputStream);
        downloadUtil.download(byteArrayOutputStream,response,"运单表.xlsx");
    }


    public CellStyle bigTitleStyle(Workbook wb){
        final CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        final Font font = wb.createFont();
        font.setFontHeight((short)480);
        font.setBold(true);
        font.setColor(Font.COLOR_RED);
        cellStyle.setFont(font);
        return cellStyle;
    }

    public CellStyle titleStyle(Workbook wb){
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);

        Font font = wb.createFont();
        font.setFontHeight((short)300);

        cellStyle.setFont(font);
        return cellStyle;
    }

    public CellStyle contentStyle(Workbook wb){
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);

        Font font = wb.createFont();
        font.setFontHeight((short)200);

        cellStyle.setFont(font);
        return cellStyle;
    }
}
