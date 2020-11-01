package com.study.springboot.service;

import com.study.springboot.domain.Post;
import com.study.springboot.dto.PostResponseDto;
import com.study.springboot.dto.PostSaveRequestDto;
import com.study.springboot.dto.PostUpdateRequestDto;
import com.study.springboot.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    public Long save(PostSaveRequestDto requestDto) {
        return postRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostUpdateRequestDto requestDto) {
        Post post = postRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));
        post.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    @Transactional
    public PostResponseDto findById(Long id) {
        Post entity = postRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));

        return new PostResponseDto(entity);
    }
}
