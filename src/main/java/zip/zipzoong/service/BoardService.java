package zip.zipzoong.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import zip.zipzoong.domain.entity.Board;
import zip.zipzoong.domain.entity.RoomImage;
import zip.zipzoong.domain.repository.BoardRepository;
import zip.zipzoong.domain.repository.RoomImageRepository;
import zip.zipzoong.dto.InsertBoardDto;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final RoomImageRepository roomImageRepository;

    @Value("${file.boardImagePath}")
    private String uploadFolder;

    public Board createBoard(InsertBoardDto insertBoardDto) {
        String roomImage = insertImage(insertBoardDto);


        return boardRepository.save(
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
//                        .roomImages(roomImage)
                        .url(roomImage)
                        .build());
    }

    @Transactional
    public String insertImage(InsertBoardDto insertBoardDto) {
        List<RoomImage> roomImages = new ArrayList<>();

        if(insertBoardDto.getFiles() != null && !insertBoardDto.getFiles().isEmpty()) {
            //만약 이미지파일이 있고, 이미지파일이 비어있지 않다면

            if(insertBoardDto.getFiles().size() > 7 ) {
                throw new RuntimeException("이미지는 최대 6개까지 가능합니다.");
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
            return "이미지 등록 완료";
    }



}
