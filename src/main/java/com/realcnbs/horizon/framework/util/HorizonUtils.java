package com.realcnbs.horizon.framework.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class HorizonUtils {

    private static final int RANDOM_STRING_LENGTH = 25;

    private static final char[] CHARSET_AZ_09 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

    public static String parseApiKeyFromJwtToken(String jwtSecret, String token) throws Exception {

        Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("horizon")
                .build();
        DecodedJWT jwt = verifier.verify(token);

        String apiKey = jwt.getClaim("api-key").asString();
        if (apiKey.length() != 74) {
            throw new Exception("Invalid key");
        }

        return apiKey;
    }

    public static String getRandomString() {
        return getRandomString(RANDOM_STRING_LENGTH);
    }

    public static String getRandomString(int length) {
        Random random = new SecureRandom();
        char[] result = new char[length];
        for (int i = 0; i < result.length; i++) {
            // picks a random index out of character set > random character
            int randomCharIndex = random.nextInt(CHARSET_AZ_09.length);
            result[i] = CHARSET_AZ_09[randomCharIndex];
        }
        return new String(result);
    }

    public static String sha256Hash(String toHash) throws UnsupportedEncodingException {

        MessageDigest digest = getDigest("SHA-256");
        byte[] hashed = digest.digest(toHash.getBytes(StandardCharsets.UTF_8));

        StringBuilder hexString = new StringBuilder();
        for (byte aHashed : hashed) {
            String hex = Integer.toHexString(0xff & aHashed);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }

        return hexString.toString();
    }

    private static MessageDigest getDigest(String algorithmName) {
        try {
            return MessageDigest.getInstance(algorithmName);
        } catch (NoSuchAlgorithmException e) {
            String msg = "No native '" + algorithmName + "' MessageDigest instance available on the current JVM.";
            throw new IllegalStateException(msg, e);
        }
    }
}