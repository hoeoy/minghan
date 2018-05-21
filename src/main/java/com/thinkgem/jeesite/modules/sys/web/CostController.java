package com.thinkgem.jeesite.modules.sys.web;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Lists;
import com.ks.app.entity.WTakeOut;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.cmd.service.ComboService;
import com.thinkgem.jeesite.modules.sys.dao.BrokerageDao;
import com.thinkgem.jeesite.modules.sys.dao.OrderDao;
import com.thinkgem.jeesite.modules.sys.entity.Balance;
import com.thinkgem.jeesite.modules.sys.entity.Brokerage;
import com.thinkgem.jeesite.modules.sys.entity.Cost;
import com.thinkgem.jeesite.modules.sys.entity.Order;
import com.thinkgem.jeesite.modules.sys.service.BalanceService;
import com.thinkgem.jeesite.modules.sys.service.TakeOutService;

@Controller
@RequestMapping(value = "${adminPath}/sys/cost")
public class CostController extends  BaseController{
	@Autowired
	private BrokerageDao brokerageDao;
	@Autowired
	private BalanceService balanceService;
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private TakeOutService takeOutService;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
	
	@RequestMapping(value = "costIndex")
	public String financialCounting(Cost cost,HttpServletRequest request,Model model) throws ParseException{
		Balance balance = new Balance();
		Brokerage brokerage = new Brokerage();
		Order order = new Order();
		Cost c = new Cost();
		
		String start = request.getParameter("startTime");
		System.out.println("***************************start"+start);
		if(start!=null&&!("".equals(start))){
			Date startTime =sdf1.parse(start);
			balance.setStartTime(startTime);
			brokerage.setStartTime(startTime);
			order.setStartTime(startTime);
			c.setStartTime(startTime);
		}
		String end =request.getParameter("endTime");
		System.out.println("***************************end"+end);
		if(end!=null&&!("".equals(end))){
			Date endTime = sdf1.parse(end);
			balance.setEndTime(endTime);
			brokerage.setEndTime(endTime);
			order.setEndTime(endTime);
			c.setEndTime(endTime);
		}
		balance.setIndFlag("1");
		//计算充值 balanceX
		List<Balance> balanceList = balanceService.findList(balance);
		if(balanceList==null){
			balanceList = Lists.newArrayList();
		}
		BigDecimal balanceX = new BigDecimal(0);
		for(int i = 0;i<balanceList.size();i++){
			balanceX = balanceX.add(balanceList.get(i).getMoney());
		}
		//计算佣金奖励 brokerageX
		List<Brokerage> brokerageList = brokerageDao.findList(brokerage);
		if(brokerageList==null){
			brokerageList = Lists.newArrayList();
		}
		BigDecimal brokerageX = new BigDecimal(0);
		for(int i = 0;i<brokerageList.size();i++){
			brokerageX=	brokerageX.add(brokerageList.get(i).getMoney());
		}
		//计算业绩
		BigDecimal performanceX = new BigDecimal(0);
		List<BigDecimal> performanceList = orderDao.getDealPrice(order);
		if(performanceList==null){
			performanceList = Lists.newArrayList();
		}
		for(int i = 0;i<performanceList.size();i++){
			performanceX=	performanceX.add(performanceList.get(i));
		}
		c.setRecharge(balanceX);
		c.setBrokerage(brokerageX);
		c.setPerformance(performanceX);
		model.addAttribute("cost", c);
		return"modules/sys/costIndex";
	}
	
	

}
