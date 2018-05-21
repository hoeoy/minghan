package com.thinkgem.jeesite.common.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @description 得到当前应用的系统路径
 */
public class SystemPath {

	public static String getSysPath() {
		String path = Thread.currentThread().getContextClassLoader()
				.getResource("").toString();
		String temp = path.replaceFirst("file:/", "").replaceFirst(
				"WEB-INF/classes/", "");
		String separator = System.getProperty("file.separator");
		String resultPath = temp.replaceAll("/", separator + separator);
		return resultPath;
	}

	public static String getClassPath() {
		String path = Thread.currentThread().getContextClassLoader()
				.getResource("").toString();
		String temp = path.replaceFirst("file:/", "");
		String separator = System.getProperty("file.separator");
		String resultPath = temp.replaceAll("/", separator + separator);
		return resultPath;
	}

	public static String getSystempPath() {
		return System.getProperty("java.io.tmpdir");
	}

	public static String getSeparator() {
		return System.getProperty("file.separator");
	}
/*	*//**
	 * 添加图片完全路径
	 * 
	 * @param articleInfo
	 * @return
	 *//*
	public static void setArticleImagePath(ArticleImage articleImage,String prePath) {
		if(articleImage == null)
			articleImage = new ArticleImage();
		// 文件保存路径
		String uploadPath = articleImage.getImageName();
		if(StringUtils.isNotBlank(uploadPath)){
			articleImage.setImageName(prePath+"/"+FileUtils.uploadPath+ uploadPath);
		}
		String uploadPath_s = articleImage.getImageThumb();
		if(StringUtils.isNotBlank(uploadPath_s)){
			articleImage.setImageThumb(prePath+"/"+FileUtils.uploadPath+ uploadPath_s);
		}
	}
	public static void setArticleImagePath(List<ArticleImage> articleImages,String prePath) {
		if(articleImages == null)
			return;
		for (ArticleImage articleImage : articleImages) {
			setArticleImagePath(articleImage,prePath);
		}
	}
*/	
	public static String getRealPath(String uploadPath,String prePath){
		if(StringUtils.isNotBlank(uploadPath)){
//			return prePath+FileUtils.uploadPath+ uploadPath;
			return prePath + uploadPath;
		}
		return null;
	}
	public static String getRequestPreUrl(HttpServletRequest request){
		return request.getScheme() //当前链接使用的协议
			    +"://" + request.getServerName()//服务器地址 
			    + (request.getServerPort() == 80 ? "":(":" + request.getServerPort())); //端口号 
//			    + request.getContextPath(); //应用名称，如果应用名称为
	}
	public static String getRequestProjectUrl(HttpServletRequest request){
		return request.getScheme() //当前链接使用的协议
				+"://" + request.getServerName()//服务器地址 
				+ (request.getServerPort() == 80 ? "":(":" + request.getServerPort())) //端口号 
			    + request.getContextPath(); //应用名称，如果应用名称为
	}
	public static void main(String[] args) {
		System.out.println(getSysPath());
		System.out.println(System.getProperty("java.io.tmpdir"));
		System.out.println(getSeparator());
		System.out.println(getClassPath());
	}
	public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if(index != -1){
                return ip.substring(0,index);
            }else{
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            return ip;
        }
        return request.getRemoteAddr();
    }
}
