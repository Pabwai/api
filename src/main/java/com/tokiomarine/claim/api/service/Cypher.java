package com.tokiomarine.claim.api.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableEntryException;
import java.util.Enumeration;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.apache.tomcat.util.codec.binary.Base64;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;


public class Cypher {	

    public static Key readKeyFromFile(String keyFileName) throws IOException
    {
        Key key = null;
        InputStream inputStream = new FileInputStream(keyFileName);
        ObjectInputStream objectInputStream = new ObjectInputStream(new BufferedInputStream(inputStream));
        try
        {
            BigInteger modulus = (BigInteger) objectInputStream.readObject();
            BigInteger exponent = (BigInteger) objectInputStream.readObject();
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            if (keyFileName.startsWith("public"))
                key = keyFactory.generatePublic(new RSAPublicKeySpec(modulus, exponent));
            else
                key = keyFactory.generatePrivate(new RSAPrivateKeySpec(modulus, exponent));

        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            objectInputStream.close();
        }
        return key;
    }

    public byte[] encrypt(String plainText, PublicKey publicKey) throws Exception
    {
       

        // Get Cipher Instance
        //Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-512ANDMGF1PADDING");
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

        // Initialize Cipher for ENCRYPT_MODE
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        // Perform Encryption
        byte[] cipherText = cipher.doFinal(plainText.getBytes());
        //byte[] cipherText = blockCipher(plainText.getBytes(),Cipher.ENCRYPT_MODE,cipher);

        return cipherText;
    }

    public static String decrypt(byte[] cipherTextArray, String fileName) throws Exception
    {
        Key privateKey = readKeyFromFile("private.key");

        // Get Cipher Instance
		//Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-512ANDMGF1PADDING");
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

        // Initialize Cipher for DECRYPT_MODE
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        // Perform Decryption
        //byte[] decryptedTextArray = cipher.doFinal(cipherTextArray);
        byte[] decryptedTextArray = blockCipher(cipherTextArray,Cipher.DECRYPT_MODE,cipher);


        return new String(decryptedTextArray);
    }

    public static byte[] blockCipher(byte[] bytes, int mode,Cipher cipher ) throws IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException{

        // string initialize 2 buffers.
        // scrambled will hold intermediate results
        byte[] scrambled = new byte[0];
    
        // toReturn will hold the total result
        byte[] toReturn = new byte[0];
        // if we encrypt we use 100 byte long blocks. Decryption requires 128 byte long blocks (because of RSA)
        int length = (mode == Cipher.ENCRYPT_MODE)? 2 : 256;
    
        // another buffer. this one will hold the bytes that have to be modified in this step
        byte[] buffer = new byte[length];
    
        for (int i=0; i< bytes.length; i++){
    
            // if we filled our buffer array we have our block ready for de- or encryption
            if ((i > 0) && (i % length == 0)){
                //execute the operation
                scrambled = cipher.doFinal(buffer);
                // add the result to our total result.
                toReturn = append(toReturn,scrambled);
                // here we calculate the length of the next buffer required
                int newlength = length;
    
                // if newlength would be longer than remaining bytes in the bytes array we shorten it.
                if (i + length > bytes.length) {
                     newlength = bytes.length - i;
                }
                // clean the buffer array
                buffer = new byte[newlength];
            }
            // copy byte into our buffer.
            buffer[i%length] = bytes[i];
        }
    
        // this step is needed if we had a trailing buffer. should only happen when encrypting.
        // example: we encrypt 110 bytes. 100 bytes per run means we "forgot" the last 10 bytes. they are in the buffer array
        scrambled = cipher.doFinal(buffer);
    
        // final step before we can return the modified data.
        toReturn = append(toReturn,scrambled);
    
        return toReturn;
    }

    public static byte[] append(byte[] prefix, byte[] suffix){
        byte[] toReturn = new byte[prefix.length + suffix.length];
        for (int i=0; i< prefix.length; i++){
            toReturn[i] = prefix[i];
        }
        for (int i=0; i< suffix.length; i++){
            toReturn[i+prefix.length] = suffix[i];
        }
        return toReturn;
    }


	public static PublicKey getPublicKey(String publicKeyBase64) throws CertificateException, IOException{		
		
		byte[] decodedBytes = Base64.decodeBase64(publicKeyBase64);        
        java.security.cert.CertificateFactory certFactory = java.security.cert.CertificateFactory.getInstance("X.509");       
        InputStream in = new ByteArrayInputStream(decodedBytes);
        X509Certificate certificate = (X509Certificate)certFactory.generateCertificate(in);        
        
        RSAPublicKey key = (RSAPublicKey)certificate.getPublicKey();       
		return certificate.getPublicKey();
		
	}

	public static PrivateKey getPrivateKey(String privateKeyBase64) {		
		
	
		char[] ansCompPrivateKeyPassword = "0472".toCharArray();
         //byte[] decodedBytes = Base64.getDecoder().decode(ansCompPrivateKeyBase64.getBytes());  
         
         byte[] decodedBytes = Base64.decodeBase64(privateKeyBase64.getBytes());
              
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
			 return (PrivateKey) keyStore.getKey(alias, ansCompPrivateKeyPassword);
			 
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
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}	

    public static void saveKeyToFile(String fileName, BigInteger modulus, BigInteger exponent) throws IOException
    {
        ObjectOutputStream ObjOutputStream = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(fileName)));
        try
        {
            ObjOutputStream.writeObject(modulus);
            ObjOutputStream.writeObject(exponent);
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            ObjOutputStream.close();
        }
    }
	
	//  public PublicKey CertificateFactory(String publicKeyBase64) throws CertificateException {		
		
	// 	byte[] decodedBytes = Base64.getDecoder().decode(publicKeyBase64);        
    //     CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
    //     InputStream in = new ByteArrayInputStream(decodedBytes);
    //     X509Certificate certificate = (X509Certificate)certFactory.generateCertificate(in);
        
	// 	/*
	// 	 * System.out.println("Subject DN : " + certificate.getSubjectDN().getName());
	// 	 * System.out.println("Issuer : " + certificate.getIssuerDN().getName());
	// 	 * System.out.println("Not After: " + certificate.getNotAfter());
	// 	 * System.out.println("Not Before: " + certificate.getNotBefore());
	// 	 * System.out.println("version: " + certificate.getVersion());
	// 	 * System.out.println("serial number : " + certificate.getSerialNumber());
	// 	 */

    //     PublicKey publicKey = certificate.getPublicKey();
        
         
		
		
	// 	return publicKey;
		
	// } 
}
