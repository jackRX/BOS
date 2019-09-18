package com.czxy.sms.consumer;

//import com.czxy.bos.service.LoginLogService;
import com.czxy.sms.config.DataConfig;
import com.czxy.sms.util.SmsUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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

//    @JmsListener(destination = "java1.bos.sms")
//    public void recive(Message message){
//        try {
//            final MapMessage mapMessage = (MapMessage) message;
//            final String telephone = mapMessage.getString("telephone");
//            final String code = mapMessage.getString("code");
//            System.out.println(telephone+"---"+code);
//            // 发送短信，调用SMSutil
//            // SmsUtil.sendSms(telephone,code);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    @Resource
//    private LoginLogService loginLogService;
    @Resource
   private DataConfig dataConfig;
    @Resource
    private RestTemplate restTemplate;

    @JmsListener(destination = "java.bos.sms")
    public void recive(Message message){
        try {
//            final MapMessage mapMessage = (MapMessage) message;
//            final String userid = mapMessage.getString("userid");
//            final String logintime = mapMessage.getString("logintime");
//            final int id = Integer.parseInt(userid);
//            final String data = dataConfig.formatDate(logintime);
//            System.out.println(data+"   "+id+"  "+userid);
//            String url="";
//            final ResponseEntity<Void> entity = restTemplate.postForEntity(url, customer, Void.class);
//            loginLogService.insertLogin(userid,logintime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
