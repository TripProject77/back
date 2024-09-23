package com.example.demo0810.dto;

import com.example.demo0810.Entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto {

    private String name;
    private String email;
    private LocalDateTime updateAtPost = LocalDateTime.now();

    public UserEntity UpdateUserEntity() {
        return UserEntity.builder()
                .name(name)
                .email(email)
                .build();
    }
}

