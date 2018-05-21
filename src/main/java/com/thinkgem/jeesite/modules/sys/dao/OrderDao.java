package com.thinkgem.jeesite.modules.sys.dao;

import java.math.BigDecimal;
import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.Order;
@MyBatisDao
public interface OrderDao extends CrudDao<Order>{

	List<BigDecimal> getDealPrice(Order order);

}