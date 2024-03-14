package zip.zipzoong.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public ResponseEntity<Member> join(@RequestBody MemberJoinDto memberJoinDto) {    // HTTP응답을 나타내는 클래스 <Body의 타입>
        Member result = memberService.join(memberJoinDto);
        return ResponseEntity.ok(result);   // 200번 코드와 resul 를 반환
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
