package com.ks.app.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.ks.app.dao.WMonthBackDao;
import com.ks.app.dao.WTakeOutDao;
import com.ks.app.entity.AUser;
import com.ks.app.entity.WMonthBack;
import com.ks.app.entity.WTakeOut;
import com.ks.utils.EnumConstant.MonthBackAuditFlag;
import com.ks.utils.EnumConstant.MonthBackState;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SysConstantService;
@Service
@Transactional
public class WMonthBackService extends CrudService<WMonthBackDao, WMonthBack>{
	@Autowired
	private WMonthBackDao wMonthBackDao;
	@Autowired
	private UserDao userDao ;
	@Autowired
	private SysConstantService sysConstantService;
	@Autowired
	private WTakeOutDao wTakeOutDao;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	public List<WMonthBack> getListById(String id) {
		List<WMonthBack> list = wMonthBackDao.getListById(id);
		if(list == null)
			list = Lists.newArrayList();
		for(int i=0;i<list.size();i++){
			list.get(i).setStartTimeString(sdf.format(list.get(i).getStartTime()));
			list.get(i).setState(MonthBackState.monthBackState(list.get(i).getState()));
			list.get(i).setAuditFlag(MonthBackAuditFlag.monthBackAuditFlag(list.get(i).getAuditFlag()));
			Date date = list.get(i).getStartTime();
			Date now = new Date();
			list.get(i).setTime(date.getTime()-now.getTime());
		}
		return list;
	}
	
	/**
	 * 点击返现领取后 入提现单表
	 * @param id 12月单id
	 * @param auto 自动修改则为true
	 * @return
	 */
	public int getReturn(String id, boolean auto) {
		WMonthBack wmb = wMonthBackDao.get(id);
		if(wmb == null){
			return 2;//没有返现记录
		}
		long now = new Date().getTime();
		long time = wmb.getStartTime().getTime();
		if(now < time){
			return 0; //未到可提现时间
		}
		
		wmb.setState("1");//返现标记 0未领取 1已领取
		wmb.setAuditFlag("1");//审核状态0未开始，1待审核，2已通过
		wmb.setType("0");//0手动申请  1自动申请
		if(auto){
			wmb.setType("1");
		}
		wMonthBackDao.update(wmb);
		
		String s = sysConstantService.getCustomShouXuFei();
		BigDecimal sxf = new BigDecimal(s);
		//入order_out_info表
		WTakeOut wto = new WTakeOut();
		wto.setApp(true);
		wto.setCurUserId(wmb.getUserId());
		wto.preInsert();
		wto.preUpdate();
		
		wto.setType("1");//提现类型 0佣金 1返现
		wto.setBack(wmb.getMoney());
		wto.setBackMonthId(wmb.getId());
		wto.setUserId(wmb.getUserId());
		wto.setServiceCharge(wmb.getMoney().multiply(sxf));//手续费
		wto.setAuditFlag("0");//审核状态 0未审核 1已审核
		wto.setAuditId(null);
		
		User user = userDao.get(wmb.getUserId());
		if(user != null){
			wto.setUserName(user.getName());
		}
		wTakeOutDao.insertOrder(wto);
		return 1;
	}
	
	/**
	 * 根据用户获取其返现列表
	 * @param session
	 * @return
	 */
	public List<WMonthBack> getMonthBackListByUser(AUser user) {
		/*String openId = (String)session.getAttribute("openId");
		AUser user = aUserDao.getUser(openId);*/
		if(user == null)
			return Lists.newArrayList();
		List<WMonthBack> list = wMonthBackDao.getListById(user.getId());
		if(list == null)
			list = Lists.newArrayList();
		return list;
	}

	public String getNameById(String comboId) {
		// TODO Auto-generated method stub
		return wMonthBackDao.getNameById(comboId);
	}
}
