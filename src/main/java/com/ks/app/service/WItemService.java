package com.ks.app.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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

import com.google.common.collect.Lists;
import com.ks.app.dao.AUserDao;
import com.ks.app.dao.WBalanceDao;
import com.ks.app.dao.WBrokerageDao;
import com.ks.app.dao.WItemDao;
import com.ks.app.dao.WOrderDao;
import com.ks.app.dao.WRewardDao;
import com.ks.app.dao.WScoreDao;
import com.ks.app.entity.AUser;
import com.ks.app.entity.WBalance;
import com.ks.app.entity.WBrokerage;
import com.ks.app.entity.WItem;
import com.ks.app.entity.WItemImage;
import com.ks.app.entity.WOrder;
import com.ks.app.entity.WReward;
import com.ks.app.entity.WScore;
import com.ks.app.utils.WeixinUtil;
import com.ks.utils.Constant;
import com.ks.utils.EnumConstant.UserAmbassador;
import com.ks.utils.EnumConstant.UserType;
import com.ks.utils.OrderCodeUtil;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.service.SysConstantService;

@Service
@Transactional(rollbackFor=Exception.class)
public class WItemService extends CrudService<WItemDao,WItem>{

	@Autowired
	private AUserService aUserService;
	@Autowired
	private WItemDao wItemDao;
	@Autowired
	private AUserDao aUserDao;
	@Autowired
	private WOrderDao wOrderDao;
	@Autowired
	private WRewardDao wRewardDao;
	@Autowired
	private WUserLevelService wUserLevelService;
	@Autowired
	private WBalanceDao wBalanceDao;
	@Autowired
	private WScoreDao wScoreDao;
	@Autowired
	private WBrokerageDao wBrokerageDao;
	@Autowired
	private SysConstantService sysConstantService;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat sdf1= new SimpleDateFormat("yyyy-MM-dd");
	private DecimalFormat df = new DecimalFormat("#.00");
	
	private static final BigDecimal ZERO = new BigDecimal(0);
//	private static final BigDecimal TEN_PERCENT = new BigDecimal(0.1);
	
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
	 * 分页查询项目信息
	 * @param page
	 * @param size 
	 * @param request 
	 * @param prePath 
	 * @return
	 */
	public List<WItem> getItemByPage(Integer page, Integer size, String prePath, HttpServletRequest request){
		int startPage = (page-1)*size;
		List<WItem> items = wItemDao.getItemByPage(startPage, size);
		for(WItem item : items){
			String description = changePathImg(css+StringEscapeUtils.unescapeHtml3(item.getDescription()),prePath,request.getContextPath());
			item.setDescription(description);
			//查滚动图
			List<WItemImage> imgs = wItemDao.getItemImages(item.getId());
			item.setImages(imgs);
			//查价格区间，从订单中获取，若订单中无则取item_info中的数据
			/*BigDecimal currentMax = wItemDao.getItemMaxCurrentPrice(item.getName());
			BigDecimal currentMin = wItemDao.getItemMinCurrentPrice(item.getName());
			BigDecimal oldMax = wItemDao.getItemMaxOldPrice(item.getName());
			BigDecimal oldMin = wItemDao.getItemMinOldPrice(item.getName());
			if(currentMax == null){
				item.setCurrentMax(item.getCurrentPrice());
			}
			if(currentMin == null){
				item.setCurrentMin(item.getCurrentPrice());
			}
			if(oldMax == null){
				item.setOldMax(item.getCurrentPrice());
			}
			if(oldMin == null){
				item.setOldMin(item.getCurrentPrice());
			}*/
		}
		return items;
	}

