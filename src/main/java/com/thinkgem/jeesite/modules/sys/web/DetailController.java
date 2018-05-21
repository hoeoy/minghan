package com.thinkgem.jeesite.modules.sys.web;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Lists;
import com.ks.app.entity.AUser;
import com.ks.app.service.AUserService;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Balance;
import com.thinkgem.jeesite.modules.sys.entity.DetailValue;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.BalanceService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;

@Controller
@RequestMapping(value = "${adminPath}/sys/detail")
public class DetailController extends BaseController{
	@Autowired
	private AUserService aUserServiec;
	@Autowired
	private BalanceService balanceService;
	@Autowired 
	private SystemService systemService;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
	
	@RequiresPermissions("sys:detail:view")
	@RequestMapping(value = {"index"})
	public String index(User user, Model model) {
		return "modules/sys/detailIndex";
	}
	
	@RequiresPermissions("sys:detail:view")
	@RequestMapping(value = {"list", ""})
	public String list(Balance balance, HttpServletRequest request, HttpServletResponse response, Model model) throws ParseException {
		String start = request.getParameter("startTime");
		Map<String,DetailValue> map = new HashMap<String,DetailValue>();
		System.out.println("***************************start"+start);
		if(start!=null&&!("".equals(start))){
			Date startTime =sdf1.parse(start);
			balance.setStartTime(startTime);
		}
		String end =request.getParameter("endTime");
		System.out.println("***************************end"+end);
		if(end!=null&&!("".equals(end))){
			Date endTime = sdf1.parse(end);
			balance.setEndTime(endTime);
		}
		Page<Balance> page = balanceService.findPage(new Page<Balance>(request, response), balance);
		  List<Balance> list = page.getList();
		  if(list==null){
			  list = Lists.newArrayList();
		  }
		  BigDecimal allMoney = new BigDecimal(0);
		  
	        for(int i =0;i<list.size();i++){
	        	allMoney = allMoney.add(list.get(i).getMoney());
	        	AUser user = aUserServiec.getUserById(list.get(i).getUserId());
	        	String operatorId = list.get(i).getOperator();
	        	if(StringUtils.isNoneBlank(operatorId)){
	        		User operatorUser = systemService.getUserById(operatorId);
	        		list.get(i).setOperator(operatorUser.getName());
	        	}
	        	if(StringUtils.isNotBlank(list.get(i).getOrderId())){
	        		String goodName = balanceService.getOrderNameById(list.get(i).getOrderId());
	        		list.get(i).setOrderId(goodName);
	        		if(map.containsKey(goodName)){
	        			DetailValue dv = map.get(goodName);
	        			dv.setNub(dv.getNub()+1);
	        			dv.setMoney(dv.getMoney().add(list.get(i).getMoney()));
	        			map.put(goodName,dv);
	        		}else{
	        			DetailValue dv = new DetailValue();
	        			dv.setNub(1);
	        			dv.setMoney(list.get(i).getMoney());
	        			map.put(goodName,dv);
	        		}
	        	}
	        	if(user != null){
	        		list.get(i).setUserId(user.getName());
	        	}
	        	list.get(i).setTime(sdf.format(list.get(i).getCreateDate()));
	        }
	        page.setList(list);
	    model.addAttribute("allMoney", allMoney);   
        model.addAttribute("page", page);
        model.addAttribute("map", map);
		return "modules/sys/detailList";
	}
	
	//sys_balanceId
	@RequiresPermissions("sys:detail:view")
	@RequestMapping(value = {"transactionDelete"})
	public String transactionDelete(String id){
		balanceService.transactionDelete(id);
		return "redirect:" + adminPath + "/sys/detail/list?repage";
	}
}
