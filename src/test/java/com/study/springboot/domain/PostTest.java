package com.study.springboot.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PostTest {

    @Test
    @DisplayName("Post 엔티티 테스트")
    void postEntity() {
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