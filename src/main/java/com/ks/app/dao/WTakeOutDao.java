package com.ks.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ks.app.entity.WTakeOut;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface WTakeOutDao extends CrudDao<WTakeOut>{

	/**
	 * 根据提现单主键id查
	 * @param orderId 提现单主键id
	 * @return
	 */
	WTakeOut getTakeOutById(String orderId);
	
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
	List<WTakeOut> getList(WTakeOut out);
	
	/**
	 * 生成订单
	 * @param takeout
	 * @return
	 */
	int insertOrder(WTakeOut takeout);

	void remi(@Param("id")String id,@Param("gmId")String gmId);

	/**
	 * 根据order_out_info主键id查询month_back_id
	 * @param id
	 * @return
	 */
	String getMonthBackId(String id);

	
}
