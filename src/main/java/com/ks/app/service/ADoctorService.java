package com.ks.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.ks.app.dao.ADoctorDao;
import com.ks.app.entity.ADoctor;
import com.thinkgem.jeesite.common.service.CrudService;
@Service
@Transactional
public class ADoctorService extends CrudService<ADoctorDao,ADoctor>{

	@Autowired
	private ADoctorDao doctorDao;

	public List<ADoctor> getAll() {
		List<ADoctor> list = doctorDao.getAll();
		if(list==null){
			list = Lists.newArrayList();
		}
		return list;
	}
}
