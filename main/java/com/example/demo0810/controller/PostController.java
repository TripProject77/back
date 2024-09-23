package com.example.demo0810.controller;

import com.example.demo0810.Entity.PostEntity;
import com.example.demo0810.Entity.UserEntity;
import com.example.demo0810.dto.*;
import com.example.demo0810.jwt.JwtUtill;
import com.example.demo0810.repository.PostRepository;
import com.example.demo0810.repository.UserRepository;
import com.example.demo0810.service.PostService;
import com.example.demo0810.service.UserService;
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

    @PostMapping("/write")
    public ResponseEntity<String> postWrite(@RequestBody PostRequestDto postRequestDto, HttpServletRequest request) {

        // 요청의 Authorization 헤더에서 JWT 토큰 추출
        String authorizationHeader = request.getHeader("Authorization");
        String username = null;

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>("UNAUTHORIZED", HttpStatus.UNAUTHORIZED);
        }

        // "Bearer " 제거하여 순수 JWT 토큰만 추출
        String token = authorizationHeader.substring(7);

        try {
            username = jwtUtill.getUsername(token);
        } catch (Exception e) {
            // 토큰 검증 실패 또는 만료 시
            return new ResponseEntity<>("UNAUTHORIZED", HttpStatus.UNAUTHORIZED);
        }

        UserEntity user = userRepository.findByUsername(username);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        PostEntity postEntity = postRequestDto.toPostEntity();
        postEntity.setUser(user);

        postService.PostSave(postEntity);

        return ResponseEntity.ok("post_ok");
    }

    @GetMapping("/AllPost")
    public ResponseEntity<List<PostEntity>> getPostList() {
        List<PostEntity> post = postService.getAllPost();

        return ResponseEntity.ok(post);
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<PostResponseDto> getPostInfo(@PathVariable("id") Long id, HttpServletRequest request) {

        Optional<PostEntity> post = postRepository.findById(id);

        System.out.println(post);

        if (post.isPresent()) {
            PostEntity postEntity = post.get();

            PostResponseDto postResponseDto = PostResponseDto.fromEntity(postEntity);

            postRepository.updateCount(id);

            return new ResponseEntity<>(postResponseDto, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable("id") Long id, HttpServletRequest request) {

        try {
            postService.deletePost(id);

            return new ResponseEntity<>("Post deleted successfully", HttpStatus.OK);

        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>("Post not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting post", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> updatePost(HttpServletRequest request, @RequestBody PostUpdateDto postUpdateDto) {

        try {
            postService.updatePost(postUpdateDto.getId(), postUpdateDto);
            return new ResponseEntity<>("Post updated successfully", HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("Error updating post", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
