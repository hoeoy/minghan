package com.thinkgem.jeesite.modules.cmd.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.cmd.dao.RateDao;
import com.thinkgem.jeesite.modules.cmd.dao.TakeOutDao;
import com.thinkgem.jeesite.modules.cmd.entity.Rate;
import com.thinkgem.jeesite.modules.cmd.entity.TakeOut;

@Service
@Transactional(readOnly=false)
public class ManageService extends CrudService<TakeOutDao, TakeOut>{

	@Autowired
	private TakeOutDao takeOutDao;
	@Autowired
	private RateDao rateDao;

	public int changeFlag(String orderId, String userId){
		
		return takeOutDao.changeFlag(orderId, userId);
	}
	
	public int insertOrder(){
		TakeOut takeout = new TakeOut();
//		takeout.setId(IdGen.uuid());
		takeout.setType("0");
		takeout.preInsert();
		return takeOutDao.insertOrder(takeout);
	}
	
	public List<TakeOut> getList() {
		List<TakeOut> list = takeOutDao.getList();
		if(list != null){
			return list;
		}
		return new ArrayList<TakeOut>();
	}

	public Page<TakeOut> getTakeOutList(Page<TakeOut> page, TakeOut takeOut) {
		if(takeOut.getId() != null){//若传入了orderId则只查询该条记录
			TakeOut order = takeOutDao.getTakeOutById(takeOut.getId());
			List<TakeOut> list = new ArrayList<TakeOut>();
			list.add(order);
			page.setList(list);
			return page;
		}
		Page<TakeOut> takeOutPage = super.findPage(page, takeOut);
		return takeOutPage;
	}

	/**
	 * 获取所有比例
	 * @return
	 */
	public List<Rate> getRateList() {
		List<Rate> rates = new ArrayList<Rate>();
		for(int i=15; i<=28; i++){
			Rate rate = rateDao.get(i+"");
			rates.add(rate);
		}
		return rates;
	}

	public Rate getRate(Rate rate) {
		rate = rateDao.get(rate);
		return rate;
	}

	public void saveRate(Rate rate) {
		rate.preUpdate();
		rateDao.update(rate);
	}

}
