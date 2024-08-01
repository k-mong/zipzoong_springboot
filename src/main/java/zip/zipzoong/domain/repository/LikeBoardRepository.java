package zip.zipzoong.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zip.zipzoong.domain.entity.Board;
import zip.zipzoong.domain.entity.LikeBoard;
import zip.zipzoong.domain.entity.Member;

import java.util.List;
import java.util.Optional;

public interface LikeBoardRepository extends JpaRepository<LikeBoard, Long> {

    void deleteByMemberAndBoard(Member member, Board board);
    List<LikeBoard> findByMember(Member member);

    Optional<LikeBoard> findByMemberAndBoard(Member member, Board board);
}
