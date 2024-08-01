package zip.zipzoong.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import zip.zipzoong.dto.response.BoardDetailDto;
import zip.zipzoong.dto.response.BoardListDto;
import zip.zipzoong.dto.response.LikeBoardListDto;
import zip.zipzoong.security.TokenProvider;
import zip.zipzoong.service.BoardService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberViewController {

    @Autowired
    private BoardService boardService;
    @Autowired
    private TokenProvider tokenProvider;

    @GetMapping("/login")
    public String showLogin() {
        return "emailLogin";
    }

    @GetMapping("/kakao")
    public String showKakaoLogin() {
        return "kakaoLogin";
    }

    @GetMapping("/")
    public String index() {
        System.out.println("index실행!");
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

//    @GetMapping("/allBoardList")
//    public String showAllBoardList(Model model) {
//        System.out.println("allboardList실행!");
//        List<BoardListDto> allBoardList = boardService.findAllBoardList();
//        System.out.println("allBoardList = " + allBoardList);
//        model.addAttribute("allBoardList", allBoardList);
//        return "common/boardList";
//    }

    @GetMapping("/allBoardList")
    public String showAllBoardList(Model model,
                                   @RequestHeader(name = "X-AUTH-REFRESHTOKEN", required = false) String refreshToken,
                                   @RequestHeader(name = "X-AUTH-TOKEN", required = false) String token) {
        String newAccessToken = token; // 기본적으로는 받은 액세스 토큰을 사용
        String memberId = null;

        if (token != null && !tokenProvider.checkValidToken(token)) {  // 액세스토큰이 만료되면 리프레시토큰을 검사해서 유효한지 체크
            newAccessToken = tokenProvider.checkRefreshToken(refreshToken);
        }

        if (newAccessToken != null) {
            memberId = tokenProvider.getUserId(newAccessToken);
        }

        List<BoardListDto> allBoardList = boardService.findAllBoardList(memberId);
        model.addAttribute("allBoardList", allBoardList);
        return "common/boardList";
    }

    @GetMapping("/likeBoardList")
    public String ShowAllLikeBoardList(Model model,
                                       @RequestHeader(name = "X-AUTH-REFRESHTOKEN") String refreshToken, @RequestHeader(name = "X-AUTH-TOKEN") String token) {
        String newAccessToken = token; // 기본적으로는 받은 액세스 토큰을 사용
        if(!tokenProvider.checkValidToken(token)){  // 액세스토큰이 만료되면 리프레시토큰을 검사해서 유효한지 체크
            newAccessToken = tokenProvider.checkRefreshToken(refreshToken);
        }

        String memberId = tokenProvider.getUserId(newAccessToken);
        List<LikeBoardListDto> likeBoardList = boardService.findLikeBoard(memberId);
        model.addAttribute("likeBoardList", likeBoardList);
        return "common/boardList";
    }

    @GetMapping("/boardDetail/{id}")
    public String showBoardDetail(Model model, @PathVariable Long id) {
        BoardDetailDto boardDetail = boardService.findBoardDetail(id);
        model.addAttribute("boardDetail", boardDetail);
        return "common/boardDetail";
    }

    @GetMapping("/createBoard")
    public String createBoard() {
        return "member/createBoard";
    }

}
