package com.example.demo0810.repository;

import com.example.demo0810.Entity.ImageEntity;
import com.example.demo0810.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.*;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {

    ImageEntity findByUser(UserEntity user);
}