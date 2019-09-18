package com.czxy.bos.controller;

import com.czxy.bos.domain.base.FixedArea;
import com.czxy.bos.service.FixedAeraService;
import com.czxy.bos.utils.Constants;
import com.czxy.bos.vo.DataGridResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @ClassName FixedAeraController
 * @Author
 * @Date 2018/9/6 18:22
 * Version 1.0
 **/
@RestController
@RequestMapping("/fixeArea")
public class FixedAeraController {
    @Resource
    private FixedAeraService fixedAeraService;

    @GetMapping
    public ResponseEntity<DataGridResult> findFixeAreaByPage(Integer page, Integer rows, FixedArea fixedArea){
        try {
            final DataGridResult fixAreaByPage = fixedAeraService.findFixAreaByPage(page, rows, fixedArea);
            return new ResponseEntity<>(fixAreaByPage,HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping
    public ResponseEntity<Void> addFIxedArea(FixedArea fixedArea){
        try {
            fixedAeraService.addFixedArea(fixedArea);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/findNoAssociationCustomers")
    public ResponseEntity<String> findNoAssociationCustomers(){
        final String url = Constants.CRM_MANAGEMENT_HOST+"/customer/noAssociationCustomers";
        final ResponseEntity<String> entity = restTemplate.getForEntity(url, String.class);
        final HttpStatus statusCode = entity.getStatusCode();
        final String body = entity.getBody();
        return new ResponseEntity<>(body,statusCode);
    }


    @GetMapping("/associationFixedAreaCustomers")
    public ResponseEntity<String> findHasAssociationFixedAreaCustomers(@RequestParam("id") String fixedAreaId){
        final String url = Constants.CRM_MANAGEMENT_HOST+"/customer/associationFixedAreaCustomers?fixedAreaId="+fixedAreaId;

        final ResponseEntity<String> entity = restTemplate.getForEntity(url, String.class);
        final HttpStatus statusCode = entity.getStatusCode();
        final String body = entity.getBody();
        return new ResponseEntity<>(body,statusCode);
    }


    @GetMapping("/associationCustomersToFixedArea")
    public ResponseEntity<String> associationCustomersToFixedArea(@RequestParam("customerIds") String customerIdStr,@RequestParam("fixedAreaId") String fixedAreaId){
        final String url = Constants.CRM_MANAGEMENT_HOST+"/customer/associationCustomersToFixedarea?customerIdStr="+customerIdStr+"&fixedAreaId="+fixedAreaId;
        final ResponseEntity<String> entity = restTemplate.getForEntity(url, String.class);
        final HttpStatus statusCode = entity.getStatusCode();
        final String body = entity.getBody();
        return new ResponseEntity<>(body,statusCode);
    }

    @GetMapping("/assocationCourier")
    public ResponseEntity<Void> assocationCourier(String fixedAreaId, Integer courierId, Integer takeTimeId){
        try {
            fixedAeraService.assocationCourier(fixedAreaId, courierId, takeTimeId);

            return new ResponseEntity<Void>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
