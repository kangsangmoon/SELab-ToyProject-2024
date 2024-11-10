package com.example.project.user.controller.rest;

import com.example.project.auth.token.TokenProvider;
import com.example.project.common.util.HeaderUtil;
import com.example.project.redis.RedisService;
import com.example.project.user.service.CookieService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserLogoutController {
    private final TokenProvider tokenProvider;
    private final RedisService redisService;

    @DeleteMapping("/logout")
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String token = HeaderUtil.resolveToken(httpServletRequest);

        if (tokenProvider.validateToken(token)) {
            redisService.deleteToken(token);
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
        } else {
            httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        }
    }
}