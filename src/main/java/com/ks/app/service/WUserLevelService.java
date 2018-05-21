package com.ks.app.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ks.app.dao.AUserDao;
import com.ks.app.dao.WBrokerageDao;
import com.ks.app.dao.WOrderDao;
import com.ks.app.dao.WRewardDao;
import com.ks.app.dao.WUserLevelDao;
import com.ks.app.entity.AUser;
import com.ks.app.entity.WBrokerage;
import com.ks.app.entity.WOrder;
import com.ks.app.entity.WReward;
import com.ks.app.utils.WeixinUtil;
import com.ks.utils.Constant;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.service.SysConstantService;

@Service
@Transactional(rollbackFor=Exception.class)
public class WUserLevelService extends CrudService<WUserLevelDao, AUser>{
	@Autowired
	private AUserService aUserService;
	@Autowired
	private WUserLevelDao wUserLevelDao;
	@Autowired
	private WOrderDao wOrderDao;
	@Autowired
	private AUserDao aUserDao;
	@Autowired
	private WRewardDao wRewardDao;
	@Autowired
	private WBrokerageDao wBrokerageDao;
	@Autowired
	private SysConstantService sysConstantService;
	private SimpleDateFormat sdf1= new SimpleDateFormat("yyyy-MM-dd");
	private DecimalFormat df = new DecimalFormat("#.00");
	/**
	 * 10星团队类成员获取奖励金
	 * @param user 消费用户
	 * @param outTradeId 消费订单号
	 * @param group
	 * @param actualPay 参与奖励的金额
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public int disposeGroup(AUser user, String id, AUser[] group, BigDecimal actualPay) throws ParseException, IOException{
		String token = aUserService.getToken();
		String time = sdf1.format(new Date());
		//团队为空则无人获得奖励金
		if(group == null){
			return 2;
		}
		WOrder order = wOrderDao.getOrderByOutTradeId(id);
//		BigDecimal price = order.getDealPrice();
		/*//初始奖励金份数
		int n = 10-1;
		//若无10星用户，则无需为其保留1份奖励
		if(group[10] == null){
			n = 10;
		}*/
		
		//未分配奖励金份数
		int n = 10;
		//已分配奖励金份数
		int m = 0;
		//每次循环中用户的星级
		int level;
		
