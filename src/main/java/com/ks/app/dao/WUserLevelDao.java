package com.ks.app.dao;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Param;

import com.ks.app.entity.AUser;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface WUserLevelDao extends CrudDao<AUser>{
	
	/**
	 * 根据用户id修改其佣金
	 * @param id
	 * @param rewardMoney
	 * @return
	 */
	int updateRewardMoney(
			@Param("userId")String id, 
			@Param("reward")BigDecimal rewardMoney);
	
	/**
	 * 根据id获取用户
	 * @param id
	 * @return
	 */
	AUser getUserById(String id);

}
