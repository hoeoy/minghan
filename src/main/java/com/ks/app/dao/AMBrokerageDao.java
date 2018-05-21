package com.ks.app.dao;

import java.util.List;

import com.ks.app.entity.AMBrokerage;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
@MyBatisDao
public interface AMBrokerageDao extends CrudDao<AMBrokerage>{

	List<AMBrokerage> getList(String id);

}
