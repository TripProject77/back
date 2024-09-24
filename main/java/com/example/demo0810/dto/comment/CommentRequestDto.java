package com.example.demo0810.dto.comment;

import com.example.demo0810.Entity.CommentEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentRequestDto {

    private String comment;


    @Builder
    public CommentRequestDto (String comment) {
        this.comment = comment;
    }

    public CommentEntity toComment() {
        return CommentEntity.builder()
                .comment(comment)
                .build();
    }
}
