package zip.zipzoong.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zip.zipzoong.domain.entity.RentType;
import zip.zipzoong.domain.entity.RoomImage;
import zip.zipzoong.domain.entity.RoomInformation;

import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardDetailDto {

    private String roomImage;
    private Long id;
    private RentType rentType;
    private int deposit;
    private int month;
    private int roomArea;
    private RoomInformation roomInformation;
    private String floorsNumber;
    private LocalDate datePicker;
    private boolean elevator;
    private String roomOption;
    private String title;

}
