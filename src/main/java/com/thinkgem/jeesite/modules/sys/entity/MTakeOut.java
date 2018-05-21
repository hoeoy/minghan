package com.thinkgem.jeesite.modules.sys.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class MTakeOut extends DataEntity<MTakeOut>{
	private static final long serialVersionUID = 1L;
	private String userId;//用户id
	private String State;//提现状态 0未结算 1已结算
	private String remittanceGM; //打款人
	private String remittanceTime;//打款时间
	private BigDecimal money;//提现金额
	private BigDecimal shouxu;//手续费
	
	private String userBankCard;//用户银行卡号
	private String userMobile;//用户手机
	private String userBankType;//用户银行
	private BigDecimal shiDa;//实际打款
	private String userName;//用户名字（展示用）
	private String time;
	private Date startTime;//起始时间
	private Date endTime;//结束时间
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
	public String getRemittanceGM() {
		return remittanceGM;
	}
	public void setRemittanceGM(String remittanceGM) {
		this.remittanceGM = remittanceGM;
	}
	public String getRemittanceTime() {
		return remittanceTime;
	}
	public void setRemittanceTime(String remittanceTime) {
		this.remittanceTime = remittanceTime;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public BigDecimal getShouxu() {
		return shouxu;
	}
	public void setShouxu(BigDecimal shouxu) {
		this.shouxu = shouxu;
	}
	public String getUserBankCard() {
		return userBankCard;
	}
	public void setUserBankCard(String userBankCard) {
		this.userBankCard = userBankCard;
	}
	public String getUserMobile() {
		return userMobile;
	}
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}
	public String getUserBankType() {
		return userBankType;
	}
	public void setUserBankType(String userBankType) {
		this.userBankType = userBankType;
	}
	public BigDecimal getShiDa() {
		return shiDa;
	}
	public void setShiDa(BigDecimal shiDa) {
		this.shiDa = shiDa;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
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
	public MTakeOut() {
		super();
	}

	
	
}
