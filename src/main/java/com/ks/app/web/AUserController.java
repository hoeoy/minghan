package com.ks.app.web;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.common.collect.Maps;
import com.ks.app.entity.AUser;
import com.ks.app.service.AUserService;
import com.ks.app.utils.ActionResponse;
import com.ks.app.utils.SMSSendUtil;
import com.ks.utils.Config;
import com.ks.utils.Constant;
import com.ks.utils.EnumConstant.Integral;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.FileUtils;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.SystemPath;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.SysConstant;
import com.thinkgem.jeesite.modules.sys.service.SysConstantService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;

/**
 * 登录Controller
 */
@Controller
@RequestMapping(value = "/app/user")
public class AUserController extends BaseController{
	public static final int NUM = 4;
	@Autowired
	private AUserService aUserService;
	@Autowired
	private SysConstantService sysConstantService;
	
	/**
	 * 用户登录取得用户信息
	 * 
	 * @param userInfo
	 * @return 用户信息
	 */
//	@RequestMapping("login.do")
//	public @ResponseBody ActionResponse<Object> doLogin(@RequestParam(required = true) String mobile,@RequestParam(required = true) String password,HttpServletRequest request,HttpServletResponse response){
//
//		ActionResponse<Object> returnData = new ActionResponse<Object>();
//		try{
//			AUser user = aUserService.getUserByMobile(mobile, password,"2",SystemPath.getRequestPreUrl(request));
//			
//			if (user != null) {
//				if(Constant.DELETE_FLAG_1.equals(user.getDelFlag())){
//					returnData.setingError("您的账户已被删除，故现在无法登录。");
//					return returnData;
//				}
//				returnData.setCode(Constant.SUCCESS_CODE);
//				returnData.setMsg("登录成功");
////				AUserService.changeUserInfo(user, SystemPath.getRequestPreUrl(request));
//				returnData.setData(user);
//				return returnData;
//			}
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//		// 验证失败
//		returnData.setingError(Constant.LOGIN_PHONE_ERROR);
//		return returnData;
//	}
    
    /**
	 * 微信账号登录
	 * @param photo 头像地址
	 * @param name 昵称
	 * @param openId openId
	 * @return
	 */
	@RequestMapping(value="loginByWeixin.do")
	public @ResponseBody ActionResponse<Object> loginByWeixin(@ModelAttribute AUser user,HttpServletRequest request){
		ActionResponse<Object> returnData = new ActionResponse<Object>();
		try {
			//查询此 openID 是否已经存在于数据库中，如果存在，则表示已经登陆过了，直接给予登陆
			AUser tmpuser = aUserService.getUserByWeixinOpenId(user.getOpenId(),SystemPath.getRequestPreUrl(request));
			if(tmpuser != null){
				if(Constant.DELETE_FLAG_1.equals(tmpuser.getDelFlag())){
					returnData.setingError("您的账户已被删除，故现在用此微信。");
					return returnData;
				}
//				AUserService.changeUserInfo(tmpuser, SystemPath.getRequestPreUrl(request));
	        	returnData.setData(tmpuser);
	        	returnData.setMsg("登录成功");
	        	returnData.setCode(Constant.SUCCESS_CODE);
			}else {
				
				//没有，注册用户  
				
				//将图片保存到七牛
				//String photo = QiniuUtil.uploadNet(user.getPhoto(), QiniuUtil.getSavePathName("1", null));
				
				// 取得用户头像图片
				String weixinPhoto = user.getPhoto();
				if(StringUtils.isNotBlank(weixinPhoto)){
					String id = IdGen.uuid();
					String fileName = id+".jpg";
					String thumbFileName = id+"_sec.jpg";
					String photo = FileUtils.getUploadPath(Config.getUploadUserpath());
//					String savePath = Config.getUploadBasepath()+photo;
					String resultPaths[] = FileUtils.downloadNet(weixinPhoto, fileName, photo,thumbFileName,true);
					if(resultPaths != null){
						//删除大图标
//						String basePath = Config.getUploadBasepath();
//						FileUtils.deleteFile(basePath+resultPaths[0]);
						
						user.setPhoto(resultPaths[0]);
						user.setMinPhoto(resultPaths[1]);
					}
				}
				aUserService.insertUser(user);
				AUserService.changeUserInfo(user, SystemPath.getRequestPreUrl(request));
	        	returnData.setData(user);
	        	returnData.setMsg("登录成功");
	        	returnData.setCode(Constant.SUCCESS_CODE);
			} 
		} catch (Exception e) {
        	returnData.setingError("登录失败");
		}
		return returnData;
	}

