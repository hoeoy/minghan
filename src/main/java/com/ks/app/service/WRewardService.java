package com.ks.app.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ks.app.dao.AUserDao;
import com.ks.app.dao.WMonthBackDao;
import com.ks.app.dao.WRewardDao;
import com.ks.app.dao.WTakeOutDao;
import com.ks.app.entity.AUser;
import com.ks.app.entity.WMonthBack;
import com.ks.app.entity.WReward;
import com.ks.app.entity.WTakeOut;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.modules.sys.service.SysConstantService;

@Service
@Transactional(rollbackFor=Exception.class)
public class WRewardService extends CrudService<WRewardDao,WReward>{

	@Autowired
	private WRewardDao wRewardDao;
	@Autowired
	private WMonthBackDao wMonthBackDao;
	@Autowired
	private WTakeOutDao wTakeOutDao;
	@Autowired
	private AUserDao aUserDao;
	@Autowired
	private SysConstantService sysConstantService;
	
	private static final BigDecimal ZERO = new BigDecimal(0);
	
	/**
	 * 审核
	 * @param session
	 * @param takeOutId 提现单主键id
	 * @return
	 */
	public int checkWithdrawDeposit(AUser user, String takeOutId) {
//		AUser user = aUserDao.getUser((String)session.getAttribute("openId"));
		if(user == null)
			return -1;
		wTakeOutDao.changeFlag(takeOutId, user.getId());
		//改用户表中用户的佣金余额
		WTakeOut takeOut = wTakeOutDao.getTakeOutById(takeOutId);
		BigDecimal takeOUtMoney = new BigDecimal(0);
		if("0".equals(takeOut.getType())){
			takeOUtMoney = takeOut.getBrokerage();
		}else if("1".equals(takeOut.getType())){
			takeOUtMoney = takeOut.getBack();
			//返现 还需将返现表中状态码修改
			wMonthBackDao.changeState("3", takeOut.getUserId(), takeOut.getBackMonthId());
		}
		aUserDao.updateRewardMoney(user.getId(), user.getRewardMoney().subtract(takeOUtMoney));
		return 1;
	}
	
	/**
	 * 佣金提现
	 * @param session
	 * @param money 提现金额
	 * @return
	 */
	public int withdrawDepositByReward(AUser user, Long money) {
		if(money == 0){
			//不能提现0元
			return 3;
		}
		if(money < 0){
			return 4;
		}
//		AUser user = aUserDao.getUser((String)session.getAttribute("openId"));
		if(user == null){
			return 5;
		}
		BigDecimal accountMoney = user.getRewardMoney() == null ? new BigDecimal(0):user.getRewardMoney();
		BigDecimal takeOUtMoney = new BigDecimal(money);
		if(accountMoney.compareTo(takeOUtMoney) == -1){
			//提现金额大于拥有
			return 2;
		}
		
		String s = sysConstantService.getCustomShouXuFei();
		BigDecimal sxf = new BigDecimal(s);
		//可提现，下单入order_out_info表
		WTakeOut takeOut = new WTakeOut();
		takeOut.setApp(true);
		takeOut.setCurUserId(user.getId());
		takeOut.preInsert();
		takeOut.preUpdate();
		takeOut.setType("0");
		takeOut.setServiceCharge(ZERO);
		takeOut.setBrokerage(takeOUtMoney);
		takeOut.setServiceCharge(takeOUtMoney.multiply(sxf));//手续费
		takeOut.setBack(ZERO);
		takeOut.setBackMonthId(null);
		takeOut.setUserId(user.getId());
		takeOut.setUserName(user.getName());
		takeOut.setAuditFlag("0");
		takeOut.setAuditId(null);
		takeOut.setAuditName(null);
		wTakeOutDao.insertOrder(takeOut);
		
		//修改user的佣金
		aUserDao.updateRewardMoney(user.getId(), accountMoney.subtract(takeOUtMoney));
		
		return 1;
	}
	
	/**
	 * 用户提现 12月返现
	 * @param session
	 * @param stage 第几期
	 * @return
	 */
	public int withdrawDepositByMonth(AUser user, Integer stage) {
//		AUser user = aUserDao.getUser((String)session.getAttribute("openId"));
		if(user == null)
			return -1;
		//获取month_back_info表中信息
		WMonthBack monthBack = wMonthBackDao.getRewardByIdAndStage(user.getId(), stage);
		if(monthBack == null){
			return 2;
		}
		long now = new Date().getTime();
		long time = monthBack.getStartTime().getTime();
		BigDecimal serviceCharge = new BigDecimal(sysConstantService.getCustomShouXuFei());
		if(now >= time){
			//可提现，下单入order_out_info表
			WTakeOut takeOut = new WTakeOut();
			takeOut.setApp(true);
			takeOut.setCurUserId(user.getId());
			takeOut.preInsert();
			takeOut.preUpdate();
			takeOut.setType("1");
			takeOut.setServiceCharge(monthBack.getMoney().multiply(serviceCharge));
			takeOut.setBrokerage(ZERO);
			takeOut.setBack(monthBack.getMoney());
			takeOut.setBackMonthId(monthBack.getId());
			takeOut.setUserId(user.getId());
			takeOut.setAuditFlag("0");
			takeOut.setAuditId(null);
			wTakeOutDao.insertOrder(takeOut);
			//改month_back_info中状态码
			wMonthBackDao.changeState("2", user.getId(), monthBack.getId());
			return 1;
		}else{
			//未到可提现时间
			return 0;
		}
	}
	
	/**
	 * 在数据库中生成一条记录
	 * @return
	 */
	public int buildReward(){
		WReward reward = new WReward();
		reward.setId(IdGen.uuid());
		reward.setOrderId("");
		reward.setRewardType("0");
//		reward.setParentId("11");
//		reward.setParentReward(new BigDecimal(25));
//		reward.setGrandId("22");
//		reward.setGrandReward(new BigDecimal(5));
//		reward.setLevelReward(new BigDecimal(6));
		
		reward.setCreateDate(new Date());
		reward.setUpdateDate(new Date());
		reward.setDelFlag("0");
		return wRewardDao.insertReward(reward);
	}
	
	/**
	 * 根据主键id查询
	 * @param id
	 * @return
	 */
	public WReward getRewardById(String id){
		return wRewardDao.getRewardById(id);
	}

	public List<WReward> getRewardByorderId(String id,BigDecimal bd,String userId) {
		List<WReward> wr = wRewardDao.getRewardByorderId(id,bd,userId);
		return wr;
	}

}
