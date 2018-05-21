package com.ks.app.web;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ks.app.entity.AMTakeOut;
import com.ks.app.entity.AMUser;
import com.ks.app.service.AMTakeOutService;
import com.ks.app.service.AMUserService;
import com.ks.app.service.AUserService;
import com.ks.app.utils.ActionResponse;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.service.SysConstantService;

@Controller
@RequestMapping(value = "/app/mtakeout")
public class AMTakeOutController extends BaseController{
	
	@Autowired
	private AUserService aUserService;
	@Autowired
	private AMTakeOutService amTakeOutService;
	@Autowired
	private AMUserService amUserService;
	@Autowired
	private SysConstantService constantService;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
	
	@RequestMapping(value="getMyTakeOut.do")
	public String getMyTakeOut(HttpSession session,Model model){
		String openId = (String)session.getAttribute("openId");
		String userId = aUserService.getIdByOpenId(openId);
		AMUser user = amUserService.getMuserByUserId(userId);
		if(user!=null){
			List<AMTakeOut> list = amTakeOutService.getMyTakeOut(user.getId());
			for(int i = 0;i<list.size();i++){
				list.get(i).setTime(sdf.format(list.get(i).getCreateDate()));
			}
			model.addAttribute("list", list);
		}
		return "mobile/tixian_record";
	}
	
	@RequestMapping(value="tixian.do")
	public @ResponseBody ActionResponse<Object> tixian(HttpSession session,String money){
		ActionResponse<Object> data = new ActionResponse<Object>();
		String openId = (String)session.getAttribute("openId");
		String userId = aUserService.getIdByOpenId(openId);
		AMUser user = amUserService.getMuserByUserId(userId);
		if(user!=null){
			BigDecimal rewardMoney = user.getmRewardMoney();
			AMTakeOut amto = new AMTakeOut();
			amto.setApp(true);
			amto.setUserId(user.getId());
			amto.setState("0");
			BigDecimal bd = new BigDecimal(money);
			rewardMoney=rewardMoney.subtract(bd);
			user.setmRewardMoney(rewardMoney);
			amto.setMoney(bd);
			String shouxu = constantService.getCustomShouXuFei();
			if(StringUtils.isNoneBlank(shouxu)){
				BigDecimal sxf = new BigDecimal(shouxu);
				sxf = bd.multiply(sxf);
				amto.setShouxu(sxf);
				amTakeOutService.save(amto);
				amUserService.allterRM(user);
				data.setMsg("提现成功，等待管理员结算");
			}else{
				data.setMsg("提现失败");
			}
		}else{
			data.setMsg("提现失败");
		}
		return data;
	}
	@RequestMapping(value="tixianYuE.do")
	public @ResponseBody ActionResponse<Object> tixianYuE(HttpSession session,String money){
		ActionResponse<Object> data = new ActionResponse<Object>();
		String openId = (String)session.getAttribute("openId");
		String userId = aUserService.getIdByOpenId(openId);
		AMUser user = amUserService.getMuserByUserId(userId);
		String ddd = "0";
		if(user!=null){
			BigDecimal rewardMoney = user.getmRewardMoney();
			BigDecimal bd = new BigDecimal(money);
			if(rewardMoney.compareTo(bd)<0){
				data.setMsg("佣金不足!提现失败!");
			}else{
				ddd = "1";
			}
		}else{
			data.setMsg("提现失败");
		}
		data.setData(ddd);
		return data;
	}
	

}
