package com.czxy.fore.controller;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.exceptions.ClientException;
import com.czxy.bos.utils.Constants;
import com.czxy.bos.utils.GetRandomCodeUtil;
import com.czxy.bos.utils.MailUtil;
import com.czxy.bos.utils.SmsUtil;
import com.czxy.crm.domain.Customer;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.jms.MapMessage;
import javax.jms.Queue;
import javax.servlet.http.HttpSession;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName RegistController
 * @Author
 * @Date 2018/9/13 16:22
 * Version 1.0
 **/
@RestController
@RequestMapping("/regist")
public class RegistController {
        @Resource
        private HttpSession session;

        @Resource
        private RestTemplate restTemplate;

        @Resource
        private RedisTemplate<String,String> redisTemplate;

        @Resource
        private JmsMessagingTemplate jmsMessagingTemplate;

        @Resource
        private Queue queue;

    @GetMapping("/sendSms")
    public ResponseEntity<Void> sendSms(String telephone){
        //通过GetRandomCodeUtil生成随机验证码
//        final String code = GetRandomCodeUtil.getCode();
        final String code = "123456";
        //将code保存寄来,便于后期比较
        session.setAttribute(telephone,code);
        try {
            SmsUtil.sendSms(telephone,code);
        } catch (ClientException clientException) {
            clientException.printStackTrace();
        }

             try {
                 final MapMessage mapMessage = new ActiveMQMapMessage();
                 mapMessage.setString("telephone",telephone);
                 mapMessage.setString("code",code);

                 this.jmsMessagingTemplate.convertAndSend(this.queue,mapMessage);
             } catch (Exception e) {
                        e.printStackTrace();
                    }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> regist(String checkcode, Customer customer){
        final String code = session.getAttribute(customer.getTelephone()).toString();
        if (code!=null&&!"".equals(code.trim())){
            if (checkcode.equals(code)){
              String url=  Constants.CRM_MANAGEMENT_HOST+"/customer/saveCustomer";
                final ResponseEntity<String> entity = restTemplate.postForEntity(url, customer, String.class);
                final String adtivecode = UUID.randomUUID().toString().replace("-", "");
                redisTemplate.opsForValue().set(customer.getTelephone(),adtivecode,24,TimeUnit.HOURS);

              String activeUrl= Constants.FORE_MANAGEMENT_HOST + "/regist/activeMail?telephone="+customer.getTelephone()+"&activeCode="+adtivecode;
                String content="<a href='"+activeUrl+"'>速运快递账号激活</a>";
                try{
                    MailUtil.sendMail(customer.getEmail(),"世纪佳缘网账号激活",content);
                }catch (Exception e){
                    e.printStackTrace();
                }
                HttpStatus statusCode = entity.getStatusCode();
                return new ResponseEntity<>(statusCode);

            }
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @GetMapping("/activeMail")
    public ResponseEntity<Void> activeMail(String telephone,String activeCode){
        // 1 根据手机号到redis中查找code，
        String redisCode = redisTemplate.opsForValue().get(telephone);
        // 1.1 找不到，则直接返回无法验证
        if(redisCode==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        // 1.2 找到了
        if(redisCode.equals(activeCode)){
            // 删除激活码
            redisTemplate.delete(telephone);

            // 需要激活用户
            // 2 根据telephone查找用户信息
            String url = Constants.CRM_MANAGEMENT_HOST +"/customer/findCustomerByTelephone?telephone="+telephone;
            ResponseEntity<String> entity = restTemplate.getForEntity(url, String.class);
            String body = entity.getBody();
            System.out.println(body);
            // json格式的字符串，放的是customer的信息，这个串能否转成Customer对象？
            // 答：fastjson？阿里巴巴出品
            // 第一个参数：json串
            // 第二个参数：要转成的类型
            Customer customer = JSON.parseObject(body, Customer.class);
            if(customer.getType()!=null&&customer.getType()==1){
                System.out.println("已经激活，无需激活");
                return new ResponseEntity<>(HttpStatus.OK);
            }
            // 需要激活--修改customer的数据
            // 2.1 查到，判断用户是否已经激活，已激活不需要重复激活
            // 2.2 没查找到用户，直接返回无法验证
            // 3 未激活，进行激活操作，并且返回结果
            String updateUrl = Constants.CRM_MANAGEMENT_HOST + "/customer/updateType?telephone="+telephone;
            ResponseEntity<String> updateEntity = restTemplate.getForEntity(updateUrl,String.class);

            return new ResponseEntity<>(HttpStatus.OK);
        }
        return null;
    }

}
