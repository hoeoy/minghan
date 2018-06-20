package com.ks.app.utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Formatter;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.ks.app.entity.AccessToken;
import com.ks.app.entity.Button;
import com.ks.app.entity.ClickButton;
import com.ks.app.entity.Menu;
import com.ks.app.entity.ViewButton;
import com.ks.app.entity.WeixinUserInfo;
import com.ks.app.service.AUserService;
import com.ks.utils.Config;
import com.ks.utils.EnumConstant.WeixinMsgType;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.SystemPath;

@SuppressWarnings("deprecation")
public class WeixinUtil {
	public static final int BeautyDiaryCount = 10;
	public static final int OrderCount = 10;
	
	public static final int TOKEN_ID = 2;//token对应的记录id
	
	//zj测试号
	public static final String APPID="wx036f7ac5836cb8a7";
	public static final String APPSECRET="c31fcc8f52e2633b165f57a258e6068b";
	//public static final String APPID="wxb0f43854b0753894";
	//public static final String APPSECRET="ebcac0f7e94a07f5dc75e7d311f97d28";
	//hzy测试号
	//public static final String APPID="wxb72cdd5bb1d0f1d9";
	//public static final String APPSECRET="0f84930437bfd1c92c4a465865c05aa5";
	//ylj测试号
//	public static final String APPID="wxf48db2ec7eadca08";
//	public static final String APPSECRET="740186d794a9370f6852de21297fd290";
	/*public static final String APPID="wxf48db2ec7eadca08";
	public static final String APPSECRET="740186d794a9370f6852de21297fd290";*/
	//正式
	/*public static final String APPID="wxdb58186e27ce9e8d";
	public static final String APPSECRET="18207bfcd2c7d32ad8f71985b97c78cb";*/
	
	public static String token  = "cskuaishou";
	public static final String ACCESS_TOKEN_URL ="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";	
	public static final String CREATE_MENU_URL="https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	public static final String GET_INFO_BY_OPENID="https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	public static final String UPLOAD_URL="https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
	public static final String QR_CODE_URL="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKENPOST";
	public static final String GRANT_URL="https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s/vip/inc.do?type=%s&response_type=code&scope=snsapi_base&state=1&connect_redirect=1#wechat_redirect";//授权地址
	public static final String GRANT_URL2="https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=1&connect_redirect=1#wechat_redirect";//授权地址
	public static final String GRANT_TOKEN_URL="https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	public static final String TICKET_URL="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
	
	public static final String SHARE_KEY="21";//推广海报key
	public static final String DEFAULT_MESSAGE="%s您好！默认回复！";//默认回复
	public static final String TICKET_VALID="2592000";//该二维码有效时间，以秒为单位。 最大不超过2592000（即30天），此字段如果不填，则默认有效期为30秒
	public static final String ERROR_CODE_COLUMN="errcode";//获取用户信息，错误code字段
	public static final String CHANG_PARENT_SUCCEED="更换推荐人成功!";
	public static final String CHANG_PARENT_DEFEATED="绑定失败!您一个月内有消费记录或者推荐人已是您的团队成员!";
	public static final String START_FLAG_OFF="您已被管理员禁用所有功能，如需解除请联系门店管理！";
	public static final String M_CHANG_PARENT_SUCCEED = "商品推荐人绑定成功！";
	public static final String M_CHANG_PARENT_DEFEATED="商品推荐人绑定失败！";
	public static final int DISCOUNTS_NUM = 10;//优惠数量
	public static final BigDecimal ZHE_KOU = new BigDecimal(0.1);
	public static final String ZI_GE_ID="1";
	
	//商户ID 
    public static String MCH_ID = "1496389632";
    //微信商户平台-账户设置-安全设置-api安全,配置32位key
    public static String KEY  = "xHeTh0rvvzUK0zFlqglB9VNA6yI2I3uY";
    
  //交易类型
    public static String TRADE_TYPE_JS = "JSAPI";
    //微信支付回调url
    public static String NOTIFY_URL = "%s/app/pay/wxJSNotify.do";
    //签名加密方式
    public final static String SIGN_TYPE = "MD5";
	
	private HttpClient webClient;
	private static AUserService userService = SpringContextHolder.getBean(AUserService.class);
	
