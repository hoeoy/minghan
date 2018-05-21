package com.ks.app.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.swing.ImageIcon;

import net.sf.json.JSONObject;

import com.ks.utils.Config;
import com.ks.utils.ToolUtil;
import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.thinkgem.jeesite.common.utils.FileUtils;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.ImageUtils;



public class WeixinERCodeUtil {
	public final static String QR_SCENE_PRE = "qrscene_";  
	public static String CODE_SOURCE_FILENAME = "source.jpg";  //二维码背景图,640*1138
	// 临时二维码  
	private final static String QR_SCENE = "QR_SCENE";  
	// 临时二维码  (字符串)
	private final static String QR_STR_SCENE = "QR_STR_SCENE";  
	// 永久二维码  
	private final static String QR_LIMIT_SCENE = "QR_LIMIT_SCENE";  
	// 永久二维码(字符串)  
	private final static String QR_LIMIT_STR_SCENE = "QR_LIMIT_STR_SCENE";   
	// 创建二维码  
	private static String create_ticket_path = "https://api.weixin.qq.com/cgi-bin/qrcode/create";  
	// 通过ticket换取二维码  
	private static String showqrcode_path = "https://mp.weixin.qq.com/cgi-bin/showqrcode";  
	
	private static String CODE_FIRST = "招募形象大使";  
	private static String CODE_SECOND = "我是 【%s】";  
	private static String CODE_THIRD = "我为 【名韩回龄术】代言"; 
	
	  
	/** 
	 * 创建临时带参数二维码 
	 * @param accessToken 
	 * @expireSeconds 该二维码有效时间，以秒为单位。 最大不超过2592000（即30天），此字段如果不填，则默认有效期为30秒。 
	 * @param sceneId 场景Id 
	 * @return 
	 */  
	public static String createTempTicket(String accessToken, String expireSeconds, int sceneId) {  
	    WeiXinQRCode wxQRCode = null;  
	      
	    TreeMap<String,String> params = new TreeMap<String,String>();  
	    params.put("access_token", accessToken);  
	    Map<String,Integer> intMap = new HashMap<String,Integer>();  
	    intMap.put("scene_id",sceneId);  
	    Map<String,Map<String,Integer>> mapMap = new HashMap<String,Map<String,Integer>>();  
	    mapMap.put("scene", intMap);  
	    //  
	    Map<String,Object> paramsMap = new HashMap<String,Object>();  
	    paramsMap.put("expire_seconds", expireSeconds);  
	    paramsMap.put("action_name", QR_SCENE);  
	    paramsMap.put("action_info", mapMap);  
	    String data = JSONObject.fromObject(paramsMap).toString();  
	    data = HttpRequestUtil.HttpsDefaultExecute(HttpRequestUtil.POST_METHOD,create_ticket_path,params,data);  
        wxQRCode = (WeiXinQRCode)JSONObject.toBean(JSONObject.fromObject(data), WeiXinQRCode.class);  
	    return wxQRCode==null?null:wxQRCode.getTicket();  
	  
	}  
	/** 
	 * 创建临时带参数二维码 (字符串)
	 * @param accessToken 
	 * @expireSeconds 该二维码有效时间，以秒为单位。 最大不超过2592000（即30天），此字段如果不填，则默认有效期为30秒。 
	 * @param sceneId 场景Id 
	 * @return 
	 */  
	public static String createTempTicket(String accessToken, String expireSeconds, String sceneId) {  
		WeiXinQRCode wxQRCode = null;  
		
		TreeMap<String,String> params = new TreeMap<String,String>();  
		params.put("access_token", accessToken);  
		Map<String,String> intMap = new HashMap<String,String>();  
//		intMap.put("scene_id",sceneId);  
		intMap.put("scene_str",sceneId);  
		Map<String,Map<String,String>> mapMap = new HashMap<String,Map<String,String>>();  
		mapMap.put("scene", intMap);  
		//  
		Map<String,Object> paramsMap = new HashMap<String,Object>();  
		paramsMap.put("expire_seconds", expireSeconds);  
		paramsMap.put("action_name", QR_STR_SCENE);  
		paramsMap.put("action_info", mapMap);  
		String data = JSONObject.fromObject(paramsMap).toString();  
		data = HttpRequestUtil.HttpsDefaultExecute(HttpRequestUtil.POST_METHOD,create_ticket_path,params,data);  
		wxQRCode = (WeiXinQRCode)JSONObject.toBean(JSONObject.fromObject(data), WeiXinQRCode.class);  
		return wxQRCode==null?null:wxQRCode.getTicket();  
		
	}  
	  
