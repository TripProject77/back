package com.example.demo0810.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TravelDestinationDto {
    private int keywordRank;       // ALL_KWRD_RANK_CO {전체 키워드 수}
    private String searchWord;     // SRCHWRD_NM {검색어 명}
    private String upperCategory;  // UPPER_CTGRY_NM {상위 카테고리 명}
    private String lowerCategory;  // LWPRT_CTGRY_NM {하위 카테고리 명}
    private String areaOrCountryName; // AREA_NM 또는 COUNTRY_NM {지역명 , 또는 국가명}
}
