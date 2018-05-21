package com.ks.app.entity;

import java.math.BigDecimal;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 佣金、二级分销实体类
 * @author pc-20170905
 *
 */
public class WReward extends DataEntity<WReward>{

	private static final long serialVersionUID = 1L;
	
	/**订单号（该次奖励金来自哪一笔消费）*/
	private String orderId;
	/**奖励类型 0一级分销 1二级分销 2六星玩法 3购买套餐上级返10%*/
	private String rewardType;
	/**获得奖励用户id*/
	private String userId;
	/**用户获得奖励金金额*/
	private BigDecimal userReward;
	/**用户星级 当订单为6星玩法时才有值*/
	private int userStar;
	
	public int getUserStar() {
		return userStar;
	}
	public void setUserStar(int userStar) {
		this.userStar = userStar;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public BigDecimal getUserReward() {
		return userReward;
	}
	public void setUserReward(BigDecimal userReward) {
		this.userReward = userReward;
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
	
	public WReward() {
		super();
	}
	
	
}
