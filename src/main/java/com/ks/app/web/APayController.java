package com.ks.app.web;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ks.app.entity.AAddress;
import com.ks.app.entity.AMGoods;
import com.ks.app.entity.AMOrder;
import com.ks.app.entity.AMRecord;
import com.ks.app.entity.AMUser;
import com.ks.app.entity.AUser;
import com.ks.app.service.AAddressService;
import com.ks.app.service.AMGoodsService;
import com.ks.app.service.AMOrderService;
import com.ks.app.service.AMRecordService;
import com.ks.app.service.AMUserService;
import com.ks.app.service.AUserService;
import com.ks.app.utils.ActionResponse;
import com.ks.app.utils.WeixinUtil;
import com.ks.utils.EnumConstant.PayState;
import com.ks.utils.OrderCodeUtil;
import com.ks.utils.EnumConstant.OrderState;
import com.ks.utils.XMLUtil;
import com.ks.utils.pay.weixin.WxPayUtil;
import com.thinkgem.jeesite.common.utils.SystemPath;
import com.thinkgem.jeesite.modules.sys.service.SysConstantService;
@Controller
@RequestMapping("/app/pay")
public class APayController {
	@Autowired
	private AUserService aUserService;
	@Autowired
	private AMGoodsService amGoodsService;
	@Autowired
	private AAddressService aAddressService;
	@Autowired
	private AMOrderService amOrderService;
	@Autowired
	private SysConstantService constantService;
	@Autowired
	private AMUserService amUserService;
	@Autowired
	private AMRecordService amRecordService;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private DecimalFormat df = new DecimalFormat("#.00");
	
