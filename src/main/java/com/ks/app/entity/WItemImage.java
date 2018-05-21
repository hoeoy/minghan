package com.ks.app.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 项目图片实体类
 * @author pc-20170905
 *
 */
public class WItemImage extends DataEntity<WItemImage>{

	private static final long serialVersionUID = 1L;
	
	/**项目id*/
	private String itemId;
	/**图片路径*/
	private String itemImage;
	
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getItemImage() {
		return itemImage;
	}
	public void setItemImage(String itemImage) {
		this.itemImage = itemImage;
	}

	public WItemImage() {
		super();
	}
	
}
