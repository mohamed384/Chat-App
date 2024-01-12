package org.example.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHashing {
    public static String hashPassword(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
        BigInteger number = new BigInteger(1, hash);
        String hashedPassword = number.toString(16);
        while (hashedPassword.length() < 32) {
            hashedPassword = "0" + hashedPassword;
        }
        return hashedPassword;
    }

    public boolean passwordMatch(String password , String hashedPassword) {
        return hashPassword(password).equals(hashedPassword);
    }
}
