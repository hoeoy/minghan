package com.ks.app.dao;

import com.ks.app.entity.AMallAddress;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface AMallAddressDao extends CrudDao<AMallAddress> {
	public int updateAddressSelected(AMallAddress adress);
}
