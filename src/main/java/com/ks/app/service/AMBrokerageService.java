package com.ks.app.service;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.ks.app.dao.AMBrokerageDao;
import com.ks.app.dao.AMUserDao;
import com.ks.app.entity.AMBrokerage;
import com.ks.app.entity.AMUser;
import com.ks.utils.EnumConstant.MBrokerageType;
import com.ks.utils.EnumConstant.RewardTypeX;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;

@Service
@Transactional
public class AMBrokerageService extends CrudService<AMBrokerageDao, AMBrokerage>{
	@Autowired
	private AUserService aUserService;
	@Autowired
	private AMUserDao amUserDao;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
	
	public List<AMBrokerage> getList(String id) {
		List<AMBrokerage> list = dao.getList(id);
		if(list==null){
			list = Lists.newArrayList();
		}else{
			for(int i = 0;i<list.size();i++){
				list.get(i).setTime(sdf.format(list.get(i).getCreateDate()));
				String userId = list.get(i).getUserId();
	        	if(StringUtils.isNoneBlank(userId)){
	        		String userName = aUserService.getNameById(list.get(i).getUserId());
	        		if(StringUtils.isNoneBlank(userName)){
	        			list.get(i).setUserName(userName);
	        		}
	        	}
	        	String consumerId = list.get(i).getConsumerId();
	        	if(StringUtils.isNoneBlank(consumerId)){
	        		AMUser uu = amUserDao.get(consumerId);
	        		if(uu!=null){
	        			userId = uu.getUserId();
	        			String consumerName =aUserService.getNameById(userId);
	        			if(StringUtils.isNoneBlank(consumerName)){
	        				list.get(i).setConsumerName(consumerName);
	        			}
	        		}
	        	}
	        	list.get(i).setType(MBrokerageType.mBrokerageType(list.get(i).getType()));
	        	String awa = list.get(i).getAwardLevel();
	        	if(StringUtils.isNoneBlank(awa)){
	        		list.get(i).setAwardLevel(RewardTypeX.rewardTypeX(awa));
	        	}
			}
		}
		return list;
	}

}
