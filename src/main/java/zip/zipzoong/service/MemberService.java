package zip.zipzoong.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import zip.zipzoong.domain.entity.Member;
import zip.zipzoong.domain.repository.MemberRepository;
import zip.zipzoong.dto.MemberJoinDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    public Member join(MemberJoinDto memberJoinDto) {
        if(memberRepository.findByEmail(memberJoinDto.getEmail()).isPresent()) {
            throw new RuntimeException("이미 존재하는 회원입니다");
        } else {
            Member member = memberRepository.save(Member.builder()
                    .email(memberJoinDto.getEmail())
                    .password(memberJoinDto.getPassword())
                    .build());

            return member;
        }

    }
}
