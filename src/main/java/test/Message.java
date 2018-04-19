package test;

import java.sql.Timestamp;

public class Message {
	private int id;
	private String cookie;
	private Timestamp postTime;
	private String content;
	private int idRef;
	
	public Message() {
	}
	
	public Message(String cookie, Timestamp postTime, String content, int idRed) {
		this.cookie=cookie;
		this.postTime=postTime;
		this.content=content;
		this.idRef=idRed;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCookie() {
		return cookie;
	}
	public void setCookie(String cookie) {
		this.cookie = cookie;
	}
	public Timestamp getPostTime() {
		return postTime;
	}
	public void setPostTime(Timestamp postTime) {
		this.postTime = postTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getIdRef() {
		return idRef;
	}
	public void setIdRef(int idRef) {
		this.idRef = idRef;
	}
}
