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