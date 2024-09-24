package com.example.demo0810.service;

import com.example.demo0810.Entity.RefreshEntity;
import com.example.demo0810.Entity.UserEntity;
import com.example.demo0810.dto.user.UserRequestDto;
import com.example.demo0810.dto.user.UserUpdateDto;
import com.example.demo0810.exception.CustomException;
import com.example.demo0810.exception.ErrorCode;
import com.example.demo0810.repository.RefreshRepository;
import com.example.demo0810.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RefreshRepository refreshRepository;

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

    public void updateUser(String username, UserUpdateDto userUpdateDto) {
        // DB에서 사용자 정보 조회
        UserEntity user;

        try {
            user = userRepository.findByUsername(username);
        } catch (Exception e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, ErrorCode.NOT_EXIST_USER);
        }

        // 사용자 name과 email만 업데이트
        user.setName(userUpdateDto.getName());
        user.setEmail(userUpdateDto.getEmail());
        user.setUpdateAt(LocalDateTime.now()); // updateAt 필드 갱신

        // DB에 저장
        userRepository.save(user);
    }

    public void deleteUser(String username) {
        UserEntity user;

        try {
            user = userRepository.findByUsername(username);
            userRepository.delete(user);
        } catch (Exception e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, ErrorCode.NOT_EXIST_USER);
        }
    }

    public List<UserEntity> getAllUser() {
        return userRepository.findAll();
    }
}
