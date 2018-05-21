package com.thinkgem.jeesite.modules.sys.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class Cost extends DataEntity<Cost>{
	private static final long serialVersionUID = 1L;
	
	private BigDecimal recharge;//充值
	private BigDecimal performance;//业绩
	private BigDecimal brokerage;//佣金
	private BigDecimal back;//返现
	
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

	public Cost() {
		super();
	}
	
	public BigDecimal getRecharge() {
		return recharge;
	}
	public void setRecharge(BigDecimal recharge) {
		this.recharge = recharge;
	}
	public BigDecimal getPerformance() {
		return performance;
	}
	public void setPerformance(BigDecimal performance) {
		this.performance = performance;
	}
	public BigDecimal getBrokerage() {
		return brokerage;
	}
	public void setBrokerage(BigDecimal brokerage) {
		this.brokerage = brokerage;
	}
	public BigDecimal getBack() {
		return back;
	}
	public void setBack(BigDecimal back) {
		this.back = back;
	}
	
	

}
