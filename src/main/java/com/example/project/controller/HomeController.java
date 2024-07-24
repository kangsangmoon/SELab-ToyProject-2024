package com.example.project.controller;


import com.example.project.LoginForm;
import com.example.project.SignupForm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;


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

    @GetMapping("/signup")
    public String signupPage(Model model) {
        model.addAttribute("SignupForm", new SignupForm());
        return "login/before/signup";
    }
}

