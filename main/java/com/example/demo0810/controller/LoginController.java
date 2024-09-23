package com.example.demo0810.controller;

import com.example.demo0810.Entity.UserEntity;
import com.example.demo0810.dto.UserRequestDto;
import com.example.demo0810.dto.UserResponseDto;
import com.example.demo0810.dto.UserUpdateDto;
import com.example.demo0810.jwt.JwtUtill;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class LoginController {

    private final UserService userService;
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


    @PostMapping("/join")
    public String joinProcess(@RequestBody UserRequestDto userRequestDto) {
        userService.joinP(userRequestDto);

        return "signup_ok";
    }

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
            String password = jwtUtill.getPassword(token);

            // 사용자 정보 객체 생성 (필요 시 DB에서 사용자 정보 조회)
            UserResponseDto userInfoResponse = new UserResponseDto(username, role, category, name, email, password);

            return new ResponseEntity<>(userInfoResponse, HttpStatus.OK);
        } catch (Exception e) {
            // 토큰 검증 실패 또는 만료 시
            return new ResponseEntity<>("UNAUTHORIZED", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateUser(HttpServletRequest request, @RequestBody UserUpdateDto userUpdateDto) {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>("UNAUTHORIZED", HttpStatus.UNAUTHORIZED);
        }

        String token = authorizationHeader.substring(7);

        try {
            String username = jwtUtill.getUsername(token);

            // DB에 저장된 사용자 정보 수정 처리
            userService.updateUser(username, userUpdateDto);
            return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable("username") String username, HttpServletRequest request) {
        System.out.println("Received username: " + username);  // 로그로 확인


        // 요청의 Authorization 헤더에서 JWT 토큰 추출
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>("UNAUTHORIZED", HttpStatus.UNAUTHORIZED);
        }

        // "Bearer " 제거하여 순수 JWT 토큰만 추출
        String token = authorizationHeader.substring(7);

        try {
            // 유저 삭제 서비스 호출
            userService.deleteUser(username);

            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/AllUser")
    public ResponseEntity<List<UserEntity>> getUserList() {
        List<UserEntity> user = userService.getAllUser();
        return ResponseEntity.ok(user);
    }
}


