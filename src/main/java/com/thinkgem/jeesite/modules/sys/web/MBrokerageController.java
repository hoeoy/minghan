package com.thinkgem.jeesite.modules.sys.web;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ks.app.dao.AMUserDao;
import com.ks.app.entity.AMUser;
import com.ks.utils.EnumConstant.RewardTypeX;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Brokerage;
import com.thinkgem.jeesite.modules.sys.entity.MBrokerage;
import com.thinkgem.jeesite.modules.sys.service.MBrokerageService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;

@Controller
@RequestMapping(value = "${adminPath}/sys/mbrokerage")
public class MBrokerageController extends BaseController{
	@Autowired
	private MBrokerageService mBrokerageService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private AMUserDao amUserDao;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
	
	@RequiresPermissions("sys:mbrokerage:view")
	@RequestMapping(value = {"list"})
	public String list(MBrokerage brokerage,HttpServletRequest request, HttpServletResponse response, Model model) throws ParseException{
		String start = request.getParameter("startTime");
		String end =request.getParameter("endTime");
		if(start!=null&&!("".equals(start))){
			Date startTime =sdf1.parse(start);
			brokerage.setStartTime(startTime);
		}
		if(end!=null&&!("".equals(end))){
			Date endTime = sdf1.parse(end);
			brokerage.setEndTime(endTime);
		}
		Page<MBrokerage> page = mBrokerageService.findPage(new Page<MBrokerage>(request,response), brokerage);
		   List<MBrokerage> list = page.getList();
		   BigDecimal bd = new BigDecimal(0);
	        for(int i =0;i<list.size();i++){
	        	list.get(i).setTime(sdf.format(list.get(i).getCreateDate()));
	        	String userId = list.get(i).getUserId();
	        	if(StringUtils.isNoneBlank(userId)){
	        	AMUser user = amUserDao.get(userId);
	        	if(user!=null){
	        		String userName = systemService.getNameById(user.getUserId());
	        		if(StringUtils.isNoneBlank(userName)){
	        			list.get(i).setUserName(userName);
	        		}
	        	}
	        	}
	        	String consumerId = list.get(i).getConsumerId();
	        	if(StringUtils.isNoneBlank(consumerId)){
	        		AMUser uu = amUserDao.get(consumerId);
	        		if(uu!=null){
	        			userId = uu.getUserId();
	        			String consumerName =systemService.getNameById(userId);
	        			if(StringUtils.isNoneBlank(consumerName)){
	        				list.get(i).setConsumerName(consumerName);
	        			}
	        		}
	        	}
	        	String awa = list.get(i).getAwardLevel();
	        	if(StringUtils.isNoneBlank(awa)){
	        		list.get(i).setAwardLevel(RewardTypeX.rewardTypeX(awa));
	        	}
	        	bd = bd.add(list.get(i).getMoney());
	        }
	        page.setList(list);
	    model.addAttribute("bd", bd);
		model.addAttribute("page", page);
		model.addAttribute("brokerage", brokerage);
		return "modules/sys/mbrokerageList";
	}

}
