package com.example.demo0810.controller;

import com.example.demo0810.Entity.UserEntity;
import com.example.demo0810.dto.user.UserRequestDto;
import com.example.demo0810.dto.user.UserResponseDto;
import com.example.demo0810.dto.user.UserUpdateDto;
import com.example.demo0810.exception.CustomException;
import com.example.demo0810.exception.ErrorCode;
import com.example.demo0810.jwt.JwtUtill;
import com.example.demo0810.repository.UserRepository;
import com.example.demo0810.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtUtill jwtUtill;

    @GetMapping("/")
    public String main() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();

        return "main Controller" + " - " + username + " - " + role;
    }

    // 회원 가입
    @PostMapping("/join")
    public void joinProcess(@RequestBody UserRequestDto userRequestDto) {

        userService.joinP(userRequestDto);

    }

    // 사용자 정보 조회
    @Secured("ROLE_USER")
    @GetMapping("/info")
    public ResponseEntity<?> userInfo(HttpServletRequest request) {

        // 요청의 Authorization 헤더에서 JWT 토큰 추출
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>("UNAUTHORIZED", HttpStatus.UNAUTHORIZED);
        }

        // "Bearer " 제거하여 순수 JWT 토큰만 추출
        String token = authorizationHeader.substring(7);

        // JWT 토큰에서 사용자 정보 추출
        try {
            String username = jwtUtill.getUsername(token);
            String role = jwtUtill.getRole(token);
            String category = jwtUtill.getCategory(token);
            String name = jwtUtill.getName(token);
            String email = jwtUtill.getEmail(token);
            UserEntity user = userRepository.findByUsername(username);

            // 사용자 정보 객체 생성 (필요 시 DB에서 사용자 정보 조회)
            UserResponseDto userInfoResponse = new UserResponseDto(username, role, category, name, email, user.getCreatedDate(), user.getProfileImage());

            return new ResponseEntity<>(userInfoResponse, HttpStatus.OK);
        } catch (Exception e) {
            // 토큰 검증 실패 또는 만료 시
            return new ResponseEntity<>("UNAUTHORIZED", HttpStatus.UNAUTHORIZED);
        }
    }

    // 사용자 정보 수정
    @PostMapping("/update")
    public void updateUser(HttpServletRequest request, @RequestBody UserUpdateDto userUpdateDto) {

        userService.updateUser(userUpdateDto, request);

    }

    // 사용자 정보 삭제
    @DeleteMapping("/delete/{username}")
    public void deleteUser(@PathVariable("username") String username) {

        System.out.println("Received username: " + username);

        userService.deleteUser(username);

    }

    // 유저 리스트
    @GetMapping("/userList")
    public List<UserEntity> getUserList() {
        return userService.getAllUser();
    }

    @PostMapping("/{username}/{uploadedImageUrl}/updateImage")
    public void updateProfileImage(@PathVariable("username") String username, @PathVariable("uploadedImageUrl") String uploadedImageUrl) {

        UserEntity user = userRepository.findByUsername(username);

        user.setProfileImage(uploadedImageUrl);
        userRepository.save(user);
    }
}


