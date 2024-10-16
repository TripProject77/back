package com.example.demo0810.service;

import com.example.demo0810.Entity.post.PostEntity;
import com.example.demo0810.Entity.post.PostTagMap;
import com.example.demo0810.Entity.post.Tag;
import com.example.demo0810.Entity.user.UserEntity;
import com.example.demo0810.dto.post.PostRequestDto;
import com.example.demo0810.dto.post.PostUpdateDto;
import com.example.demo0810.exception.CustomException;
import com.example.demo0810.exception.ErrorCode;
import com.example.demo0810.jwt.JwtUtill;
import com.example.demo0810.repository.post.PostRepository;
import com.example.demo0810.repository.post.PostTagMapRepository;
import com.example.demo0810.repository.post.TagRepository;
import com.example.demo0810.repository.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public void PostSave(PostRequestDto postRequestDto, HttpServletRequest request, MultipartFile file) {

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

        // 파일 저장 로직 추가
        if (file != null && !file.isEmpty()) {
            String imageUrl = saveFile(file);  // 파일을 저장하고 경로 반환
            postRequestDto.setPostImageUrl(imageUrl); // 이미지 URL 저장
        }


        // PostEntity 저장
        PostEntity post = postRequestDto.toPostEntity();
        post.setUser(user);

        if (post.getPostImage() != null) {
            post.getPostImage().setPost(post);
        }

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

    public String saveFile(MultipartFile file) {

        // 파일을 저장할 기본 경로
        String directoryPath = "C:/Image";

        // 디렉토리가 존재하지 않으면 생성
        File directory = new File(directoryPath);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        // 파일 이름 생성
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        // "C:/Image" -> 이 경로에 저장
        File saveFile = new File(directoryPath, fileName);

        try {
            file.transferTo(saveFile);
        } catch (IOException e) {
            System.out.println("파일 저장 중 오류 발생: " + e.getMessage());  // 로그에 구체적인 오류 출력
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.FILE_SAVE_FAILED, e);
        }

        // 저장된 파일의 경로 반환
        return "/profileImages/" + fileName;  // 정적 리소스의 URL 경로 반환
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
