package zip.zipzoong.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zip.zipzoong.domain.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