	/** 
	 * 创建永久二维码(数字) 
	 * @param accessToken 
	 * @param sceneId 场景Id 
	 * @return 
	 */  
	public String createForeverTicket(String accessToken, int sceneId) {  
	      
	    TreeMap<String,String> params = new TreeMap<String,String>();  
	    params.put("access_token", accessToken);  
	    //output data  
	    Map<String,Integer> intMap = new HashMap<String,Integer>();  
	    intMap.put("scene_id",sceneId);  
	    Map<String,Map<String,Integer>> mapMap = new HashMap<String,Map<String,Integer>>();  
	    mapMap.put("scene", intMap);  
	    //  
	    Map<String,Object> paramsMap = new HashMap<String,Object>();  
	    paramsMap.put("action_name", QR_LIMIT_SCENE);  
	    paramsMap.put("action_info", mapMap);  
	    String data = JSONObject.fromObject(paramsMap).toString();  
	    data =  HttpRequestUtil.HttpsDefaultExecute(HttpRequestUtil.POST_METHOD,create_ticket_path,params,data);  
	    WeiXinQRCode wxQRCode = (WeiXinQRCode)JSONObject.toBean(JSONObject.fromObject(data), WeiXinQRCode.class);  
	    return wxQRCode==null?null:wxQRCode.getTicket();  
	}  
	  
	/** 
	 * 创建永久二维码(字符串) 
	 *  
	 * @param accessToken 
	 * @param sceneStr 场景str 
	 * @return 
	 */  
	public String createForeverStrTicket(String accessToken, String sceneStr){        
	    TreeMap<String,String> params = new TreeMap<String,String>();  
	    params.put("access_token", accessToken);  
	    //output data  
	    Map<String,String> intMap = new HashMap<String,String>();  
	    intMap.put("scene_str",sceneStr);  
	    Map<String,Map<String,String>> mapMap = new HashMap<String,Map<String,String>>();  
	    mapMap.put("scene", intMap);  
	      
	    Map<String,Object> paramsMap = new HashMap<String,Object>();  
	    paramsMap.put("action_name", QR_LIMIT_STR_SCENE);  
	    paramsMap.put("action_info", mapMap);  
	    String data = JSONObject.fromObject(paramsMap).toString();  
	    data =  HttpRequestUtil.HttpsDefaultExecute(HttpRequestUtil.POST_METHOD,create_ticket_path,params,data);  
	    WeiXinQRCode wxQRCode = (WeiXinQRCode)JSONObject.toBean(JSONObject.fromObject(data), WeiXinQRCode.class);
	    return wxQRCode==null?null:wxQRCode.getTicket();  
	}  
	
	/** 
	 * 获取二维码ticket后，通过ticket换取二维码图片展示 
	 * @param ticket 
	 * @return 
	 */  
	public static String showQrcode(String ticket){  
	    Map<String,String> params = new TreeMap<String,String>();  
	    params.put("ticket", HttpRequestUtil.urlEncode(ticket, HttpRequestUtil.DEFAULT_CHARSET));  
	    try {  
	        showqrcode_path = HttpRequestUtil.setParmas(params,showqrcode_path,"");  
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    }  
	    return showqrcode_path;  
	}  
	  
