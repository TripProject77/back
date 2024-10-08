package com.example.demo0810.dto.post;

import com.example.demo0810.Entity.PostEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class PostRequestDto {

    private String title;
    private String content;
    private String writer;
    private String mbti;
    private String place;

    @JsonFormat(pattern = "yyyy/MM/dd")  // JSON 파싱 시 날짜 형식 지정 , LocalDateTime으로 하면 초단위 까지 설정되서 LocalDate로 해야함
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate endDate;

    private List<String> tags;  // 작성한 태그 데이터를 받기 위한 필드

    @Builder
    public PostRequestDto(String title, String content, String writer, String mbti, String place, LocalDate startDate, LocalDate endDate, List<String> tags) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.mbti = mbti;
        this.place = place;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tags = tags;
    }

    // PostEntity로 변환 메서드 수정
    public PostEntity toPostEntity() {
        PostEntity post = PostEntity.builder()
                .title(title)
                .content(content)
                .writer(writer)
                .mbti(mbti)
                .place(place)
                .startDate(startDate)
                .endDate(endDate)
                .build();
        return post;
    }
}
