package com.ks.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ks.app.entity.AMOrder;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface AMOrderDao extends CrudDao<AMOrder>{

	List<AMOrder> getZGOrder(String userId);

	AMOrder getByOrderNo(String orderNo);

	List<AMOrder> getOrderList(@Param("n")int n, @Param("s")int s, @Param("orderState")String orderState,@Param("userId")String userId);

	void exitOrder(String id);

	String getState(String id);

	void shouhuo(String id);
	
	int getAllPage(@Param("orderState")String orderState);

}
