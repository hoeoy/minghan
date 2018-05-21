package com.ks.app.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.ks.app.entity.AUser;
import com.ks.app.entity.WOrder;
import com.ks.app.service.WOrderService;
import com.ks.app.utils.ActionResponse;
import com.ks.app.utils.LoginUtil;
import com.ks.utils.Constant;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 订单相关Controller
 * @author pc-20170905
 *
 */
@Controller
@RequestMapping("/weixin/order")
public class WOrderController extends BaseController{
	
	@Autowired
	private WOrderService wOrderService;
	
	/**
	 * 获取用户全部消费记录
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/getRecordList.do")
	public String getRecordList(HttpServletRequest request, Model model){
//		AUser user = aUserDao.getUser((String)session.getAttribute("openId"));
		List<WOrder> orders = Lists.newArrayList();
		int allPage = 0;
		AUser user = LoginUtil.weixinLoginUser(request);
		if(user != null){
			orders = wOrderService.getOrdersByUserIdByPage(user.getId(), 1);
			allPage = wOrderService.getNumOfOrdersByUserId(user.getId());
		}
		model.addAttribute("orders", orders);
		model.addAttribute("allPage", allPage);
		return "mobile/record";
	}
	/**
	 * 分页获取用户全部消费记录
	 * @param session
	 * @param page
	 * @return
	 */
	@RequestMapping("/getRecordListLimit.do")
	@ResponseBody
	public ActionResponse<Object> getRecordListLimit(HttpServletRequest request, String page){
		ActionResponse<Object> returnData = new ActionResponse<Object>();
//		AUser user = aUserDao.getUser((String)session.getAttribute("openId"));
		AUser user = LoginUtil.weixinLoginUser(request);
		List<WOrder> orders = null;
		if(user != null)
			orders = wOrderService.getOrdersByUserIdByPage(user.getId(), Integer.parseInt(page));
		if(orders == null){
			orders = Lists.newArrayList();
		}
		returnData.setData(orders);
		return returnData;
	}
	
	/**
	 * 获取当前用户的项目订单
	 * @param session
	 * @return
	 */
	@RequestMapping("/getItemOrderByUser.do")
	public String getItemOrderByUser(HttpServletRequest request, Model model){
		List<WOrder> orders = Lists.newArrayList();
		AUser user = LoginUtil.weixinLoginUser(request);
		if(user != null){
			orders = wOrderService.getItemOrderByUser(user);
		}else{
			String url = request.getRequestURI();
			return LoginUtil.grantUser(request, url);
		}
		model.addAttribute("orders", orders);
		return "mobile/my_projectorder";
	}
	
	/**
	 * 根据用户id查订单
	 * @param userId
	 * @return
	 */
	@RequestMapping("/getOrdersByUserId.do")
	@ResponseBody
	public ActionResponse<Object> getOrdersByUserId(String userId){
		ActionResponse<Object> returnData = new ActionResponse<Object>();
		List<WOrder> orders = wOrderService.getOrdersByUserId(userId);
		if(orders != null){
			returnData.setData(orders);
			returnData.setingSuccess(Constant.DEFAULT_SUCCESS_MSG);
		}else{
			returnData.setMsg("您还未购买过商品");
		}
		return returnData;
	}
	
	/**
	 * 根据改价人id、订单号改成交价
	 * @param price 修改后价格
	 * @param changePriceId 改价人id
	 * @param outTradeId 订单号
	 * @return
	 */
	@RequestMapping("/changeDealPrice.do")
	@ResponseBody
	public ActionResponse<Object> changeDealPrice(Integer price, String changePriceId, String outTradeId){
		ActionResponse<Object> returnData = new ActionResponse<Object>();
		int row = wOrderService.changeDealPrice(price, changePriceId, outTradeId);
		if(row == 1){
			returnData.setingSuccess("改价成功");
		}else{
			returnData.setMsg("改价失败，请您稍后重试");
		}
		return returnData;
	}
	
	/**
	 * 项目生成订单
	 * @param userId
	 * @param comboId
	 * @return
	 */
//	@RequestMapping("/buildItemOrder.do")
//	@ResponseBody
//	public ActionResponse<Object> buildItemOrder(String userId, String itemId){
//		ActionResponse<Object> returnData = new ActionResponse<Object>();
//		int row = wOrderService.buildItemOrder(userId, itemId);
//		if(row == 1){
//			returnData.setingSuccess("项目购买成功");
//		}else{
//			returnData.setMsg("套餐购买失败，请您稍后重试");
//		}
//		return returnData;
//	}
	
	/**
	 * 套餐生成订单
	 * @param userId
	 * @param comboId
	 * @return
	 */
//	@RequestMapping("/buildComboOrder.do")
//	@ResponseBody
//	public ActionResponse<Object> buildComboOrder(String userId, String comboId){
//		ActionResponse<Object> returnData = new ActionResponse<Object>();
//		int row = wOrderService.buildComboOrder(userId, comboId);
//		if(row == 1){
//			returnData.setingSuccess("套餐购买成功");
//		}else{
//			returnData.setMsg("套餐购买失败，请您稍后重试");
//		}
//		return returnData;
//	}

}
