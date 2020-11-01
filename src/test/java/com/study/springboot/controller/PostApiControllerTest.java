package com.study.springboot.controller;

import com.study.springboot.domain.Post;
import com.study.springboot.dto.PostSaveRequestDto;
import com.study.springboot.repository.PostRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostRepository postRepository;

    @AfterEach
    public void tearDown() {
        postRepository.deleteAll();
    }

    @Test
    void savePost() {
        String title = "title";
        String content = "content";
        String author = "author";

        PostSaveRequestDto requestDto = PostSaveRequestDto.builder()
                                                        .title(title)
                                                        .content(content)
                                                        .author(author)
                                                        .build();

        String url = "http://localhost:" + port + "/api/v1/post";
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Post> postList = postRepository.findAll();
        assertThat(postList.get(0).getTitle()).isEqualTo(title);
        assertThat(postList.get(0).getContent()).isEqualTo(content);
        assertThat(postList.get(0).getAuthor()).isEqualTo(author);
    }
}