    /**
     * 微信异步通知---js

     */
    @RequestMapping(value="/wxJSNotify.do",  produces = "text/html;charset=UTF-8",method={RequestMethod.POST})
    public String wxJSNotify(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	System.out.println("===============wxJSNotify================");
    	//读取参数  
    	InputStream inputStream = request.getInputStream();  
    	StringBuffer sb = new StringBuffer();  
    	String s ;  
    	BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));  
    	while ((s = in.readLine()) != null){  
    		sb.append(s);  
    	}  
    	in.close();  
    	inputStream.close();  
    	//解析xml成map  
    	Map<String, String> m = new HashMap<String, String>();  
    	m = XMLUtil.doXMLParse(sb.toString());  
    	/*for(Object keyValue : m.keySet()){
    		System.out.println(keyValue+"="+m.get(keyValue));
    	}*/
    	//过滤空 设置 TreeMap  
    	SortedMap<Object,Object> packageParams = new TreeMap<Object,Object>();        
    	Iterator<String> it = m.keySet().iterator();  
    	while (it.hasNext()) {  
    		String parameter = it.next();  
    		String parameterValue = m.get(parameter);  
    		
    		String v = "";  
    		if(null != parameterValue) {  
    			v = parameterValue.trim();  
    		}  
    		packageParams.put(parameter, v);  
    	}
    	System.out.println("===============微信js回调回来的参数================"+JSONObject.fromObject(packageParams).toString());
    	//判断签名是否正确  
    	if(WxPayUtil.isTenpaySign("UTF-8", packageParams)) {
    		String out_trade_no = (String)packageParams.get("out_trade_no"); //商户订单号
    		//String attach = (String)packageParams.get("attach"); //附加参数
    		String trade_status = (String)packageParams.get("result_code");
    		System.out.println("========微信js中trade_status========"+trade_status);
    		if("SUCCESS".equals(trade_status)){ 
    			// 这里是支付成功  
    			//////////执行自己的业务逻辑////////////////  
    			String mch_id = (String)packageParams.get("mch_id"); //商户号 
//                 String openid = (String)packageParams.get("openid");  //用户标识
    			String total_fee = (String)packageParams.get("total_fee");  
    			String transaction_id = (String)packageParams.get("transaction_id"); //微信支付订单号
    			
    			if(!WeixinUtil.MCH_ID.equals(mch_id)){
    				System.out.println("微信js中支付失败,错误信息：" + "参数错误");  
    				return "FAIL"; 
    			}else{
    				System.out.println("微信js支付成功");  
    				return successOrder(out_trade_no, transaction_id,total_fee, trade_status);
    			}
    			
    		}else {  
    			System.out.println("微信js支付失败");  
    			return failOrder(out_trade_no);
    		}  
    		
    		
    	} else{  
    		System.out.println("微信js中通知签名验证失败");  
    	}   
    	
    	return "FAIL";
    }
    /**
     * 微信统一下订单---js---自己挪动到自己的订单controller中

     */
    @RequestMapping("wxZGOrder.do")
    public @ResponseBody ActionResponse<Object> wxZGOrder(HttpServletRequest request,HttpServletResponse response,HttpSession session){
    	System.out.println("===============wxOrder================");
    	String goodsId = request.getParameter("goodsId");
    	AMGoods goods = amGoodsService.get(goodsId);
    	ActionResponse<Object> data = new ActionResponse<Object>();
    	try{
    		//插入订单
    		String openId = (String) session.getAttribute("openId");
    		AUser user = aUserService.findUserByopenId(openId, "");
    		if(user == null){
    			data.setingError("用户不存在");
    			return data;
    		}
    		AMUser muser = amUserService.getMuserByUserId(user.getId()); 
    		if(goods==null){
    			data.setingError("商品不存在");
    			return data;
    		}
    		AMOrder order = new AMOrder();
    		order.setOrderNo(OrderCodeUtil.getNo(muser.getId()));
    		order.setUserId(muser.getId());
    		order.setGoodsId(goodsId);
    		order.setGoodsName(goods.getName());
    		order.setGoodsNum(1);
    		order.setGoodsPrice(goods.getPrice());
    		order.setOrderState(OrderState.ZERO.getKey());
    		//订单入库
    		amOrderService.save(order);
    		String orderNo = order.getOrderNo();
    		BigDecimal price = goods.getPrice();
    		String shouxu = constantService.getCustomGoodsShouXuFei();
    		BigDecimal sxf = new BigDecimal(shouxu);
    		price =new BigDecimal(df.format(price.add(price.multiply(sxf))));
        	Long money = price.multiply(new BigDecimal(100)).longValue();
        	Map<String,Object> map = new HashMap<String, Object>();
        		//判断付款方式
        		//统一下单，返回xml，用return_code判断统一下单结果,获取prepay_id等预支付成功信息
            	String prePayInfoXml = WxPayUtil.unifiedOrder("WxPay", orderNo, money, WxPayUtil.getIpAddr(request), openId,SystemPath.getRequestProjectUrl(request));
            	//生成包含prepay_id的map，map传入前端
            	map = WxPayUtil.getPayMap(prePayInfoXml);
            	//将订单号放入map，用以支付后处理
            	map.put("orderNo",orderNo);
            	map.put("orderState", order.getOrderState());
            	data.setData(map);
            	data.setingSuccess("生成订单成功");
            	return data;
    	}catch (Exception e) {
    		e.printStackTrace();
		}
    	data.setingError("生成订单失败");
    	return data;
    }
    
    private String successOrder(String out_trade_no,String trade_no,String price,String trade_status){
    	// 判断该笔订单是否在商户网站中已经做过处理
		// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
		// 如果有做过处理，不执行商户的业务程序
//    	String[] params =  passback_params.split(";");
//    	String type = params[0];
//    	String orderNum = params[1];//包含多个订单id
//    	String[] orderNums = orderNum.split(",");
    	 String orderNo = out_trade_no;
    	 AMOrder aorder = amOrderService.getByOrderNo(orderNo);
    	 if(OrderState.ZERO.getKey().equals(aorder.getOrderState())){
    	 //支付交易号
    	 aorder.setTradeNo(trade_no);
    	 //支付时间
    	 aorder.setPayTime(sdf.format(new Date()));
    	 //支付状态
    	 aorder.setPayState(PayState.ZERO.getKey());
    	 if(aorder.getGoodsId().equals(WeixinUtil.ZI_GE_ID)){
    		 	aorder.setOrderState(OrderState.THREE.getKey());
    		 	amUserService.buyZiGeSucceed(aorder.getUserId(),orderNo);
    		 	amRecordService.add(aorder);
    	 }else{
    		 //更改订单状态
    		 aorder.setOrderState(OrderState.ONE.getKey());
    		 amUserService.addDiscountsNum(aorder.getDiscountsNum(),aorder.getUserId());
    	 }
    	 amGoodsService.addSellNum(aorder.getGoodsId());
    	 amOrderService.save(aorder);
    	 }
		return "SUCCESS";
    }
    
    private String failOrder(String out_trade_no){
//    	String[] params =  passback_params.split(";");
//    	String type = params[0];
//    	String orderNum = params[1];//包含多个订单
//    	String[] orderNums = orderNum.split(",");
		//修改订单状态，有优惠券支付时，退优惠券
    	 String orderId = out_trade_no;
    	 AMOrder aorder = amOrderService.getByOrderNo(orderId);
    	aorder.setPayState(PayState.ONE.getKey());
    	amOrderService.save(aorder);
    	return "success";
    }
    
    
    @RequestMapping("wxOrder.do")
    public @ResponseBody ActionResponse<Object> wxOrder(HttpServletRequest request,HttpServletResponse response,HttpSession session){
    	System.out.println("===============wxOrder================");
    	ActionResponse<Object> data = new ActionResponse<Object>();
    	try{
    		//插入订单
    		AMOrder order = (AMOrder)session.getAttribute("order");
    		if(order==null){
    			data.setingError("出错啦，请重试");
    			return data;
    		}
    		String openId = (String) session.getAttribute("openId");
    		AUser user = aUserService.findUserByopenId(openId, "");
    		if(user == null){
    			data.setingError("用户不存在");
    			return data;
    		}
    		AMUser muser = amUserService.getMuserByUserId(user.getId()); 
    		order.setOrderNo(OrderCodeUtil.getNo(muser.getId()));
    		order.setUserId(muser.getId());
    		order.setGoodsPrice(order.getNowPay());
    		order.setOrderState(OrderState.ZERO.getKey());
    		//订单入库
    		amOrderService.save(order);
    		String orderNo = order.getOrderNo();
    		BigDecimal price = order.getNowPay();
        	Long money = price.multiply(new BigDecimal(100)).longValue();
        	System.out.println(money);
        	Map<String,Object> map = new HashMap<String, Object>();
        		//判断付款方式
        		//统一下单，返回xml，用return_code判断统一下单结果,获取prepay_id等预支付成功信息
            	String prePayInfoXml = WxPayUtil.unifiedOrder("WxPay", orderNo, money, WxPayUtil.getIpAddr(request), openId,SystemPath.getRequestProjectUrl(request));
            	//生成包含prepay_id的map，map传入前端
            	map = WxPayUtil.getPayMap(prePayInfoXml);
            	//将订单号放入map，用以支付后处理
            	map.put("orderNo",orderNo);
            	map.put("orderState", order.getOrderState());
            	data.setData(map);
            	data.setingSuccess("生成订单成功");
            	return data;
    	}catch (Exception e) {
    		e.printStackTrace();
		}
    	data.setingError("生成订单失败");
    	return data;
    }
    
    @RequestMapping("wxDZFOrder.do")
    public @ResponseBody ActionResponse<Object> wxDZFOrder(HttpServletRequest request,HttpServletResponse response,HttpSession session){
    	System.out.println("===============wxOrder================");
    	ActionResponse<Object> data = new ActionResponse<Object>();
    	try{
    		//插入订单
    		String orderId = request.getParameter("orderId");
    		AMOrder order = amOrderService.get(orderId);
    		if(order==null){
    			data.setingError("出错啦，请重试");
    			return data;
    		}
    		String openId = (String) session.getAttribute("openId");
    		AUser user = aUserService.findUserByopenId(openId, "");
    		if(user == null){
    			data.setingError("用户不存在");
    			return data;
    		}
    		String orderNo = order.getOrderNo();
    		BigDecimal price = order.getGoodsPrice();
    		String shouxu = constantService.getCustomGoodsShouXuFei();
			BigDecimal sxf = new BigDecimal(shouxu);
			price =new BigDecimal(df.format(price.add(price.multiply(sxf))));
        	Long money = price.multiply(new BigDecimal(100)).longValue();
        	System.out.println(money);
        	Map<String,Object> map = new HashMap<String, Object>();
        		//判断付款方式
        		//统一下单，返回xml，用return_code判断统一下单结果,获取prepay_id等预支付成功信息
            	String prePayInfoXml = WxPayUtil.unifiedOrder("WxPay", orderNo, money, WxPayUtil.getIpAddr(request), openId,SystemPath.getRequestProjectUrl(request));
            	//生成包含prepay_id的map，map传入前端
            	map = WxPayUtil.getPayMap(prePayInfoXml);
            	//将订单号放入map，用以支付后处理
            	map.put("orderNo",orderNo);
            	map.put("orderState", order.getOrderState());
            	data.setData(map);
            	data.setingSuccess("生成订单成功");
            	return data;
    	}catch (Exception e) {
    		e.printStackTrace();
		}
    	data.setingError("生成订单失败");
    	return data;
    }
    
}
