package com.tokiomarine.claim.api.service;


import java.security.MessageDigest;
import javax.xml.bind.DatatypeConverter;

public class SHA256 {


    public static String getHast(String data) {
         
        SHA256 sj = new SHA256();
        String hash = sj.getSHA256Hash(data);
        System.out.println("The SHA256 (hexadecimal encoded) hash is:"+hash);

        return hash;
    }
 
    /**
     * Returns a hexadecimal encoded SHA-256 hash for the input String.
     * @param data
     * @return 
     */
    private String getSHA256Hash(String data) {
        String result = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes("UTF-8"));
            return bytesToHex(hash); // make it printable
        }catch(Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
     
    /**
     * Use javax.xml.bind.DatatypeConverter class in JDK to convert byte array
     * to a hexadecimal string. Note that this generates hexadecimal in upper case.
     * @param hash
     * @return 
     */
    private String  bytesToHex(byte[] hash) {
        return DatatypeConverter.printHexBinary(hash);
    }
    
}
