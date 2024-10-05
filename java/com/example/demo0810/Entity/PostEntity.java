package com.example.demo0810.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "board")
@Entity
public class PostEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private String writer;

    private String mbti;

    private String place;

    private LocalDate startDate;

    private LocalDate endDate;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PostTagMap> postTagMaps = new ArrayList<>();  // 태그와의 매핑 추가

    // 조회수
    private int count = 0;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Builder
    public PostEntity(String title, String content, String writer, String mbti, String place, LocalDate startDate, LocalDate endDate) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.count = 0;
        this.mbti = mbti;
        this.place = place;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void updatePost(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
