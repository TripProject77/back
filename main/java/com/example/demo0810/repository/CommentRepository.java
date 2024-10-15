package com.example.demo0810.repository;

import com.example.demo0810.Entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    List<CommentEntity> findByPostId(Long postId);

}
