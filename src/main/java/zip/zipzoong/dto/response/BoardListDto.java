package zip.zipzoong.dto.response;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import zip.zipzoong.domain.entity.RentType;
import zip.zipzoong.domain.entity.RoomImage;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardListDto {
    private RentType rentType;
    private RoomImage roomImage;
    private int deposit;
    private int month;
    private String floorsNumber;
    private String address;
    private String title;
    private int roomArea;
}