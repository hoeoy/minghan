package com.thinkgem.jeesite.modules.cmd.entity;

import java.math.BigDecimal;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 佣金、二级分销实体类
 * @author pc-20170905
 *
 */
public class Reward extends DataEntity<Reward>{

	private static final long serialVersionUID = 1L;
	
	/**订单号（该次奖励金来自哪一笔消费）*/
	private String orderId;
	/**奖励类型 0一级分销 1二级分销 2六星玩法 3购买套餐上级返10%*/
	private String rewardType;
	/**收益用户id*/
	private String userId;
	/**收益用户名*/
	private String userName;
	/**奖励金额*/
	private BigDecimal userReward;
	/**用户记录*/
	private int userStar;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public BigDecimal getUserReward() {
		return userReward;
	}
	public void setUserReward(BigDecimal userReward) {
		this.userReward = userReward;
	}
	public int getUserStar() {
		return userStar;
	}
	public void setUserStar(int userStar) {
		this.userStar = userStar;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getRewardType() {
		return rewardType;
	}
	public void setRewardType(String rewardType) {
		this.rewardType = rewardType;
	}
	
	public Reward() {
		super();
	}
	
	
}
