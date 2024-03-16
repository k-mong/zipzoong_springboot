package zip.zipzoong.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Board extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoomType type;

    private String address;

    private String addressDetail;

    private int roomArea;

    @Enumerated(EnumType.STRING)
    private RoomInformation roomInfo;

    @Enumerated(EnumType.STRING)
    private RentType rentType;

    private int deposit;

    private int month;

    private boolean cost;

    private int roomCost;

    private LocalDate datePicker;

    private String totalFloor;

    private String fllorsNumber;

    private boolean elevator;

    private boolean parking;

    private int parkingCost;

    private String title;

    private String textArea;

//    @OneToMany(mappedBy = "board",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<RoomImage> roomImages = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String url;


}
