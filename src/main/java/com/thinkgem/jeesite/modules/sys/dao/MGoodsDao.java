package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.MGoods;
import com.thinkgem.jeesite.modules.sys.entity.MGoodsImage;

@MyBatisDao
public interface MGoodsDao extends CrudDao<MGoods>{

	List<MGoodsImage> getImages(String id);

	void deleteImages(String id);

	void insertGoodsImages(List<MGoodsImage> images);

}
