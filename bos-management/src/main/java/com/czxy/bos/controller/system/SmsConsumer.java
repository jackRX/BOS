package com.czxy.bos.controller.system;

import com.czxy.bos.config.DataConfig;
import com.czxy.bos.service.LoginLogService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.MapMessage;
import javax.jms.Message;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName SmsConsumer
 * @Author 宋明明
 * @Date 2018/9/17 08:39
 * Version 1.0
 **/
@Component
public class SmsConsumer {

    @Resource
    private LoginLogService loginLogService;
    @Resource
    private DataConfig dataConfig;

    @JmsListener(destination = "java.bos.sms")
    public void recive(Message message){
        try {
            final MapMessage mapMessage = (MapMessage) message;
            final String userid = mapMessage.getString("userid");
            final String logintime = mapMessage.getString("logintime");
            final int id = Integer.parseInt(userid);
            final String data = dataConfig.formatDate(logintime);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            final Date parse = sdf.parse(data);
            loginLogService.insertLogin(id,parse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
