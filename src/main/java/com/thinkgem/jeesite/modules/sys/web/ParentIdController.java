package com.thinkgem.jeesite.modules.sys.web;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.RespectBinding;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.ks.app.entity.AUser;
import com.ks.app.service.AUserService;
import com.ks.app.utils.ActionResponse;
import com.ks.utils.EnumConstant.UserLevel;
import com.ks.utils.EnumConstant.UserType;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.SystemPath;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.ParentIdService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
@Controller
@RequestMapping(value = "${adminPath}/sys/parentId")
public class ParentIdController extends BaseController{
	@Autowired
	private SystemService systemService;
	@Autowired
	private ParentIdService parentIdService;
	@Autowired
	private AUserService aUserService;
	@Autowired
	private UserDao userdao;
	
	/*@ModelAttribute
	public User get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return systemService.getUser(id);
		}else{
			return new User();
		}
	}*/

	@RequiresPermissions("sys:parentId:view")
	@RequestMapping(value = {"index"})
	public String index(User user, Model model) {
		return "modules/sys/parentIdIndex";
	}

	@RequiresPermissions("sys:parentId:view")
	@RequestMapping(value = {"list", ""})
	public String list(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<User> page = systemService.findUser(new Page<User>(request, response), user);
		List<User> list =page.getList();
		for(User tmp : list){
			if(StringUtils.isNotBlank(tmp.getParentId())){
				User parent = systemService.getUserById(tmp.getParentId());
				if(parent!=null){
					tmp.setParentId(parent.getName());
				}else{
					tmp.setParentId("");
				}
			}
		}
        model.addAttribute("page", page);
		return "modules/sys/parentIdList";
	}
	
	@RequiresPermissions("sys:parentId:view")
	@RequestMapping(value = "form")
	public String form(User user, Model model,HttpServletRequest request,HttpServletResponse response) {
		if (StringUtils.isNotBlank(user.getId())){
			user = systemService.getUserById(user.getId());
			String parentId = user.getParentId();
			if(StringUtils.isNotBlank(parentId)){
				User uuu = userdao.get(parentId);
				if(uuu!=null){
					user.setParentName(uuu.getName());
				}else{
					user.setParentName("");
				}
			}
		}else{
			user = new User();
		}
		List<User> list = parentIdService.getYWY();
		model.addAttribute("list",list);
		model.addAttribute("user", user);
		model.addAttribute("allRoles", systemService.findAllRole());
		return "modules/sys/parentIdForm";
	}
	
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "save")
	public String save(User user, HttpServletRequest request, HttpServletResponse response,Model model, RedirectAttributes redirectAttributes) {
		String id = user.getId();
		String parentId = user.getParentId();
		boolean flag = aUserService.subFlag(id, parentId, true);
		if(flag){
			// 保存用户信息
			systemService.updateParentId(id,parentId);
			// 清除当前用户缓存
			if (UserType.MANAGER.getKey().equals(user.getUserType()) && user.getLoginName().equals(UserUtils.getUser().getLoginName())){
				UserUtils.clearCache();
			}
			addMessage(redirectAttributes, "修改'" + user.getName() + "'的推荐人成功");
			return "redirect:" + adminPath + "/sys/parentId/list";
		}else{
			model.addAttribute("message", "修改失败,推荐人已是用户的团队成员或者是自己");
			return form(user,model,request,response);
		}
	}
	
	@RequiresPermissions("sys:parentId:view")
	@RequestMapping(value="subordinate")
	public String subordinate(User user, HttpServletRequest req){
		String id = user.getId();
		List<AUser> list = aUserService.getSubordinateById(id);
		if(list == null){
			list = Lists.newArrayList();
		}
		for(int i =0;i<list.size();i++){
			BigDecimal bd = aUserService.getBalanceById(list.get(i).getId());
			list.get(i).setPerformance(bd);
			
			int nub = aUserService.countSub(list.get(i).getId());
			list.get(i).setSubFlag(nub);
			
			String str =UserLevel.userLevel(list.get(i).getUserLevel());
			if(StringUtils.isNoneBlank(str)&&str.length()>=2){
				list.get(i).setUserLevelEntity(str.substring(0,2));
			}else{
				list.get(i).setUserLevelEntity(UserLevel.DEFAULT.getValue().substring(0,2));
			}
			String url = list.get(i).getMinPhoto();
			if(StringUtils.isNotBlank(url)){
				list.get(i).setMinPhoto(SystemPath.getRequestPreUrl(req)+url);
			}
		}
		req.setAttribute("sbNub", aUserService.getSubordinateNub(id));
		req.setAttribute("list", list);
		return "modules/sys/subordinateList";
	}
	
	@RequestMapping("findUserList.do")
	public  @ResponseBody ActionResponse<Object> findUserList(HttpServletRequest req){
		String name = req.getParameter("name");
		System.out.println(name);
		List<User> list = userdao.findUserByName(name);
		ActionResponse<Object> returnData = new ActionResponse<>();
		returnData.setData(list);
		return returnData;
	}
}
