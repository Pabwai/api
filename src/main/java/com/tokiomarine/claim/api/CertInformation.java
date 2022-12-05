package com.tokiomarine.claim.api;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

import java.security.PublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;


public class CertInformation {
    static String privateKeyString = "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";
	static String publicKeysString = "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";
    static String plainText  = "{\"InsurerIdRq\":\"TID4\",\"TransactionRequestDtRq\":\"2565-12-02\",\"LossDtRq\":\"2565-12-02\",\"PolicyNumberRq\":\"PO/ATID4-21-000001\",\"PolicyTypeCdRq\":\"1\",\"ClaimsOccurenceRq\":\"CL/ATID4-21-000001\",\"RegistrationRq\":\"5กณ2519\",\"PaymentAmtRq\":0.0,\"UserRq\":\"TID4\",\"InsurerIdRs\":\"TID2\",\"PolicyNumberRs\":\"\",\"ClaimsOccurenceRs\":\"\",\"RegistrationRs\":\"\",\"ChassisSerialNumberRs\":\"\"}";
    //static String plainText  = "Hello";


    
    public static void main(String[] args) throws Exception
    {
        // Get an instance of the RSA key generator
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);

        // Generate the KeyPair
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // Get the public and private key
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        //PublicKey publicKey = getPublicKey(publicKeysString);
        //PrivateKey privateKey = getPrivateKey(privateKeyString);

        // Get the RSAPublicKeySpec and RSAPrivateKeySpec
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		RSAPublicKeySpec publicKeySpec = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
        RSAPrivateKeySpec privateKeySpec = keyFactory.getKeySpec(privateKey, RSAPrivateKeySpec.class);
        
        // Saving the Key to the file
        saveKeyToFile("public.key", publicKeySpec.getModulus(), publicKeySpec.getPublicExponent());
        saveKeyToFile("private.key", privateKeySpec.getModulus(), privateKeySpec.getPrivateExponent());

        System.out.println("Original Text  : " + plainText);

        // Encryption
        byte[] cipherTextArray = encrypt(plainText, "D:\\VSworkspeec\\api\\public.key");
        String encryptedText = Base64.encodeBase64String(cipherTextArray);
        System.out.println("Encrypted Text : " + encryptedText);

        // Decryption
        String decryptedText = decrypt(cipherTextArray, "D:\\VSworkspeec\\api\\private.key");
        System.out.println("DeCrypted Text : " + decryptedText);

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

    public static byte[] encrypt(String plainText, String fileName) throws Exception
    {
        Key publicKey = readKeyFromFile("public.key");

        // Get Cipher Instance
        //Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-512ANDMGF1PADDING");
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

        // Initialize Cipher for ENCRYPT_MODE
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        // Perform Encryption
        //byte[] cipherText = cipher.doFinal(plainText.getBytes());
        byte[] cipherText = blockCipher(plainText.getBytes(),Cipher.ENCRYPT_MODE,cipher);

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
        int length = (mode == Cipher.ENCRYPT_MODE)? 245 : 256;
    
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


	public static PublicKey getPublicKey(String publicKeyBase64) throws CertificateException {		
		
		byte[] decodedBytes = Base64.decodeBase64(publicKeyBase64);        
        java.security.cert.CertificateFactory certFactory = java.security.cert.CertificateFactory.getInstance("X.509");
        InputStream in = new ByteArrayInputStream(decodedBytes);
        X509Certificate certificate = (X509Certificate)certFactory.generateCertificate(in);    		
		
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



}
