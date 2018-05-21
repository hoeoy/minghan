package com.thinkgem.jeesite.modules.cmd.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ks.app.dao.AUserDao;
import com.ks.app.dao.WOrderDao;
import com.ks.app.dao.WRewardDao;
import com.ks.app.dao.WUserLevelDao;
import com.ks.app.entity.AUser;
import com.ks.app.entity.WOrder;
import com.ks.app.entity.WReward;
import com.ks.utils.Constant;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.cmd.dao.UserLevelDao;
import com.thinkgem.jeesite.modules.sys.entity.User;

@Service
@Transactional(rollbackFor=Exception.class)
public class UserLevelService extends CrudService<UserLevelDao, User>{

	@Autowired
	private UserLevelDao userLevelDao;
	@Autowired
	private WOrderDao wOrderDao;
	@Autowired
	private AUserDao aUserDao;
	@Autowired
	private WRewardDao wRewardDao;
	
	private static final BigDecimal ZERO = new BigDecimal(0);
	
	/**
	 * 6星团队类成员获取奖励金
	 * @param user 消费用户
	 * @param outTradeId 消费订单号
	 * @param group
	 * @return
	 */
	/*public int disposeGroup(AUser user, String outTradeId, AUser[] group){
		//团队为空则无人获得奖励金
		if(group == null){
			return 2;
		}
		WOrder order = wOrderDao.getOrderByOutTradeId(outTradeId);
		BigDecimal price = order.getDealPrice();
		//初始奖励金点数
		int n = 6-1;
		//若无6星用户，则无需为其保留1%奖励
		if(group[6] == null){
			n = 6;
		}
		for(int i=0; i<group.length; i++){
			if(group[i] == null){
				continue;
			}
			//星级
			int level = Integer.parseInt(group[i].getUserLevel());
			//获取其佣金，累加，进入奖励金表reward_relation
			if(n>=level && i!=6){
				aUserDao.AddUpRewardMoney(group[i].getId(), new BigDecimal(level*0.01).multiply(price));
				WReward reward = new WReward();
				reward.setApp(true);
				reward.setCurUserId(Constant.MANAGER_ID);
				reward.preInsert();
				reward.preUpdate();
				reward.setOrderId(order.getId());
				reward.setRewardType("2");
				reward.setParentId(null);
				reward.setParentReward(ZERO);
				reward.setGrandId(null);
				reward.setGrandReward(ZERO);
				reward.setLevelReward(new BigDecimal(level*0.01).multiply(price));
				wRewardDao.insertReward(reward);
				n = n-level;
			}else if(n<level && i!=6){
				aUserDao.AddUpRewardMoney(group[i].getId(), new BigDecimal(n*0.01).multiply(price));
				WReward reward = new WReward();
				reward.setApp(true);
				reward.setCurUserId(Constant.MANAGER_ID);
				reward.preInsert();
				reward.preUpdate();
				reward.setOrderId(order.getId());
				reward.setRewardType("2");
				reward.setParentId(null);
				reward.setParentReward(ZERO);
				reward.setGrandId(null);
				reward.setGrandReward(ZERO);
				reward.setLevelReward(new BigDecimal(n*0.01).multiply(price));
				wRewardDao.insertReward(reward);
				n = 0;
			}else if(n>=0 && i==6){
				if(group[6] == null){
					break;
				}
				aUserDao.AddUpRewardMoney(group[i].getId(), new BigDecimal((n+1)*0.01).multiply(price));
				WReward reward = new WReward();
				reward.setApp(true);
				reward.setCurUserId(Constant.MANAGER_ID);
				reward.preInsert();
				reward.preUpdate();
				reward.setOrderId(order.getId());
				reward.setRewardType("2");
				reward.setParentId(null);
				reward.setParentReward(ZERO);
				reward.setGrandId(null);
				reward.setGrandReward(ZERO);
				reward.setLevelReward(new BigDecimal((n+1)*0.01).multiply(price));
				wRewardDao.insertReward(reward);
			}else{
				break;
			}
		}
		return 1;
	}*/
	
	/**
	 * 根据用户id获取其6星团队成员(不含本人)
	 * @param userId
	 * @return
	 */
	/*public AUser[] getGroup(String userId) {
		//定义长度为7的容器
		AUser[] group = new AUser[7];
		//获取用户的上级，根据其星级，将其放入容器
		AUser user = userLevelDao.getUserById(userId);
		//没有上级则返回null，其团队为空，奖励金公司保留
		if(StringUtils.isBlank(user.getParentId())){
			return group;
		}
		AUser userStart = userLevelDao.getUserById(user.getParentId());
		int start = Integer.parseInt(userStart.getUserLevel());
		group[start] = userStart;
		//若其上级为6星，则其1个人获得全部奖励金
		if(start == 6){
			return group;
		}
		//如果user的上上级为空则只返回group，内只有1个元素
		if(StringUtils.isBlank(userStart.getParentId())){
			return group;
		}
		
		//星级缓存
		int LevelIndex = start;
		//上级id缓存
		String parentIdIndex = userStart.getParentId();
		
		//获取团队内其他成员
		do{
			AUser u = userLevelDao.getUserById(parentIdIndex);
			if(StringUtils.isBlank(u.getParentId())){
				return group;
			}
			parentIdIndex = u.getParentId();
			if(StringUtils.isBlank(u.getUserLevel())){
				continue;
			}
			//上级星级高于下级则放入容器
			if(Integer.parseInt(u.getUserLevel()) > LevelIndex){
				LevelIndex = Integer.parseInt(u.getUserLevel());
				group[LevelIndex] = u;
			} 
		}while(LevelIndex < 6);
		return group;
	}*/
	
	/**
	 * 根据用户id获取其星级
	 * @param userId
	 * @return
	 */
	public String getLevelByUserId(String userId){
		return userLevelDao.getUserById(userId).getUserLevel();
	}

	
}
