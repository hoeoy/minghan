package com.ks.app.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.Poster;

public class APoster extends DataEntity<Poster>{
	private static final long serialVersionUID = 1L;
	private String path;
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public APoster() {
		super();
	}
	
	

}
