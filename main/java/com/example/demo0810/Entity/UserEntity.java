package com.example.demo0810.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class) // Auditing 기능 활성화
@Entity
public class UserEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String post;

    private String role;

    private String name;

    private String email;

    private String category;

    private String profileImage; // 프로필 이미지 URL

    @Builder
    public UserEntity(String username, String password, String role, String name, String email, String category, String profileImage) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.name = name;
        this.email = email;
        this.category = category;
        this.profileImage = profileImage;
    }

    public void updateUser(String name, String email) {
        this.name = name;
        this.email = email;
    }
}