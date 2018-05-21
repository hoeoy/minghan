package com.ks.app.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ks.app.entity.AVersionInfo;
import com.ks.app.service.AVersionService;
import com.ks.app.utils.ActionResponse;
import com.ks.utils.Constant;
import com.thinkgem.jeesite.common.utils.SystemPath;

@Controller
@RequestMapping("/app/version")
public class AVersionController {

	@Autowired
	private AVersionService aVersionService;
	/**
	 * 取得最新版本信息
	 * 
	 * @return 版本信息
	 */
	@RequestMapping("getVersionInfo.do")
	public @ResponseBody ActionResponse<Object>  getVersionInfo(@RequestParam(required=true)String versionType,HttpServletRequest request){
		ActionResponse<Object> returnData = new ActionResponse<Object>();
		String errorMsg = Constant.DEFAULT_ERROR_MSG;
		try{
			String prePath = SystemPath.getRequestPreUrl(request);
			// 取得最新版本信息
			AVersionInfo versionInfo = aVersionService.getLastVersionInfo(versionType,prePath);

			if (versionInfo != null) {
				returnData.setData(versionInfo);
			} 
			returnData.setingSuccess(Constant.DEFAULT_SUCCESS_MSG);
			return returnData;
		}catch (Exception e) {
			e.printStackTrace();
		}
		returnData.setingError(errorMsg);
		return returnData;
	}
}