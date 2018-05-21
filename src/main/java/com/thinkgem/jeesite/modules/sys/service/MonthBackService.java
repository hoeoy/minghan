package com.thinkgem.jeesite.modules.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.dao.MonthBackDao;
import com.thinkgem.jeesite.modules.sys.entity.MonthBack;

@Service
@Transactional
public class MonthBackService extends CrudService<MonthBackDao,MonthBack>{
	
	@Autowired
	private MonthBackDao monthBackDao;

	public String getComboNameById(String comboId) {
		String name = monthBackDao.getComboNameById(comboId);
		return name;
	}

}
