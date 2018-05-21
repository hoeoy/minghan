package com.thinkgem.jeesite.modules.sys.entity;

import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 系统常量Entity一个
 */
public class SysConstant extends DataEntity<SysConstant> {

	private static final long serialVersionUID = 1L;
	private String value;	// 数据值
	private String type;	// 类型
	private String description;// 描述
	private Integer sort;

	public SysConstant() {
		super();
	}
	
	public SysConstant(String id){
		super(id);
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	@JsonIgnore
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@XmlAttribute
	@Length(min=0, max=5000)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	@JsonIgnore
	public boolean getIsNewRecord() {
		return super.getIsNewRecord();
	}
	@JsonIgnore
	public Date getCreateDate() {
		return super.getCreateDate();
	}
	@JsonIgnore
	public Date getUpdateDate() {
		return super.getUpdateDate();
	}
	@JsonIgnore
	public String getRemarks() {
		return super.getRemarks();
	}
	@JsonIgnore
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
}