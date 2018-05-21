package com.ks.app.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.ks.app.dao.AAddressDao;
import com.ks.app.entity.AAddress;
import com.thinkgem.jeesite.common.service.CrudService;

@Service
@Transactional
public class AAddressService extends CrudService<AAddressDao, AAddress> {

	public AAddress getPrimaryByUserId(String userId){
		return dao.getPrimaryByUserId(userId);
	}

	public List<AAddress> findAddressOfUser(String userId) {
		List<AAddress> list = dao.findAddressOfUser(userId);
		if(list==null){
			list = Lists.newArrayList();
		}
		return list;
	}

	public List<AAddress> getMyaddress(String id) {
		List<AAddress> list = dao.getMyaddress(id);
		if(list==null){
			list=Lists.newArrayList();
		}
		return list;
	}

	public void updatePrimaryFlag(String id) {
		dao.primaryDown();
		dao.primaryUp(id);
	}
}
