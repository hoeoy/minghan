package com.thinkgem.jeesite.modules.sys.web;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.h2.engine.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.drew.lang.StringUtil;
import com.ks.app.entity.AUser;
import com.ks.app.service.AUserService;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Balance;
import com.thinkgem.jeesite.modules.sys.entity.Doctor;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.BalanceService;
import com.thinkgem.jeesite.modules.sys.service.RechargeExService;
import com.thinkgem.jeesite.modules.sys.service.RechargeServiece;
import com.thinkgem.jeesite.modules.sys.service.SystemService;

@Controller
@RequestMapping(value = "${adminPath}/sys/recharge")
public class RechargeController extends BaseController {
	
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private RechargeServiece rechargeServiece;
	@Autowired
	private AUserService aUserServiec;
	@Autowired
	private RechargeExService rexService;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
	
	@RequiresPermissions("sys:recharge:view")
	@RequestMapping(value = {"index"})
	public String index(User user, Model model) {
		return "modules/sys/rechargeIndex";
	}
	@RequiresPermissions("sys:recharge:view")
	@RequestMapping(value = {"list", ""})
	public String list(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		user.setStartFlag("0");
		Page<User> page = systemService.findUser(new Page<User>(request, response), user);
        model.addAttribute("page", page);
        model.addAttribute("user", user);
		return "modules/sys/rechargeList";
	}
	
	@RequiresPermissions("sys:recharge:view")
	@RequestMapping(value = {"form"})
	public String form(Model model,HttpServletRequest req){
		String id = req.getParameter("id");
		User user = systemService.getUserById(id);
		model.addAttribute("user", user);
		return "modules/sys/rechargeForm";
	}
	
	@RequiresPermissions("sys:recharge:view")
	@RequestMapping(value = "save")
	public String save(User user, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		BigDecimal bd = new BigDecimal(request.getParameter("money"));
		String str = request.getParameter("explain");
		rechargeServiece.recharge(user.getId(),bd,str);
		addMessage(redirectAttributes, user.getName() + "充值成功");
		return "redirect:" + adminPath + "/sys/recharge/list?repage";
	}
	
	@RequiresPermissions("sys:recharge:view")
	@RequestMapping(value = "exList")
	public String exList(User user,HttpServletRequest request, HttpServletResponse response, Model model,HttpSession session) throws ParseException{
		session.removeAttribute("user");
		session.setAttribute("user", user);
		Balance b = new Balance();
		b.setUserId(user.getId());
		String start = request.getParameter("startTime");
		System.out.println("***************************start"+start);
		if(start!=null&&!("".equals(start))){
			Date startTime =sdf1.parse(start);
			b.setStartTime(startTime);
		}
		String end =request.getParameter("endTime");
		System.out.println("***************************end"+end);
		if(end!=null&&!("".equals(end))){
			Date endTime = sdf1.parse(end);
			b.setEndTime(endTime);
		}
		Page<Balance> page = rexService.findPage(new Page<Balance>(request,response), b);
		  List<Balance> list = page.getList();
	        for(int i =0;i<list.size();i++){
	        	String operatorId = list.get(i).getOperator();
	        	if(StringUtils.isNoneBlank(operatorId)){
	        		User operatorUser = systemService.getUserById(operatorId);
	        		list.get(i).setOperator(operatorUser.getName());
	        	}
	        	AUser u = aUserServiec.getUserById(list.get(i).getUserId());
	        	if(u != null){
	        		list.get(i).setUserId(u.getName());
	        	}
	        	list.get(i).setTime(sdf.format(list.get(i).getCreateDate()));
	        }
	        page.setList(list);
      model.addAttribute("page", page);
		return "modules/sys/exList";
	}
	
//	@RequiresPermissions("sys:recharge:view")
//	@RequestMapping(value = "exListTime")
//	public String exListTime(HttpServletRequest request, HttpServletResponse response, Model model,HttpSession session) throws ParseException{
//		User user = (User) session.getAttribute("user");
//		Balance b = new Balance();
//		b.setUserId(user.getId());
//		String start = request.getParameter("startTime");
//		System.out.println("***************************start"+start);
//		if(start!=null&&!("".equals(start))){
//			Date startTime =sdf1.parse(start);
//			b.setStartTime(startTime);
//		}
//		String end =request.getParameter("endTime");
//		System.out.println("***************************end"+end);
//		if(end!=null&&!("".equals(end))){
//			Date endTime = sdf1.parse(end);
//			b.setEndTime(endTime);
//		}
//		Page<Balance> page = rexService.findPage(new Page<Balance>(request,response), b);
//		  List<Balance> list = page.getList();
//	        for(int i =0;i<list.size();i++){
//	        	AUser u = aUserServiec.getUserById(list.get(i).getUserId());
//	        	if(u != null){
//	        		list.get(i).setUserId(u.getName());
//	        	}
//	        	list.get(i).setTime(sdf.format(list.get(i).getCreateDate()));
//	        }
//	        page.setList(list);
//      model.addAttribute("page", page);
//		return "modules/sys/exList";
//		
//	}

}
