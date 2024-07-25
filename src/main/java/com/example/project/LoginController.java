package com.example.project;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController(value = "/login")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final CookieService cookieService;

    @GetMapping
    public String loginForm(){
        return "login_form"; //POST
    }

    @PostMapping
    public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
        String id = request.getParameter("id");
        String password = request.getParameter("password");

        User loginUser = loginService.login(id, password);

        if(loginUser.getId().equals("error")){
            model.addAttribute("idError","ID 가 틀렸습니다");
            return "login_error_form";
        }else if (loginUser.getPassword().equals("error")){
            model.addAttribute("passwordError", "Password 가 틀렸습니다");
            return "login_error_form";
        }

        Cookie cookie = cookieService.loginCookieMaker(id, password);
        response.addCookie(cookie);

        return "success_home";
    }

    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("LoginCookie", null); // 쿠키 이름을 동일하게 설정
        cookie.setMaxAge(0); // 쿠키 유지 시간을 0으로 설정하여 쿠키를 삭제
        cookie.setSecure(true); // HTTPS를 통해 쿠키가 전송되도록 함
        cookie.setHttpOnly(true); // 자바스크립트를 통한 접근 방지
        response.addCookie(cookie); // 응답에 쿠키 추가

        return "logout_success"; // 로그아웃 성공 페이지로 리다이렉트
    }
}