package com.apihackday.app.pojo;

public class SmsMessage {

	private String From;
	private String To;
	private String Body;
	
	public SmsMessage(){
		
	}
	
	public String getFrom() {
		return From;
	}
	public void setFrom(String from) {
		From = from;
	}
	public String getTo() {
		return To;
	}
	public void setTo(String to) {
		To = to;
	}
	public String getBody() {
		return Body;
	}
	public void setBody(String body) {
		Body = body;
	}
}
