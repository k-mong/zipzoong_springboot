package zip.zipzoong.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
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
    public ModelAndView join(@ModelAttribute MemberJoinDto memberJoinDto) {    // HTTP응답을 나타내는 클래스 <Body의 타입>
        memberService.join(memberJoinDto);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("join");
        return modelAndView;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>>login(MemberLoginDto memberLoginDto) {
        String token = tokenProvider.generationToken(memberLoginDto.getEmail());
        String refreshToken = tokenProvider.generateRefreshToken(memberLoginDto);
        String result = memberService.login(memberLoginDto);

        HashMap<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("refreshToken", refreshToken);
        response.put("result", result);
        return ResponseEntity.ok(response);
    }

}
