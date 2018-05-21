package com.ks.app.entity;

import java.math.BigDecimal;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 佣金
 * @author pc-20170905
 *
 */
public class WBrokerage extends DataEntity<WBrokerage>{

	private static final long serialVersionUID = 1L;
	
	/**单次增减佣金*/
	private BigDecimal money;
	/**所属用户id*/
	private String userId;
	/**所属用户名*/
	private String userName;
	/**增减状态码 0提现 1奖励*/
	private String status;
	/**关联提现/消费订单id*/
	private String orderId;
	/**订单类型 0提现 1买套餐项目 2买商品 3星级团队获得*/
	private String orderType;
	private String rewardType;//奖励类型
	private String fromUserName;//来源用户名
	private String orderName;//项目名称
	private BigDecimal orderMoney;//项目消费金额
	private String time;//订单时间
	
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRewardType() {
		return rewardType;
	}

	public void setRewardType(String rewardType) {
		this.rewardType = rewardType;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public BigDecimal getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(BigDecimal orderMoney) {
		this.orderMoney = orderMoney;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public WBrokerage() {
		super();
	}
	
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
