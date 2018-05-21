package com.thinkgem.jeesite.modules.sys.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class MGoodsImage extends DataEntity<MGoodsImage>{
	
private static final long serialVersionUID = 1L;
	
	private String goodsId;//商品id
	private String goodsImage;//图片地址
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsImage() {
		return goodsImage;
	}
	public void setGoodsImage(String goodsImage) {
		this.goodsImage = goodsImage;
	}
	public MGoodsImage() {
		super();
	}
	
	

}
