package com.study.springboot.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class PostTest {

    @Test
//    @DisplayName("Post 엔티티 테스트")
    public void postEntity() {
        Post post = Post.builder()
                        .title("Book")
                        .content("Java")
                        .author("홍길동")
                        .build();


        assertThat(post.getTitle()).isEqualTo("Book");
        assertThat(post.getContent()).isEqualTo("Java");
        assertThat(post.getAuthor()).isEqualTo("홍길동");
    }

}