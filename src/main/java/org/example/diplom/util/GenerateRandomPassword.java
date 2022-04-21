package org.example.diplom.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.security.SecureRandom;

public class GenerateRandomPassword {
    public static String generateRandomPassword() {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }
        return sb.toString();
    }

    public static String toMD5(String password) {
        String md5Hex = DigestUtils.md5Hex(password);

        return md5Hex;
    }
}
