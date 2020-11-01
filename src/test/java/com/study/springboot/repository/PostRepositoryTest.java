package com.study.springboot.repository;

import com.study.springboot.domain.Post;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @AfterEach
    void cleanUp() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("게시글 저장")
    void savePost() {
        Post post = Post.builder()
                        .title("테스트")
                        .content("테스트")
                        .author("홍길동")
                        .build();

        postRepository.save(post);
        List<Post> postList = postRepository.findAll();
        Post findPost = postList.get(0);

        assertThat(post.getTitle()).isEqualTo(findPost.getTitle());
        assertThat(post.getContent()).isEqualTo(findPost.getContent());
        assertThat(post.getAuthor()).isEqualTo(findPost.getAuthor());
    }

    @Test
    @DisplayName("JPA Auditing 기능 테스트")
    void jpaAuditingTest() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        Post post = Post.builder()
                        .title("title")
                        .content("content")
                        .author("author")
                        .build();
        postRepository.save(post);

        List<Post> postList = postRepository.findAll();
        Post savedPost = postList.get(0);

        System.out.println("current time is " + currentDateTime);
        System.out.println("savedPost is created at " + savedPost.getCreatedDate());
        System.out.println("savedPost is modified at " + savedPost.getModifiedDate());

        assertThat(savedPost.getCreatedDate()).isAfter(currentDateTime);
        assertThat(savedPost.getModifiedDate()).isAfter(currentDateTime);
    }

}