package com.ks.app.dao;

import com.ks.app.entity.AVersionInfo;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
@MyBatisDao
public interface AVersionDao extends CrudDao<AVersionInfo>{
	/**
	 * 取得最新版本信息
	 * 
	 * @return
	 */
	public AVersionInfo getLastVersionInfo(AVersionInfo versionInfo);
}