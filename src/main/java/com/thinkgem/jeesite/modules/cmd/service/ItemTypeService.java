package com.thinkgem.jeesite.modules.cmd.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.cmd.dao.ItemTypeDao;
import com.thinkgem.jeesite.modules.cmd.entity.ItemType;

@Service
@Transactional
public class ItemTypeService extends CrudService<ItemTypeDao, ItemType>{

	/**
	 * 获取所有项目分类
	 * @param page
	 * @param itemType
	 * @return
	 */
	public Page<ItemType> getItemTypeList(Page<ItemType> page, ItemType itemType) {
		Page<ItemType> findPage = super.findPage(page, itemType);
		return findPage;
	}

	/**
	 * 获取所有项目类型
	 * @return
	 */
	public List<ItemType> getAllItemType() {
		List<ItemType> types = dao.getAllItemType();
		if(types == null || types.size() < 1){
			types = Lists.newArrayList();
		}
		return types;
	}

}
