package com.example.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    @GetMapping("/main")
    public String mainPage() {

        //jsp
        //thymeleaf

        //model.addAttribute("name","kim");

        return "beforelogin/main_page";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "beforelogin/login_page";
    }

    @GetMapping("/signup")
    public String signupPage() {

        return "beforelogin/signup_page";
    }

}

