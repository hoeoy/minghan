package com.ks.app.utils;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.impl.util.json.JSONObject;

import com.ks.app.entity.AUser;
import com.ks.app.service.AUserService;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.SystemPath;

public class LoginUtil {
	private static AUserService userService = SpringContextHolder.getBean(AUserService.class);
	
	public static boolean isLogin(String userId){
		if(StringUtils.isNotBlank(userId)){
			AUser user = userService.get(userId);
			return user == null ? false:true;
		}
		return false;
	}
	
	public static AUser weixinLoginUser(HttpServletRequest request){
		String openId = (String) request.getSession().getAttribute("openId");
		AUser user = null;
		if(StringUtils.isNotBlank(openId)){
			user = userService.findUserByopenId(openId,"");
		}else{
			String CODE = request.getParameter("code");
			if(StringUtils.isNotBlank(CODE)){
				String APPID = WeixinUtil.APPID;
				String SECRET = WeixinUtil.APPSECRET;
				String URL = WeixinUtil.GRANT_TOKEN_URL.replace("APPID", APPID).replace("SECRET", SECRET).replace("CODE", CODE);
				String jsonStr = URLConnectionHelper.sendGet(URL);
				JSONObject jsonObj = new JSONObject(jsonStr);
				String openid = jsonObj.get("openid").toString();
				request.getSession().setAttribute("openId", openid);
			}
		}
		return user;
	}
	
	public static String grantUser(HttpServletRequest request,String redictUrl){
		String allURL=  SystemPath.getRequestPreUrl(request)+"/"+redictUrl;
		return "redirect:"+String.format(WeixinUtil.GRANT_URL2,WeixinUtil.APPID, allURL);
	}
	
	
}
