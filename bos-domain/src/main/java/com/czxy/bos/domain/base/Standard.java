package com.czxy.bos.domain.base;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName Standard
 * @Author
 * @Date 2018/9/4 08:11
 * Version 1.0
 **/
@Table(name = "t_standard")
public class Standard {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) //对应主键自增长
    @Column(name = "ID")
    private Integer id; // 主键
    @Column(name = "NAME")
    private String name; // 标准名称
    @Column(name = "MIN_WEIGHT")
    private Integer minWeight; // 最小重量
    @Column(name = "MAX_WEIGHT")
    private Integer maxWeight; // 最大重量
    @Column(name = "MIN_LENGTH")
    private Integer minLength; // 最小长度
    @Column(name = "MAX_LENGTH")
    private Integer maxLength; // 最大重量
    @Column(name = "OPERATING_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date operatingTime;// 操作时间
    @Column(name = "OPERATOR")
    private String operator; // 操作员
    @Column(name = "OPERATING_COMPANY")
    private String operatingCompany; // 操作单位
    //getter 、setter 方法

    @Override
    public String toString() {
        return "Standard{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", minWeight=" + minWeight +
                ", maxWeight=" + maxWeight +
                ", minLength=" + minLength +
                ", maxLength=" + maxLength +
                ", operatingTime=" + operatingTime +
                ", operator='" + operator + '\'' +
                ", operatingCompany='" + operatingCompany + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMinWeight() {
        return minWeight;
    }

    public void setMinWeight(Integer minWeight) {
        this.minWeight = minWeight;
    }

    public Integer getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(Integer maxWeight) {
        this.maxWeight = maxWeight;
    }

    public Integer getMinLength() {
        return minLength;
    }

    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public Date getOperatingTime() {
        return operatingTime;
    }

    public void setOperatingTime(Date operatingTime) {
        this.operatingTime = operatingTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperatingCompany() {
        return operatingCompany;
    }

    public void setOperatingCompany(String operatingCompany) {
        this.operatingCompany = operatingCompany;
    }
}
