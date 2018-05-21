package com.thinkgem.jeesite.modules.sys.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 广告信息
 * 
 * @author Administrator
 *
 */
public class AdvertInfo extends DataEntity<AdvertInfo> {

	private static final long serialVersionUID = 1L;

	/**
	 * 广告关联ID
	 */
	private String relateId;

	/**
	 * 广告标题
	 */
	private String advertTitle;

	/**
	 * 广告类型
	 */
	private String advertType;

	/**
	 * 置顶状态
	 */
	private String advertState;
	
	//点击链接
	private String relateUrl;
	//图片地址
	private String advertUrl;
	//广告位置id
	private String locationType;
	
	//排序
	private int sort;

	public String getRelateId() {
		return relateId;
	}

	public void setRelateId(String relateId) {
		this.relateId = relateId;
	}

	public String getAdvertTitle() {
		return advertTitle;
	}

	public void setAdvertTitle(String advertTitle) {
		this.advertTitle = advertTitle;
	}
	public String getAdvertType() {
		return advertType;
	}

	public void setAdvertType(String advertType) {
		this.advertType = advertType;
	}
	@JsonIgnore
	public String getAdvertState() {
		return advertState;
	}

	public void setAdvertState(String advertState) {
		this.advertState = advertState;
	}

	public String getAdvertUrl() {
		return advertUrl;
	}

	public void setAdvertUrl(String advertUrl) {
		this.advertUrl = advertUrl;
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

	public String getLocationType() {
		return locationType;
	}

	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getRelateUrl() {
		return relateUrl;
	}

	public void setRelateUrl(String relateUrl) {
		this.relateUrl = relateUrl;
	}
	
}
