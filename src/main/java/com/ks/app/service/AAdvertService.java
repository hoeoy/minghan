package com.ks.app.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ks.app.dao.AAdvertDao;
import com.ks.app.entity.AAdvertInfo;
import com.ks.utils.Constant;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.ImageUtils;
import com.thinkgem.jeesite.common.utils.SystemPath;

@Service
@Transactional
public class AAdvertService extends CrudService<AAdvertDao, AAdvertInfo>{

	public List<AAdvertInfo> findAdvertList(AAdvertInfo advertInfo,Integer width,Integer height,HttpServletRequest request) {
		List<AAdvertInfo> advertList = dao.findList(advertInfo);
		if(advertList != null && advertList.size()>0){
			for (AAdvertInfo aAdvertInfo : advertList) {
					Integer[] scale = ImageUtils.getImgScale(width,height,Constant.DEFAULT_WIDTH,Constant.DEFAULT_HEIGHT);
					
					width = scale[0];
					height = scale[1];
					//获取
					String imageUrl = ImageUtils.getCacheAdvertThumb(aAdvertInfo.getId(),width,height,aAdvertInfo.getAdvertUrl(),SystemPath.getRequestPreUrl(request),true);
					aAdvertInfo.setAdvertUrl(imageUrl);
//				}
				
//				aAdvertInfo.setShareUrl(SystemPath.getRequestProjectUrl(request)+"/"+(String.format(Constant.DEFAULT_SHARE_URL, StringUtils.isBlank(advertInfo.getRelateId()) ? "":advertInfo.getRelateId())));
			}
		}
		return advertList;
	}
}
