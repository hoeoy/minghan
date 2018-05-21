package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.Balance;
@MyBatisDao
public interface BalanceDao extends CrudDao<Balance>{

	void recharge(Balance balance);

	List<Balance> getBalanceByUser(@Param("user_id")String id,@Param("n")int n,@Param("s")int s);

	int getBDPage(@Param("id")String id);

	String getOrderNameById(@Param("id")String orderId);

	void deleteBalanceById(String id);

	
	

}
