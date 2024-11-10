package com.example.project.user.controller;

import com.example.project.auth.token.TokenProvider;
import com.example.project.common.util.HeaderUtil;
import com.example.project.redis.RedisService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/users/logout")
@RequiredArgsConstructor
public class LogoutController {
    private final RedisService redisService;

    @PostMapping
    public void logout(
            HttpServletResponse httpServletResponse,
            HttpServletRequest httpServletRequest
    ) throws IOException {
        redisService.deleteToken(HeaderUtil.resolveToken(httpServletRequest));
        httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
    }
}