@PostMapping("login")
public String login(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletResponse response) {
    if (bindingResult.hasErrors()) {
        return "login/loginForm";
    }

    Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
    if (loginMember == null) {
        bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
        return "login/loginForm";
    }

    Cookie idCookie = new Cookie("memberId", String.valueOf(loginMember.getId()));
    idCookie.setPath("/"); // 쿠키 경로 설정
    response.addCookie(idCookie);

    return "redirect:/";
}//서버에서 쿠키 생성하기