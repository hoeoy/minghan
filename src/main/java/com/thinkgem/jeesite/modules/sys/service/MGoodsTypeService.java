package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.dao.MGoodsTypeDao;
import com.thinkgem.jeesite.modules.sys.entity.MGoodsType;
@Service
@Transactional
public class MGoodsTypeService extends CrudService<MGoodsTypeDao, MGoodsType>{

	/**
	 * 获取所有项目分类
	 * @param page
	 * @param itemType
	 * @return
	 */
	public Page<MGoodsType> getGoodsTypeList(Page<MGoodsType> page, MGoodsType mgoodsType) {
		Page<MGoodsType> findPage = super.findPage(page, mgoodsType);
		return findPage;
	}

	/**
	 * 获取所有项目类型
	 * @return
	 */
	public List<MGoodsType> getAllItemType() {
		List<MGoodsType> types = dao.getAllGoodsType();
		if(types == null || types.size() < 1){
			types = Lists.newArrayList();
		}
		return types;
	}
}
