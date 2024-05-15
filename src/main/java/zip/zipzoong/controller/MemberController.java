package zip.zipzoong.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
        memberService.join(memberJoinDto);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("join");
        return new RedirectView("/");
    }

    @PostMapping("/login")
    public RedirectView login(@ModelAttribute MemberLoginDto memberLoginDto) {
        String token = tokenProvider.generationToken(memberLoginDto.getEmail());
        String refreshToken = tokenProvider.generateRefreshToken(memberLoginDto);
        String result = memberService.login(memberLoginDto);

        HashMap<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("refreshToken", refreshToken);
        response.put("result", result);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("emailLogin");
        modelAndView.addObject("memberNick", memberLoginDto.getEmail());
        return new RedirectView("/");
    }

}
