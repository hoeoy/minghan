package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.cmd.entity.ItemType;
import com.thinkgem.jeesite.modules.sys.entity.MGoodsType;

@MyBatisDao
public interface MGoodsTypeDao extends CrudDao<MGoodsType>{

	/**
	 * 获取全部项目类型
	 * @return
	 */
	List<MGoodsType> getAllGoodsType();
}
