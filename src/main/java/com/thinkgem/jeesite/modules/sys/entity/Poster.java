package com.thinkgem.jeesite.modules.sys.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class Poster extends DataEntity<Poster>{
	private static final long serialVersionUID = 1L;
	private String path;
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Poster() {
		super();
	}
	
	

}
