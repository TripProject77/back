package com.example.demo0810.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="comment")
@Entity
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String comment;

    @Column
    private String author;

    @ManyToOne
    @JoinColumn(name="post_id")
    private PostEntity post;

    @ManyToOne
    @JoinColumn(name="user_id")
    private UserEntity user;

    private LocalDateTime createAtComment;

    @PrePersist
    public void prePersist() {
        this.createAtComment = LocalDateTime.now();
    }
}
