package com.sparta.gamjaquick.config.jpa;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.gamjaquick.config.security.UserDetailsImpl;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

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
    public AuditorAware<String> auditorAware() { // @CreatedBy 에 해당하는 부분이 들어가게 된다.
        return () -> Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(auth -> {
                    Object principal = auth.getPrincipal();
                    if (principal instanceof UserDetailsImpl) {
                        return ((UserDetailsImpl) principal).getUsername();
                    } else {
                        return "";
                    }
                })
                .orElse("").describeConstable();
    }


}
