package com.example.demo0810.dto;

import com.example.demo0810.Entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private String username;
    private String role;
    private String category;
    private String name;
    private String email;
    private String password;
}
