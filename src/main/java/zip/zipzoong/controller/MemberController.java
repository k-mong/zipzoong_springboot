package zip.zipzoong.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import zip.zipzoong.domain.entity.Member;
import zip.zipzoong.dto.MemberJoinDto;
import zip.zipzoong.dto.MemberLoginDto;
import zip.zipzoong.security.TokenProvider;
import zip.zipzoong.service.MemberService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final TokenProvider tokenProvider;


    @PostMapping("/join")
    public RedirectView join(@ModelAttribute MemberJoinDto memberJoinDto) {
        System.out.println("join 요청");
        memberService.join(memberJoinDto);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("join");
        return new RedirectView("/");
    }

    @PostMapping("/login")
    public RedirectView login(@ModelAttribute MemberLoginDto memberLoginDto, HttpSession session, Model model) {
        System.out.println("login 요청 들어옴");
        String token = tokenProvider.generationToken(memberLoginDto.getEmail());
        String refreshToken = tokenProvider.generateRefreshToken(memberLoginDto);
        String result = memberService.login(memberLoginDto);

        HashMap<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("refreshToken", refreshToken);
        response.put("result", result);

        session.setAttribute("memberNick", memberLoginDto.getEmail());
        model.addAttribute("memberNick", memberLoginDto.getEmail());

        System.out.println(memberLoginDto.getEmail());
        return new RedirectView("/");
    }

}
