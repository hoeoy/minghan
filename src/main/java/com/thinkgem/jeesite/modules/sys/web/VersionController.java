package com.thinkgem.jeesite.modules.sys.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.VersionInfo;
import com.thinkgem.jeesite.modules.sys.service.VersionService;

/**
 * 版本Controller
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/version")
public class VersionController extends BaseController {

	@Autowired
	private VersionService versionService;
	
	//@RequiresPermissions("sys:version:view")
	@RequestMapping(value = {"list", ""})
	public String list(VersionInfo versionInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<VersionInfo> page = versionService.getVersionList(new Page<VersionInfo>(request, response), versionInfo); 
        model.addAttribute("page", page);
        model.addAttribute("version", versionInfo);
		return "modules/sys/versionList";
	}

	//@RequiresPermissions("sys:version:view")
	@RequestMapping(value = "form")
	public String form(VersionInfo versionInfo, Model model) {
		versionInfo = StringUtils.isNotBlank(versionInfo.getId()) ? versionService.get(versionInfo):versionInfo;
		model.addAttribute("version", versionInfo);
		return "modules/sys/versionForm";
	}

	//@RequiresPermissions(value={"sys:version:edit","sys:version:add"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(VersionInfo versionInfo,HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, versionInfo)){
			return form(versionInfo, model);
		}
		/*if (EnumConstant.VersionType.ANDROID.getKey().equals(versionInfo.getVersionType()) && ServletFileUpload.isMultipartContent(request)) {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			// 取得上传的文件
			MultipartFile file = multipartRequest.getFile("apkFile");
			if (file != null && !file.isEmpty()) {
				// 文件保存路径
				String uploadPath = Config.getUploadApppath();
				
				String savePath = Config.getUploadBasepath() + uploadPath;

				File fileDir = new File(savePath);
				// 如果文件夹不存在则创建
				if (!fileDir.exists() && !fileDir.isDirectory()) {
					fileDir.mkdirs();
				}

				// 文件名称
				String fileName = "fojiao_"
						+ DateUtils.getDate(DateUtils.DATE_FORMAT_3) + ".apk";
				// 保存文件
				String saveFile = savePath+ File.separator + fileName;
				try {
					file.transferTo(new File(saveFile));

					String vUrl = Config.getUploadBasePrepath()+uploadPath.replaceAll("\\\\", "/")+ "/" + fileName;
					versionInfo.setFileName(vUrl);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}*/
		versionService.insertVersion(versionInfo);
		addMessage(redirectAttributes, "保存版本成功");
		return "redirect:" + adminPath + "/sys/version/?repage";
	}
	
	//@RequiresPermissions("sys:dict:edit")
	@RequestMapping(value = "delete")
	public String delete(VersionInfo versionInfo, RedirectAttributes redirectAttributes) {
		versionService.delete(versionInfo);
		addMessage(redirectAttributes, "删除版本成功");
		return "redirect:" + adminPath + "/sys/version/?repage";
	}
}
