package zip.zipzoong.service;

import com.fasterxml.jackson.core.JsonParser;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import zip.zipzoong.domain.entity.Board;
import zip.zipzoong.domain.entity.Member;
import zip.zipzoong.domain.entity.RoomImage;
import zip.zipzoong.domain.repository.BoardRepository;
import zip.zipzoong.domain.repository.MemberRepository;
import zip.zipzoong.domain.repository.RoomImageRepository;
import zip.zipzoong.dto.InsertBoardDto;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final RoomImageRepository roomImageRepository;
    private final MemberRepository memberRepository;

    @Value("${file.boardImagePath}")
    private String uploadFolder;

    public String createBoard(InsertBoardDto insertBoardDto, String memberId) {
        System.out.println("createBoard 실행");
        System.out.println(insertBoardDto.getDatePicker() + "getDatePicker BoardService");
        List<RoomImage> roomImage = insertImage(insertBoardDto);

        Member member = memberRepository.findByEmail(memberId).get();

        boardRepository.save(
                Board.builder()
                        .type(insertBoardDto.getRoomType())
                        .address(insertBoardDto.getAddress())
                        .addressDetail(insertBoardDto.getAddressDetail())
                        .roomArea(insertBoardDto.getRoomArea())
                        .roomInfo(insertBoardDto.getRoomInfo())
                        .rentType(insertBoardDto.getRentType())
                        .deposit(insertBoardDto.getDeposit())
                        .month(insertBoardDto.getMonth())
                        .cost(insertBoardDto.isCost())
                        .roomCost(insertBoardDto.getRoomCost())
                        .datePicker(insertBoardDto.getDatePicker())
                        .totalFloor(insertBoardDto.getTotalFloor())
                        .fllorsNumber(insertBoardDto.getFollrsNumber())
                        .elevator(insertBoardDto.isElevator())
                        .parking(insertBoardDto.isParking())
                        .parkingCost(insertBoardDto.getParkingCost())
                        .title(insertBoardDto.getTitle())
                        .textArea(insertBoardDto.getTextArea())
                        .url(roomImage.toString())
                        .member(member)
                        .build());


        return "게시글 등록 완료!";
    }

    @Transactional
    public List<RoomImage> insertImage(InsertBoardDto insertBoardDto) {
        List<RoomImage> roomImages = new ArrayList<>();

        if(insertBoardDto.getFiles() != null && !insertBoardDto.getFiles().isEmpty()) {
            //만약 이미지파일이 있고, 이미지파일이 비어있지 않다면

            if(insertBoardDto.getFiles().size() > 7 ) {
                throw new RuntimeException("이미지는 최대 7개까지 가능합니다.");
            }

            for (MultipartFile file : insertBoardDto.getFiles()) {

                // MultipartFile file 안에 인자값으로 들어온 이미지 파일을 하나씩 너어줘
                UUID uuid = UUID.randomUUID();
                // 서버 내부에서 관리하는 파일명은 유일한 이름을 생성하는 UUID를 사용해서 충돌하지 않도록 한다.
                // 각 이미지에대한 식별값 생성
                String imageFileName = uuid + "_" + file.getOriginalFilename();
                // 이미지파일 이름 규칙지정
                File destinationFile = new File(uploadFolder + imageFileName);
                try {
                    file.transferTo(destinationFile);
                    // 인자값으로 들어온 이미지파일의 저장경로 지정
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                RoomImage image = RoomImage.builder()
                        .url("/boardImages/" + imageFileName)
                        .build();

                roomImageRepository.save(image);

                roomImages.add(image);
            }
        }
        return roomImages;
    }

    public String deleteBoard(Long boardId, String memberId) {
        Member findMember = memberRepository.findByEmail((memberId))
                .orElseThrow(()-> new RuntimeException("존재하지 않는 회원입니다."));

        Board board = boardRepository.findByIdAndMember(boardId, findMember)
                .orElseThrow(()->new RuntimeException("게시글을 찾을 수 없습니다."));

        boardRepository.delete(board);

        return "게시글이 삭제됐습니다.";

    }

    public String getAddress() throws UnsupportedEncodingException, URISyntaxException, ParseException {
        String lat = "";
        String lng = "";
        String city = "인천광역시 서구 심곡동";
        String b_code = "";

        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String appkey = "KakaoAK {b7d2b17b0b31f160b34d7bf533c7e9fb}";
        headers.set("Authorization", appkey);

        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        String encode = URLEncoder.encode(city, "UTF-8");
        String rawURI = "https://dapi.kakao.com/v2/local/search/address.json?query=" + encode;
        URI uri = new URI(rawURI);

        ResponseEntity<String> res = rest.exchange(uri, HttpMethod.GET, entity, String.class);

        JSONParser jsonParser = new JSONParser();
        JSONObject body = (JSONObject) jsonParser.parse(res.getBody().toString());
        JSONArray docu = (JSONArray) body.get("documents");

        if (docu.size() != 0) {
            JSONObject addr = (JSONObject) docu.get(0);
            if(addr.size() != 0) {
                JSONObject addr1 = (JSONObject) addr.get("address");
                if (b_code.length() < 1) {
                    b_code = ((String) addr1.get("b_code")).substring(0, 5);
                } else {
                    b_code = ((String) addr1.get("h_code")).substring(0, 5);
                }
            }
        }

        return b_code;

    }




}
