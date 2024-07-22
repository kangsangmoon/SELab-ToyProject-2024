package com.example.project;

import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class CookieService {

    public Cookie loginCookieMaker(String id, String password) throws NoSuchAlgorithmException {

        String encryptId = encrypt(id);
        String encryptPassword = encrypt(password);

        Cookie cookie = new Cookie("LoginCookie", String.format("%s||%s", encryptId, encryptPassword));
        cookie.setMaxAge(1800);//쿠키 유지 시간 30분
        cookie.setSecure(true);//HTTPS를 통해 쿠키가 전송되도록 함
        cookie.setHttpOnly(true);//자바스크립트를 통한 접근 방지

        return cookie;
    }

    public String encrypt(String text) throws NoSuchAlgorithmException {
        // SHA-256 알고리즘의 인스턴스 생성
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        // 입력 문자열의 바이트 배열을 업데이트하여 해시 값을 계산
        md.update(text.getBytes());
        // 계산된 해시 값을 16진수 문자열로 변환하여 반환
        return bytesToHex(md.digest());
    }

    // 바이트 배열을 16진수 문자열로 변환하는 메서드
    private String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            // 바이트 값을 16진수 문자열로 변환하여 StringBuilder에 추가
            builder.append(String.format("%02x", b));
        }
        // StringBuilder의 내용을 문자열로 반환
        return builder.toString();
    }
}