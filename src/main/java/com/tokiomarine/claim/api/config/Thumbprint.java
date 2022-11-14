package com.tokiomarine.claim.api.config;


import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.bind.DatatypeConverter;

import org.apache.tomcat.util.codec.binary.Base64;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.KeyManagementException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;


public class Thumbprint {	
	
	private static String privateKey = "src/main/resources/www-tmsthws4-com.pem";
	
    public static void X509() throws CertificateException, IOException, NoSuchAlgorithmException {
        X509Certificate certObject = getCertObject(privateKey);
        System.out.println(getThumbprint(certObject));
    }

    public static X509Certificate getCertObject(String filePath) throws IOException, CertificateException {
        try (FileInputStream is = new FileInputStream(filePath)) {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            return (X509Certificate) certificateFactory.generateCertificate(is);
        }
    }

    private static String getThumbprint(X509Certificate cert) throws NoSuchAlgorithmException, CertificateEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(cert.getEncoded());
        return DatatypeConverter.printHexBinary(md.digest()).toLowerCase();
    }
    
    public static RSAPublicKey readPublicKey() throws Exception {
        String key = new String(Files.readAllBytes( (Path) new FileInputStream(privateKey)), Charset.defaultCharset());

        String publicKeyPEM = key
          .replace("-----BEGIN PUBLIC KEY-----", "")
          .replaceAll(System.lineSeparator(), "")
          .replace("-----END PUBLIC KEY-----", "");

        byte[] encoded = Base64.decodeBase64(publicKeyPEM);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }
    
    
    public static void disableSSLVerificationForHttps() throws NoSuchAlgorithmException, KeyManagementException {
    	TrustManager[] trustAllCerts = new TrustManager[] {
	        new X509TrustManager() {
	          public java.security.cert.X509Certificate[] getAcceptedIssuers() {
	            return null;
	          }

	          public void checkClientTrusted(X509Certificate[] certs, String authType) {  }

	          public void checkServerTrusted(X509Certificate[] certs, String authType) {  }

	        }
	    };
    	SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        
        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
              return true;
            }
        };
          
       // Install the all-trusting host verifier
       HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    }
    
}

