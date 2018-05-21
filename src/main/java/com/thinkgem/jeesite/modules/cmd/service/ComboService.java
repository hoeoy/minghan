package com.thinkgem.jeesite.modules.cmd.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.cmd.dao.ComboDao;
import com.thinkgem.jeesite.modules.cmd.entity.Combo;
import com.thinkgem.jeesite.modules.cmd.entity.ComboContract;
import com.thinkgem.jeesite.modules.cmd.entity.ComboImage;

@Service
@Transactional(rollbackFor=Exception.class)
public class ComboService extends CrudService<ComboDao, Combo>{

//	@Autowired
//	private ComboDao comboDao;
//	@Autowired
//	private AUserDao aUserDao;
//	@Autowired
//	private WOrderDao wOrderDao;
//	@Autowired
//	private WRewardDao wRewardDao;
//	@Autowired
//	private WMonthBackDao wMonthBackDao;
	
//	private static final BigDecimal ZERO = new BigDecimal(0);
//	private static final BigDecimal POINT_ONE = new BigDecimal(0.1);
//	private static final BigDecimal TWELVE = new BigDecimal(12);
	
	//商品介绍单独一个接口
	private String css = "<style type='text/css'>p{margin:0px;}</style>";
	public String changePathImg(String content,String prePath,String contextPath) {   
		if(StringUtils.isBlank(content))
			return "";
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
	
	public Page<Combo> getComboList(Page<Combo> page, Combo combo) {
		Page<Combo> comboPage = super.findPage(page, combo);
		List<Combo> list = comboPage.getList();
		for(Combo c : list){
			//查询滚动图
			List<ComboImage> images = dao.getComboImagesByComboId(c.getId());
			if(images != null)
				c.setImages(images);
			//查合同图
			List<ComboContract> contracts = dao.getComboContractsByComboId(c.getId());
			if(contracts != null)
				c.setContracts(contracts);
		}
		return comboPage;
	}

	/**
	 * 获取所有形象大使信息
	 * @return
	 */
//	public List<WCombo> getComboList(String prePath,HttpServletRequest request) {
//		List<WCombo> combos = wComboDao.getComboList();
//		for(WCombo combo : combos){
//			String description = changePathImg(css+StringEscapeUtils.unescapeHtml3(combo.getDescription()),prePath,request.getContextPath());
//			combo.setDescription(description);
//			List<WComboContract> contracts = wComboDao.getComboContratsByComboId(combo.getId());
//			combo.setContracts(contracts);
//		}
//		return combos;
//	}

	/**
	 * 获取套餐详细信息
	 * @param id
	 * @param request 
	 * @param prePath 
	 * @return
	 */
	public Combo getComboById(String id, String prePath, HttpServletRequest request) {
		Combo combo = dao.getComboById(id);
		if(combo != null){
			String description = changePathImg(css+StringEscapeUtils.unescapeHtml3(combo.getDescription()),prePath,request.getContextPath());
			combo.setDescription(description);
		}
		return combo == null ? new Combo():combo;
	}

	/**
	 * 删除套餐相关图片和合同
	 * @param combo
	 */
	public void deleteImageAndContract(Combo combo) {
		dao.deleteComboImages(combo.getId());
		dao.deleteComboContracts(combo.getId());
	}

	/**
	 * 新增滚动图片和合同图拼接
	 * @param combo
	 */
	public void inFrameFotosaveCombo(Combo combo) {
		if(StringUtils.isNotBlank(combo.getImagesLocation())){
			//拼接图片的地址到list
			String[] str = combo.getImagesLocation().split(",");
			List<ComboImage> images = new ArrayList<ComboImage>();
			for (int i = 0; i < str.length; i++) {
				if(StringUtils.isNotBlank(str[i])){
					ComboImage comboImage = new ComboImage();
					comboImage.setComboId(combo.getId());
					comboImage.setImage(str[i]);
					comboImage.preInsert();
					images.add(comboImage);
				}
			}
			if(images.size()>0){
//				Map<String,Object> param = new HashMap<String, Object>();
//				param.put("images", images);
				dao.insertComboImage(images);
			}
		}
		if(StringUtils.isNotBlank(combo.getContractsLocation())){
			String[] str = combo.getContractsLocation().split(",");
			List<ComboContract> images = new ArrayList<ComboContract>();
			for (int i = 0; i < str.length; i++) {
				if(StringUtils.isNotBlank(str[i])){
					ComboContract comboContract = new ComboContract();
					comboContract.setComboId(combo.getId());
					comboContract.setContract(str[i]);
					comboContract.preInsert();
					images.add(comboContract);
				}
			}
			if(images.size()>0){
//				Map<String,Object> param = new HashMap<String, Object>();
//				param.put("images", images);
				dao.insertComboContract(images);
			}
		}
	}

	public Combo getComboImageAndContract(Combo combo) {
		//查询滚动图
		List<ComboImage> images = dao.getComboImagesByComboId(combo.getId());
		if(images != null)
			combo.setImages(images);
		//查合同图
		List<ComboContract> contracts = dao.getComboContractsByComboId(combo.getId());
		if(contracts != null)
			combo.setContracts(contracts);
		return combo;
	}

	/**
	 * 获取套餐合同详细信息
	 * @param comboId
	 * @return
	 */
//	public List<WComboContract> getComboContratsByComboId(String comboId) {
//		return wComboDao.getComboContratsByComboId(comboId);
//	}
	
	/**
	 * 购买套餐
	 * @param session
	 * @param comboId 套餐id
	 * @return
	 */
	/*public int buyCombo(HttpSession session, String comboId) {
		WCombo combo = wComboDao.getComboById(comboId);
		String openId = (String)session.getAttribute("openId");
//		String openId = "oEGwA1neksT_x6WfGLbNVuXBnbkc";
		//获取user
		AUser user = aUserDao.getUser(openId);
		String userId = user.getId();
		//判断是否曾经购买过，限购一次
		if(wOrderDao.hasCombo(userId) != null){
			return 2;
		}
		//判断用户余额是否足够
		BigDecimal accountMoney = user.getAccountMoney();
		//套餐现价乘以折扣率
		BigDecimal currentPrice = combo.getCurrentPrice().multiply(new BigDecimal(combo.getDiscount()));
		//余额不足
		if(accountMoney.compareTo(currentPrice) == -1){
			return 3;
		}
		
		//下订单all_order_info
		WOrder order = new WOrder();
		order.setBuyId(userId);
		order.setOutTradeId(IdGen.uuid());
		order.setGoodsId(comboId);
		order.setGoodsType("1");
		order.setReceiverId(userId);
		order.setReceiverName(user.getName());
		order.setReceiveAddr(user.getAddr());
		order.setTel(user.getMobile());
		order.setSendState("1");
		order.setReceiveState("1");
		order.setPayState("1");
		order.setDealPrice(currentPrice);
		order.setChangePriceId("");
		order.setChangePriceDate(null);
		order.setChargeScore(combo.getScore());
		order.setChargeBalance(ZERO);
		order.setParentAward(currentPrice.multiply(POINT_ONE));
		order.setGrandAward(ZERO);
		order.setApp(true);
		order.setCurUserId(user.getId());
		order.preInsert();
		order.preUpdate();
		wOrderDao.insertOrder(order);
		
		//扣除余额
		BigDecimal money = user.getAccountMoney().subtract(currentPrice);
		aUserDao.updateAccountMoney(userId, money);
		//增加积分
		aUserDao.updateScore(userId, combo.getScore());
		
		//进入奖励金表reward_relation
		WReward reward = new WReward();
		reward.setApp(true);
		reward.setCurUserId(Constant.MANAGER_ID);
		reward.preInsert();
		reward.preUpdate();
		
		reward.setOrderId(order.getId());
		reward.setRewardType("3");
		reward.setParentId(user.getParentId());
		reward.setParentReward(order.getDealPrice().multiply(POINT_ONE));
		reward.setGrandId("");
		reward.setGrandReward(ZERO);
		reward.setLevelReward(ZERO);
		wRewardDao.insertReward(reward);
		
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
			
			monthBack.setTime(date);
			monthBack.setUserId(userId);
			monthBack.setComboId(comboId);
			monthBack.setMoney(order.getDealPrice().divide(TWELVE));
			monthBack.setState("1");
			monthBack.setStage(flag);
			if(flag >1){
				monthBack.setState("0");
				calendar.add(Calendar.MONTH, 1);
				monthBack.setTime(calendar.getTime());
			}
			wMonthBackDao.insertMonthBack(monthBack);
			flag++;
		}while(flag <= 12);
		
		//销售次数+1
		wComboDao.count(comboId);
		
		//用户级别变更
		aUserDao.updateUserLevel(userId, order.getGoodsId());
		
		//上级奖励10%，无上级则不update
		if(user.getParentId() == null){
			return 1;
		}
		aUserDao.AddUpRewardMoney(user.getParentId(), order.getDealPrice().multiply(POINT_ONE));
		return 1;
	}*/
	
	
}
