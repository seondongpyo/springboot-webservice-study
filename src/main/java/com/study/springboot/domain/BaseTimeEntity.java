package com.study.springboot.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass   // 다른 엔티티 클래스가 해당 클래스를 상속할 경우, 해당 클래스의 필드들도 컬럼으로 인식하도록 설정
@EntityListeners(AuditingEntityListener.class)  // Auditing 기능 포함시키기
public abstract class BaseTimeEntity {

    @CreatedDate    // 엔티티가 생성되어 저장될 떄의 시간을 자동으로 저장
    private LocalDateTime createdDate;

    @LastModifiedDate   // 엔티티 값이 변경될 때의 시간을 자동으로 저장
    private LocalDateTime modifiedDate;
}
