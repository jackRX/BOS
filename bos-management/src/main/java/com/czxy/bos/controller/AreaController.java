package com.czxy.bos.controller;

import com.czxy.bos.domain.base.Area;
import com.czxy.bos.service.AreaService;
import com.czxy.bos.utils.DownloadUtil;
import com.czxy.bos.utils.PinYin4jUtils;
import com.czxy.bos.vo.DataGridResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName AreaController
 * @Author
 * @Date 2018/9/6 11:03
 * Version 1.0
 **/
@RestController
@RequestMapping("/area")
public class AreaController {

    @Resource
    private AreaService areaService;


    @GetMapping
    public ResponseEntity<DataGridResult>  findAllArea(Integer page,Integer rows,Area area){
        try {
            final DataGridResult area1 = areaService.findAllArea(page, rows,area);
            return new ResponseEntity<>(area1, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping
    @RequestMapping("/batchImport")
    public ResponseEntity<Void> batchImport(@RequestParam("myFile") MultipartFile file){
        try {
            final InputStream is = file.getInputStream();
            //创建workbook
            final Workbook wb = new HSSFWorkbook(is);
            //获取sheet
            final Sheet sheet = wb.getSheetAt(0);
            //定义list集合存放数据，方便最后一次性保存
            final ArrayList<Area> list = new ArrayList<>();
            //获取row
            for (Row row : sheet) {
                //获取单元格数据
                //跳过第一行
                if (row.getRowNum() == 0) {
                    continue;
                }
                //如果第一列为空，整条数据都不读取
                if (row.getCell(0) == null || StringUtils.isBlank(row.getCell(0).getStringCellValue())) {
                    continue;
                }
                //设置Area的值
                final Area area = new Area();
                // 5.1 区域编码id
                area.setId(row.getCell(0).getStringCellValue());
                // 5.2 省份
                area.setProvince(row.getCell(1).getStringCellValue());
                // 5.3 城市
                area.setCity(row.getCell(2).getStringCellValue());
                // 5.4 区域
                area.setDistrict(row.getCell(3).getStringCellValue());
                // 5.5 邮编
                area.setPostcode(row.getCell(4).getStringCellValue());

                String cityName = area.getProvince().substring(0, area.getProvince().length() - 1) +
                        area.getCity().substring(0, area.getCity().length() - 1) +
                        area.getDistrict().substring(0, area.getDistrict().length() - 1);
                String cityCode = PinYin4jUtils.hanziToPinyin(cityName, "");
                //5.7 城市简码
                String shortCode = PinYin4jUtils.stringArrayToString(PinYin4jUtils.getHeadByString(cityName));
                area.setCitycode(cityCode);
                area.setShortcode(shortCode);

                list.add(area);
            }
            //保存到数据库
                areaService.saveAreas(list);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/batchExport")
    public ResponseEntity<Void> batchExport(HttpServletResponse response){
        try {

            //1 准备导出的Workbook--创建Workbook
            Workbook wb = new HSSFWorkbook();
            //2 创建sheet
            Sheet sheet = wb.createSheet();

            //3 设置标题
            // 创建标题
            // 定义公共变量
            Row nRow = null;
            Cell nCell = null;
            int rowNo = 0;// 行号
            int cellNo = 0;// 列号

            String[] titles = {"区域编码","省份","城市","地区","邮编","城市编码","城市简码"};
            /*****************将标题添加至workbook中********************/
            // 创建标题行
            nRow = sheet.createRow(rowNo);
            for(String title:titles){
                nCell = nRow.createCell(cellNo++);
                nCell.setCellValue(title);
            }

            //4 遍历数据列表，创建row---? 多少条数据，就需要创建多少条row--循环----》条件：数据库中所有的数据
            //4.1  查出t_area表中的所有数据
            List<Area> list = areaService.findAllAreas();
            //4.2 遍历数据
            for(Area area:list){
                // 行号++
                rowNo++;
                // 列号要变化吗？
                cellNo = 0;
                // 创建行
                nRow = sheet.createRow(rowNo);

                // 创建列
                //"区域编码",
                nCell = nRow.createCell(cellNo++);
                nCell.setCellValue(area.getId());
                //"省份","
                nCell = nRow.createCell(cellNo++);
                nCell.setCellValue(area.getProvince());
                //城市",
                nCell = nRow.createCell(cellNo++);
                nCell.setCellValue(area.getCity());
                //"地区",
                nCell = nRow.createCell(cellNo++);
                nCell.setCellValue(area.getDistrict());
                //"邮编","
                nCell = nRow.createCell(cellNo++);
                nCell.setCellValue(area.getPostcode());
                //城市编码",
                nCell = nRow.createCell(cellNo++);
                nCell.setCellValue(area.getCitycode());
                //"城市简码"
                nCell = nRow.createCell(cellNo++);
                nCell.setCellValue(area.getShortcode());

            }

            //5 下载
            DownloadUtil down = new DownloadUtil();
            //5.1 准备流
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            wb.write(byteArrayOutputStream);

            /**
             * 参数一：ByteArrayOutputStream byteArrayOutputStream, 下载文件的流
             * 参数二：HttpServletResponse response：response对象
             * 参数三：String returnName：下载的文件名字
             */
            down.download(byteArrayOutputStream,response,"地区.xls");


        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
