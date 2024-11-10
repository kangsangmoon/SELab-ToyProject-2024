package com.example.project.auth.controller;

import com.example.project.auth.dto.TokenDto;
import com.example.project.auth.token.TokenProvider;
import com.example.project.common.util.HeaderUtil;
import com.example.project.redis.RedisService;
import com.example.project.user.dto.UserResponse;
import com.example.project.user.dto.login.LoginRequest;
import com.example.project.user.dto.request.UserRegisterRequest;
import com.example.project.user.service.LoginService;
import com.example.project.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final LoginService loginService;
    private final TokenProvider tokenProvider;
    private final RedisService redisService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping("/authenticate")
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginRequest loginDto) {
        UserResponse userResponse = loginService.userLogin(loginDto.getUserId(), loginDto.getPassword());

        String accessToken = tokenProvider.createAccessToken(userResponse.getId(),userResponse.getRoleType().getRole());
        String refreshToken = tokenProvider.createRefreshToken(userResponse.getId(),userResponse.getRoleType().getRole());

        log.info("jwt token -> {}", accessToken);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HeaderUtil.AUTHORIZATION_HEADER, "Bearer " + accessToken);

        return new ResponseEntity<>(new TokenDto(accessToken, refreshToken), httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/authenticate")
    public ResponseEntity<TokenDto> getToken(HttpServletRequest request) {
        String token = HeaderUtil.resolveToken(request);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HeaderUtil.AUTHORIZATION_HEADER, token);

        return new ResponseEntity<>(new TokenDto(token, null), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(@Valid @RequestBody UserRegisterRequest userDto) {
        return ResponseEntity.ok(userService.register(userDto));
    }
}