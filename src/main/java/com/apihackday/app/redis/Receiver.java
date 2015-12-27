package com.apihackday.app.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.apihackday.app.exotel.api.service.SendSmsService;
import com.apihackday.app.pojo.SosMessage;

public class Receiver {


    @Autowired 
    private StringRedisTemplate template;
    
    @Autowired SendSmsService sendSmsService;
    
    @Autowired
    public Receiver() {
    }

    public void receiveMessage(String message) {
    	System.out.println("Received Message: "+message);
    	SosMessage sos = SosMessage.fromJson(message);
    	HashOperations<String,String, String> ops = this.template.opsForHash();//("call").getOperations().opsForValue();
    	//HashOperations<String,String, String> ops1 = this.template.boundValueOps("sms").getOperations().opsForValue();
		String key = sos.getTo();		
		ops.put("call",key, sos.getMessage());
		ops.put("sms",key, sos.getFrom());
		System.out.println("Message queued: Phone=" + key + ", Message=" + ops.get("call", key));
		System.out.println("Message queued: Phone=" + key + ", From=" +ops.get("sms", key));
		System.out.println("Sending message to: "+sos.getTo());
		sendSmsService.sendSms(sos);
		
    }
}
