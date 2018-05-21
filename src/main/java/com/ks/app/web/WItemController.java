package com.ks.app.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.ks.app.entity.AMGoodsType;
import com.ks.app.entity.AUser;
import com.ks.app.entity.WItem;
import com.ks.app.entity.WItemType;
import com.ks.app.entity.WOrder;
import com.ks.app.service.AMGoodsTypeService;
import com.ks.app.service.WItemService;
import com.ks.app.service.WItemTypeService;
import com.ks.app.service.WOrderService;
import com.ks.app.utils.ActionResponse;
import com.ks.app.utils.LoginUtil;
import com.ks.utils.Constant;
import com.thinkgem.jeesite.common.utils.SystemPath;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 项目相关Controller
 * @author pc-20170905
 *
 */
@Controller
@RequestMapping("/weixin/item")
@Transactional(rollbackFor=Exception.class)
public class WItemController extends BaseController{
	
	@Autowired
	private WItemService wItemService;
//	@Autowired
//	private WShopService wShopService;
	@Autowired
	private WItemTypeService wItemTypeService;
	@Autowired
	private WOrderService wOrderService;
	@Autowired
	private AMGoodsTypeService aMGoodsTypeService;
	
	/**
	 * 改价审核后，继续跑订单
	 * @param id 订单id
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@RequestMapping("/afterChangePrice.do")
	@ResponseBody
	public void afterChangePrice(String id) throws ParseException, IOException{
		wItemService.afterChangePrice(id);
	}
	
	/**
	 * 项目改价审核
	 * @param id 订单id
	 * @return
	 */
	@RequestMapping("/check.do")
	@ResponseBody
	public ActionResponse<Object> check(HttpServletRequest request, String id){
		ActionResponse<Object> returnData = new ActionResponse<Object>();
		AUser user = LoginUtil.weixinLoginUser(request);
		int row = -1;//没有用户
		if(user != null){
			row = wItemService.check(user, id);
			if(row > 0){
				returnData.setingSuccess("审核成功");
			}else if(row == -1){
				returnData.setingError("审核失败，用户当前余额不足");
			}else{
				returnData.setingError("审核失败");
			}
		}else{
			returnData.setingError(Constant.NO_USER);
		}
		returnData.setData(row);
		return returnData;
	}
	
	/**
	 * 项目改价
	 * @param id 订单号
	 * @param newPrice
	 * @return
	 */
	@RequestMapping("/changePrice.do")
	@ResponseBody
	public ActionResponse<Object> changePrice(HttpServletRequest request, String id, Double newPrice){
		ActionResponse<Object> returnData = new ActionResponse<Object>();
		AUser user = LoginUtil.weixinLoginUser(request);
		int row = -1;//没有用户
		if(user != null){
			row = wItemService.changePrice(user, id, newPrice);
			if(row > 0){
				returnData.setingSuccess("改价成功");
			}else{
				returnData.setingError("改价失败");
			}
		}else{
			returnData.setingError(Constant.NO_USER);
		}
		returnData.setData(row);
		return returnData;
	}
	
	/**
	 * 获取当前用户
	 * @return
	 */
	@RequestMapping("/getUser.do")
	@ResponseBody
	public ActionResponse<Object> getUser(HttpServletRequest request){
		ActionResponse<Object> returnData = new ActionResponse<Object>();
//		String openId = (String) session.getAttribute("openId");
//		AUser user = aUserDao.getUser(openId);
		AUser user = LoginUtil.weixinLoginUser(request);
		if(user != null){
			returnData.setData(user);
			returnData.setingSuccess(Constant.DEFAULT_SUCCESS_MSG);
		}else{
			returnData.setingError(Constant.NO_USER);
		}
		return returnData;
	}
	
	/**
	 * 打开价格确认页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/priceConfirm.do")
	public String priceConfirm(HttpServletRequest request, Model model){
		List<WOrder> orders = wItemService.getPriceInfo();
		model.addAttribute("orders", orders==null ? Lists.newArrayList() : orders);
		return "mobile/price_confirm";
	}
	
	/**
	 * 点击支付下订单
	 * @param id
	 * @param point 使用积分
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@RequestMapping("/payConfirm.do")
	@ResponseBody
	public ActionResponse<Object> payConfirm(String id, HttpServletRequest request,HttpServletResponse response) throws ParseException, IOException{
		ActionResponse<Object> returnData = new ActionResponse<Object>();
		AUser user = LoginUtil.weixinLoginUser(request);
		int row = -1;//没有用户
		if(user != null){
			row = wItemService.buyItem(user, id);
		}
		returnData.setData(row);
		if(row == 1){
			returnData.setingSuccess("购买成功");
		}else if(row == 2){
			returnData.setingError("您没有推荐人，您暂时无法购物");
		}else if(row == 3){
			returnData.setingError("您不是形象大使，您暂时无法购买整容项目");
		}else if(row == 4){
			returnData.setingError("您的个人信息不完善，请先完善个人信息");
		}else if(row == 5){
			returnData.setingError("您的推荐人不是形象大使，暂时不能购买");
		}else if(row == 0 || row == -1){
			returnData.setingError(Constant.NO_USER);
		}
		return returnData;
	}
	
	/**
	 * 点击购买项目，展示购买信息，未下单
	 * @param itemId
	 * @return
	 */
	@RequestMapping("/buyItem.do")
	public String buyItem(String id, HttpServletRequest request, Model model){
		String prePath = SystemPath.getRequestPreUrl(request);
		WItem item = wItemService.getItemById(id, prePath, request);
		model.addAttribute("item", item);
		/*if(StringUtils.isNotBlank(shopId)){
			WShop shop = wShopService.getShopById(shopId);
			model.addAttribute("shop", shop);
		}*/
		return "mobile/item_payment";
	}
	
