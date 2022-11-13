package com.tokiomarine.claim.api.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


import tokiomarine.service.gen.GetGateWayRequest;
import tokiomarine.service.gen.GetGateWayResponse;

@Endpoint
public class WebServiceEndpiont {
	
	@Autowired
	BroadcastQuestion broadcastQuestion;	
	
	private static final String NAMESPACE_URI = "http://tokiomarine/service/gen";

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getGateWayRequest")
	@ResponsePayload
	public GetGateWayResponse getCountry(@RequestPayload GetGateWayRequest request) throws IOException {
		GetGateWayResponse response = new GetGateWayResponse();	
		
		String token = broadcastQuestion.SendingQuestion(request);		
		//CliamsKnockReqModel s = new CliamsKnockReqModel();
		
		//System.out.println(s.toString());		
		response.setTx(token);
		return response;
	}

}
