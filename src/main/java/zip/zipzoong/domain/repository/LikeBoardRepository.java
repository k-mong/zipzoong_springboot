package zip.zipzoong.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zip.zipzoong.domain.entity.Board;
import zip.zipzoong.domain.entity.LikeBoard;
import zip.zipzoong.domain.entity.Member;

import java.util.List;

public interface LikeBoardRepository extends JpaRepository<LikeBoard, Long> {

    void deleteByMemberAndBoard(Member member, Board board);
    List<LikeBoard> findLikeBoard(Member member);
}
