package com.thinkgem.jeesite.modules.sys.web;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ks.app.entity.WTakeOut;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SysConstantService;
import com.thinkgem.jeesite.modules.sys.service.TakeOutService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
@Controller
@RequestMapping(value = "${adminPath}/sys/remittance")
/**
 * 提现/返现管理
 * @author Administrator
 *
 */
public class RemittanceController extends BaseController{
	@Autowired
	private TakeOutService takeOutService;
	@Autowired
	private UserDao userDao;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
	
	@ModelAttribute("remittance")
	public WTakeOut get() {
			return new WTakeOut();
	}
	
	@RequiresPermissions("sys:remittance:view")
	@RequestMapping(value = {"index"})
	public String index(WTakeOut wto, Model model) {
		return "modules/sys/remittanceIndex";
	}
	
	@RequiresPermissions("sys:remittance:view")
	@RequestMapping(value = {"list", ""})
	public String list(WTakeOut wto, HttpServletRequest request, HttpServletResponse response, Model model) throws ParseException {
		String userName = request.getParameter("userName");
		wto.setUserName(userName);
		String start = request.getParameter("startTime");
		System.out.println("***************************start"+start);
		if(start!=null&&!("".equals(start))){
			Date startTime =sdf1.parse(start);
			wto.setStartTime(startTime);
		}
		String end =request.getParameter("endTime");
		System.out.println("***************************end"+end);
		if(end!=null&&!("".equals(end))){
			Date endTime = sdf1.parse(end);
			wto.setEndTime(endTime);
		}
		Page<WTakeOut> page = takeOutService.findPage(new Page<WTakeOut>(request,response), wto);
		List<WTakeOut> list = page.getList();
		BigDecimal bd = new BigDecimal(0);
		for(int i=0;i<list.size();i++){
			if("0".equals(list.get(i).getType())){
				if(list.get(i).getServiceCharge()!=null){
					list.get(i).setShiDa(list.get(i).getBrokerage().subtract(list.get(i).getServiceCharge()));
					bd = bd.add(list.get(i).getShiDa());
				}else{
					list.get(i).setShiDa(list.get(i).getBrokerage());
					bd = bd.add(list.get(i).getShiDa());
				}
			}else{
				BigDecimal bbb = list.get(i).getServiceCharge();
				if(list.get(i).getServiceCharge()!=null){
					list.get(i).setShiDa(list.get(i).getBack().subtract(list.get(i).getServiceCharge()));
					bd = bd.add(list.get(i).getShiDa());
				}else{
					list.get(i).setShiDa(list.get(i).getBack());
					bd = bd.add(list.get(i).getShiDa());
				}
			}
			User user = userDao.get(list.get(i).getUserId());
			if(user != null){
				list.get(i).setUserBankCard(user.getBankCard());
				list.get(i).setUserBankType(user.getBankType());
				list.get(i).setUserMobile(user.getMobile());
				list.get(i).setUserFlag(user.getStartFlag());
			}
			User audit = userDao.get(list.get(i).getAuditId());
			if(audit!=null){
				list.get(i).setAuditName(audit.getName());
			}
			String str = list.get(i).getRemittanceGM();
			if(StringUtils.isNoneBlank(str)){
				User Gm = userDao.get(str);
				list.get(i).setRemittanceGM(Gm.getName());
			}
			list.get(i).setTime(sdf.format(list.get(i).getCreateDate()));
		}
		page.setList(list);
		model.addAttribute("wto", wto);
		 model.addAttribute("bd", bd);
		 model.addAttribute("page", page);
		return "modules/sys/remittanceList";
	}
	
	@RequiresPermissions("sys:remittance:view")
	@RequestMapping(value = "remi")
	public String remi(HttpServletRequest req,Model model,RedirectAttributes redirectAttributes){
		String id = req.getParameter("id");
		User curUser = UserUtils.getUser();
		String gmId = curUser.getId();
		takeOutService.remi(id,gmId);
		addMessage(redirectAttributes, "转账成功");
		return "redirect:" + adminPath + "/sys/remittance/list?repage";
	}
}
