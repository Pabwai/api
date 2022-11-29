package com.tokiomarine.claim.api.controller;


import java.io.IOException;
import java.net.MalformedURLException;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.tokiomarine.claim.api.model.ReqestClaimModel;
import com.tokiomarine.claim.api.service.RSAUtil;
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
    private String PublicKeyBase64;

    ReqestClaimModel reqestClaimModel;
  
	public String SendingQuestion(ReqestClaimModel reqestClaimModel) throws IOException{			
		this.reqestClaimModel = reqestClaimModel;
			
		//this.token = getApiToken();
		//token = getApiToken();
		//this.PublicKeyBase64=getAnsCompanyPublicKey();
		//System.out.println(token);		
		String req = getCondition();
		System.out.println(req);
		return req;
		
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
        	return tokenparse.getString("token") ;
        }
	
	}
	
	
	public String getAnsCompanyPublicKey() {		
		
		JSONObject getPublicKey = new JSONObject();			
		getPublicKey.put("companyCode", reqestClaimModel.getCompanyCode());
		
		
		RestRequestHttp send = new RestRequestHttp();
		
		String PublicKeyBase64 = send.getPublicKeyTest(ApiAnsCompanyPublicKey);
		JSONObject publicKeyParse  = new JSONObject(PublicKeyBase64);	
		
		if (PublicKeyBase64.isBlank()||PublicKeyBase64.isEmpty()) {        
			if(publicKeyParse.getString("publicKeyBase64").isEmpty()||publicKeyParse.getString("publicKeyBase64").isBlank()) {
				return "401 - Unauthorized Access is denied" ; 				
			}
            return PublicKeyBase64 ;           
        }else { 
        	return publicKeyParse.getString("publicKeyBase64") ;
        }
	}
	
	
	
	
	
	public String getCondition(){	
		String key = "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";
		String serviceConditionEN = null;
		JSONObject condition = new JSONObject();			
		condition.put("tx", "");
		condition.put("qusCompany", reqestClaimModel.getQusCompany());
		condition.put("ansCompany", reqestClaimModel.getAnsCompany());
		condition.put("ServiceCode", reqestClaimModel.getServiceCode());		
		condition.put("RequestDatetime", reqestClaimModel.getRequestDatetime());
		condition.put("UserRequest", reqestClaimModel.getUsername());
		
		
		
		condition.put("ServiceCondition",serviceConditionEN);
		
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


