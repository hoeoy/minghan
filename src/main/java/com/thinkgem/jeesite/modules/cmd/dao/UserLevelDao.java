package com.thinkgem.jeesite.modules.cmd.dao;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.User;

@MyBatisDao
public interface UserLevelDao extends CrudDao<User>{
	
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
	User getUserById(String id);

}
