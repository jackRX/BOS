package com.czxy.fore.controller;

import com.czxy.bos.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @ClassName SalesController
 * @Author 宋明明
 * @Date 2018/9/21 09:43
 * Version 1.0
 **/
@RestController
@RequestMapping("/sales")
public class SalesController {
    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/findPromotionByPage")
    private ResponseEntity<String> queryPromotionByPage(Integer page,Integer rows){
        final String host = Constants.BOS_MANAGEMENT_HOST+"/promotion?page="+page+"&rows="+rows;
        final ResponseEntity<String> entity = restTemplate.getForEntity(host, String.class);
        final HttpStatus statusCode = entity.getStatusCode();
        final String body = entity.getBody();
        return new ResponseEntity<>(body,statusCode);
    }
}
