package com.example.project.controller;


import com.example.project.LoginForm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


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
    public String loginPage(@ModelAttribute LoginForm loginForm, Model model, HttpServletRequest request)throws Exception  {

        return "login/before/login";
    }

    @PostMapping("/login.do")
    public String login(@Validated LoginForm loginForm, Model model, HttpServletRequest request, BindingResult result,
                        RedirectAttributes redirect, HttpServletResponse response)throws Exception  {
        return "login/aftre/signin_main";
    }

    @GetMapping("/signup")
    public String signupPage() {

        return "login/before/signup";
    }

}

