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
			
		//this.token = getApiToken();
		
		
		return getCondition();
		
	}
	
	public String getApiToken() {
		JSONObject getToken = new JSONObject();			
		getToken.put("username", reqestClaimModel.getUsername());
		getToken.put("password", reqestClaimModel.getPassword());
		getToken.put("companyCode", reqestClaimModel.getCompanyCode());
		
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
	
	
	public String getAnsCompanyPublicKey() {
		
		return "";
	}
	
	
	public String getCondition(){	
		JSONObject condition = new JSONObject();			
		condition.put("tx", "");
		condition.put("qusCompany", reqestClaimModel.getQusCompany());
		condition.put("ansCompany", reqestClaimModel.getAnsCompany());
		condition.put("ServiceCode", reqestClaimModel.getServiceCode());
		condition.put("ServiceCondition", reqestClaimModel.getServiceCondition());
		condition.put("RequestDatetime", reqestClaimModel.getRequestDatetime());
		condition.put("UserRequest", reqestClaimModel.getUsername());
		
		
		
		
		
		
		
	
		
		return condition.toString();
		
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


