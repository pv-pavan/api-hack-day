package com.apihackday.app.sendgrid.inbound;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apihackday.app.pojo.SosMessage;

@RestController
@RequestMapping(value="/sendgrid")
public class ParseController {
	
	@Autowired StringRedisTemplate template;

	@RequestMapping(value="/inboundParse", 
			method=RequestMethod.POST)
	public void inboundParse(@RequestParam("text") String text,
			@RequestParam("from") String from,
			@RequestParam("to") String to,
			@RequestParam("subject") String subject){
		System.out.println("Received email from: "+from+"-  to: "+to);
		
		
		System.out.println("Sending message...");
		String[] subs = subject.split(",");
		for(String sub: subs){
			SosMessage msg = new SosMessage(sub.trim(), text, from);
			template.convertAndSend("sos", msg.toJson());
		}
	}
}
