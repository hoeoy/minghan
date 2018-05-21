package com.ks.app.utils;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.ks.app.entity.Image;
import com.ks.app.entity.ImageMessage;
import com.ks.app.entity.LinkMessage;
import com.ks.app.entity.TextMessage;
import com.ks.utils.EnumConstant.WeixinMsgType;
import com.thoughtworks.xstream.XStream;

/**
 * xml转换成集合
 * @author Administrator
 *
 */
@SuppressWarnings("unchecked")
public class MessageUtil {
	public static Map<String,String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException{
		Map<String,String> map = new HashMap<String, String>();
		SAXReader reader = new SAXReader();
		InputStream is = request.getInputStream();
		Document doc = reader.read(is);
		
		Element root = doc.getRootElement();
		
		List<Element> list = root.elements();
		
		for(Element e : list){
			map.put(e.getName(),e.getText());
		}
		
		is.close();
		return map;
	}
	
	/**
	 *集合转xml
	 */
	public static String textMessageToXml(TextMessage textmessage){
		XStream xstream = new XStream();
		xstream.alias("xml", textmessage.getClass());
		return xstream.toXML(textmessage);
	}
	
	/**
	 * 图片消息转xml
	 * @param imageMessage
	 * @return
	 */
	public static String imageMessageToXml(ImageMessage imageMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", imageMessage.getClass());
		return xstream.toXML(imageMessage);
	}
	
	/**
	 * 发送图片消息
	 * @param toUserName
	 * @param fromUserName
	 * @param filePath
	 * @param accessToken
	 * @param type
	 * @return
	 */
	public static String initImageMessage(String toUserName,String fromUserName,String filePath,String accessToken,String type){
		String message = null;
		Image image = new Image();
		try {
			image.setMediaId(WeixinUtil.upload(filePath, accessToken, type));
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageMessage imageMessage = new ImageMessage();
		imageMessage.setFromUserName(toUserName);
		imageMessage.setToUserName(fromUserName);
		imageMessage.setMsgType("image");
		imageMessage.setCreateTime(new Date().getTime());
		imageMessage.setImage(image);
		message = imageMessageToXml(imageMessage);
		return message;
	}
	/**
	 * 链接消息转xml
	 * @param linkMessage
	 * @return
	 */
	public static String linkMessageToXml(LinkMessage linkMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", linkMessage.getClass());
		return xstream.toXML(linkMessage);
	}
	
	public static String initLinkMessage(String toUserName,String fromUserName,String Title,String Description,String Url){
		String message = null;
		LinkMessage linkMessage = new LinkMessage();
		
		linkMessage.setFromUserName(toUserName);
		linkMessage.setToUserName(fromUserName);
		linkMessage.setMsgType(WeixinMsgType.LINK.getKey());
		linkMessage.setCreateTime(new Date().getTime());
		linkMessage.setTitle(Title);
		linkMessage.setDescription(Description);
		linkMessage.setUrl(Url);
		message = linkMessageToXml(linkMessage);
		return message;
	}

}
