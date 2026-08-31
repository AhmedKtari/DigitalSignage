package io.github.ahmedktarii.digitalsingage.Utils;

import java.security.MessageDigest;

public class FileHashUtil {
    // a method that recive a fileBytes and convert it to hash String unique for every media 
    public static String computeFileHash(byte[] fileBytes) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(fileBytes);

        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}