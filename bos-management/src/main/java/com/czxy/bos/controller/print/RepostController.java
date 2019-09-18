package com.czxy.bos.controller.print;

import com.czxy.bos.service.WayBillService;
import com.czxy.bos.take_delivery.WayBill;
import com.itextpdf.awt.AsianFontMapper;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * @ClassName RepostController
 * @Author 宋明明
 * @Date 2018/10/12 21:51
 * Version 1.0
 **/
@RestController
@RequestMapping("/pdf")
public class RepostController {

    @Resource
    private WayBillService wayBillService;

    @GetMapping("/exportPdf")
    public void exportPdf(HttpServletResponse response) throws Exception{
        final List<WayBill> wayBillList = wayBillService.findAllWayBull();
        final Document document = new Document();

        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        PdfWriter.getInstance(document,byteArrayOutputStream);

        document.open();
//       Font titleFont= BaseFont.createFont(AsianFontMapper.ChineseSimplifiedFont,AsianFontMapper.ChineseSimplifiedEncoding_H,false);


        document.close();
    }
}
