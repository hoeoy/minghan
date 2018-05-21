package com.ks.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ks.app.dao.WShopDao;
import com.ks.app.entity.WShop;
import com.thinkgem.jeesite.common.service.CrudService;

@Service
@Transactional
public class WShopService  extends CrudService<WShopDao,WShop>{
	
	@Autowired
	private WShopDao wShopDao;
	
	/**
	 * 根据套餐id查询所属门店
	 * @param comboId
	 * @return
	 */
	public List<WShop> getShopsByComboId(String comboId){
		List<String> shopIds = wShopDao.getShopIdByComboId(comboId);
		List<WShop> shops = new ArrayList<WShop>();
		for(String shopId : shopIds){
			WShop shop = wShopDao.getShopById(shopId);
			shops.add(shop);
		}
		return shops;
	}
	
	/**
	 * 根据项目id查询所属门店
	 * @return
	 */
	public List<WShop> getShopsByItemId(String itemId){
		List<String> shopIds = wShopDao.getShopByItemId(itemId);
		List<WShop> shops = new ArrayList<WShop>();
		for(String shopId : shopIds){
			WShop shop = wShopDao.getShopById(shopId);
			shops.add(shop);
		}
		return shops;
	}
	
//	public List<String> getShopByItemId(String itemId){
//		return wShopDao.getShopByItemId(itemId);
//	}
	
	/**
	 * 查询门店详情
	 * @param id
	 * @return
	 */
	public WShop getShopById(String id) {
		return wShopDao.getShopById(id);
	}

	/**
	 * 根据套餐id获取门店数量
	 * @param id
	 * @return
	 */
	public int getNumOfShop(String id) {
		List<String> shopIds = wShopDao.getShopIdByComboId(id);
		if(shopIds != null){
			return shopIds.size();
		}
		return 0;
	}

	/**
	 * 根据项目id获取门店数量
	 * @param id
	 * @return
	 */
	public int getItemNumOfShop(String id) {
		List<String> itemIds = wShopDao.getShopByItemId(id);
		if(itemIds != null){
			return itemIds.size();
		}
		return 0;
	}

	/**
	 * 获取全部门店
	 * @return
	 */
	public List<WShop> getShopsList() {
		List<WShop> list = wShopDao.getShopsList();
		return list;
	}

}
