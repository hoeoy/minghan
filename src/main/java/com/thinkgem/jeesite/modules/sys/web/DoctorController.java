package com.thinkgem.jeesite.modules.sys.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Doctor;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.DoctorService;
/**
 * 专家团队
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/doctor")
public class DoctorController extends BaseController{
	@Autowired
	DoctorService doctorService;
	
	@RequiresPermissions("sys:doctor:view")
	@RequestMapping(value = {"index"})
	public String index(User user, Model model) {
		return "modules/sys/doctorIndex";
	}
	
	@RequiresPermissions("sys:doctor:view")
	@RequestMapping(value = {"list", ""})
	public String list(Doctor doctor, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Doctor> page = doctorService.findPage(new Page<Doctor>(request, response), doctor);
        model.addAttribute("page", page);
		return "modules/sys/doctorList";
	}
	
	@RequiresPermissions("sys:doctor:view")
	@RequestMapping(value = "form")
	public String form(Model model,Doctor doctor) {
		doctor = doctorService.get(doctor);
		if(doctor==null){
			doctor = new Doctor();
		}
		model.addAttribute("doctor", doctor);
		return "modules/sys/doctorForm";
	}
	
	@RequiresPermissions("sys:doctor:view")
	@RequestMapping(value = "delete")
	public String delete(HttpServletRequest req,RedirectAttributes redirectAttributes) {
		String id = req.getParameter("id");
		doctorService.deleteById(id);
		addMessage(redirectAttributes, "删除专家成功");
		return "redirect:" + adminPath + "/sys/doctor/list?repage";
	}
	
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "save")
	public String save(Doctor doctor, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		doctorService.save(doctor);
		addMessage(redirectAttributes, "保存专家'" + doctor.getName() + "'成功");
		return "redirect:" + adminPath + "/sys/doctor/list?repage";
	}
}
