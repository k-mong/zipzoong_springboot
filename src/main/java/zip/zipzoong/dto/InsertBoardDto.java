package zip.zipzoong.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import zip.zipzoong.domain.entity.Member;
import zip.zipzoong.domain.entity.RentType;
import zip.zipzoong.domain.entity.RoomInformation;
import zip.zipzoong.domain.entity.RoomType;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class InsertBoardDto {
    private RoomType roomType;
    private String address;
    private String addressDetail;
    private int roomArea;
    private RoomInformation roomInfo;
    private RentType rentType;
    private int deposit;
    private int month;
    private boolean cost;
    private int roomCost;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate datePicker;
    private String totalFloor;
    private String floorsNumber;
    private boolean elevator;
    private boolean parking;
    private int parkingCost;
    private String title;
    private String textArea;
    private Member member;
    private List<MultipartFile> files;

}
