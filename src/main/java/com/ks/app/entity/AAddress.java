package com.ks.app.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class AAddress extends  DataEntity<AAddress>{
	private static final long serialVersionUID = 1L;
	private String userId;//用户id
	private String linkman;//联系人
	private String mobile;//联系电话
	private String province;//省
	private String city;//市
	private String zone;//区
	private String addr;//详细地址
	private String primaryFlag;//默认flag
	
	
	
	public String getPrimaryFlag() {
		return primaryFlag;
	}
	public void setPrimaryFlag(String primaryFlag) {
		this.primaryFlag = primaryFlag;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public AAddress() {
		super();
	}
	
	

}
