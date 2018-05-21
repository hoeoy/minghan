package com.ks.app.dao;

import java.util.List;

import com.ks.app.entity.WShop;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface WShopDao extends CrudDao<WShop>{

	/**
	 * 查询门店详情
	 * @param id
	 * @return
	 */
	WShop getShopById(String id);
	
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

	/**
	 * 获取全部门店
	 * @return
	 */
	List<WShop> getShopsList();

}
