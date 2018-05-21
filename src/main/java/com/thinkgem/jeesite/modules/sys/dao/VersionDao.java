package com.thinkgem.jeesite.modules.sys.dao;

import java.util.Map;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.VersionInfo;

@MyBatisDao
public interface VersionDao extends CrudDao<VersionInfo> {

	public int deleteVersions(Map<String, Object> params);
	
}
