package com.ks.app.dao;

import java.util.List;

import com.ks.app.entity.AAddress;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
@MyBatisDao
public interface AAddressDao extends CrudDao<AAddress> {

	AAddress getPrimaryByUserId(String userId);

	List<AAddress> findAddressOfUser(String userId);

	List<AAddress> getMyaddress(String id);

	void primaryDown();

	void primaryUp(String id);

}
