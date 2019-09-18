package com.czxy.bos.controller;

import com.czxy.bos.domain.base.Courier;
import com.czxy.bos.service.CourierService;
import com.czxy.bos.vo.DataGridResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName CourierController
 * @Author
 * @Date 2018/9/4 11:36
 * Version 1.0
 **/
@RestController
@RequestMapping("/courier")
public class CourierController {

    @Resource
    private CourierService courierService;

    @PostMapping
    public ResponseEntity<Void> addCourier(Courier courier){
        try {
            courierService.addCourier(courier);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<DataGridResult> findCourierByPage(Integer page,Integer rows,Courier courier){
        try {
            final DataGridResult courierByPage = courierService.findCourierByPage(page, rows,courier);
            return new ResponseEntity<>(courierByPage,HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findNoAssociationCouriers")
    public ResponseEntity<List<Courier>> findNoAssociationCouriers(){
        // 调用业务层，查询未关联定区的快递员
        List<Courier> result = courierService.findNoAssociationCouriers();

        return new ResponseEntity<List<Courier>>(result , HttpStatus.OK);
    }
}
