package com.thinkgem.jeesite.modules.sys.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.dao.MGoodsDao;
import com.thinkgem.jeesite.modules.sys.entity.MGoods;
import com.thinkgem.jeesite.modules.sys.entity.MGoodsImage;
@Service
@Transactional
public class MGoodsService extends CrudService<MGoodsDao, MGoods>{

	public List<MGoodsImage> getImages(String id) {
		List<MGoodsImage> list = dao.getImages(id);
		if(list==null){
			list = Lists.newArrayList();
		}
		return list;
	}

	public void deleteImages(String id) {
		dao.deleteImages(id);
	}

	public void inFrameFotosaveGoods(MGoods goods) {
		if(StringUtils.isNotBlank(goods.getImagesLocation())){
			String[] icp = goods.getImagesLocation().split(",");
			List<MGoodsImage> images = new ArrayList<MGoodsImage>();
			for (int i = 0; i < icp.length; i++) {
				if(StringUtils.isNotBlank(icp[i])){
					MGoodsImage image = new MGoodsImage();
					image.setGoodsId(goods.getId());
					image.setGoodsImage(icp[i]);
					image.preInsert();
					images.add(image);
				}
			}
			dao.insertGoodsImages(images);
		}
		
	}

}
