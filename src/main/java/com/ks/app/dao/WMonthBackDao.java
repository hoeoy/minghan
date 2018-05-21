package com.ks.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ks.app.entity.WMonthBack;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface WMonthBackDao extends CrudDao<WMonthBack>{

	/**
	 * 根据用户id和期数查
	 * @param userId
	 * @param stage
	 * @return
	 */
	WMonthBack getRewardByIdAndStage(
		@Param("userId")String userId,
		@Param("stage")int stage);
	
	/**
	 * 修改状态
	 * @param state 返现状态
	 * @param userId
	 * @param id 主键
	 * @return
	 */
	int changeState(
		@Param("state")String state,
		@Param("userId")String userId, 
		@Param("id")String id);
	
	/**
	 * 生成一条记录
	 * @param monthBack
	 * @return
	 */
	int insertMonthBack(WMonthBack monthBack);
	
	/**
	 * 获取个人全部返现信息
	 * @param userId 用户id
	 * @return
	 */
	List<WMonthBack> getListById(@Param("userId")String userId);

	/**
	 * 根据id查
	 * @param backMonthId
	 * @return
	 */
	WMonthBack getEntityById(String backMonthId);

	/**
	 * 根据month_back_info主键id修改audit_flag
	 * @param id
	 */
	void changeAuditFlag(String id);

	String getNameById(@Param("id")String comboId);

	
	

}
