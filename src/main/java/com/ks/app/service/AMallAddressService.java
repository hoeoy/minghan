package com.ks.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ks.app.dao.AMallAddressDao;
import com.ks.app.entity.AAddressArea;
import com.ks.app.entity.AMallAddress;
import com.ks.utils.Constant;
import com.ks.utils.EnumConstant.SelectState;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.User;
@Service
@Transactional
public class AMallAddressService extends CrudService<AMallAddressDao,AMallAddress >{
	@Autowired
	private AAddressAreaService aAddressAreaService;
	public  Page<AMallAddress> findAddressPage(AMallAddress address,Integer pageNo,Integer pageSize){
		pageNo = pageNo == null ? 1:pageNo;
		pageSize = pageSize == null ? Integer.valueOf(Global.getConfig("page.pageSize")):pageSize;
		Page<AMallAddress> page = new Page<AMallAddress>(pageNo, pageSize);
		
		Page<AMallAddress> result = super.findPage(page, address);
		if(result != null){
			List<AMallAddress> mallAddressList = result.getList();
			if(mallAddressList != null && mallAddressList.size()>0){
				for (AMallAddress mallAddressInfo : mallAddressList) {
					//添加省市区的名称
					geProvinces(mallAddressInfo);
				}
			}
		}
		return result;
	}

	public void insertAddress(AMallAddress address,String userId){
		if(StringUtils.isBlank(address.getSelected())){
			address.setSelected(SelectState.NO.getKey());
		}
		address.setApp(true);
		address.setCurUserId(userId);
		address.setDelFlag(Constant.DELETE_FLAG_0);
		address.preInsert();
		dao.insert(address);
		
		if(SelectState.YES.getKey().equals(address.getSelected())){
			//取消除新增的记录为选中之外其他不是默认
			dao.updateAddressSelected(address);
		}
	}

	public AMallAddress getAddressById(AMallAddress address){
		AMallAddress  addressInfo = dao.get(address);
		//添加省市区的名称
		geProvinces(addressInfo);
		return addressInfo;
	}
	
	public void updateAddress(AMallAddress address,String userId){
		address.setApp(true);
		address.setCurUserId(userId);
		address.preUpdate();
		dao.update(address);
	}
	public void deleteAddressById(String id){
		AMallAddress address = new AMallAddress();
		address.setId(id);
		dao.delete(address);
	}
	public void updateAddressSelected(String id,String selected,String userId){
		AMallAddress address = new AMallAddress();
		address.setId(id);
		address.setSelected(selected);
		
		User user = new User(userId);
		address.setCreateBy(user);
		
		dao.updateAddressSelected(address);
	}
	//省市区返回
	public void geProvinces(AMallAddress addressInfo){
		if(addressInfo!=null){
			//查询省市区
			AAddressArea aAddressArea = new AAddressArea();
			String province = addressInfo.getProvince();
			if (province != null && province.length() > 0) {
				//省
				aAddressArea.setId(province);
				addressInfo.setProvinceName(aAddressAreaService.get(aAddressArea).getName());
			}
			//市
			String town =addressInfo.getTown();
			if (town != null && town.length() > 0) {
				aAddressArea.setId(town);
				addressInfo.setTownName(aAddressAreaService.get(aAddressArea).getName());
			}
			//区zone
			String zone=addressInfo.getZone();
			if (zone != null && zone.length() > 0) {
				aAddressArea.setId(zone);
				addressInfo.setZoneName(aAddressAreaService.get(aAddressArea).getName());
			}
		}
	}
}
