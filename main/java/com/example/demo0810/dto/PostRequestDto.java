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

    @Builder
    public PostRequestDto (String title, String content) {
        this.title = title;
        this.content = content;
    }

    public PostEntity toPostEntity() {
        return PostEntity.builder()
                .title(title)
                .content(content)
                .build();
    }
}
