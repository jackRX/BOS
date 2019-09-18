package com.czxy.bos.controller.take_delivery;

import com.czxy.bos.service.OrderService;
import com.czxy.bos.take_delivery.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName OrderController
 * @Author 宋明明
 * @Date 2018/9/25 10:52
 * Version 1.0
 **/
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping("/findByOrderNum")
    public ResponseEntity<Order> findByOrderNum(){
        return null;
    }
}
