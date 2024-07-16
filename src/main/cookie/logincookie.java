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

@GetMapping("/")
public String homeLogin(@CookieValue(name = "memberId", required = false) Long memberId, Model model) {
    if (memberId == null) {
        return "home";
    }

    Member loginMember = memberRepository.findById(memberId);
    if (loginMember == null) {
        return "home";
    }

    model.addAttribute("member", loginMember);
    return "loginHome";
}//서버에서 쿠키 조회하기

@PostMapping("/logout")
public String logout(HttpServletResponse response) {
    expiredCookie(response, "memberId");
    return "redirect:/";
}

private void expiredCookie(HttpServletResponse response, String cookieName) {
    Cookie cookie = new Cookie(cookieName, null);
    cookie.setMaxAge(0);
    cookie.setPath("/"); // 경로가 올바르게 설정되었는지 확인
    response.addCookie(cookie);
} //서버에서 쿠키 없애기(로그아웃 할 때)