package com.ks.app.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 项目实体类 美容项目
 * @author pc-20170905
 *
 */
public class WItem extends DataEntity<WItem>{
	
	private static final long serialVersionUID = 1L;
	
	/**项目名*/
	private String name;
	/**项目编号*/
	private String number;
	/**排序*/
	private Integer sort;
	/**标题*/
	private String title;
	/**内容*/
	private String content;
	/**介绍*/
	private String description;
	/**原价*/
	private BigDecimal oldPrice;
	/**现价*/
	private BigDecimal currentPrice;
	/**商品状态 0下架 1上架*/
	private String status;
	/**售出份数*/
	private Integer saleCount;
	/**logo图片路径*/
	private String logo;
	/**分类*/
	private String type;
	
	private String shopId;//
	private List<WItemImage> images = new ArrayList<WItemImage>();
	private BigDecimal currentMax; //价格区间
	private BigDecimal currentMin;
	private BigDecimal oldMax;
	private BigDecimal oldMin;
	private String shopName;//所属门店名
	private List<WShop> shops;//所有门店
	private List<WItemType> types;//所有项目分类
	private String typeName;//分类名

	public List<WShop> getShops() {
		return shops;
	}

	public List<WItemType> getTypes() {
		return types;
	}

	public void setTypes(List<WItemType> types) {
		this.types = types;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public void setShops(List<WShop> shops) {
		this.shops = shops;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
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

	public WItem() {
		super();
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

	public List<WItemImage> getImages() {
		return images;
	}

	public void setImages(List<WItemImage> images) {
		this.images = images;
	}
	
}
