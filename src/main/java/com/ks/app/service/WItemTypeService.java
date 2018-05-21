package com.ks.app.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.ks.app.dao.WItemTypeDao;
import com.ks.app.entity.WItemType;
import com.thinkgem.jeesite.common.service.CrudService;

@Service
@Transactional
public class WItemTypeService extends CrudService<WItemTypeDao, WItemType>{

	/**
	 * 获取所有项目分类
	 * @return
	 */
	public List<WItemType> getAllItemType() {
		List<WItemType> types = dao.getAllItemType();
		if(types == null || types.size() < 1){
			types = Lists.newArrayList();
		}
		return types;
	}

}
