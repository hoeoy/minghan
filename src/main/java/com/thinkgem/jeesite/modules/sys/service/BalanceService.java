package com.thinkgem.jeesite.modules.sys.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.ks.app.dao.AUserDao;
import com.ks.app.service.WUserLevelService;
import com.ks.app.utils.WeixinUtil;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.cmd.dao.OrderComboDao;
import com.thinkgem.jeesite.modules.cmd.dao.OrderItemDao;
import com.thinkgem.jeesite.modules.cmd.dao.RewardDao;
import com.thinkgem.jeesite.modules.cmd.entity.OrderCombo;
import com.thinkgem.jeesite.modules.sys.dao.BalanceDao;
import com.thinkgem.jeesite.modules.sys.dao.BrokerageDao;
import com.thinkgem.jeesite.modules.sys.dao.MonthBackDao;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.Balance;
import com.thinkgem.jeesite.modules.sys.entity.Brokerage;
import com.thinkgem.jeesite.modules.sys.entity.User;

@Service
@Transactional
public class BalanceService extends CrudService<BalanceDao,Balance>{
	
	@Autowired
	private BalanceDao balancedao;
	@Autowired
	private OrderComboDao orderComboDao;
	@Autowired
	private OrderItemDao orderItemDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private AUserDao aUserDao;
	@Autowired
	private BrokerageDao brokerageDao;
	@Autowired
	private BalanceDao balanceDao;
	@Autowired
	private RewardDao rewardDao;
	@Autowired
	private MonthBackDao monthBackDao;
	@Autowired
	private WUserLevelService wUserLevelService;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public List<Balance> getBalanceByUser(String id,int index) {
		int n = (index-1)*10;
		int s = WeixinUtil.BeautyDiaryCount;
		List <Balance> list = balancedao.getBalanceByUser(id,n,s);
		if(list==null){
			list = Lists.newArrayList();
		}
		 for(int i =0;i<list.size();i++){
	        	list.get(i).setTime(sdf.format(list.get(i).getCreateDate()));
	        }
		return list;
	}

	public int getBDPage(String id) {
		int allPage;
		int index =balancedao.getBDPage(id);
		allPage=index/WeixinUtil.BeautyDiaryCount;
		if(index%WeixinUtil.BeautyDiaryCount!=0){
			allPage+=1;
		}
		return allPage;
	}

	public String getOrderNameById(String orderId) {
		String str = balancedao.getOrderNameById(orderId);
		return str;
	}

	public void transactionDelete(String id) {
		Balance balance = balanceDao.get(id);
		String indFlag = balance.getIndFlag();
		if("0".equals(indFlag)){//消费
			String orderType = balance.getOrderType();
			String orderId = balance.getOrderId();
			
			if("0".equals(orderType)){//0套餐
				OrderCombo orderCombo = orderComboDao.get(orderId);
				BigDecimal chargeBalance = orderCombo.getChargeBalance();//实际消费余额
				//回退自己余额
				String buyUserId = orderCombo.getBuyId();
				User user = userDao.get(buyUserId);
				/*BigDecimal accountMoney = user.getAccountMoney();
				accountMoney = accountMoney.add(chargeBalance);
				user.setAccountMoney(accountMoney);
				userDao.updateAccountMoney(user);*/
				//回退上级佣金
				String parentId = user.getParentId();
				if(StringUtils.isNotBlank(parentId)){
					User parentUser = userDao.get(parentId);
					if(parentUser != null){
						Brokerage brokerage = new Brokerage();
						brokerage.setOrderId(orderId);
						brokerage.setOrderType("1");//1买套餐
						brokerage.setUserId(parentId);
						brokerage = brokerageDao.get(brokerage);
						BigDecimal money = brokerage.getMoney();
						BigDecimal rewardMoney = parentUser.getRewardMoney();
						rewardMoney = rewardMoney.subtract(money);
						parentUser.setRewardMoney(rewardMoney);
//						userDao.updateRewardMoney(parentUser);
						//获得佣金记录删除
//						brokerageDao.deleteBrokerage(brokerage.getId());
						//奖励金记录删除
//						rewardDao.deleteRewardByOrderId(orderId, "3");//3购买套餐上级返10%
					}
				}
				//12月返现记录删除
//				monthBackDao.deleteMonthBackByOrderId(orderId);
				//订单删除
				orderComboDao.deleteOrderById(orderId);
				//余额记录删除
//				balanceDao.deleteBalanceById(id);
			}else if("1".equals(orderType)){//1项目
				/*OrderItem orderItem = orderItemDao.get(orderId);
				BigDecimal chargeBalance = orderItem.getChargeBalance();//实际消费余额
				//回退自己余额
				String buyUserId = orderItem.getBuyId();
				User user = userDao.get(buyUserId);
				BigDecimal accountMoney = user.getAccountMoney();
				accountMoney = accountMoney.add(chargeBalance);
				user.setAccountMoney(accountMoney);
				userDao.update(user);*/
				//订单备注
				//orderItemDao.text("locked", orderItem.getId());
				orderItemDao.deleteOrderById(orderId);
			}
			
		}else if("1".equals(indFlag)){//充值
			//扣掉充值的余额
			String userId = balance.getUserId();
			User user = userDao.get(userId);
			BigDecimal bd = balance.getMoney();
			bd = user.getAccountMoney().subtract(bd);
			user.setAccountMoney(bd);
			userDao.updateAccountMoney(user);
			//充值余额记录删除
			balanceDao.deleteBalanceById(id);
		}
	}
	
}
