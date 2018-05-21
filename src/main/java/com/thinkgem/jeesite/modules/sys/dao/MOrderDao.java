package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.MOrder;
@MyBatisDao
public interface MOrderDao extends CrudDao<MOrder>{

	List<MOrder> findListDetail(MOrder order);

	void updateFlag(MOrder order);

}
