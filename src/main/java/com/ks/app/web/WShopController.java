package com.ks.app.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.ks.app.entity.WShop;
import com.ks.app.service.WShopService;
import com.ks.app.utils.ActionResponse;
import com.ks.utils.Constant;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 门店相关Controller
 * @author pc-20170905
 *
 */
@Controller
@RequestMapping("/weixin/shop")
public class WShopController extends BaseController{

	@Autowired
	private WShopService wShopService;
	
	/**
	 * 获取全部门店
	 * @param model
	 * @return
	 */
	@RequestMapping("/getShopsList.do")
	public String getShopsList(Model model){
		List<WShop> shops= wShopService.getShopsList();
		model.addAttribute("shops", shops==null ? Lists.newArrayList() : shops);
		return "mobile/mendian";
	}
	
	/**
	 * 获取门店电话
	 * @param shopId
	 * @return
	 */
	@RequestMapping("/getShopTel.do")
	@ResponseBody
	public ActionResponse<Object> getShopTel(String shopId){
		ActionResponse<Object> returnData = new ActionResponse<Object>();
		try{
			WShop shop = wShopService.getShopById(shopId);
			String tel = shop.getShopTel();
			if(tel != null && tel != ""){
				returnData.setData(tel);
				returnData.setingSuccess(Constant.DEFAULT_SUCCESS_MSG);
			}else{
				returnData.setingError("暂无该门店电话");
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnData.setingError(Constant.DEFAULT_ERROR_MSG);
		}
		return returnData;
	}
	
	/**
	 * 根据套餐id查询所属门店
	 * @param comboId
	 * @return
	 */
	@RequestMapping("/getShopsByComboId.do")
	public String getShopsByComboId(String id, Model model){
		List<WShop> shops= wShopService.getShopsByComboId(id);
		model.addAttribute("shops", shops==null ? Lists.newArrayList() : shops);
		model.addAttribute("comboId", id);//将套餐id带入门店页面
		return "mobile/application_store_combo";
	}
	
	/**
	 * 根据项目id查询所有门店
	 * @param itemId
	 * @return
	 */
	@RequestMapping("/getShopsByItemId.do")
	public String getShopsByItemId(String id, Model model){
		List<WShop> shops= wShopService.getShopsByItemId(id);
		model.addAttribute("shops", shops==null ? Lists.newArrayList() : shops);
		model.addAttribute("itemId", id);//将项目id带入门店页面
		return "mobile/application_store";
	}
	
	/**
	 * 根据套餐id获取门店数量
	 * @param id
	 * @return
	 */
	@RequestMapping("/getNumOfShop.do")
	@ResponseBody
	public ActionResponse<Object> getNumOfShop(String id){
		ActionResponse<Object> returnData = new ActionResponse<Object>();
		int num = wShopService.getNumOfShop(id);
		returnData.setData(num);
		return returnData;
	}
	
	/**
	 * 根据项目id获取门店数量
	 * @param id
	 * @return
	 */
	@RequestMapping("/getItemNumOfShop.do")
	@ResponseBody
	public ActionResponse<Object> getItemNumOfShop(String id){
		ActionResponse<Object> returnData = new ActionResponse<Object>();
		int num = wShopService.getItemNumOfShop(id);
		returnData.setData(num);
		return returnData;
	}
	
	
	/**
	 * 根据门店id查询门店详情
	 * @param id
	 * @return
	 */
	@RequestMapping("/getShopById.do")
	@ResponseBody
	public ActionResponse<Object> getShopById(String id){
		ActionResponse<Object> returnData = new ActionResponse<Object>();
		WShop wShop= wShopService.getShopById(id);
		if(wShop != null){
			returnData.setData(wShop);
			returnData.setingSuccess(Constant.DEFAULT_SUCCESS_MSG);
		}else{
			returnData.setingError(Constant.DEFAULT_ERROR_MSG);
		}
		return returnData;
	}
}
