package zip.zipzoong.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zip.zipzoong.domain.entity.Board;
import zip.zipzoong.domain.entity.Member;
import zip.zipzoong.domain.repository.BoardRepository;
import zip.zipzoong.domain.repository.MemberRepository;
import zip.zipzoong.dto.MemberJoinDto;
import zip.zipzoong.dto.MemberLoginDto;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private  final BoardRepository boardRepository;

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

    public String login(MemberLoginDto memberLoginDto) {
        Optional<Member> loginMember = memberRepository.findByEmail(memberLoginDto.getEmail());
        if (!loginMember.isPresent()) {
            throw new RuntimeException("아이디 또는 비밀번호가 틀렸습니다.");
        } else if(passwordEncoder.matches(loginMember.get().getPassword(), memberLoginDto.getPassword())) {
            throw new RuntimeException("아이디 또는 비밀번호가 틀렸습니다.");
        }

        return "로그인 성공!";
    }

    public String likeBoard(String memberId, Long boardId) {
        Member member = memberRepository.findByEmail(memberId)
                .orElseThrow(() -> new RuntimeException("사용자가 없습니다"));
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));



        return "게시글 좋아요";
    }
}
