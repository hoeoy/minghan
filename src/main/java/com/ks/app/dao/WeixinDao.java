package com.ks.app.dao;

import com.ks.app.entity.AUser;
import com.thinkgem.jeesite.common.persistence.CrudDao;

public interface WeixinDao extends CrudDao<AUser> {
	public AUser findUserByopen_id(String open_id);
	
}