	/*@RequestMapping("bindWeixinOpenId.do")
	public @ResponseBody ActionResponse<Object> bindWeixinOpenId(@RequestParam(required = true) String mobile, 
			@RequestParam(required = true) String password, 
			@RequestParam(required = true) String openId,HttpServletRequest request) {
		ActionResponse<Object> returnData = new ActionResponse<Object>();
		try {
			AUser user = aUserService.getUserByMobile(mobile, password,false,SystemPath.getRequestPreUrl(request));
			if(user != null) {
				if(Constant.DELETE_FLAG_1.equals(user.getDelFlag())){
					aUserService.deleteUserByOpenId(openId);
					returnData.setCode(Constant.FAILURE_CODE);
					returnData.setMsg("您的账户已被删除，故现在无法绑定微信登录");
					return returnData;
				}
				user.setOpenId(openId);
				aUserService.updateUserInfo(user,request);
				
//				AUserService.changeUserInfo(user, SystemPath.getRequestPreUrl(request));
				returnData.setData(user);
	        	returnData.setMsg("绑定成功");
	        	returnData.setCode(Constant.SUCCESS_CODE);
			}else {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("openId", openId);
				
				returnData.setCode(Constant.FAILURE_CODE);
				returnData.setMsg(Constant.LOGIN_PHONE_ERROR);
			}
		} catch (Exception e) {
			returnData.setMsg("登录失败");
        	returnData.setCode(Constant.FAILURE_CODE);
		}
		return returnData;
	}*/
	@RequestMapping("getUserById.do")
	public @ResponseBody ActionResponse<Object> getUserById(String userId,HttpServletRequest request){
		ActionResponse<Object> returnData = new ActionResponse<Object>();
		// 根据用户ID取得用户信息
		AUser user = aUserService.getUserById(userId);

		if (user != null) {
			AUserService.changeUserInfo(user, SystemPath.getRequestPreUrl(request));
			returnData.setData(user);
			returnData.setCode(Constant.SUCCESS_CODE);
		} else {
			returnData.setingError("获取用户信息失败");
		}

		return returnData;
	}
	/**
	 * 更新用户信息
	 * 
	 * @return
	 */
	@RequestMapping(value="updateUser.do")
	public @ResponseBody ActionResponse<Object> updateUser(@ModelAttribute AUser userInfo, HttpServletRequest request){

		ActionResponse<Object> returnData = new ActionResponse<Object>();

		String userId = userInfo.getId();
		// 根据用户ID取得用户信息
		AUser user = aUserService.getUserById(userId);

		if (user != null) {
			// 更新用戶
			user.setName(userInfo.getName());
			int result = aUserService.updateUserInfo(user,request);

			if(result == 1) {
				returnData.setingSuccess("修改用户信息成功");
			} else {
				returnData.setingError("用户信息修改失败");
			}
		} else {
			returnData.setingError("无此用户");
		}

		return returnData;
	}
//	/**
//	 * 注册用户
//	 * 
//	 * @return
//	 */
//	@RequestMapping(value="registerUser.do")
//	public @ResponseBody ActionResponse<Object> registerUser(String mobile,String code,String password, HttpServletRequest request){
//		
//		ActionResponse<Object> returnData = new ActionResponse<Object>();
//		
//		// 根据用户ID取得用户信息
//		AUser user = aUserService.getUserByMobile(mobile, password,"1",SystemPath.getRequestPreUrl(request));
//		
//		if (user != null) {
//			returnData.setingError("此手机号已注册");
//		} else {
//			if(!isValidateCode(mobile, code, false)){
//				returnData.setingError("验证码不正确");
//				return returnData;
//			}
//			// 新增用戶
//			user = new AUser();
//			user.setName(mobile);
//			user.setMobile(mobile);
//			String epwd = SystemService.entryptPassword(password);
//			user.setPassword(epwd);
//			//注册时送的积分
//			user.setPoint(Integral.NEWUSER.getKey());
//			aUserService.insertUser(user);
//			
//			returnData.setingSuccess("手机注册成功");
//		}
//		
//		return returnData;
//	}
	/**
	 *
	 * 用户头像修改接口
	 * @param userId 登录用户Id
	 */
	@RequestMapping("updateUserPhoto.do")
	protected @ResponseBody ActionResponse<Object> updateUserPhoto(@RequestParam(required=true)String userId, MultipartHttpServletRequest request){
		ActionResponse<Object> serviceResponse = new ActionResponse<Object>();
		try {
			// 根据用户ID取得用户信息
			AUser user = aUserService.getUserById(userId);

			if (user != null) {
				serviceResponse = aUserService.updateUserPhoto(userId,request);
			} else {
				serviceResponse.setingError("无此用户");
			}
		} catch (Exception e) {
			e.printStackTrace();
			serviceResponse.setingError("修改头像失败");
		}
		return serviceResponse;
	}
	/**
	 *
	 * 用户昵称修改接口
	 * @param userId 登录用户Id
	 */
	@RequestMapping("updateUserName.do")
	protected @ResponseBody ActionResponse<Object> updateUserName(@RequestParam(required=true)String userId,@RequestParam(required=true)String name,HttpServletRequest request){
		ActionResponse<Object> serviceResponse = new ActionResponse<Object>();
		try {
			// 根据用户ID取得用户信息
			AUser user = aUserService.getUserById(userId);

			if (user != null) {
				// 更新用戶
				user.setName(name);
				int result = aUserService.updateUserInfo(user,request);

				if(result == 1) {
					serviceResponse.setingSuccess("修改用户昵称成功");
				} else {
					serviceResponse.setingError("用户用户昵称失败");
				}
			} else {
				serviceResponse.setingError("无此用户");
			}
		} catch (Exception e) {
			e.printStackTrace();
			serviceResponse.setingError("修改用户昵称失败");
		}
		return serviceResponse;
	}
//	/**
//	 *
//	 * 用户签名修改接口
//	 * @param userId 登录用户Id
//	 */
//	@RequestMapping("updateUserSign.do")
//	protected @ResponseBody ActionResponse<Object> updateUserSign(@RequestParam(required=true)String userId,@RequestParam(required=true)String sign,HttpServletRequest request){
//		ActionResponse<Object> serviceResponse = new ActionResponse<Object>();
//		try {
//			// 根据用户ID取得用户信息
//			AUser user = aUserService.getUserById(userId);
//			
//			if (user != null) {
//				// 更新用戶
//				user.setSign(sign);
//				int result = aUserService.updateUserInfo(user,request);
//				
//				if(result == 1) {
//					serviceResponse.setingSuccess("修改用户签名成功");
//				} else {
//					serviceResponse.setingError("用户用户签名失败");
//				}
//			} else {
//				serviceResponse.setingError("无此用户");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			serviceResponse.setingError("修改用户签名失败");
//		}
//		return serviceResponse;
//	}
	/**
	 * @param mobile 手机号
	 * @return
	 */
	@RequestMapping("sendVercode.do")
	public @ResponseBody ActionResponse<Object> sendVercode(String mobile,HttpServletRequest request){
		ActionResponse<Object> serviceResponse = new ActionResponse<Object>();
		 //随机生成 num 位验证码
        String code="";
        Random r = new Random(new Date().getTime());
        for(int i=0;i<NUM;i++){
            code = code+r.nextInt(10);
        }
		try {
			String result = SMSSendUtil.sendSMSCode(code, mobile);
			if("true".equals(result)){
				//将验证码加入容器中---用户输入验证码之后验证
				isValidateCode(mobile, code, true);
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("code", code);
				serviceResponse.setData(map);
				serviceResponse.setingSuccess("发送成功");
			}else{
				serviceResponse.setingError(result);
			}
			return serviceResponse;
		} catch (Exception e) {
			e.printStackTrace();
		}
		serviceResponse.setingError("发送失败");
		return serviceResponse;
		
	}

