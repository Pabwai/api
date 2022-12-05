package com.tokiomarine.claim.api.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;

public class LoadKeyPublicKeyAns {

	
	
	
	public PublicKey CertificateFactory(String publicKeyBase64) throws CertificateException {		
		
		byte[] decodedBytes = Base64.getDecoder().decode(publicKeyBase64);        
        CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
        InputStream in = new ByteArrayInputStream(decodedBytes);
        X509Certificate certificate = (X509Certificate)certFactory.generateCertificate(in);
        
		/*
		 * System.out.println("Subject DN : " + certificate.getSubjectDN().getName());
		 * System.out.println("Issuer : " + certificate.getIssuerDN().getName());
		 * System.out.println("Not After: " + certificate.getNotAfter());
		 * System.out.println("Not Before: " + certificate.getNotBefore());
		 * System.out.println("version: " + certificate.getVersion());
		 * System.out.println("serial number : " + certificate.getSerialNumber());
		 */

        PublicKey publicKey = certificate.getPublicKey();
        
         
		
		
		return publicKey;
		
	}
}
