package com.tokiomarine.claim.api;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import java.util.Enumeration;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.tomcat.util.codec.binary.Base64;


public class Test {

	public static void main(String[] args) throws NoSuchAlgorithmException {

         String ansCompPrivateKeyBase64 = "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";
         //String text = "B72EjoXVaqApelK8UkOb5EuQH+RJkfTZWLB3OlIDHZhMkrv6PCWzGS9KWTmLbk+0jVWQTDy+HaJe6sd+ubcyIcUiFUJwwAcIRzIKuq1sWUNE0E1Z/21XUHnIw0yMB7qcqPYtwe2HO6/dRgrHviMZ+Aqzvls2m8GFFz8MHdFL+1hcU6iNaA/jolRdlZhL4RtEAlkl4rO4mpuRHnhioPYKnuVkkTz0bs7DJhJXFYeLfEcgTfAEpZfHZO65gkP/yVybCyc7lBSMX/MOpvgD+EqGi+98aDWU/v2HOAKYFF8z5Azr/Iv03040PgobLJWCRgKPlyQIHkbRiHI3I7spmFotMg==|hgOzZrs6TqJi032K5VncnLvPzxVD26SnimjWrgpQpUWQ6nkiWdfY2Xn+rLuw9dAwP8c/hucX7iYcbi0Aak6rZAn46lkffSu1cpk9N7Q8riiRXgHTMFyeX/oEfcGCXED0iRjUr79PSVkBvGegDPfw7sqyk9TRnaGB/YOI1zf7RJlVA0HolMPnQlJ10vPa9NWXUm/qXchIS8ABKHkWh1RXp5q+KTz1c2o4P1kG0DpPpVFz42w1vBtPv355fV/QhMriBr+sOeCIto7h1YJCCTN1G2TWfkrc4vI3Ghw8cJxfg6k0S8XkW9d6YKwa2Hq2/aPsjxEi3TINQh3D1neVWbQvXA==|";
         String text = "TWVQ7VRqv72bvgtDg85tJLL/ZHC8ovAxVEYNf8uPwnl5hb9gtOYayZJbaihEduzfhqd+Gx57qMAXd8muZXabFnd+ip/KjMZgVhHGa1G6bck5iWiMdXVvAdW3MvV1SzeZOvapZWaTkSAb7JU0IelKtcFJhxF+NL2xWcDxz5JOS5xOlLGtMlgDGCNzIGuHbUV2B6hovXNm0Vzb8HyZGLUgrXdwXVo/ic+D+9euSS17LXRFK+4Qg/L+986AeXz9llhBaNXr9267+korbFMisya2BW3t2TeA0D7/dWdZStAZW+mTERuEHWoAu4Wz1YEJ8C/xJoSTxdelKyfmuYDwKvUYzg==";

         
        
         
         char[] ansCompPrivateKeyPassword = "0472".toCharArray();
         //byte[] decodedBytes = Base64.getDecoder().decode(ansCompPrivateKeyBase64.getBytes());  
         
         byte[] decodedBytes = Base64.decodeBase64(ansCompPrivateKeyBase64.getBytes());
         boolean isAliasWithPrivateKey = false;      
        
         
         try {
        	 
        	// Provide location of Java Keystore and password for access
			KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
			keyStore.load(new ByteArrayInputStream(decodedBytes), ansCompPrivateKeyPassword);
			
			
			// iterate over all aliases
			Enumeration<String> es = keyStore.aliases();
			String alias = "";
			 while (es.hasMoreElements()) {
				 alias = (String) es.nextElement();
				 // if alias refers to a private key break at that point
				 // as we want to use that certificate
				 if (isAliasWithPrivateKey = keyStore.isKeyEntry(alias)) {
					 break;
				 }
				
			 }
			 //Key rsakey = keyStore.getKey(alias, ansCompPrivateKeyPassword);   // Get Private Key		
			
			 if (isAliasWithPrivateKey) {
	
					KeyStore.PrivateKeyEntry pkEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(alias,
							new KeyStore.PasswordProtection(ansCompPrivateKeyPassword));
	
					PrivateKey myPrivateKey = pkEntry.getPrivateKey();				
					
					
					Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
					
			        cipher.init(Cipher.DECRYPT_MODE, myPrivateKey); 
			        	
			        byte[] de = Base64.decodeBase64(text);     
			        

			        byte[] cipherData = cipher.doFinal(de);

			        
			        String decrypted = new String(cipherData);
			        System.out.println(decrypted);
	
			}
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnrecoverableEntryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
         
         

	}
}
