package com.ks.app.service;



import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ks.app.dao.AAddressAreaDao;
import com.ks.app.entity.AAddressArea;
import com.thinkgem.jeesite.common.service.CrudService;

@Service
@Transactional
public class AAddressAreaService extends CrudService<AAddressAreaDao, AAddressArea>{
	
	public List<AAddressArea> addressAreaList(AAddressArea addressArea){
		addressArea.setPid("0");
		//查询1级分类
		List<AAddressArea> areaList = dao.findList(addressArea);
		//查询所有分类
		List<AAddressArea> allAreaList = dao.findList(new AAddressArea());
		if (areaList != null && areaList.size()>0) {
			for (AAddressArea addressAreaInfo : areaList) {
				List<AAddressArea> sub =addressAreaInfo.getSub();
				if(areaList != null && areaList.size()>0){
					for (AAddressArea allAreaInfo : allAreaList) {
						List<AAddressArea> sub2 = allAreaInfo.getSub();
						if (addressAreaInfo.getId().equals(allAreaInfo.getPid())) {
							
							for (AAddressArea allAreaInfo2 : allAreaList) {
								if (allAreaInfo2.getPid().equals(allAreaInfo.getId())) {
									sub2.add(allAreaInfo2);
								}
							}
							//3级分类
							allAreaInfo.setSub(sub2);
							//2级分类
							sub.add(allAreaInfo);
							
						}
					}
					addressAreaInfo.setSub(sub);
				}
			}
		}
		
		return areaList;
	}
	
	public List<AAddressArea> addressAreaListById(String id){
		AAddressArea addressArea = new AAddressArea();
		addressArea.setPid(id);
		return dao.findList(addressArea);
	}
	
}
