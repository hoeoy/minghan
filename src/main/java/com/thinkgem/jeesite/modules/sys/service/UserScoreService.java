package com.thinkgem.jeesite.modules.sys.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.ks.app.dao.WMonthBackDao;
import com.ks.app.dao.WOrderDao;
import com.ks.app.entity.AUser;
import com.ks.app.entity.WMonthBack;
import com.ks.app.entity.WOrder;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.cmd.dao.OrderComboDao;
import com.thinkgem.jeesite.modules.cmd.dao.OrderItemDao;
import com.thinkgem.jeesite.modules.cmd.dao.TakeOutDao;
import com.thinkgem.jeesite.modules.cmd.entity.TakeOut;
import com.thinkgem.jeesite.modules.sys.dao.ScoreDao;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.Score;
import com.thinkgem.jeesite.modules.sys.entity.User;

@Service
@Transactional
public class UserScoreService extends CrudService<ScoreDao, Score>{
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private ScoreDao scoreDao;
	@Autowired
	private WOrderDao wOrderDao;
	@Autowired
	private TakeOutDao takeOutDao;
	@Autowired
	private WMonthBackDao wMonthBackDao;
	@Autowired
	private OrderComboDao orderComboDao;
	@Autowired
	private OrderItemDao orderItemDao;

	public Page<Score> getScoreList(Page<Score> page, Score score) {
		Page<Score> scorePage = super.findPage(page, score);
		List<Score> list = scorePage.getList();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		for(Score s : list){
			//用户名
			User user = userDao.get(s.getUserId());
			if(user != null){
				s.setUserName(user.getName());
			}
			//时间格式
			String format = sdf.format(s.getUpdateDate());
			s.setStringUpdateDate(format);
			//套餐/项目名
			if("1".equals(s.getOrderType())){//项目
				String itemName = orderItemDao.getNameByoutTradeId(s.getOrderId());
				s.setGoodsName(itemName);
			}else if("0".equals(s.getOrderType())){//套餐
				String comboName = orderComboDao.getNameByoutTradeId(s.getOrderId());
				s.setGoodsName(comboName);
			}
		}
		return scorePage;
	}

	/**
	 * 获取当前用户的积分明细
	 * @param session
	 * @return
	 */
	public List<Score> getScoreListByUser(AUser user) {
//		AUser user = aUserDao.getUser((String)session.getAttribute("openId"));
		List<Score> list = Lists.newArrayList();
		if(user != null){
			list = scoreDao.getScoreByUserId(user.getId());
			if(list != null ){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				for(Score score : list){
					score.setStringCreateDate(sdf.format(score.getCreateDate()));
					String orderId = score.getOrderId();
					WOrder order = wOrderDao.getOrderByOutTradeId(orderId);
					score.setGoodsName(order.getGoodsName());
				}
			}
		}
		return list;
	}

	/**
	 * 获取当前用户的返现明细
	 * @param session
	 * @return
	 */
	public Page<TakeOut> findReturnPage(TakeOut out ,Integer pageNo) {
		pageNo = pageNo == null ? 1:pageNo;
		int pageSize = Global.getDefaultPageSize();
		Page<TakeOut> page = new Page<>(pageNo, pageSize);
		out.setPage(page);
		page.setList(takeOutDao.findList(out));
		List<TakeOut> list = page.getList();
		if(list != null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			for(TakeOut takeout : list){
				//将返现金额扣除手续费
				if(takeout.getServiceCharge() != null){
					BigDecimal s = takeout.getServiceCharge();
					BigDecimal b = takeout.getBack();
					takeout.setBack(b.subtract(s));
				}else{
					takeout.setServiceCharge(new BigDecimal(0));
				}
				
				WMonthBack mb = wMonthBackDao.getEntityById(takeout.getBackMonthId());
				if(mb != null){
					WOrder order = wOrderDao.getOrderByOutTradeId(mb.getOrderId());
					if(order != null){
						takeout.setComboName(order.getId());
						if(order.getCreateDate() != null && order.getCreateDate() instanceof java.util.Date){
							takeout.setStringCreateDate(sdf.format(order.getCreateDate()));
						}
					}
				}
			}
		}
		return page;
	}

}
