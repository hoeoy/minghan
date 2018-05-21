package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.MonthBack;

@MyBatisDao
public interface MonthBackDao extends  CrudDao<MonthBack> {

	String getComboNameById(@Param("id")String comboId);

	/**
	 * 获取可以自动领取的12月返现记录
	 * @return
	 */
	List<MonthBack> getCanCheck();

	void deleteMonthBackByOrderId(String orderId);

}
