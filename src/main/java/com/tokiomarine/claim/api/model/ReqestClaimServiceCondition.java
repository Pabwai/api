package com.tokiomarine.claim.api.model;

import org.springframework.stereotype.Component;

import tokiomarine.service.gen.Condition;


@Component
public class ReqestClaimServiceCondition {

    private String insurerIdRq;
    private String transactionRequestDtRq;
    private String lostDtRq;
    private String policyNumberRq;
    private String policyTypeCdRq;
    private String claimsOccurenceRq;
    private String registrationRq;
    private String paymentAmtRq;
    private String userRq;
    private String insurerIdRs;
    private String policyNumberRs;
    private String claimsOccurenceRs;
    private String registratinRs;
    private String chassisSerialNumberRs;
    public ReqestClaimServiceCondition() {
    	
    }
    
    
	public ReqestClaimServiceCondition(Condition condition) {
		this.insurerIdRq = condition.getInsurerIdRq();
		this.transactionRequestDtRq = condition.getTransactionRequestDtRq();
		this.lostDtRq = condition.getLostDtRq();
		this.policyNumberRq = condition.getPolicyNumberRq();
		this.policyTypeCdRq = condition.getPolicyTypeCdRq();
		this.claimsOccurenceRq = condition.getClaimsOccurenceRq();
		this.registrationRq = condition.getRegistrationRq();
		this.paymentAmtRq = condition.getPaymentAmtRq();
		this.userRq = condition.getUserRq();
		this.insurerIdRs = condition.getInsurerIdRs();
		this.policyNumberRs = condition.getPolicyNumberRs();
		this.claimsOccurenceRs = condition.getClaimsOccurenceRs();
		this.registratinRs = condition.getRegistratinRs();
		this.chassisSerialNumberRs = condition.getChassisSerialNumberRs();
	}



	public String getInsurerIdRq() {
		return insurerIdRq;
	}



	public String getTransactionRequestDtRq() {
		return transactionRequestDtRq;
	}



	public String getLostDtRq() {
		return lostDtRq;
	}



	public String getPolicyNumberRq() {
		return policyNumberRq;
	}



	public String getPolicyTypeCdRq() {
		return policyTypeCdRq;
	}



	public String getClaimsOccurenceRq() {
		return claimsOccurenceRq;
	}



	public String getRegistrationRq() {
		return registrationRq;
	}



	public String getPaymentAmtRq() {
		return paymentAmtRq;
	}



	public String getUserRq() {
		return userRq;
	}



	public String getInsurerIdRs() {
		return insurerIdRs;
	}



	public String getPolicyNumberRs() {
		return policyNumberRs;
	}



	public String getClaimsOccurenceRs() {
		return claimsOccurenceRs;
	}



	public String getRegistratinRs() {
		return registratinRs;
	}


	@Override
	public String toString() {
		return "\"{\\\"insurerIdRq\\\":\\\"" + insurerIdRq + "\\\",\\\"transactionRequestDtRq\\\":\\\""
				+ transactionRequestDtRq + "\\\",\\\"lostDtRq\\\":\\\"" + lostDtRq + "\\\",\\\"policyNumberRq\\\":\\\""
				+ policyNumberRq + "\\\",\\\"policyTypeCdRq\\\":\\\"" + policyTypeCdRq
				+ "\\\",\\\"claimsOccurenceRq\\\":\\\"" + claimsOccurenceRq + "\\\",\\\"registrationRq\\\":\\\""
				+ registrationRq + "\\\",\\\"paymentAmtRq\\\":\\\"" + paymentAmtRq + "\\\",\\\"userRq\\\":\\\"" + userRq
				+ "\\\",\\\"insurerIdRs\\\":\\\"" + insurerIdRs + "\\\",\\\"policyNumberRs\\\":\\\"" + policyNumberRs
				+ "\\\",\\\"claimsOccurenceRs\\\":\\\"" + claimsOccurenceRs + "\\\",\\\"registratinRs\\\":\\\""
				+ registratinRs + "\\\",\\\"chassisSerialNumberRs\\\":\\\"" + chassisSerialNumberRs + "\\\"}\",\n\n";
	} 
	
	
	
	
	


	


	


	
	
    

}
