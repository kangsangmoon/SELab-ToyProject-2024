package com.example.project;

import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CookieService {

    private final EncryptionService encryptionService;

    public Cookie loginCookieMaker(String id, String password){

        String encryptId = encryptionService.encrypt(id);
        String encryptPassword = encryptionService.encrypt(password);

        Cookie cookie = new Cookie("LoginCookie", String.format("%s||%s", encryptId, encryptPassword));
        cookie.setMaxAge(1800);//쿠키 유지 시간 30분
        cookie.setSecure(true);//HTTPS를 통해 쿠키가 전송되도록 함
        cookie.setHttpOnly(true);//자바스크립트를 통한 접근 방지

        return cookie;
    }
}