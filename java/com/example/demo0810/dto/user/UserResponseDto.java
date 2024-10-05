package com.example.demo0810.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private String username;
    private String role;
    private String category;
    private String name;
    private String email;
    private LocalDateTime createdDate;
    private String profileImage;
}
