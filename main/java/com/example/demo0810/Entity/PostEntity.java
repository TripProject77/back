package com.example.demo0810.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name="board")
@Entity
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private String writer;

    // 조회수
    private int count = 0;

    private LocalDateTime createAtPost;

    @PrePersist
    public void prePersist() {
        this.createAtPost = LocalDateTime.now();
    }

    private LocalDateTime updateAtPost;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private UserEntity user;

    @Builder
    public PostEntity (String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.count = 0;
    }
}