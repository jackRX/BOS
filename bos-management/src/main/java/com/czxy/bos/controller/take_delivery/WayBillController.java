package com.czxy.bos.controller.take_delivery;

import com.czxy.bos.service.WayBillService;
import com.czxy.bos.take_delivery.WayBill;
import com.czxy.bos.vo.DataGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName WayBillController
 * @Author 宋明明
 * @Date 2018/9/25 10:50
 * Version 1.0
 **/
@RestController
@RequestMapping("/wayBill")
public class WayBillController {

    @Autowired
    private WayBillService wayBillService;

    @GetMapping
    public ResponseEntity<DataGridResult> findWayBillByPage(Integer page,Integer rows){
        try {
            final DataGridResult pageInfo= wayBillService.findWayBillByPage(page,rows);
            if (pageInfo!=null){
                return new ResponseEntity<>(pageInfo,HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveWayBill(WayBill wayBill){
        try {
            wayBillService.saveWayBill(wayBill);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/pageQuery")
    public ResponseEntity<DataGridResult> pageQuery(WayBill wayBill,Integer page,Integer rows){
               DataGridResult result=  wayBillService.pageQuery(wayBill,page,rows);
               return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
