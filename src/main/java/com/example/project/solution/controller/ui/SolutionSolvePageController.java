package com.example.project.solution.controller.ui;

import com.example.project.auth.token.TokenProvider;
import com.example.project.common.util.HeaderUtil;
import com.example.project.solution.service.UserSolutionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/solutions")
@RequiredArgsConstructor
public class SolutionSolvePageController {
    private final UserSolutionService userSolutionService;
    private final TokenProvider tokenProvider;

    @RequestMapping("/{id}")
    public String solvePage(
            @PathVariable(name = "id") Long id,
            Model model,
            HttpServletRequest request
    ) {
        var response = userSolutionService.read(id);
        model.addAttribute("title", response.getTitle());
        model.addAttribute("description", response.getDescription());
        model.addAttribute("inExample", response.getInExample());
        model.addAttribute("outExample", response.getOutExample());
        if (tokenProvider.validateToken(HeaderUtil.resolveToken(request))) {
            return "auth/solution/solve";
        }
        return "non-auth/solution/solve";
    }
}