package com.ks.app.dao;

import com.ks.app.entity.APoster;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
@MyBatisDao
public interface APosterDao extends CrudDao<APoster>{
	String getPath();

	String getMPath();

}
