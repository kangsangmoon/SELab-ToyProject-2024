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

import java.security.NoSuchAlgorithmException;

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
    public String login(HttpServletRequest request, HttpServletResponse response, Model model) throws NoSuchAlgorithmException {
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
}
