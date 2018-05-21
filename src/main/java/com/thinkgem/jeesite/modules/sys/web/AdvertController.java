package com.thinkgem.jeesite.modules.sys.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ks.app.utils.ActionResponse;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.AdvertInfo;
import com.thinkgem.jeesite.modules.sys.entity.AdvertLocationType;
import com.thinkgem.jeesite.modules.sys.service.AdvertService;

/**
 * 广告Controller
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/advert")
public class AdvertController extends BaseController {

	@Autowired
	private AdvertService advertService;
	
	@RequiresPermissions("sys:advert:view")
	@RequestMapping(value = {"list"})
	public String list(AdvertInfo advertInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<AdvertInfo> page = advertService.findAdvertPage(new Page<AdvertInfo>(request, response), advertInfo); 
        model.addAttribute("page", page);
        model.addAttribute("advert", advertInfo);
		return "modules/sys/advert/advertList";
	}

	@RequiresPermissions("sys:advert:view")
	@RequestMapping(value = "form")
	public String form(AdvertInfo advertInfo, Model model) {
		advertInfo = StringUtils.isNotBlank(advertInfo.getId()) ? advertService.get(advertInfo):advertInfo;
		model.addAttribute("advert", advertInfo);
		return "modules/sys/advert/advertForm";
	}

	@RequiresPermissions(value={"sys:advert:edit","sys:advert:add"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(AdvertInfo advertInfo,HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, advertInfo)){
			return form(advertInfo, model);
		}
		advertService.saveAdvert(advertInfo);
		addMessage(redirectAttributes, "保存广告位成功");
//		model.addAttribute("advertInfo", advertInfo);
		return "redirect:" + adminPath + "/sys/advert/list?locationType="+advertInfo.getLocationType()+"&repage";
	}
	@RequiresPermissions("sys:advert:edit")
	@RequestMapping(value = "sort")
	public @ResponseBody ActionResponse<Object> sort(AdvertInfo advertInfo) {
		ActionResponse<Object> data = new ActionResponse<Object>();
		try{
			advertService.updateAdvertSort(advertInfo);
			data.setingSuccess("排序成功");
		}catch (Exception e) {
			e.printStackTrace();
			data.setingError("排序失败");
		}
		return data;
	}
	
	@RequiresPermissions("sys:advert:delete")
	@RequestMapping(value = "delete")
	public String delete(AdvertInfo advertInfo, RedirectAttributes redirectAttributes) {
		advertService.delete(advertInfo);
		addMessage(redirectAttributes, "删除广告位成功");
		return "redirect:" + adminPath + "/sys/advert/list?locationType="+advertInfo.getLocationType()+"&repage";
	}
	
	@RequiresPermissions("sys:location:view")
	@RequestMapping(value = {"listLocation", ""})
	public String listLocation(AdvertLocationType locationType, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<AdvertLocationType> page = advertService.findLocationTypePage(new Page<AdvertLocationType>(request, response), locationType); 
        model.addAttribute("page", page);
        model.addAttribute("locationType", locationType);
		return "modules/sys/advert/locationTypeList";
	}

	@RequiresPermissions("sys:location:view")
	@RequestMapping(value = "locationForm")
	public String locationForm(AdvertLocationType locationType, Model model) {
		locationType = StringUtils.isNotBlank(locationType.getId()) ? advertService.getLocationTypeById(locationType.getId()):locationType;
		model.addAttribute("locationType", locationType);
		return "modules/sys/advert/locationTypeForm";
	}

	@RequiresPermissions(value={"sys:location:add"})
	@RequestMapping(value = "saveLocation")
	public String saveLocation(AdvertLocationType locationType,HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, locationType)){
			return locationForm(locationType, model);
		}
		advertService.insertLocationType(locationType);
		addMessage(redirectAttributes, "保存分类成功");
		return "redirect:" + adminPath + "/sys/advert/listLocation?repage";
	}
	@RequiresPermissions("sys:location:view")
	@RequestMapping(value = "sortLocation")
	public @ResponseBody ActionResponse<Object> sortLocation(AdvertLocationType locationType) {
		ActionResponse<Object> data = new ActionResponse<Object>();
		try{
			advertService.updateLocationTypeSort(locationType);
			data.setingSuccess("排序成功");
		}catch (Exception e) {
			e.printStackTrace();
			data.setingError("排序失败");
		}
		return data;
	}
}
