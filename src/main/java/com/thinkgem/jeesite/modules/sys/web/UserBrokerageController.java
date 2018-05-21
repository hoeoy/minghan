package com.thinkgem.jeesite.modules.sys.web;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Brokerage;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.UserBrokerageService;

/**
 * 展示佣金明细
 * @author pc-20170905
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/userBrokerage")
public class UserBrokerageController extends BaseController {
	
	@Autowired
	private UserBrokerageService userBrokerageService;
	
	@RequestMapping(value = {"index"})
	public String index(User user, Model model) {
		return "modules/sys/userBrokerageIndex";
	}

	@RequestMapping(value = {"list"})
	public String list(Brokerage brokerage, HttpServletRequest request, HttpServletResponse response, Model model) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String start = request.getParameter("start");
		//System.out.println("***************************start"+start);
		if(start!=null&&!("".equals(start))){
			Date startTime =sdf.parse(start);
			brokerage.setStartTime(startTime);
		}
		String end =request.getParameter("end");
		//System.out.println("***************************end"+end);
		if(end!=null&&!("".equals(end))){
			Date endTime = sdf.parse(end);
			brokerage.setEndTime(endTime);
		}
		Page<Brokerage> page = userBrokerageService.getBrokerageList(new Page<Brokerage>(request, response), brokerage);
		model.addAttribute("page", page);
		BigDecimal allMoney = new BigDecimal(0);
		List<Brokerage> list = page.getList();
		if(list==null){
			list = Lists.newArrayList();
		}
		for(int i=0;i<list.size();i++){
			allMoney = allMoney.add(list.get(i).getMoney());
		}
		model.addAttribute("allMoney", allMoney);
		model.addAttribute("brokerage", brokerage);
		return "modules/sys/userBrokerageList";
	}
	
}
