package com.thinkgem.jeesite.modules.sys.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class MGoodsType extends DataEntity<MGoodsType>{
private static final long serialVersionUID = 1L;
	
	/**项目分类名*/
	private String typeName;

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public MGoodsType() {
		super();
	}

}
