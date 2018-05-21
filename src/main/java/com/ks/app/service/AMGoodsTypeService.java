package com.ks.app.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.ks.app.dao.AMGoodsTypeDao;
import com.ks.app.entity.AMGoodsType;
import com.thinkgem.jeesite.common.service.CrudService;
@Service
@Transactional
public class AMGoodsTypeService extends CrudService<AMGoodsTypeDao, AMGoodsType>{

	public List<AMGoodsType> getAllGoodsType() {
		List<AMGoodsType> list = dao.getAllGoodsType();
		if(list==null){
			list = Lists.newArrayList();
		}
		return list;
	}

}
