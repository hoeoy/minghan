package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.Score;

@MyBatisDao
public interface ScoreDao extends CrudDao<Score>{

	/**
	 * 根据用户id查积分明细
	 * @param id
	 * @return
	 */
	List<Score> getScoreByUserId(String id);

}
