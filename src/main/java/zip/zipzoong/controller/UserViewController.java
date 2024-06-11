package zip.zipzoong.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class UserViewController {
    @GetMapping("/login")
    public String showLogin() {
        return "emailLogin";
    }

    @GetMapping("/kakao")
    public String showKakaoLogin() {
        return "kakaoLogin";
    }

    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        System.out.println("들어옴");
        String token = (String) session.getAttribute("token");
        String refreshToken = (String) session.getAttribute("refreshToken");

        System.out.println("Session Token: " + token);
        System.out.println("Session RefreshToken: " + refreshToken);

        // 토큰을 모델에 추가하여 뷰에서 사용할 수 있게 함
        model.addAttribute("token", token);
        model.addAttribute("refreshToken", refreshToken);
        return "index";
    }

    @GetMapping("/join")
    public String showJoin() {
        return "join";
    }

    @GetMapping("/header")
    public String showHeader(HttpSession session, Model model) {
        System.out.println("header 요청 들어옴");
        String memberNick = (String) session.getAttribute("memberNick");
        model.addAttribute("memberNick", memberNick);
        return "common/header";
    }

    @GetMapping("/createBoard")
    public String createBoard() {
        return "member/createBoard";
    }

}
