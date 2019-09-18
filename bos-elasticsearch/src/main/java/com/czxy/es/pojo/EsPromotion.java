package com.czxy.es.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @description:促销信息实体类
 */
@Document(indexName = "bostest_promotion",type = "promotion_test",shards = 5,replicas = 1)
public class EsPromotion {
	@Id
	private Integer id;

	@Field(type = FieldType.Text,analyzer="ik_max_word")
	private String title; // 宣传概要(标题)

	@Field(type = FieldType.Text)
	private String titleImg; // 宣传图片

	@Field(type = FieldType.Text)
	private String activeScope;// 活动范围

	@Field(type = FieldType.Date)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startDate; // 发布时间

	@Field(type = FieldType.Date)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endDate; // 失效时间

	@Field(type = FieldType.Date)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updateTime; // 更新时间

	@Field(type = FieldType.Text)
	private String updateUnit; // 更新单位

	@Field(type = FieldType.Text)
	private String updateUser;// 更新人 后续与后台用户关联

	@Field(type = FieldType.Text)
	private String status = "1"; // 状态 可取值：0 未开始  1.进行中 2. 已结束

	@Field(type = FieldType.Text)
	private String description; // 宣传内容(活动描述信息)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitleImg() {
		return titleImg;
	}

	public void setTitleImg(String titleImg) {
		this.titleImg = titleImg;
	}

	public String getActiveScope() {
		return activeScope;
	}

	public void setActiveScope(String activeScope) {
		this.activeScope = activeScope;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUnit() {
		return updateUnit;
	}

	public void setUpdateUnit(String updateUnit) {
		this.updateUnit = updateUnit;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "EsPromotion{" +
				"id=" + id +
				", title='" + title + '\'' +
				", titleImg='" + titleImg + '\'' +
				", activeScope='" + activeScope + '\'' +
				", startDate=" + startDate +
				", endDate=" + endDate +
				", updateTime=" + updateTime +
				", updateUnit='" + updateUnit + '\'' +
				", updateUser='" + updateUser + '\'' +
				", status='" + status + '\'' +
				", description='" + description + '\'' +
				'}';
	}
}

//@Document(indexName = "bos1",type = "waybill",shards = 5,replicas = 1)
//public class EsPromotion implements Serializable {
//
//	@Id
//	private Integer id;
//	@Field(type = FieldType.Text)
//	private String title; // 宣传概要(标题)
//	@Field(type = FieldType.Text)
//	private String titleImg; // 宣传图片
//	@Field(type = FieldType.Text)
//	private String activeScope;// 活动范围
//	@Field(type = FieldType.Date)
//	@DateTimeFormat(pattern = "yyyy-MM-dd")
//	private Date startDate; // 发布时间
//	@Field(type = FieldType.Date)
//	@DateTimeFormat(pattern = "yyyy-MM-dd")
//	private Date endDate; // 失效时间
//	@Field(type = FieldType.Date)
//	private Date updateTime; // 更新时间
//	@Field(type = FieldType.Text)
//	private String updateUnit; // 更新单位
//	@Field(type = FieldType.Text)
//	private String updateUser;// 更新人 后续与后台用户关联
//	@Field(type = FieldType.Text)
//	private String status = "1"; // 状态 可取值：0 未开始  1.进行中 2. 已结束
//	@Field(type = FieldType.Text)
//	private String description; // 宣传内容(活动描述信息)
//
//	public Integer getId() {
//		return id;
//	}
//
//	public void setId(Integer id) {
//		this.id = id;
//	}
//
//	public String getTitle() {
//		return title;
//	}
//
//	public void setTitle(String title) {
//		this.title = title;
//	}
//
////	public String getTitleImg() {
////		// 当此处返回图片地址的时候，自动加上  manage.boss.com/xxx/*.jpg
////		if(titleImg.contains(Constants.IMG_MANAGEMENT_HOST)){
////			return titleImg;
////		}
////		return Constants.IMG_MANAGEMENT_HOST +"/" + titleImg;
////	}
//
//	public void setTitleImg(String titleImg) {
//		this.titleImg = titleImg;
//	}
//
//	public String getActiveScope() {
//		return activeScope;
//	}
//
//	public void setActiveScope(String activeScope) {
//		this.activeScope = activeScope;
//	}
//
//	public Date getStartDate() {
//		return startDate;
//	}
//
//	public void setStartDate(Date startDate) {
//		this.startDate = startDate;
//	}
//
//	public Date getEndDate() {
//		return endDate;
//	}
//
//	public void setEndDate(Date endDate) {
//		this.endDate = endDate;
//	}
//
//	public Date getUpdateTime() {
//		return updateTime;
//	}
//
//	public void setUpdateTime(Date updateTime) {
//		this.updateTime = updateTime;
//	}
//
//	public String getUpdateUnit() {
//		return updateUnit;
//	}
//
//	public void setUpdateUnit(String updateUnit) {
//		this.updateUnit = updateUnit;
//	}
//
//	public String getUpdateUser() {
//		return updateUser;
//	}
//
//	public void setUpdateUser(String updateUser) {
//		this.updateUser = updateUser;
//	}
//
//	public String getStatus() {
//		return status;
//	}
//
//	public void setStatus(String status) {
//		this.status = status;
//	}
//
//	public String getDescription() {
//		return description;
//	}
//
//	public void setDescription(String description) {
//		this.description = description;
//	}
//
//}
