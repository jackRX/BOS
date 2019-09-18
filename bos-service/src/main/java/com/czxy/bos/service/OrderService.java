package com.czxy.bos.service;

import com.czxy.bos.dao.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName OrderService
 * @Author 宋明明
 * @Date 2018/9/25 10:53
 * Version 1.0
 **/
@Service
@Transactional
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;
}
