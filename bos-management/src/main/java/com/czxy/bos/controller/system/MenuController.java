package com.czxy.bos.controller.system;

import com.czxy.bos.service.MenuService;
import com.czxy.bos.system.Menu;
import com.czxy.bos.vo.DataGridResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName MenuController
 * @Author 宋明明
 * @Date 2018/10/12 08:24
 * Version 1.0
 **/
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private MenuService menuService;

    @GetMapping
    public ResponseEntity<DataGridResult> queryMenuByPage(Integer page,Integer rows){
        try {
            final DataGridResult result = menuService.queryMenuByPage(page, rows);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Void> addMenu(Menu menu){
        menuService.save(menu);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
