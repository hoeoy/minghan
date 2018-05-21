package com.thinkgem.jeesite.modules.cmd.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ks.app.entity.WReward;
import com.ks.app.service.WRewardService;
import com.ks.app.service.WUserLevelService;
import com.ks.app.utils.ActionResponse;
import com.ks.utils.Constant;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.cmd.entity.Reward;
import com.thinkgem.jeesite.modules.cmd.service.RewardService;
import com.thinkgem.jeesite.modules.cmd.service.UserLevelService;

/**
 * 佣金、二级分销Controller
 * @author pc-20170905
 *
 */
@Controller
@RequestMapping("/weixin/reward")
public class RewardController extends BaseController{
	
	@Autowired
	private RewardService rewardService;
	@Autowired
	private UserLevelService userLevelService;
	
	//审核提现
//	@RequestMapping("/checkWithdrawDeposit.do")
//	@ResponseBody
//	public ActionResponse<Object> checkWithdrawDeposit(HttpSession session ,String takeOutId){
//		ActionResponse<Object> returnData = new ActionResponse<Object>();
//		int row = rewardService.checkWithdrawDeposit(session, takeOutId);
//		if(row == 1){
//			returnData.setingSuccess("审核成功");
//		}else{
//			returnData.setingError("操作失败，请稍后重试");
//		}
//		return returnData;
//	}
	
	/**
	 * 佣金提现
	 * @param session
	 * @param money 提现金额
	 * @return
	 */
//	@RequestMapping("/withdrawDepositByReward.do")
//	@ResponseBody
//	public ActionResponse<Object> withdrawDepositByReward(HttpSession session ,Integer money){
//		ActionResponse<Object> returnData = new ActionResponse<Object>();
//		int row = rewardService.withdrawDepositByReward(session, money);
//		if(row == 1){
//			returnData.setingSuccess("操作成功，请等待审核");
//		}else if(row == 2){
//			returnData.setingError("提现失败，你的佣金不足");
//		}else if(row == 3){
//			returnData.setingError("提现失败，提现佣金不能为零");
//		}else if(row == 4){
//			returnData.setingError("提现失败，提现佣金不能小于零");
//		}else{
//			returnData.setingError("操作失败，请稍后重试");
//		}
//		return returnData;
//	}
	
	/**
	 * 用户提现 12月返现
	 * @param session
	 * @param stage 第几期
	 * @return
	 */
//	@RequestMapping("/withdrawDepositByMonth.do")
//	@ResponseBody
//	public ActionResponse<Object> withdrawDepositByMonth(HttpSession session, Integer stage){
//		ActionResponse<Object> returnData = new ActionResponse<Object>();
//		int row = rewardService.withdrawDepositByMonth(session, stage);
//		if(row == 0){
//			returnData.setingError("还未到可提现时间");
//		}else if(row == 1){
//			returnData.setingSuccess("操作成功，请等待审核");
//		}else if(row == 2){
//			returnData.setingError("您暂无返现");
//		}else{
//			returnData.setingError("操作失败，请稍后重试");
//		}
//		return returnData;
//	}
	
	/**
	 * 根据用户id获取其星级
	 * @param userId
	 * @return
	 */
//	@RequestMapping("/getLevelByUserId.do")
//	@ResponseBody
//	public ActionResponse<Object> getLevelByUserId(String userId){
//		ActionResponse<Object> returnData = new ActionResponse<Object>();
//		String level = userLevelService.getLevelByUserId(userId);
//		if(level != null){
//			returnData.setData(level);
//			returnData.setingSuccess(Constant.DEFAULT_SUCCESS_MSG);
//		}else{
//			returnData.setingError(Constant.DEFAULT_ERROR_MSG);
//		}
//		return returnData;
//	}
	
	/**
	 * 在数据库中生成一条记录
	 * @return
	 */
//	@RequestMapping("/buildReward.do")
//	@ResponseBody
//	public ActionResponse<Object> buildReward(){
//		ActionResponse<Object> returnData = new ActionResponse<Object>();
//		int row = rewardService.buildReward();
//		if(row == 1){
//			returnData.setingSuccess(Constant.DEFAULT_SUCCESS_MSG);
//		}else{
//			returnData.setMsg("无奖励记录");
//		}
//		return returnData;
//	}
	
	/**
	 * 根据主键id查询
	 * @param id
	 * @return
	 */
//	@RequestMapping("/getRewardById.do")
//	@ResponseBody
//	public ActionResponse<Object> getRewardById(String id){
//		ActionResponse<Object> returnData = new ActionResponse<Object>();
//		Reward reward = rewardService.getRewardById(id);
//		if(reward != null){
//			returnData.setData(reward);
//			returnData.setingSuccess(Constant.DEFAULT_SUCCESS_MSG);
//		}else{
//			returnData.setMsg("无奖励记录");
//		}
//		return returnData;
//	}
	
	

}
