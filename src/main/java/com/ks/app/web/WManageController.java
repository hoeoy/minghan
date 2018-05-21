package com.ks.app.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.ks.app.entity.AUser;
import com.ks.app.entity.WTakeOut;
import com.ks.app.service.WManageService;
import com.ks.app.utils.ActionResponse;
import com.ks.app.utils.LoginUtil;
import com.ks.utils.Constant;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 管理类controller
 * @author pc-20170905
 *
 */
@Controller
@RequestMapping("/weixin/manage")
public class WManageController extends BaseController{
	
	@Autowired
	private WManageService wManageService;

	/**
	 * 获取全部提现订单
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/getALL.do")
	public String getALL(HttpServletRequest request, HttpServletResponse response,Model model){
		WTakeOut out = new WTakeOut();
		Page<WTakeOut> page = wManageService.getPage(new Page<WTakeOut>(1,Global.getDefaultPageSize()), out);
		if(page != null && page.getList() != null && !page.getList().isEmpty()){
			model.addAttribute("list", page.getList());
			model.addAttribute("totalPage", page.getTotalPage());
		}
		return "mobile/withdraw_review";
	}
	@RequestMapping("/getNext.do")
	public @ResponseBody ActionResponse<Object> getNext(int pageNo,Model model){
		ActionResponse<Object> returnData = new ActionResponse<Object>();
		try{
			WTakeOut out = new WTakeOut();
			Page<WTakeOut> page = wManageService.getPage(new Page<WTakeOut>(pageNo,Global.getDefaultPageSize()), out);
			if(page != null && page.getList() != null && !page.getList().isEmpty()){
				returnData.setData(page.getList());
			}else{
				returnData.setData(Lists.newArrayList());
			}
			returnData.setingSuccess(Constant.DEFAULT_SUCCESS_MSG);
			return returnData;
		}catch (Exception e) {
			e.printStackTrace();
		}
		returnData.setingError(Constant.DEFAULT_ERROR_MSG);
		return returnData;
	}
	
	//生成订单
	@RequestMapping("/build.do")
	@ResponseBody
	public ActionResponse<Object> build(){
		ActionResponse<Object> returnData = new ActionResponse<Object>();
		
		int row = wManageService.insertOrder();
		
		if(row == 1){
			returnData.setingSuccess(Constant.DEFAULT_SUCCESS_MSG);
		}else{
			returnData.setingError("");
		}
		return returnData;
	}
	
	/**
	 * 提现审核，点击确认
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/rewardAudit.do")
	@ResponseBody
	public ActionResponse<Object> rewardAudit(String orderId, HttpServletRequest request){
		ActionResponse<Object> returnData = new ActionResponse<Object>();
		AUser user = LoginUtil.weixinLoginUser(request);
		int row = -1;//没有用户
		if(user != null){
			row = wManageService.changeFlag(orderId, user.getId());
		}
		if(row == 1){
			returnData.setingSuccess(Constant.DEFAULT_SUCCESS_MSG);
		}else if(row == 0){
			returnData.setingError(Constant.NO_USER);
		}else{
			returnData.setingError(Constant.DEFAULT_ERROR_MSG);
		}
		return returnData;
	}
	
}
