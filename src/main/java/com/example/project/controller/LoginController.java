package com.example.project.controller;

import com.example.project.dto.LoginForm;
import com.example.project.exception.LoginException;
import com.example.project.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("LoginForm", new LoginForm());
        return "login/before/login";
    }

    @PostMapping("/login")
    public String login(LoginForm loginForm, Model model){
        log.info("[ SYSTEM ] Login Tried ID : {}",loginForm.getUserId());
        log.info("[ SYSTEM ] Login Tried PASSWORD : {}",loginForm.getPassword());

        try {
            loginService.login(loginForm);
        }catch (LoginException e){
            if(e.getMessage().equals("Id Error")){
                model.addAttribute("IdError","존재하지 않는 ID 입니다");
                return "login/before/login";
            }
            model.addAttribute("PasswordError","Password 가 일치하지 않습니다");
            return "login/before/login";
        }


        return null;
    }
}
