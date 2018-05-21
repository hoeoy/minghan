package com.thinkgem.jeesite.modules.sys.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class Balance extends DataEntity<Balance>{
	
	private static final long serialVersionUID = 1L;
	private String userId;//用户id
	private String indFlag;//类型 1充值 0消费
	private BigDecimal money;//金额
	private String orderId;//订单id
	private String orderType;//订单类型 0套餐 1项目
	private String time;//时间
	private Date startTime;//起始时间
	private Date endTime;//结束时间
	private String explain;//充值说明
	private String operator;//操作员
	
	
	
	
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

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

	public Balance() {
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
