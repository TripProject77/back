package com.example.demo0810.dto.user;

import com.example.demo0810.Entity.ImageEntity;
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

    // 이미지 URL을 문자열
    private String profileImageUrl;

    @Builder
    public UserRequestDto(String username, String password, String role, String name, String email, String category, String profileImageUrl) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.name = name;
        this.email = email;
        this.category = category;
        this.profileImageUrl = profileImageUrl;
    }

    public UserEntity toEntity() {

        ImageEntity imageEntity = null;

        if (profileImageUrl != null && !profileImageUrl.isEmpty()) {
            imageEntity = ImageEntity.builder()
                    .profileImageUrl(profileImageUrl)
                    .build();
        }

        return UserEntity.builder()
                .username(username)
                .password(password)
                .role(role)
                .name(name)
                .email(email)
                .category(category)
                .image(imageEntity)  // 이미지 엔티티 설정
                .build();
    }
}