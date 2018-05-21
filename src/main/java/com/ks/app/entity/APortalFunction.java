package com.ks.app.entity;

import java.io.Serializable;

public class APortalFunction implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String label;
	private String url;
	private int order;
	public APortalFunction(){}
	
	public APortalFunction(String id,String label,String url,int order) {
		this.id=id;
		this.label=label;
		this.url=url;
		this.order=order;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
}