		for(int i=0; i<group.length; i++){
			if(group[i] == null){
				continue;
			}
			level = Integer.parseInt(group[i].getUserLevel());//星级
			
			
			BigDecimal item_star = sysConstantService.getItemStar();
			
			//获取其佣金，累加，进入奖励金表reward_relation
			String userId = group[i].getId();
			AUser u = aUserDao.getUserById(userId);
			String openId = u.getOpenId();
			
			BigDecimal bd = new BigDecimal(level - m).multiply(actualPay).multiply(item_star);
			aUserDao.AddUpRewardMoney(userId,bd);
			String str = df.format(bd);
			WeixinUtil.sendTextMessageToUser("返现到账通知！\n"+time+"\n\n恭喜你获得"+group[i].getUserLevel()+"星奖励!\n"+"订单:团队奖励\n"+"奖励金额:"+str+"元\n",openId, token);
			//入奖励金信息表
			WReward reward = new WReward();
			reward.setApp(true);
			reward.setCurUserId(Constant.MANAGER_ID);
			reward.preInsert();
			reward.preUpdate();
			reward.setOrderId(order.getId());
			reward.setRewardType("2");
			reward.setUserId(group[i].getId());
			reward.setUserReward(new BigDecimal(level - m).multiply(actualPay).multiply(item_star));
			reward.setUserStar(Integer.parseInt(group[i].getUserLevel()));
			wRewardDao.insertReward(reward);
			//入佣金信息表
			WBrokerage brokerage = new WBrokerage();
			brokerage.setMoney(new BigDecimal(level - m).multiply(actualPay).multiply(item_star));
			brokerage.setUserId(group[i].getId());
			brokerage.setStatus("1");
			brokerage.setOrderId(order.getId());
			brokerage.setOrderType("3");
			brokerage.setApp(true);
			brokerage.setCurUserId(Constant.MANAGER_ID);
			brokerage.preInsert();
			brokerage.preUpdate();
			wBrokerageDao.insert(brokerage);
			
			m = level;//已分配的=星级
			if(m == 0){//第一次分奖励
				n = n - level;
			}else{
				if(n >= level){
					n = n - level + m;//未分配的=未分配-星级
				}else{
					n = level - m;
				}
			}
			
			/*if(n>=level && i!=10){
				//佣金累加
				BigDecimal bd = new BigDecimal(level).multiply(actualPay).multiply(item_star);
				aUserDao.AddUpRewardMoney(userId,bd);
				String str = df.format(bd);
				WeixinUtil.sendTextMessageToUser("返现到账通知！\n"+time+"\n\n恭喜你获得"+group[i].getUserLevel()+"星奖励!\n"+"订单:团队奖励\n"+"奖励金额:"+str+"元\n",openId, token);
				//入奖励金信息表
				WReward reward = new WReward();
				reward.setApp(true);
				reward.setCurUserId(Constant.MANAGER_ID);
				reward.preInsert();
				reward.preUpdate();
				reward.setOrderId(order.getId());
				reward.setRewardType("2");
				reward.setUserId(group[i].getId());
				reward.setUserReward(new BigDecimal(level).multiply(actualPay).multiply(item_star));
				reward.setUserStar(Integer.parseInt(group[i].getUserLevel()));
				wRewardDao.insertReward(reward);
				//入佣金信息表
				WBrokerage brokerage = new WBrokerage();
				brokerage.setMoney(new BigDecimal(level).multiply(actualPay).multiply(item_star));
				brokerage.setUserId(group[i].getId());
				brokerage.setStatus("1");
				brokerage.setOrderId(order.getId());
				brokerage.setOrderType("3");
				brokerage.setApp(true);
				brokerage.setCurUserId(Constant.MANAGER_ID);
				brokerage.preInsert();
				brokerage.preUpdate();
				wBrokerageDao.insert(brokerage);
				n = n-level;
			}else if(n<level && i!=10){
				//佣金累加
				BigDecimal bd = new BigDecimal(n).multiply(actualPay).multiply(item_star);
				aUserDao.AddUpRewardMoney(userId,bd);
				WeixinUtil.sendTextMessageToUser("返现到账通知！\n"+time+"\n\n恭喜你获得"+group[i].getUserLevel()+"星奖励!\n"+"订单:团队奖励\n"+"奖励金额:"+bd.toString()+"元\n",openId, token);
				//入奖励金信息表
				WReward reward = new WReward();
				reward.setApp(true);
				reward.setCurUserId(Constant.MANAGER_ID);
				reward.preInsert();
				reward.preUpdate();
				reward.setOrderId(order.getId());
				reward.setRewardType("2");
				reward.setUserId(group[i].getId());
				reward.setUserReward(new BigDecimal(n).multiply(actualPay).multiply(item_star));
				reward.setUserStar(Integer.parseInt(group[i].getUserLevel()));
				wRewardDao.insertReward(reward);
				//入佣金信息表
				WBrokerage brokerage = new WBrokerage();
				brokerage.setMoney(new BigDecimal(n).multiply(actualPay).multiply(item_star));
				brokerage.setUserId(group[i].getId());
				brokerage.setStatus("1");
				brokerage.setOrderId(order.getId());
				brokerage.setOrderType("3");
				brokerage.setApp(true);
				brokerage.setCurUserId(Constant.MANAGER_ID);
				brokerage.preInsert();
				brokerage.preUpdate();
				wBrokerageDao.insert(brokerage);
				n = 0;
			}else if(n>=0 && i==10){
				if(group[10] == null){
					break;
				}
				//佣金累加
				BigDecimal bd = new BigDecimal(n+1).multiply(actualPay).multiply(item_star);
				aUserDao.AddUpRewardMoney(userId,bd);
				WeixinUtil.sendTextMessageToUser("返现到账通知！\n"+time+"\n\n恭喜你获得"+group[i].getUserLevel()+"星奖励!\n"+"订单:团队奖励\n"+"奖励金额:"+bd.toString()+"元\n",openId, token);
				//入奖励金信息表
				WReward reward = new WReward();
				reward.setApp(true);
				reward.setCurUserId(Constant.MANAGER_ID);
				reward.preInsert();
				reward.preUpdate();
				reward.setOrderId(order.getId());
				reward.setRewardType("2");
				reward.setUserId(group[i].getId());
				reward.setUserReward(new BigDecimal(n+1).multiply(actualPay).multiply(item_star));
				reward.setUserStar(Integer.parseInt(group[i].getUserLevel()));
				wRewardDao.insertReward(reward);
				//入佣金信息表
				WBrokerage brokerage = new WBrokerage();
				brokerage.setMoney(new BigDecimal(n+1).multiply(actualPay).multiply(item_star));
				brokerage.setUserId(group[i].getId());
				brokerage.setStatus("1");
				brokerage.setOrderId(order.getId());
				brokerage.setOrderType("3");
				brokerage.setApp(true);
				brokerage.setCurUserId(Constant.MANAGER_ID);
				brokerage.preInsert();
				brokerage.preUpdate();
				wBrokerageDao.insert(brokerage);
			}else{
				break;
			}*/
		}
		return 1;
	}
	
	/**
	 * 根据用户id获取其10星团队成员(含本人)
	 * @param userId
	 * @return
	 */
	public AUser[] getGroup(String userId) {
		//定义长度为11的容器
		AUser[] group = new AUser[11];
		//获取本人，根据其星级，将其放入容器
		AUser userStart = wUserLevelDao.getUserById(userId);
		if(userStart == null || StringUtils.isBlank(userStart.getUserLevel())){
			return group;
		}
		int start = Integer.parseInt(userStart.getUserLevel());
		group[start] = userStart;
		//若其为10星，则其1个人获得全部奖励金
		if(start == 10){
			return group;
		}
		
		//获取用户的上级，根据其星级，将其放入容器，没有上级则返回group
		if(StringUtils.isBlank(userStart.getParentId())){
			return group;
		}
		AUser pUser = wUserLevelDao.getUserById(userStart.getParentId());
		if(pUser == null){//若根据父id查不到用户则没有上级
			return group;
		}
		
		//星级缓存
		int LevelIndex = start;
		//上级id缓存
		String parentIdIndex = userStart.getParentId();
		
		//获取团队内其他成员
		do{
			AUser u = wUserLevelDao.getUserById(parentIdIndex);
			if(StringUtils.isBlank(u.getParentId()) || wUserLevelDao.getUserById(u.getParentId())==null){
				//u没有上级，但其本身要加入group，再return
				//若没有上级，则判断其星级，是否加入团队
				if(Integer.parseInt(u.getUserLevel()) > LevelIndex){
					LevelIndex = Integer.parseInt(u.getUserLevel());
					group[LevelIndex] = u;
					return group;
				}else{
					return group;
				}
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
		}while(LevelIndex < 10);
		return group;
	}
	
	/**
	 * 根据用户id获取其星级
	 * @param userId
	 * @return
	 */
	public String getLevelByUserId(String userId){
		return wUserLevelDao.getUserById(userId).getUserLevel();
	}

	
}
