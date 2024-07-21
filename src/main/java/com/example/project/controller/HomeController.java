package com.example.project.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


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
    public ModelAndView signinPage(@ModelAttribute Signin signin, HttpServletRequest request) throws  Exception
    {
       return new ModelAndView("signin_page");
    }

    @RequestMapping("/signin.do")
    public ModelAndView doSignin(@Valid Signin signin, BindingResult result,
           RedirectAttributes redirect, HttpServletRequest request, HttpServletResponse response)
           throws Exception {
        return new ModelAndView("main_page");
    }
    @GetMapping("/signup_page")
    public String signupPage() {

        return "signup_page";
    }
}

