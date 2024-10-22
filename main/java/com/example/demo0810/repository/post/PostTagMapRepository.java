package com.example.demo0810.repository.post;

import com.example.demo0810.Entity.post.Participation;
import com.example.demo0810.Entity.post.PostTagMap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostTagMapRepository extends JpaRepository<PostTagMap, Long> {

}

