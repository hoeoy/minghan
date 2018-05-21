package com.thinkgem.jeesite.modules.cmd.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.cmd.dao.ItemDao;
import com.thinkgem.jeesite.modules.cmd.entity.Item;
import com.thinkgem.jeesite.modules.cmd.entity.ItemImage;
import com.thinkgem.jeesite.modules.cmd.entity.OrderItem;
import com.thinkgem.jeesite.modules.cmd.entity.Reward;
import com.thinkgem.jeesite.modules.cmd.entity.VOitemReward;
import com.thinkgem.jeesite.modules.cmd.service.OrderItemService;
/**
 * 项目订单Controller
 * @version 
 */
@Controller
@RequestMapping(value = "${adminPath}/cmd/orderItem")
public class OrderItemController extends BaseController {
	
	@Autowired
	private OrderItemService orderItemService;
	@Autowired
	private ItemDao imteDao;
	
	/**
	 * 项目订单奖励详情
	 * @param orderItem
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"reward"})  
	public String reward(OrderItem orderItem, Model model){
		OrderItem order = orderItemService.getOrder(orderItem.getId());
		List<Reward> rewards = orderItemService.getRewardByOrderId(order.getId());
		VOitemReward vo = new VOitemReward();
		vo.setOrderItem(order);
		vo.setRewards(rewards);
		model.addAttribute("vo", vo);
		return "modules/cmd/rewardItemParticulas";
	}
	
	/**
	 * 获取项目详情
	 * @param orderCombo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"particulas"})  
	public String particulas(OrderItem orderItem, Model model){
		Item item = imteDao.get(orderItem.getId());
		//获取滚动图
		List<ItemImage> images = imteDao.getImagesById(item.getId());
		item.setImages(images);
		model.addAttribute("item", item);
		return "modules/cmd/orderItemParticulas";
	}
	
	/**
	 * 获取项目订单列表
	 * @param orderCombo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"list"})
	public String list(OrderItem orderItem, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OrderItem> page = orderItemService.findPage(new Page<OrderItem>(request, response), orderItem);
		model.addAttribute("page", page);
		return "modules/cmd/orderItemList";
	}
	
	/**
	 * 修改支付状态
	 * @param orderCombo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"form"})  
	public String form(OrderItem orderItem, Model model){
		orderItem = orderItemService.get(orderItem);
		if("0".equals(orderItem.getPayState())){
			orderItem.setPayState("1");
		}else {
			orderItem.setPayState("0");
		}
		orderItemService.save(orderItem);
		return "redirect:" + adminPath + "/cmd/orderItem/list?repage";
	}
	
}
