package com.example.demo0810.service;

import com.example.demo0810.Entity.PostEntity;
import com.example.demo0810.Entity.PostTagMap;
import com.example.demo0810.Entity.Tag;
import com.example.demo0810.Entity.UserEntity;
import com.example.demo0810.dto.post.PostRequestDto;
import com.example.demo0810.dto.post.PostResponseDto;
import com.example.demo0810.dto.post.PostUpdateDto;
import com.example.demo0810.exception.CustomException;
import com.example.demo0810.exception.ErrorCode;
import com.example.demo0810.jwt.JwtUtill;
import com.example.demo0810.repository.PostRepository;
import com.example.demo0810.repository.PostTagMapRepository;
import com.example.demo0810.repository.TagRepository;
import com.example.demo0810.repository.UserRepository;
import io.jsonwebtoken.Jwt;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final TagRepository tagRepository;  // 태그 저장을 위한 리포지토리
    private final PostTagMapRepository postTagMapRepository;  // PostTagMap 저장 리포지토리
    private final JwtUtill jwtUtill;

    // 게시글 작성
    @Transactional
    public void PostSave(PostRequestDto postRequestDto, HttpServletRequest request) {

        // 요청의 Authorization 헤더에서 JWT 토큰 추출
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new CustomException(HttpStatus.BAD_REQUEST, ErrorCode.UNATHORIZATION, "invalid jwt token");
        }

        String token = authorizationHeader.substring(7);
        String username = jwtUtill.getUsername(token);
        UserEntity user = userRepository.findByUsername(username);

        if (user == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, ErrorCode.NOT_EXIST_USER);
        }

        // PostEntity 저장
        PostEntity post = postRequestDto.toPostEntity();
        post.setUser(user);
        postRepository.save(post);

        // 태그 처리 및 PostTagMap 저장
        if (postRequestDto.getTags() != null && !postRequestDto.getTags().isEmpty()) {
            for (String tagContent : postRequestDto.getTags()) {
                // 태그가 존재하는지 확인하고 없으면 새로 생성
                Tag tag = tagRepository.findByTagContent(tagContent)
                        .orElseGet(() -> {
                            Tag newTag = new Tag();
                            newTag.setTagContent(tagContent);
                            return tagRepository.save(newTag);
                        });

                // PostTagMap 저장
                PostTagMap postTagMap = new PostTagMap();
                postTagMap.setPost(post);
                postTagMap.setTag(tag);
                postTagMapRepository.save(postTagMap);
            }
        }
    }

    // 게시글 수정
    @Transactional
    public void updatePost(Long id, PostUpdateDto postUpdateDto) {

        Optional<PostEntity> postUpdate = postRepository.findById(id);

        PostEntity post = postUpdate.orElseThrow(() ->
                new CustomException(HttpStatus.BAD_REQUEST, ErrorCode.NOT_EXIST_POST)
        );

        post.updatePost(postUpdateDto.getTitle(), postUpdateDto.getContent());

        postRepository.save(post);
    }

    // 게시글 삭제
    @Transactional
    public void deletePost(Long id) {

        Optional<PostEntity> post = postRepository.findById(id);

        try {
            post.ifPresent(postRepository::delete);
        } catch (Exception e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, ErrorCode.POST_DELETE_ERROR, "post deleting error");
        }

    }

    // 게시글 리스트
    public List<PostEntity> getAllPost() {

        return postRepository.findAll();
    }
}
