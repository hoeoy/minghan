package com.thinkgem.jeesite.modules.cmd.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ks.app.utils.ActionResponse;
import com.ks.utils.Constant;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.cmd.entity.Rate;
import com.thinkgem.jeesite.modules.cmd.entity.TakeOut;
import com.thinkgem.jeesite.modules.cmd.service.ManageService;

/**
 * 提现管理类controller
 * @author pc-20170905
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/cmd/orderOut")
public class ManageController extends BaseController{
	
	@Autowired
	private ManageService manageService;
	
	/**
	 * 费率列表
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"rateList"})
	public String rateList(HttpServletRequest request, HttpServletResponse response, Model model){
		List<Rate> rates = manageService.getRateList();
		model.addAttribute("rates", rates);
		return "modules/cmd/rateList";
	}
	
	/**
	 * 修改费率比例
	 * @param rate
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"rateForm"})
	public String rateForm(Rate rate, Model model){
		rate = manageService.getRate(rate);
		model.addAttribute("rate", rate);
		return "modules/cmd/rateForm";
	}
	
	/**
	 * 保存
	 * @param rate
	 * @param request
	 * @return
	 */
	@RequestMapping(value = {"rateSave"})
	public String rateSave(Rate rate, HttpServletRequest request){
		manageService.saveRate(rate);
		return "redirect:" + adminPath + "/cmd/orderOut/rateList";
	}

	/**
	 * 获取全部提现订单
	 * @param takeOut
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"list"})
	public String list(TakeOut takeOut, HttpServletRequest request, HttpServletResponse response, Model model){
		Page<TakeOut> page = manageService.getTakeOutList(new Page<TakeOut>(request, response), takeOut);
		model.addAttribute("page", page);
//		model.addAttribute("takeOut", takeOut);
		return "modules/cmd/orderOutList";
	}
	
	//生成订单
	@RequestMapping("/build.do")
	@ResponseBody
	public ActionResponse<Object> build(){
		ActionResponse<Object> returnData = new ActionResponse<Object>();
		
		int row = manageService.insertOrder();
		
		if(row == 1){
			returnData.setingSuccess(Constant.DEFAULT_SUCCESS_MSG);
		}else{
			returnData.setingError("");
		}
		return returnData;
	}
	
	/**
	 * 提现审核
	 */
	@RequestMapping("/rewardAudit.do")
	@ResponseBody
	public ActionResponse<Object> rewardAudit(String orderId, String userId){
		ActionResponse<Object> returnData = new ActionResponse<Object>();
		
		int row = manageService.changeFlag(orderId, userId);
		
		if(row == 1){
			returnData.setingSuccess(Constant.DEFAULT_SUCCESS_MSG);
		}else{
			returnData.setingError("");
		}
		return returnData;
	}
	
}
