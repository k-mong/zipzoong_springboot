package zip.zipzoong.controller;

import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import zip.zipzoong.dto.BoardImgForm;
import zip.zipzoong.dto.InsertBoardDto;
import zip.zipzoong.dto.response.BoardListDto;
import zip.zipzoong.security.TokenProvider;
import zip.zipzoong.service.BoardService;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final TokenProvider tokenProvider;

    @PostMapping("/create")
    public ResponseEntity<String> insertBoard(@ModelAttribute InsertBoardDto insertBoardDto,
                                              @ModelAttribute BoardImgForm boardImgForm,
                                              @RequestHeader(name = "X-AUTH-REFRESHTOKEN", required = false) String refreshToken,
                                              @RequestHeader(name = "X-AUTH-TOKEN") String token) {
        System.out.println("createBoard 실행");
        String newAccessToken = token; // 기본적으로는 받은 액세스 토큰을 사용
        if(!tokenProvider.checkValidToken(token)){  // 액세스토큰이 만료되면 리프레시토큰을 검사해서 유효한지 체크
            newAccessToken = tokenProvider.checkRefreshToken(refreshToken);
            if(newAccessToken == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("토큰이 만료되었습니다."); // 상태코드 401
            }
        }

        String memberId = tokenProvider.getUserId(newAccessToken);
        String result = String.valueOf(boardService.createBoard(insertBoardDto, boardImgForm, memberId));
        return ResponseEntity.ok(result + newAccessToken);
    }

    @GetMapping("/allBoardList")
    public ResponseEntity<List<BoardListDto>> allBoardList() {
        List<BoardListDto> allBoardList = boardService.findAllBoardList();
        return ResponseEntity.ok(allBoardList);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBoard(@PathVariable Long id,
                                              @RequestHeader(name = "X-AUTH-REFRESHTOKEN") String refreshToken, @RequestHeader(name = "X-AUTH-TOKEN") String token) {
        String newAccessToken = token; // 기본적으로는 받은 액세스 토큰을 사용
        if(!tokenProvider.checkValidToken(token)){  // 액세스토큰이 만료되면 리프레시토큰을 검사해서 유효한지 체크
            newAccessToken = tokenProvider.checkRefreshToken(refreshToken);
            if(newAccessToken == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("토큰이 만료되었습니다."); // 상태코드 401
            }
        }

        String memberId = tokenProvider.getUserId(newAccessToken);

        String result = boardService.deleteBoard(id, memberId);
        return ResponseEntity.ok(result + newAccessToken);
    }


}
