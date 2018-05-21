package com.thinkgem.jeesite.modules.sys.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.SysConstant;
import com.thinkgem.jeesite.modules.sys.service.SysConstantService;
@Controller
@RequestMapping(value = "${adminPath}/sys/phone")
public class PhoneController extends BaseController{
	
	@Autowired
	SysConstantService sysConstantService;
	
	@RequiresPermissions("sys:phone:view")
	@RequestMapping(value = {"index"})
	public String phoneIndex(Model model){
		SysConstant params = sysConstantService.getCustomPhoneDict();
		String phone = params.getValue();
		model.addAttribute("phone", phone);
		return "modules/sys/phoneIndex";
	}
	
	@RequiresPermissions("sys:phone:view")
	@RequestMapping(value = {"updatePhone"})
	public String updatePhone(HttpServletRequest req,Model model){
		String phone = req.getParameter("phone");
		sysConstantService.updatePhone(phone);
		model.addAttribute("message", "修改成功");
		return phoneIndex(model);
	}

}
