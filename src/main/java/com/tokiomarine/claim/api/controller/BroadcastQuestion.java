package com.tokiomarine.claim.api.controller;



import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import com.tokiomarine.claim.api.model.ReqestClaimModel;
import com.tokiomarine.claim.api.service.Cypher;
import com.tokiomarine.claim.api.service.RestRequestHttp;

@Component
public class BroadcastQuestion {

	
    private String ApiToken = "https://api-test.insure.co.th/token/";
    private String ApiBroadcast = "https://api-test.insure.co.th/QuestionService/BroadcastQuestion/";
    private String ApiBroadcastOffline = "https://api-test.insure.co.th/QuestionService/OfflineBroadcastQuestion/";
    private String ApiAnsCompanyPublicKey = "https://api-test.insure.co.th/PKI/PublicKey";  	
 	ReqestClaimModel reqestClaimModel;
  
	public String SendingQuestion(ReqestClaimModel reqestClaimModel) throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, CertificateException, JSONException, IllegalBlockSizeException, BadPaddingException{			
		this.reqestClaimModel = reqestClaimModel;
			
		
		//this.PublicKeyBase64=getAnsCompanyPublicKey();
		//System.out.println(token);	
		String publicKeyRequest = 	getAnsCompanyPublicKey();	

		return new RestRequestHttp().CreateRequst(ApiBroadcastOffline,getApiToken(),getCondition(publicKeyRequest));
		
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
        	return  publicKeyParse.getString("publicKeyBase64") ;
        }
	}
	
	
	public JSONObject getCondition(String key) {
	
		
		JSONObject condition = new JSONObject();			
		condition.put("tx","");
		condition.put("qusCompany", reqestClaimModel.getQusCompany());
		condition.put("ansCompany", reqestClaimModel.getAnsCompany());
		condition.put("ServiceCode", reqestClaimModel.getServiceCode());		
		condition.put("RequestDatetime", reqestClaimModel.getRequestDatetime());
		condition.put("UserRequest", reqestClaimModel.getUsername());

		Cypher cypher = new Cypher();
		
		try {
			PublicKey publicKey = Cypher.getPublicKey(key);
			
			//System.out.println("Original Text  : " + reqestClaimModel.getServiceCondition());

			// Encryption
			byte[] cipherTextArray = cypher.encrypt(reqestClaimModel.getServiceCondition(),publicKey);
			String encryptedText = Base64.encodeBase64String(cipherTextArray);
			//System.out.println("Encrypted Text : " + encryptedText);

			condition.put("ServiceCondition", encryptedText);
		
			//byte[] servicebyte = reqestClaimModel.getServiceCondition().getBytes();   


		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
		
		return condition;		
	}	

}


