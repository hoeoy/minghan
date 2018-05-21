package com.ks.app.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ks.app.dao.AMRecordDao;
import com.ks.app.entity.AMOrder;
import com.ks.app.entity.AMRecord;
import com.thinkgem.jeesite.common.service.CrudService;
@Service
@Transactional
public class AMRecordService extends CrudService<AMRecordDao, AMRecord>{

	public void add(AMOrder order) {
		if(order!=null){
			AMRecord amr = new AMRecord();
			amr.setUserId(order.getUserId());
			amr.setOrderNo(order.getOrderNo());
			amr.setGoodsId(order.getGoodsId());
			amr.setGoodsName(order.getGoodsName());
			amr.setGoodsNum(order.getGoodsNum());
			amr.setGoodsPrice(order.getGoodsPrice());
			save(amr);
		}
		
	}

}
