package com.ks.app.web;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ks.app.entity.AAddress;
import com.ks.app.entity.AMGoods;
import com.ks.app.entity.AMGoodsImage;
import com.ks.app.entity.AMOrder;
import com.ks.app.entity.AMUser;
import com.ks.app.entity.AUser;
import com.ks.app.service.AAddressService;
import com.ks.app.service.AMGoodsService;
import com.ks.app.service.AMUserService;
import com.ks.app.service.AUserService;
import com.ks.app.utils.ActionResponse;
import com.ks.app.utils.WeixinUtil;
import com.ks.utils.Constant;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.service.SysConstantService;
@Controller
@RequestMapping(value = "/app/mgoods")
public class AMGoodsController extends BaseController{
	@Autowired
	private AMGoodsService amGoodsService;
	
	@Autowired
	private AUserService aUserService;
	@Autowired
	private SysConstantService constantService;
	@Autowired
	private AMUserService amUserService;
	@Autowired
	private AAddressService aAddressService;
	private DecimalFormat df = new DecimalFormat("#.00");
	
	@RequestMapping(value="getGoodsByType.do")
	public @ResponseBody ActionResponse<Object> getGoodsByType(String type){
		ActionResponse<Object> data = new ActionResponse<Object>();
		List<AMGoods> list = amGoodsService.getGoodsByType(type);
		if(list.size()>0){
			data.setingSuccess(Constant.DEFAULT_SUCCESS_MSG);
			data.setData(list);
		}else{
			data.setingError(Constant.DEFAULT_ERROR_MSG);
		}
		return data;
	}
	
	@RequestMapping(value="tequan_detail.do")
	public String tequan_detail(Model model,HttpSession session){
		String openId = (String)session.getAttribute("openId");
		String userId = aUserService.getIdByOpenId(openId);
		AMUser user = amUserService.getMuserByUserId(userId);
		String flag = user.getFlag();
		String parentId = user.getParentId();
		String userComplete = "0";
		if(aUserService.userComplete(userId)){
			userComplete = "1";
		}
		AMGoods goods = amGoodsService.get(WeixinUtil.ZI_GE_ID);
		List<AMGoodsImage> list = amGoodsService.getImageByGoodsId(goods.getId());
		String completeFlag = "0";
		if(aUserService.userComplete(userId)){
			completeFlag = "1";
		}
		model.addAttribute("completeFlag", completeFlag);
		model.addAttribute("parentId", parentId);
		model.addAttribute("userComplete", userComplete);
		model.addAttribute("imgs", list);
		model.addAttribute("goods", goods);
		model.addAttribute("flag", flag);
		
		return "mobile/tequan_detail";
	}
	
	@RequestMapping(value="buytequan.do")
	public String buytequan(String id,Model model,HttpSession session){
		AMGoods goods = amGoodsService.get(id);
		String openId = (String)session.getAttribute("openId");
		String userId = aUserService.getIdByOpenId(openId);
		String name = aUserService.getNameById(userId);
		BigDecimal price = goods.getPrice();
		String shouxu = constantService.getCustomGoodsShouXuFei();
		BigDecimal sxf = new BigDecimal(shouxu);
		price =new BigDecimal(df.format(price.add(price.multiply(sxf))));
		goods.setNowPrice(price);
		model.addAttribute("name", name);
		model.addAttribute("goods", goods);
		return "mobile/tequan_payment";
	}
	
	@RequestMapping(value="good_detail.do")
	public String good_detail(String id,Model model,HttpSession session){
		String openId = (String)session.getAttribute("openId");
		String userId = aUserService.getIdByOpenId(openId);
		AMUser user = amUserService.getMuserByUserId(userId);
		String flag = user.getFlag();
		AMGoods goods = amGoodsService.get(id);
		model.addAttribute("goods", goods);List<AMGoodsImage> list = amGoodsService.getImageByGoodsId(goods.getId());
		model.addAttribute("imgs", list);
		model.addAttribute("goods", goods);
		model.addAttribute("flag", flag);
		return "mobile/good_detail";
	}
	
