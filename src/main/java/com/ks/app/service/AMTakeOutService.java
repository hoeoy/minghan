package com.ks.app.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.ks.app.dao.AMTakeOutDao;
import com.ks.app.entity.AMTakeOut;
import com.ks.app.entity.AMUser;
import com.thinkgem.jeesite.common.service.CrudService;
@Service
@Transactional
public class AMTakeOutService extends	CrudService<AMTakeOutDao, AMTakeOut>{
	
	@Autowired
	private AMUserService amUserService;

	public List<AMTakeOut> getMyTakeOut(String id) {
		List<AMTakeOut> list = dao.getMyTakeOut(id);
		if(list==null){
			list = Lists.newArrayList();
		}
		return list;
	}
	

}
