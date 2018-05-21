package com.thinkgem.jeesite.modules.cmd.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.cmd.entity.Shop;
import com.thinkgem.jeesite.modules.cmd.service.ShopService;

/**
 * 门店相关Controller
 * @author pc-20170905
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/cmd/shop")
public class ShopController extends BaseController{

	@Autowired
	private ShopService shopService;
	
	/**
	 * 获取全部门店信息
	 * @param shop
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"list"})
	public String list(Shop shop, HttpServletRequest request, HttpServletResponse response, Model model){
		Page<Shop> page = shopService.getShopList(new Page<Shop>(request, response), shop);
		model.addAttribute("page", page);
		model.addAttribute("shop", shop);
		return "modules/cmd/shopList";
	}
	
	/**
	 * 保存
	 * @param shop
	 * @return
	 */
	@RequestMapping(value = {"save"})
	public String save(Shop shop){
		shopService.save(shop);
		return "redirect:" + adminPath + "/cmd/shop/list?repage";
	}
	
	/**
	 * 删除
	 * @param shop
	 * @return
	 */
	@RequestMapping(value = {"delete"})
	public String delete(Shop shop){
		shopService.delete(shop);
		return "redirect:" + adminPath + "/cmd/shop/list?repage";
	}
	
	/**
	 * 修改/新增
	 * @param shop
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"form"})
	public String form(Shop shop, Model model){
		shop = shopService.get(shop);
		if(shop == null){
			shop = new Shop();
		}
		model.addAttribute("shop", shop);
		return "modules/cmd/shopForm";
	}
	
}