	/**
	 * 分页查询项目信息
	 * @param page 当前页
	 * @param size 每页展示几条数据
	 * @return
	 */
	@RequestMapping("/getItemByPage.do")
	@ResponseBody
	public ActionResponse<Object> getItemByPage(Integer page, Integer size, HttpServletRequest request){
		ActionResponse<Object> returnData = new ActionResponse<Object>();
		String prePath = SystemPath.getRequestPreUrl(request);
		List<WItem> items = wItemService.getItemByPage(page, size, prePath, request);
		if(items != null){
			returnData.setData(items);
			returnData.setingSuccess(Constant.DEFAULT_SUCCESS_MSG);
		}else{
			returnData.setingError(Constant.DEFAULT_ERROR_MSG);
		}
		return returnData;
	}
	
	/**
	 * 查询项目详情
	 * @param id
	 * @return
	 */
	@RequestMapping("/getItemById.do")
	public String getItemById(String id, String shopId, HttpServletRequest request, Model model){
		String prePath = SystemPath.getRequestPreUrl(request);
		WItem item = wItemService.getItemById(id, prePath, request);
		model.addAttribute("item", item);
		model.addAttribute("imgs", item.getImages()==null ? Lists.newArrayList() : item.getImages());
		/*if(StringUtils.isNotBlank(shopId)){//获取门店
			WShop shop = wShopService.getShopById(shopId);
			model.addAttribute("shop", shop);
		}*/
		return "mobile/item_detail";
	}
	
	/**
	 * 获取项目详情（json）
	 * @param orderId 订单id
	 * @return
	 */
	@RequestMapping("/getItemDetail.do")
	@ResponseBody
	public ActionResponse<Object> getItemDetail(String orderId){
		ActionResponse<Object> returnData = new ActionResponse<Object>();
		if(StringUtils.isBlank(orderId)){
			returnData.setingError("获取项目信息失败");
			return returnData;
		}
		WOrder order = wOrderService.getWOrderById(orderId);
		WItem item = wItemService.getItemById(order.getGoodsId(), null, null);
		if(item != null){
			returnData.setingSuccess(Constant.DEFAULT_SUCCESS_MSG);
			returnData.setData(item);
		}else{
			returnData.setingError(Constant.DEFAULT_ERROR_MSG);
		}
		return returnData;
	}
	
	/**
	 * 跳转整形项目，默认只显示回龄术，返回分类信息
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/goItemProject.do")
	public String goItemProject(Model model){
//		String type = "回龄术";
//		List<WItem> items = wItemService.getItemByType(type);
//		model.addAttribute("items", items);
		
//		List<String> types = wItemService.getItemTypes();
		
		List<WItemType> types = wItemTypeService.getAllItemType();
		List<AMGoodsType> goodsType = aMGoodsTypeService.getAllGoodsType();
		model.addAttribute("types", types);
		model.addAttribute("goodsType", goodsType);
		return "mobile/project";
	}
	
	/**
	 * 根据分类获取项目
	 * @param type
	 * @return
	 */
	@RequestMapping("/getItemByType.do")
	@ResponseBody
	public ActionResponse<Object> getItemByType(String type){
		ActionResponse<Object> returnData = new ActionResponse<Object>();
		List<WItem> items = wItemService.getItemByType(type);
		if(items != null && items.size() > 0){
			returnData.setingSuccess(Constant.DEFAULT_SUCCESS_MSG);
			returnData.setData(items);
		}else{
			returnData.setingError(Constant.DEFAULT_ERROR_MSG);
		}
		return returnData;
	}
	
	/**
	 * 根据分类获分页取项目
	 * @param type
	 * @param page
	 * @return
	 */
	@RequestMapping("/getItemByTypeByPage.do")
	@ResponseBody
	public ActionResponse<Object> getItemByTypeByPage(String type, String page){
		ActionResponse<Object> returnData = new ActionResponse<Object>();
		List<WItem> items = null;
		if(type != null && type != ""){
			items = wItemService.getItemByTypeByPage(type, Integer.parseInt(page));
		}
		if(items == null){
			items = Lists.newArrayList();
		}
		returnData.setData(items);
		return returnData;
	}
	
}


