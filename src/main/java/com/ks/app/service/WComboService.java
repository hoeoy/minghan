package com.ks.app.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ks.app.dao.AUserDao;
import com.ks.app.dao.WBalanceDao;
import com.ks.app.dao.WBrokerageDao;
import com.ks.app.dao.WComboDao;
import com.ks.app.dao.WMonthBackDao;
import com.ks.app.dao.WOrderDao;
import com.ks.app.dao.WRewardDao;
import com.ks.app.dao.WScoreDao;
import com.ks.app.entity.AUser;
import com.ks.app.entity.WBalance;
import com.ks.app.entity.WBrokerage;
import com.ks.app.entity.WCombo;
import com.ks.app.entity.WComboContract;
import com.ks.app.entity.WComboImage;
import com.ks.app.entity.WMonthBack;
import com.ks.app.entity.WOrder;
import com.ks.app.entity.WReward;
import com.ks.app.entity.WScore;
import com.ks.app.utils.WeixinUtil;
import com.ks.utils.Constant;
import com.ks.utils.OrderCodeUtil;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.service.SysConstantService;

@Service
@Transactional(rollbackFor=Exception.class)
public class WComboService extends CrudService<WComboDao, WCombo>{

	@Autowired
	private WComboDao wComboDao;
	@Autowired
	private AUserDao aUserDao;
	@Autowired
	private WOrderDao wOrderDao;
	@Autowired
	private WRewardDao wRewardDao;
	@Autowired
	private WMonthBackDao wMonthBackDao;
	@Autowired
	private WScoreDao wScoreDao;
	@Autowired
	private WBalanceDao wBalanceDao;
	@Autowired
	private WBrokerageDao wBrokerageDao;
	@Autowired
	private AUserService aUserService;
	@Autowired
	private SysConstantService sysConstantService;
	@Autowired
	private WUserLevelService wUserLevelService;
	private SimpleDateFormat sdf1= new SimpleDateFormat("yyyy-MM-dd");
	private DecimalFormat df = new DecimalFormat("#.00");
	private static final BigDecimal TWELVE = new BigDecimal(12);
	
	//商品介绍单独一个接口
	private String css = "<style type='text/css'>p{margin:0px;}</style>";
	public String changePathImg(String content,String prePath,String contextPath) {   
		String imgReg = "<img[^>]*src=['\"]([^'\"]+)[^>]*>";  
		Pattern imgPattern = Pattern.compile(imgReg);  
		Matcher m = imgPattern.matcher(content);   
		while (m.find()) {     
			String img = m.group();
			String src = img.replaceAll("<img[^>]*src=['\"]", "");
			src = src.replaceAll("['\"][^>]*>", "");   
			if(src.contains("https://")||src.contains("http://")){	
				
			}else {
				String newImg = img.replace(src, prePath+"/"+ src);  
				content = content.replace(img, newImg);
			}
		}
		return content;
	}

	/**
	 * 获取所有形象大使信息
	 * @return
	 */
	public List<WCombo> getComboList(String prePath,HttpServletRequest request) {
		List<WCombo> combos = wComboDao.getComboList();
		for(WCombo combo : combos){
			String description = changePathImg(css+StringEscapeUtils.unescapeHtml3(combo.getDescription()),prePath,request.getContextPath());
			combo.setDescription(description);
			List<WComboContract> contracts = wComboDao.getComboContratsByComboId(combo.getId());
			combo.setContracts(contracts);
		}
		return combos;
	}

	/**
	 * 获取套餐详细信息
	 * @param id
	 * @param request 
	 * @param prePath 
	 * @return
	 */
	public WCombo getComboById(String id, String prePath, HttpServletRequest request) {
		WCombo combo = wComboDao.getComboById(id);
		String description = changePathImg(css+StringEscapeUtils.unescapeHtml3(combo.getDescription()),prePath,request.getContextPath());
		combo.setDescription(description);
		List<WComboContract> contracts = wComboDao.getComboContratsByComboId(combo.getId());
		combo.setContracts(contracts);
		List<WComboImage> images = wComboDao.getComboImagesByComboId(combo.getId());
		combo.setImages(images);
		return combo;
	}

