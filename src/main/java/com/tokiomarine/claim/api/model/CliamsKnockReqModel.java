package com.tokiomarine.claim.api.model;

import org.springframework.beans.factory.annotation.Autowired;

import tokiomarine.service.gen.Condition;

public class CliamsKnockReqModel {

    protected String insurerIdRq;
    protected String transactionRequestDtRq;
    protected String registrationRq;
    protected String paymentAmtRq;
    protected String userRq;
    protected String insurerIdRs;
    protected String policyNumberRs;
    protected String claimsOccurenceRs;
    protected String chassisSerialNumberRs;
    
    
    private Condition condition;

	@Autowired
	public CliamsKnockReqModel (Condition condition) {
		this.condition = condition;	
		
	}

	public CliamsKnockReqModel() {		
		this.insurerIdRq = condition.getInsurerIdRq();
		this.transactionRequestDtRq = condition.getTransactionRequestDtRq();
		this.registrationRq = condition.getRegistrationRq();
		this.paymentAmtRq = condition.getPaymentAmtRq();
		this.userRq = condition.getUserRq();
		this.insurerIdRs = condition.getInsurerIdRq();
		this.policyNumberRs = condition.getPolicyNumberRs();
		this.claimsOccurenceRs = condition.getClaimsOccurenceRs();
		this.chassisSerialNumberRs = condition.getChassisSerialNumberRs();	
		
	}

	public String getInsurerIdRq() {
		return insurerIdRq;
	}

	public String getTransactionRequestDtRq() {
		return transactionRequestDtRq;
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

	public String getChassisSerialNumberRs() {
		return chassisSerialNumberRs;
	}

	public Condition getCondition() {
		return condition;
	}

	@Override
	public String toString() {
		return "[\\insurerIdRq\\:\\" + insurerIdRq + "\\, transactionRequestDtRq\\:\\"
				+ transactionRequestDtRq + "\\, registrationRq\\:\\" + registrationRq + "\\, paymentAmtRq\\:\\"
				+ paymentAmtRq + "\\, userRq\\:\\" + userRq + "\\, insurerIdRs\\:\\" + insurerIdRs
				+ "\\, policyNumberRs\\:\\" + policyNumberRs + "\\, claimsOccurenceRs\\:\\" + claimsOccurenceRs
				+ "\\, chassisSerialNumberRs\\:\\" + chassisSerialNumberRs + "\\, condition\\:\\" + condition + "]";
	}
	
	
	
	
    
    

}
