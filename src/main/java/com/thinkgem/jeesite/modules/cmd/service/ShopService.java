package com.thinkgem.jeesite.modules.cmd.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.cmd.dao.ShopDao;
import com.thinkgem.jeesite.modules.cmd.entity.Shop;

@Service
@Transactional
public class ShopService  extends CrudService<ShopDao, Shop>{
	
//	@Autowired
//	private ShopDao shopDao;

	public Page<Shop> getShopList(Page<Shop> page, Shop shop) {
		return super.findPage(page, shop);
	}
	
	/**
	 * 根据套餐id查询所属门店
	 * @param comboId
	 * @return
	 */
//	public List<WShop> getShopsByComboId(String comboId){
//		List<String> shopIds = wShopDao.getShopIdByComboId(comboId);
//		List<WShop> shops = new ArrayList<WShop>();
//		for(String shopId : shopIds){
//			WShop shop = wShopDao.getShopById(shopId);
//			shops.add(shop);
//		}
//		return shops;
//	}
	
	/**
	 * 查询所有门店
	 * @return
	 */
//	public List<WShop> getShopsByItemId(String itemId){
//		List<String> shopIds = wShopDao.getShopByItemId(itemId);
//		List<WShop> shops = new ArrayList<WShop>();
//		for(String shopId : shopIds){
//			WShop shop = wShopDao.getShopById(shopId);
//			shops.add(shop);
//		}
//		return shops;
//	}
	
//	public List<String> getShopByItemId(String itemId){
//		return wShopDao.getShopByItemId(itemId);
//	}
	
	/**
	 * 查询门店详情
	 * @param id
	 * @return
	 */
//	public WShop getShopById(String id) {
//		return wShopDao.getShopById(id);
//	}

}
