package com.tokiomarine.claim.api.controller;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.json.JSONException;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import com.tokiomarine.claim.api.model.ReqestClaimModel;
import com.tokiomarine.claim.api.service.Thumbprint;

import tokiomarine.service.gen.GetGateWayRequest;
import tokiomarine.service.gen.GetGateWayResponse;

@Endpoint
public class WebServiceEndpiont {
	
	

	private static final String NAMESPACE_URI = "http://tokiomarine/service/gen";

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getGateWayRequest")
	@ResponsePayload
	public GetGateWayResponse getCountry(@RequestPayload GetGateWayRequest request) throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, CertificateException, JSONException, IllegalBlockSizeException, BadPaddingException {
		
		ReqestClaimModel reqestClaimModel = new ReqestClaimModel(request);		
		GetGateWayResponse response = new GetGateWayResponse();	
		
		try {
			Thumbprint.disableSSLVerificationForHttps();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		BroadcastQuestion broadcastQuestion = new BroadcastQuestion();
		
		//System.out.println(request.getServiceCondition().toString());
		//String condi = request.getServiceCondition().toString();
		response.setQusCompany(broadcastQuestion.SendingQuestion(reqestClaimModel));
		return response;
	}

}
