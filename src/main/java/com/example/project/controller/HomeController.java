package com.example.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @GetMapping("/main")
    public String mainPage() {

        //jsp
        //thymeleaf

        //model.addAttribute("name","kim");

        return "main_page";
    }

    @GetMapping("/signin_page")
    public String signinPage() {

        return "signin_page";
    }

    @GetMapping("/signup_page")
    public String signupPage() {

        return "signup_page";
    }
}
