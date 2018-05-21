package com.thinkgem.jeesite.modules.sys.service;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.cmd.dao.ComboDao;
import com.thinkgem.jeesite.modules.cmd.dao.ItemDao;
import com.thinkgem.jeesite.modules.cmd.dao.RewardDao;
import com.thinkgem.jeesite.modules.cmd.entity.Combo;
import com.thinkgem.jeesite.modules.cmd.entity.Item;
import com.thinkgem.jeesite.modules.cmd.entity.Reward;
import com.thinkgem.jeesite.modules.sys.dao.BrokerageDao;
import com.thinkgem.jeesite.modules.sys.dao.OrderDao;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.Brokerage;
import com.thinkgem.jeesite.modules.sys.entity.Order;
import com.thinkgem.jeesite.modules.sys.entity.User;

@Service
@Transactional
public class UserBrokerageService extends CrudService<BrokerageDao, Brokerage> {

	@Autowired
	private UserDao userDao;
	@Autowired
	private RewardDao rewardDao;
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private ItemDao itemDao;
	@Autowired
	private ComboDao comboDao;
	
	public Page<Brokerage> getBrokerageList(Page<Brokerage> page, Brokerage brokerage){
		Page<Brokerage> brokeragePage = super.findPage(page, brokerage);
		List<Brokerage> list = brokeragePage.getList();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		for(Brokerage b : list){
			//获取受益人名
			User user = userDao.get(b.getUserId());
			if(user != null){
				if(StringUtils.isNotBlank(user.getName())){
					b.setUserName(user.getName());
				}
			}
			//时间
			String format = sdf.format(b.getUpdateDate());
			b.setTime(format);
			
			if("1".equals(b.getStatus())){//0提现 1奖励
				Order order = orderDao.get(b.getOrderId());
				if(order != null && StringUtils.isNotBlank(order.getBuyId())){
					//贡献人名
					User buyUser = userDao.get(order.getBuyId());
					if(buyUser != null && StringUtils.isNotBlank(buyUser.getName())){
						b.setFromUserName(buyUser.getName());
					}
					//购买商品
					if("2".equals(order.getGoodsType())){//商品类型：1套餐 2项目
						Item item = itemDao.get(order.getGoodsId());
						if(item != null && StringUtils.isNotBlank(item.getName())){
							b.setOrderName(item.getName());
						}
					}else if("1".equals(order.getGoodsType())){
						Combo combo = comboDao.get(order.getGoodsId());
						b.setOrderName(combo.getName());
					}
				}
				
				//奖励类型
				Reward reward = new Reward();
				reward.setOrderId(b.getOrderId());
				reward.setUserId(b.getUserId());
				reward.setUserReward(b.getMoney());
//				reward = rewardDao.get(reward);
				List<Reward> rewards = rewardDao.findList(reward);
				if(rewards != null && rewards.size() > 0){
					if(rewards.size() == 1){
						reward = rewards.get(0);
						if(reward != null && StringUtils.isNotBlank(reward.getRewardType())){
							String type = reward.getRewardType();//奖励类型 0一级分销 1二级分销 2六星玩法 3购买套餐上级返10%
							if("0".equals(type) || "3".equals(type)){
								b.setRewardType("一级分销");
							}else if("1".equals(type)){
								b.setRewardType("二级分销");
							}else if("2".equals(type)){
								b.setRewardType("团队奖励");
							}
						}
						//受益人星级
						if(reward != null && reward.getUserStar() >= 0){
							b.setStar(reward.getUserStar());
						}
					}else{
						
					}
				}
				
			}
		}
		return brokeragePage;
	}

}
