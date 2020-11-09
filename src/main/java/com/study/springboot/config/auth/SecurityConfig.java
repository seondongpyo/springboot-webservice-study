package com.study.springboot.config.auth;

import com.study.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity  // Spring Security 설정을 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .headers().frameOptions().disable() // h2-console 화면을 사용하기 위한 설정

                .and().authorizeRequests()    // URL별 권한 관리를 설정하는 옵션의 시작점
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()  // 권한 관리 대상을 지정, permitAll() : 전체 열람 권한 부여
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())    // 해당 api 접근은 USER 권한을 가진 유저만 가능하도록 설정
                .anyRequest().authenticated()   // 설정된 값들 이외의 나머지 URL(anyRequest()들은 인증된 사용자들에게만 허용(authenticated())

                .and().logout().logoutSuccessUrl("/") // 로그아웃 성공 시 "/"로 이동

                .and().oauth2Login()  // OAuth2 로그인 기능에 대한 설정의 진입점
                .userInfoEndpoint() // OAuth2 로그인 성공 이후 사용자 정보를 가져올 떄의 설정을 담당
                .userService(customOAuth2UserService);  // 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록
    }
}
