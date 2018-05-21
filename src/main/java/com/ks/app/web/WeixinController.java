package com.ks.app.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringEscapeUtils;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ks.app.dao.APosterDao;
import com.ks.app.dao.AUserDao;
import com.ks.app.entity.AMUser;
import com.ks.app.entity.AUser;
import com.ks.app.entity.TextMessage;
import com.ks.app.entity.TokenOfSql;
import com.ks.app.entity.WeixinUserInfo;
import com.ks.app.service.AMUserService;
import com.ks.app.service.AUserService;
import com.ks.app.utils.ActionResponse;
import com.ks.app.utils.CheckUtil;
import com.ks.app.utils.MessageUtil;
import com.ks.app.utils.WeixinERCodeUtil;
import com.ks.app.utils.WeixinUtil;
import com.ks.utils.Config;
import com.ks.utils.EnumConstant.WeixinEventType;
import com.ks.utils.EnumConstant.WeixinMsgType;
import com.thinkgem.jeesite.common.utils.FileUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;




@Controller
@RequestMapping("/weixin")
public class WeixinController extends BaseController {
	@Autowired
	AUserService aUserService;
	@Autowired
	AUserDao aUserDao;
	@Autowired
	private APosterDao aPosterDao;
	@Autowired
	private AMUserService amUserService;
	
	@RequestMapping(value = "/weixin.action", method = RequestMethod.GET)
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//微信加密签名
			String signature = req.getParameter("signature");
			//时间戳
			String timestamp = req.getParameter("timestamp");
			//随机数
			String nonce = req.getParameter("nonce");
			//随机字符串
			String echostr = req.getParameter("echostr");
			
