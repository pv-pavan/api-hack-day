package com.apihackday.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apihackday.app.pojo.SosMessage;

@RestController
public class ImSafeSosController {

	@Autowired StringRedisTemplate template;
	
	@RequestMapping(value="/sos/sendSms", method=RequestMethod.POST)
	public String sendSosMessage(@RequestParam String to, 
			@RequestParam String message,
			@RequestParam String from ){
		SosMessage msg = new SosMessage(to, message, from);
		template.convertAndSend("sos", msg.toJson());
		
		return "success";
	}
}
