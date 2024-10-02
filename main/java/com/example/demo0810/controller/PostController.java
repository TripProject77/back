package com.example.demo0810.controller;

import com.example.demo0810.Entity.PostEntity;
import com.example.demo0810.Entity.UserEntity;
import com.example.demo0810.dto.post.PostRequestDto;
import com.example.demo0810.dto.post.PostResponseDto;
import com.example.demo0810.dto.post.PostUpdateDto;
import com.example.demo0810.exception.CustomException;
import com.example.demo0810.exception.ErrorCode;
import com.example.demo0810.jwt.JwtUtill;
import com.example.demo0810.repository.PostRepository;
import com.example.demo0810.repository.UserRepository;
import com.example.demo0810.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/post")
@RequiredArgsConstructor
@Data
@RestController
public class PostController {

    private final PostService postService;
    private final PostRepository postRepository;
    private final JwtUtill jwtUtill;
    private final UserRepository userRepository;

    // 게시글 작성
    @PostMapping("/write")
    public void postWrite(@RequestBody PostRequestDto postRequestDto, HttpServletRequest request) {

        postService.PostSave(postRequestDto, request);

    }

    // 게시글 수정
    @PostMapping("/update")
    public void updatePost(@RequestBody PostUpdateDto postUpdateDto) {

        postService.updatePost(postUpdateDto.getId(), postUpdateDto);

    }

    // 게시글 삭제
    @DeleteMapping("/delete/{id}")
    public void deletePost(@PathVariable("id") Long id) {

        postService.deletePost(id);

    }

    // 게시글 상세 정보
    @GetMapping("/info/{id}")
    public PostResponseDto PostInfo(@PathVariable("id") Long id) {

        Optional<PostEntity> post = postRepository.findById(id);

        if (post.isPresent()) {
            PostEntity postEntity = post.get();

            postRepository.updateCount(id);

            return new PostResponseDto(postEntity.getId(), postEntity.getTitle(), postEntity.getContent(), postEntity.getWriter(), postEntity.getCount(), postEntity.getCreatedDate(), postEntity.getUpdatedDate());
        } else {
            throw new CustomException(HttpStatus.BAD_REQUEST, ErrorCode.NOT_EXIST_POST);
        }
    }

    // 게시물 리스트
    @GetMapping("/postList")
    public List<PostEntity> getPostList() {

        return postService.getAllPost();

    }
}
