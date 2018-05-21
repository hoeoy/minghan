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
import com.thinkgem.jeesite.modules.cmd.dao.ComboDao;
import com.thinkgem.jeesite.modules.cmd.entity.Combo;
import com.thinkgem.jeesite.modules.cmd.entity.ComboContract;
import com.thinkgem.jeesite.modules.cmd.entity.ComboImage;
import com.thinkgem.jeesite.modules.cmd.entity.OrderCombo;
import com.thinkgem.jeesite.modules.cmd.entity.Reward;
import com.thinkgem.jeesite.modules.cmd.entity.VOcomboReward;
import com.thinkgem.jeesite.modules.cmd.service.ComboService;
import com.thinkgem.jeesite.modules.cmd.service.OrderComboService;
/**
 * 套餐订单Controller
 * @version 
 */
@Controller
@RequestMapping(value = "${adminPath}/cmd/orderCombo")
public class OrderComboController extends BaseController {
	
	@Autowired
	private OrderComboService orderComboService;
	@Autowired
	private ComboService comboService;
	@Autowired
	private ComboDao comboDao;
	
	/**
	 * 套餐订单奖励详情
	 * @param orderCombo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"reward"})
	public String reward(OrderCombo orderCombo, Model model){
		String orderId = orderCombo.getId();
		OrderCombo order = orderComboService.getOrder(orderId);
		List<Reward> rewards = orderComboService.getRewardByOrderId(orderId);
		VOcomboReward vo = new VOcomboReward();
		vo.setOrderCombo(order);
		vo.setRewards(rewards);
		model.addAttribute("vo", vo);
		return "modules/cmd/rewardComboParticulas";
	}
	
	/**
	 * 获取套餐详情
	 * @param orderCombo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"particulas"})
	public String particulas(OrderCombo orderCombo, Model model){
		Combo combo = comboService.get(orderCombo.getId());
		if(combo != null){
			//获取滚动图和合同图
			List<ComboImage> iamges = comboDao.getComboImagesByComboId(combo.getId());
			if(iamges != null){
				combo.setImages(iamges);
			}
			List<ComboContract> contracts = comboDao.getComboContractsByComboId(combo.getId());
			if(contracts != null){
				combo.setContracts(contracts);
			}
		}
		model.addAttribute("combo", combo == null ? new Combo():combo);
		return "modules/cmd/orderComboParticulas";
	}
	
	/**
	 * 获取套餐订单列表
	 * @param orderCombo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"list"})
	public String list(OrderCombo orderCombo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OrderCombo> page = orderComboService.findPage(new Page<OrderCombo>(request, response), orderCombo);
		model.addAttribute("page", page);
//		model.addAttribute("orderCombo", orderCombo);
		return "modules/cmd/orderComboList";
	}
	
	/**
	 * 修改支付状态
	 * @param orderCombo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"form"})  
	public String form(OrderCombo orderCombo, Model model){
		orderCombo = orderComboService.get(orderCombo);
		if("0".equals(orderCombo.getPayState())){
			orderCombo.setPayState("1");
		}else {
			orderCombo.setPayState("0");
		}
		orderComboService.save(orderCombo);
		return "redirect:" + adminPath + "/cmd/orderCombo/list?repage";
	}
	
}
