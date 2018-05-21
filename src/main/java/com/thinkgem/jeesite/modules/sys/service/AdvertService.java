package com.thinkgem.jeesite.modules.sys.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ks.utils.Constant;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.dao.AdvertDao;
import com.thinkgem.jeesite.modules.sys.entity.AdvertInfo;
import com.thinkgem.jeesite.modules.sys.entity.AdvertLocationType;

@Service
@Transactional
public class AdvertService extends CrudService<AdvertDao,AdvertInfo > {
	
	public Page<AdvertInfo> findAdvertPage(Page<AdvertInfo> page,AdvertInfo advertInfo) {

		Page<AdvertInfo> versionPage = super.findPage(page, advertInfo);

		return versionPage;
	}
	public long saveAdvert(AdvertInfo advertInfo) {
		String id = advertInfo.getId();
		if(StringUtils.isBlank(id)){
			advertInfo.setAdvertState(Constant.DELETE_FLAG_1);
			return insertAdvert(advertInfo);
		}
		return updateAdvert(advertInfo);
	}
	public long insertAdvert(AdvertInfo advertInfo) {
		advertInfo.preInsert();
		return dao.insert(advertInfo);
	}

	public long updateAdvert(AdvertInfo advertInfo) {
		advertInfo.preUpdate();
		return dao.update(advertInfo);
	}
	
	public int updateAdvertSort(AdvertInfo advertInfo){
		advertInfo.preUpdate();
		return dao.updateAdvertSort(advertInfo);
	}
	
	
	public int insertLocationType(AdvertLocationType advertLocationType){
		advertLocationType.preInsert();
		return dao.insertLocationType(advertLocationType);
	}
	
	public int updateLocationTypeSort(AdvertLocationType advertLocationType){
		advertLocationType.preUpdate();
		return dao.updateLocationTypeSort(advertLocationType);
	}
	
	public AdvertLocationType getLocationTypeById(String typeId){
		return dao.getLocationTypeById(typeId);
	}
	
	public Page<AdvertLocationType> findLocationTypePage(Page<AdvertLocationType> page,AdvertLocationType advertLocationType) {

//		Page<AdvertLocationType> versionPage = super.findPage(page, AdvertLocationType);
		advertLocationType.setPage(page);
		page.setList(dao.findLocationTypeList(advertLocationType));
		return page;
	}
}
