package com.ks.app.service;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.ks.app.dao.ABalanceDao;
import com.ks.app.entity.ABalance;
import com.ks.app.utils.WeixinUtil;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.Balance;
@Service
@Transactional
public class ABalanceService extends CrudService<ABalanceDao,ABalance> {
	@Autowired
	ABalanceDao abalancedao;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	public List<ABalance> getBalanceByUser(String id,int index) {
		int n = (index-1)*10;
		int s = WeixinUtil.BeautyDiaryCount;
		List <ABalance> list = abalancedao.getBalanceByUser(id,n,s);
		if(list==null){
			list = Lists.newArrayList();
		}
		 for(int i =0;i<list.size();i++){
	        	list.get(i).setTime(sdf.format(list.get(i).getCreateDate()));
	        }
		return list;
	}

	public int getBDPage(String id) {
		int allPage;
		int index =abalancedao.getBDPage(id);
		allPage=index/WeixinUtil.BeautyDiaryCount;
		if(index%WeixinUtil.BeautyDiaryCount!=0){
			allPage+=1;
		}
		return allPage;
	}
}

