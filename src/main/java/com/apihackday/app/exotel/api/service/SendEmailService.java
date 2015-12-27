package com.apihackday.app.exotel.api.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.stereotype.Service;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

@Service
public class SendEmailService {
	
	private static String URL = "https://api.sendgrid.com/api/mail.send.json";
	private static String APIKEY="Bearer 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";

	public void sendEmail(String to, String from, String body, String fromNum){
		HttpClient client = new HttpClient();
		PostMethod postMethod = new PostMethod(URL);
		String userPass = "imsafe" + ":" + "d63394e50425c6e85f227081c9aa6e030a30829d";
		String encoded = new String(Base64.encode(userPass.getBytes()));
		postMethod.setRequestHeader("Authorization", "Basic " + encoded);

		postMethod.setParameter("from", from);
		postMethod.setParameter("to", to);
		postMethod.setParameter("subject", "SOS - Reply from "+fromNum);
		postMethod.setParameter("text", body);
		
		postMethod.setRequestHeader("Authorization", APIKEY);

		BufferedReader br = null;
		try {
			int returnCode = client.executeMethod(postMethod);

			if (returnCode == HttpStatus.SC_NOT_IMPLEMENTED) {
				// TODO handle errors
				System.err.println("The Post method is not implemented by this URI");
				// still consume the response body
				postMethod.getResponseBodyAsString();
			} else {
				if(returnCode==200){
					System.out.println("Mail sent successfully");
				}
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
