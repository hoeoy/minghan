package com.ks.app.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ks.app.entity.AMUser;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.MUser;
@MyBatisDao
public interface AMUserDao extends CrudDao<AMUser>{

	AMUser getMuserByUserId(String userId);

	List<AMUser> getByParentId(String parentId);

	void allterRM(AMUser user);

	void buyZiGeSucceed(String userId);

	int getSubNum(String id);

	void updateParentId(AMUser user);
	
	int getDiscountsNum(String userId);

	void updateDiscountsNum(@Param("index")int index, @Param("userId")String userId);

}
