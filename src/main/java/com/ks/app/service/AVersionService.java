package com.ks.app.service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ks.app.dao.AVersionDao;
import com.ks.app.entity.AVersionInfo;
import com.ks.utils.EnumConstant.VersionType;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.SystemPath;

@Service
@Transactional
public class AVersionService extends CrudService<AVersionDao,AVersionInfo > {
	public AVersionInfo getLastVersionInfo(String versionType,String prePath){
		AVersionInfo versionInfo = new AVersionInfo();
		versionInfo.setVersionType(versionType);
		versionInfo = dao.getLastVersionInfo(versionInfo);
		changeVersionInfo(versionInfo,prePath);
		return  versionInfo;
	}
	public void changeVersionInfo(AVersionInfo versionInfo,String prePath) {
		if(versionInfo == null)
			return;
		if(!VersionType.IOS.getKey().equals(versionInfo.getVersionType())){
			String downloadUrl = SystemPath.getRealPath(versionInfo.getFileName(), prePath);
			versionInfo.setFileName(downloadUrl);
		}
	}
}
