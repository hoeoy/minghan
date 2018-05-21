package com.thinkgem.jeesite.modules.cmd.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.cmd.entity.Shop;

@MyBatisDao
public interface ShopDao extends CrudDao<Shop>{

	/**
	 * 查询门店详情
	 * @param id
	 * @return
	 */
	Shop getShopById(String id);
	
	/**
	 * 根据套餐id查询所属门店的id
	 * @param comboId
	 * @return
	 */
	List<String> getShopIdByComboId(String comboId);
	
	/**
	 * 查某项目所属门店的id
	 * @param itemId
	 * @return
	 */
	List<String> getShopByItemId(String itemId);

}
