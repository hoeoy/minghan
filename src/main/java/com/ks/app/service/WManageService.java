package com.ks.app.service;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.ks.app.dao.AUserDao;
import com.ks.app.dao.WMonthBackDao;
import com.ks.app.dao.WTakeOutDao;
import com.ks.app.entity.AUser;
import com.ks.app.entity.WTakeOut;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.IdGen;

@Service
@Transactional(readOnly=false, rollbackFor=Exception.class)
public class WManageService extends CrudService<WTakeOutDao, WTakeOut>{

	@Autowired
	private WTakeOutDao wTakeOutDao;
	@Autowired
	private WMonthBackDao wMonthBackDao;
	@Autowired
	private AUserDao aUserDao;

	/**
	 * 提现审核，点击确认
	 * @param orderId
	 * @return
	 */
	public int changeFlag(String orderId, String userId){
		//若为12月返现，还需修改month_back_info中的审核状态
		String MonthBackId = wTakeOutDao.getMonthBackId(orderId);
		if(MonthBackId != null || MonthBackId != ""){
			wMonthBackDao.changeAuditFlag(MonthBackId);
		}
		int row = wTakeOutDao.changeFlag(orderId, userId);
		return row;
	}
	
	public int insertOrder(){
		WTakeOut takeout = new WTakeOut();
		takeout.setId(IdGen.uuid());
		takeout.setType("0");
		return wTakeOutDao.insertOrder(takeout);
	}
	
	/**
	 * 获取全部提现订单
	 * @param session
	 * @return
	 */
	public List<WTakeOut> getList(WTakeOut out) {
		List<WTakeOut> list = wTakeOutDao.getList(out);
		if(list == null)
			return Lists.newArrayList();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		for(WTakeOut takeout : list){
			takeout.setStringCreateDate(sdf.format(takeout.getCreateDate()));
		}
		return list;
	}
	/**
	 * 获取提现订单---分页
	 * @param session
	 * @return
	 */
	public Page<WTakeOut> getPage(Page<WTakeOut> page, WTakeOut out) {
		out.setPage(page);
		page.setList(dao.getList(out));
		List<WTakeOut> list = page.getList();
		if(list != null && list.size()>0){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			for (WTakeOut tmp: list) {
				tmp.setStringCreateDate(sdf.format(tmp.getCreateDate()));
				//添加受益人名字
				AUser user = aUserDao.getUserById(tmp.getUserId());
				if(user!=null){
					tmp.setUserName(user.getName());
				}
			}
		}
		return page;
	}

}
