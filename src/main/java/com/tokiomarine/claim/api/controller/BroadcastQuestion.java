package com.tokiomarine.claim.api.controller;


import java.io.IOException;
import java.net.MalformedURLException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.tokiomarine.claim.api.model.ReqestClaimModel;
import com.tokiomarine.claim.api.service.RestRequestHttp;
import com.tokiomarine.claim.api.service.Thumbprint;
import tokiomarine.service.gen.Condition;
import tokiomarine.service.gen.GetGateWayRequest;
@Component
public class BroadcastQuestion {

	
    private String ApiToken = "https://api-test.insure.co.th/token/";
    private String ApiBroadcast = "https://api-test.insure.co.th/QuestionService/BroadcastQuestion/";
    private String ApiBroadcastOffline = "https://api-test.insure.co.th/QuestionService/OfflineBroadcastQuestion/";
    private String ApiAnsCompanyPublicKey = "https://api-test.insure.co.th/PKI/PublicKey";   
    private String token;
    

    ReqestClaimModel reqestClaimModel;
  
	public String SendingQuestion(ReqestClaimModel reqestClaimModel) throws IOException{			
		this.reqestClaimModel = reqestClaimModel;
			
		this.token = getApiToken();
		
		
		return "ok";
		
	}
	
	public String getApiToken() {
		Map<String, String> map = new HashMap<>();
		map.put("username", reqestClaimModel.getUsername());
		map.put("password", reqestClaimModel.getPassword());
		map.put("companyCode", reqestClaimModel.getCompanyCode());
		JSONObject getToken = new JSONObject(map);			
		
		RestRequestHttp send = new RestRequestHttp();
		
		String token = send.RequestTokenApi(ApiToken, getToken);
		JSONObject tokenparse  = new JSONObject(token);	
		
		if (tokenparse.getString("token").isEmpty()||tokenparse.getString("token").isBlank()){        
			if(token.isBlank()||token.isEmpty()) {
				return "401 - Unauthorized Access is denied" ; 				
			}
            return token ;           
        }else {
            token = tokenparse.getString("token");
        	return token ;
        }
	
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
		
		Condition condition = new Condition();
		
		Map<String, String> map = new HashMap<>();
		map.put("TX", "null");
		map.put("qusCompany", data.getQusCompany());
		map.put("ansCompany", data.getAnsCompany());
		map.put("ServiceCode", data.getServiceCode());
		map.put("ServiceCondition", condition.toString());
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
		con.setRequestProperty("Authorization","Bearer "+token);
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


