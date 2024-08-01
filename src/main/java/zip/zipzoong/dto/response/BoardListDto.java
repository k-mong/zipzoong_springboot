package zip.zipzoong.dto.response;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import zip.zipzoong.domain.entity.RentType;
import zip.zipzoong.domain.entity.RoomImage;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardListDto {
    private Long id;
    private RentType rentType;
    private String roomImage;
    private int deposit;
    private int month;
    private String floorsNumber;
    private String address;
    private String title;
    private int roomArea;
    private boolean likeBoard;
}
