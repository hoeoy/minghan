package com.thinkgem.jeesite.modules.cmd.entity;

import java.math.BigDecimal;
import java.util.List;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 项目实体类 美容项目
 * @author pc-20170905
 *
 */
public class Item extends DataEntity<Item>{
	
	private static final long serialVersionUID = 1L;
	
	/**项目名*/
	private String name;
	/**项目编号*/
	private String number;
	/**排序*/
	private Integer sort=0;
	/**标题*/
	private String title;
	/**内容*/
	private String content;
	/**介绍*/
	private String description;
	/**原价*/
	private BigDecimal oldPrice = new BigDecimal(0);
	/**现价*/
	private BigDecimal currentPrice = new BigDecimal(0);
	/**商品状态 0下架 1上架*/
	private String status;
	/**售出份数*/
	private Integer saleCount=0;
	/**logo图片路径*/
	private String logo;
	/**分类id*/
	private String type;
	/**门店id*/
	private String shopId;
	
	private BigDecimal currentMax; //价格区间
	private BigDecimal currentMin;
	private BigDecimal oldMax;
	private BigDecimal oldMin;
	private List<ItemImage> images = Lists.newArrayList();
	private List<Shop> shops;
	private List<ItemType> types;//所有项目分类
	private String typeName;//分类名
	/**滚动图地址*/
	private String imagesLocation;
	
	public Item() {
		super();
	}

	public BigDecimal getCurrentMax() {
		return currentMax;
	}

	public void setCurrentMax(BigDecimal currentMax) {
		this.currentMax = currentMax;
	}

	public BigDecimal getCurrentMin() {
		return currentMin;
	}

	public void setCurrentMin(BigDecimal currentMin) {
		this.currentMin = currentMin;
	}

	public BigDecimal getOldMax() {
		return oldMax;
	}

	public void setOldMax(BigDecimal oldMax) {
		this.oldMax = oldMax;
	}

	public BigDecimal getOldMin() {
		return oldMin;
	}

	public void setOldMin(BigDecimal oldMin) {
		this.oldMin = oldMin;
	}

	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public List<ItemType> getTypes() {
		return types;
	}
	public void setTypes(List<ItemType> types) {
		this.types = types;
	}
	public List<Shop> getShops() {
		return shops;
	}
	public void setShops(List<Shop> shops) {
		this.shops = shops;
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getImagesLocation() {
		return imagesLocation;
	}

	public void setImagesLocation(String imagesLocation) {
		this.imagesLocation = imagesLocation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getOldPrice() {
		return oldPrice;
	}

	public void setOldPrice(BigDecimal oldPrice) {
		this.oldPrice = oldPrice;
	}

	public BigDecimal getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(BigDecimal currentPrice) {
		this.currentPrice = currentPrice;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getSaleCount() {
		return saleCount;
	}

	public void setSaleCount(Integer saleCount) {
		this.saleCount = saleCount;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public List<ItemImage> getImages() {
		return images;
	}

	public void setImages(List<ItemImage> images) {
		this.images = images;
	}

	@Override
	public String toString() {
		return "Item [name=" + name + ", logo=" + logo + ", images=" + images
				+ ", imagesLocation=" + imagesLocation + "]";
	}
	
}
