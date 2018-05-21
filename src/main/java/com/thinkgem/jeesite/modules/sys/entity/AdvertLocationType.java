package com.thinkgem.jeesite.modules.sys.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 广告位置信息
 * 
 * @author Administrator
 *
 */
public class AdvertLocationType extends DataEntity<AdvertLocationType> {

	private static final long serialVersionUID = 1L;

	// 位置名称
	private String name;

	//位置标识
	private String module;

	//位置类型
	private String type;

	//排序
	private int sort;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

}
