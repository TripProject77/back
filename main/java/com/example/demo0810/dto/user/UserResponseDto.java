package com.example.demo0810.dto.user;

import com.example.demo0810.Entity.ImageEntity;
import com.example.demo0810.Entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserResponseDto {
    private String username;
    private String role;
    private String category;
    private String name;
    private String email;
    private LocalDateTime createdDate;
    private String profileImageUrl;

}


