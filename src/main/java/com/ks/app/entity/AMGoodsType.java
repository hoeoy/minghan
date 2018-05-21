package com.ks.app.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class AMGoodsType extends DataEntity<AMGoodsType>{
	
	private static final long serialVersionUID = 1L;
	
	/**项目分类名*/
	private String typeName;

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public AMGoodsType() {
		super();
	}
	
	

}