	/**
	 * get请求
	 * @param url
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static JSONObject doGetStr(String url) throws ParseException, IOException{
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		JSONObject jsonObject = null;
		HttpResponse httpResponse = client.execute(httpGet);
		HttpEntity entity = httpResponse.getEntity();
		if(entity != null){
			String result = EntityUtils.toString(entity,"UTF-8");
			jsonObject = JSONObject.fromObject(result);
		}
		return jsonObject;
	}
	
	/**
	 * POST请求
	 * @param url
	 * @param outStr
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static JSONObject doPostStr( String url,String outStr) throws ParseException, IOException{
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(url);
		JSONObject jsonObject = null;
		httpost.setEntity(new StringEntity(outStr,"UTF-8"));
		HttpResponse response = client.execute(httpost);
		String result = EntityUtils.toString(response.getEntity(),"UTF-8");
		jsonObject = net.sf.json.JSONObject.fromObject(result);
		return jsonObject;
	}
	/**
	 * 获取accessToken
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static AccessToken getAccessToken() throws ParseException, IOException{
		AccessToken token = new AccessToken();
		String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
		JSONObject jsonObject = doGetStr(url);
		if(jsonObject!=null){
			if(jsonObject.containsKey(ERROR_CODE_COLUMN)){
				System.out.println(getErrorMsg(jsonObject.getInt(ERROR_CODE_COLUMN)));
				return token;
			}
			token.setToken(jsonObject.getString("access_token"));
			token.setExpiresIn(jsonObject.getInt("expires_in"));
		}
		return token;
	}
	/**
	 * 组装菜单
	 * @return
	 */
	public static Menu initMenu(HttpServletRequest request){
		Menu menu = new Menu();
		ViewButton button11 = new ViewButton();
		button11.setName("公司主页");
		button11.setType("view");
		button11.setUrl(SystemPath.getRequestProjectUrl(request)+"/vip/redirect?type=1");
		
		ClickButton button21 =new ClickButton();
		button21.setName("推广海报");
		button21.setType("click");
		button21.setKey(SHARE_KEY);
		
		ViewButton button31 = new ViewButton();
		button31.setName("会员中心");
		button31.setType("view");
		button31.setUrl(SystemPath.getRequestProjectUrl(request)+"/vip/redirect?type=2");
		
		menu.setButton(new Button[]{button11,button21,button31});
		return menu;
	}
	/**
	 * 创建菜单
	 * @param token
	 * @param menu
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static int createMenu(String token,String menu) throws ParseException, IOException{
		int result = 0;
		String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = doPostStr(url, menu);
		if(jsonObject != null){
			if(jsonObject.containsKey(ERROR_CODE_COLUMN)){
				System.out.println(getErrorMsg(jsonObject.getInt(ERROR_CODE_COLUMN)));
				return jsonObject.getInt(ERROR_CODE_COLUMN);
			}
			result = jsonObject.getInt("errcode");
		}
		return result;
	}
	/**
	   * 发送https请求
	   * 
	   * @param requestUrl 请求地址
	   * @param requestMethod 请求方式（GET、POST）
	   * @param outputStr 提交的数据
	   * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	   */
	  public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
	    JSONObject jsonObject = null;
	    try {
	      // 创建SSLContext对象，并使用我们指定的信任管理器初始化
	      TrustManager[] tm = { new MyX509TrustManager() };
	      SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
	      sslContext.init(null, tm, new java.security.SecureRandom());
	      // 从上述SSLContext对象中得到SSLSocketFactory对象
	      SSLSocketFactory ssf = sslContext.getSocketFactory();
	      URL url = new URL(requestUrl);
	      HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
	      conn.setSSLSocketFactory(ssf);
	      conn.setDoOutput(true);
	      conn.setDoInput(true);
	      conn.setUseCaches(false);
	      // 设置请求方式（GET/POST）
	      conn.setRequestMethod(requestMethod);
	      // 当outputStr不为null时向输出流写数据
	      if (null != outputStr) {
	        OutputStream outputStream = conn.getOutputStream();
	        // 注意编码格式
	        outputStream.write(outputStr.getBytes("UTF-8"));
	        outputStream.close();
	      }
	      // 从输入流读取返回内容
	      InputStream inputStream = conn.getInputStream();
	      InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
	      BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
	      String str = null;
	      StringBuffer buffer = new StringBuffer();
	      while ((str = bufferedReader.readLine()) != null) {
	        buffer.append(str);
	      }
	      // 释放资源
	      bufferedReader.close();
	      inputStreamReader.close();
	      inputStream.close();
	      inputStream = null;
	      conn.disconnect();
	      jsonObject = JSONObject.fromObject(buffer.toString());
	    } catch (ConnectException ce) {
	    } catch (Exception e) {
	    }
	    return jsonObject;
	  }
	  /**
	   * 获取微信用户信息
	   * @param accessToken
	   * @param openId
	   * @return
	   */
	public static WeixinUserInfo getWeixinUserInfo(String accessToken,String openId){
		WeixinUserInfo weixinUserInfo = null;
		String url=GET_INFO_BY_OPENID.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
		 JSONObject jsonObject = WeixinUtil.httpsRequest(url, "GET", null);
		 if (null != jsonObject) {
		      try {
		    	  if(jsonObject.containsKey(ERROR_CODE_COLUMN)){
					 int code = jsonObject.getInt(ERROR_CODE_COLUMN);
					 if(code == 42001){//token失效
						 accessToken = userService.getToken();
						 if(StringUtils.isNotBlank(accessToken)){
							 return getWeixinUserInfo(accessToken, openId);
						 }
					 }
					 return null;
				 }
		        weixinUserInfo = new WeixinUserInfo();
		        // 用户的标识
		        weixinUserInfo.setOpenId(jsonObject.getString("openid"));
		        // 关注状态（1是关注，0是未关注），未关注时获取不到其余信息
		        weixinUserInfo.setSubscribe(jsonObject.getInt("subscribe"));
		        // 用户关注时间
		        weixinUserInfo.setSubscribeTime(jsonObject.getString("subscribe_time"));
		        // 昵称
		        weixinUserInfo.setNickname(jsonObject.getString("nickname"));
		        // 用户的性别（1是男性，2是女性，0是未知）
		        weixinUserInfo.setSex(jsonObject.getInt("sex"));
		        // 用户所在国家
		        weixinUserInfo.setCountry(jsonObject.getString("country"));
		        // 用户所在省份
		        weixinUserInfo.setProvince(jsonObject.getString("province"));
		        // 用户所在城市
		        weixinUserInfo.setCity(jsonObject.getString("city"));
		        // 用户的语言，简体中文为zh_CN
		        weixinUserInfo.setLanguage(jsonObject.getString("language"));
		        // 用户头像
		        weixinUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
		      } catch (Exception e) {
		        if (0 == weixinUserInfo.getSubscribe()) {
		        } else {
		          int errorCode = jsonObject.getInt("errcode");
		          String errorMsg = jsonObject.getString("errmsg");
		          System.out.println("错误码："+errorCode);
		          System.out.println("错误信息："+errorMsg);
		        }
		      }
		    }
		return weixinUserInfo;
	}
	
	/**
	 * 获取mediaId
	 * @param filePath-图片路径
	 * @param accessToken-凭据
	 * @param type-类型
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws KeyManagementException
	 */
	public static String upload(String filePath, String accessToken,String type) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
		File file = new File(filePath);
		if (!file.exists() || !file.isFile()) {
			throw new IOException("文件不存在");
		}

		String url = UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE",type);
		
		URL urlObj = new URL(url);
		//链接
		HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

		con.setRequestMethod("POST"); 
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false); 

		//设置请求消息头
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");

		//设置边界
		String BOUNDARY = "----------" + System.currentTimeMillis();
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

		StringBuilder sb = new StringBuilder();
		sb.append("--");
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");

		byte[] head = sb.toString().getBytes("utf-8");

		//获得输出流
		OutputStream out = new DataOutputStream(con.getOutputStream());
		//输出表头
		out.write(head);

		//文件正文部分
		//把文件以流的方式推入url中
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = in.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		in.close();

		//结尾部分
		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");

		out.write(foot);

		out.flush();
		out.close();

		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		String result = null;
		try {
			//定义BufferedReader输入流来读取url的响应
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			if (result == null) {
				result = buffer.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
		}

		JSONObject jsonObj = JSONObject.fromObject(result);
		System.out.println(jsonObj);
		String typeName = "media_id";
		if(!WeixinMsgType.IMAGE.getKey().equals(type)){
			typeName = type + "_media_id";
		}
		String mediaId = jsonObj.getString(typeName);
		return mediaId;
	}
	
	private static String getErrorMsg(int errorCode){
		String errorMsg = "";
		switch (errorCode) {
			case 45009:
				errorMsg = "接口调用频次超过限制";
				break;
	
			default:
				break;
		}
		return errorMsg;
	}
	
	  public  static void sendTextMessageToUser(String content,String toUser,String Token){

	       String json = "{\"touser\": \""+toUser+"\",\"msgtype\": \"text\", \"text\": {\"content\": \""+content+"\"}}";

	       //获取access_token


	       String accessToken = Token;

	       //获取请求路径

	       String action = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+accessToken;

	       System.out.println("json:"+json);

	       try {

	           connectWeiXinInterface(action,json);

	       } catch (Exception e) {

	           e.printStackTrace();

	       }

	   }
	   /**

	     * 连接请求微信后台接口

	     * @param action 接口url

	     * @param json  请求接口传送的json字符串

	     */

	    public  static void connectWeiXinInterface(String action,String json){

	        URL url;

	       try {

	           url = new URL(action);

	           HttpURLConnection http = (HttpURLConnection) url.openConnection();

	           http.setRequestMethod("POST");

	           http.setRequestProperty("Content-Type",

	                   "application/x-www-form-urlencoded");

	           http.setDoOutput(true);

	           http.setDoInput(true);

	           System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒

	           System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒

	           http.connect();

	           OutputStream os = http.getOutputStream();

	           os.write(json.getBytes("UTF-8"));// 传入参数

	           InputStream is = http.getInputStream();

	           int size = is.available();

	           byte[] jsonBytes = new byte[size];

	           is.read(jsonBytes);

	           String result = new String(jsonBytes, "UTF-8");

	           System.out.println("请求返回结果:"+result);

	           os.flush();

	           os.close();

	       } catch (Exception e) {

	           e.printStackTrace();

	       }

	    }
	    /** 
	     * 随机加密 
	     * @param hash 
	     * @return 
	     */  
	    public static String byteToHex(final byte[] hash) {  
	        Formatter formatter = new Formatter();  
	        for (byte b : hash)  
	        {  
	            formatter.format("%02x", b);  
	        }  
	        String result = formatter.toString();  
	        formatter.close();  
	        return result;  
	    }  
		
		/** 
	     * 产生随机串--由程序自己随机产生 
	     * @return 
	     */  
	    public static String create_nonce_str() {  
	        return UUID.randomUUID().toString();  
	    }  
	  
	    /** 
	     * 由程序自己获取当前时间 
	     * @return 
	     */  
	    public static String create_timestamp() {  
	        return Long.toString(System.currentTimeMillis() / 1000);  
	    } 
	    /** 
	     *  
	     * 根据文件id下载文件 
	     *  
	     *  
	     *  
	     * @param mediaId 
	     *  
	     *            媒体id 
	     *  
	     * @throws Exception 
	     */  
	  
	    public static InputStream getInputStream(String accessToken, String mediaId) {  
	        InputStream is = null;  
	        String url = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token="  
	                + accessToken + "&media_id=" + mediaId;  
	        try {  
	            URL urlGet = new URL(url);  
	            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();  
	            http.setRequestMethod("GET"); // 必须是get方式请求  
	            http.setRequestProperty("Content-Type",  
	                    "application/x-www-form-urlencoded");  
	            http.setDoOutput(true);  
	            http.setDoInput(true);  
	            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒  
	            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒  
	            http.connect();  
	            // 获取文件转化为byte流  
	            is = http.getInputStream();  
	  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	        return is;  
	  
	    } 
	    /** 
	     *  
	     * 获取下载图片信息（jpg） 
	     *  
	     *  
	     *  
	     * @param mediaId 文件的id 
	     * @param uploadPath 中间路径
	     * @param fileName 文件名字，不包含后缀
	     * @param basePath 基础路径
	     * @throws Exception 
	     */  
	  
	    public static String[] saveImageToDisk(String accessToken, String mediaId, String uploadPath,String fileName,String basePath)  
	            throws Exception {  
	        InputStream inputStream = getInputStream(accessToken, mediaId);  
	        byte[] data = new byte[10240];  
	        int len = 0;  
	        FileOutputStream fileOutputStream = null;  
	        try {  
	        	// 文件保存路径
				String filePath = Config.getUploadBasepath() + uploadPath;
				File fileDir = new File(filePath);
				// 如果文件夹不存在则创建
				if (!fileDir.exists()) {
					fileDir.mkdirs();
				}
				fileName = fileName+".jpg";
	            fileOutputStream = new FileOutputStream(filePath+"/"+fileName);  
	            while ((len = inputStream.read(data)) != -1) {  
	                fileOutputStream.write(data, 0, len);  
	            }  
	            
	            return new String[]{basePath+uploadPath+"/"+fileName,""};
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        } finally {  
	            if (inputStream != null) {  
	                try {  
	                    inputStream.close();  
	                } catch (IOException e) {  
	                    e.printStackTrace();  
	                }  
	            }  
	            if (fileOutputStream != null) {  
	                try {  
	                    fileOutputStream.close();  
	                } catch (IOException e) {  
	                    e.printStackTrace();  
	                }  
	            }  
	        }  
	        return null;
	    }
	
}
