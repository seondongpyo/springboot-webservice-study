package com.study.springboot.controller;

import com.study.springboot.domain.Post;
import com.study.springboot.dto.PostSaveRequestDto;
import com.study.springboot.dto.PostUpdateRequestDto;
import com.study.springboot.repository.PostRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
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
    @DisplayName("게시물 저장")
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

    @Test
    @DisplayName("게시물 수정")
    void updatePost() {
        Post post = Post.builder()
                        .title("title")
                        .content("content")
                        .author("author")
                        .build();

        Post savedPost = postRepository.save(post);

        Long updateId = savedPost.getId();
        String updatedTitle = "title2";
        String updatedContent = "content2";
        PostUpdateRequestDto requestDto = PostUpdateRequestDto.builder()
                                                            .title(updatedTitle)
                                                            .content(updatedContent)
                                                            .build();

        HttpEntity<PostUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);
        String url = "http://localhost:" + port + "/api/v1/post/" + updateId;
        ResponseEntity<Long> responseEntity
                = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Post> postList = postRepository.findAll();
        assertThat(postList.get(0).getTitle()).isEqualTo(updatedTitle);
        assertThat(postList.get(0).getContent()).isEqualTo(updatedContent);
    }
}