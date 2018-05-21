package com.thinkgem.jeesite.modules.cmd.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.cmd.entity.Rate;

@MyBatisDao
public interface RateDao extends CrudDao<Rate>{

}
