package com.example.demo0810.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;
import java.io.File;  // File 클래스 추가

@Service
public class ImageService {

    @Value("${file.path}")
    private String filePath;

    @Value("${file.url}")
    private String fileUrl;

    public String uploadFile(MultipartFile file) {

        if (file.isEmpty()) return null;

        String originalFileName = file.getOriginalFilename();
        System.out.println(originalFileName);

        // 지정된 형식으로 파일명을 만듭니다.
        String saveFileName = originalFileName;
        String savePath = filePath + saveFileName;  // 저장 경로

        try {
            // 파일을 지정된 경로에 저장
            file.transferTo(new File(savePath));
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }

        // 반환할 URL을 지정된 형식으로 생성
        String url = "http://localhost:8080/file/" + saveFileName;
        System.out.println(url);

        return url;
    }


    public Resource getImage(String fileName) {
        Resource resource = null;

        try {
            resource = new UrlResource("file:" + filePath + fileName);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
        return resource;
    }

}
