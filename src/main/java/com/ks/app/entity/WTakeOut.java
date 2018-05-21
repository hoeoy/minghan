package com.ks.app.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 *  提现实体类（佣金、返现）
 * @author pc-20170905
 *
 */
public class WTakeOut extends DataEntity<WTakeOut>{
	
	private static final long serialVersionUID = 1L;
	
	/**提现类型 0佣金 1返现*/
	private String type;
	/**返现提现手续费*/
	private BigDecimal serviceCharge;
	/**该笔佣金提现多少*/
	private BigDecimal brokerage;
	/**该笔返现提现多少*/
	private BigDecimal back;
	/**若为12月返现则记录其返现表id*/
	private String backMonthId;
	/**佣金、返现归属用户id*/
	private String userId;
	/**佣金、返现归属用户名*/
	private String userName;
	/**审核状态 0未审核 1已审核*/
	private String auditFlag;
	/**审核人id*/
	private String auditId;
	/**审核人名*/
	private String auditName;
	/**财务是否打款 0未 1已**/
	private String remittanceFlag;
	/**转账人**/
	private String remittanceGM;
	private String userBankCard;//用户银行卡号
	private String userMobile;//用户手机
	private String userBankType;//用户银行
	private BigDecimal shiDa;//实际打款
	private String time;
	private Date startTime;//起始时间
	private Date endTime;//结束时间
	
	private String userFlag;
	

	
	public String getUserFlag() {
		return userFlag;
	}

	public void setUserFlag(String userFlag) {
		this.userFlag = userFlag;
	}

	public String getRemittanceGM() {
		return remittanceGM;
	}

	public void setRemittanceGM(String remittanceGM) {
		this.remittanceGM = remittanceGM;
	}

	public BigDecimal getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(BigDecimal serviceCharge) {
		this.serviceCharge = serviceCharge;
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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}



	public BigDecimal getShiDa() {
		return shiDa;
	}

	public void setShiDa(BigDecimal shiDa) {
		this.shiDa = shiDa;
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

	public String getRemittanceFlag() {
		return remittanceFlag;
	}

	public void setRemittanceFlag(String remittanceFlag) {
		this.remittanceFlag = remittanceFlag;
	}

	//字符串形式创建日期
	private String stringCreateDate;
	
	public String getStringCreateDate() {
		return stringCreateDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAuditName() {
		return auditName;
	}

	public void setAuditName(String auditName) {
		this.auditName = auditName;
	}

	public void setStringCreateDate(String stringCreateDate) {
		this.stringCreateDate = stringCreateDate;
	}

	public String getBackMonthId() {
		return backMonthId;
	}

	public void setBackMonthId(String backMonthId) {
		this.backMonthId = backMonthId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getBack() {
		return back;
	}

	public void setBack(BigDecimal back) {
		this.back = back;
	}

	public BigDecimal getBrokerage() {
		return brokerage;
	}

	public void setBrokerage(BigDecimal brokerage) {
		this.brokerage = brokerage;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAuditFlag() {
		return auditFlag;
	}

	public void setAuditFlag(String auditFlag) {
		this.auditFlag = auditFlag;
	}

	public String getAuditId() {
		return auditId;
	}

	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}

	public WTakeOut() {
		super();
	}
	
}