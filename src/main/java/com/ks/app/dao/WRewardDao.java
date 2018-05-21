package com.ks.app.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ks.app.entity.WReward;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface WRewardDao extends CrudDao<WReward>{

	/**
	 * 根据主键id查询
	 * @param id
	 * @return
	 */
	WReward getRewardById(String id);
	
	/**
	 * 生成一条记录
	 * @param reward
	 * @return
	 */
	int insertReward(WReward reward);

	List<WReward> getRewardByorderId(@Param("orderId")String id,@Param("userReward")BigDecimal bd,@Param("userId")String userId);

}
