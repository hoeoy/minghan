package com.thinkgem.jeesite.modules.sys.entity;

import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 积分
 * @author pc-20170905
 *
 */
public class Score extends DataEntity<Score>{

	private static final long serialVersionUID = 1L;
	
	/**单次增减积分*/
	private long point;
	/**所属用户id*/
	private String userId;
	/**所属用户名*/
	private String userName;
	/**增减状态码 0减少 1增加*/
	private String status;
	/**关联消费订单id*/
	private String orderId;
	/**订单类型 0套餐 1项目 2商品*/
	private String orderType;
	
	//字符串类型日期
	private String stringCreateDate;
	//项目或套餐名
	private String goodsName;
	private Date startTime;//起始时间
	private Date endTime;//结束时间
	private String stringUpdateDate;//字符串更新日期
	
	public String getStringUpdateDate() {
		return stringUpdateDate;
	}

	public void setStringUpdateDate(String stringUpdateDate) {
		this.stringUpdateDate = stringUpdateDate;
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

	public Score() {
		super();
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getStringCreateDate() {
		return stringCreateDate;
	}
	public void setStringCreateDate(String stringCreateDate) {
		this.stringCreateDate = stringCreateDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
