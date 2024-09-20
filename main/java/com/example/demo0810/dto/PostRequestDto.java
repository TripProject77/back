package com.example.demo0810.dto;

import com.example.demo0810.Entity.PostEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostRequestDto {

    private String title;
    private String content;
    private String writer;

    @Builder
    public PostRequestDto (String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    public PostEntity toPostEntity() {
        return PostEntity.builder()
                .title(title)
                .content(content)
                .writer(writer)
                .build();
    }
}
