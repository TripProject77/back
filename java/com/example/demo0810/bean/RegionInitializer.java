package com.example.demo0810.bean;

import com.example.demo0810.Entity.Region;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class RegionInitializer implements ApplicationRunner {

    @Value("${resources.location}")
    private String resourceLocation;

    private final EntityManager entityManager;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        File file = new File(resourceLocation);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            String line = br.readLine(); // skip header

            // 데이터 파싱 및 저장
            while ((line = br.readLine()) != null) {
                String[] splits = line.split(",");
                Long id = Long.parseLong(splits[0]);

                if (entityManager.find(Region.class, id) == null) {
                    entityManager.persist(new Region(
                            id,
                            splits[1],
                            splits[2],
                            Integer.parseInt(splits[3]),
                            Integer.parseInt(splits[4])
                    ));
                }
            }
            log.info("Region 데이터가 성공적으로 초기화되었습니다.");
        } catch (IOException e) {
            log.error("Region 데이터 초기화 중 오류 발생", e);
            throw new RuntimeException("Region 데이터 초기화 중 오류 발생", e);
        }
    }
}
