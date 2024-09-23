package com.example.demo0810.dto;

import com.example.demo0810.Entity.PostEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PostResponseDto {

    private Long id;
    private String title;
    private String content;
    private String writer;
    private int count;
    private LocalDateTime createAtPost = LocalDateTime.now();
    private LocalDateTime updateAtPost;

    @Builder
    public PostResponseDto(Long id, String title, String content, String writer, int count, LocalDateTime createAtPost, LocalDateTime updateAtPost) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.count = count;
        this.createAtPost = createAtPost;
        this.updateAtPost = updateAtPost;
    }


    public static PostResponseDto fromEntity(PostEntity postEntity) {
        return PostResponseDto.builder()
                .id(postEntity.getId())
                .title(postEntity.getTitle())
                .content(postEntity.getContent())
                .writer(postEntity.getWriter())
                .count(postEntity.getCount())
                .createAtPost(postEntity.getCreateAtPost())
                .updateAtPost(postEntity.getUpdateAtPost())
                .build();
    }
}

