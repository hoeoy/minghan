package com.ks.app.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thinkgem.jeesite.common.persistence.DataEntity;
/**
 * 收货地址Entity
 */
public class AMallAddress extends DataEntity<AMallAddress> {

	private static final long serialVersionUID = 1L;

	private String linkMan;// 收货人
	private String address;// 收货地址
	private String telephone;// 联系方式
	private String selected;//选择状态，0不是默认，1默认
	
	private String province;//省
	private String town;//市
	private String zone;//区
	private String provinceName;//省名称
	private String townName;//市名称
	private String zoneName;//区名称
	public AMallAddress(){}
	
	public AMallAddress(String linkMan, String address, String telephone,
			String selected,String province,String town,String zone) {
		this.linkMan = linkMan;
		this.address = address;
		this.telephone = telephone;
		this.selected = selected;
		this.province=province;
		this.town=town;
		this.zone=zone;
	}


	public String getLinkMan() {
		return linkMan;
	}
	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getSelected() {
		return selected;
	}
	public void setSelected(String selected) {
		this.selected = selected;
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

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getTownName() {
		return townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	
}
