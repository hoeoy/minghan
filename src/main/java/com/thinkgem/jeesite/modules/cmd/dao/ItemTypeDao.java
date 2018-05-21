package com.thinkgem.jeesite.modules.cmd.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.cmd.entity.ItemType;

@MyBatisDao
public interface ItemTypeDao extends CrudDao<ItemType>{

	/**
	 * 获取全部项目类型
	 * @return
	 */
	List<ItemType> getAllItemType();
	
}
