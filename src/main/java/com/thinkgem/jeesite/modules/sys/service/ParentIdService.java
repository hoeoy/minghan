package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
@Service
@Transactional
public class ParentIdService  extends CrudService<UserDao,User>{
@Autowired
private UserDao userDao;

	public List<User> getYWY() {
		List<User> list  = userDao.getYWY();
		return list;
	}

}
