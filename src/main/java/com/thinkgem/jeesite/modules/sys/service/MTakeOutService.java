package com.thinkgem.jeesite.modules.sys.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.dao.MTakeOutDao;
import com.thinkgem.jeesite.modules.sys.entity.MTakeOut;

@Service
@Transactional
public class MTakeOutService extends CrudService<MTakeOutDao,MTakeOut>{

	public void transfer(MTakeOut takeout) {
		dao.transfer(takeout);
		
	}

}
