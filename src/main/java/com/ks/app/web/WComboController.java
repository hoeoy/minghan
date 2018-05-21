package com.ks.app.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.ks.app.entity.AUser;
import com.ks.app.entity.WCombo;
import com.ks.app.entity.WComboContract;
import com.ks.app.entity.WComboImage;
import com.ks.app.entity.WShop;
import com.ks.app.service.WComboService;
import com.ks.app.service.WShopService;
import com.ks.app.utils.ActionResponse;
import com.ks.app.utils.LoginUtil;
import com.ks.utils.Constant;
import com.thinkgem.jeesite.common.utils.SystemPath;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 套餐相关Controller
 * @author pc-20170905
 *
 */
@Controller
@RequestMapping("/weixin/combo")
public class WComboController extends BaseController{

	@Autowired
	private WComboService wComboService;
	@Autowired
	private WShopService wShopService;
	
	/**
	 * 点击支付下订单
	 * @param comboId
	 * @param session
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@RequestMapping("/payConfirm.do")
	@ResponseBody
	public ActionResponse<Object> payConfirm(String comboId, HttpServletRequest request) throws ParseException, IOException{
		ActionResponse<Object> returnData = new ActionResponse<Object>();
		AUser user = LoginUtil.weixinLoginUser(request);
		int row = -1;//没有用户
		if(user != null){
			row = wComboService.buyCombo(user, comboId);
			if(row == 1){
				returnData.setingSuccess("购买成功");
			}else if(row == 2){
				returnData.setingError("每人限购任意一款套餐");
			}else if(row == 3){
				returnData.setingError("您的余额不足");
			}else if(row == 4){
				returnData.setingError("您的个人信息不完善，请先完善个人信息");
			}else if(row == 5){
				returnData.setingError("您没有推荐人，暂时无法购物");
			}else if(row == 6){
				returnData.setingError("您的推荐人不是形象大使，您暂时无法购物");
			}else if(row == 7){
				returnData.setingError("您已成为形象大使，请勿重复购买");
			}
		}else{
			returnData.setingError(Constant.NO_USER);
		}
		returnData.setData(row);
		return returnData;
	}
	
	/**
	 * 点击购买套餐，展示购买信息，未下单
	 * @param session
	 * @param id 套餐id
	 * @return
	 */
	@RequestMapping("/buyCombo.do")
	public String buyCombo(String id, HttpServletRequest request, Model model){
		String prePath = SystemPath.getRequestPreUrl(request);
		WCombo combo = wComboService.getComboById(id,prePath,request);
		model.addAttribute("combo", combo);
		/*if(StringUtils.isNotBlank(shopId)){
			WShop shop = wShopService.getShopById(shopId);
			model.addAttribute("shop", shop);
		}*/
		return "mobile/meal_payment";
	}
	
	/**
	 * 获取所有形象大使信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/getComboList.do")
	@ResponseBody
	public ActionResponse<Object> getWComboList(HttpServletRequest request){
		ActionResponse<Object> returnData = new ActionResponse<Object>();
		String prePath = SystemPath.getRequestPreUrl(request);
		List<WCombo> comboList = wComboService.getComboList(prePath,request);
		if(comboList != null){
			returnData.setData(comboList);
			returnData.setingSuccess(Constant.DEFAULT_SUCCESS_MSG);
		}else{
			returnData.setingError(Constant.DEFAULT_ERROR_MSG);
		}
		return returnData;
	}
	
	/**
	 * 获取套餐详细信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/getComboById.do")
	public String getWComboById(String id, String shopId,HttpServletRequest request, Model model){
		String prePath = SystemPath.getRequestPreUrl(request);
		WCombo combo = wComboService.getComboById(id,prePath,request);
		model.addAttribute("combo", combo);
		List<WComboImage> images = combo.getImages();
		model.addAttribute("images", images==null ? Lists.newArrayList() : images);
		/*if(StringUtils.isNotBlank(shopId)){//获取门店
			WShop shop = wShopService.getShopById(shopId);
			model.addAttribute("shop", shop);
		}*/
		return "mobile/meal_detail";
	}
	
	/**
	 * 获取套餐合同详细信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/getComboContratsByComboId.do")
	public String getComboContratsByComboId(String id, Model model){
		List<WComboContract> contracts = wComboService.getComboContratsByComboId(id);
		model.addAttribute("contracts", contracts==null ? Lists.newArrayList() : contracts);
		return "mobile/meal_contract";
	}
	
	/**
	 * 查询套餐是否有合同
	 * @param id
	 * @return
	 */
	@RequestMapping("/hasComboContractsByComboId.do")
	@ResponseBody
	public ActionResponse<Object> hasComboContractsByComboId(String id){
		ActionResponse<Object> returnData = new ActionResponse<Object>();
		int flag = wComboService.hasContracts(id);
		returnData.setData(flag);
		return returnData;
	}
	
	
}










