package com.thinkgem.jeesite.modules.sys.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 版本管理
 * @author Administrator
 *
 */
public class VersionInfo extends DataEntity<VersionInfo>{

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
	private String versionTypeName;
	
	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public Integer getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(Integer versionCode) {
		this.versionCode = versionCode;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getVersionType() {
		return versionType;
	}

	public void setVersionType(String versionType) {
		this.versionType = versionType;
	}

	public String getVersionTypeName() {
		return versionTypeName;
	}

	public void setVersionTypeName(String versionTypeName) {
		this.versionTypeName = versionTypeName;
	}

}
