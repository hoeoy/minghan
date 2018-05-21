package com.ks.app.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class ADoctor extends DataEntity<ADoctor> {
	private static final long serialVersionUID = 1L;


	private String name;//名字
	private String post;//岗位
	private String photo;//头像
	private String minPhoto;//略缩图
	private String intro;//介绍	
	private String attestationFlag;//是否认证
	
	public ADoctor() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getMinPhoto() {
		return minPhoto;
	}

	public void setMinPhoto(String minPhoto) {
		this.minPhoto = minPhoto;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getAttestationFlag() {
		return attestationFlag;
	}

	public void setAttestationFlag(String attestationFlag) {
		this.attestationFlag = attestationFlag;
	}
	

	
	

}
