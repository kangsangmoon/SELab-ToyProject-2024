package com.example.project.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class MainController {

    @GetMapping("/main")
    public String mainPage(Model model, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        model.addAttribute("numberOfSolution",1);
        model.addAttribute("ableToSolve", 2);
        model.addAttribute("ableToGrading",2);

        return "login/before/main";
    }


}
