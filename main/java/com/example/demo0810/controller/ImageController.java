package com.example.demo0810.controller;

import com.example.demo0810.exception.CustomException;
import com.example.demo0810.exception.ErrorCode;
import com.example.demo0810.service.ImageService;
import com.example.demo0810.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000") // Allow CORS for this controller
public class ImageController {

    private final ImageService imageService;

    private final String FILE_PATH = "C://FileUpload/";
    private final UserService userService;

    // 파일을 업로드하고 경로를 반환하는 API
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file,
                             @RequestParam("username") String username) {
        try {
            // 파일 저장 경로
            String fileName = file.getOriginalFilename();
            String filePath = FILE_PATH + fileName;

            // DB에 저장할 파일 경로 (서버의 접근 가능한 URL로 설정)
            String fileUrl = "http://localhost:8080/file/" + fileName;

            // 사용자 프로필 이미지 경로 업데이트
            userService.updateUserProfileImage(username, fileUrl); // URL 경로 저장
            return fileUrl; // 클라이언트에 파일 URL 반환
        } catch (Exception e) {
            throw new RuntimeException("파일 업로드 실패", e);
        }
    }

    // 파일 서빙 API
    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable ("filename") String filename) {
        try {
            Path filePath = Paths.get(FILE_PATH).resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(Files.probeContentType(filePath)))
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("파일을 찾을 수 없습니다: " + filename, e);
        } catch (Exception e) {
            throw new RuntimeException("파일 타입을 확인할 수 없습니다: " + filename, e);
        }
    }


}
