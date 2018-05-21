package com.ks.app.service;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.ks.app.dao.WBrokerageDao;
import com.ks.app.entity.WBrokerage;
import com.ks.app.utils.WeixinUtil;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.Balance;
@Service
@Transactional
public class WUserBrokerageService  extends CrudService<WBrokerageDao, WBrokerage>{

	@Autowired
	WBrokerageDao wBrokerageDao;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public Object getBDPage(String id) {
		int allPage;
		int index =wBrokerageDao.getBDPage(id);
		allPage=index/WeixinUtil.BeautyDiaryCount;
		if(index%WeixinUtil.BeautyDiaryCount!=0){
			allPage+=1;
		}
		return allPage;
	}

	public List<WBrokerage> getBrokerageByUserId(String id,int index) {
		int n = (index-1)*10;
		int s = WeixinUtil.BeautyDiaryCount;
		List <WBrokerage> list = wBrokerageDao.getBrokerageByUser(id,n,s);
		if(list==null){
			list = Lists.newArrayList();
		}
		 for(int i =0;i<list.size();i++){
	        	list.get(i).setTime(sdf.format(list.get(i).getCreateDate()));
	        }
		return list;
	}

}
