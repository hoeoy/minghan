package com.thinkgem.jeesite.modules.cmd.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 门店实体类
 * @author pc-20170905
 *
 */
public class Shop extends DataEntity<Shop>{
	
	private static final long serialVersionUID = 1L;
	
	/**门店名*/
	private String shopName;
	/**门店地址*/
	private String shopAddress;
	/**门店电话*/
	private String shopTel;
	/**经度*/
	private String longitude;
	/**维度*/
	private String latitude;
	/**门店logo*/
	private String shopLogo;
	
	public Shop() {
		super();
	}
	
	public String getShopLogo() {
		return shopLogo;
	}
	public void setShopLogo(String shopLogo) {
		this.shopLogo = shopLogo;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getShopAddress() {
		return shopAddress;
	}
	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}
	public String getShopTel() {
		return shopTel;
	}
	public void setShopTel(String shopTel) {
		this.shopTel = shopTel;
	}
	

}
