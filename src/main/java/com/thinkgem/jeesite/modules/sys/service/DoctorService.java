package com.thinkgem.jeesite.modules.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ks.utils.Constant;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.dao.DoctorDao;
import com.thinkgem.jeesite.modules.sys.entity.AdvertInfo;
import com.thinkgem.jeesite.modules.sys.entity.Doctor;
import com.thinkgem.jeesite.modules.sys.entity.User;
@Service
@Transactional
public class DoctorService extends CrudService<DoctorDao,Doctor>{
	@Autowired
	DoctorDao doctorDao;
	
	public Page<Doctor> findDoctor(Page<Doctor> page, Doctor doctor) {
		// 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
		doctor.getSqlMap().put("dsf", dataScopeFilter(doctor.getCurrentUser(), "o", "a"));
		// 设置分页参数
		doctor.setPage(page);
		// 执行分页查询
		page.setList(doctorDao.findList(doctor));
		return page;
	}
	public void deleteById(String id){
		doctorDao.deleteById(id);
	}
	
	public long saveAdvert(Doctor doctor) {
		String id = doctor.getId();
		if(StringUtils.isBlank(id)){
			return insertDoctor(doctor);
		}
		return updateDoctor(doctor);
	}
	
	public long insertDoctor(Doctor doctor) {
		doctor.preInsert();
		return dao.insert(doctor);
	}

	public long updateDoctor(Doctor doctor) {
		doctor.preUpdate();
		return dao.update(doctor);
	}
}
