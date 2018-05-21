package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.MUser;
@MyBatisDao
public interface MUserDao extends CrudDao<MUser>{

	void parentsave(MUser muser);

	List<MUser> getSubordinateById(String id);

	String getUserIdById(String userId);


}
