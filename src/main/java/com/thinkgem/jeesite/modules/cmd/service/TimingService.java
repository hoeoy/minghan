package com.thinkgem.jeesite.modules.cmd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ks.app.service.WMonthBackService;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.dao.MonthBackDao;
import com.thinkgem.jeesite.modules.sys.entity.MonthBack;

@Service
@Lazy(false)
@Transactional
public class TimingService extends CrudService<MonthBackDao, MonthBack>{
	
	@Autowired
	private MonthBackDao monthBackDao;
	@Autowired
	private WMonthBackService wMonthBackService;
	
	//每月26日0点0分0秒开始
	@Scheduled(cron = "0 0 0 26 * ?")
	public void run() {
		//12月返现，获取所有auditFlag为0未开始，state为0未领取的全部记录
		List<MonthBack> list = monthBackDao.getCanCheck();
		/*//判断日期是否可以领取
		Iterator<MonthBack> iterator = list.iterator();
		while(iterator.hasNext()){
			MonthBack monthBack = iterator.next();
			if(){
				
			}
		}*/
		//修改状态，自动领取
		for(MonthBack mb : list){
			wMonthBackService.getReturn(mb.getId(), true);
		}
	}
	
	/**
	 * 数据备份
	 */
	/*@Scheduled(cron = "0 0 0 26 * ?")
	public void Databackups(){
		
	}*/
	
	/*int n = 0;
	@Scheduled(cron = "0/10 * * * * ?")
	public void run() {
		System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date())+""+n++);
	}*/
	
}
