package com.ks.app.service;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.ks.app.dao.AMOrderDao;
import com.ks.app.dao.AMUserDao;
import com.ks.app.entity.AMBrokerage;
import com.ks.app.entity.AMGoods;
import com.ks.app.entity.AMOrder;
import com.ks.app.entity.AMRecord;
import com.ks.app.entity.AMUser;
import com.ks.app.utils.WeixinUtil;
import com.ks.utils.EnumConstant.MBrokerageType;
import com.ks.utils.EnumConstant.OrderState;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.service.SysConstantService;
@Service
@Transactional
public class AMOrderService extends CrudService<AMOrderDao, AMOrder>{
	@Autowired
	private AMGoodsService amGoodsService;
	@Autowired
	private AMUserDao amUserDao;
	@Autowired
	private AMBrokerageService amBrokerageService;
	@Autowired
	private SysConstantService sysConstantService;
	@Autowired
	private AMOrderDao aMOrderDao;
	@Autowired
	private AMRecordService amRecordService;
	
	public List<AMOrder> getZGOrder(String userId) {
		List<AMOrder> list = dao.getZGOrder(userId);
		if(list==null){
			list=Lists.newArrayList();
		}
		return list;
	}

	public AMOrder getByOrderNo(String orderNo) {
		AMOrder order = dao.getByOrderNo(orderNo);
		return order;
	}

	public List<AMOrder> getOrderList(AMOrder order,int index) {
		int n = (index-1)*10;
		int s = WeixinUtil.OrderCount;
		String orderState = order.getOrderState();
		String userId = order.getUserId();
		List<AMOrder> list = dao.getOrderList(n,s,orderState,userId);
		if(list==null){
			list = Lists.newArrayList();
		}else{
			for(int i = 0;i<list.size();i++){
				AMGoods goods = amGoodsService.get(list.get(i).getGoodsId());
				if(goods!=null){
					list.get(i).setGoodsLogo(goods.getLogo());
					list.get(i).setGoodsAllPrice(list.get(i).getGoodsPrice());
					list.get(i).setGoodsPrice(goods.getPrice());
					list.get(i).setGoodsMarket(goods.getMarket());
				}
				list.get(i).setOrderState(OrderState.orderState(list.get(i).getOrderState()));
			}
		}
		return list;
	}
    public int getAllPage(String orderState){
    	int allPage;
		int index =aMOrderDao.getAllPage(orderState);
		allPage=index/WeixinUtil.OrderCount;
		if(index%WeixinUtil.OrderCount!=0){
			allPage+=1;
		}
		return allPage;
    }
	public void exitOrder(String id) {
		dao.exitOrder(id);
	}

	public String getState(String id) {
		return dao.getState(id);
	}

	public void shouhuo(String id,HttpSession session) {
		AMOrder order = dao.get(id);
		dao.shouhuo(id);
		amRecordService.add(order);
	}
	public void fahuo(String id,HttpSession session){
		AMOrder order = dao.get(id);
		int index = order.getDiscountsNum();
		if(index>0){
		String userId = order.getUserId();
		AMUser user = amUserDao.get(userId);
		if(user!=null){
			String parendId = user.getParentId();
			if(StringUtils.isNoneBlank(parendId)){
				String mSub = sysConstantService.getCustomGoodsSub();
				AMUser u = amUserDao.get(parendId);
				BigDecimal bd = u.getmRewardMoney();
				bd = bd.add(new BigDecimal(mSub).multiply(new BigDecimal(index)));
				u.setmRewardMoney(bd);
				amUserDao.allterRM(u);
				AMBrokerage amb = new AMBrokerage();
				AMGoods goods = amGoodsService.get(order.getGoodsId());
				amb.setConsumerId(user.getId());
				amb.setUserId(u.getId());
				amb.setType(MBrokerageType.THREE.getKey());
				amb.setGoodsName(goods.getName());
				amb.setGoodsPrice(order.getGoodsPrice());
				amb.setGoodsId(goods.getId());
				amb.setMoney(new BigDecimal(mSub).multiply(new BigDecimal(index)));
				amBrokerageService.save(amb);
			}
		}
		}
	}


}
