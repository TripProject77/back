package com.example.demo0810.service;

import com.example.demo0810.Entity.CommentEntity;
import com.example.demo0810.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public void CommentSave(CommentEntity comment) {
        commentRepository.save(comment);
    }

    public List<CommentEntity> getAllComment(Long postId) {

        return commentRepository.findByPostId(postId);
    }
}
