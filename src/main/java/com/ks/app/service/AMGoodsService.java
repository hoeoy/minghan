package com.ks.app.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.ks.app.dao.AMGoodsDao;
import com.ks.app.entity.AMGoods;
import com.ks.app.entity.AMGoodsImage;
import com.thinkgem.jeesite.common.service.CrudService;
@Service
@Transactional
public class AMGoodsService extends CrudService<AMGoodsDao, AMGoods>{

	public List<AMGoods> getGoodsByType(String type) {
		List<AMGoods> list = dao.getGoodsByType(type);
		if(list==null){
			list = Lists.newArrayList();
		}
		return list;
	}

	public List<AMGoodsImage> getImageByGoodsId(String id) {
		List<AMGoodsImage> list = dao.getImageByGoodsId(id);
		if(list==null){
			list = Lists.newArrayList();
		}
		return list;
	}

	public List<AMGoods> getTwoGoods() {
		List<AMGoods> list = dao.getTwoGoods();
		if(list==null){
			list = Lists.newArrayList();
		}
		return list;
	}

	public void addSellNum(String goodsId) {
		int sellNum = dao.getSellNumById(goodsId);
		sellNum++;
		AMGoods goods = new AMGoods();
		goods.setId(goodsId);
		goods.setSellNum(sellNum);
		dao.updateSellNum(goods);
		
	}

}
