package zip.zipzoong.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomOption {
    @Id
    private Long id;

    private boolean fridge;
    private boolean washer;
    private boolean air_conditioner;
    private boolean oven;
    private boolean closet;
    private boolean desk;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;
}
