package com.example.demo0810.repository;


import com.example.demo0810.Entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Long> {

    // JpaRepository 를 활용한 CRUD
    // JpaRepository 생성,
}
