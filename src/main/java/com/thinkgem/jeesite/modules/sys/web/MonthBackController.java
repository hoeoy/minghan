package com.thinkgem.jeesite.modules.sys.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.Page;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.DetailValue;
import com.thinkgem.jeesite.modules.sys.entity.MonthBack;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.MonthBackService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
@Controller
@RequestMapping(value = "${adminPath}/sys/month")
public class MonthBackController extends BaseController {
	@Autowired
	private MonthBackService monthBackService;
	@Autowired
	private SystemService systemService;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

	@RequiresPermissions("sys:month:view")
	@RequestMapping(value = {"list"})
	public String list(MonthBack mb, HttpServletRequest request, HttpServletResponse response, Model model) throws ParseException{
		Page<MonthBack> page = monthBackService.findPage(new Page<MonthBack>(request,response), mb);
		List<MonthBack> list = page.getList();
		if(list==null){
			list = Lists.newArrayList();
		}
		for(int i =0;i<list.size();i++){
			list.get(i).setStartTimeString(sdf.format(list.get(i).getStartTime()));
			list.get(i).setComboId(monthBackService.getComboNameById(list.get(i).getComboId()));
			User user = systemService.getUserById(list.get(i).getUserId());
			if(user==null){
				user = new User();
			}
			list.get(i).setUserId(user.getName());
		}
		model.addAttribute("page", page);
		return "modules/sys/monthBackList";
	}
}
