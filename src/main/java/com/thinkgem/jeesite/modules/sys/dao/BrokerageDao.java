package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.Brokerage;

@MyBatisDao
public interface BrokerageDao extends CrudDao<Brokerage>{

	void deleteBrokerage(String id);

	void deleteBrokerageForeach(@Param("list")List<String> list);

}
