package com.thinkgem.jeesite.modules.sys.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.MTakeOut;

@MyBatisDao
public interface MTakeOutDao extends CrudDao<MTakeOut>{

	void transfer(MTakeOut takeout);

}
