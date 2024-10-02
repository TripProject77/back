package com.example.demo0810.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WeatherResponseDTO {
    private WeatherDto weather;
    private String message;
}
