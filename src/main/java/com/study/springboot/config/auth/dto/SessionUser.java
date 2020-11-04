package com.study.springboot.config.auth.dto;

import com.study.springboot.domain.User;
import lombok.Getter;

import java.io.Serializable;

/**
 * 인증된 사용자 정보만 필요하므로 name, email, picture만 팔드로 선언한다.
 *
 * User 클래스를 세션에 저장하려고 하면 직렬화 에러가 발생하는데,
 * 이 때 User 클래스는 엔티티이므로 다른 엔티티와 연관관계가 형성되면
 * 성능 이슈나 부수 효과(side effect)가 발생할 수 있다.
 * 따라서 운영 및 유지보수 측면을 고려했을 때 직렬화 기능을 가진 세션용 Dto를 추가로 생성하여 사용하는 것이 좋다.
 */
@Getter
public class SessionUser implements Serializable {

    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
