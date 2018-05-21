package com.ks.app.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 版本管理
 * @author Administrator
 *
 */
public class AVersionInfo extends DataEntity<AVersionInfo>{

	private static final long serialVersionUID = 1L;

	/**
	 * 版本名称
	 */
	private String versionName;

	/**
	 * 版本号
	 */
	private Integer versionCode;

	/**
	 * 文件名称
	 */
	private String fileName;

	/**
	 * 备注
	 */
	private String remarks;
	
	private String versionType;


	/**
	 * @return the versionName
	 */
	public String getVersionName() {
		return versionName;
	}

	/**
	 * @param versionName the versionName to set
	 */
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	/**
	 * @return the versionCode
	 */
	public Integer getVersionCode() {
		return versionCode;
	}

	/**
	 * @param versionCode the versionCode to set
	 */
	public void setVersionCode(Integer versionCode) {
		this.versionCode = versionCode;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getVersionType() {
		return versionType;
	}

	public void setVersionType(String versionType) {
		this.versionType = versionType;
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
}
