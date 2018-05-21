package com.thinkgem.jeesite.modules.sys.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.dao.VersionDao;
import com.thinkgem.jeesite.modules.sys.entity.VersionInfo;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

@Service
@Transactional(readOnly = true)
public class VersionService extends CrudService<VersionDao, VersionInfo> {
	
	public Page<VersionInfo> getVersionList(Page<VersionInfo> page,VersionInfo versionInfo) {

		Page<VersionInfo> versionPage = super.findPage(page, versionInfo);

		/*List<VersionInfo> versionList = versionPage.getList();
		if(versionList == null){
			versionList = new ArrayList<VersionInfo>();
		}
		for (VersionInfo versionInfo2 : versionList) {
			if(StringUtils.isNotBlank(versionInfo2.getVersionType())){
				String versionTypeName = EnumConstant.VersionType.ANDROID.getKey().equals(versionInfo2.getVersionType()) ? EnumConstant.VersionType.ANDROID.getValue():EnumConstant.VersionType.IOS.getValue();
				versionInfo2.setVersionTypeName(versionTypeName);
			}
		}*/
		return versionPage;
	}
	@Transactional(readOnly = false)
	public int insertVersion(VersionInfo versionInfo) {
		versionInfo.preInsert();
		return dao.insert(versionInfo);
	}

	/**
	 * 设为最新版本
	 * 
	 * @param versionId 版本ID
	 * @return
	 */
	@Transactional(readOnly = false)
	public int updateVersion(String versionId) {

		int result = 0;

		// 根据ID取得版本信息
		VersionInfo versionInfo = dao.get(versionId);
		if(versionInfo != null){
			result = insertVersion(versionInfo);
		}
		// 返回结果
		return result;
	}
	/**
	 * 批量删除
	 * 
	 * @return
	 */
	@Transactional(readOnly = false)
	public Map<String, String> deleteVersions(Integer[] idArray) {
		Map<String, String> map = new HashMap<String, String>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idArray", idArray);
		params.put("updateUser", UserUtils.getUser().getId());
		params.put("updateDate", new Date());
		int result = dao.deleteVersions(params);
		map.put("result", result > 0 ? "yes" : "no");
		return map;
	}
}
