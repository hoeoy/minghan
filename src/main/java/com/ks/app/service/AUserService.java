/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.ks.app.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.common.collect.Lists;
import com.ks.app.dao.AUserDao;
import com.ks.app.entity.AUser;
import com.ks.app.entity.AccessToken;
import com.ks.app.entity.TokenOfSql;
import com.ks.app.entity.WeixinUserInfo;
import com.ks.app.utils.ActionResponse;
import com.ks.app.utils.URLConnectionHelper;
import com.ks.app.utils.WeixinUtil;
import com.ks.utils.Config;
import com.ks.utils.Constant;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.FileUtils;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.SystemPath;
import com.thinkgem.jeesite.modules.sys.entity.Balance;
import com.thinkgem.jeesite.modules.sys.service.SystemService;

/**
 * 系统管理，安全相关实体的管理类,包括用户、角色、菜单.
 * @author ThinkGem
 * @version 2013-12-05
 */
@Service
@Transactional
public class AUserService extends CrudService<AUserDao, AUser> {
	@Autowired
	AUserDao aUserDao;
	
	/**
	 * 根据登录名获取用户
	 * @param loginName
	 * @param flag 2登录，1添加，0修改
	 * @return
	 */
//	public AUser getUserByMobile(String mobile,String password,String flag,String prePath) {
//		Map<String,Object> params = new HashMap<String, Object>();
//		params.put("mobile", mobile);
//		
//		AUser user = dao.getUserByMobile(params);
//		if(user != null){
//			if("2".equals(flag)){
//				boolean okFlag = SystemService.validatePassword(password, user.getPassword());
//				if(okFlag){
//					changeUserInfo(user, prePath);
//					return user;
//				}
//				return null;
//			}
//			if("0".equals(flag) || ("1".equals(flag) && Constant.DELETE_FLAG_0.equals(user.getDelFlag()))){
//				changeUserInfo(user, prePath);
//				return user;
//			}
//		}
//		
//		return null;
//	}
	public AUser getUserByWeixinOpenId(String openId,String prePath) {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("openId", openId);
		//params.put("deleteFlag", Constant.DELETE_FLAG_0);
		AUser user = dao.getUserByMobile(params);
		changeUserInfo(user, prePath);
		return user;
	}
	@Transactional(readOnly = false)
	public void deleteUserByOpenId(String openId){
		dao.deleteUserByOpenId(openId);
	}
	
