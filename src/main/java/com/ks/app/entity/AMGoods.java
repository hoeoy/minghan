package com.ks.app.entity;

import java.math.BigDecimal;
import java.util.List;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.MGoodsImage;
import com.thinkgem.jeesite.modules.sys.entity.MGoodsType;

public class AMGoods extends DataEntity<AMGoods>{
	private static final long serialVersionUID = 1L;
	private String type;//商品类型
	private String name;//商品名字
	private String logo;//logo地址
	private String description;//商品详情
	private BigDecimal market;//原价
	private BigDecimal price;//售价
	private int sellNum;//销售数量
	private String putFlag;//是否上架 0上架 1下架
	private String sort;//排序
	
	private BigDecimal nowPrice;
	
	private String imagesLocation;
	
	private List<AMGoodsImage> images;
	
	private List<AMGoodsType> types;

	
	public BigDecimal getNowPrice() {
		return nowPrice;
	}

	public void setNowPrice(BigDecimal nowPrice) {
		this.nowPrice = nowPrice;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getMarket() {
		return market;
	}

	public void setMarket(BigDecimal market) {
		this.market = market;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getSellNum() {
		return sellNum;
	}

	public void setSellNum(int sellNum) {
		this.sellNum = sellNum;
	}

	public String getPutFlag() {
		return putFlag;
	}

	public void setPutFlag(String putFlag) {
		this.putFlag = putFlag;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getImagesLocation() {
		return imagesLocation;
	}

	public void setImagesLocation(String imagesLocation) {
		this.imagesLocation = imagesLocation;
	}

	public List<AMGoodsImage> getImages() {
		return images;
	}

	public void setImages(List<AMGoodsImage> images) {
		this.images = images;
	}

	public List<AMGoodsType> getTypes() {
		return types;
	}

	public void setTypes(List<AMGoodsType> types) {
		this.types = types;
	}

	public AMGoods() {
		super();
	}
	
	

}
