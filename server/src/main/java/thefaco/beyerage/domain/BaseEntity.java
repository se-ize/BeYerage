package thefaco.beyerage.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * 등록일, 수정일 MappedSuperClass
 */
@MappedSuperclass
@Getter @Setter(AccessLevel.PROTECTED)
public abstract class BaseEntity {

    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

}