	@Transactional(readOnly = false)
	public int updateUserInfo(AUser user,HttpServletRequest request) {
		String imgNum = request.getParameter("imgNum");
		if(StringUtils.isNotBlank(imgNum) && Integer.valueOf(imgNum)>0){
			MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest)request;
			// 取得用户头像图片
			MultipartFile uiFile = mRequest.getFile("imgFile");
			if(uiFile != null && !uiFile.isEmpty()){
				String[] photo = AUserService.uploadUserPhoto(uiFile,user.getPhoto(),user.getMinPhoto());
				if(photo != null){
					user.setPhoto(photo[0]);
					user.setMinPhoto(photo[1]);
				}
			}
		}
		user.preUpdate();
		return dao.updateUserInfo(user);
	}
	@Transactional(readOnly = false)
	public int updatePassword(String mobile,String password) {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("mobile", mobile);
		params.put("password", SystemService.entryptPassword(password));
		return dao.updatePassword(params);
	}
	@Transactional(readOnly = false)
	public int changePassword(AUser user) {
		user.preUpdate();
		return dao.update(user);
	}
	@Transactional(readOnly = false)
	public void insertUser(AUser user) {
		user.preInsert();
		dao.insert(user);
	}
	
	public AUser getUserById(String userId){
		return dao.getUserById(userId);
	}
	
	@Transactional(readOnly = false)
	public ActionResponse<Object> updateUserPhoto(String userId,MultipartHttpServletRequest request) {
		ActionResponse<Object> serviceResponse= new ActionResponse<Object>();
		try {
			AUser user = getUserById(userId);
			if(user != null){
				// 取得用户头像图片
				MultipartFile uiFile = request.getFile("imgFile");
				if(uiFile != null && !uiFile.isEmpty()){
					String[] photo = uploadUserPhoto(uiFile,user.getPhoto(),user.getMinPhoto());
					if(photo != null){
						user.setPhoto(photo[0]);
						user.setMinPhoto(photo[1]);
						user.preUpdate();
						if(dao.update(user) == 1){
							changeUserInfo(user, SystemPath.getRequestPreUrl(request));
							serviceResponse.setData(user);
							serviceResponse.setCode(Constant.SUCCESS_CODE);
						} else {
							serviceResponse.setingError("更新头像失败");
						}
					}
				}else{
					serviceResponse.setingError("头像格式不对");
				}
	
			}
		} catch (Exception e) {
			e.printStackTrace();
			serviceResponse.setingError("更新头像失败");
		}
		return serviceResponse;
	}
	
	public static String[] uploadUserPhoto(MultipartFile file,String oldPhoto,String oldMinPhoto){
		//上传七牛
		//String key = StringUtils.isNullOrEmpty(oldPhoto) ? QiniuUtil.getSavePathName("1", null):oldPhoto;
		//String photo = QiniuUtil.uploadFileBytes(file.getBytes(), key);
		
		//上传到自己服务器
		String id = IdGen.uuid();
		String extention = FileUtils.getFileExtension(file.getOriginalFilename());
		String fileName = id+"."+extention;
		String uploadPath = FileUtils.getUploadPath(Config.getUploadUserpath());
		String thumb = id+"_sec."+extention;
		String resultPath[] = FileUtils.fileUpload(file, uploadPath, fileName, thumb, true, false,Config.getUploadBasePrepath());
		
		if(resultPath != null){
			//删除大图标
//			String basePath = Config.getUploadBasepath();
//			FileUtils.deleteFile(basePath+resultPath[0]);
			
			if(StringUtils.isNotBlank(oldPhoto)){
				//删除旧头像
				FileUtils.deleteFile(Config.getUploadBasepath()+oldPhoto);
				FileUtils.deleteFile(Config.getUploadBasepath()+oldMinPhoto);
			}
			return resultPath;
		}
		return null;
	}
	/**
	 * 添加图片完全路径
	 * 
	 * @param articleInfo
	 * @return
	 */
	public static void changeUserInfo(AUser user,String prePath) {
		if(user == null)
			user = new AUser();
		user.setPhoto(SystemPath.getRealPath(user.getPhoto(),prePath));
		user.setMinPhoto(SystemPath.getRealPath(user.getMinPhoto(),prePath));
	}
	
	/***************************************************************************************/
	public String findIdByopenId(String openId,String rewardUser){
		String thisId = aUserDao.getIdByOpenId(openId);
		if(thisId==null){
			System.out.println("这是一个新用户，开始保存新的数据！");
			String newId = IdGen.uuid();
			TokenOfSql token = aUserDao.getTokenById(WeixinUtil.TOKEN_ID);
			WeixinUserInfo wui = WeixinUtil.getWeixinUserInfo(token.getAccessToken(), openId);
			if(wui == null || StringUtils.isBlank(wui.getOpenId())){
				System.out.println("获取微信用户信息失败");
				return "";
			}
			System.out.println("newId:**********"+newId);
			String flag = "0";
			String name = wui.getNickname();
			String photoUrlOfWeixin = wui.getHeadImgUrl();
			String photo = null;
			String minPhoto = null;
			if(StringUtils.isNotBlank(photoUrlOfWeixin)){
				String id = IdGen.uuid();
				String fileName = id+".jpg";
				String thumbFileName = id+"_sec.jpg";
				String photoUrl = FileUtils.getUploadPath(Config.getUploadUserpath());
//				String savePath = Config.getUploadBasepath()+photo;
				String resultPaths[] = FileUtils.downloadNet(photoUrlOfWeixin, fileName, photoUrl,thumbFileName,true);
				photo = resultPaths[0];
				minPhoto = (resultPaths[1]);
			}
			String province = wui.getProvince();
			String city = wui.getCity();
			Date date = new Date();
			aUserDao.addUser(newId,openId,flag,flag,name,photo,minPhoto,province,city,rewardUser,date);
			aUserDao.subscribe(openId,rewardUser);
			return newId;
		}else{
			return thisId ;
		}
	}
	
	public AUser findUserByopenId(String openId,String rewardUser){
		AUser user = aUserDao.getUser(openId);
		if(user==null){
			System.out.println("这是一个新用户，开始保存新的数据！");
			String newId = IdGen.uuid();
			TokenOfSql token = aUserDao.getTokenById(WeixinUtil.TOKEN_ID);
			WeixinUserInfo wui = WeixinUtil.getWeixinUserInfo(token.getAccessToken(), openId);
			if(wui == null || StringUtils.isBlank(wui.getOpenId())){
				System.out.println("获取微信用户信息失败");
				return null;
			}
			System.out.println("newId:**********"+newId);
			String flag = "0";
			String name = wui.getNickname();
			String photoUrlOfWeixin = wui.getHeadImgUrl();
			String photo = null;
			String minPhoto = null;
			if(StringUtils.isNotBlank(photoUrlOfWeixin)){
				String id = IdGen.uuid();
				String fileName = id+".jpg";
				String thumbFileName = id+"_sec.jpg";
				String photoUrl = FileUtils.getUploadPath(Config.getUploadUserpath());
//				String savePath = Config.getUploadBasepath()+photo;
				String resultPaths[] = FileUtils.downloadNet(photoUrlOfWeixin, fileName, photoUrl,thumbFileName,true);
				photo = resultPaths[0];
				minPhoto = (resultPaths[1]);
			}
			String province = wui.getProvince();
			String city = wui.getCity();
			Date date = new Date();
			aUserDao.addUser(newId,openId,flag,flag,name,photo,minPhoto,province,city,rewardUser,date);
			user= aUserDao.getUserById(newId);
			return user;
		}else{
			if(StringUtils.isNotBlank(rewardUser) && StringUtils.isNotBlank(user.getParentId()) && rewardUser.equals(user.getParentId())){
				aUserDao.subscribe(openId,rewardUser);
			}
			return user ;
		}
	}
	
	public String getToken() throws ParseException, IOException {
		TokenOfSql tos = getTokenSql();
		if(tos==null){
			return null;
		}
		return tos.getAccessToken();
	}
	public TokenOfSql getTokenSql() throws ParseException, IOException {
		TokenOfSql tos = aUserDao.getTokenById(WeixinUtil.TOKEN_ID);
		if(tos==null){
			System.out.println("数据库没有token");
			AccessToken accessToken = WeixinUtil.getAccessToken();
			TokenOfSql tos1 = new TokenOfSql();
			tos1.setAccessToken(accessToken.getToken());
			tos1.setExpriresIn(accessToken.getExpiresIn());
			tos1.setCreateTime(System.currentTimeMillis());
			tos1.setId(WeixinUtil.TOKEN_ID);
			aUserDao.insertToken(tos1);
			return dao.getTokenById(WeixinUtil.TOKEN_ID);
		}
		String token = tos.getAccessToken();
		int expiresIn = tos.getExpriresIn();
		long time = tos.getCreateTime();
		System.out.println((System.currentTimeMillis()-time)/1000);
		System.out.println(expiresIn);
		if ((System.currentTimeMillis()-time)/1000 <7200) {// 有效  
			System.out.println("无须创建");  
			return tos;
		}
		AccessToken accessToken = WeixinUtil.getAccessToken();
		if(accessToken == null){
			System.out.println("获取token失败");
			return null;
		}
		token = accessToken.getToken();
		expiresIn = accessToken.getExpiresIn();
		time = System.currentTimeMillis();
		aUserDao.updateTokenById(token,time,WeixinUtil.TOKEN_ID);
		System.out.println("**************"+token);
		return dao.getTokenById(WeixinUtil.TOKEN_ID);
	}
	
	public AUser getUser(String openId){
		return aUserDao.getUser(openId);
	}
	public void updateAUser(String openid, String name, String mobile,
			String birthday, String card, String mail, String province,String city,String zone,
			String addr, String bankType, String bankCard,String updateFlagName,String updateFlagCard) {
		aUserDao.updateAUser(name,mobile,birthday,card,mail,province,city,zone,addr,bankType,bankCard,updateFlagName,updateFlagCard,openid);
		System.out.println("执行完毕");
		
	}
	public String getIdByOpenId(String openId){
		return aUserDao.getIdByOpenId(openId);
	}
	public List<AUser> getSubordinateById(String id) {
		List<AUser> list =  aUserDao.getSubordinateById(id);
		if(list==null){
			list = Lists.newArrayList();
		}
		return list;
	}
	
	public void updateUserRecom(String id,String rewardUser) {
		AUser user = new AUser();
		user.setId(id);
		user.setParentId(rewardUser);
		aUserDao.updateParentId(user);
	}
	public void unsubscribe(String id) {
		aUserDao.unsubscribe(id);
	}
	public int getSubordinateNub(String id){
		return aUserDao.getSubordinateNub(id);
	}
	public int countSub(String id) {
		return aUserDao.countSub(id);
	}
	public BigDecimal getBalanceById(String id) {
		BigDecimal bd = new BigDecimal(0);
		List<Balance> list = aUserDao.getBalanceById(id);
		if(list==null){
			list = Lists.newArrayList();
		}
			for(int i = 0;i<list.size();i++){
				if(list.get(i).getMoney() != null ){
					bd=bd.add(list.get(i).getMoney());
				}
			}
		return bd;
	}

	//add by andyzhao
	public BigDecimal getBalanceSumContainChildrenById(String id) {
		BigDecimal bd = aUserDao.getBalanceSumContainChildrenById(id);
		if (bd == null) {
			bd = new BigDecimal("0");
		}
		return bd;
	}


	public BigDecimal getAllPerformanceById(String id) {
		BigDecimal bd = new BigDecimal(0);
		bd=recursionPerformance(bd,id);
		return bd;
	}
	
	public BigDecimal recursionPerformance(BigDecimal bd,String id){
		List<AUser> list = aUserDao.getSubordinateById(id);
		if(list==null){
			list = Lists.newArrayList();
		}
		for(int i = 0;i<list.size();i++){
			List<Balance> blist = aUserDao.getBalanceById(list.get(i).getId());
			if(blist.size()!=0){
				for(int j=0;j<blist.size();j++){
					bd=bd.add(blist.get(j).getMoney());
				}
			}
			bd=recursionPerformance(bd,list.get(i).getId());
		}
		return bd;
	}
	public boolean getBalanceOneMonthById(String id,String parentId) {
		Date now = new Date();
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		Date oneMonth = c.getTime(); 
		List <Balance> list = aUserDao.getBalanceOneMonthById(id,now,oneMonth);
		boolean flag = true;
		flag = subFlag(id, parentId, flag);
		if(list.size()==0&&flag){
			return true;
		}else{
			return false;
	}
	}
	public boolean subFlag(String id,String parentId,boolean flag){
		if(id.equals(parentId)){
			return false;
		}
		List<AUser> list = aUserDao.getSubordinateById(id);
		if(list.size()!=0){
			for(int i =0;i<list.size();i++){
				if(parentId.equals(list.get(i).getId())){
					flag=false;
					break;
				}else{
					subFlag(list.get(i).getId(), parentId, flag);
				}
			}
		}
		return flag;
	}
	/**
	 * 判断用户是否完善资料
	 * @param id
	 * @return
	 */
	public boolean userComplete(String id){
		boolean flag = false;
		AUser user = aUserDao.getUserById(id);
		String bankType = user.getBankType();
		String bankCard = user.getBankCard();
		String mobile = user.getMobile();
		if(bankType!=null&&!("".equals(bankType))&&bankCard!=null&&!("".equals(bankCard))&&mobile!=null&&!("".equals(mobile))){
			flag = true;
		}
		return flag;
	}
	public List<String> getYWYopenId() {
		List<String> list = aUserDao.getYWYopenId();
		return list;
	}
	
	public TokenOfSql getJSAPITicketSql() throws ParseException, IOException {
		TokenOfSql tos = getTokenSql();
		if (tos==null || StringUtils.isBlank(tos.getAccessToken())) {
			return null;
		}
        String ticket = tos.getJsapiTicket();
		String token = tos.getAccessToken();
//		int expiresIn = tos.getTicketExpriresIn();
		long time = tos.getTicketCreateTime();
		if (StringUtils.isNotBlank(ticket)) {
			if ((System.currentTimeMillis()-time)/1000 < 7200) {// 有效  
	            System.out.println("无须创建ticket");  
			}else{
				return ticketCreate(token);
			}
		}else {
			return ticketCreate(token);
		}
        return tos;
	}
	
	private TokenOfSql ticketCreate(String accessToken){
		String URL = WeixinUtil.TICKET_URL.replace("ACCESS_TOKEN", accessToken);
        String jsonStr = URLConnectionHelper.sendGet(URL);
        JSONObject jsonObj = JSONObject.fromObject(jsonStr);
        if(jsonObj.containsKey(WeixinUtil.ERROR_CODE_COLUMN)){
        	int code = jsonObj.getInt(WeixinUtil.ERROR_CODE_COLUMN);
        	if(code == 0){
        		String ticket = jsonObj.getString("ticket");  
        		
//        		int expireTime = jsonObj.getInt("expires_in");
        		
        		long time = System.currentTimeMillis();
        		
        		dao.updateTicketById(ticket, time, WeixinUtil.TOKEN_ID);
        		
        		return dao.getTokenById(WeixinUtil.TOKEN_ID);
        	}
        }
        return null;
	}
	public String getNameById(String userId) {
		String name = dao.getNameById(userId);
		return name;
	}
	
	/**
	 * 判断是否有上级，有则无操作，无则绑定推荐关系，此处只做判断不做操作
	 * @param id 扫码人id
	 * @param rewardUser 被扫码人id
	 * @return false无操作，true绑定推荐关系
	 */
	public boolean insertParentUser(String id, String rewardUser) {
		AUser user = dao.get(id);
		if(user == null || StringUtils.isNotBlank(user.getParentId())){
			return false;
		}
		return true;
	}
	
}
