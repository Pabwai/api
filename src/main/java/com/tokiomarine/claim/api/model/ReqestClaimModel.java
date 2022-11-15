package com.tokiomarine.claim.api.model;
import org.springframework.stereotype.Component;

import tokiomarine.service.gen.Condition;
import tokiomarine.service.gen.GetGateWayRequest;

public class ReqestClaimModel {
	
    private String username;
    private String password;
    private String companyCode;
    private String qusCompany;
    private String ansCompany;
    private String serviceCode;
    private Condition serviceCondition;
    private String requestDatetime;   
 
    
	public ReqestClaimModel(GetGateWayRequest getGateWayRequest) { 	
		this.username = getGateWayRequest.getUsername();
		this.password = getGateWayRequest.getPassword();
		this.companyCode = getGateWayRequest.getCompanyCode();
		this.qusCompany = getGateWayRequest.getQusCompany();
		this.ansCompany = getGateWayRequest.getAnsCompany();
		this.serviceCode = getGateWayRequest.getServiceCode();
		this.serviceCondition = getGateWayRequest.getServiceCondition();		
		this.requestDatetime = getGateWayRequest.getRequestDatetime();
		
		new ReqestClaimServiceCondition(serviceCondition);
	}
	
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public String getQusCompany() {
		return qusCompany;
	}
	public String getAnsCompany() {
		return ansCompany;
	}
	public String getServiceCode() {
		return serviceCode;
	}
	public Condition getServiceCondition() {
		return serviceCondition;
	}
	public String getRequestDatetime() {
		return requestDatetime;
	}

    
    
    
}
