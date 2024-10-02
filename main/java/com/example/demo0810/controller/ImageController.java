package com.example.demo0810.controller;

import com.example.demo0810.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000") // Allow CORS for this controller
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/upload")
    public String upload(@RequestParam(value="file", required = false) MultipartFile file) {
        String url = imageService.uploadFile(file);
        return url;
    }

    @GetMapping(value="{fileName}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public Resource getImage(
            @PathVariable("fileName") String fileName
    ) {
        Resource resource = imageService.getImage(fileName);
        return resource;
    }

}
