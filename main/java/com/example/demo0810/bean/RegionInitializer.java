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

    // DB와의 상호작용
    private final EntityManager entityManager;

    // 메서드 내의 모든 DB 작업이 하나의 트랜잭션으로 처리됨
    // 오류가 발생하면 모든 변경 사항 롤백
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

                // find를 사용하여 해당 ID Region 엔티티가 이미 존재하는지 확인
                // 존재하지 않으면 새로운 Region을 생성하고 persist 메서드를 호출하여 DB에 저장
                // 이미 존재하는 경우 경고 로그 출력
                if (entityManager.find(Region.class, id) == null) {
                    entityManager.persist(new Region(
                            id,
                            splits[1],
                            splits[2],
                            Integer.parseInt(splits[3]),
                            Integer.parseInt(splits[4])
                    ));
                } else {
                    log.warn("ID {}를 가진 Region이 이미 존재합니다. 건너뜁니다.", id);
                }
            }
            log.info("Region 데이터가 성공적으로 초기화되었습니다.");
        } catch (IOException e) {
            log.error("Region 데이터 초기화 중 오류 발생", e);
            throw new RuntimeException("Region 데이터 초기화 중 오류 발생", e);
        }
    }
}
