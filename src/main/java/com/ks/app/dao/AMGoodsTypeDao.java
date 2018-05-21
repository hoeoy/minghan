package com.ks.app.dao;

import java.util.List;

import com.ks.app.entity.AMGoodsType;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
@MyBatisDao

public interface AMGoodsTypeDao extends CrudDao<AMGoodsType>{
	List<AMGoodsType> getAllGoodsType();
}
