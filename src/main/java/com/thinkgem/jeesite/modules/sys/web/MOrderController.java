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
import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ks.app.service.AMOrderService;
import com.ks.utils.EnumConstant.OrderState;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.DetailValue;
import com.thinkgem.jeesite.modules.sys.entity.MGoods;
import com.thinkgem.jeesite.modules.sys.entity.MOrder;
import com.thinkgem.jeesite.modules.sys.entity.Order;
import com.thinkgem.jeesite.modules.sys.service.MOrderService;
import com.thinkgem.jeesite.modules.sys.service.MUserService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
@Controller
@RequestMapping(value = "${adminPath}/sys/morder")
public class MOrderController extends BaseController{
	@Autowired
	private MOrderService mOrderService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private MUserService mUserService;
	@Autowired
	private AMOrderService amOrderService;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
	
	@RequiresPermissions("sys:morder:edit")
	@RequestMapping(value = {"list"})
	public String list(MOrder order,HttpServletRequest request, HttpServletResponse response, Model model) throws ParseException{
		String start = request.getParameter("startTime");
		String end =request.getParameter("endTime");
		if(start!=null&&!("".equals(start))){
			Date startTime =sdf1.parse(start);
			order.setStartTime(startTime);
		}
		if(end!=null&&!("".equals(end))){
			Date endTime = sdf1.parse(end);
			order.setEndTime(endTime);
		}
		Page<MOrder> page = mOrderService.findPage(new Page<MOrder>(request,response), order);
		   List<MOrder> list = page.getList();
	        for(int i =0;i<list.size();i++){
	        	String userId = mUserService.getUserIdById(list.get(i).getUserId());
	        	String name = systemService.getNameById(userId);
	        	if(StringUtils.isNoneBlank(name)){
	        		list.get(i).setUserName(name);
	        	}
	        	list.get(i).setOrderState(OrderState.orderState(list.get(i).getOrderState()));
	        }
	        page.setList(list);
		model.addAttribute("page", page);
		model.addAttribute("order",order);
		return "modules/sys/morderList";
	}
	
	@RequiresPermissions("sys:morder:edit")
	@RequestMapping(value = {"form"})
	public String form(MOrder order,Model model){
		order = mOrderService.get(order);
		if(order == null){
			order = new MOrder();
		}
		model.addAttribute("order", order);
		return "modules/sys/morderForm";
	}
	
	@RequiresPermissions("sys:morder:edit")
	@RequestMapping(value = "save")
	public String save(MOrder order, HttpSession session,HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		Date date = new Date();
		String orderId = order.getId();
		String str = sdf.format(date);
		MOrder newOrder = mOrderService.get(orderId);
		order.setSendTime(str);
		mOrderService.save(order);
		addMessage(redirectAttributes, "修改物流信息成功");
		if(newOrder.getOrderState().equals(OrderState.ONE.getKey())){
			amOrderService.fahuo(orderId, session);
		}
		return "redirect:" + adminPath + "/sys/morder/list?repage";
	}

	@RequiresPermissions("sys:morder:edit")
	@RequestMapping(value="detailList")
	public String detailList (MOrder order,HttpServletRequest request,HttpServletResponse response,Model model) throws ParseException{
		
		String start = request.getParameter("startTime");
		String end =request.getParameter("endTime");
		Map<String,DetailValue> map = new HashMap<String,DetailValue>();
		 BigDecimal allMoney = new BigDecimal(0);
		if(start!=null&&!("".equals(start))){
			Date startTime =sdf1.parse(start);
			order.setStartTime(startTime);
		}
		if(end!=null&&!("".equals(end))){
			Date endTime = sdf1.parse(end);
			order.setEndTime(endTime);
		}
		Page<MOrder> page = mOrderService.findPageDetail(new Page<MOrder>(request,response), order);
		List<MOrder> list = page.getList();
		 for(int i =0;i<list.size();i++){
	        	String userId = mUserService.getUserIdById(list.get(i).getUserId());
	        	String name = systemService.getNameById(userId);
	        	if(StringUtils.isNoneBlank(name)){
	        		list.get(i).setUserName(name);
	        	}
	        	list.get(i).setOrderState(OrderState.orderState(list.get(i).getOrderState()));
	        }
	        page.setList(list);
		model.addAttribute("page", page);
		model.addAttribute("order",order);
		return "modules/sys/mDetailList";
	}
	
	@RequiresPermissions("sys:morder:edit")
	@RequestMapping(value="getFlag")
	public String getFlag(Model model,MOrder order,RedirectAttributes redirectAttributes){
		if(order==null){
			addMessage(redirectAttributes, "系统出错");
			return "redirect:" + adminPath + "/sys/morder/list?repage";
		}
		order.setGetFlag(1);
		mOrderService.updateFlag(order);
		addMessage(redirectAttributes, "领取成功");
		return "redirect:" + adminPath + "/sys/morder/list?repage";
	}
}