	/**
	 * 根据手机号码，修改用户密码
	 * 
	 * @param mobile 手机号码
	 * @param password 新密码
	 * @return
	 */
	@RequestMapping("updatePassword.do")
	public @ResponseBody ActionResponse<Object> updatePassword(String mobile,String code, String password){
		ActionResponse<Object> serviceResponse = new ActionResponse<Object>();
		if(!isValidateCode(mobile, code, false)){
			serviceResponse.setingError("验证码不正确");
			return serviceResponse;
		}
		// 更新用戶
		int result = aUserService.updatePassword(mobile, password);

		if(result == 1) {
			// 更新成功
			serviceResponse.setingSuccess("找回密码成功");
		} else {
			// 更新失败
			serviceResponse.setingError("找回密码失败");
		}

		return serviceResponse;
	}

//	/**
//	 * 根据用户ID，修改用户密码
//	 * 
//	 * @param userId 用户ID
//	 * @param oldPassword 旧密码
//	 * @param newPassword 新密码
//	 * @return
//	 */
//	@RequestMapping("changePassword.do")
//	public @ResponseBody ActionResponse<Object> changePassword(String userId, String oldPassword, String newPassword){
//		ActionResponse<Object> serviceResponse = new ActionResponse<Object>();
//		// 根据用户ID取得用户信息
//		AUser user = aUserService.getUserById(userId);
//		if(user != null){
//			if(StringUtils.isNotBlank(oldPassword)){
//				String password = SystemService.entryptPassword(oldPassword);
//				if(password.equals(user.getPassword())){
//					user.setPassword(SystemService.entryptPassword(newPassword));
//					// 更新用戶
//					int result = aUserService.changePassword(user);
//					if(result == 1) {
//						serviceResponse.setingSuccess("修改密码成功");
//					}
//				} else {
//					serviceResponse.setingError("原密码错误，请重新输入。");
//				}
//			} else {
//				serviceResponse.setingError("原密码不能为空。");
//			}
//		} else {
//			serviceResponse.setingError("请先登录");
//		}
//		return serviceResponse;
//	}
	/**
	 * 是否是验证码
	 * @param useruame 用户名
	 * @param isAdd 是否添加 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isValidateCode(String mobile,String appCode, boolean isAdd){
		Map<String, String> appCodeMap = (Map<String, String>)CacheUtils.get("appCodeMap");
		if (appCodeMap==null){
			appCodeMap = Maps.newHashMap();
			CacheUtils.put("appCodeMap", appCodeMap);
		}
		if (isAdd){
			appCodeMap.put(mobile, appCode);
		}else{
			String code = appCodeMap.get(mobile);
			if (code==null || !code.equals(appCode)){
				return false;
			}
			appCodeMap.remove(mobile);
		}
		return true;
	}
	
//	/**
//	 * 更新用户定位地址
//	 * 
//	 * @param request
//	 * @param userId
//	 * @param location
//	 * @param longitude
//	 * @param latitude
//	 * @param response
//	 */
//	@RequestMapping("updateUserLocation.do")
//	protected @ResponseBody ActionResponse<Object> updateUserLocation(String userId,String location,Double longitude,Double latitude,HttpServletRequest request){
//		ActionResponse<Object> serviceResponse = new ActionResponse<Object>();
//		try {
//			// 根据用户ID取得用户信息
//			AUser user = aUserService.getUserById(userId);
//			if(user != null){
//				user.setId(userId);
//				user.setLocation(location);
//				user.setLongitude(longitude);
//				user.setLatitude(latitude);
//				aUserService.updateUserInfo(user, request);
//			} else {
//				serviceResponse.setingError("请先登录");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			serviceResponse.setingError("更新失败");
//		}
//		return serviceResponse;
//	}
//	
	/**
	 * 获取客服电话
	 * 
	 * @return
	 */
	@RequestMapping(value="getCustomPhone.do")
	public @ResponseBody ActionResponse<Object> getCustomPhone(){
		
		ActionResponse<Object> returnData = new ActionResponse<Object>();
		try {
			SysConstant params = sysConstantService.getCustomPhoneDict();
			if(params != null){
				returnData.setData(params);
			}else{
				returnData.setData(new SysConstant());
			}
			returnData.setingSuccess(Constant.DEFAULT_SUCCESS_MSG);
		} catch (Exception e) {
			e.printStackTrace();
			returnData.setingError(Constant.DEFAULT_ERROR_MSG);
		}
		return returnData;
	}
}
