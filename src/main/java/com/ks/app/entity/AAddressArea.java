package com.ks.app.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thinkgem.jeesite.common.persistence.DataEntity;

public class AAddressArea extends DataEntity<AAddressArea>{
	private static final long serialVersionUID = 1L;
	private String pid;
	private String name;
	private String pname;
	
	private List<AAddressArea> sub = new ArrayList<AAddressArea>();
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@JsonIgnore
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
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
	@JsonIgnore
	public Date getCreateDate() {
		return createDate;
	}
	public List<AAddressArea> getSub() {
		return sub;
	}
	public void setSub(List<AAddressArea> sub) {
		this.sub = sub;
	}

	
}
