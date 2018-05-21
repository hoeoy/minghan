package com.ks.app.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 项目分类信息 实体
 * @author pc-20170905
 *
 */
public class WItemType extends DataEntity<WItemType>{
	
	private static final long serialVersionUID = 1L;
	
	/**项目分类名*/
	private String typeName;

	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	public WItemType() {
		super();
	}

}
