package com.example.demo0810.dto;

import com.example.demo0810.Entity.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostUpdateDto {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createAtPost;

}
