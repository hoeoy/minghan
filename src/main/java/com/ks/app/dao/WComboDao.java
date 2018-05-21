package com.ks.app.dao;

import java.util.List;

import com.ks.app.entity.WCombo;
import com.ks.app.entity.WComboContract;
import com.ks.app.entity.WComboImage;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface WComboDao extends CrudDao<WCombo>{

	/**
	 * 套餐购买次数+1
	 * @param id
	 * @return
	 */
	int count(String id);
	
	/**
	 * 获取套餐合同详情
	 * @param comboId 套餐id
	 * @return
	 */
	List<WComboContract> getComboContratsByComboId(String comboId);
	
	/**
	 * 获取1-4形象大使信息
	 * @return
	 */
	List<WCombo> getComboList();

	/**
	 * 获取套餐详细信息
	 * @param id
	 * @return
	 */
	WCombo getComboById(String id);

	/**
	 * 获取套餐滚动图详情
	 * @param id
	 * @return
	 */
	List<WComboImage> getComboImagesByComboId(String id);

	
}
