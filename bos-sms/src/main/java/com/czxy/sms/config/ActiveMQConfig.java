package com.czxy.sms.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Queue;

/**
 * @ClassName ActiveMQConfig
 * @Author 宋明明
 * @Date 2018/9/17 08:36
 * Version 1.0
 **/
@Configuration
public class ActiveMQConfig {

    @Bean
    public Queue CreatQueue(){
        return new ActiveMQQueue("java.bos.sms");
    }
}