	/**
	 * 查询项目详情
	 * @param id
	 * @param request 
	 * @param prePath 
	 * @return
	 */
	public WItem getItemById(String id, String prePath, HttpServletRequest request) {
		WItem item = wItemDao.getItemById(id);
		if(StringUtils.isNotBlank(item.getDescription())){
			if(prePath !=null && prePath != "" && request != null){
				String description = changePathImg(css+StringEscapeUtils.unescapeHtml3(item.getDescription()),prePath,request.getContextPath());
				item.setDescription(description);
			}
		}
		//查滚动图
		List<WItemImage> imgs = wItemDao.getItemImages(item.getId());
		if(imgs != null && imgs.size() > 0){
			item.setImages(imgs);
		}
		/*//查价格区间，从订单中获取，若订单中无则取item_info中的数据
		BigDecimal currentMax = wItemDao.getItemMaxCurrentPrice(item.getName());
		BigDecimal currentMin = wItemDao.getItemMinCurrentPrice(item.getName());
		BigDecimal oldMax = wItemDao.getItemMaxOldPrice(item.getName());
		BigDecimal oldMin = wItemDao.getItemMinOldPrice(item.getName());
		if(currentMax == null){
			item.setCurrentMax(item.getCurrentPrice());
		}
		if(currentMin == null){
			item.setCurrentMin(item.getCurrentPrice());
		}
		if(oldMax == null){
			item.setOldMax(item.getCurrentPrice());
		}
		if(oldMin == null){
			item.setOldMin(item.getCurrentPrice());
		}*/
		return item;
	}
	
	/**
	 * 改价
	 * @param session
	 * @param id 订单号
	 * @param newPrice 
	 * @return
	 */
	public int changePrice(AUser user, String id, Double newPrice){
//		AUser manager = aUserDao.getUser((String)session.getAttribute("openId")); 
//		AUser manager = aUserDao.getUser("oEGwA1vhQH3Tpstau-KVjcKHOlFo");
		WOrder order = wOrderDao.getOrderByOutTradeId(id);
		if(order != null){
			BigDecimal useMoney = order.getChargeBalance();
			BigDecimal orderPrice = order.getOrderPrice();
			BigDecimal sub = orderPrice.subtract(new BigDecimal(newPrice));
			BigDecimal chargeBalance = useMoney.subtract(sub);
			int row = wOrderDao.changeDealPrice(new BigDecimal(newPrice), user.getId(), id, chargeBalance);
			return row;
		}
		return 0;
	}
	
