package com.thinkgem.jeesite.modules.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ks.app.dao.WTakeOutDao;
import com.ks.app.entity.WTakeOut;
import com.thinkgem.jeesite.common.service.CrudService;
@Service
@Transactional
public class TakeOutService extends CrudService<WTakeOutDao,WTakeOut>{
	
@Autowired
private WTakeOutDao wTakeOutDao;

	public void remi(String id,String gmId) {
		wTakeOutDao.remi(id,gmId);
		
	}
}
