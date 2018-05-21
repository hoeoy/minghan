package com.ks.app.dao;

import java.util.List;

import com.ks.app.entity.AMTakeOut;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
@MyBatisDao
public interface AMTakeOutDao extends CrudDao<AMTakeOut>{

	List<AMTakeOut> getMyTakeOut(String id);

}
