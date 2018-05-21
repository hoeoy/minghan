package com.ks.app.utils;

import java.io.Serializable;

/**
 * 接口返回对象包裹一层结构提
 * code:0正常/1不兼容; 
 * msg:提示信息; 
 * data:返回对象
 * 
 */
public class ActionResponse<T> implements Serializable {
	
	private static final long serialVersionUID = -5130284009910508141L;
	
	private int code;
	private String msg;
	private T data;
	private Long serverTime = System.currentTimeMillis();
	private int totalPage;

	public ActionResponse() {
	}

	public ActionResponse(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
		this.serverTime = System.currentTimeMillis();
	}

	public ActionResponse(int code, String msg, T data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
		this.serverTime = System.currentTimeMillis();
	}
	public ActionResponse(int code, String msg, T data,int totalPage) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
		this.totalPage = totalPage;
		this.serverTime = System.currentTimeMillis();
	}

	/**
	 * 接口无消息提示正常返回是使用
	 * @param data
	 */
	public ActionResponse(T data) {
		super();
		this.code = 0;
		this.msg = "";
		this.data = data;
		this.totalPage = 0;
		this.serverTime = System.currentTimeMillis();
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Long getServerTime() {
		return serverTime;
	}

	public void setServerTime(Long serverTime) {
		this.serverTime = serverTime;
	}
	
	public void setingError(String msg) {
		this.code = 1;
		this.setMsg(msg);
	}
	
	public void setingSuccess(String msg) {
		this.code = 0;
		this.setMsg(msg);
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
}
