package zip.zipzoong.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zip.zipzoong.domain.entity.RentType;
import zip.zipzoong.domain.entity.RoomImage;
import zip.zipzoong.domain.entity.RoomInformation;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardDetailDto {

    private List<String> roomImage;
    private Long id;
    private RentType rentType;
    private int deposit;
    private int month;
    private int roomCost;
    private int roomArea;
    private RoomInformation roomInformation;
    private String floorsNumber;
    private boolean parking;
    private int parkingCost;
    private LocalDate datePicker;
    private boolean elevator;
    private String roomOption;
    private String title;
    private String textArea;

}
