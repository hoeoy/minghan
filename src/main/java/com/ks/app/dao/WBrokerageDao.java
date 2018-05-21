package com.ks.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ks.app.entity.WBrokerage;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.Balance;

@MyBatisDao
public interface WBrokerageDao extends CrudDao<WBrokerage>{

	int getBDPage(@Param("id")String id);

	List<WBrokerage> getBrokerageByUser(@Param("user_id")String id,@Param("n")int n,@Param("s")int s);

}
