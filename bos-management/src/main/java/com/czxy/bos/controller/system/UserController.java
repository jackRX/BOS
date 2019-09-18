package com.czxy.bos.controller.system;

import com.czxy.bos.HighChart;
import com.czxy.bos.service.LoginLogService;
import com.czxy.bos.system.OnlineInfo;
import com.czxy.bos.system.User;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.jms.MapMessage;
import javax.jms.Queue;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * @ClassName UserMapper
 * @Author 宋明明
 * @Date 2018/10/9 08:05
 * Version 1.0
 **/
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private HttpSession session;

    @Resource
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Resource
    private LoginLogService loginLogService;

    @Resource
    private Queue queue;

    @GetMapping("/login")
    public ResponseEntity<Void> login(User user){
        try {
            System.out.println(user);
            //1 通过shiro进行用户登录
            SecurityUtils.getSubject().login(new UsernamePasswordToken(user.getUsername(), user.getPassword()));
            // 登录成功
            // 将用户信息 保存到 Session
            User loginUser = (User) SecurityUtils.getSubject().getPrincipal();

           /* //将登陆信息放入MQ中
            final Date date = new Date();
            final String time = date.toString();
            final MapMessage mapMessage = new ActiveMQMapMessage();
            mapMessage.setString("userid",loginUser.getId()+"");
            mapMessage.setString("logintime",time);
            this.jmsMessagingTemplate.convertAndSend(this.queue,mapMessage);*/
            session.setAttribute("user", loginUser);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (IncorrectCredentialsException e) {
            // 登录失败
            return new ResponseEntity<Void>( HttpStatus.INTERNAL_SERVER_ERROR);
        } catch(Exception e){
            return new ResponseEntity<Void>( HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @PostMapping("/log")
    public List<HighChart> insertLogin(){
        List<HighChart> list= loginLogService.queryOnline();
       return list;
    }


    @GetMapping("/logout")
    public ResponseEntity<Void> logout(){
        //移除
        session.removeAttribute("user");
        //注销
        SecurityUtils.getSubject().logout();
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
