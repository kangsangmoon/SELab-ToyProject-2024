package com.example.project.user.controller.ui;

import com.example.project.error.exception.user.InvalidLoginUserIdException;
import com.example.project.error.exception.user.InvalidLoginPasswordException;
import com.example.project.user.controller.UserApiController;
import com.example.project.user.dto.login.LoginRequest;
import com.example.project.user.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController(value = "/user")
@RequiredArgsConstructor
public class UserController {

    private final UserApiController userApiController;
    private final LoginService loginService;

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("LoginRequest", new LoginRequest());

        return "/non-authentication/user/login";
    }

    //TODO 쿠키를 남긴다
    @PostMapping("/login")
    public String login(LoginRequest loginRequest, Model model) {
        log.info("[ SYSTEM ] Login Tried ID : {}", loginRequest.getUserId());
        log.info("[ SYSTEM ] Login Tried PASSWORD : {}", loginRequest.getPassword());

        try {
            loginService.login(loginRequest);
        } catch (InvalidLoginUserIdException e) {
            log.info("[ SYSTEM ] ID가 일치하지 않습니다");

            model.addAttribute("IdError","입력한 ID "+loginRequest.getUserId()+"가 존재하지 않습니다");

            return "/non-authentication/user/login";
        } catch (InvalidLoginPasswordException e) {
            log.info("[ SYSTEM ] PASSWORD가 일치하지 않습니다");

            model.addAttribute("PasswordError","입력한 PASSWORD "+loginRequest.getPassword()+"가 존재하지 않습니다");

            return "/non-authentication/user/login";
        }

        return null;
    }
}
