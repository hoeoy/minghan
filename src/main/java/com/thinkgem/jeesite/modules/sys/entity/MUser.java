package com.thinkgem.jeesite.modules.sys.entity;

import java.math.BigDecimal;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class MUser extends DataEntity<MUser>{
	private static final long serialVersionUID = 1L;
	private String userId;//用户id
	private String flag;//是否购买权限 0未1已
	private String parentId;//面膜推荐人
	private BigDecimal mRewardMoney;//面膜佣金
	private String mUserLevel;//面膜星级
	
	private String name;//名字
	private String mobile;//手机
	private String card;//身份证
	private String performance;//业绩
	private String parentName;//推荐人名字;
	
	
	
	
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCard() {
		return card;
	}
	public void setCard(String card) {
		this.card = card;
	}
	public String getPerformance() {
		return performance;
	}
	public void setPerformance(String performance) {
		this.performance = performance;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public BigDecimal getmRewardMoney() {
		return mRewardMoney;
	}
	public void setmRewardMoney(BigDecimal mRewardMoney) {
		this.mRewardMoney = mRewardMoney;
	}
	public String getmUserLevel() {
		return mUserLevel;
	}
	public void setmUserLevel(String mUserLevel) {
		this.mUserLevel = mUserLevel;
	}
	public MUser() {
		super();
	}
	
	

}
