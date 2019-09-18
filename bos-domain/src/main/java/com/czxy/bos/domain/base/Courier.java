package com.czxy.bos.domain.base;

import javax.persistence.*;

/**
 * @ClassName Courier
 * @Author
 * @Date 2018/9/4 11:24
 * Version 1.0
 **/
@Table(name = "t_courier")
public class Courier {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) //对应主键自增长
    @Column(name = "ID")
    private Integer id;
    @Column(name = "check_pwd")
    private String checkPwd;// 查台密码
    @Column(name = "company")
    private String company;//公司
    @Column(name = "courier_num")
    private String courierNum;// 员工号
    @Column(name = "deltag")
    private char deltag;// 是否删除
    @Column(name = "name")
    private String name;//姓名
    @Column(name = "pda")
    private String pda;//查台账号
    @Column(name = "telephone")
    private String telephone;//手机号码
    @Column(name = "type")
    private String type;//快递员类型：小件员  中件员  大件员
    @Column(name = "vehicle_num")
    private String vehicleNum;//车牌
    @Column(name = "vehicle_type")
    private String vehicleType;//车辆信息  卡车  小轿车  自行车  三轮车
    @Column(name = "standard_id")
    private Integer standardId;// 外键，取派标准表
    @Temporal(TemporalType.TIMESTAMP)
    private Standard standard;
    @Column(name = "taketime_id")

    private Integer taketimeId;// 工作时间段

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCheckPwd() {
        return checkPwd;
    }

    public void setCheckPwd(String checkPwd) {
        this.checkPwd = checkPwd;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCourierNum() {
        return courierNum;
    }

    public void setCourierNum(String courierNum) {
        this.courierNum = courierNum;
    }

    public char getDeltag() {
        return deltag;
    }

    public void setDeltag(char deltag) {
        this.deltag = deltag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPda() {
        return pda;
    }

    public void setPda(String pda) {
        this.pda = pda;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVehicleNum() {
        return vehicleNum;
    }

    public void setVehicleNum(String vehicleNum) {
        this.vehicleNum = vehicleNum;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Integer getStandardId() {
        return standardId;
    }

    public void setStandardId(Integer standardId) {
        this.standardId = standardId;
    }

    public Standard getStandard() {
        return standard;
    }

    public void setStandard(Standard standard) {
        this.standard = standard;
    }

    public Integer getTaketimeId() {
        return taketimeId;
    }

    public void setTaketimeId(Integer taketimeId) {
        this.taketimeId = taketimeId;
    }


    public void setInfo(String s) {
    }
}
