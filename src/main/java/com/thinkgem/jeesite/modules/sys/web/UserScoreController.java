package com.thinkgem.jeesite.modules.sys.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Score;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.UserScoreService;

/**
 * 展示积分明细
 * @author pc-20170905
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/userScore")
public class UserScoreController extends BaseController{

	@Autowired
	private UserScoreService userScoreService;
	
	@RequestMapping(value = {"index"})
	public String index(User user, Model model) {
		return "modules/sys/userScoreIndex";
	}
	
	@RequestMapping(value = {"list"})
	public String list(Score score, HttpServletRequest request, HttpServletResponse response, Model model) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String start = request.getParameter("startTime");
		//System.out.println("***************************start"+start);
		if(start!=null&&!("".equals(start))){
			Date startTime =sdf.parse(start);
			score.setStartTime(startTime);
		}
		String end =request.getParameter("endTime");
		//System.out.println("***************************end"+end);
		if(end!=null&&!("".equals(end))){
			Date endTime = sdf.parse(end);
			score.setEndTime(endTime);
		}
		Page<Score> page = userScoreService.getScoreList(new Page<Score>(request, response), score);
		model.addAttribute("page", page);
//		model.addAttribute("score", score);
		return "modules/sys/userScoreList";
	}
}
















