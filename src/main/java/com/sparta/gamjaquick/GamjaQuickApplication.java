package com.sparta.gamjaquick;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GamjaQuickApplication {

    public static void main(String[] args) {
        SpringApplication.run(GamjaQuickApplication.class, args);
    }

}
