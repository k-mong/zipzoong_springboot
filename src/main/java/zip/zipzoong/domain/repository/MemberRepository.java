package zip.zipzoong.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zip.zipzoong.domain.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
}
