package com.example.project.home.controller;

import com.example.project.auth.token.TokenProvider;
import com.example.project.common.util.HeaderUtil;
import com.example.project.user.dto.UserResponse;
import com.example.project.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
    private final TokenProvider tokenProvider;
    private final UserService userService;

    @RequestMapping
    public String home(
            HttpServletRequest request,
            Model model
    ) {
        if (tokenProvider.validateToken(HeaderUtil.resolveToken(request))) {
            Long userIdByToken = tokenProvider.getUserIdByToken(HeaderUtil.resolveToken(request));
            UserResponse userResponse = userService.find(userIdByToken);
            log.info("home token -> {}", HeaderUtil.resolveToken(request));
            model.addAttribute("user", userResponse.getName().getName());
            return "auth/main";
        }
        return "non-auth/main";
    }

    @RequestMapping(value = "/ranking")
    public String ranking(HttpServletRequest request) {
        if (tokenProvider.validateToken(HeaderUtil.resolveToken(request))) {
            log.info("home token -> {}", HeaderUtil.resolveToken(request));
            return "auth/ranking";
        }
        return "non-auth/ranking";
    }

}