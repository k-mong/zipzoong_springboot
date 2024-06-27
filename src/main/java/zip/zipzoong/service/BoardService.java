package zip.zipzoong.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import zip.zipzoong.domain.entity.Board;
import zip.zipzoong.domain.entity.Member;
import zip.zipzoong.domain.entity.RoomImage;
import zip.zipzoong.domain.repository.BoardRepository;
import zip.zipzoong.domain.repository.MemberRepository;
import zip.zipzoong.domain.repository.RoomImageRepository;
import zip.zipzoong.dto.BoardImgForm;
import zip.zipzoong.dto.InsertBoardDto;
import zip.zipzoong.dto.response.BoardListDto;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final RoomImageRepository roomImageRepository;
    private final MemberRepository memberRepository;

    @Value("${file.path}")
    private String uploadPath;

    @Transactional
    public Board createBoard(InsertBoardDto insertBoardDto,BoardImgForm boardImgForm, String memberId) {
        System.out.println("createBoard 실행");
        System.out.println(insertBoardDto.getDatePicker() + "getDatePicker");


        Member member = memberRepository.findByEmail(memberId).get();
        System.out.println("memberId = " + member);
        System.out.println("roomType = " + insertBoardDto.getRoomType());
        System.out.println("address = " + insertBoardDto.getAddress());
        System.out.println("addressDetail = " + insertBoardDto.getAddressDetail());
        System.out.println("roomArea = " + insertBoardDto.getRoomArea());
        System.out.println("roomInfo = " + insertBoardDto.getRoomInfo());
        System.out.println("RentType = " + insertBoardDto.getRentType());
        System.out.println("Deposit = " + insertBoardDto.getDeposit());
        System.out.println("Month = " + insertBoardDto.getMonth());
        System.out.println("isCost = " + insertBoardDto.isCost());
        System.out.println("totalFloor = " + insertBoardDto.getTotalFloor());
        System.out.println("floorsNumber = " + insertBoardDto.getFloorsNumber());
        System.out.println("isElevator = " + insertBoardDto.isElevator());
        System.out.println("isParking = " + insertBoardDto.isParking());
        System.out.println("parkingCost = " + insertBoardDto.getParkingCost());
        System.out.println("title = " + insertBoardDto.getTitle());
        System.out.println("content = " + insertBoardDto.getTextArea());
//        System.out.println("roomImage = " + roomImage);

        Board board = boardRepository.save(
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
                        .floorsNumber(insertBoardDto.getFloorsNumber())
                        .elevator(insertBoardDto.isElevator())
                        .parking(insertBoardDto.isParking())
                        .parkingCost(insertBoardDto.getParkingCost())
                        .title(insertBoardDto.getTitle())
                        .textArea(insertBoardDto.getTextArea())
                        .member(member)
                        .build());

        List<RoomImage> roomImage = insertImage(boardImgForm, board);
        board.setRoomImages(roomImage);
        return board;
    }


    public List<RoomImage> insertImage(BoardImgForm boardImgForm, Board board) {
        System.out.println("boardImgForm = " + boardImgForm);
        System.out.println("insertImage 실행");
        String imagePath = System.getProperty("user.dir") + uploadPath;


        List<RoomImage> roomImages = new ArrayList<>();

        if(boardImgForm.getFiles() != null && !boardImgForm.getFiles().isEmpty()) {
            //만약 이미지파일이 있고, 이미지파일이 비어있지 않다면

            if(boardImgForm.getFiles().size() > 7 ) {
                throw new RuntimeException("이미지는 최대 7개까지 가능합니다.");
            }

            for (MultipartFile file : boardImgForm.getFiles()) {
                // MultipartFile file 안에 인자값으로 들어온 이미지 파일을 하나씩 너어줘

                UUID uuid = UUID.randomUUID();
                // 서버 내부에서 관리하는 파일명은 유일한 이름을 생성하는 UUID를 사용해서 충돌하지 않도록 한다.
                // 각 이미지에대한 식별값 생성
                String imageFileName = uuid + "_" + file.getOriginalFilename();
                // 이미지파일 이름 규칙지정
                File destinationFile = new File(imagePath + imageFileName);

                try {
                    file.transferTo(destinationFile);
                    // 인자값으로 들어온 이미지파일의 저장경로 지정
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                RoomImage image = RoomImage.builder()
                        .url("/boardImages/" + imageFileName)
                        .createdAt(LocalDateTime.now())
                        .board(board)
                        .build();

                roomImageRepository.save(image);

                roomImages.add(image);
            }
        }
        return roomImages;
    }

    public List<BoardListDto> findAllBoardList() {
        List<Board> boardList = boardRepository.findAll();
        List<BoardListDto> boardListDtos = new ArrayList<>();
        for (Board board : boardList) {
            BoardListDto boardListDto = BoardListDto.builder()
                    .rentType(board.getRentType())
                    .roomImage(board.getRoomImages().get(0))
                    .deposit(board.getDeposit())
                    .month(board.getMonth())
                    .floorsNumber(board.getFloorsNumber())
                    .address(board.getAddress())
                    .title(board.getTitle())
                    .roomArea(board.getRoomArea())
                    .build();

            boardListDtos.add(boardListDto);

            System.out.println("rentType" + board.getRentType());
            System.out.println("roomImage" + board.getRoomImages());
            System.out.println("floorsNumber" + board.getFloorsNumber());
            System.out.println("deposit" + board.getDeposit());
            System.out.println("month" + board.getMonth());
            System.out.println("address" + board.getFloorsNumber());
            System.out.println("title" + board.getAddress());
            System.out.println("roomArea" + board.getRoomArea());
        }



        return boardListDtos;

    }

    public String deleteBoard(Long boardId, String memberId) {
        Member findMember = memberRepository.findByEmail((memberId))
                .orElseThrow(()-> new RuntimeException("존재하지 않는 회원입니다."));

        Board board = boardRepository.findByIdAndMember(boardId, findMember)
                .orElseThrow(()->new RuntimeException("게시글을 찾을 수 없습니다."));

        boardRepository.delete(board);

        return "게시글이 삭제됐습니다.";

    }




}
