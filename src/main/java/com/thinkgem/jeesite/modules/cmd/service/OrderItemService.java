package com.thinkgem.jeesite.modules.cmd.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.cmd.dao.OrderItemDao;
import com.thinkgem.jeesite.modules.cmd.entity.OrderItem;
import com.thinkgem.jeesite.modules.cmd.entity.Reward;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.User;

@Service
@Transactional
public class OrderItemService extends CrudService<OrderItemDao, OrderItem>{
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private OrderItemDao orderItemDao;
	
	public Page<OrderItem> findPage(Page<OrderItem> page, OrderItem orderItem) {
		if(orderItem.getId() != null){//若传入了orderId则只查询该条记录
			OrderItem order = orderItemDao.get(orderItem.getId());
			List<OrderItem> list = new ArrayList<OrderItem>();
			list.add(order);
			page.setList(list);
			return page;
		}
		Page<OrderItem> pageItem = super.findPage(page, orderItem);
		List<OrderItem> list = pageItem.getList();
		for(OrderItem oi : list){
			User buyUser = userDao.get(oi.getBuyId());
			if(buyUser != null){
				oi.setBuyName(buyUser.getName());
			}
			if(StringUtils.isNotBlank(oi.getChangePriceId())){
				User changeUser = userDao.get(oi.getChangePriceId());
				if(changeUser != null){
					oi.setChangePriceName(changeUser.getName());
				}
			}
		}
		return pageItem;
	}

	public List<Reward> getRewardByOrderId(String id) {
		List<Reward> reward = dao.getRewardByOrderId(id);
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

	public OrderItem getOrder(String id) {
		OrderItem orderItem = super.get(id);
		if(orderItem != null){
			User user = userDao.get(orderItem.getBuyId());
			if(user != null){
				orderItem.setBuyName(user.getName());
			}
		}
		return orderItem == null ? new OrderItem():orderItem;
	}
	
}
