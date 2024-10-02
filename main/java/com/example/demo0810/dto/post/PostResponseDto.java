package com.example.demo0810.dto.post;

import com.example.demo0810.Entity.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {

    private Long id;
    private String title;
    private String content;
    private String writer;
    private int count;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}

