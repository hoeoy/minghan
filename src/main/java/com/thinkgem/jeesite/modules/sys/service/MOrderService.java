package com.thinkgem.jeesite.modules.sys.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.dao.MOrderDao;
import com.thinkgem.jeesite.modules.sys.entity.MOrder;
@Service
@Transactional
public class MOrderService extends CrudService<MOrderDao, MOrder>{

	public Page<MOrder> findPageDetail(Page<MOrder> page, MOrder order) {
		order.setPage(page);
		page.setList(dao.findListDetail(order));
		return page;
	}

	public void updateFlag(MOrder order) {
		dao.updateFlag(order);
		
	}
	

}
