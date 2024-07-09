package com.example.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/main")
    public String mainPage(Model model){

        //jsp
        //thymeleaf

        model.addAttribute("name","kim");

        return "main_page";
    }
}