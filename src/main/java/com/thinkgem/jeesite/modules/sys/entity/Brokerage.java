package com.thinkgem.jeesite.modules.sys.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 佣金
 * @author pc-20170905
 *
 */
public class Brokerage extends DataEntity<Brokerage>{

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
	/**订单类型 0提现 1买套餐 2买项目 3星级获得*/
	private String orderType;
	private String rewardType;//奖励类型
	private String fromUserName;//来源用户名
	private String orderName;//项目名称（佣金list中封装商品名）
	private BigDecimal orderMoney;//项目消费金额
	private String time;//订单时间
	private Date startTime;//起始时间
	private Date endTime;//结束时间
	private String start;
	private String end;
	//受益人星级
	private Integer star;
	
	public Date getStartTime() {
		return startTime;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
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

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}



	public Brokerage() {
		super();
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



	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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

	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}
	
}