	/**
	 * 获取套餐合同详细信息
	 * @param comboId
	 * @return
	 */
	public List<WComboContract> getComboContratsByComboId(String comboId) {
		return wComboDao.getComboContratsByComboId(comboId);
	}
	
	/**
	 * 购买套餐
	 * @param session
	 * @param comboId 套餐id
	 * @param userMoney 前端使用的余额
	 * @param score 前端使用的积分
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public int buyCombo(AUser user, String comboId) throws ParseException, IOException {
		String token = aUserService.getToken();
		String time = sdf1.format(new Date());
		WCombo combo = wComboDao.getComboById(comboId);
		/*String openId = (String)session.getAttribute("openId");
		//获取user
		AUser user = aUserDao.getUser(openId);*/
		String userId = user.getId();
		if(StringUtils.isBlank(user.getParentId())){
			return 5;
		}//没有上级
		AUser parentUser = aUserDao.getUserById(user.getParentId());
		if(parentUser == null){
			return 5;
		}
		if("0".equals(parentUser.getUserAmbassador()) && "0".equals(parentUser.getUserLevel())){
			return 6;//上级既不是形象大使也不是星级会员
		}
		//判断是否完善个人信息
		boolean userComplete = aUserService.userComplete(user.getId());
		if(userComplete == false){
			return 4;//个人信息不完善
		}
		//判断是否已经为形象大使
		if(!"0".equals(user.getUserAmbassador())){
			return 7;
		}
		//判断是否曾经购买过，限购一次
//		if(wOrderDao.hasCombo(userId) != null){
//			return 2;
//		}
		//判断用户余额是否足够
		BigDecimal accountMoney = user.getAccountMoney();
		BigDecimal currentPrice = combo.getCurrentPrice();
		//余额不足
		if(accountMoney.compareTo(currentPrice) == -1){
			return 3;
		}
		
		//下订单all_order_info
		WOrder order = new WOrder();
		String orderId = OrderCodeUtil.getNo(userId);
		order.setBuyId(userId);
		order.setGoodsId(comboId);
		order.setGoodsType("1");
		order.setGoodsName(combo.getName());
		order.setPayState("1");
		order.setCheckState("0");
		order.setDealPrice(currentPrice);
		order.setOrderPrice(currentPrice);
		order.setChangePriceId("");
		order.setChangePriceDate(null);
		order.setChargeScore(0L);
		order.setChargeBalance(currentPrice);
		order.setApp(true);
		order.setCurUserId(user.getId());
		order.preInsert();
		order.preUpdate();
		order.setId(orderId);
		wOrderDao.insertOrder(order);
		
		//扣除余额
		BigDecimal money = accountMoney.subtract(currentPrice);
		aUserDao.updateAccountMoney(userId, money);
		//入余额信息表
		WBalance balance = new WBalance();
		balance.setUserId(userId);
		balance.setIndFlag("0");
		balance.setMoney(currentPrice);
		balance.setOrderId(orderId);
		balance.setOrderType("0");
		balance.setApp(true);
		balance.setCurUserId(Constant.MANAGER_ID);
		balance.preInsert();
		balance.preUpdate();
		wBalanceDao.recharge(balance);
		//增加积分
//		aUserDao.updateScore(userId, combo.getCurrentPrice().longValue() + user.getPoint());
		//入积分信息表
		/*WScore s = new WScore();
		s.setPoint(combo.getCurrentPrice().longValue());
		s.setUserId(userId);
		s.setStatus("1");
		s.setOrderId(orderId);
		s.setOrderType("0");
		s.setApp(true);
		s.setCurUserId(Constant.MANAGER_ID);
		s.preInsert();
		s.preUpdate();
		wScoreDao.insert(s);*/
		
		BigDecimal combo_level_one = sysConstantService.getComboLevelOne();
		
