package com.example.demo0810.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String post;

    private String role;

    private String name;

    private String email;

    @CreatedDate
    private LocalDateTime createAt = LocalDateTime.now();

    @LastModifiedDate
    private LocalDateTime updateAt = LocalDateTime.now();

    @Builder
    public UserEntity(String username, String password, String role, String name, String email) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.name = name;
        this.email = email;
    }
}