package com.example.demo0810.service;

import com.example.demo0810.Entity.PostEntity;
import com.example.demo0810.Entity.UserEntity;
import com.example.demo0810.dto.PostRequestDto;
import com.example.demo0810.exception.CustomException;
import com.example.demo0810.exception.ErrorCode;
import com.example.demo0810.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
