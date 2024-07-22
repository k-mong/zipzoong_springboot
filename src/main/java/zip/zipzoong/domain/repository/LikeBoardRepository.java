package zip.zipzoong.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zip.zipzoong.domain.entity.Board;
import zip.zipzoong.domain.entity.LikeBoard;
import zip.zipzoong.domain.entity.Member;

public interface LikeBoardRepository extends JpaRepository<LikeBoard, Long> {

    void deleteByMemberAndBoard(Member member, Board board);
}
