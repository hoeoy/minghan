package com.ks.app.web;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.ks.app.dao.AUserDao;
import com.ks.app.entity.ABalance;
import com.ks.app.entity.AUser;
import com.ks.app.entity.WBrokerage;
import com.ks.app.entity.WOrder;
import com.ks.app.entity.WReward;
import com.ks.app.service.ABalanceService;
import com.ks.app.service.WOrderService;
import com.ks.app.service.WRewardService;
import com.ks.app.service.WUserBrokerageService;
import com.ks.app.utils.ActionResponse;
import com.ks.app.utils.LoginUtil;
import com.ks.utils.Constant;
import com.ks.utils.EnumConstant.BrokerageStatus;
import com.ks.utils.EnumConstant.RewardTypeF;
import com.ks.utils.EnumConstant.RewardTypeX;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.cmd.entity.TakeOut;
import com.thinkgem.jeesite.modules.sys.entity.Score;
import com.thinkgem.jeesite.modules.sys.service.UserScoreService;

/**
 * 展示积分明细，余额、佣金、返现明细都在这里
 * @author pc-20170905
 *
 */
@Controller
@RequestMapping("/sys/userScore")
public class WScoreController extends BaseController{
	
//	@Autowired
//	private AUserService aUserService;
	@Autowired
	private UserScoreService userScoreService;
	@Autowired
	private ABalanceService abalanceService;
	@Autowired
	private WUserBrokerageService wUserBrokerageService;
	@Autowired
	private AUserDao aUserDao;
	@Autowired
	private WOrderService wOrderSeervice;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	@Autowired
	private WRewardService wrewardService;
	/**
	 * 获取当前用户的返现明细
	 * @param model
	 * @return
	 */
	@RequestMapping("/getReturnList.do")
	public String returnList(Integer pageNo,HttpServletRequest request, Model model){
		
		AUser user = LoginUtil.weixinLoginUser(request);
		Page<TakeOut> page = null;
		if(user != null){
			TakeOut out = new TakeOut();
			out.setUserId(user.getId());
			out.setType(Constant.DELETE_FLAG_1);
			page = userScoreService.findReturnPage(out,pageNo);
		}else{
			String url = request.getRequestURI();
			return LoginUtil.grantUser(request, url);
		}
		model.addAttribute("page", page);
		return "mobile/returndetail";
	}
	/**
	 * 获取当前用户的返现明细下一页
	 * @param model
	 * @return
	 */
	@RequestMapping("/getNextReturnList.do")
	public @ResponseBody ActionResponse<Object> getNextReturnList(Integer pageNo,HttpServletRequest request, Model model){
		 ActionResponse<Object> data = new ActionResponse<Object>();
		 try{
			 AUser user = LoginUtil.weixinLoginUser(request);
			 if(user != null){
				 TakeOut out = new TakeOut();
				 out.setUserId(user.getId());
				 Page<TakeOut> page = userScoreService.findReturnPage(out,pageNo);
				 if(page != null && page.getList() != null && !page.getList().isEmpty()){
					 data.setData(page.getList());
					 data.setTotalPage(page.getTotalPage());
				 }else{
					 data.setData(Lists.newArrayList());
				 }
				 data.setingSuccess(Constant.DEFAULT_SUCCESS_MSG);
				 return data;
			 }
			 data.setingError(Constant.NO_USER);
		 }catch (Exception e) {
			 e.printStackTrace();
			 data.setingError(Constant.DEFAULT_ERROR_MSG);
		}
		return data;
	}
	
	/**
	 * 获取当前用户的积分明细
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getScoreList.do")
	public String list(HttpServletRequest request, Model model){
		AUser user = LoginUtil.weixinLoginUser(request);
		List<Score> list = null;
		String userScore = "";
		if(user != null){
			list = userScoreService.getScoreListByUser(user);
			userScore = String.valueOf(user.getPoint());
		}
		if(list == null)
			list = Lists.newArrayList();
//		AUser user = aUserDao.getUser((String)session.getAttribute("openId"));
		model.addAttribute("list", list);
		model.addAttribute("userScore", userScore);
		return "mobile/jifen_detail";
	}
	/**
	 * 获取当前用户的余额明细
	 * @param model
	 * @return
	 */
	@RequestMapping("/getBalanceList.do")
	public String balanceList(Model model,HttpServletRequest req){
//		String id = aUserService.getIdByOpenId((String)session.getAttribute("openId"));
		AUser user = LoginUtil.weixinLoginUser(req);
		List<ABalance> list = null;
		String id = "";
		if(user != null){
			id = user.getId();
			list = abalanceService.getBalanceByUser(id,1);
		}
		if(list == null)
			list = Lists.newArrayList();
		model.addAttribute("list", list);
		req.setAttribute("allPage", abalanceService.getBDPage(id));
		return "mobile/yue_detail";
	}
	
