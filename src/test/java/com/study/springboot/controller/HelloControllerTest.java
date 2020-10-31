package com.study.springboot.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = HelloController.class)
class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("/hello 테스트")
    void hello() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello Spring Boot!"));
    }

    @Test
    @DisplayName("/hello/dto 테스트")
    void helloDto() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/hello/dto")
                    .param("name", "hello") // param()은 String만 가능하다
                    .param("amount", String.valueOf(1000)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("hello"))) // jsonPath() : JSON 응답 값을 필드별로 검증 가능
                .andExpect(jsonPath("$.amount", is(1000))); // $를 기준으로 필드명을 명시한다
    }
}