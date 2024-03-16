package zip.zipzoong.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;
import zip.zipzoong.domain.entity.RentType;
import zip.zipzoong.domain.entity.RoomInformation;
import zip.zipzoong.domain.entity.RoomType;

import java.time.LocalDate;
import java.util.List;

@Getter
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
    private LocalDate datePicker;
    private String totalFloor;
    private String follrsNumber;
    private boolean elevator;
    private boolean parking;
    private int parkingCost;
    private String title;
    private String textArea;
    private List<MultipartFile> files;

}
