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
import com.tokiomarine.claim.api.model.ReqestClaimServiceCondition;
import com.tokiomarine.claim.api.service.Cypher;
import com.tokiomarine.claim.api.service.RestRequestHttp;
import com.tokiomarine.claim.api.service.SHA256;

@Component
public class BroadcastQuestion {

	
    private String ApiToken = "https://api-test.insure.co.th/token/";
	//private String ApiToken = "https://tgiagateway.herokuapp.com/token";
    private String ApiBroadcast = "https://api-test.insure.co.th/QuestionService/BroadcastQuestion/";
	//private String ApiBroadcast = "https://tgiagateway.herokuapp.com/QuestionService/BroadcastQuestion";
    private String ApiBroadcastOffline = "https://api-test.insure.co.th/QuestionService/OfflineBroadcastQuestion/";
    private String ApiAnsCompanyPublicKey = "https://api-test.insure.co.th/PKI/PublicKey";  	
 	ReqestClaimModel reqestClaimModel;
  
	public String SendingQuestion(ReqestClaimModel reqestClaimModel) throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, CertificateException, JSONException, IllegalBlockSizeException, BadPaddingException{			
		this.reqestClaimModel = reqestClaimModel;
			
		
		//this.PublicKeyBase64=getAnsCompanyPublicKey();
		//System.out.println(token);	
		String publicKeyRequest = 	getAnsCompanyPublicKey();	

		return new RestRequestHttp().CreateRequst(ApiBroadcast,getApiToken(),getCondition(publicKeyRequest));
		
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
		
		Cypher cypher = new Cypher();
		//System.out.println(new String(Base64.decodeBase64("CiM5zqtsThj7D4d97I4tK41PFtmV7VYk/SfuUJyCWu9KBr4zTKNFc19zryAGnqA+gyGRpdPbXxE3br8pN3Q0N/lXu9BDxZdTOfmaFhjNiRUuh+Z8vFhy9bm6eInoJitNLrS0uACtyo52DNNqofs0EJMczA75bNGwYfhFxA/bIArfjR11GxmRz5N5Rsj91NHNBKAsBqCKHvC3oDwoIru7MpG7nKPDA/za5sI+mTgPvE6/iehYlaAs1djNlvq73PLpQubAcca9xGUXzC9CHQ6QaeiQQ9QtGgHBATM1XPlkKCJcYhALgk5Dt4MoqEStnrmlh1zi6MMYmaRmXa9G5YP7sA==|iwk/35VrCzzd0ldB5h2xZb3vBNNPYqgSIXBLfmudtDB68Ix9BZmShReFkX6bUJLbF+fAVcyDr1grreNeVGhwebohS7yp40SkO3wAkW3upc//LBgI7Fx6ssKkiAafQ5S/78t0PIGf18kVgGlQ3ylbGT63AsyVFawsb7Hq2/QIaYUtj2lPCK5D+3gA/pqTWwDXnMhc5GIebs3PS32hcgyvz+Dr7pCOh/IVS5R9KiepizJeN8D6RvlwF2xd3TPbQiKaJufSZWeVRvwu/WFGOliyauTZxoHXBbvz9uO+Ai5GN7sfd/1YfHum3qY5dKzbkt6c2SgxdNXchtdOrDZRvNIA9A==|lR1dlcdc14lT89mavVyKTtvx7d8KJw9khH/t1VlGSon8LEtjbVEXDeVKxFylqQwPqleTEbIV++/SGtJ8z1ayAdjXYfhm1ZTJWxW552yMd/pUmWExmNutFQXh1acug+t4JVsmPucEFf8F+YvKG+leJ9BOfLHd6KU9MGl5WVYWR0YCM1PgObhyKaERx+3vA+XUzhyU9PqftgRMh1kR0d7vvQ0V88+2jXdL5YV43x0GuQ24CUB/aZ6rAqDwu574M8cNJGSnEo9IFd9CTG94cP7sv3mwMiPJBArDF5AslZ4Rg+50Xq/nu3M5DuqMlTpcFUPaJXDnkYKKQ45EzODb2xoTGA==|")).length());
		//System.out.println(new String(Base64.decodeBase64("CiM5zqtsThj7D4d97I4tK41PFtmV7VYk/SfuUJyCWu9KBr4zTKNFc19zryAGnqA+gyGRpdPbXxE3br8pN3Q0N/lXu9BDxZdTOfmaFhjNiRUuh+Z8vFhy9bm6eInoJitNLrS0uACtyo52DNNqofs0EJMczA75bNGwYfhFxA/bIArfjR11GxmRz5N5Rsj91NHNBKAsBqCKHvC3oDwoIru7MpG7nKPDA/za5sI+mTgPvE6/iehYlaAs1djNlvq73PLpQubAcca9xGUXzC9CHQ6QaeiQQ9QtGgHBATM1XPlkKCJcYhALgk5Dt4MoqEStnrmlh1zi6MMYmaRmXa9G5YP7sA==|iwk/35VrCzzd0ldB5h2xZb3vBNNPYqgSIXBLfmudtDB68Ix9BZmShReFkX6bUJLbF+fAVcyDr1grreNeVGhwebohS7yp40SkO3wAkW3upc//LBgI7Fx6ssKkiAafQ5S/78t0PIGf18kVgGlQ3ylbGT63AsyVFawsb7Hq2/QIaYUtj2lPCK5D+3gA/pqTWwDXnMhc5GIebs3PS32hcgyvz+Dr7pCOh/IVS5R9KiepizJeN8D6RvlwF2xd3TPbQiKaJufSZWeVRvwu/WFGOliyauTZxoHXBbvz9uO+Ai5GN7sfd/1YfHum3qY5dKzbkt6c2SgxdNXchtdOrDZRvNIA9A==|lR1dlcdc14lT89mavVyKTtvx7d8KJw9khH/t1VlGSon8LEtjbVEXDeVKxFylqQwPqleTEbIV++/SGtJ8z1ayAdjXYfhm1ZTJWxW552yMd/pUmWExmNutFQXh1acug+t4JVsmPucEFf8F+YvKG+leJ9BOfLHd6KU9MGl5WVYWR0YCM1PgObhyKaERx+3vA+XUzhyU9PqftgRMh1kR0d7vvQ0V88+2jXdL5YV43x0GuQ24CUB/aZ6rAqDwu574M8cNJGSnEo9IFd9CTG94cP7sv3mwMiPJBArDF5AslZ4Rg+50Xq/nu3M5DuqMlTpcFUPaJXDnkYKKQ45EzODb2xoTGA==|")));
		//System.out.println("{\"InsurerIdRq\":\"TID4\",\"TransactionRequestDtRq\":\"2565-12-06\",\"LossDtRq\":\"2565-12-06\",\"PolicyNumberRq\":\"PO/ATID4-21-000001\",\"PolicyTypeCdRq\":\"1\",\"ClaimsOccurenceRq\":\"CL/ATID4-21-000001\",\"RegistrationRq\":\"5กณ2519\",\"PaymentAmtRq\":0.0,\"UserRq\":\"TID4\",\"InsurerIdRs\":\"TID2\",\"PolicyNumberRs\":\"\",\"ClaimsOccurenceRs\":\"\",\"RegistrationRs\":\"กท\",\"ChassisSerialNumberRs\":\"\"}".getBytes().length);
		try {
			
			PublicKey publicKey = Cypher.getPublicKey(key);
			
			
			//System.out.println("Original Text  : " + reqestClaimModel.getServiceCondition());
			// Encryption
			
			
			byte[] cipherTextArray = cypher.encrypt(SHA256.getHast(reqestClaimModel.getServiceCondition()),publicKey);			
			String encryptedText = Base64.encodeBase64String(cipherTextArray);
			//System.out.println("Encrypted Text : " + encryptedText);

			//condition.put("tx","");
			condition.put("qusCompany", reqestClaimModel.getQusCompany());
			condition.append("ansCompany", reqestClaimModel.getAnsCompany());
			condition.put("ServiceCode", reqestClaimModel.getServiceCode());		
			condition.put("ServiceCondition", encryptedText);
			condition.put("RequestDatetime", reqestClaimModel.getRequestDatetime());
			condition.put("UserRequest", reqestClaimModel.getUsername());			
			
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
		//System.out.println(condition.toString());
		
		return condition;		
	}	

}


