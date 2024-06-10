package zip.zipzoong.controller;

import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zip.zipzoong.dto.InsertBoardDto;
import zip.zipzoong.security.TokenProvider;
import zip.zipzoong.service.BoardService;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final TokenProvider tokenProvider;

    @PostMapping("/create")
    public ResponseEntity<String> insertBoard(@ModelAttribute InsertBoardDto insertBoardDto,
                                              @RequestHeader(name = "X-AUTH-REFRESHTOKEN") String refreshToken, @RequestHeader(name = "X-AUTH-TOKEN") String token) {
        System.out.println("Received datePicker: " + insertBoardDto.getDatePicker());
        System.out.println("createBoard 실행");
        String newAccessToken = token; // 기본적으로는 받은 액세스 토큰을 사용
        if(!tokenProvider.checkValidToken(token)){  // 액세스토큰이 만료되면 리프레시토큰을 검사해서 유효한지 체크
            newAccessToken = tokenProvider.checkRefreshToken(refreshToken);
            if(newAccessToken == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("토큰이 만료되었습니다."); // 상태코드 401
            }
        }

        String memberId = tokenProvider.getUserId(newAccessToken);
        String result = boardService.createBoard(insertBoardDto, memberId);
        return ResponseEntity.ok(result + newAccessToken);
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

    @GetMapping("/getAddress")
    public ResponseEntity<String> getAddress() throws UnsupportedEncodingException, URISyntaxException, ParseException {
        String address = boardService.getAddress();
        return ResponseEntity.ok(address);
    }
}
