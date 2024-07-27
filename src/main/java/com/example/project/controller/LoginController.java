package com.example.project.controller;

import com.example.project.LoginForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("LoginForm", new LoginForm());
        return "login/before/login";
    }

    @PostMapping("/login")
    public String login(LoginForm loginForm){
        log.info("[ SYSTEM ] Login Tried ID : {}",loginForm.getUserId());
        log.info("[ SYSTEM ] Login Tried PASSWORD : {}",loginForm.getPassword());

        return null;
    }
}
