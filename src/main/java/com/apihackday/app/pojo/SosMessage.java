package com.apihackday.app.pojo;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SosMessage {

	private String from;
	private String to;
	private String message;
	
	public SosMessage(){
		
	}
	
	public SosMessage(String to, String message, String from) {
		super();
		this.from = from;
		this.to = to;
		this.message = message;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String toJson(){
		ObjectMapper objMapper = new ObjectMapper();
		try {
			return objMapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			return "";
		}
	}
	
	public static SosMessage fromJson(String json){
		ObjectMapper objMapper = new ObjectMapper();
		try {
			return objMapper.readValue(json, SosMessage.class);
		} catch (JsonParseException e) {
			return null;
		} catch (JsonMappingException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
	}
}