		//上级奖励10%，无上级则不update
		if(user.getParentId() != null){
			//进入奖励金表reward_relation
			WReward reward = new WReward();
			reward.setApp(true);
			reward.setCurUserId(Constant.MANAGER_ID);
			reward.preInsert();
			reward.preUpdate();
			reward.setOrderId(orderId);
			reward.setRewardType("3");
			reward.setUserId(user.getParentId());//上级获得奖励
			reward.setUserReward(order.getDealPrice().multiply(combo_level_one));
			wRewardDao.insertReward(reward);
			//入佣金信息表
			WBrokerage brokerage = new WBrokerage();
			brokerage.setMoney(order.getDealPrice().multiply(combo_level_one));
			brokerage.setUserId(user.getParentId());
			brokerage.setStatus("1");
			brokerage.setOrderId(orderId);
			brokerage.setOrderType("1");
			brokerage.setApp(true);
			brokerage.setCurUserId(Constant.MANAGER_ID);
			brokerage.preInsert();
			brokerage.preUpdate();
			wBrokerageDao.insert(brokerage);
			//用户佣金增加
			String id = user.getParentId();
			AUser u = aUserService.getUserById(id);
			String openId = u.getOpenId();
			aUserDao.AddUpRewardMoney(id, order.getDealPrice().multiply(combo_level_one));
			String str = df.format(order.getDealPrice().multiply(combo_level_one));
			WeixinUtil.sendTextMessageToUser("返现到账通知！\n"+time+"\n\n恭喜你获得推荐奖励!\n"+"奖励金额:"+str+"元\n"+"消费项目:"+order.getGoodsName(),openId, token);
		}
		
		//进入返现表month_back_info
		int flag = 1;
		Calendar calendar = Calendar.getInstance();
		Date date = new Date();
		do{
			WMonthBack monthBack = new WMonthBack();
			monthBack.setApp(true);
			monthBack.setCurUserId(Constant.MANAGER_ID);
			monthBack.preInsert();
			monthBack.preUpdate();
			monthBack.setUserId(userId);
			monthBack.setComboId(comboId);
			monthBack.setOrderId(orderId);
			monthBack.setStage(flag);
			monthBack.setMoney(order.getDealPrice().divide(TWELVE, 2, BigDecimal.ROUND_HALF_DOWN));
			monthBack.setState("0");//返现标记 0未领取 1已领取
			monthBack.setAuditFlag("0");//审核状态0未开始，1待审核，2已通过
			if(flag >1){
				monthBack.setStartTime(calendar.getTime());
				calendar.add(Calendar.MONTH, 1);
				monthBack.setAutoTime(calendar.getTime());
			}else{
				monthBack.setStartTime(date);
				calendar.add(Calendar.MONTH, 1);
				monthBack.setAutoTime(calendar.getTime());
			}
			wMonthBackDao.insertMonthBack(monthBack);
			flag++;
		}while(flag <= 12);
		
		//销售次数+1
		wComboDao.count(comboId);
		//用户级别变更(形象大使)
		if("1".equals(order.getGoodsId())){
			aUserDao.updateUserAmbassador(userId, "1");
		}else if("2".equals(order.getGoodsId())){
			aUserDao.updateUserAmbassador(userId, "2");
		}else if("3".equals(order.getGoodsId())){
			aUserDao.updateUserAmbassador(userId, "3");
		}else if("4".equals(order.getGoodsId())){
			aUserDao.updateUserAmbassador(userId, "4");
		}
		//aUserDao.updateUserLevel(userId, order.getGoodsId());
		AUser[] group = wUserLevelService.getGroup(userId);
		disposeGroup(user, orderId, group, order.getChargeBalance());
		return 1;
	}

	/**
	 * 查询套餐是否有合同
	 * @param id
	 * @return
	 */
	public int hasContracts(String id) {
		List<WComboContract> contracts = wComboDao.getComboContratsByComboId(id);
		if(contracts == null || contracts.size() < 1){
			return 0;//无合同
		}
		return 1;//有合同
	}
	
	
	
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
			//星级
			level = Integer.parseInt(group[i].getUserLevel());
			
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
	
	
}
