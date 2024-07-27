package com.example.project;

import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class EncryptionService {

    public String encrypt(String input) {
        MessageDigest md = null;
        int retryCount = 0;
        while (md == null && retryCount < 5) {
            try {
                md = MessageDigest.getInstance("SHA-256");
            }catch (NoSuchAlgorithmException e){
                System.err.println("Error: SHA-256 algorithm not found. Please check your JDK.");
                System.out.println("Retrying...");
                retryCount++;
            }
        }
        if (md == null) {
            System.err.println("Error: SHA-256 algorithm not found. Please check your JDK.");
            return "ERROR";
        }
        md.update(input.getBytes());
        return bytesToHex(md.digest());
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
}
