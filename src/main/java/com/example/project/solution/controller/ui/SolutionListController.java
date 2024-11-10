package com.example.project.solution.controller.ui;

import com.example.project.auth.token.TokenProvider;
import com.example.project.common.util.HeaderUtil;
import com.example.project.solution.dto.response.list.NonAuthSolutionListResponse;
import com.example.project.solution.dto.response.list.AuthSolutionListResponse;
import com.example.project.solution.service.UserSolutionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/solutions/list")
@RequiredArgsConstructor
public class SolutionListController {

    private final UserSolutionService userSolutionService;
    private final TokenProvider tokenProvider;

    @RequestMapping
    public String solutionList(
            Model model,
            HttpServletRequest request
    ) {
        log.info("solutionList entry -> token: {}", HeaderUtil.resolveToken(request));
        if (tokenProvider.validateToken(HeaderUtil.resolveToken(request))) {
            Long userIdByToken = tokenProvider.getUserIdByToken(HeaderUtil.resolveToken(request));
            log.info("token is validate -> user: {}", userIdByToken);

            List<AuthSolutionListResponse> authSolutionListResponses = userSolutionService.getAuthSolutionList(userIdByToken);
            log.info("solutionListResponse size: {}", authSolutionListResponses.size());

            model.addAttribute("SolutionList", authSolutionListResponses);
            return "auth/solution/solution_list";
        } else {
            log.info("token is not validate");

            List<NonAuthSolutionListResponse> nonAuthSolutionList = userSolutionService.getNonAuthSolutionList();
            log.info("solutionListResponse size: {}", nonAuthSolutionList.size());

            model.addAttribute("SolutionList", nonAuthSolutionList);
            return "non-auth/solution/solution_list";
        }
    }
}