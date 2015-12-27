package com.apihackday.app.exotel.api.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.stereotype.Service;

import com.apihackday.app.pojo.SmsMessage;
import com.apihackday.app.pojo.SosMessage;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

@Service
public class SendSmsService {

	private static String URL = "https://imsafe:d63394e50425c6e85f227081c9aa6e030a30829d@twilix.exotel.in/v1/Accounts/imsafe/Sms/send";

	public void sendSms(SosMessage msg) {
		SmsMessage sms = new SmsMessage();
		sms.setBody(msg.getMessage());
		sms.setTo(msg.getTo());
		sms.setFrom("09243422233");

		HttpClient client = new HttpClient();
		PostMethod postMethod = new PostMethod(URL);
		String userPass = "imsafe" + ":" + "d63394e50425c6e85f227081c9aa6e030a30829d";
		String encoded = new String(Base64.encode(userPass.getBytes()));
		postMethod.setRequestHeader("Authorization", "Basic " + encoded);

		postMethod.setParameter("From", sms.getFrom());
		postMethod.setParameter("To", sms.getTo());
		postMethod.setParameter("Body", sms.getBody());

		BufferedReader br = null;
		try {
			int returnCode = client.executeMethod(postMethod);

			if (returnCode == HttpStatus.SC_NOT_IMPLEMENTED) {
				// TODO handle errors
				System.err.println("The Post method is not implemented by this URI");
				// still consume the response body
				postMethod.getResponseBodyAsString();
			} else {
				br = new BufferedReader(new InputStreamReader(
						postMethod.getResponseBodyAsStream()));
				StringBuilder builder = new StringBuilder();
				String readLine;
				while (((readLine = br.readLine()) != null)) {
					builder.append(readLine);
					System.out.println(readLine);
				}
			}
		} catch (Exception e) {
			// TODO handle errors
			System.err.println(e);
		} finally {
			postMethod.releaseConnection();
			if (br != null)
				try {
					br.close();
				} catch (Exception fe) {
				}
		}
	}
}
