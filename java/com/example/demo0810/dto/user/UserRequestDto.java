package com.example.demo0810.dto.user;

import com.example.demo0810.Entity.UserEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRequestDto {

    private String username;

    private String password;

    private String role;

    private String name;

    private String email;

    private String category;

    @Builder
    public UserRequestDto(String username, String password, String role, String name, String email, String category) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.name = name;
        this.email = email;
        this.category = category;
    }

    public UserEntity toEntity() {
        return UserEntity.builder()
                .username(username)
                .password(password)
                .role(role)
                .name(name)
                .email(email)
                .category(category)
                .build();
    }
}
