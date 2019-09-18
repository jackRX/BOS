package com.czxy.bos.system;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @ClassName OnlineInfo
 * @Author 宋明明
 * @Date 2018/10/19 08:26
 * Version 1.0
 **/
@Entity
@Table(name = "online_info_t")
public class OnlineInfo {
    private char a1;

    public char getA1() {
        return a1;
    }

    public void setA1(char a1) {
        this.a1 = a1;
    }
}
