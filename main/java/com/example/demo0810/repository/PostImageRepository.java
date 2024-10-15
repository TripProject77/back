package com.example.demo0810.repository;

import com.example.demo0810.Entity.PostEntity;
import com.example.demo0810.Entity.PostImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageRepository extends JpaRepository<PostImageEntity, Long> {

    PostImageEntity findByPost(PostEntity post);
}
