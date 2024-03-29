package zip.zipzoong.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zip.zipzoong.domain.entity.Board;
import zip.zipzoong.dto.InsertBoardDto;
import zip.zipzoong.security.TokenProvider;
import zip.zipzoong.service.BoardService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final TokenProvider tokenProvider;

    @PostMapping("/insert")
    public ResponseEntity<Board> insertBoard(InsertBoardDto insertBoardDto, @RequestHeader(name = "X-AUTH-TOKEN") String refreshToken) {
        String token = tokenProvider.checkToken(refreshToken);
        if(token == null) {
            throw new RuntimeException("토큰이 만료되었습니다.");
        }
        Board result = boardService.createBoard(insertBoardDto);
        return ResponseEntity.ok(result);
    }
}
