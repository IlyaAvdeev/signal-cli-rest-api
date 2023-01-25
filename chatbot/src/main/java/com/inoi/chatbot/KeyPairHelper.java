package com.inoi.chatbot;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public class KeyPairHelper {

    private final static String KEY_ALGORITHM = "EC";
    private final static String SIGNATURE_ALGORITHM = "SHA256withECDSA";
    private final KeyPair keyPair;

    public KeyPairHelper() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPair = keyPairGenerator.generateKeyPair();
    }

    public String getEncodedPublicKey() {
        byte[] encoded = keyPair.getPublic().getEncoded();
        return Base64.getEncoder().encodeToString(encoded);
    }

    public String getEncodedPrivateKey() {
        byte[] encoded = keyPair.getPrivate().getEncoded();
        return Base64.getEncoder().encodeToString(encoded);
    }

    public String sign(String message) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(keyPair.getPrivate(), new SecureRandom());
        byte[] messageData = message.getBytes(StandardCharsets.UTF_8);
        signature.update(messageData);
        byte[] digitalSignature = signature.sign();
        return Base64.getEncoder().encodeToString(digitalSignature);
    }
}