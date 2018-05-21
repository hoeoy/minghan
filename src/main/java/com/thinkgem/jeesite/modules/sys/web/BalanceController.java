package com.thinkgem.jeesite.modules.sys.web;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Balance;
import com.thinkgem.jeesite.modules.sys.service.BalanceService;

@Controller
@RequestMapping(value = "${adminPath}/sys/balance")
public class BalanceController extends BaseController {
	@Autowired
	private BalanceService balanceService;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@RequiresPermissions("sys:detail:view")
	@RequestMapping(value = {"list"})
	public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
		String id=request.getParameter("id");
		Balance balance = new Balance();
		balance.setUserId(id);
		Page<Balance> page = balanceService.findPage(new Page<Balance>(request,response), balance);
        List<Balance> list = page.getList();
        for(int i =0;i<list.size();i++){
        	if(list.get(i).getCreateDate()!=null){
        	list.get(i).setTime(sdf.format(list.get(i).getCreateDate()));
        }
        }
        page.setList(list);
		model.addAttribute("page", page);
		return "modules/sys/balanceList";
	}
	
	
	
	

}
