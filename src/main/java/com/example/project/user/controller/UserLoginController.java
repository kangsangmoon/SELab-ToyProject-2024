package com.example.project.user.controller;

import com.example.project.auth.token.TokenProvider;
import com.example.project.common.util.HeaderUtil;
import com.example.project.redis.RedisService;
import com.example.project.user.dto.UserResponse;
import com.example.project.user.dto.login.LoginRequest;
import com.example.project.user.service.CookieService;
import com.example.project.user.service.LoginService;
import com.example.project.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserLoginController {
    private final LoginService loginService;
    private final CookieService cookieService;
    private final TokenProvider tokenProvider;
    private final RedisService redisService;
    private final UserService userService;

    @PostMapping("/login")
    public void login(
            @RequestBody LoginRequest loginRequest,
            HttpServletResponse httpServletResponse,
            HttpServletRequest httpServletRequest
    ) {
        log.info("jwtAuthLogin {}, {}", loginRequest.getUserId(), loginRequest.getPassword());

        try {
            //TODO RefeshToken 사용하도록 수정
            Long userIdByToken = redisService.getUserIdByToken(HeaderUtil.resolveToken(httpServletRequest));
            String jwt = "";

            if(userIdByToken == null) {
                UserResponse userResponse = loginService.userLogin(loginRequest.getUserId(), loginRequest.getPassword());
                String accessToken = tokenProvider.createAccessToken(userResponse.getId(), userResponse.getRoleType().getRole());
                log.info("authrize jwt {}", accessToken);
                jwt = accessToken;
            }else jwt = HeaderUtil.resolveToken(httpServletRequest);

            httpServletResponse.setHeader(HeaderUtil.AUTHORIZATION_HEADER, jwt);
            httpServletResponse.addCookie(cookieService.createJWTCookie(jwt));
            httpServletResponse.setStatus(HttpStatus.OK.value());

        } catch (Exception e) {
            log.info("로그인 예외 발생 {}", e.getMessage());
            httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        }
    }

    @PostMapping("/login/token")
    public void tokenLogin(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader(HeaderUtil.AUTHORIZATION_HEADER);
        log.info("token login tryed {}", token);

        if (tokenProvider.validateToken(token)) {
            Long userIdByToken = redisService.getUserIdByToken(token);
            log.info("Login {}", userIdByToken);
            response.addCookie(cookieService.createJWTCookie(token));
            response.setStatus(HttpStatus.OK.value());
        } else response.setStatus(HttpStatus.BAD_REQUEST.value());
    }
}