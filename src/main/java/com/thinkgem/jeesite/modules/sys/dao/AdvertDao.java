package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.AdvertInfo;
import com.thinkgem.jeesite.modules.sys.entity.AdvertLocationType;

@MyBatisDao
public interface AdvertDao extends CrudDao<AdvertInfo> {
	public int updateAdvertSort(AdvertInfo advertInfo);

	public int insertLocationType(AdvertLocationType advertLocationType);
	
	public int updateLocationTypeSort(AdvertLocationType advertLocationType);
	
	public AdvertLocationType getLocationTypeById(String typeId);
	
	public List<AdvertLocationType> findLocationTypeList(AdvertLocationType advertLocationType);
	
}
