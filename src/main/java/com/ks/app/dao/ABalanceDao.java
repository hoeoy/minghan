package com.ks.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ks.app.entity.ABalance;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
@MyBatisDao
public interface ABalanceDao extends CrudDao<ABalance> {

	List<ABalance> getBalanceByUser(@Param("user_id")String id,@Param("n")int n,@Param("s")int s);

	int getBDPage(@Param("id")String id);

}
