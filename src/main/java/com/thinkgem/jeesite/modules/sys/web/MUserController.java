package com.thinkgem.jeesite.modules.sys.web;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ks.app.entity.AMUser;
import com.ks.app.entity.AUser;
import com.ks.app.service.AMUserService;
import com.ks.app.utils.ActionResponse;
import com.ks.utils.EnumConstant.UserLevel;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.MUser;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.MUserService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
@Controller
@RequestMapping(value = "${adminPath}/sys/muser")
public class MUserController extends BaseController{
	@Autowired
	private SystemService systemService;
	@Autowired
	private MUserService mUserService;
	@Autowired
	private AMUserService amUserService;
	
	@RequiresPermissions("sys:muser:edit")
	@RequestMapping(value = {"list"})
	public String list(MUser muser, HttpServletRequest request, HttpServletResponse response, Model model) {
		String name = request.getParameter("name");
		String mobile = request.getParameter("mobile");
		muser.setName(name);
		muser.setMobile(mobile);
		Page<MUser> page = mUserService.findPage(new Page<MUser>(request, response), muser);
		List<MUser> list = page.getList();
		for(int i = 0;i<list.size();i++){
			String userId = list.get(i).getUserId();
			if(StringUtils.isNoneBlank(userId)){
				User user = systemService.getUserById(userId);
				if(user!=null){
					list.get(i).setName(user.getName());
					list.get(i).setMobile(user.getMobile());
					list.get(i).setCard(user.getCard());
				}
				if(StringUtils.isNoneBlank(list.get(i).getParentId())){
					MUser u = mUserService.get(list.get(i).getParentId());
					if(u!=null){
						if(StringUtils.isNoneBlank(u.getUserId())){
							User uuu = systemService.getUserById(u.getUserId());
							list.get(i).setParentName(uuu.getName());
						}
					}
				}
			}
			list.get(i).setmUserLevel(UserLevel.userLevel(list.get(i).getmUserLevel()));
		}
		model.addAttribute("user", muser);
		model.addAttribute("page", page);
		return "modules/sys/muserList";
	}
	
	@RequiresPermissions("sys:muser:edit")
	@RequestMapping(value = {"form"})
	public String form(MUser muser,Model model){
		if(StringUtils.isNoneBlank(muser.getId())){
			muser = mUserService.get(muser);
		}else{
			muser = new MUser();
		}
		model.addAttribute("user", muser);
		return "modules/sys/muserForm";
	}
	
	@RequiresPermissions("sys:muser:edit")
	@RequestMapping(value = {"save"})
	public String save(MUser muser,Model model, RedirectAttributes redirectAttributes){
		mUserService.save(muser);
		addMessage(redirectAttributes, "保存用户成功");
		return "redirect:" + adminPath + "/sys/muser/list";
	}
	
	@RequiresPermissions("sys:muser:edit")
	@RequestMapping(value = {"parentform"})
	public String subordinate(MUser user,Model model){
		if (StringUtils.isNotBlank(user.getId())){
			user = mUserService.get(user.getId());
			String parentId = user.getParentId();
			if(StringUtils.isNotBlank(parentId)){
				MUser u = mUserService.get(parentId);
				if(u!=null){
					if(StringUtils.isNoneBlank(u.getUserId())){
						User uuu = systemService.getUserById(u.getUserId());
						user.setParentName(uuu.getName());
					}
				}else{
					user.setParentName("");
				}
			}
			String userId = user.getUserId();
			if(StringUtils.isNoneBlank(userId)){
				User usus = systemService.getUserById(userId);
				if(usus!=null){
					user.setName(usus.getName());
					user.setMobile(usus.getMobile());
					user.setCard(usus.getCard());
				}
			}
		}else{
			user = new MUser();
		}
		model.addAttribute("user", user);
		return "modules/sys/muserParentForm";
	}
	
	@RequestMapping("findUserList.do")
	public  @ResponseBody ActionResponse<Object> findUserList(HttpServletRequest req){
		String name = req.getParameter("name");
		System.out.println(name);
		MUser user = new MUser();
		user.setName(name);
		List<MUser> list = mUserService.findList(user);
		for(int i = 0;i<list.size();i++){
			String userId = list.get(i).getUserId();
			if(StringUtils.isNoneBlank(userId)){
				User u = systemService.getUserById(userId);
				if(u!=null){
					list.get(i).setName(u.getName());
					list.get(i).setMobile(u.getMobile());
				}
			}
		}
		ActionResponse<Object> returnData = new ActionResponse<>();
		returnData.setData(list);
		return returnData;
	}
	
	@RequiresPermissions("sys:muser:edit")
	@RequestMapping(value = {"parentsave"})
	public String parentsave(MUser muser,Model model, RedirectAttributes redirectAttributes){
		String id = muser.getId();
		String parentId = muser.getParentId();
		boolean flag = mUserService.usubFlag(id, parentId, true);
		if(flag){
			mUserService.parentsave(muser);
			addMessage(redirectAttributes, "修改'" + muser.getName() + "'的推荐人成功");
			return "redirect:" + adminPath + "/sys/muser/list";
		}else{
			model.addAttribute("message", "修改失败,推荐人已是用户的团队成员或者是自己");
			return parentsave(muser,model,redirectAttributes);
		}
	}
	
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value={"subordinate"})
	public String myTeam(String id,Model model){
		List<AMUser> list = amUserService.getByParentId(id);
		for(int i = 0;i<list.size();i++){
			String userId = list.get(i).getUserId();
			User auser = systemService.getUserById(userId);
			if(auser!=null){
				list.get(i).setName(auser.getName());
				list.get(i).setMinPhoto(auser.getMinPhoto());
				list.get(i).setPerformance(amUserService.getPerformance(list.get(i).getId(), new BigDecimal(0)).toString());
				list.get(i).setSubFlag(amUserService.getSubNum(list.get(i).getId()));
				list.get(i).setmUserLevel(UserLevel.userLevel(list.get(i).getmUserLevel()));
			}
		}
		model.addAttribute("list", list);
		return "mobile/m_subordinate";
	}
	
	

}
