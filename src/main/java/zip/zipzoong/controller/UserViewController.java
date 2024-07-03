package zip.zipzoong.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import zip.zipzoong.dto.response.BoardListDto;
import zip.zipzoong.service.BoardService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserViewController {

    @Autowired
    private BoardService boardService;

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

    @GetMapping("/allBoardList")
    public String showAllBoardList(Model model) {
        System.out.println("allboardList실행!");
        List<BoardListDto> allBoardList = boardService.findAllBoardList();
        System.out.println("allBoardList = " + allBoardList);
        model.addAttribute("allBoardList", allBoardList);
        return "common/boardList";
    }

    @GetMapping("/createBoard")
    public String createBoard() {
        return "member/createBoard";
    }

}
