package com.ks.app.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 12月返现实体类
 * @author pc-20170905
 *
 */
public class WMonthBack extends DataEntity<WMonthBack>{

	private static final long serialVersionUID = 1L;
	
	/**返现人id*/
	private String userId;
	/**所属订单id*/
	private String orderId;
	/**所属套餐id*/
	private String comboId;
	/**1-12期*/
	private int stage;
	/**每期返现金额*/
	private BigDecimal money;
	/**返现标记 0未领取 1已领取*/
	private String state;
	/**可返现时间*/
	private Date startTime;
	/**自动返现时间*/
	private Date autoTime;
	/**0手动申请  1自动申请*/
	private String type;
	/**审核状态0未开始，1待审核，2已通过**/
	private String auditFlag;
	/**String类型开始时间**/
	private String startTimeString;
	/**String类型结束时间**/
	private String autoTimeString;
	/**是否到开始时间**/
	private long time;
	
	private String comboName;
	
	

	public String getComboName() {
		return comboName;
	}
	public void setComboName(String comboName) {
		this.comboName = comboName;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public String getStartTimeString() {
		return startTimeString;
	}
	public void setStartTimeString(String startTimeString) {
		this.startTimeString = startTimeString;
	}
	public String getAutoTimeString() {
		return autoTimeString;
	}
	public void setAutoTimeString(String autoTimeString) {
		this.autoTimeString = autoTimeString;
	}
	public String getAuditFlag() {
		return auditFlag;
	}
	public void setAuditFlag(String auditFlag) {
		this.auditFlag = auditFlag;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getAutoTime() {
		return autoTime;
	}
	public void setAutoTime(Date autoTime) {
		this.autoTime = autoTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getComboId() {
		return comboId;
	}
	public void setComboId(String comboId) {
		this.comboId = comboId;
	}
	public int getStage() {
		return stage;
	}
	public void setStage(int stage) {
		this.stage = stage;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public WMonthBack() {
		super();
	}
	
	
}