	@RequestMapping(value="good_payment.do")
	public String good_payment(String id,String num,Model model,HttpSession session){
		int disNum = Integer.parseInt(constantService.getDISCOUNTS_NUM());
		int index = Integer.parseInt(num);
		String openId = (String)session.getAttribute("openId");
		String userId = aUserService.getIdByOpenId(openId);
		AUser u = aUserService.getUserById(userId);
		String name = u.getName();
		model.addAttribute("name", name);
		AMUser user = amUserService.getMuserByUserId(userId);
		if(user!=null){
			session.removeAttribute("order");
			AMGoods goods = amGoodsService.get(id);
			AAddress address = aAddressService.getPrimaryByUserId(userId);
			if(goods!=null){
				AMOrder order = new AMOrder();
				order.setUserId(user.getId());
				order.setGoodsLogo(goods.getLogo());
				order.setGoodsId(id);
				order.setGoodsPrice(goods.getPrice());
				order.setGoodsName(goods.getName());
				order.setGoodsNum(index);
				order.setGoodsMarket(goods.getMarket());
				order.setGoodsAllPrice(goods.getPrice().multiply(new BigDecimal(index)));
				int userNum = user.getDiscountsNum();
				String shouxu = constantService.getCustomGoodsShouXuFei();
				double iii = Double.parseDouble(shouxu)*100;
				model.addAttribute("shouxulv", iii);
				BigDecimal sxf = new BigDecimal(shouxu);
				if(userNum>=disNum){//没有优惠数量了
				BigDecimal price = goods.getPrice();
				price =new BigDecimal(df.format(price.add(price.multiply(sxf))));
				price = price.multiply(new BigDecimal(index));
				order.setNowPay(price);
				}else{//还有优惠数量
					userNum = disNum - userNum;//优惠价数量
					if(index-userNum<=0){//数量够
						order.setDiscountsNum(index);
						BigDecimal price = goods.getPrice();
						price = price.multiply(WeixinUtil.ZHE_KOU);
						price = price.add(price.multiply(sxf));
						price = price.multiply(new BigDecimal(index));
						price = new BigDecimal(df.format(price));
						order.setNowPay(price);
					}else{//数量不够
						index = index-userNum;//index为原价数量
						order.setDiscountsNum(userNum);
						BigDecimal all = new BigDecimal(0);
						BigDecimal price = goods.getPrice();
						price =new BigDecimal(df.format(price.add(price.multiply(sxf))));
						price = price.multiply(new BigDecimal(index));
						all = all.add(price);
						price = goods.getPrice();
						price = price.multiply(WeixinUtil.ZHE_KOU);
						price = price.add(price.multiply(sxf));
						price = price.multiply(new BigDecimal(userNum));
						price = new BigDecimal(df.format(price));
						all = all.add(price);
						order.setNowPay(all);
					}
				}
				if(address!=null){
					order.setAddId(address.getId());
					order.setLinkman(address.getLinkman());
					order.setMobile(address.getMobile());
					order.setAddress(address.getProvince()+"  "+address.getCity()+"  "+address.getZone()+"  "+address.getAddr());
				}
				session.setAttribute("order", order);
				model.addAttribute("order", order);
			}
		}
		return "mobile/good_payment";
	}
	
	
	@RequestMapping(value="updateOrderAddress.do")
	public String updateOrderAddress(HttpSession session,Model model,String id){
		AMOrder order = (AMOrder)session.getAttribute("order");
		if(StringUtils.isNoneBlank(id)){
			AAddress address = aAddressService.get(id);
			if(address!=null){
				order.setAddId(address.getId());
				order.setLinkman(address.getLinkman());
				order.setMobile(address.getMobile());
				order.setAddress(address.getProvince()+"  "+address.getCity()+"  "+address.getZone()+"  "+address.getAddr());
			}
			session.removeAttribute("order");
			session.setAttribute("order", order);
			model.addAttribute("order", order);
		}
		return "mobile/good_payment";
	}
	
}
