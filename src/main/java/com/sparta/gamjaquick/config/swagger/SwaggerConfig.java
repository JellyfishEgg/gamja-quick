package com.sparta.gamjaquick.config.swagger;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//일단은 jwt 없는 버전으로 해놓았습니다. 추후 인증 구현되면 수정하겠습니다

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("GAMJA QUICK API") // API의 제목
                .description("감자 퀵의 API 입니다.") // API에 대한 설명
                .version("1.0.0"); // API의 버전
    }
}