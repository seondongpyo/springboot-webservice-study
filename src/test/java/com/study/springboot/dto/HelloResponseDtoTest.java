package com.study.springboot.dto;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class HelloResponseDtoTest {

    @Test
//    @DisplayName("lombok 라이브러리 테스트")
    public void lombokTest() {
        String name = "test";
        int amount = 10000;

        HelloResponseDto dto = new HelloResponseDto(name, amount);

        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);
    }

}