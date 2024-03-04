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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoomType type;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String addressDetail;

    @Column(nullable = false)
    private int roomArea;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoomInformation roomInfo;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RentType rentType;

    @Column(nullable = false)
    private int deposit;

    @Column(nullable = false)
    private int month;

    @Column(nullable = false)
    private boolean cost;

    private int roomCost;

    @Column(nullable = false)
    private LocalDate datePicker;

    @Column(nullable = false)
    private String totalFloor;

    @Column(nullable = false)
    private String fllorsNumber;

    @Column(nullable = false)
    private boolean elevator;

    @Column(nullable = false)
    private boolean parking;

    private int parkingCost;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String textArea;

    @OneToMany(mappedBy = "roomimage",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RoomImage> roomImages = new ArrayList<>();

    @OneToMany(mappedBy = "like", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Like> likes = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // Member 객체 안에 Board 객체를 넣어준다.
    public void setMember(Member member) {
        this.member = member;
        member.getBoards().add(this);
    }


}