	/** 
	 * 获取二维码ticket后，通过ticket换取二维码图片 
	 * @param ticket 
	 * @param savePath  保存的路径,例如 F:\\test\test.jpg 
	 * @return      Result.success = true 表示下载图片下载成功 
	 */  
	public static WeiXinResult showQrcode(String ticket,String savePath) throws Exception{  
	    TreeMap<String,String> params = new TreeMap<String,String>();  
	    params.put("ticket", HttpRequestUtil.urlEncode(ticket, HttpRequestUtil.DEFAULT_CHARSET));  
//	    params.put("rewardUser", rewardUser);  
	    WeiXinResult result = HttpRequestUtil.downMeaterMetod(params,HttpRequestUtil.GET_METHOD,showqrcode_path,savePath);  
	    return result;  
	} 
	/**
	 * 图片上加文字或图片等
	 * @param username 用户名
	 * @param sourceImg 主图片
	 * @param headImg 附加图片
	 * @param savePath 保存路径 如d:/file/
	 * @param outPath 外部访问路径 如/minghan/image/
	 * return [外部访问路径,图片保存路径]
	 */
    public static String[] exportImg(String username,String sourceImg,String headImg,String savePath,String outPath){  
        try {  
            //1.jpg是你的 主图片的路径  
            InputStream is = new FileInputStream(sourceImg);  
              
              
            //通过JPEG图象流创建JPEG数据流解码器  
            JPEGImageDecoder jpegDecoder = JPEGCodec.createJPEGDecoder(is);  
            //解码当前JPEG数据流，返回BufferedImage对象  
            BufferedImage buffImg = jpegDecoder.decodeAsBufferedImage();  
            //得到画笔对象  
            Graphics g = buffImg.getGraphics();  
            
            int height = buffImg.getHeight();//源图高度
            int width = buffImg.getWidth();//源图宽度
              
            //创建你要附加的图象。  
            String saveThumbFile = savePath+IdGen.uuid()+"_200_200."+FileUtils.getFileExtension(headImg) ;
            ImageUtils.scale(headImg, saveThumbFile, 200, 200, false);
            //小图片的路径  
            ImageIcon imgIcon = new ImageIcon(saveThumbFile);   
              
            //得到Image对象。  
            Image img = imgIcon.getImage();  
            
            //将小图片绘到大图片上。  
            //5,300 .表示你的小图片在大图片上的位置。  
            g.drawImage(img,width/2-120,height/2-50,null);  
              
            //设置颜色。  
            g.setColor(Color.BLACK);  
              
              
            //最后一个参数用来设置字体的大小  
            Font f = new Font("微软雅黑",Font.BOLD,45);  
            Color mycolor = new Color(221, 213, 141);  
            g.setColor(mycolor);  
            g.setFont(f);  
              
            //10,20 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。
            int x = 90;
            int y = height-height/5;
            g.drawString(CODE_FIRST,x,y);  
            y = y+60;
            g.drawString(String.format(CODE_SECOND, username),x,y);  
            y = y+60;
//            g.drawString(CODE_THIRD,x,y);  
              
            g.dispose();  
              
              
            OutputStream os;  
          
            //os = new FileOutputStream("d:/union.jpg"); 
            String fileName = System.currentTimeMillis() + ".jpg";
            String shareFileName = savePath +fileName ;  
            os = new FileOutputStream(shareFileName);  
             //创键编码器，用于编码内存中的图象数据。            
            JPEGImageEncoder en = JPEGCodec.createJPEGEncoder(os);  
            en.encode(buffImg);           
              
            is.close();  
            os.close();  
            return new String[]{outPath+fileName,shareFileName};
        } catch (FileNotFoundException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (ImageFormatException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        return null;
          
    } 
    
    public static String[] MexportImg(String username,String sourceImg,String headImg,String savePath,String outPath){  
        try {  
            //1.jpg是你的 主图片的路径  
            InputStream is = new FileInputStream(sourceImg);  
              
              
            //通过JPEG图象流创建JPEG数据流解码器  
            JPEGImageDecoder jpegDecoder = JPEGCodec.createJPEGDecoder(is);  
            //解码当前JPEG数据流，返回BufferedImage对象  
            BufferedImage buffImg = jpegDecoder.decodeAsBufferedImage();  
            //得到画笔对象  
            Graphics g = buffImg.getGraphics();  
            
            int height = buffImg.getHeight();//源图高度
            int width = buffImg.getWidth();//源图宽度
              
            //创建你要附加的图象。  
            String saveThumbFile = savePath+IdGen.uuid()+"_200_200."+FileUtils.getFileExtension(headImg) ;
            ImageUtils.scale(headImg, saveThumbFile, 300, 300, false);
            //小图片的路径  
            ImageIcon imgIcon = new ImageIcon(saveThumbFile);   
              
            //得到Image对象。  
            Image img = imgIcon.getImage();  
            
            //将小图片绘到大图片上。  
            //5,300 .表示你的小图片在大图片上的位置。  
            g.drawImage(img,width/2-120,height/2-280,null);  
              
            //设置颜色。  
            g.setColor(Color.BLACK);  
              
              
            //最后一个参数用来设置字体的大小  
            Font f = new Font("微软雅黑",Font.BOLD,45);  
            Color mycolor = new Color(221, 213, 141);  
            g.setColor(mycolor);  
            g.setFont(f);  
              
            /*//10,20 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。
            int x = 90;
            int y = height-height/5;
            g.drawString("",x,y);  
            y = y+60;
            g.drawString(String.format("", username),x,y);  
            y = y+60;
            g.drawString("",x,y);*/ 
              
            g.dispose();  
              
              
            OutputStream os;  
          
            //os = new FileOutputStream("d:/union.jpg"); 
            String fileName = System.currentTimeMillis() + ".jpg";
            String shareFileName = savePath +fileName ;  
            os = new FileOutputStream(shareFileName);  
             //创键编码器，用于编码内存中的图象数据。            
            JPEGImageEncoder en = JPEGCodec.createJPEGEncoder(os);  
            en.encode(buffImg);           
              
            is.close();  
            os.close();  
            return new String[]{outPath+fileName,shareFileName};
        } catch (FileNotFoundException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (ImageFormatException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        return null;
          
    } 
    //[外部地址，服务器地址]
    public static String[] getERCodePath(String accessToken,String userId,String savePath,String outPath,HttpServletRequest request){
    	try {
	    	//获取ticket
			String ticket = createTempTicket(accessToken,WeixinUtil.TICKET_VALID,userId);
			String fileName = IdGen.uuid()+".jpg";
			String saveFilePath = savePath+fileName;
			//创建二维码,并下载到本地
			WeiXinResult result = showQrcode(ticket, saveFilePath);
			if(result.isSuccess()){
				outPath = outPath+fileName;
				return new String[]{outPath,result.getObj().toString()};
			}
    	}catch (Exception e) {
			// TODO: handle exception
    		e.printStackTrace();
		}
		return null;
    }
    //[外部地址，服务器地址]
    public static String[] getShareERCodePath(String path,String accessToken,String userId,String userName,HttpServletRequest request){
    	try {
    		String savePath = Config.getUploadBasepath()+Config.getUploadErweimapath();
    		String outPath = Config.getUploadBasePrepath()+Config.getUploadErweimapath();
    		String[] paths = getERCodePath(accessToken, userId,savePath, outPath, request);
    		if(paths != null){
    			//组装海报
    			String[] resultFile = exportImg(ToolUtil.filter(userName), path, paths[1], savePath,outPath);
    			if(resultFile != null){
    				outPath = paths[0];
    				return new String[]{resultFile[0],resultFile[1]};
    			}
    		}
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }
    
    public static String[] getShareERCodePathOn(String path,String accessToken,String userId,String userName,HttpServletRequest request){
    	try {
    		String savePath = Config.getUploadBasepath()+Config.getUploadErweimapath();
    		String outPath = Config.getUploadBasePrepath()+Config.getUploadErweimapath();
    		String[] paths = getERCodePath(accessToken, userId+"***",savePath, outPath, request);
    		if(paths != null){
    			//组装海报
    			String[] resultFile = exportImg(ToolUtil.filter(userName), path, paths[1], savePath,outPath);
    			if(resultFile != null){
    				outPath = paths[0];
    				return new String[]{resultFile[0],resultFile[1]};
    			}
    		}
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }
    public static String[] getShareERCodePathOnM(String path,String accessToken,String userId,String userName,HttpServletRequest request){
    	try {
    		String savePath = Config.getUploadBasepath()+Config.getUploadErweimapath();
    		String outPath = Config.getUploadBasePrepath()+Config.getUploadErweimapath();
    		String[] paths = getERCodePath(accessToken, userId+"***",savePath, outPath, request);
    		if(paths != null){
    			//组装海报
    			String[] resultFile = MexportImg(ToolUtil.filter(userName), path, paths[1], savePath,outPath);
    			if(resultFile != null){
    				outPath = paths[0];
    				return new String[]{resultFile[0],resultFile[1]};
    			}
    		}
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }
    
    public static void main(String[] args){  
//        exportImg("梦","d:/4.jpg","d:/1_2.jpg","d:/upload");  
    } 
    
    
}

