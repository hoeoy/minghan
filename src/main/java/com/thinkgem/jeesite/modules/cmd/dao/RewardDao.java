package com.thinkgem.jeesite.modules.cmd.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.cmd.entity.Reward;

@MyBatisDao
public interface RewardDao extends CrudDao<Reward>{

	/**
	 * 根据主键id查询
	 * @param id
	 * @return
	 */
	Reward getRewardById(String id);
	
	/**
	 * 生成一条记录
	 * @param reward
	 * @return
	 */
	int insertReward(Reward reward);

	void deleteRewardByOrderId(
		@Param("orderId")String orderId, 
		@Param("rewardType")String rewardType);

	void deleteRewardByOrderIdForeach(@Param("map")Map<String, Reward> map);

	void deleteByOrderId(String orderId);

}
