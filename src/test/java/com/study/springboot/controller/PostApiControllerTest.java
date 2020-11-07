package com.study.springboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.springboot.domain.Post;
import com.study.springboot.dto.PostSaveRequestDto;
import com.study.springboot.dto.PostUpdateRequestDto;
import com.study.springboot.repository.PostRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

//    @BeforeEach
    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

//    @AfterEach
    @After
    public void tearDown() {
        postRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
//    @DisplayName("게시물 저장")
    public void savePost() throws Exception {
        String title = "title";
        String content = "content";
        String author = "author";

        PostSaveRequestDto requestDto = PostSaveRequestDto.builder()
                                                        .title(title)
                                                        .content(content)
                                                        .author(author)
                                                        .build();

        String url = "http://localhost:" + port + "/api/v1/post";

//        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody()).isGreaterThan(0L);
        mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
            .andExpect(status().isOk());

        List<Post> postList = postRepository.findAll();
        assertThat(postList.get(0).getTitle()).isEqualTo(title);
        assertThat(postList.get(0).getContent()).isEqualTo(content);
        assertThat(postList.get(0).getAuthor()).isEqualTo(author);
    }

    @Test
    @WithMockUser(roles = "USER")
//    @DisplayName("게시물 수정")
    public void updatePost() throws Exception {
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

//        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody()).isGreaterThan(0L);
        mvc.perform(put(url).contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(new ObjectMapper().writeValueAsString(requestDto)))
            .andExpect(status().isOk());

        List<Post> postList = postRepository.findAll();
        assertThat(postList.get(0).getTitle()).isEqualTo(updatedTitle);
        assertThat(postList.get(0).getContent()).isEqualTo(updatedContent);
    }
}