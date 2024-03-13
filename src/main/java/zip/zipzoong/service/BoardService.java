package zip.zipzoong.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zip.zipzoong.domain.entity.Board;
import zip.zipzoong.domain.repository.BoardRepository;
import zip.zipzoong.dto.InsertBoardDto;
import zip.zipzoong.dto.InsertImageDto;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public Board createBoard(InsertBoardDto insertBoardDto) {
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
                        .build());
    }

    public String insertImage(InsertImageDto insertImageDto) {
        return "이미지 등록 완료";
    }
}
