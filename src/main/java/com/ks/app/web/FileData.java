package com.ks.app.web;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Base64基础JavaBean
 * @author taolin
 *
 */
public class FileData {
	String mine;//信息
	String suffix;//文件后缀
	String data;//Base64原始码
	
	boolean isError;//判断是否参数有误
	
	public boolean isError() {
		return isError;
	}
 
	/**
	 * 源字符串 直接解析
	 * @param original
	 */
	public FileData(String original) {
		super();
		
		String[] temps = original.split(",");
		try {
			this.mine=temps[0];
			this.data=temps[1];
			this.suffix = handleSuffix(mine);
			this.isError = true;
		} catch (Exception e) {
			// TODO: handle exception
			this.isError = false;
		}
	}
	/**
	 * 自动解析拆分
	 * @param mine
	 * @param data
	 */
	public FileData(String mine, String data) {
		super();
		this.mine = mine;
		this.data = data;
		this.suffix = handleSuffix(mine);
	}
	public String getMine() {
		return mine;
	}
	public void setMine(String mine) {
		this.mine = mine;
		this.suffix = handleSuffix(mine);
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	/**
	 * 用于解析两个匹配字符中间的数据。
	 * @param mine
	 * @return
	 */
	private String handleSuffix(String mine){
        String regext = "(?<=(data:image/))(.?)*(?=(;base64))";
        Pattern pattern = Pattern.compile(regext);
        Matcher matcher = pattern.matcher(mine);
        while(matcher.find())
        {
           return matcher.group();
        }
        return "";
		
	}
	/**
	 * 路径+文件名
	 * @return 返回路径+文件全名
	 */
	public String createPicFullFilePath(){
		return createNewDateFilePath()+File.separator+getRandomFileName()+"."+this.suffix;
	}
	
	/**
	 * 路径（文件路径日期方式生成）
	 * @return 返回路径信息
	 */
	public String createNewDateFilePath() {
		Date date = new Date(); 
		String path=new SimpleDateFormat("yyyy/MM/dd").format(date);
		String[] tmps=path.split("/");
//		StringBuffer mosaic = new StringBuffer(FileData.basePath);
		StringBuffer mosaic = new StringBuffer("");
		for (String lujing : tmps) {
			mosaic.append(lujing+File.separator);
		}
		mosaic.deleteCharAt(mosaic.length()-1);
		return mosaic.toString();
	}
	
    /**
     * 获取随机文件名称   yyyyMMdd+5尾随机数
     * @return
     */
    private String getRandomFileName() {    
        SimpleDateFormat simpleDateFormat;  
        simpleDateFormat = new SimpleDateFormat("yyyyMMdd");  
        Date date = new Date();  
        String str = simpleDateFormat.format(date);  
        Random random = new Random();  
        int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数  
        return rannum + str;// 当前时间  
    } 
}
