package com.example.demo0810.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageUploadDto {

    private MultipartFile file;
}