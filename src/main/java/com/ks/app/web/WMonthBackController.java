package com.ks.app.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.ks.app.entity.AUser;
import com.ks.app.entity.WMonthBack;
import com.ks.app.service.WMonthBackService;
import com.ks.app.utils.LoginUtil;
import com.thinkgem.jeesite.common.web.BaseController;
@Controller
@RequestMapping(value = "/app/monthBack")
public class WMonthBackController  extends BaseController{
	
	@Autowired
	private WMonthBackService wMonthBackService;
	
	/**
	 * 根据用户获取其返现列表
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("getMonthBackListByUser.do")
	public String getMonthBackListByUser(HttpServletRequest request, Model model){
		AUser user = LoginUtil.weixinLoginUser(request);
		List<WMonthBack> list = Lists.newArrayList();
		if(user != null){
			wMonthBackService.getMonthBackListByUser(user);
		}
		model.addAttribute("list", list);
		return "mobile/returnclaim";
	}
	
	/**
	 * 微信端返现领取
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping("getById.do")
	public String getById(HttpSession session,HttpServletRequest req){
//		String openId = (String) session.getAttribute("openId");
//		String id = aUserService.getIdByOpenId(openId);
		AUser user = LoginUtil.weixinLoginUser(req);
		List<WMonthBack> list = Lists.newArrayList();
		if(user != null){
			list = wMonthBackService.getListById(user.getId());
			if(list.size()>0){
			String comboId = list.get(0).getComboId();
			String comboName = wMonthBackService.getNameById(comboId);
			for(int i = 0;i<list.size();i++){
				list.get(i).setComboName(comboName);
			}
		}
		}
		req.setAttribute("list", list);
		return "mobile/monthBackOfId";
	}
	
	/**
	 * 微信端 返现领取
	 * @param id
	 * @param req
	 * @return
	 */
	@RequestMapping("getReturn.do")
	public @ResponseBody int getReturn(String id,HttpServletRequest req){
		int row = wMonthBackService.getReturn(id, false);
		return row;
	}
}