	@RequestMapping("/getBalanceListLimit.do")
	public @ResponseBody ActionResponse<Object> balanceListLimit(HttpServletRequest req,HttpSession session){
		ActionResponse<Object> returnData = new ActionResponse<>();
//		String id = aUserService.getIdByOpenId((String)session.getAttribute("openId"));
		AUser user = LoginUtil.weixinLoginUser(req);
		String id = "";
		if(user != null){
			id = user.getId();
		}
		String page = req.getParameter("page");
		List<ABalance> list = abalanceService.getBalanceByUser(id,Integer.parseInt(page));
		if(list==null){
			list = Lists.newArrayList();
		}
		for(int i = 0;i<list.size();i++){
			if("0".equals(list.get(i).getIndFlag())){
				list.get(i).setIndFlag("消费");
			}else{
				list.get(i).setIndFlag("充值");
			}
			
		}
		returnData.setData(list);
		return returnData;
	}
	/**
	 * 获取当前用户的佣金明细
	 * @param model
	 * @return
	 */
	@RequestMapping("/getBrokerageList.do")
	public String brokerageList(HttpServletRequest request, HttpServletResponse response,HttpSession session,Model model){
//		String id = aUserService.getIdByOpenId((String)session.getAttribute("openId"));
		AUser user = LoginUtil.weixinLoginUser(request);
		String id = "";
		if(user != null)
			id = user.getId();
		List<WBrokerage> list = wUserBrokerageService.getBrokerageByUserId(id,1);
		if(list==null){
			list = Lists.newArrayList();
		}
		for(int i =0;i<list.size();i++){
			WOrder w = wOrderSeervice.getWOrderById(list.get(i).getOrderId());
			if(w != null){
				list.get(i).setStatus(BrokerageStatus.brokerageStatus(list.get(i).getStatus()));
				AUser u = aUserDao.getUserById(w.getBuyId());
				if(u != null){
					list.get(i).setFromUserName(u.getName());
				}
				list.get(i).setOrderMoney(w.getDealPrice());
				list.get(i).setTime(sdf.format(w.getCreateDate()));
				list.get(i).setOrderName(w.getGoodsName());
				List<WReward> wr = wrewardService.getRewardByorderId(w.getId(),list.get(i).getMoney(),list.get(i).getUserId());
				if(wr != null && wr.size() == 1){
					WReward en = wr.get(0);
					if("2".equals(en.getRewardType())){
						list.get(i).setRewardType(RewardTypeX.rewardTypeX(String.valueOf(en.getUserStar())));
					}else{
						list.get(i).setRewardType(RewardTypeF.rewardTypeF(en.getRewardType()));
					}
				}
			}
		}
		request.setAttribute("allPage", wUserBrokerageService.getBDPage(id));
		model.addAttribute("list", list);
		return "mobile/yongjin_detail";
	}
	@RequestMapping("getBrokerageListLimit.do")
	public @ResponseBody ActionResponse<Object> brokerageListLimit(HttpServletRequest req,HttpSession session){
		ActionResponse<Object> returnData = new ActionResponse<>();
//		AUser user = aUserService.getUser((String)session.getAttribute("openId"));
		AUser user = LoginUtil.weixinLoginUser(req);
		List<WBrokerage> list = Lists.newArrayList();
		if(user != null){
			String page = req.getParameter("page");
			list = wUserBrokerageService.getBrokerageByUserId(user.getId(),Integer.parseInt(page));
			if(list==null){
				list = Lists.newArrayList();
			}
			for(int i =0;i<list.size();i++){
				list.get(i).setStatus(BrokerageStatus.brokerageStatus(list.get(i).getStatus()));
				WOrder w = wOrderSeervice.getWOrderById(list.get(i).getOrderId());
				if(w != null){
					AUser buy = aUserDao.getUserById(w.getBuyId());
					if(buy != null){
						list.get(i).setFromUserName( buy.getName());
					}
					list.get(i).setOrderMoney(w.getDealPrice());
					list.get(i).setTime(sdf.format(w.getCreateDate()));
					list.get(i).setOrderName(w.getGoodsName());
					List<WReward> wr = wrewardService.getRewardByorderId(w.getId(),list.get(i).getMoney(),list.get(i).getUserId());
					if(wr != null && wr.size() == 1){
						WReward en = wr.get(0);
						if("2".equals(en.getRewardType())){
							list.get(i).setRewardType(RewardTypeX.rewardTypeX(String.valueOf(en.getUserStar())));
						}else{
							list.get(i).setRewardType(RewardTypeF.rewardTypeF(en.getRewardType()));
						}
					}
				}
			}
		}
		returnData.setData(list);
		return returnData;
	}
}












