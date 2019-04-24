package com.mcgj.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 基本实体
 * @author 杨晨
 * @date 2019-04-24
 * @address 深圳
 *
 */
public class BaseEntity implements Serializable{

	private Date createDate = new Date();//数据爬取时间
	
	private String address;//数据爬取地址
	
	private String dataStatus;//数据爬去状态(0失败1成功)

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
	}

}
