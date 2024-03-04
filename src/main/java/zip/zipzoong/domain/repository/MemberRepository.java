package zip.zipzoong.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zip.zipzoong.domain.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
