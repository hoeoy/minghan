package com.thinkgem.jeesite.modules.sys.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class BeautyDiary extends DataEntity<BeautyDiary> {
	
	private static final long serialVersionUID = 1L;
	private String authorName;//作者名字
	private String authorMinPhoto;//作者头像
	private String authorId;//作者id
	private String before;//术前相册封面地址
	private String after;//术后相册封面地址
	private String diary;//日记文字
	private String time;//时间
	
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public String getAuthorMinPhoto() {
		return authorMinPhoto;
	}
	public void setAuthorMinPhoto(String authorMinPhoto) {
		this.authorMinPhoto = authorMinPhoto;
	}
	public String getAuthorId() {
		return authorId;
	}
	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}
	public String getBefore() {
		return before;
	}
	public void setBefore(String before) {
		this.before = before;
	}
	public String getAfter() {
		return after;
	}
	public void setAfter(String after) {
		this.after = after;
	}
	public String getDiary() {
		return diary;
	}
	public void setDiary(String diary) {
		this.diary = diary;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	public BeautyDiary() {
		super();
	}
	
	

}
