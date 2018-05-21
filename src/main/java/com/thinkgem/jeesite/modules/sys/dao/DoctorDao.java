package com.thinkgem.jeesite.modules.sys.dao;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.Doctor;
import com.thinkgem.jeesite.modules.sys.entity.Log;
@MyBatisDao
public interface DoctorDao extends CrudDao<Doctor> {

	void deleteById(@Param("id")String id);
	

}
