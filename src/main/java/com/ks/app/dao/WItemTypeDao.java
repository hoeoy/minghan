package com.ks.app.dao;

import java.util.List;

import com.ks.app.entity.WItemType;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface WItemTypeDao extends CrudDao<WItemType>{

	/**
	 * 获取所有项目分类
	 * @return
	 */
	List<WItemType> getAllItemType();

}
