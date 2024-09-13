package com.example.demo0810.controller;

import com.example.demo0810.dto.PostRequestDto;
import com.example.demo0810.dto.UserRequestDto;
import com.example.demo0810.service.PostService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/post")
@RequiredArgsConstructor
@Data
@RestController
public class PostController {

    private final PostService postService;

    @PostMapping("/write")
    public String postWrite(@RequestBody PostRequestDto postRequestDto) {
        postService.PostSave(postRequestDto);

        return "post_ok";
    }

}
