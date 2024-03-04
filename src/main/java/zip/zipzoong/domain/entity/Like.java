package zip.zipzoong.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    public void setMember(Member member) {
        this.member = member;
        member.getLikes().add(this);
    }

    public void setBoard(Board board) {
        this.board = board;
        board.getLikes().add(this);
    }


}
