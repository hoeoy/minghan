package com.thinkgem.jeesite.modules.sys.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.BeautyDiary;
import com.thinkgem.jeesite.modules.sys.entity.DiaryImage;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.BeautyDiaryService;

@Controller
@RequestMapping(value = "${adminPath}/sys/diary")
public class BeautyDiaryController extends BaseController {
	
	@Autowired
	private BeautyDiaryService beautyDiaryService;

	/**
	 * 查看日记图片
	 * @param diaryImageId
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:diary:view")
	@RequestMapping(value = {"showDiaryImages"})
	public String showDiaryImages(String diaryId, Model model) {
		List<DiaryImage> images = beautyDiaryService.getDiaryImagesByDiaryId(diaryId);
		Map<String, List<DiaryImage>> map = beautyDiaryService.separateImages(images);
		model.addAttribute("before", map.get("before"));
		model.addAttribute("after", map.get("after"));
		model.addAttribute("diaryId", diaryId);
		return "modules/sys/diaryImagesList";
	}
	
	@RequiresPermissions("sys:diary:view")
	@RequestMapping(value = {"index"})
	public String index(User user, Model model) {
		return "modules/sys/diaryIndex";
	}
	
	@RequiresPermissions("sys:diary:view")
	@RequestMapping(value = {"list"})
	public String list(HttpServletRequest request, HttpServletResponse response, Model model){
		BeautyDiary bd = new BeautyDiary();
		Page<BeautyDiary> page = beautyDiaryService.findPage(new Page<BeautyDiary>(request,response), bd);
		   List<BeautyDiary> list = page.getList();
	        for(int i =0;i<list.size();i++){
	        	list.get(i).setTime(DateUtils.formatDate(list.get(i).getCreateDate(), DateUtils.parsePatterns[0]));
	        }
	        page.setList(list);
		model.addAttribute("page", page);
		return "modules/sys/diaryList";
	}
	
	@RequiresPermissions("sys:diary:view")
	@RequestMapping(value={"delete"})
	public String delete(HttpServletRequest req,RedirectAttributes redirectAttributes){
		String id = req.getParameter("id");
		beautyDiaryService.deleteById(id);
		addMessage(redirectAttributes, "删除日记成功");
		return "redirect:" + adminPath + "/sys/diary/list?repage";
	}
	
}
