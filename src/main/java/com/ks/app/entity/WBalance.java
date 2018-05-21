package com.ks.app.entity;

import java.math.BigDecimal;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 余额
 * @author pc-20170905
 *
 */
public class WBalance extends DataEntity<WBalance>{
	
	private static final long serialVersionUID = 1L;
	private String userId;
	/**消费还是充值 0减少 1增加*/
	private String indFlag;
	/**该笔费用*/
	private BigDecimal money;
	private String orderId;
	/**0套餐  1项目 2商品*/
	private String orderType;
	public WBalance() {
		super();
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getIndFlag() {
		return indFlag;
	}
	public void setIndFlag(String indFlag) {
		this.indFlag = indFlag;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
	
	

}
