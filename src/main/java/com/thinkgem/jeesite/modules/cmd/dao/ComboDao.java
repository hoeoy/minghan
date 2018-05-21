package com.thinkgem.jeesite.modules.cmd.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.cmd.entity.Combo;
import com.thinkgem.jeesite.modules.cmd.entity.ComboContract;
import com.thinkgem.jeesite.modules.cmd.entity.ComboImage;

@MyBatisDao
public interface ComboDao extends CrudDao<Combo>{

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
	List<ComboContract> getComboContractsByComboId(String comboId);
	/**
	 * 获取套餐滚动图详情
	 * @param comboId
	 * @return
	 */
	List<ComboImage> getComboImagesByComboId(String comboId);
	
	
	/**
	 * 获取1-4形象大使信息
	 * @return
	 */
	List<Combo> getComboList();

	/**
	 * 获取套餐详细信息
	 * @param id
	 * @return
	 */
	Combo getComboById(String id);

	/**
	 * 删除套餐相关图片
	 * @param comboId
	 */
	void deleteComboImages(String comboId);

	/**
	 * 删除套餐相关合同
	 * @param comboId
	 */
	void deleteComboContracts(String comboId);

//	void insertComboImage(Map<String,Object> param);
//	void insertComboContract(Map<String,Object> param);
	void insertComboImage(List<ComboImage> images);
	void insertComboContract(List<ComboContract> images);

}
