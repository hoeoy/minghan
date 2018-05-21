package com.thinkgem.jeesite.modules.cmd.entity;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class VOcomboReward extends DataEntity<VOcomboReward>{

	private static final long serialVersionUID = 1L;

	private OrderCombo orderCombo;
	private List<Reward> rewards;
	
	public OrderCombo getOrderCombo() {
		return orderCombo;
	}
	public void setOrderCombo(OrderCombo orderCombo) {
		this.orderCombo = orderCombo;
	}
	public List<Reward> getRewards() {
		return rewards;
	}
	public void setRewards(List<Reward> rewards) {
		this.rewards = rewards;
	}
	public VOcomboReward() {
		super();
	}
	
}
