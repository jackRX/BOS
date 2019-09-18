package com.czxy.bos.controller.loginlog;


import com.czxy.bos.service.loginlog.LoginlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.czxy.bos.system.LoginLog;

import javax.annotation.Resource;

@RestController
@RequestMapping("/loginlog")
public class LoginlogController {

    @Resource
    private LoginlogService loginlogService;

//    @GetMapping
//    public ResponseEntity<DataGridBean>  findAll(Integer  page, Integer  rows){
//        System.out.println("----------------------");
//        try {
//            PageInfo<Loginlog> info = loginlogService.findAll(page,rows);
//            return   new ResponseEntity<>(new DataGridBean(info.getTotal(),info.getList()), HttpStatus.OK);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return     new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
