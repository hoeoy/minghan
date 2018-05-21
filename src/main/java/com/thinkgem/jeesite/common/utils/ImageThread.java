package com.thinkgem.jeesite.common.utils;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.ks.utils.Constant;

/**
 * 图片处理的线程
 * 
 * @author Administrator
 */
public class ImageThread extends Thread{

	/** 水印图片 */
	public static final String WATERMARK_IMAGE = ImageThread.class.getClassLoader().getResource("").getPath() + "/conf/main_logo.png";

	/** 文件对象 */
	private MultipartFile mfile;

	/** 文件路径 */
	private String filePath;

	/** 原文件名称 */
	private String fileName;

	/** 缩略图文件名称 */
	private String thumbFileName;

	/** 是否生成缩略图(true:生成; false:不生成) */
	private boolean tflag;

	/** 是否加水印(true:生成; false:不生成) */
	private boolean sflag;

	/**
	 * 图片处理的线程
	 * 
	 */
	public void run() {
		
		File fileDir = new File(filePath);
		// 如果文件夹不存在则创建
		if (!fileDir.exists() && !fileDir.isDirectory()) {
			fileDir.mkdirs();
		}

		if (StringUtils.isNotBlank(fileName)) {
			// 保存文件
			String saveFile = filePath + File.separator + fileName;
			

			// 生成缩略图
			if (tflag) {
				String saveThumbFile = filePath + File.separator + thumbFileName;
				ImageUtils.scale(saveFile, saveThumbFile, Constant.DEFAULT_HEIGHT, Constant.DEFAULT_WIDTH, false);
			}

			// 图片加水印
			if (sflag) {
				ImageUtils.pressImage(WATERMARK_IMAGE, saveFile, saveFile, 250, 100, 1f);
			}
		}
	}
	
	public void saveFile() throws IllegalStateException, IOException{
		File fileDir = new File(filePath + File.separator);
		// 如果文件夹不存在则创建
		if (!fileDir.exists() && !fileDir.isDirectory()) {
			fileDir.mkdirs();
		}
		String saveFile = filePath + File.separator + fileName;
		mfile.transferTo(new File(saveFile));
	}

	/**
	 * @return the mfile
	 */
	public MultipartFile getMfile() {
		return mfile;
	}

	/**
	 * @param mfile the mfile to set
	 */
	public void setMfile(MultipartFile mfile) {
		this.mfile = mfile;
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the thumbFileName
	 */
	public String getThumbFileName() {
		return thumbFileName;
	}

	/**
	 * @param thumbFileName the thumbFileName to set
	 */
	public void setThumbFileName(String thumbFileName) {
		this.thumbFileName = thumbFileName;
	}

	/**
	 * @return the tflag
	 */
	public boolean isTflag() {
		return tflag;
	}

	/**
	 * @param tflag the tflag to set
	 */
	public void setTflag(boolean tflag) {
		this.tflag = tflag;
	}

	/**
	 * @return the sflag
	 */
	public boolean isSflag() {
		return sflag;
	}

	/**
	 * @param sflag the sflag to set
	 */
	public void setSflag(boolean sflag) {
		this.sflag = sflag;
	}
}
