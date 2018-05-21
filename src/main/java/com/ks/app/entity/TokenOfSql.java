package com.ks.app.entity;

public class TokenOfSql {
private int id;
private String accessToken;
private int expriresIn;
private long createTime;

private String jsapiTicket;//jsapi的ticket
private int ticketExpriresIn=0;//ticket有效期
private long ticketCreateTime=0;//ticket创建时间

public TokenOfSql() {
	super();
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getAccessToken() {
	return accessToken;
}
public void setAccessToken(String accessToken) {
	this.accessToken = accessToken;
}
public int getExpriresIn() {
	return expriresIn;
}
public void setExpriresIn(int expriresIn) {
	this.expriresIn = expriresIn;
}
public long getCreateTime() {
	return createTime;
}
public void setCreateTime(long createTime) {
	this.createTime = createTime;
}
public String getJsapiTicket() {
	return jsapiTicket;
}
public void setJsapiTicket(String jsapiTicket) {
	this.jsapiTicket = jsapiTicket;
}
public int getTicketExpriresIn() {
	return ticketExpriresIn;
}
public void setTicketExpriresIn(int ticketExpriresIn) {
	this.ticketExpriresIn = ticketExpriresIn;
}
public long getTicketCreateTime() {
	return ticketCreateTime;
}
public void setTicketCreateTime(long ticketCreateTime) {
	this.ticketCreateTime = ticketCreateTime;
}

}
