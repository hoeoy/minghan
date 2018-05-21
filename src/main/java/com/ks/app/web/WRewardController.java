package com.ks.app.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ks.app.entity.AUser;
import com.ks.app.entity.WReward;
import com.ks.app.service.WRewardService;
import com.ks.app.service.WUserLevelService;
import com.ks.app.utils.ActionResponse;
import com.ks.app.utils.LoginUtil;
import com.ks.utils.Constant;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 佣金、二级分销Controller
 * @author pc-20170905
 *
 */
@Controller
@RequestMapping("/weixin/reward")
public class WRewardController extends BaseController{
	
	@Autowired
	private WRewardService wRewardService;
	@Autowired
	private WUserLevelService wUserLevelService;
	
	//审核提现
	@RequestMapping("/checkWithdrawDeposit.do")
	@ResponseBody
	public ActionResponse<Object> checkWithdrawDeposit(HttpServletRequest request ,String takeOutId){
		ActionResponse<Object> returnData = new ActionResponse<Object>();
		AUser user = LoginUtil.weixinLoginUser(request);
		int row = -1;//没有用户
		if(user != null)
			row = wRewardService.checkWithdrawDeposit(user, takeOutId);
		if(row == 1){
			returnData.setingSuccess("审核成功");
		}else if(row == -1){
			returnData.setingError(Constant.NO_USER);
		}else{
			returnData.setingError("操作失败，请稍后重试");
		}
		returnData.setData(row);
		return returnData;
	}
	
	/**
	 * 佣金提现
	 * @param money 提现金额
	 * @return
	 */
	@RequestMapping("/withdrawDepositByReward.do")
	@ResponseBody
	public ActionResponse<Object> withdrawDepositByReward(HttpServletRequest request ,Long money){
		ActionResponse<Object> returnData = new ActionResponse<Object>();
		try{
			AUser user = LoginUtil.weixinLoginUser(request);
			int row = -1;//没有用户
			if(user != null)
				row = wRewardService.withdrawDepositByReward(user, money);
			if(row == 1){
				returnData.setingSuccess("操作成功，请等待审核");
			}else if(row == 2){
				returnData.setingError("提现失败，你的佣金不足");
			}else if(row == 3){
				returnData.setingError("提现失败，提现佣金不能为零");
			}else if(row == 4){
				returnData.setingError("提现失败，提现佣金不能小于零");
			}else if(row == -1){
				returnData.setingError(Constant.NO_USER);
			}else{
				returnData.setingError("操作失败，请稍后重试");
			}
			returnData.setData(row);
		}catch (Exception e) {
			e.printStackTrace();
			returnData.setingError("操作失败，请稍后重试");
		}
		return returnData;
	}
	
	/**
	 * 用户提现 12月返现
	 * @param stage 第几期
	 * @return
	 */
	/*@RequestMapping("/withdrawDepositByMonth.do")
	@ResponseBody
	public ActionResponse<Object> withdrawDepositByMonth(HttpServletRequest request, Integer stage){
		ActionResponse<Object> returnData = new ActionResponse<Object>();
		AUser user = LoginUtil.weixinLoginUser(request);
		int row = -1;//没有用户
		if(user != null)
			row = wRewardService.withdrawDepositByMonth(user, stage);
		if(row == 0){
			returnData.setingError("还未到可提现时间");
		}else if(row == 1){
			returnData.setingSuccess("操作成功，请等待审核");
		}else if(row == 2){
			returnData.setingError("您暂无返现");
		}else if(row == -1){
			returnData.setingError(Constant.NO_USER);
		}else{
			returnData.setingError("操作失败，请稍后重试");
		}
		returnData.setData(row);
		return returnData;
	}*/
	
	/**
	 * 根据用户id获取其星级
	 * @param userId
	 * @return
	 */
	@RequestMapping("/getLevelByUserId.do")
	@ResponseBody
	public ActionResponse<Object> getLevelByUserId(String userId){
		ActionResponse<Object> returnData = new ActionResponse<Object>();
		String level = wUserLevelService.getLevelByUserId(userId);
		if(level != null){
			returnData.setData(level);
			returnData.setingSuccess(Constant.DEFAULT_SUCCESS_MSG);
		}else{
			returnData.setingError(Constant.DEFAULT_ERROR_MSG);
		}
		return returnData;
	}
	
	/**
	 * 在数据库中生成一条记录
	 * @return
	 */
	@RequestMapping("/buildReward.do")
	@ResponseBody
	public ActionResponse<Object> buildReward(){
		ActionResponse<Object> returnData = new ActionResponse<Object>();
		int row = wRewardService.buildReward();
		if(row == 1){
			returnData.setingSuccess(Constant.DEFAULT_SUCCESS_MSG);
		}else{
			returnData.setMsg("无奖励记录");
		}
		return returnData;
	}
	
	/**
	 * 根据主键id查询
	 * @param id
	 * @return
	 */
	@RequestMapping("/getRewardById.do")
	@ResponseBody
	public ActionResponse<Object> getRewardById(String id){
		ActionResponse<Object> returnData = new ActionResponse<Object>();
		WReward reward = wRewardService.getRewardById(id);
		if(reward != null){
			returnData.setData(reward);
			returnData.setingSuccess(Constant.DEFAULT_SUCCESS_MSG);
		}else{
			returnData.setMsg("无奖励记录");
		}
		return returnData;
	}
	
	

}
