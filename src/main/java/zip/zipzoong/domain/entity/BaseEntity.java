package zip.zipzoong.domain.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass   // 상속할경우 칼럼 인식
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @CreatedDate    // 생성되어 저장될 때 시간이 자동 저장
    private LocalDateTime createdAt;

    @LastModifiedDate   // 엔티티의 값을 변경할 때 시간이 자동 저장
    private LocalDateTime updatedAt;
}
