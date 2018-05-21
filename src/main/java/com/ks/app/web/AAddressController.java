package com.ks.app.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.ks.app.entity.AAddress;
import com.ks.app.entity.AAddressArea;
import com.ks.app.entity.AMallAddress;
import com.ks.app.service.AAddressAreaService;
import com.ks.app.service.AAddressService;
import com.ks.app.service.AMallAddressService;
import com.ks.app.service.AUserService;
import com.ks.app.utils.ActionResponse;
import com.ks.app.utils.LoginUtil;
import com.ks.utils.Constant;
import com.ks.utils.EnumConstant.SelectState;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 收货地址Controller
 */
@Controller
@RequestMapping(value = "/app/address")
public class AAddressController extends BaseController {
	@Autowired
	private AUserService aUserService;
	@Autowired
	private AAddressService aAddressService;
	
	@RequestMapping(value="getMyaddress.do" ,produces = {"application/text;charset=UTF-8"})
	public String getMyaddress(HttpSession session,Model model,String flag){
		String openId = (String) session.getAttribute("openId");
		String id = aUserService.getIdByOpenId(openId);
		List<AAddress> list = aAddressService.getMyaddress(id);
		
		
		if(list.size()>0){
			model.addAttribute("list", list);
			if(StringUtils.isNoneBlank(flag)){
				return "mobile/address-choose";
			}else{
				return "mobile/address-list";
			}
		}else{
			return "mobile/address-empty";
		}
	}
	@RequestMapping(value="addAddress.do")
	public String addAddress(){
		return "mobile/address-create";
	}
	
	@RequestMapping(value="updateAddress.do")
	public String updateAddress(String id,Model model){
		AAddress address = new AAddress();
		address.setId(id);
		address = aAddressService.get(address);
		model.addAttribute("addr", address);
		return "mobile/address-create";
	}
	
	@RequestMapping(value="saveAddress.do")
	public String saveAddress(HttpSession session,String linkman,String mobile,String addr,String pcz,String id){
		String openId = (String) session.getAttribute("openId");
		String userId = aUserService.getIdByOpenId(openId);
		List<AAddress> list = aAddressService.getMyaddress(userId);
		if(list==null){
			list = Lists.newArrayList();
		}
		String  location[] = pcz.split("-");
		String province = "";
		String city = "";
		String zone = "";
		if(location.length>0){
			province = location[0];
		}
		if(location.length>1){
			city = location[1];
		}
		if(location.length>2){
			zone = location[2];
		}
		AAddress address = new AAddress();
		address.setId(id);
		address.setUserId(userId);
		address.setLinkman(linkman);
		address.setMobile(mobile);
		address.setAddr(addr);
		address.setProvince(province);
		address.setCity(city);
		address.setZone(zone);
		if(list.size()==0){
			address.setPrimaryFlag("1");
		}else{
			address.setPrimaryFlag("0");
		}
		aAddressService.save(address);
		return "redirect:" + "/app/address/getMyaddress.do";
	}
	
	@RequestMapping(value="deleteAddress.do")
		public String deleteAddress(String id){
		AAddress addr = new AAddress();
		addr.setId(id);
		aAddressService.delete(addr);
		return "redirect:" + "/app/address/getMyaddress.do";
		}
	
	@RequestMapping(value="updatePrimaryFlag.do")
	public void updatePrimaryFlag(String id){
		aAddressService.updatePrimaryFlag(id);
	}
	
}