	/**
	 * 改价审核后，继续跑订单
	 * @param id 订单id
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public int afterChangePrice(String id) throws ParseException, IOException{
		String token = aUserService.getToken();
		WOrder order = wOrderDao.getOrderByOutTradeId(id);
//		AUser user = aUserDao.getUser("oAl0WwM8qW5YwZeDLFdUHfe5Gf0U");
		AUser buyUser = aUserDao.getUserById(order.getBuyId());
		BigDecimal score = new BigDecimal(buyUser.getPoint());
		BigDecimal account = buyUser.getAccountMoney();
		BigDecimal deal = order.getDealPrice();
		
		//计算实际消费金额
		BigDecimal actualPay = new BigDecimal(0);
		if(score.compareTo(deal) != -1){//积分大于等于成交价
			actualPay = ZERO;
		}else{
			actualPay = deal.subtract(score);
		}
		
		//入余额信息表，
		WBalance balance = new WBalance();
		balance.setUserId(buyUser.getId());
		balance.setIndFlag("0");
		balance.setOrderId(order.getId());
		balance.setOrderType("1");
		balance.setApp(true);
		balance.setCurUserId(Constant.MANAGER_ID);
		balance.preInsert();
		balance.preUpdate();
		//入积分信息表
		WScore s = new WScore();
		s.setUserId(buyUser.getId());
		s.setStatus("0");
		s.setOrderId(order.getId());
		s.setOrderType("1");
		s.setApp(true);
		s.setCurUserId(Constant.MANAGER_ID);
		s.preInsert();
		s.preUpdate();
		//修改用户积分 余额，订单同步修改消费积分和余额，
		if(score.compareTo(deal) > 0){//积分高于价格，仅使用积分购买，余额明细则不需入表
			aUserDao.updateScore(buyUser.getId(), score.subtract(deal).longValue());
			wOrderDao.afterPayChange(order.getId(), deal.longValue(), ZERO);
			balance.setMoney(ZERO);
			s.setPoint(deal.longValue());
			//wBalanceDao.recharge(balance);
			wScoreDao.insert(s);
		}else if(score.compareTo(deal) == 0){//积分=价格，仅使用积分购买，余额明细则不需入表
			aUserDao.updateScore(buyUser.getId(), 0L);
			aUserDao.updateAccountMoney(buyUser.getId(), account.subtract(deal.subtract(score)));
			wOrderDao.afterPayChange(order.getId(), buyUser.getPoint(), deal.subtract(score));
			balance.setMoney(deal.subtract(score));
			s.setPoint(buyUser.getPoint());
			//wBalanceDao.recharge(balance);
			wScoreDao.insert(s);
		}else if(score.compareTo(ZERO) == 0){//积分为0，仅使用余额购买，则不入积分表
			aUserDao.updateScore(buyUser.getId(), 0L);
			aUserDao.updateAccountMoney(buyUser.getId(), account.subtract(deal.subtract(score)));
			wOrderDao.afterPayChange(order.getId(), buyUser.getPoint(), deal.subtract(score));
			balance.setMoney(deal.subtract(score));
			s.setPoint(buyUser.getPoint());
			wBalanceDao.recharge(balance);
			//wScoreDao.insert(s);
		}else{//同时消耗积分余额，需入2张表
			aUserDao.updateScore(buyUser.getId(), 0L);
			aUserDao.updateAccountMoney(buyUser.getId(), account.subtract(deal.subtract(score)));
			wOrderDao.afterPayChange(order.getId(), buyUser.getPoint(), deal.subtract(score));
			balance.setMoney(deal.subtract(score));
			s.setPoint(buyUser.getPoint());
			wBalanceDao.recharge(balance);
			wScoreDao.insert(s);
		}
		
		//支付状态改为已支付，
		wOrderDao.changePayState(order.getId(), "1");
		
		BigDecimal ambassador_one_item_level_one = sysConstantService.getAmbassadorOneItemLevelOne();
		BigDecimal ambassador_two_item_level_one = sysConstantService.getAmbassadorTwoItemLevelOne();
		BigDecimal ambassador_three_item_level_one = sysConstantService.getAmbassadorThreeItemLevelOne();
		BigDecimal ambassador_four_item_level_one = sysConstantService.getAmbassadorFourItemLevelOne();
		BigDecimal item_level_two = sysConstantService.getItemLevelTwo();
		
		BigDecimal shop_to_shop = sysConstantService.getShopToShop();

		BigDecimal director_level_one = sysConstantService.getDirectorLevelOne();
		BigDecimal director_level_two = sysConstantService.getDirectorLevelTwo();

		BigDecimal consultant_level_two = sysConstantService.getConsultantLevelTwo();
		
		BigDecimal no_shop_level_two = sysConstantService.getNoShopLevelTwo();
		
		//一级分销，进入奖励金表reward_relation
		String parentId = buyUser.getParentId();
		AUser u= aUserService.getUserById(parentId);
		String openId = u.getOpenId();
		String time = sdf1.format(new Date());
		if(StringUtils.isNotBlank(parentId)){
			//入奖励金信息表
			WReward reward = new WReward();
			reward.setApp(true);
			reward.setCurUserId(Constant.MANAGER_ID);
			reward.preInsert();
			reward.preUpdate();
			reward.setOrderId(order.getId());
			reward.setRewardType("0");
			reward.setUserId(parentId);
			AUser parentUser = aUserDao.getUserById(parentId);
			String grandUserType = "";
			if(StringUtils.isNotBlank(parentUser.getParentId())){
				AUser grandUser = aUserDao.getUserById(parentUser.getParentId());
				if(grandUser != null){
					grandUserType = grandUser.getUserType();
				}
			}
			
			//实际消费大于0，奖励金是计算实际消费金额，实际消费为0则不入表
			if(actualPay.compareTo(ZERO) == 1){
				if(UserType.DIRECTOR.getKey().equals(grandUserType)){
					reward.setUserReward(actualPay.multiply(director_level_one));
				}else{
					if(UserAmbassador.ONE_ANGEL.getKey().equals(parentUser.getUserAmbassador())){
						reward.setUserReward(actualPay.multiply(ambassador_one_item_level_one));
					}else if(UserAmbassador.TWO_ANGEL.getKey().equals(parentUser.getUserAmbassador())){
						reward.setUserReward(actualPay.multiply(ambassador_two_item_level_one));
					}else if(UserAmbassador.THREE_ANGEL.getKey().equals(parentUser.getUserAmbassador())){
						reward.setUserReward(actualPay.multiply(ambassador_three_item_level_one));
					}else if(UserAmbassador.FOUR_ANGEL.getKey().equals(parentUser.getUserAmbassador())){
						if(UserAmbassador.FOUR_ANGEL.getKey().equals(buyUser.getUserAmbassador())){
							if(StringUtils.isNotBlank(buyUser.getUserLevel())){
								String le = buyUser.getUserLevel();
								Integer lee = Integer.valueOf(le);
								if(lee > 0){
									reward.setUserReward(actualPay.multiply(shop_to_shop));
								}else{
									reward.setUserReward(actualPay.multiply(ambassador_four_item_level_one));
								}
							}
							reward.setUserReward(actualPay.multiply(shop_to_shop));
						}else{
							reward.setUserReward(actualPay.multiply(ambassador_four_item_level_one));
						}
					}
				}
//			else{//上级不是形象大使 没有奖励
//				reward.setUserReward(deal.multiply(ZERO));
//			}
				wRewardDao.insertReward(reward);
			}
			
			//入佣金信息表
			WBrokerage brokerage = new WBrokerage();
			//实际消费大于0，奖励金是计算实际消费金额，实际消费为0则不入表
			if(actualPay.compareTo(ZERO) == 1){
				if(UserType.DIRECTOR.getKey().equals(grandUserType)){
					brokerage.setMoney(actualPay.multiply(director_level_one));
				}else{
					if(UserAmbassador.ONE_ANGEL.getKey().equals(parentUser.getUserAmbassador())){
						brokerage.setMoney(actualPay.multiply(ambassador_one_item_level_one));
					}else if(UserAmbassador.TWO_ANGEL.getKey().equals(parentUser.getUserAmbassador())){
						brokerage.setMoney(actualPay.multiply(ambassador_two_item_level_one));
					}else if(UserAmbassador.THREE_ANGEL.getKey().equals(parentUser.getUserAmbassador())){
						brokerage.setMoney(actualPay.multiply(ambassador_three_item_level_one));
					}else if(UserAmbassador.FOUR_ANGEL.getKey().equals(parentUser.getUserAmbassador())){
						if(UserAmbassador.FOUR_ANGEL.getKey().equals(buyUser.getUserAmbassador())){
							brokerage.setMoney(actualPay.multiply(shop_to_shop));
						}else{
							brokerage.setMoney(actualPay.multiply(ambassador_four_item_level_one));
						}
					}
				}
//			else{
//				brokerage.setMoney(deal.multiply(ZERO));
//			}
				brokerage.setUserId(parentId);
				brokerage.setStatus("1");
				brokerage.setOrderId(order.getId());
				brokerage.setOrderType("2");
				brokerage.setApp(true);
				brokerage.setCurUserId(Constant.MANAGER_ID);
				brokerage.preInsert();
				brokerage.preUpdate();
				wBrokerageDao.insert(brokerage);
			}
			
			//上级佣金累加
			//实际消费大于0，奖励金是计算实际消费金额，实际消费为0则不入表
			if(actualPay.compareTo(ZERO) == 1){
				if(UserType.DIRECTOR.getKey().equals(grandUserType)){
					aUserDao.AddUpRewardMoney(parentId, actualPay.multiply(director_level_one));
					String str = df.format(actualPay.multiply(director_level_one));
					WeixinUtil.sendTextMessageToUser("返现到账通知！\n"+time+"\n\n恭喜你获得推荐奖励!\n"+"奖励金额:"+str+"元\n"+"消费项目:"+order.getGoodsName(),openId, token);
				}else{
					if(UserAmbassador.ONE_ANGEL.getKey().equals(parentUser.getUserAmbassador())){
						aUserDao.AddUpRewardMoney(parentId, actualPay.multiply(ambassador_one_item_level_one));
						String str = df.format(actualPay.multiply(ambassador_one_item_level_one));
						WeixinUtil.sendTextMessageToUser("返现到账通知！\n"+time+"\n\n恭喜你获得推荐奖励!\n"+"奖励金额:"+str+"元\n"+"消费项目:"+order.getGoodsName(),openId, token);
					}else if(UserAmbassador.TWO_ANGEL.getKey().equals(parentUser.getUserAmbassador())){
						aUserDao.AddUpRewardMoney(parentId, actualPay.multiply(ambassador_two_item_level_one));
						String str = df.format(actualPay.multiply(ambassador_two_item_level_one));
						WeixinUtil.sendTextMessageToUser("返现到账通知！\n"+time+"\n\n恭喜你获得推荐奖励!\n"+"奖励金额:"+str+"元\n"+"消费项目:"+order.getGoodsName(),openId, token);
					}else if(UserAmbassador.THREE_ANGEL.getKey().equals(parentUser.getUserAmbassador())){
						aUserDao.AddUpRewardMoney(parentId, actualPay.multiply(ambassador_three_item_level_one));
						String str = df.format(actualPay.multiply(ambassador_three_item_level_one));
						WeixinUtil.sendTextMessageToUser("返现到账通知！\n"+time+"\n\n恭喜你获得推荐奖励!\n"+"奖励金额:"+str+"元\n"+"消费项目:"+order.getGoodsName(),openId, token);
					}else if(UserAmbassador.FOUR_ANGEL.getKey().equals(parentUser.getUserAmbassador())){
						if(UserAmbassador.FOUR_ANGEL.getKey().equals(buyUser.getUserAmbassador())){
							aUserDao.AddUpRewardMoney(parentId, actualPay.multiply(shop_to_shop));
							String str = df.format(actualPay.multiply(shop_to_shop));
							WeixinUtil.sendTextMessageToUser("返现到账通知！\n"+time+"\n\n恭喜你获得推荐奖励!\n"+"奖励金额:"+str+"元\n"+"消费项目:"+order.getGoodsName(),openId, token);
						}else{
							aUserDao.AddUpRewardMoney(parentId, actualPay.multiply(ambassador_four_item_level_one));
							String str = df.format(actualPay.multiply(ambassador_four_item_level_one));
							WeixinUtil.sendTextMessageToUser("返现到账通知！\n"+time+"\n\n恭喜你获得推荐奖励!\n"+"奖励金额:"+str+"元\n"+"消费项目:"+order.getGoodsName(),openId, token);
						}
					}
				}
			}
//			else{
//				aUserDao.AddUpRewardMoney(parentId, deal.multiply(ZERO));
//			}
			
			boolean af = UserAmbassador.FOUR_ANGEL.getKey().equals(buyUser.getUserAmbassador());
			boolean bf = UserAmbassador.FOUR_ANGEL.getKey().equals(parentUser.getUserAmbassador());
			boolean cf = af && bf;
			if(!cf){
				//二级分销
				String grandId = parentUser.getParentId();
				if(StringUtils.isNotBlank(grandId)){
					AUser uu= aUserService.getUserById(grandId);
					
					String buyA = buyUser.getUserAmbassador();
					String parentA = u.getUserAmbassador();
					String grandA = parentUser.getUserAmbassador();
					
					String openId1 = uu.getOpenId();
					//实际消费大于0，奖励金是计算实际消费金额，实际消费为0则不入表
					if(actualPay.compareTo(ZERO) == 1){
						//入奖励金信息表
						WReward r = new WReward();
						r.setApp(true);
						r.setCurUserId(Constant.MANAGER_ID);
						r.preInsert();
						r.preUpdate();
						r.setOrderId(order.getId());
						r.setRewardType("1");
						r.setUserId(grandId);
						r.setUserReward(actualPay.multiply(item_level_two));
						if(!UserAmbassador.FOUR_ANGEL.getKey().equals(buyA) && 
							UserAmbassador.FOUR_ANGEL.getKey().equals(parentA) && 
							UserAmbassador.FOUR_ANGEL.getKey().equals(grandA)){
							r.setUserReward(actualPay.multiply(no_shop_level_two));
						}else{
							if(UserType.DIRECTOR.getKey().equals(grandUserType)){
								r.setUserReward(actualPay.multiply(director_level_two));
							}else if(UserType.SALE.getKey().equals(grandUserType)){
								r.setUserReward(actualPay.multiply(consultant_level_two));
							}
						}
						wRewardDao.insertReward(r);
						//入佣金信息表
						WBrokerage b = new WBrokerage();
						b.setMoney(actualPay.multiply(item_level_two));
						if(!UserAmbassador.FOUR_ANGEL.getKey().equals(buyA) && 
								UserAmbassador.FOUR_ANGEL.getKey().equals(parentA) && 
								UserAmbassador.FOUR_ANGEL.getKey().equals(grandA)){
							b.setMoney(actualPay.multiply(consultant_level_two));
						}else{
							if(UserType.DIRECTOR.getKey().equals(grandUserType)){
								b.setMoney(actualPay.multiply(director_level_two));
							}else if(UserType.SALE.getKey().equals(grandUserType)){
								b.setMoney(actualPay.multiply(consultant_level_two));
							}
						}
						b.setUserId(grandId);
						b.setStatus("1");
						b.setOrderId(order.getId());
						b.setOrderType("2");
						b.setApp(true);
						b.setCurUserId(Constant.MANAGER_ID);
						b.preInsert();
						b.preUpdate();
						wBrokerageDao.insert(b);
						//上级佣金累加
						if(!UserAmbassador.FOUR_ANGEL.getKey().equals(buyA) && 
								UserAmbassador.FOUR_ANGEL.getKey().equals(parentA) && 
								UserAmbassador.FOUR_ANGEL.getKey().equals(grandA)){
							aUserDao.AddUpRewardMoney(grandId, actualPay.multiply(consultant_level_two));
							String str = df.format(actualPay.multiply(consultant_level_two));
							WeixinUtil.sendTextMessageToUser("返现到账通知！\n"+time+"\n\n恭喜你获得推荐奖励!\n"+"奖励金额:"+str+"元\n"+"消费项目:"+order.getGoodsName(),openId1, token);
						}else{
							if(UserType.DIRECTOR.getKey().equals(grandUserType)){
								aUserDao.AddUpRewardMoney(grandId, actualPay.multiply(director_level_two));
								String str = df.format(actualPay.multiply(director_level_two));
								WeixinUtil.sendTextMessageToUser("返现到账通知！\n"+time+"\n\n恭喜你获得推荐奖励!\n"+"奖励金额:"+str+"元\n"+"消费项目:"+order.getGoodsName(),openId1, token);
							}else if(UserType.SALE.getKey().equals(grandUserType)){
								aUserDao.AddUpRewardMoney(grandId, actualPay.multiply(consultant_level_two));
								String str = df.format(actualPay.multiply(consultant_level_two));
								WeixinUtil.sendTextMessageToUser("返现到账通知！\n"+time+"\n\n恭喜你获得推荐奖励!\n"+"奖励金额:"+str+"元\n"+"消费项目:"+order.getGoodsName(),openId1, token);
							}else{
								aUserDao.AddUpRewardMoney(grandId, actualPay.multiply(item_level_two));
								String str = df.format(actualPay.multiply(item_level_two));
								WeixinUtil.sendTextMessageToUser("返现到账通知！\n"+time+"\n\n恭喜你获得推荐奖励!\n"+"奖励金额:"+str+"元\n"+"消费项目:"+order.getGoodsName(),openId1, token);
							}
						}
						
					}
				}
			}
		}
		
		//实际消费大于0，奖励金是计算实际消费金额，实际消费为0则不入表
		if(actualPay.compareTo(ZERO) == 1){
			//判断星级玩法
			AUser[] group = wUserLevelService.getGroup(buyUser.getId());
			wUserLevelService.disposeGroup(buyUser, order.getId(), group, actualPay);
		}
		
		//销售次数+1
		wItemDao.addCount(order.getGoodsId());
		List<String> list = aUserService.getYWYopenId();
		if(list==null){
			list = Lists.newArrayList();
		}
		String username = buyUser.getName();
		String orderName = order.getGoodsName();
		BigDecimal money = order.getDealPrice();
		String changId = order.getChangePriceId();
		if(changId!=null){
			AUser auser = aUserService.getUserById(changId);
			if(auser!=null){
				String auserName = auser.getName();
				for(int i = 0;i<list.size();i++){
					WeixinUtil.sendTextMessageToUser("新订单支付完成通知！\n\n"+"提交时间:"+time+"\n订单类型:余额支付\n"+"客户信息:"+username+"\n"+"支付信息:"+orderName+money.toString()+"元\n"+"审核人:"+auserName+"\n",list.get(i), token);
				}
			}
		}
		return 1;
	}
	 
	
	/**
	 * 购买项目，下单后等待改价，付款后继续后续流程
	 * @param session
	 * @param itemId
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public int buyItem(AUser user, String itemId) throws ParseException, IOException{
		WItem item = wItemDao.getItemById(itemId);
//		String openId = (String)session.getAttribute("openId"); 
//		String openId = "oAl0WwM8qW5YwZeDLFdUHfe5Gf0U";
		//获取user
//		AUser user = aUserDao.getUser(openId);
		if(StringUtils.isBlank(user.getParentId())){
			return 2;
		}
		//判断是否完善个人信息
		boolean userComplete = aUserService.userComplete(user.getId());
		if(userComplete == false){
			return 4;//个人信息不完善
		}
		//上级不是形象大使，不能购买
		AUser pUser = aUserDao.getUserById(user.getParentId());
		if(pUser == null){
			return 2;
		}
		if("0".equals(pUser.getUserAmbassador())){
			return 5;
		}
		//如果不是形象大使，不能购买项目
		if("0".equals(user.getUserAmbassador())){
			return 3;
		}
		String userId = user.getId();
		//仅下单，入订单表all_order_info
		WOrder order = new WOrder();
		order.setBuyId(userId);
		order.setGoodsId(itemId);
		order.setGoodsType("2");
		order.setGoodsName(item.getName());
		order.setPayState("0");
		order.setCheckState("0");
		order.setDealPrice(item.getCurrentPrice());
		order.setOrderPrice(item.getCurrentPrice());
		order.setChangePriceId("");
		order.setChangePriceDate(null);
		BigDecimal score = new BigDecimal(user.getPoint());
		int f = score.compareTo(item.getCurrentPrice());
		if(f == -1){//积分不够
			order.setChargeScore(user.getPoint());
			order.setChargeBalance(item.getCurrentPrice().subtract(score));
		}else{
			order.setChargeScore(item.getCurrentPrice().longValue());
			order.setChargeBalance(ZERO);
		}
		order.setApp(true);
		order.setCurUserId(user.getId());
		order.preInsert();
		order.preUpdate();
		order.setId(OrderCodeUtil.getNo(userId));
		wOrderDao.insertOrder(order);
		String token = aUserService.getToken();
		String orderName = wItemDao.getItemById(item.getId()).getName();
		String username =user.getName();
		String money = order.getDealPrice().toString();
		String time = sdf.format(new Date());
		List<String> list = aUserService.getYWYopenId();
		if(list==null){
			list = Lists.newArrayList();
	}
		for(int i = 0;i<list.size();i++){
			WeixinUtil.sendTextMessageToUser("新订单支付通知！\n\n"+"提交时间:"+time+"\n订单类型:余额支付\n"+"客户信息:"+username+"\n"+"支付信息:"+orderName+money+"元\n"+"请各业务员尽快审核！\n",list.get(i), token);
		}
		return 1;
	}

	/**
	 * 获取价格确认列表
	 * @return
	 */
	public List<WOrder> getPriceInfo() {
		List<WOrder> orders = wOrderDao.canBeChangeOrder();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(WOrder o : orders){
			AUser user = aUserDao.getUserById(o.getBuyId());
			o.setBuyName(user.getName());
			String scd = sdf.format(o.getCreateDate());
			o.setStringCreateDate(scd);
		}
		return orders;
	}

