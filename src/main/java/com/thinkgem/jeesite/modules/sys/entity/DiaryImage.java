package com.thinkgem.jeesite.modules.sys.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class DiaryImage extends DataEntity<DiaryImage>{
	
	private static final long serialVersionUID = 1L;

	private String diaryId;//日记id
	private String imgPath;//图片路径
	private String flag;//术前术后状态码，术前0术后1
	private int sort;//排序;
	
	public DiaryImage() {
		super();
	}

	public String getDiaryId() {
		return diaryId;
	}

	public void setDiaryId(String diaryId) {
		this.diaryId = diaryId;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}
	

}
