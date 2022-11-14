package com.tokiomarine.claim.api.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.tokiomarine.claim.api.config.Thumbprint;

import tokiomarine.service.gen.GetGateWayRequest;

@Component
public class BroadcastQuestion {

	
    String ApiToken = "https://api-test.insure.co.th/token/";
    String ApiBroadcast = "https://api-test.insure.co.th/QuestionService/BroadcastQuestion/";
    String ApiBroadcastOffline = "https://api-test.insure.co.th/QuestionService/OfflineBroadcastQuestion/";
    String ApiAnsCompanyPublicKey = "https://api-test.insure.co.th/PKI/PublicKey";
    
	public String SendingQuestion(GetGateWayRequest data ) throws IOException{		
		
		try {
			Thumbprint.disableSSLVerificationForHttps();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		Map<String, String> map = new HashMap<>();
		map.put("username", data.getUsername());
		map.put("password", data.getPassword());
		map.put("companyCode", data.getCompanyCode());
		JSONObject users = new JSONObject(map);
		
		return getCondition(data,"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6IkdXVXNyVGVzdC05OTk0fDEwLjExMC4xLjExMSIsIm5iZiI6MTY2ODQwODA0NywiZXhwIjoxNjY4NDA4MjI3LCJpYXQiOjE2Njg0MDgwNDd9.2Tx7P6J2M3xSkHrB_Otnom1pLCMzsXXOVKX2kFU-lSQ");
		/*
		HttpURLConnection con = (HttpURLConnection) new URL(ApiToken).openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type","application/json");
		
		// For POST only - START
		con.setDoOutput(true);
		OutputStream os = con.getOutputStream();
		os.write(users.toString().getBytes());
		os.flush();
		os.close();
		// For POST only - END

		int responseCode = con.getResponseCode();
		System.out.println("POST Response Code :: " + responseCode);

		if (responseCode == HttpURLConnection.HTTP_OK) { //success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			System.out.println(response.toString());
			
			
			getCondition(data,response.toString());
			
			
			return response.toString();
		} else {
			System.out.println("POST request not worked");
			return "POST request not worked";
		}
		*/
	}
	
	public String getCondition(GetGateWayRequest data,String token ) throws MalformedURLException, IOException {
		
		try {
			Thumbprint.disableSSLVerificationForHttps();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		Map<String, String> map = new HashMap<>();
		map.put("qusCompany", data.getQusCompany());
		map.put("ansCompany", data.getAnsCompany());
		map.put("ServiceCode", data.getServiceCode().toCharArray().toString());
		map.put("ServiceCondition", data.getServiceCondition().toString());
		map.put("RequestDatetime", data.getCompanyCode());
		map.put("UserRequest", data.getUsername());
		JSONObject users = new JSONObject(map);
		//JSONObject obj = new JSONObject(token);
		//String auth = obj.getString("token");
		
		System.out.println(map.toString());

		
		return map.toString();
		
		/*
		HttpURLConnection con = (HttpURLConnection) new URL(ApiBroadcastOffline).openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Authorization","Bearer " + auth);
		con.setRequestProperty("Content-Type","application/json");
		
		// For POST only - START
		con.setDoOutput(true);
		OutputStream os = con.getOutputStream();
		os.write(users.toString().getBytes());
		os.flush();
		os.close();
		// For POST only - END

		int responseCode = con.getResponseCode();
		System.out.println("POST Response Code :: " + responseCode);

		if (responseCode == HttpURLConnection.HTTP_OK) { //success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			System.out.println(response.toString());
			return response.toString();
		} else {
			System.out.println("POST request not worked");
			return "POST request not worked";
		}	
		
		*/
		
	}
	

}


