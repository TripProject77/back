package com.example.demo0810.service;

import com.example.demo0810.dto.PostRequestDto;
import com.example.demo0810.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void PostSave(PostRequestDto postRequestDto) {
        postRepository.save(postRequestDto.toPostEntity());
    }

}
