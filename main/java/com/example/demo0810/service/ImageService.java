package com.example.demo0810.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService {

    private final String FILE_PATH = "C://FileUpload/";

    // 특정 폴더의 파일 목록을 읽어오기 위한 메서드
    public List<String> getFileList() {
        File folder = new File(FILE_PATH);
        File[] files = folder.listFiles();
        List<String> fileNames = new ArrayList<>();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {  // 파일만 목록에 추가
                    fileNames.add(file.getName());
                }
            }
        }
        return fileNames;
    }

    public String saveSelectedFilePath(String fileName) {
        String filePath = FILE_PATH + fileName;

        return filePath;
    }

    public Resource getImage(String fileName) {
        Resource resource = null;

        try {
            resource = new UrlResource("file:" + FILE_PATH + fileName);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
        return resource;
    }
}
