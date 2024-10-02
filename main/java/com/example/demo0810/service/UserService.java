package com.example.demo0810.service;

import com.example.demo0810.Entity.RefreshEntity;
import com.example.demo0810.Entity.UserEntity;
import com.example.demo0810.dto.user.UserRequestDto;
import com.example.demo0810.dto.user.UserResponseDto;
import com.example.demo0810.dto.user.UserUpdateDto;
import com.example.demo0810.exception.CustomException;
import com.example.demo0810.exception.ErrorCode;
import com.example.demo0810.jwt.JwtUtill;
import com.example.demo0810.repository.RefreshRepository;
import com.example.demo0810.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RefreshRepository refreshRepository;
    private final JwtUtill jwtUtill;

    // 유저 등록
    @Transactional
    public void joinP(UserRequestDto userRequestDto) {

        // 비밀번호 암호화
        String encodedPassword = bCryptPasswordEncoder.encode(userRequestDto.getPassword());
        userRequestDto.setPassword(encodedPassword);

        Boolean isExist = userRepository.existsByUsername(userRequestDto.getUsername());

        if (isExist) {
            // 중복된 ID일 경우 CustomException 발생
            throw new CustomException(HttpStatus.BAD_REQUEST, ErrorCode.DUPLICATED_ID_FAILED);
        }

        // 권한 설정
        if (!userRequestDto.getUsername().equals("admin0515")) {
            userRequestDto.setRole("ROLE_USER");
        } else {
            userRequestDto.setRole("ROLE_ADMIN");
        }

        userRepository.save(userRequestDto.toEntity());
    }

    // 유저 수정
    @Transactional
    public void updateUser(UserUpdateDto userUpdateDto, HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new CustomException(HttpStatus.BAD_REQUEST, ErrorCode.UNATHORIZATION);
        }

        String token = authorizationHeader.substring(7);

        String username = jwtUtill.getUsername(token);

        UserEntity user;

        try {
            user = userRepository.findByUsername(username);
        } catch (CustomException e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, ErrorCode.NOT_EXIST_USER);
        }

        // 사용자 name과 email만 업데이트
        user.updateUser(userUpdateDto.getName(), userUpdateDto.getEmail());

        // DB에 저장
        userRepository.save(user);
    }

    // 유저 삭제
    @Transactional
    public void deleteUser(String username) {

        try {
            UserEntity user = userRepository.findByUsername(username);

            userRepository.delete(user);
        } catch (Exception e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, ErrorCode.NOT_EXIST_USER);
        }
    }

    // 유저 리스트
    public List<UserEntity> getAllUser() {
        return userRepository.findAll();
    }

    public void addRefreshEntity(String username, String refresh, Long expired) {
        Date date = new Date(System.currentTimeMillis() + expired);

        RefreshEntity refreshEntity = new RefreshEntity();
        refreshEntity.setUsername(username);
        refreshEntity.setRefresh(refresh);
        refreshEntity.setExpiration(date.toString());

        refreshRepository.save(refreshEntity);
    }

    public Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60);
        cookie.setHttpOnly(true);

        return cookie;
    }
}
