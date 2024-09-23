package com.example.demo0810.service;

import com.example.demo0810.Entity.PostEntity;
import com.example.demo0810.Entity.UserEntity;
import com.example.demo0810.dto.PostRequestDto;
import com.example.demo0810.dto.PostUpdateDto;
import com.example.demo0810.dto.UserUpdateDto;
import com.example.demo0810.exception.CustomException;
import com.example.demo0810.exception.ErrorCode;
import com.example.demo0810.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void PostSave(PostEntity postEntity) {
        postRepository.save(postEntity);
    }

    public List<PostEntity> getAllPost() {

        return postRepository.findAll();
    }

    public void deletePost(Long id) {

        Optional<PostEntity> post = postRepository.findById(id);

        try {
            post.ifPresent(postRepository::delete);
        } catch (Exception e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, ErrorCode.NOT_EXIST_USER);
        }

    }

    public void updatePost(Long id, PostUpdateDto postUpdateDto) {

        Optional<PostEntity> postUpdate = postRepository.findById(id);

        PostEntity post = postUpdate.orElseThrow(() ->
                new CustomException(HttpStatus.BAD_REQUEST, ErrorCode.NOT_EXIST_USER)
        );

        post.setTitle(postUpdateDto.getTitle());
        post.setContent(postUpdateDto.getContent());
        post.setUpdateAtPost(LocalDateTime.now());

        postRepository.save(post);
    }
}
