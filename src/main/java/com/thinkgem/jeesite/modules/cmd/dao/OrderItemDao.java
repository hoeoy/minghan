package com.thinkgem.jeesite.modules.cmd.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.cmd.entity.OrderCombo;
import com.thinkgem.jeesite.modules.cmd.entity.OrderItem;
import com.thinkgem.jeesite.modules.cmd.entity.Reward;

@MyBatisDao
public interface OrderItemDao extends CrudDao<OrderItem>{

	/**
	 * 修改向上返点及消耗的积分余额
	 * @param chargeScore
	 * @param chargeBalance
	 * @param parentAward
	 * @param grandAward
	 * @return
	 */
	int afterPayChange(
		@Param("id")String id,
		@Param("score")Long chargeScore,
		@Param("balance")BigDecimal chargeBalance,
		@Param("parentAward")BigDecimal parentAward,
		@Param("grandAward")BigDecimal grandAward);
	
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
	OrderCombo getOrderByOutTradeId(String outTradeId);
	
	/**
	 * 根据用户id查订单
	 * @param userId
	 * @return
	 */
	List<OrderCombo> getOrdersByUserId(String userId);
	
	/**
	 * 查询可改价订单
	 * @return
	 */
	List<OrderCombo> canBeChangeOrder();

	/**
	 * 根据改价人id、订单号改成交价
	 * @param price
	 * @param userId
	 * @param outTradeId
	 * @return
	 */
	int changeDealPrice(
			@Param("price")BigDecimal price, 
			@Param("changePriceId")String changePriceId, 
			@Param("outTradeId")String outTradeId);

	/**
	 * 订单表插入
	 * @return
	 */
	int insertOrder(OrderCombo order);

	/**
	 * 根据订单号获取项目名
	 * @return
	 */
	String getNameByoutTradeId(String outTradeId);

	/**
	 * 模糊查询出套餐id
	 * @param comboName
	 * @return
	 */
	List<String> getNameId(String comboName);

	List<Reward> getRewardByOrderId(String orderId);

	void deleteOrderById(String orderId);

	void text(@Param("text")String text, @Param("id")String id);

}
