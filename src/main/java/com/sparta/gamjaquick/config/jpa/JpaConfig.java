package com.sparta.gamjaquick.config.jpa;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@Configuration
public class JpaConfig {

    // queryDSL JPAQueryFactory Bean 생성
    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);
    }

    // @CreatedBy, @LastModifiedDate 필드에 자동으로 값이 등록됩니다.
    @Bean
    public AuditorAware<String> auditorAware() {
        // TODO: security 적용 시 해당 로직 수정
        return "user1"::describeConstable;
    }
}
