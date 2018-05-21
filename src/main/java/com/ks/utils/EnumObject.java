package com.ks.utils;

import java.io.Serializable;

public class EnumObject implements Serializable{
	private static final long serialVersionUID = 1L;
	private String label;
	private String value;
	public EnumObject() {
	}
	public EnumObject(String label, String value) {
		super();
		this.label = label;
		this.value = value;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
