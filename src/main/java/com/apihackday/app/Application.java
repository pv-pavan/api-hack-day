package com.apihackday.app;


import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apihackday.app.exotel.api.service.SendEmailService;
import com.apihackday.app.pojo.SosMessage;


@SpringBootApplication
@RestController
public class Application {
	
	@Autowired
	private StringRedisTemplate template;
	@Autowired 
	private SendEmailService sendEmailService;
	
	@RequestMapping(value="/imsafe", method=RequestMethod.GET)
	public String imSafe(@RequestParam("CallSid") String callSid,
			@RequestParam("From") String from,
			@RequestParam("To") String to,
			@RequestParam("DialWhomNumber") String dialWhomNumber){
		System.out.println("From: "+from+" To: "+to+" CallSid: "+callSid+" DialWhomNumber: "+dialWhomNumber);
		String message = "You do not have a message right now";
		HashOperations<String, String, String> ops = this.template.opsForHash();		
		
		if(this.template.hasKey("call")){
			message = ops.get("call",from);
			System.out.println("Found key.. Answering call with "+message);
		}

		return message;
	}
	
	@RequestMapping(value="/imsafe/incomingSms", method=RequestMethod.GET)
	public void incomingSms(@RequestParam("SmsSid") String callSid,
			@RequestParam("From") String from,
			@RequestParam("To") String to,
			@RequestParam("Date") String date,
			@RequestParam("Body") String body){
		System.out.println("From: "+from+" To: "+to+" Body: "+body);
		String emailTo = null;
		HashOperations<String, String, String> ops = this.template.opsForHash();
		if(this.template.hasKey("sms")){
			emailTo = ops.get("sms",from);
			System.out.println("Found key..");
			if(emailTo!=null){
				System.out.println("Sending mail to "+emailTo);
				sendEmailService.sendEmail(emailTo, "sos@hackday.bymail.in", body, from);
			}
		}

	}

	public static void main(String[] args) {
		
		ApplicationContext ctx = SpringApplication.run(Application.class, args);

		//StringRedisTemplate template = ctx.getBean(StringRedisTemplate.class);
	
		/*System.out.println("Sending message...");
		SosMessage msg = new SosMessage("09442260425", "I am safe");
		template.convertAndSend("sos", msg.toJson());*/

	}
}
