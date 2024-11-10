package com.example.project.user.controller.ui;

import com.example.project.auth.token.TokenProvider;
import com.example.project.common.util.HeaderUtil;
import com.example.project.redis.RedisService;
import com.example.project.user.dto.UserResponse;
import com.example.project.user.dto.request.UserUpdateRequest;
import com.example.project.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/users/info")
@RequiredArgsConstructor
public class UserInfoController {
    private final TokenProvider tokenProvider;
    private final UserService userService;
    private final RedisService redisService;

    @RequestMapping
    public String info(
            Model model,
            HttpServletRequest httpServletRequest
    ) {
        String token = HeaderUtil.resolveToken(httpServletRequest);
        log.info("info entry -> token: {}", token);
        if (tokenProvider.validateToken(token)) {
            log.info("token is validate");
            Long userIdByToken = redisService.getUserIdByToken(token);
            UserResponse userResponse = userService.find(userIdByToken);
            model.addAttribute("UserInfo", userResponse);
            return "/auth/user/info";
        }
        return "/non-auth/main";
    }

    @RequestMapping("/edit")
    public String editInfo(
            Model model,
            HttpServletRequest httpServletRequest
    ) {
        String token = HeaderUtil.resolveToken(httpServletRequest);

        if (tokenProvider.validateToken(token)) {
            Long userIdByToken = redisService.getUserIdByToken(token);
            UserResponse userResponse = userService.find(userIdByToken);
            model.addAttribute("UserInfo", userResponse);
            model.addAttribute("UpdateRequest", new UserUpdateRequest());
            return "/auth/user/info";
        }

        return "/non-auth/main";
    }
}