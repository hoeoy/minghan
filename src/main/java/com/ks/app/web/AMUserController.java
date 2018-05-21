package com.ks.app.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ks.app.dao.APosterDao;
import com.ks.app.entity.AMTakeOut;
import com.ks.app.entity.AMUser;
import com.ks.app.entity.AUser;
import com.ks.app.service.AMTakeOutService;
import com.ks.app.service.AMUserService;
import com.ks.app.service.AUserService;
import com.ks.app.utils.ActionResponse;
import com.ks.utils.EnumConstant.UserLevel;
import com.ks.app.utils.WeixinERCodeUtil;
import com.ks.utils.Config;
import com.thinkgem.jeesite.common.utils.FileUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.MUser;
@Controller
@RequestMapping(value="/app/muser")
public class AMUserController extends BaseController{
	@Autowired
	private AMTakeOutService amTakeOutService;
	@Autowired
	private AMUserService amUserService;
	@Autowired
	private AUserService aUserService;
	@Autowired
	private APosterDao aPosterDao;
	
	
	@RequestMapping(value="goods-center.do")
	public String goodsCenter(HttpSession session,Model model){
		String openId = (String)session.getAttribute("openId");
		String userId = aUserService.getIdByOpenId(openId);
		AMUser user = amUserService.getMuserByUserId(userId);
		if(user==null){
			user = new AMUser();
			user.setUserId(userId);
			user.setmUserLevel("0");
			amUserService.save(user);
			user = amUserService.getMuserByUserId(userId);
			user.setPerformance("0.00");
		}
		int index = amUserService.getSubNum(user.getId());
		model.addAttribute("subNum", index);
		BigDecimal bd = amUserService.getPerformance(user.getId(), new BigDecimal(0));
		user.setPerformance(bd.toString());
		model.addAttribute("user", user);
		return "mobile/good-center";
	}
	
	
	@RequestMapping(value="mShare.do")
	public String mShare(HttpSession session,HttpServletRequest req,HttpServletResponse resp,Model model) throws ParseException, IOException {
		String accessToken = aUserService.getToken();
		String openId = (String)session.getAttribute("openId");
		String userId = aUserService.getIdByOpenId(openId);
		AUser auser= aUserService.getUserById(userId);
		String userName= auser.getName();
		AMUser user = amUserService.getMuserByUserId(userId);
    	if(StringUtils.isNotBlank(accessToken)){
        	if(user != null){
        		String path = aPosterDao.getMPath();
        		//创建二维码,并下载到本地,[外部地址，服务器地址]
        		path = FileUtils.getFileSavePath(path, Config.getUploadPoster());
        		String[] resultFile = WeixinERCodeUtil.getShareERCodePathOnM(path,accessToken,user.getId(),userName,req);
        		if(resultFile != null){
        			String filePath = resultFile[0];
        			try { 
        				Thread.sleep(100); 
        				} catch (InterruptedException e) { 
        				e.printStackTrace(); 
        				}
        			req.setAttribute("mfilePath", filePath);
        		}
        	}
    	}
		
		return "mobile/mShare";
	}
	
	@RequestMapping(value="myTeam.do")
	public String myTeam(String id,Model model){
		List<AMUser> list = amUserService.getByParentId(id);
		for(int i = 0;i<list.size();i++){
			String userId = list.get(i).getUserId();
			AUser auser = aUserService.getUserById(userId);
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
	
	@RequestMapping(value="seeSubordinate.do")
	public @ResponseBody ActionResponse<Object> seeSubordinate(String id){
		ActionResponse<Object> data = new ActionResponse<Object>();
		List<AMUser> list = amUserService.getByParentId(id);
		for(int i = 0;i<list.size();i++){
			String userId = list.get(i).getUserId();
			AUser auser = aUserService.getUserById(userId);
			if(auser!=null){
				list.get(i).setName(auser.getName());
				list.get(i).setMinPhoto(auser.getMinPhoto());
				list.get(i).setPerformance(amUserService.getPerformance(list.get(i).getId(), new BigDecimal(0)).toString());
				list.get(i).setSubFlag(amUserService.getSubNum(list.get(i).getId()));
				list.get(i).setmUserLevel(UserLevel.userLevel(list.get(i).getmUserLevel()));
			}
		}
		data.setData(list);
		return data;
	}
	
}
