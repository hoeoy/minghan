package com.ks.app.dao;

import com.ks.app.entity.WBalance;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface WBalanceDao extends CrudDao<WBalance>{

	void recharge(WBalance balance);

}
