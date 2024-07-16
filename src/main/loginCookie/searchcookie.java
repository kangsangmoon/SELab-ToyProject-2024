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