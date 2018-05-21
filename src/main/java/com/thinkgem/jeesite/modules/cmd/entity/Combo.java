package com.thinkgem.jeesite.modules.cmd.entity;

import java.math.BigDecimal;
import java.util.List;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 套餐实体类 (1-4级形象大使)
 * @author pc-20170905
 *
 */
public class Combo extends DataEntity<Combo>{
	
	private static final long serialVersionUID = 1L;
	
	/**套餐名*/
	private String name;
	/**套餐编号*/
	private String number;
	/**排序*/
	private Integer sort;
	/**标题*/
	private String title;
	/**内容*/
	private String content;
	/**介绍*/
	private String description;
	/**折扣率*/
	private Double discount = 0D;
	/**原价*/
	private BigDecimal oldPrice  = new BigDecimal(0);
	/**现价*/
	private BigDecimal currentPrice = new BigDecimal(0);
	/**购买获得积分*/
	private Long score = 0L;
	/**商品状态 0下架 1上架*/
	private String status;
	/**售出份数*/
	private Integer saleCount = 0;
	/**logo图片路径*/
	private String logo;
	
	/**图片路径集合*/
	private List<ComboImage> images = Lists.newArrayList() ;
	/**合同图片路径集合*/
	private List<ComboContract> contracts = Lists.newArrayList() ;
	/**滚动图地址*/
	private String imagesLocation;
	/**合同图地址*/
	private String contractsLocation;
	
	public String getImagesLocation() {
		return imagesLocation;
	}
	public void setImagesLocation(String imagesLocation) {
		this.imagesLocation = imagesLocation;
	}
	public String getContractsLocation() {
		return contractsLocation;
	}
	public void setContractsLocation(String contractsLocation) {
		this.contractsLocation = contractsLocation;
	}
	public List<ComboContract> getContracts() {
		return contracts;
	}
	public void setContracts(List<ComboContract> contracts) {
		this.contracts = contracts;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public List<ComboImage> getImages() {
		return images;
	}
	public void setImages(List<ComboImage> images) {
		this.images = images;
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
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public BigDecimal getOldPrice() {
		return oldPrice;
	}
	public void setOldPrice(BigDecimal oldPrice) {
		this.oldPrice = oldPrice;
	}
	public Long getScore() {
		return score;
	}
	public void setScore(Long score) {
		this.score = score;
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
	public BigDecimal getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(BigDecimal currentPrice) {
		this.currentPrice = currentPrice;
	}
	
	public Combo() {
		super();
	}
	
}