			PrintWriter out = resp.getWriter();
			//通过校验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
			if(CheckUtil.checkSignature(signature,timestamp,nonce)){
				out.print(echostr);
				out.close();
			}
			out.close();
			out = null;
	}
	
	
	@RequestMapping(value="/weixin.action",method=RequestMethod.POST)
	protected void doPost(HttpServletRequest req, HttpServletResponse resp,HttpSession session)
			throws ServletException, IOException {
	 req.setCharacterEncoding("UTF-8");
	 resp.setCharacterEncoding("UTF-8");
	 PrintWriter out = resp.getWriter();
		try {
			Map<String,String> map = MessageUtil.xmlToMap(req);
			String toUserName = map.get("ToUserName");
			String fromUserName = map.get("FromUserName");
			String msgType = map.get("MsgType");
			System.out.println("*******************************MsgTyoe:"+msgType);
//			String content = map.get("Content");
			String accessToken = aUserService.getToken();
			if(accessToken == null){
				return;
			}
			
			if(WeixinMsgType.TEXT.getKey().equals(msgType)||WeixinMsgType.IMAGE.getKey().equals(msgType)||WeixinMsgType.VIDEO.getKey().equals(msgType)||
					WeixinMsgType.VOICE.getKey().equals(msgType)||WeixinMsgType.SHORT_VIDEO.getKey().equals(msgType)||WeixinMsgType.LOCATION.getKey().equals(msgType)||
					WeixinMsgType.LINK.getKey().equals(msgType)){
				WeixinUserInfo wui = WeixinUtil.getWeixinUserInfo(accessToken, fromUserName);
				if(wui == null || StringUtils.isBlank(wui.getOpenId())){
					System.out.println("获取用户信息失败");
				}
				String name = wui == null ? "":wui.getNickname();
				String message = null;
				TextMessage textmessage = new TextMessage();
				textmessage.setFromUserName(toUserName);
				textmessage.setToUserName(fromUserName);
				textmessage.setMsgType(WeixinMsgType.TEXT.getKey());
				textmessage.setCreateTime(new Date().getTime());
				textmessage.setContent(String.format(WeixinUtil.DEFAULT_MESSAGE, name));
				message = MessageUtil.textMessageToXml(textmessage);
				out.print(message);
				out.close();
			}else if(WeixinMsgType.EVENT.getKey().equals(msgType)){
				String event = map.get("Event");
				System.out.println("***********************************************event:"+event);
				if(WeixinEventType.SUBSCRIBE.getKey().equals(event)){
					String menu = JSONObject.fromObject(WeixinUtil.initMenu(req)).toString();
					int result = WeixinUtil.createMenu(accessToken, menu);
					if(result==0){
						System.out.println("成功");
					}else{
						System.out.println("错误码："+result);
					}
					String eventKey = map.get("EventKey");
					String rewardUser = "";
					if(StringUtils.isNotBlank(eventKey)){
						rewardUser = eventKey.substring(WeixinERCodeUtil.QR_SCENE_PRE.length(),eventKey.length());
						int len = rewardUser.length();
						String str = rewardUser.substring(len-3);
						if("***".equals(str)){
							String userId =  aUserService.findIdByopenId(fromUserName, "");
							AMUser user = amUserService.getMuserByUserId(userId);
							if(user!=null){
								boolean flag = amUserService.upDateParentIdFlag(user.getId(),rewardUser.substring(0,rewardUser.length()-3),true);
								if(flag){
								user.setParentId(rewardUser.substring(0,rewardUser.length()-3));
								amUserService.updateParentId(user);
							}
						}
						}
					}
					AUser user = aUserService.findUserByopenId(fromUserName,rewardUser);
					String name = "";
					if(user != null){
						aUserDao.changDelFlag(user.getId());
						name = user.getName();
					}
					String message = null;
					TextMessage textmessage = new TextMessage();
					textmessage.setFromUserName(toUserName);
					textmessage.setToUserName(fromUserName);
					textmessage.setMsgType(WeixinMsgType.TEXT.getKey());
					textmessage.setCreateTime(new Date().getTime());
					textmessage.setContent(String.format(WeixinUtil.DEFAULT_MESSAGE, name));
					message = MessageUtil.textMessageToXml(textmessage);
					out.print(message);
					out.close();
				}else if(WeixinEventType.UNSUBSCRIBE.getKey().equals(event)){
					String id = aUserService.getIdByOpenId(fromUserName);
					if(StringUtils.isNotBlank(id)){
						aUserService.unsubscribe(id);
					}
				}else if(WeixinEventType.CLICK.getKey().equals(event)){
					String EventKey = map.get("EventKey");
					System.out.println("+++推荐海报+++"+EventKey);
					if(WeixinUtil.SHARE_KEY.equals(EventKey)){
						System.out.println("+++推荐海报+++");
						AUser user = aUserService.findUserByopenId(fromUserName,"");
						if(user != null){
							System.out.println("+++推荐海报+++启用状态"+user.getStartFlag());
							if(!("0".equals(user.getStartFlag()))){
								String message = null;
								TextMessage textmessage = new TextMessage();
								textmessage.setFromUserName(toUserName);
								textmessage.setToUserName(fromUserName);
								textmessage.setMsgType(WeixinMsgType.TEXT.getKey());
								textmessage.setCreateTime(new Date().getTime());
								textmessage.setContent(WeixinUtil.START_FLAG_OFF);
								message = MessageUtil.textMessageToXml(textmessage);
								out.print(message);
								out.close();
							}else{
								String rewardUser = user.getId();
								System.out.println("+++推荐海报+++用户id"+rewardUser);
								try {
									//创建二维码,并下载到本地
									String path = aPosterDao.getPath();
									if(StringUtils.isNotBlank(path)){
										path = FileUtils.getFileSavePath(path, Config.getUploadPoster());
										String[] resultFile = WeixinERCodeUtil.getShareERCodePath(path,accessToken,rewardUser,user.getName(),req);
										if(resultFile != null){
											System.out.println("+++推荐海报+++二维码"+resultFile[0]+","+resultFile[1]);
											String message =MessageUtil.initImageMessage(toUserName, fromUserName,resultFile[1] , accessToken, WeixinMsgType.IMAGE.getKey());
											out.print(message);
											out.close();
										}
									}
									
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					}
				}else if(WeixinEventType.SCAN.getKey().equals(event)){
					String eventKey = map.get("EventKey");
					String rewardUser = "";
					if(StringUtils.isNotBlank(eventKey)){
						rewardUser = eventKey;
						int len = rewardUser.length();
						String str = rewardUser.substring(len-3);
						if("***".equals(str)){
							String userId =  aUserService.getIdByOpenId(fromUserName);
							AMUser user = amUserService.getMuserByUserId(userId);
							if(user!=null){
								boolean flag = amUserService.upDateParentIdFlag(user.getId(),rewardUser.substring(0,rewardUser.length()-3),true);
								if(flag){
								user.setParentId(rewardUser.substring(0,rewardUser.length()-3));
								amUserService.updateParentId(user);
								String message = null;
								TextMessage textmessage = new TextMessage();
								textmessage.setFromUserName(toUserName);
								textmessage.setToUserName(fromUserName);
								textmessage.setMsgType(WeixinMsgType.TEXT.getKey());
								textmessage.setCreateTime(new Date().getTime());
								textmessage.setContent(WeixinUtil.M_CHANG_PARENT_SUCCEED);
								message = MessageUtil.textMessageToXml(textmessage);
								out.print(message);
								out.close();
								return;
							}else{
								String message = null;
								TextMessage textmessage = new TextMessage();
								textmessage.setFromUserName(toUserName);
								textmessage.setToUserName(fromUserName);
								textmessage.setMsgType(WeixinMsgType.TEXT.getKey());
								textmessage.setCreateTime(new Date().getTime());
								textmessage.setContent(WeixinUtil.M_CHANG_PARENT_DEFEATED);
								message = MessageUtil.textMessageToXml(textmessage);
								out.print(message);
								out.close();
								return;
							}
						}
						}
					}
					//通过当前用户的openId查到id
					String id = aUserService.getIdByOpenId(fromUserName);
					if(StringUtils.isNotBlank(id)){
//						boolean flag = aUserService.getBalanceOneMonthById(id,rewardUser);
						boolean flag = aUserService.insertParentUser(id, rewardUser);
						String message = null;
						TextMessage textmessage = new TextMessage();
						textmessage.setFromUserName(toUserName);
						textmessage.setToUserName(fromUserName);
						textmessage.setMsgType(WeixinMsgType.TEXT.getKey());
						textmessage.setCreateTime(new Date().getTime());
						if(flag){
							aUserService.updateUserRecom(id,rewardUser);
//							textmessage.setContent(WeixinUtil.CHANG_PARENT_SUCCEED);
							textmessage.setContent("绑定推荐关系成功！");
							message = MessageUtil.textMessageToXml(textmessage);
							out.print(message);
							out.close();
						}else{
//							textmessage.setContent(WeixinUtil.CHANG_PARENT_DEFEATED);
							textmessage.setContent("已有推荐关系，咱不能变更！");
							message = MessageUtil.textMessageToXml(textmessage);
							out.print(message);
							out.close();
						}
					}
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}finally{
			out.close();
		}
	}
	
	//获取微信信息
	@RequestMapping("getJsConfig")
	public @ResponseBody ActionResponse<Object> getJsConfig(String url,HttpServletRequest request) {
		ActionResponse<Object> data = new ActionResponse<Object>();
		String message = "获取微信配置信息失败！";
		try {
			if(StringUtils.isBlank(url)){
				data.setingError("参数错误");
				return data;
			}
			String encodeUrl = StringEscapeUtils.unescapeHtml3(url);
			
			TokenOfSql tokenSql =  aUserService.getJSAPITicketSql();
			if (tokenSql == null || StringUtils.isBlank(tokenSql.getJsapiTicket())) {
				data.setingError("微信网页ticket失败");
				return data;
			}
    		String ticket = tokenSql.getJsapiTicket();  
    		Map<String, String> ret = new HashMap<String, String>();  
    		String nonce_str = WeixinUtil.create_nonce_str();  
    		String timestamp = WeixinUtil.create_timestamp();  
    		String string1;  
    		String signature = "";  
    		
    		//注意这里参数名必须全部小写，且必须有序  
    		string1 = "jsapi_ticket=" + ticket +  
    				"&noncestr=" + nonce_str +  
    				"&timestamp=" + timestamp +  
    				"&url=" + encodeUrl;  
    		System.out.println("string1="+string1);  
    		
    		try  
    		{  
    			MessageDigest crypt = MessageDigest.getInstance("SHA-1");  
    			crypt.reset();  
    			crypt.update(string1.getBytes("UTF-8"));  
    			signature = WeixinUtil.byteToHex(crypt.digest());  
    		}  
    		catch (NoSuchAlgorithmException e)  
    		{  
    			e.printStackTrace();  
    		}  
    		catch (UnsupportedEncodingException e)  
    		{  
    			e.printStackTrace();  
    		}  
    		
    		ret.put("url", encodeUrl);  
    		ret.put("jsapi_ticket", ticket);  
    		ret.put("nonceStr", nonce_str);  
    		ret.put("timestamp", timestamp);  
    		ret.put("signature", signature);  
    		ret.put("appId", WeixinUtil.APPID);  
    		
    		System.out.println("1.ticket(原始)="+ticket);  
    		System.out.println("2.url="+ret.get("url"));  
    		System.out.println("3.jsapi_ticket（处理后）="+ret.get("jsapi_ticket"));  
    		System.out.println("4.nonceStr="+ret.get("nonceStr"));  
    		System.out.println("5.signature="+ret.get("signature"));  
    		System.out.println("6.timestamp="+ret.get("timestamp")); 
    		
    		data.setData(ret);
    		data.setingSuccess("获取签名成功");
    		return  data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		data.setingError(message);
		return data;
	}
}
