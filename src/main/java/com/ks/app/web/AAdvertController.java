package com.ks.app.web;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ks.app.entity.AAdvertInfo;
import com.ks.app.entity.APortalFunction;
import com.ks.app.service.AAdvertService;
import com.ks.app.utils.ActionResponse;
import com.ks.utils.Constant;
import com.ks.utils.EnumConstant;
import com.ks.utils.EnumConstant.AdvertLocationType;

@Controller
@RequestMapping("/app/advert")
public class AAdvertController {

	@Autowired
	private AAdvertService aAdvertService;
	
	@RequestMapping(value="getPortalAdvertList.do")
	public @ResponseBody ActionResponse<Object> getPortalAdvertList(Integer width,Integer height,HttpServletRequest request){
		ActionResponse<Object> returnData = new ActionResponse<Object>();
		String errorMsg = Constant.DEFAULT_ERROR_MSG;
		try{
//			if(LoginUtil.isLogin(userId)){
//				String prePath = SystemPath.getRequestPreUrl(request);
				AAdvertInfo advertInfo = new AAdvertInfo();
				advertInfo.setAdvertState(EnumConstant.TopState.YES.getKey());
				advertInfo.setLocationModule(AdvertLocationType.PORTAL.getKey());
				// 取得列表
				List<AAdvertInfo> advertList = aAdvertService.findAdvertList(advertInfo,width,height,request);
				if(advertList != null){
					returnData.setData(advertList);
				}
				returnData.setingSuccess(Constant.DEFAULT_SUCCESS_MSG);
				return returnData;
//			}else{
//				errorMsg = "请先登录";
//			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		returnData.setingError(errorMsg);
		return returnData;
	}
	//获取首页菜单
	@RequestMapping(value="getPortalFunctions.do")
	public @ResponseBody ActionResponse<Object> getPortalFunctions(String userId,Integer width,Integer height,HttpServletRequest request){
		ActionResponse<Object> returnData = new ActionResponse<Object>();
		String errorMsg = Constant.DEFAULT_ERROR_MSG;
		try{
			AAdvertInfo advertInfo = new AAdvertInfo();
			advertInfo.setAdvertState(EnumConstant.TopState.YES.getKey());
			advertInfo.setLocationModule(AdvertLocationType.PORTAL_MENU.getKey());
			// 取得列表
			List<AAdvertInfo> advertList = aAdvertService.findAdvertList(advertInfo,width,height,request);
			if(advertList != null){
				List<APortalFunction> portalList = new LinkedList<APortalFunction>();
				for (AAdvertInfo portalFunction : advertList) {
					String url = portalFunction.getAdvertUrl();
					APortalFunction fun = new APortalFunction(portalFunction.getId(),portalFunction.getAdvertTitle(),url,portalFunction.getSort());
					portalList.add(fun);
				}
				returnData.setData(portalList);
			}
			returnData.setingSuccess(Constant.DEFAULT_SUCCESS_MSG);
			return returnData;
		}catch (Exception e) {
			e.printStackTrace();
		}
		returnData.setingError(errorMsg);
		return returnData;
	}
	//获取美容攻略
	@RequestMapping(value="getSalonSteps.do")
	public @ResponseBody ActionResponse<Object> getSalonSteps(String userId,Integer width,Integer height,HttpServletRequest request){
		ActionResponse<Object> returnData = new ActionResponse<Object>();
		String errorMsg = Constant.DEFAULT_ERROR_MSG;
		try{
			AAdvertInfo advertInfo = new AAdvertInfo();
			advertInfo.setAdvertState(EnumConstant.TopState.YES.getKey());
			advertInfo.setLocationModule(AdvertLocationType.SALON.getKey());
			// 取得列表
			List<AAdvertInfo> advertList = aAdvertService.findAdvertList(advertInfo,width,height,request);
			if(advertList != null){
				List<APortalFunction> portalList = new LinkedList<APortalFunction>();
				for (AAdvertInfo portalFunction : advertList) {
					String url = portalFunction.getAdvertUrl();
					APortalFunction fun = new APortalFunction(portalFunction.getId(),portalFunction.getAdvertTitle(),url,portalFunction.getSort());
					portalList.add(fun);
				}
				returnData.setData(portalList);
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