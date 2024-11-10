package com.example.project.user.controller;

import com.example.project.auth.token.TokenProvider;
import com.example.project.common.util.HeaderUtil;
import com.example.project.redis.RedisService;
import com.example.project.user.dto.UserResponse;
import com.example.project.user.dto.request.UserRegisterRequest;
import com.example.project.user.service.CookieService;
import com.example.project.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/users/")
@RequiredArgsConstructor
public class UserRegisterController {
    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final CookieService cookieService;
    private final RedisService redisService;

    @PostMapping("/signup")
    public void signup(
            @Valid @RequestBody UserRegisterRequest userDto,
            HttpServletResponse httpServletResponse
    ) {
        UserResponse register = userService.register(userDto);

        String jwt = tokenProvider.createAccessToken(register.getId(), register.getRoleType().getRole());
        redisService.saveToken(jwt, register.getId());
        log.info("jwt token registered {}", jwt);

        httpServletResponse.setStatus(HttpStatus.OK.value());
        httpServletResponse.setHeader(HeaderUtil.AUTHORIZATION_HEADER, jwt);
        httpServletResponse.addCookie(cookieService.createJWTCookie(jwt));
    }
}