package com.example.demo0810.dto;

import com.example.demo0810.Entity.PostEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostResponseDto {

    private String title;
    private String content;
    private String writer;
    private Long count;

    @Builder
    public PostResponseDto(String title, String content, String writer, Long count) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.count = count;
    }

    public static PostResponseDto fromEntity(PostEntity postEntity) {
        return PostResponseDto.builder()
                .title(postEntity.getTitle())
                .content(postEntity.getContent())
                .writer(postEntity.getWriter())
                .count(postEntity.getCount())
                .build();
    }
}

