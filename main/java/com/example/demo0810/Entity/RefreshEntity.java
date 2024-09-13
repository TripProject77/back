package com.example.demo0810.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Data
public class RefreshEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Column(length = 512)
    private String refresh;

    private String expiration;
    private LocalDateTime createAt = LocalDateTime.now();
}
