package com.thinkgem.jeesite.modules.cmd.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.cmd.dao.OrderComboDao;
import com.thinkgem.jeesite.modules.cmd.entity.OrderCombo;
import com.thinkgem.jeesite.modules.cmd.entity.Reward;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.User;

@Service
@Transactional
public class OrderComboService extends CrudService<OrderComboDao, OrderCombo>{
	
	@Autowired
	private OrderComboDao orderComboDao;
	@Autowired
	private UserDao userDao;

	/**
	 * 根据用户id查订单
	 * @param userId
	 * @return
	 */
//	public List<WOrder> getOrdersByUserId(String userId) {
//		List<WOrder> orders = wOrderDao.getOrdersByUserId(userId);
//		return orders;
//	}

	public Page<OrderCombo> findPage(Page<OrderCombo> page, OrderCombo orderCombo) {
		if(orderCombo.getId() != null){//若传入了orderId则只查询该条记录
			OrderCombo order = orderComboDao.get(orderCombo.getId());
			List<OrderCombo> list = new ArrayList<OrderCombo>();
			list.add(order);
			page.setList(list);
			return page;
		}
		Page<OrderCombo> pageCombo = super.findPage(page, orderCombo);
		List<OrderCombo> list = pageCombo.getList();
		for(OrderCombo oc : list){
			User buyUser = userDao.get(oc.getBuyId());
			if(buyUser != null)
				oc.setBuyName(buyUser.getName());
		}
		return pageCombo;
	}

	public List<Reward> getRewardByOrderId(String orderId) {
		List<Reward> reward = orderComboDao.getRewardByOrderId(orderId);
		if(reward != null && !reward.isEmpty()){
			for(Reward r : reward){
				User user = userDao.get(r.getUserId());
				if(user != null){
					r.setUserName(user.getName());
				}
			}
		}
		return reward == null ? new ArrayList<Reward>():reward;
	}

	public OrderCombo getOrder(String orderId) {
		OrderCombo order = super.get(orderId);
		if(order != null){
			User user = userDao.get(order.getBuyId());
			if(user != null){
				order.setBuyName(user.getName());
			}
		}
		return order == null ? new OrderCombo():order;
	}
	
}
