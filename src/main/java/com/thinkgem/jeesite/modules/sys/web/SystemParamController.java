package com.thinkgem.jeesite.modules.sys.web;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Poster;
import com.thinkgem.jeesite.modules.sys.entity.SysConstant;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.DictService;
import com.thinkgem.jeesite.modules.sys.service.PosterService;
import com.thinkgem.jeesite.modules.sys.service.SysConstantService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 系统参数Controller
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/param")
public class SystemParamController extends BaseController {

	@Autowired
	private PosterService posterService;
	
	@RequiresPermissions(value={"sys:param:view","sys:param:edit"},logical=Logical.OR)
	@RequestMapping(value = "list")
	public String list(Poster poster,Model model,HttpServletRequest request,HttpServletResponse response){
		Page<Poster> page = posterService.findPage(new Page<Poster>(request,response), poster);
		model.addAttribute("page", page);
		return "modules/sys/constant/paramList";
	}
	
	@RequiresPermissions(value={"sys:param:view","sys:param:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Poster poster,Model model) {
		poster = posterService.get(poster);
		model.addAttribute("poster", poster);
		return "modules/sys/constant/paramForm";
	}
	
	@RequiresPermissions(value={"sys:param:view","sys:param:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Poster poster, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		posterService.save(poster);
		addMessage(redirectAttributes,"修改成功");
		return "modules/sys/constant/paramForm";
	}
}
