package com.example.demo0810.repository;

import com.example.demo0810.Entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByTagContent(String tagContent);
}
