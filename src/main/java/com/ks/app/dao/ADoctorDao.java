package com.ks.app.dao;

import java.util.List;

import com.ks.app.entity.ADoctor;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
@MyBatisDao
public interface ADoctorDao extends CrudDao<ADoctor> {

	public List<ADoctor> getAll();

}