	/**
	 * 审核订单改价
	 * @param session
	 * @param id 订单id
	 * @return
	 */
	public int check(AUser user, String id) {
//		AUser manager = aUserDao.getUser((String)session.getAttribute("openId"));
		//判断用户的积分余额是否足够购买
		WOrder order = wOrderDao.getOrderByOutTradeId(id);
		AUser buyUser = aUserDao.getUserById(order.getBuyId());
		BigDecimal score = new BigDecimal(buyUser.getPoint());
		BigDecimal account = buyUser.getAccountMoney();
		BigDecimal deal = order.getDealPrice();
		//积分余额不足
		if(score.add(account).compareTo(deal) == -1){
			return -1;
		}
		return wOrderDao.Checked(id, user.getId());
	}

	/**
	 * 根据分类获取项目
	 * @param type
	 * @return
	 */
	public List<WItem> getItemByType(String type) {
		List<WItem> items = wItemDao.getItemByType(type);
		return items;
	}

	/**
	 * 根据分类获分页取项目
	 * @param type
	 * @param page
	 * @return
	 */
	public List<WItem> getItemByTypeByPage(String type, int index) {
		int n = (index-1)*10;
		int s = WeixinUtil.BeautyDiaryCount;
		List<WItem> items = wItemDao.getItemByTypeByPage(type, n, s);
		return items;
	}

	/**
	 * 获取项目有哪些类型
	 * @return
	 */
	public List<String> getItemTypes() {
		List<String> types = wItemDao.getItemTypes();
		if(types == null || types.size() < 1){
			types = Lists.newArrayList();
		}
		return types;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
