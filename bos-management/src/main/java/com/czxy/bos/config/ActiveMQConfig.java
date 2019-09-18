package com.czxy.bos.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Queue;
import javax.jms.Topic;

/**
 * @ClassName ActiveMQConfig
 * @Author 宋明明
 * @Date 2018/9/17 08:13
 * Version 1.0
 **/
@Configuration
public class ActiveMQConfig {

    @Bean
    public Queue CreatQueue(){
        return new ActiveMQQueue("java.bos.sms");
    }

    @Bean
    public Topic topic() {
        return new ActiveMQTopic("java.bos.topic");
    }
}
