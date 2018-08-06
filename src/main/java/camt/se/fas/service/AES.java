package camt.se.fas.service;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.Key;

public class AES {
    private static final String ALGO = "AES";
    private static final byte[] keyValue =
            new byte[]{'F', 'a', 'c', 'i', 'a', 'l', 'A', 'u', 't', 'h', 'e', 'n', 'C', 'A', 'M', 'T'};


    public static String encrypt(String data){
        try {
            Key key = generateKey();
            Cipher c = Cipher.getInstance(ALGO);
            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] encVal = c.doFinal(data.getBytes());
            String encryptedValue = new BASE64Encoder().encode(encVal);
            String urlEncodeddata = URLEncoder.encode(encryptedValue, "UTF-8");
            return urlEncodeddata;
        }catch (Exception e){
            return null;
        }
    }
    public static String decrypt(String encryptedData) {
        try {
            Key key = generateKey();
            Cipher c = Cipher.getInstance(ALGO);
            c.init(Cipher.DECRYPT_MODE, key);
            String urlDecodedData = URLDecoder.decode(encryptedData, "UTF-8");
            byte[] decordedValue = new BASE64Decoder().decodeBuffer(urlDecodedData);
            byte[] decValue = c.doFinal(decordedValue);
            String decryptedValue= new String(decValue);
            return decryptedValue;
        }catch (Exception e){
            e.getMessage();
            return null;
        }

    }

    private static Key generateKey() throws Exception {
        return new SecretKeySpec(keyValue, ALGO);
    }

}
