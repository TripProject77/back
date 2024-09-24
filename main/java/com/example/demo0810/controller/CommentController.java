package com.example.demo0810.controller;

import com.example.demo0810.Entity.CommentEntity;
import com.example.demo0810.Entity.PostEntity;
import com.example.demo0810.Entity.UserEntity;
import com.example.demo0810.dto.comment.CommentRequestDto;
import com.example.demo0810.jwt.JwtUtill;
import com.example.demo0810.repository.PostRepository;
import com.example.demo0810.repository.UserRepository;
import com.example.demo0810.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/post")
public class CommentController {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final JwtUtill jwtUtill;
    private final CommentService commentService;


    @PostMapping("/{id}/comment")
    public ResponseEntity<String> commentWrite(@PathVariable("id") Long id, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");
        String username = null;

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>("UNAUTHORIZED", HttpStatus.UNAUTHORIZED);
        }

        String token = authorizationHeader.substring(7);

        try {
            username = jwtUtill.getUsername(token);
        } catch (Exception e) {
            return new ResponseEntity<>("UNAUTHORIZED", HttpStatus.UNAUTHORIZED);
        }

        UserEntity user = userRepository.findByUsername(username);

        Optional<PostEntity> post = postRepository.findById(id);

        if (!post.isPresent()) {
            return new ResponseEntity<>("Post not found", HttpStatus.NOT_FOUND);
        }

        PostEntity postEntity = post.get();

        CommentEntity commentEntity = commentRequestDto.toComment();
        commentEntity.setUser(user);
        commentEntity.setPost(postEntity);
        commentEntity.setAuthor(username);

        commentService.CommentSave(commentEntity);

        return ResponseEntity.ok("comment_ok");
    }


    @GetMapping("/AllComment/{id}")
    public ResponseEntity<List<CommentEntity>> getCommentList(@PathVariable("id") Long id) {
        List<CommentEntity> comments = commentService.getAllComment(id);
        return ResponseEntity.ok(comments);
    }

}