package com.example.project.user.controller;

import com.example.project.auth.token.TokenProvider;
import com.example.project.common.util.HeaderUtil;
import com.example.project.redis.RedisService;
import com.example.project.user.dto.request.UserUpdateRequest;
import com.example.project.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/users/info")
@RequiredArgsConstructor
public class UserInfoApiController {
    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final RedisService redisService;

    @PostMapping("/edit")
    public void editUser(
            @RequestBody UserUpdateRequest updateRequest,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse
    ) {
        if (tokenProvider.validateToken(HeaderUtil.resolveToken(httpServletRequest))) {
            Long userIdByToken = redisService.getUserIdByToken(HeaderUtil.resolveToken(httpServletRequest));
            var user = userService.find(userIdByToken);

            if (user.getUserId().equals(updateRequest.getUserId())) {
                var updateUser = userService.updateUser(updateRequest);
                log.info("editUser updateUser -> {}", updateUser.getId());
                httpServletResponse.setStatus(HttpStatus.OK.value());
            }
        } else httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
    }

    @GetMapping("/my-page")
    public void myPage(
            Model model,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse
    ) {
        if (tokenProvider.validateToken(HeaderUtil.resolveToken(httpServletRequest))) {
            Long userIdByToken = redisService.getUserIdByToken(HeaderUtil.resolveToken(httpServletRequest));
            var user = userService.find(userIdByToken);
            log.info("userResponse id {}", user.getId());
            model.addAttribute("UserInfo", user);
            httpServletResponse.setStatus(HttpStatus.OK.value());
        } else httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
    }
}