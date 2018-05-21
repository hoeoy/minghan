package com.ks.app.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ks.app.entity.WOrder;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface WOrderDao extends CrudDao<WOrder>{

	/**
	 * 修改消耗的积分余额
	 * @param chargeScore
	 * @param chargeBalance
	 * @return
	 */
	int afterPayChange(
		@Param("id")String id,
		@Param("score")Long chargeScore,
		@Param("balance")BigDecimal chargeBalance);
	
	/**
	 * 改支付状态
	 * @param state
	 * @return
	 */
	int changePayState(
		@Param("id")String id, 
		@Param("state")String state);
	
	/**
	 * 检查是否购买过套餐
	 * @param userId
	 * @return
	 */
	Integer hasCombo(String userId);
	
	/**
	 * 检查是否购买过任意商品（购买则有订单）
	 * @param userId
	 * @return
	 */
	Integer hasOrder(String userId);
	
	/**
	 * 根据订单号查询订单
	 * @param outTradeId
	 * @return
	 */
	WOrder getOrderByOutTradeId(String outTradeId);
	
	/**
	 * 根据用户id查订单
	 * @param userId
	 * @return
	 */
	List<WOrder> getOrdersByUserId(
			@Param("userId")String id,
			@Param("goodsType")String type);
	
	/**
	 * 分页获取用户全部消费记录
	 * @param id
	 * @param n
	 * @param s
	 * @return
	 */
	List<WOrder> getOrdersByUserIdByPage(
		@Param("id")String id, 
		@Param("n")int n, 
		@Param("s")int s);
	
	/**
	 * 查询可改价订单
	 * @return
	 */
	List<WOrder> canBeChangeOrder();

	/**
	 * 根据改价人id、订单号改成交价
	 * @param price
	 * @param chargeBalance 
	 * @param userId
	 * @param outTradeId
	 * @return
	 */
	int changeDealPrice(
			@Param("price")BigDecimal price, 
			@Param("changePriceId")String changePriceId, 
			@Param("id")String id, 
			@Param("chargeBalance")BigDecimal chargeBalance);

	/**
	 * 订单表插入
	 * @return
	 */
	int insertOrder(WOrder order);

	/**
	 * 订单审核状态改为已审核
	 * @param id 订单id
	 * @param ManageId 改价人id
	 * @return
	 */
	int Checked(
			@Param("id")String id, 
			@Param("ManageId")String ManageId);

	WOrder getWOrderById(@Param("id")String orderId);

	/**
	 * 获取用户有多少条订单数量
	 * @param id
	 * @return
	 */
	int getNumOfOrdersByUserId(String id);


}
