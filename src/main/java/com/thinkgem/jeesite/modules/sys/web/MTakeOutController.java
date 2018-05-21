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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.MTakeOut;
import com.thinkgem.jeesite.modules.sys.entity.MUser;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.MTakeOutService;
import com.thinkgem.jeesite.modules.sys.service.MUserService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

@Controller
@RequestMapping(value = "${adminPath}/sys/mtakeout")
public class MTakeOutController extends BaseController{
	@Autowired
	private MUserService mUserService;
	@Autowired
	private MTakeOutService mTakeOutService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private UserDao userDao;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

	
	@RequiresPermissions("sys:mtakeout:edit")
	@RequestMapping(value = {"list"})
	public String list(MTakeOut takeout,HttpServletRequest request, HttpServletResponse response, Model model) throws ParseException{
		String start = request.getParameter("startTime");
		String end =request.getParameter("endTime");
		if(start!=null&&!("".equals(start))){
			Date startTime =sdf1.parse(start);
			takeout.setStartTime(startTime);
		}
		if(end!=null&&!("".equals(end))){
			Date endTime = sdf1.parse(end);
			takeout.setEndTime(endTime);
		}
		Page<MTakeOut> page = mTakeOutService.findPage(new Page<MTakeOut>(request,response), takeout);
		   List<MTakeOut> list = page.getList();
		   BigDecimal bd = new BigDecimal(0);
	        for(int i =0;i<list.size();i++){
	        	list.get(i).setTime(sdf.format(list.get(i).getCreateDate()));
	        	String userId = list.get(i).getUserId();
	        	if(StringUtils.isNoneBlank(userId)){
	        		MUser u = mUserService.get(userId);
	        		if(u!=null){
	        			User user = systemService.getUserById(u.getUserId());
	        			if(user!=null){
	        				list.get(i).setUserName(user.getName());
	        				list.get(i).setUserBankCard(user.getBankCard());
	        				list.get(i).setUserBankType(user.getBankType());
	        				list.get(i).setUserMobile(user.getMobile());
	        			}
	        		}
	        	}
	        		String str = list.get(i).getRemittanceGM();
	        		if(StringUtils.isNoneBlank(str)){
	        			User Gm = userDao.get(str);
	        			list.get(i).setRemittanceGM(Gm.getName());
	        	}
	        		BigDecimal shouxu = list.get(i).getShouxu();
	        		BigDecimal shida = list.get(i).getMoney().subtract(shouxu);
	        		list.get(i).setShiDa(shida);
	        		bd = bd.add(list.get(i).getShiDa());
	        }
	        page.setList(list);
	    model.addAttribute("bd", bd);
		model.addAttribute("page", page);
		model.addAttribute("takeout", takeout);
		return "modules/sys/mtakeOutList";
	}
	
	@RequiresPermissions("sys:mtakeout:edit")
	@RequestMapping(value = "transfer")
	public String transfer (MTakeOut takeout, RedirectAttributes redirectAttributes,HttpServletRequest req){
		Date date = new Date();
		String transferTime = sdf.format(date);
		takeout.setRemittanceTime(transferTime);
		User user = UserUtils.getUser();
		takeout.setRemittanceGM(user.getId());
		mTakeOutService.transfer(takeout);
		addMessage(redirectAttributes, "结算成功");
		return "redirect:" + adminPath + "/sys/mtakeout/list?repage";
	}
}
