package com.thinkgem.jeesite.modules.cmd.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.cmd.entity.TakeOut;

@MyBatisDao
public interface TakeOutDao extends CrudDao<TakeOut>{

	/**
	 * 根据提现单主键id查
	 * @param orderId 提现单主键id
	 * @return
	 */
	TakeOut getTakeOutById(String orderId);
	
	/**
	 * 未审核改为已审核
	 * @param userId 审核人id
	 * @param orderId 提现单主键id
	 * @return
	 */
	int changeFlag(
		@Param("id")String orderId, 
		@Param("userId")String userId);
	
	/**
	 * 获取全部提现单
	 * @return
	 */
	List<TakeOut> getList();
	
	/**
	 * 生成订单
	 * @param takeout
	 * @return
	 */
	int insertOrder(TakeOut takeout);

	/**
	 * 根据用户id获取返现明细
	 * @param id
	 * @return
	 */
	List<TakeOut> getTakeOutByUserId(TakeOut out);

	
}
