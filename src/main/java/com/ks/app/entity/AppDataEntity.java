package com.ks.app.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thinkgem.jeesite.common.persistence.DataEntity;

public class AppDataEntity<T> extends DataEntity<T> {

	private static final long serialVersionUID = 1L;
	protected String createUser;
	private String createUserName;
	private String createUserPhoto;
	private String createTime;
	
	public AppDataEntity() {
		super();
		super.setApp(true);
	}
	public AppDataEntity(String id) {
		super(id);
		super.setApp(true);
	}
	@JsonIgnore
	public String getRemarks() {
		return remarks;
	}
	@JsonIgnore
	public Date getUpdateDate() {
		return updateDate;
	}
	@JsonIgnore
	public boolean getIsNewRecord() {
		return isNewRecord;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getCreateUserPhoto() {
		return createUserPhoto;
	}
	public void setCreateUserPhoto(String createUserPhoto) {
		this.createUserPhoto = createUserPhoto;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	@JsonIgnore
	public Date getCreateDate() {
		return createDate;
	}
}