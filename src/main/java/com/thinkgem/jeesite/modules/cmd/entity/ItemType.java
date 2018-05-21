package com.thinkgem.jeesite.modules.cmd.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**项目分类 实体类*/
public class ItemType extends DataEntity<ItemType>{
	
	private static final long serialVersionUID = 1L;
	
	/**项目类型名*/
	private String typeName;

	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	public ItemType() {
		super();
	}
	
}
