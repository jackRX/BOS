package com.czxy.bos.controller;

import com.czxy.bos.domain.base.Standard;
import com.czxy.bos.service.StandardService;
import com.czxy.bos.vo.DataGridResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName StandardController
 * @Author
 * @Date 2018/9/4 08:42
 * Version 1.0
 **/
@RestController
@RequestMapping("/standard")
public class StandardController {
    @Resource
    private StandardService standardService;


    @PostMapping
    public ResponseEntity<Void> addStandard(Standard standard){
        try {
            standardService.addStandard(standard);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<DataGridResult> quaryStandardByPage(Integer page,Integer rows){
        try {
            final DataGridResult gridResult = standardService.quaryStandardByPage(page, rows);
            return new ResponseEntity<>(gridResult,HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Standard>> findAllStandard(){
        try {
            final List<Standard> gridResult = standardService.findAllStandard();
            return new ResponseEntity<>(gridResult,HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<Void> updateStandard(Standard standard){
        try {
            standardService.updateStandard(standard);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStandard(@PathVariable("id")  String id){
        try {
            standardService.deleteStandard(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
