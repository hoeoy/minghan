package com.ks.app.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ks.app.entity.AMBrokerage;
import com.ks.app.entity.AMUser;
import com.ks.app.service.AMBrokerageService;
import com.ks.app.service.AMUserService;
import com.ks.app.service.AUserService;
import com.ks.app.utils.ActionResponse;
import com.thinkgem.jeesite.common.web.BaseController;
@Controller
@RequestMapping(value="/app/mbrokerage")
public class AMBrokerageController extends BaseController{
	@Autowired
	private AMBrokerageService amBrokerageService;
	@Autowired
	private AUserService aUserService;
	@Autowired
	private AMUserService amUserService;
	
	@RequestMapping(value="getList.do")
	public String getList(HttpSession session,Model model){
		String openId=(String)session.getAttribute("openId");
		String id = aUserService.getIdByOpenId(openId);
		AMUser user = amUserService.getMuserByUserId(id);
		if(user!=null){
			List<AMBrokerage> list = amBrokerageService.getList(user.getId());
			model.addAttribute("list", list);
		}
		return "mobile/commissions";
	}
	
	

}
