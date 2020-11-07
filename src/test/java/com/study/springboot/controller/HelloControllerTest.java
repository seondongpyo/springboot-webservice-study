package com.study.springboot.controller;

import com.study.springboot.config.auth.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HelloController.class,
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)}
)
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser(roles = "USER")
//    @DisplayName("/hello 테스트")
    public void hello() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello Spring Boot!"));
    }

    @Test
    @WithMockUser(roles = "USER")
//    @DisplayName("/hello/dto 테스트")
    public void helloDto() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/hello/dto")
                .param("name", "hello") // param()은 String만 가능하다
                .param("amount", String.valueOf(1000)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("hello"))) // jsonPath() : JSON 응답 값을 필드별로 검증 가능
                .andExpect(jsonPath("$.amount", is(1000))); // $를 기준으로 필드명을 명시한다
    }
}