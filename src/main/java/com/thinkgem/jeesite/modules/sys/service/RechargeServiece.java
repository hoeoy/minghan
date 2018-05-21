package com.thinkgem.jeesite.modules.sys.service;

import java.math.BigDecimal;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ks.utils.OrderCodeUtil;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.dao.BalanceDao;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.Balance;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
@Service
@Transactional
public class RechargeServiece extends CrudService<BalanceDao,Balance>{
	@Autowired
	BalanceDao balancedao;
	
	@Autowired
	UserDao userDao;
	public void recharge(String id, BigDecimal bd,String explain) {
		User user = userDao.get(id);
		BigDecimal xx = user.getAccountMoney();
		if(xx==null){
			xx = new BigDecimal(0);
		}
		user.setAccountMoney(xx.add(bd));
		userDao.update(user);
		Balance b = new Balance();
		b.preInsert();
		b.setUserId(id);
		b.setMoney(bd);
		b.setIndFlag("1");
		b.setId("B"+OrderCodeUtil.getNo(id));
		b.setExplain(explain);
		User now = UserUtils.getUser();
		b.setOperator(now.getId());
		balancedao.recharge(b);
		
	}
	
}
