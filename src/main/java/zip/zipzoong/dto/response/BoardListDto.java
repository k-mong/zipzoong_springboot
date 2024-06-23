package zip.zipzoong.dto.response;

import lombok.Builder;
import zip.zipzoong.domain.entity.RoomImage;

@Builder
public class BoardListDto {
    private RoomImage roomImage;
    private int depoist;
    private int month;
    private int floorsNumber;
    private String address;
    private String title;

}
