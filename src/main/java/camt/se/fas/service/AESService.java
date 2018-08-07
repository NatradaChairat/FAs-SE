package camt.se.fas.service;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;

public interface AESService {
    String encrypt(String data);
    String decrypt(String encryptedData);
    Key generateKey() throws Exception;
}
