package com.ks.utils;

import org.springframework.beans.factory.annotation.Autowired;

import com.ks.app.dao.AUserDao;
import com.ks.app.entity.AUser;
import com.ks.utils.EnumConstant.Integral;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;


public class UserPoint {
	
	@Autowired
	private static AUserDao aUserDao = SpringContextHolder.getBean(AUserDao.class);
	
	public static void pointContent(String userId,String type){
		switch (type) {
			case "1"://供佛送的
				pointInsert(userId,Integral.FORBUDDHA.getKey());
				break;
			case "2"://回向送的
				pointInsert(userId,Integral.BACKTHE.getKey());
				break;
			case "3"://发帖送的
				pointInsert(userId,Integral.POSTING.getKey());
				break;
			case "4"://回复帖子送的
				pointInsert(userId,Integral.RETURNCARD.getKey());
				break;
			default:
				break;
		}
	}
	
	private static void pointInsert(String userId,Long integral){
		AUser auser;
		auser = aUserDao.get(userId);	
		if (auser!=null) {
			auser.setPoint(auser.getPoint()+integral);
			auser.preUpdate();
			aUserDao.update(auser);
		}
	}
}
