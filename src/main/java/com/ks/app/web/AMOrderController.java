package com.ks.app.web;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.ks.app.entity.AMGoods;
import com.ks.app.entity.AMOrder;
import com.ks.app.entity.AMUser;
import com.ks.app.service.AMGoodsService;
import com.ks.app.service.AMOrderService;
import com.ks.app.service.AMUserService;
import com.ks.app.service.AUserService;
import com.ks.app.utils.ActionResponse;
import com.ks.utils.EnumConstant.OrderState;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.service.SysConstantService;
@Controller
@RequestMapping(value="/app/morder")
public class AMOrderController extends BaseController{
	
	@Autowired
	private AMOrderService amOrderService;
	@Autowired
	private AUserService aUserService;
	@Autowired
	private AMUserService amUserService;
	@Autowired
	private SysConstantService constantService;
	@Autowired
	private AMGoodsService amGoodsService;
	private DecimalFormat df = new DecimalFormat("#.00");
	  /**
		 * 获取所有商品订单
		 * @return
		 */
		@RequestMapping(value="getOrderList.do")
		public String getAllOrder(HttpSession session,Model model,String state){
			String openId = (String)session.getAttribute("openId");
			String userId = aUserService.getIdByOpenId(openId);
			if(userId!=null){
				AMUser user = amUserService.getMuserByUserId(userId);
				if(user!=null){
					AMOrder order = new AMOrder();
					order.setOrderState(state);
					order.setUserId(user.getId());
					List<AMOrder> list = amOrderService.getOrderList(order,1);
					int allPage=amOrderService.getAllPage(state);
					model.addAttribute("allPage", allPage);
					model.addAttribute("list", list);
				}
			}
			model.addAttribute("state", state);
			return "mobile/order-allGood";
		}
		
    	@RequestMapping(value="orderBask.do")
		public @ResponseBody ActionResponse<Object> orderBask(HttpSession session,String page,String state){
			ActionResponse<Object> returnData = new ActionResponse<>();
			/*List<ADiscuss> list = aDiscussService.getAdiscuss(Integer.parseInt(page));*/
		    String openId = (String)session.getAttribute("openId");
			String userId = aUserService.getIdByOpenId(openId);
			List<AMOrder> list=new ArrayList<>();
			if(userId!=null){
				AMUser user = amUserService.getMuserByUserId(userId);
				if(user!=null){
					AMOrder order = new AMOrder();
					order.setOrderState(state);
					order.setUserId(user.getId());
					list = amOrderService.getOrderList(order,Integer.parseInt(page));
				}
			}
			if(list == null){
				list = Lists.newArrayList();
			};
			returnData.setData(list);
			return returnData;
		}
		
		
		@RequestMapping(value="exitOrder.do")
		public String exitOrder(String id,HttpSession session,Model model){
			String state = amOrderService.getState(id);
			amOrderService.exitOrder(id);
			return getAllOrder(session,model,state);
		}
		@RequestMapping(value="shouhuo.do")
		public String shouhuo(String id,HttpSession session,Model model){
			String state = amOrderService.getState(id);
			amOrderService.shouhuo(id,session);
			return getAllOrder(session, model, state);
		}
		
		@RequestMapping(value="getOrder.do")
		public @ResponseBody ActionResponse<Object> getOrder(String id){
			ActionResponse<Object> data = new ActionResponse<Object>();
			AMOrder order = amOrderService.get(id);
			BigDecimal bd = order.getGoodsPrice();
			String shouxu = constantService.getCustomGoodsShouXuFei();
			BigDecimal sxf = new BigDecimal(shouxu);
			bd =new BigDecimal(df.format(bd.add(bd.multiply(sxf))));
			data.setData(bd);
			return data;
			
		}
		
		@RequestMapping(value="getOrderDetail.do")
		public String getOrderDetail(String id,Model model){
			AMOrder order = amOrderService.get(id);
			if(order!=null){
				AMGoods goods = amGoodsService.get(order.getGoodsId());
				if(goods!=null){
					order.setGoodsLogo(goods.getLogo());
					order.setGoodsMarket(goods.getMarket());
					BigDecimal money = order.getGoodsPrice();
					String shouxu = constantService.getCustomGoodsShouXuFei();
					BigDecimal sxf = new BigDecimal(shouxu);
					money =new BigDecimal(df.format(money.add(money.multiply(sxf))));
					order.setNowPay(money);
					order.setGoodsPrice(goods.getPrice());
					order.setOrderState(OrderState.orderState(order.getOrderState()));
				}
				model.addAttribute("order", order);
			}
			return "mobile/orderDetail";
		}
		
		
}
