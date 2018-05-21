package com.ks.app.dao;

import java.util.List;

import com.ks.app.entity.AMGoods;
import com.ks.app.entity.AMGoodsImage;
import com.ks.app.entity.AMGoodsType;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
@MyBatisDao
public interface AMGoodsDao extends CrudDao<AMGoods>{

	List<AMGoods> getGoodsByType(String type);

	List<AMGoodsImage> getImageByGoodsId(String id);

	List<AMGoods> getTwoGoods();

	int getSellNumById(String goodsId);

	void updateSellNum(AMGoods goods);

	

}
