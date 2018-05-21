package com.ks.app.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 积分
 * @author pc-20170905
 *
 */
public class WScore extends DataEntity<WScore>{

	private static final long serialVersionUID = 1L;
	
	/**单次增减积分*/
	private long point;
	/**所属用户id*/
	private String userId;
	/**增减状态码 0减少 1增加*/
	private String status;
	/**关联消费订单id*/
	private String orderId;
	/**订单类型 0套餐  1项目 2商品*/
	private String orderType;
	
	public WScore() {
		super();
	}
	
	public long getPoint() {
		return point;
	}
	public void setPoint(long point) {
		this.point = point;
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
