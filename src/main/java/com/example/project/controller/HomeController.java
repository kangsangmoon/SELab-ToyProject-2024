package com.example.project.controller;


import com.example.project.LoginForm;
import com.example.project.SignupForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Slf4j
@Controller
public class HomeController {

    @GetMapping("/main")
    public String mainPage() {

        //jsp
        //thymeleaf

        //model.addAttribute("name","kim");
        return "login/before/main";
    }

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

    @GetMapping("/signup")
    public String signupPage(Model model) {
        model.addAttribute("SignupForm", new SignupForm());
        return "login/before/signup";
    }

    @PostMapping("/signup")
    public String singup(SignupForm signupForm){
        log.info("[ SYSTEM ] REGISTER Tried ID : {}",signupForm.getUserId());   //로그로 아이디를 띄움
        log.info("[ SYSTEM ] REGISTER Tried PASSWORD : {}",signupForm.getPassword());

        return null;    //null 값을 받아왔기 때문에 화이트라벨 에러가 발생함
    }
}

