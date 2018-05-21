package com.thinkgem.jeesite.modules.cmd.entity;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class VOitemReward extends DataEntity<VOitemReward>{

	private static final long serialVersionUID = 1L;

	private OrderItem orderItem;
	private List<Reward> rewards;
	
	public VOitemReward() {
		super();
	}
	
	public OrderItem getOrderItem() {
		return orderItem;
	}
	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
	}
	public List<Reward> getRewards() {
		return rewards;
	}
	public void setRewards(List<Reward> rewards) {
		this.rewards = rewards;
	}
	
}
