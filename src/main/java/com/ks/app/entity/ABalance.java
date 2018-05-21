package com.ks.app.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.Balance;

public class ABalance extends DataEntity<ABalance>{
	
	private static final long serialVersionUID = 1L;
	private String userId;//用户id
	private String indFlag;//类型
	private BigDecimal money;//金额
	private String orderId;//订单id
	private String orderType;//订单类型
	private String time;//时间
	private Date startTime;//起始时间
	private Date endTime;//结束时间
	
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public ABalance() {
		super();
	}
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
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
