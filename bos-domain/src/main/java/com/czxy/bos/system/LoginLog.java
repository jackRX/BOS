package com.czxy.bos.system;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName LoginLog
 * @Author 宋明明
 * @Date 2018/10/19 08:15
 * Version 1.0
 **/
@Entity
@Table(name = "t_login_log")
public class LoginLog {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "userid")
    private int userid;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date logintime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public Date getLogintime() {
        return logintime;
    }

    public void setLogintime(Date logintime) {
        this.logintime = logintime;
    }
}
