package com.ks.app.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.drew.lang.StringUtil;
import com.google.common.collect.Lists;
import com.ks.app.dao.ABeautyDiaryDao;
import com.ks.app.dao.AUserDao;
import com.ks.app.dao.WOrderDao;
import com.ks.app.dao.WShopDao;
import com.ks.app.entity.AUser;
import com.ks.app.entity.WOrder;
import com.ks.app.utils.WeixinUtil;
import com.thinkgem.jeesite.common.service.CrudService;

@Service
@Transactional
public class WOrderService extends CrudService<WOrderDao, WOrder>{
	
	@Autowired
	private WOrderDao wOrderDao;
	@Autowired
	private AUserDao aUserDao;
	@Autowired
	private WShopDao wShopDao;
	@Autowired
	private ABeautyDiaryDao aBeautyDiaryDao;

	/**
	 * 根据用户id查订单
	 * @param userId
	 * @return
	 */
	public List<WOrder> getOrdersByUserId(String userId) {
		List<WOrder> orders = wOrderDao.getOrdersByUserId(userId, null);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		for(WOrder order : orders){
			order.setStringCreateDate(sdf.format(order.getCreateDate()));
			String goodsType = order.getGoodsType();
			if("1".equals(goodsType)){//套餐
				List<String> shop = wShopDao.getShopIdByComboId(order.getGoodsId());
				order.setShopName(shop.size()<1 ? "未查询到匹配门店" : wShopDao.getShopById(shop.get(0)).getShopName());
			}else if("2".equals(goodsType)){//项目
				List<String> shop = wShopDao.getShopByItemId(order.getGoodsId());
				order.setShopName(shop.size()<1 ? "未查询到匹配门店" : wShopDao.getShopById(shop.get(0)).getShopName());
			}
		}
		return orders;
	}
	
	/**
	 * 分页获取用户全部消费记录
	 * @param id
	 * @param index
	 * @return
	 */
	public List<WOrder> getOrdersByUserIdByPage(String id, int index) {
		int n = (index-1)*10;
		int s = WeixinUtil.BeautyDiaryCount;
		List<WOrder> orders = wOrderDao.getOrdersByUserIdByPage(id, n, s);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(orders == null){
			orders = Lists.newArrayList();
		}
		for(WOrder order : orders){
			order.setStringCreateDate(sdf.format(order.getCreateDate()));
			String goodsType = order.getGoodsType();
			if("1".equals(goodsType)){//套餐
				List<String> shop = wShopDao.getShopIdByComboId(order.getGoodsId());
				order.setShopName(shop.size()<1 ? "未查询到匹配门店" : wShopDao.getShopById(shop.get(0)).getShopName());
			}else if("2".equals(goodsType)){//项目
				List<String> shop = wShopDao.getShopByItemId(order.getGoodsId());
				order.setShopName(shop.size()<1 ? "未查询到匹配门店" : wShopDao.getShopById(shop.get(0)).getShopName());
			}
		}
		return orders;
	}

	/**
	 * 根据改价人id、订单号改成交价
	 * @param price
	 * @param changePriceId
	 * @param outTradeId
	 * @return
	 */
	public int changeDealPrice(Integer price, String changePriceId, String outTradeId) {
		return wOrderDao.changeDealPrice(new BigDecimal(price), changePriceId, outTradeId, null);
	}

	/**
	 * 获取当前用户的项目订单
	 * @param session
	 * @return
	 */
	public List<WOrder> getItemOrderByUser(AUser user) {
//		AUser user = aUserDao.getUser((String)session.getAttribute("openId"));
		if(user == null)
			return Lists.newArrayList();
		List<WOrder> orders = wOrderDao.getOrdersByUserId(user.getId(), "2");
		if(orders != null && !orders.isEmpty()){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			for(WOrder order : orders){
				//创建时间
				String format = sdf.format(order.getCreateDate());
				order.setStringCreateDate(format);
				//审核人
				if(order.getCheckState() != null){
					if("1".equals(order.getCheckState())){//已审核
						if(StringUtils.isNotBlank(order.getChangePriceId())){
							AUser changeUser = aUserDao.getUserById(order.getChangePriceId());
							if(changeUser!=null){
								order.setCheckName(changeUser.getName());
							}
						}
					}
				}
				if(order.getCheckState() != null){
					if("0".equals(order.getCheckState())){//未审核
						order.setCheckName("审核中...");
					}
				}
				//判断该项目是否写过日记
				Integer row = aBeautyDiaryDao.hasDiaryByOrderId(order.getId());
				if(row == 0){
					order.setHasDiary("0");
				}else{
					order.setHasDiary("1");
				}
			}
		}
		return orders == null ? new ArrayList<WOrder>():orders;
	}

	public WOrder getWOrderById(String orderId){
		return wOrderDao.getWOrderById(orderId);
	}

	/**
	 * 获取用户有多少条订单数量
	 * @param id
	 * @return
	 */
	public int getNumOfOrdersByUserId(String id) {
		int allPage;
		int index = wOrderDao.getNumOfOrdersByUserId(id);
		allPage=index/WeixinUtil.BeautyDiaryCount;
		if(index%WeixinUtil.BeautyDiaryCount!=0){
			allPage+=1;
		}
		return allPage;
	}

}
